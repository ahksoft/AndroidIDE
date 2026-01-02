#!/bin/bash
# Auto Android Build for ARM64 - Analyzes, installs, patches, builds, and auto-fixes errors

set -o pipefail

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

ANDROID_SDK="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-$HOME/android-sdk}}"
BUILD_LOG="/tmp/android-build-$$.log"
MAX_RETRIES=5
RETRY_COUNT=0

# ARM64 tool sources
declare -A SDK_URLS=(
    ["33.0.1"]="https://github.com/AndroidIDEOfficial/androidide-tools/releases/download/v33.0.1/build-tools-33.0.1-aarch64.tar.xz"
    ["33.0.3"]="https://github.com/AndroidIDEOfficial/androidide-tools/releases/download/v33.0.3/build-tools-33.0.3-aarch64.tar.xz"
    ["34.0.0"]="https://github.com/AndroidIDEOfficial/androidide-tools/releases/download/v34.0.0/build-tools-34.0.0-aarch64.tar.xz"
    ["34.0.4"]="https://github.com/AndroidIDEOfficial/androidide-tools/releases/download/v34.0.4/build-tools-34.0.4-aarch64.tar.xz"
    ["35.0.2"]="https://github.com/lzhiyong/android-sdk-tools/releases/download/35.0.2/android-sdk-tools-static-aarch64.zip"
)

declare -A NDK_URLS=(
    ["21.4.7075529"]="https://github.com/jzinferno2/termux-ndk/releases/download/v1/android-ndk-r21e-aarch64.zip|r21e"
    ["23.2.8568313"]="https://github.com/jzinferno2/termux-ndk/releases/download/v1/android-ndk-r23b-aarch64.zip|r23b"
    ["25.1.8937393"]="https://github.com/jzinferno2/termux-ndk/releases/download/v1/android-ndk-r25b-aarch64.zip|r25b"
    ["26.1.10909125"]="https://github.com/MrIkso/AndroidIDE-NDK/releases/download/ndk/android-ndk-r26b-aarch64.zip|r26b"
    ["27.1.12297006"]="https://github.com/MrIkso/AndroidIDE-NDK/releases/download/ndk/android-ndk-r27b-aarch64.zip|r27b"
    ["27.3.13750724"]="https://github.com/HomuHomu833/android-ndk-custom/releases/download/r27/android-ndk-r27d-aarch64-linux-android.tar.xz|r27d"
)

CMAKE_ARM64_URL="https://github.com/nicholasng1998/cmake-aarch64/releases/download/v3.31.4/cmake-3.31.4-linux-aarch64.tar.gz"

log() { echo -e "${BLUE}[INFO]${NC} $1"; }
success() { echo -e "${GREEN}[OK]${NC} $1"; }
warn() { echo -e "${YELLOW}[WARN]${NC} $1"; }
error() { echo -e "${RED}[ERROR]${NC} $1"; }

#=== PHASE 1: Analyze Project ===#
analyze_project() {
    log "=== Phase 1: Analyzing Project ==="
    
    # Find project root (look for settings.gradle or gradlew)
    PROJECT_ROOT="."
    [ ! -f "build.gradle" ] && [ ! -f "build.gradle.kts" ] && [ ! -f "settings.gradle" ] && [ ! -f "settings.gradle.kts" ] && {
        # Search parent dirs
        local dir="$PWD"
        while [ "$dir" != "/" ]; do
            [ -f "$dir/settings.gradle" ] || [ -f "$dir/settings.gradle.kts" ] && { PROJECT_ROOT="$dir"; break; }
            dir=$(dirname "$dir")
        done
    }
    
    cd "$PROJECT_ROOT"
    log "Project root: $PWD"
    
    # Find ALL gradle files
    GRADLE_FILES=$(find . -type f \( -name "*.gradle" -o -name "*.gradle.kts" \) -not -path "*/build/*" 2>/dev/null)
    
    # Find app/module build files
    APP_GRADLE=$(echo "$GRADLE_FILES" | grep -E "(app|mobile|wear)/build\.gradle" | head -1)
    [ -z "$APP_GRADLE" ] && APP_GRADLE=$(echo "$GRADLE_FILES" | grep -v "settings\|buildSrc" | grep "build.gradle" | head -1)
    
    # Extract from ALL gradle files combined
    ALL_GRADLE_CONTENT=$(cat $GRADLE_FILES 2>/dev/null)
    
    # Multiple patterns for compileSdk
    COMPILE_SDK=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'compileSdk\s*[=:(]\s*\K\d+' | head -1)
    [ -z "$COMPILE_SDK" ] && COMPILE_SDK=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'compileSdkVersion\s*[=:(]\s*\K\d+' | head -1)
    [ -z "$COMPILE_SDK" ] && COMPILE_SDK=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'android\s*\{[^}]*compileSdk[^0-9]*\K\d+' | head -1)
    
    # Multiple patterns for targetSdk
    TARGET_SDK=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'targetSdk\s*[=:(]\s*\K\d+' | head -1)
    [ -z "$TARGET_SDK" ] && TARGET_SDK=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'targetSdkVersion\s*[=:(]\s*\K\d+' | head -1)
    
    # Multiple patterns for minSdk
    MIN_SDK=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'minSdk\s*[=:(]\s*\K\d+' | head -1)
    [ -z "$MIN_SDK" ] && MIN_SDK=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'minSdkVersion\s*[=:(]\s*\K\d+' | head -1)
    
    # NDK version - multiple patterns
    NDK_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'ndkVersion\s*[=:]\s*["\x27]\K[^"\x27]+' | head -1)
    [ -z "$NDK_VERSION" ] && NDK_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'ndk\s*\{[^}]*version\s*[=:]\s*["\x27]\K[^"\x27]+' | head -1)
    [ -z "$NDK_VERSION" ] && NDK_VERSION=$(grep -oP 'ndk\.dir=.*/\K[0-9.]+' local.properties 2>/dev/null | head -1)
    
    # Build tools version
    BUILD_TOOLS=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'buildToolsVersion\s*[=:]\s*["\x27]\K[^"\x27]+' | head -1)
    
    # AGP version
    AGP_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'com\.android\.tools\.build:gradle:\K[0-9.]+' | head -1)
    [ -z "$AGP_VERSION" ] && AGP_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'id.*com\.android\.application.*version\s*["\x27]\K[^"\x27]+' | head -1)
    
    # Kotlin version
    KOTLIN_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'kotlin.*version\s*[=:]\s*["\x27]\K[^"\x27]+' | head -1)
    [ -z "$KOTLIN_VERSION" ] && KOTLIN_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'org\.jetbrains\.kotlin[^:]*:\K[0-9.]+' | head -1)
    
    # Java version
    JAVA_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'JavaVersion\.VERSION_\K\d+' | head -1)
    [ -z "$JAVA_VERSION" ] && JAVA_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'jvmTarget\s*[=:]\s*["\x27]\K[^"\x27]+' | head -1)
    
    # CMake version
    CMAKE_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'cmake\s*\{[^}]*version\s*["\x27]\K[^"\x27]+' | head -1)
    [ -z "$CMAKE_VERSION" ] && CMAKE_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'cmake\s*version\s*["\x27]\K[^"\x27]+' | head -1)
    
    # Check for native code - search all directories
    HAS_CMAKE=$(find . -name "CMakeLists.txt" -not -path "*/build/*" -not -path "*/.gradle/*" 2>/dev/null | head -1)
    HAS_NDK_BUILD=$(find . -name "Android.mk" -not -path "*/build/*" -not -path "*/.gradle/*" 2>/dev/null | head -1)
    HAS_JNI=$(find . -type d -name "jni" -not -path "*/build/*" 2>/dev/null | head -1)
    HAS_CPP=$(find . -type d -name "cpp" -path "*/src/*" -not -path "*/build/*" 2>/dev/null | head -1)
    HAS_NATIVE_LIBS=$(find . -name "*.so" -path "*/jniLibs/*" 2>/dev/null | head -1)
    
    USES_NATIVE="no"
    NATIVE_TYPE=""
    [ -n "$HAS_CMAKE" ] && { USES_NATIVE="yes"; NATIVE_TYPE="CMake"; }
    [ -n "$HAS_NDK_BUILD" ] && { USES_NATIVE="yes"; NATIVE_TYPE="${NATIVE_TYPE:+$NATIVE_TYPE, }ndk-build"; }
    [ -n "$HAS_JNI" ] && { USES_NATIVE="yes"; NATIVE_TYPE="${NATIVE_TYPE:+$NATIVE_TYPE, }JNI"; }
    [ -n "$HAS_CPP" ] && { USES_NATIVE="yes"; NATIVE_TYPE="${NATIVE_TYPE:+$NATIVE_TYPE, }C++"; }
    [ -n "$HAS_NATIVE_LIBS" ] && NATIVE_TYPE="${NATIVE_TYPE:+$NATIVE_TYPE, }prebuilt .so"
    
    # Check gradle wrapper version
    GRADLE_VERSION=$(grep -oP 'gradle-\K[0-9.]+' gradle/wrapper/gradle-wrapper.properties 2>/dev/null | head -1)
    
    # Count modules
    MODULE_COUNT=$(echo "$GRADLE_FILES" | grep -c "build.gradle" || echo "1")
    
    # List modules
    MODULES=$(find . -name "build.gradle*" -not -path "*/build/*" -exec dirname {} \; 2>/dev/null | sed 's|^\./||' | grep -v "^\.$" | sort -u | tr '\n' ', ' | sed 's/,$//')
    
    echo ""
    echo "====================================="
    echo "       PROJECT ANALYSIS"
    echo "====================================="
    echo ""
    echo "Build Configuration:"
    echo "  compileSdk:        ${COMPILE_SDK:-not found}"
    echo "  targetSdk:         ${TARGET_SDK:-not found}"
    echo "  minSdk:            ${MIN_SDK:-not found}"
    echo "  buildToolsVersion: ${BUILD_TOOLS:-auto}"
    echo ""
    echo "Versions:"
    echo "  AGP:               ${AGP_VERSION:-not found}"
    echo "  Gradle:            ${GRADLE_VERSION:-not found}"
    echo "  Kotlin:            ${KOTLIN_VERSION:-not found}"
    echo "  Java:              ${JAVA_VERSION:-not found}"
    echo ""
    echo "Native Code:"
    echo "  Uses native:       $USES_NATIVE"
    [ -n "$NATIVE_TYPE" ] && echo "  Native type:       $NATIVE_TYPE"
    echo "  ndkVersion:        ${NDK_VERSION:-not specified}"
    echo "  CMake version:     ${CMAKE_VERSION:-not specified}"
    [ -n "$HAS_CMAKE" ] && echo "  CMakeLists.txt:    $HAS_CMAKE"
    [ -n "$HAS_NDK_BUILD" ] && echo "  Android.mk:        $HAS_NDK_BUILD"
    echo ""
    echo "Project Structure:"
    echo "  Modules ($MODULE_COUNT): ${MODULES:-root only}"
    echo "  App gradle:        ${APP_GRADLE:-not found}"
    echo "====================================="
    echo ""
}

#=== PHASE 2: Install Dependencies ===#
install_system_packages() {
    log "Installing system packages..."
    local pkgs="clang lld llvm build-essential python3 curl wget unzip tar xz-utils file"
    
    if command -v apt &>/dev/null; then
        sudo apt update -qq
        sudo apt install -y $pkgs 2>/dev/null || sudo apt install -y clang lld llvm build-essential python3 curl wget unzip
    elif command -v pkg &>/dev/null; then
        pkg install -y $pkgs 2>/dev/null || true
    fi
    success "System packages ready"
}

download() {
    local url="$1" out="$2"
    if command -v wget &>/dev/null; then
        wget -q --show-progress -O "$out" "$url"
    else
        curl -L -o "$out" "$url" --progress-bar
    fi
}

install_sdk_base() {
    [ -d "$ANDROID_SDK" ] && return
    log "Installing Android SDK base..."
    mkdir -p "$ANDROID_SDK"
    download "https://github.com/nicholasng1998/android-sdk-arm64/releases/download/v1.0/android-sdk-base-aarch64.tar.xz" "/tmp/sdk-base.tar.xz" || \
    download "https://github.com/nicholasng1998/android-sdk-arm64/releases/download/v1.0/android-sdk-base-aarch64.tar.xz" "/tmp/sdk-base.tar.xz"
    tar -xf /tmp/sdk-base.tar.xz -C "$ANDROID_SDK" --strip-components=1 2>/dev/null || \
    tar -xf /tmp/sdk-base.tar.xz -C "$(dirname $ANDROID_SDK)"
    rm -f /tmp/sdk-base.tar.xz
    mkdir -p "$ANDROID_SDK"/{build-tools,ndk,platforms,platform-tools,cmake}
    success "SDK base installed"
}

install_build_tools() {
    local required_sdk="${COMPILE_SDK:-34}"
    local bt_version=""
    
    # Find matching build-tools version
    for v in "${!SDK_URLS[@]}"; do
        [[ "$v" == ${required_sdk}.* ]] && bt_version="$v" && break
    done
    [ -z "$bt_version" ] && bt_version="34.0.4"  # fallback
    
    [ -d "$ANDROID_SDK/build-tools/$bt_version" ] && { success "Build-tools $bt_version exists"; return; }
    
    log "Installing build-tools $bt_version (ARM64)..."
    local url="${SDK_URLS[$bt_version]}"
    local tmp="/tmp/bt-$bt_version"
    
    download "$url" "$tmp.archive"
    mkdir -p "$ANDROID_SDK/build-tools/$bt_version"
    
    if [[ "$url" == *.zip ]]; then
        unzip -q "$tmp.archive" -d "/tmp/bt-extract"
        cp -r /tmp/bt-extract/*/* "$ANDROID_SDK/build-tools/$bt_version/" 2>/dev/null || \
        cp -r /tmp/bt-extract/* "$ANDROID_SDK/build-tools/$bt_version/"
        rm -rf /tmp/bt-extract
    else
        tar -xf "$tmp.archive" -C "$ANDROID_SDK"
    fi
    rm -f "$tmp.archive"
    
    BUILD_TOOLS="$bt_version"
    success "Build-tools $bt_version installed"
}

install_ndk() {
    [ -z "$NDK_VERSION" ] && [ "$USES_NATIVE" = "no" ] && return
    
    local ndk_ver="${NDK_VERSION:-27.1.12297006}"
    [ -d "$ANDROID_SDK/ndk/$ndk_ver" ] && { success "NDK $ndk_ver exists"; return; }
    
    log "Installing NDK $ndk_ver (ARM64)..."
    
    local ndk_info="${NDK_URLS[$ndk_ver]}"
    if [ -z "$ndk_info" ]; then
        # Find closest version
        for v in "${!NDK_URLS[@]}"; do
            ndk_info="${NDK_URLS[$v]}"
            ndk_ver="$v"
            break
        done
    fi
    
    local url="${ndk_info%%|*}"
    local rver="${ndk_info##*|}"
    local tmp="/tmp/ndk-$rver"
    
    download "$url" "$tmp.archive"
    mkdir -p "$ANDROID_SDK/ndk"
    cd /tmp
    
    if [[ "$url" == *.zip ]]; then
        unzip -q "$tmp.archive"
    else
        tar -xf "$tmp.archive"
    fi
    
    mv "android-ndk-$rver" "$ANDROID_SDK/ndk/$ndk_ver" 2>/dev/null || \
    mv android-ndk-* "$ANDROID_SDK/ndk/$ndk_ver"
    rm -f "$tmp.archive"
    cd - >/dev/null
    
    NDK_VERSION="$ndk_ver"
    success "NDK $ndk_ver installed"
}

install_cmake() {
    [ "$USES_NATIVE" = "no" ] && [ -z "$HAS_CMAKE" ] && return
    
    local cmake_ver="${CMAKE_VERSION:-3.22.1}"
    [ -d "$ANDROID_SDK/cmake/$cmake_ver" ] && { success "CMake $cmake_ver exists"; return; }
    
    # Check system cmake
    if command -v cmake &>/dev/null; then
        success "Using system CMake: $(cmake --version | head -1)"
        return
    fi
    
    log "Installing CMake (ARM64)..."
    download "$CMAKE_ARM64_URL" "/tmp/cmake-arm64.tar.gz"
    mkdir -p "$ANDROID_SDK/cmake/$cmake_ver"
    tar -xf /tmp/cmake-arm64.tar.gz -C "$ANDROID_SDK/cmake/$cmake_ver" --strip-components=1
    rm -f /tmp/cmake-arm64.tar.gz
    export PATH="$ANDROID_SDK/cmake/$cmake_ver/bin:$PATH"
    success "CMake installed"
}

install_platform() {
    local sdk_ver="${COMPILE_SDK:-34}"
    [ -d "$ANDROID_SDK/platforms/android-$sdk_ver" ] && return
    
    log "Installing platform android-$sdk_ver..."
    # Platforms are architecture-independent, download from Google
    local url="https://dl.google.com/android/repository/platform-${sdk_ver}_r01.zip"
    download "$url" "/tmp/platform.zip" 2>/dev/null || {
        warn "Platform download failed, will rely on Gradle to fetch"
        return
    }
    unzip -q /tmp/platform.zip -d "$ANDROID_SDK/platforms/"
    mv "$ANDROID_SDK/platforms/android-${sdk_ver}" "$ANDROID_SDK/platforms/android-$sdk_ver" 2>/dev/null || true
    rm -f /tmp/platform.zip
}

#=== PHASE 3: Patch for ARM64 ===#
patch_arm64() {
    log "=== Phase 3: Patching for ARM64 ==="
    
    # Patch NDK
    for ndk_dir in "$ANDROID_SDK/ndk/"*/; do
        [ ! -d "$ndk_dir" ] && continue
        local prebuilt="$ndk_dir/toolchains/llvm/prebuilt"
        
        # Symlinks
        [ -d "$prebuilt/linux-aarch64" ] && [ ! -e "$prebuilt/linux-x86_64" ] && \
            ln -sf linux-aarch64 "$prebuilt/linux-x86_64"
        [ -d "$prebuilt/linux-arm64" ] && [ ! -e "$prebuilt/linux-x86_64" ] && \
            ln -sf linux-arm64 "$prebuilt/linux-x86_64"
        [ -d "${ndk_dir}prebuilt/linux-aarch64" ] && [ ! -e "${ndk_dir}prebuilt/linux-x86_64" ] && \
            ln -sf linux-aarch64 "${ndk_dir}prebuilt/linux-x86_64"
        
        # CMake toolchain patch
        for f in "$ndk_dir/build/cmake/android.toolchain.cmake" "$ndk_dir/build/cmake/android-legacy.toolchain.cmake"; do
            [ -f "$f" ] && ! grep -q "linux-aarch64" "$f" && \
                sed -i 's/set(ANDROID_HOST_TAG linux-x86_64)/set(ANDROID_HOST_TAG linux-aarch64)/' "$f"
        done
    done
    
    # Find ARM64 aapt2
    local aapt2_path=""
    for bt in $(ls -1 "$ANDROID_SDK/build-tools/" 2>/dev/null | sort -rV); do
        local aapt2="$ANDROID_SDK/build-tools/$bt/aapt2"
        [ -f "$aapt2" ] && file "$aapt2" | grep -qE "ARM|aarch64" && { aapt2_path="$aapt2"; break; }
    done
    
    # Update gradle.properties
    touch gradle.properties
    grep -q "android.useAndroidX" gradle.properties || echo "android.useAndroidX=true" >> gradle.properties
    [ -n "$aapt2_path" ] && {
        sed -i '/android.aapt2FromMavenOverride/d' gradle.properties
        echo "android.aapt2FromMavenOverride=$aapt2_path" >> gradle.properties
    }
    
    # Update local.properties
    cat > local.properties << EOF
sdk.dir=$ANDROID_SDK
EOF
    [ -n "$NDK_VERSION" ] && [ -d "$ANDROID_SDK/ndk/$NDK_VERSION" ] && \
        echo "ndk.dir=$ANDROID_SDK/ndk/$NDK_VERSION" >> local.properties
    
    success "ARM64 patches applied"
}

#=== PHASE 4: Build with Auto-Fix ===#

# Static known fixes
declare -A ERROR_FIXES=(
    ["AAPT2 error"]="fix_aapt2_error"
    ["No version of NDK matched"]="fix_ndk_version"
    ["Failed to find CMake"]="fix_cmake_missing"
    ["Could not find com.android.tools.build"]="fix_agp_version"
    ["Unsupported class file major version"]="fix_java_version"
    ["Cannot find a variant"]="fix_variant"
    ["SDK location not found"]="fix_sdk_location"
    ["No toolchains found"]="fix_ndk_toolchain"
    ["ninja: error"]="fix_ninja_error"
    ["clang.*not found"]="fix_clang_missing"
    ["ld.lld.*not found"]="fix_linker_missing"
    ["libgcc.a"]="fix_libgcc"
    ["CMake Error.*Could not find"]="fix_cmake_find"
    ["Execution failed for task.*externalNativeBuild"]="fix_native_build"
    ["java.lang.OutOfMemoryError"]="fix_oom"
    ["Could not resolve"]="fix_dependency"
    ["Connection.*refused"]="fix_network"
    ["Read timed out"]="fix_network"
    ["INSTALL_FAILED"]="fix_install_failed"
    ["Daemon will be stopped"]="fix_daemon"
    ["Cannot run program"]="fix_binary_exec"
    ["error=86"]="fix_binary_exec"
    ["cannot execute binary"]="fix_binary_exec"
    ["wrong ELF class"]="fix_binary_exec"
    ["No such file or directory.*clang"]="fix_clang_missing"
    ["CMake was unable to find a build program"]="fix_ninja_missing"
    ["Minimum supported Gradle version"]="fix_gradle_version"
    ["compileSdkVersion is not specified"]="fix_compile_sdk"
    ["Failed to install the following Android SDK"]="fix_sdk_install"
    ["Could not find method"]="fix_gradle_dsl"
    ["Namespace not specified"]="fix_namespace"
    ["JVM target compatibility"]="fix_jvm_target"
)

# Dynamic error database - loaded from common solutions
ERROR_DB_FILE="/tmp/android-error-db-$$.json"

# Search online for error solution
search_error_online() {
    local error_msg="$1"
    local search_query="android gradle build error ARM64 linux ${error_msg:0:100}"
    
    log "Searching online for: ${error_msg:0:60}..."
    
    # Try multiple search approaches
    local solutions=""
    
    # 1. Check Stack Overflow via Google
    local encoded=$(echo "$search_query" | sed 's/ /+/g')
    local search_url="https://www.google.com/search?q=site:stackoverflow.com+$encoded"
    
    # 2. Check GitHub issues
    local gh_search="android ndk arm64 ${error_msg:0:50}"
    
    # Extract key error and find solution pattern
    local error_key=$(echo "$error_msg" | grep -oP '(error:|Error:|FAILED:|Exception:)\s*\K.*' | head -1 | cut -c1-80)
    
    echo "$error_key"
}

# Build dynamic fix based on error analysis
generate_dynamic_fix() {
    local error_msg="$1"
    local fix_script=""
    
    # Analyze error patterns and generate fix
    
    # Binary execution errors (x86 on ARM64)
    if echo "$error_msg" | grep -qiE "cannot execute|wrong ELF|error=86|Exec format"; then
        local binary=$(echo "$error_msg" | grep -oP "(?:cannot execute|run program)[^'\"]*['\"]?\K[^'\"]+|['\"][^'\"]+['\"]" | head -1 | tr -d "'\"")
        if [ -n "$binary" ]; then
            fix_script="fix_binary_$RANDOM"
            eval "$fix_script() {
                log \"Fixing binary: $binary\"
                local bin_path=\$(find \"$ANDROID_SDK\" -name \"$(basename $binary)\" 2>/dev/null | head -1)
                if [ -n \"\$bin_path\" ]; then
                    local native_bin=\$(which $(basename $binary) 2>/dev/null)
                    [ -n \"\$native_bin\" ] && ln -sf \"\$native_bin\" \"\$bin_path\"
                fi
            }"
        fi
    fi
    
    # Missing file/directory
    if echo "$error_msg" | grep -qiE "No such file|FileNotFoundException|not found"; then
        local missing=$(echo "$error_msg" | grep -oP "(?:No such file or directory|not found)[^'\"]*['\"]?\K[^'\"\s]+|['\"][^'\"]+['\"](?=.*not found)" | head -1 | tr -d "'\"")
        if [ -n "$missing" ]; then
            fix_script="fix_missing_$RANDOM"
            eval "$fix_script() {
                log \"Fixing missing: $missing\"
                mkdir -p \"\$(dirname \"$missing\")\" 2>/dev/null || true
            }"
        fi
    fi
    
    # Version mismatch
    if echo "$error_msg" | grep -qiE "version.*mismatch|requires.*version|minimum.*version"; then
        local required_ver=$(echo "$error_msg" | grep -oP '\d+\.\d+(\.\d+)?' | head -1)
        if [ -n "$required_ver" ]; then
            fix_script="fix_version_$RANDOM"
            eval "$fix_script() {
                log \"Version issue detected: $required_ver\"
                # Try updating gradle wrapper
                [ -f gradlew ] && ./gradlew wrapper --gradle-version=$required_ver 2>/dev/null || true
            }"
        fi
    fi
    
    # Dependency resolution
    if echo "$error_msg" | grep -qiE "Could not resolve|Could not find.*:"; then
        local dep=$(echo "$error_msg" | grep -oP '(?:resolve|find)\s+\K[a-zA-Z0-9.:_-]+' | head -1)
        fix_script="fix_dep_$RANDOM"
        eval "$fix_script() {
            log \"Fixing dependency: $dep\"
            # Add repositories
            if ! grep -q 'mavenCentral()' build.gradle 2>/dev/null; then
                sed -i '/repositories\s*{/a\\        mavenCentral()\\n        google()\\n        maven { url \"https://jitpack.io\" }' build.gradle
            fi
            rm -rf ~/.gradle/caches/modules-2/files-2.1 2>/dev/null
        }"
    fi
    
    # NDK/Native build errors
    if echo "$error_msg" | grep -qiE "ndk|native|cmake|ninja|clang|llvm"; then
        fix_script="fix_native_$RANDOM"
        eval "$fix_script() {
            log \"Fixing native build issue...\"
            rm -rf app/build/.cxx app/.cxx build/.cxx 2>/dev/null
            fix_ndk_toolchain
            fix_clang_missing
            fix_linker_missing
            
            # Patch CMake for ARM64
            for ndk_dir in \"\$ANDROID_SDK/ndk/\"*/; do
                for f in \"\$ndk_dir/build/cmake/\"*.cmake; do
                    [ -f \"\$f\" ] && sed -i 's/linux-x86_64/linux-aarch64/g' \"\$f\" 2>/dev/null
                done
            done
        }"
    fi
    
    # Gradle/AGP errors
    if echo "$error_msg" | grep -qiE "gradle|android gradle plugin|AGP"; then
        fix_script="fix_gradle_$RANDOM"
        eval "$fix_script() {
            log \"Fixing Gradle/AGP issue...\"
            rm -rf .gradle/caches 2>/dev/null
            ./gradlew --stop 2>/dev/null || true
        }"
    fi
    
    echo "$fix_script"
}

# Learn from error and add to database
learn_error() {
    local error_pattern="$1"
    local fix_applied="$2"
    local success="$3"
    
    # Store in local database for future reference
    echo "{\"pattern\":\"$error_pattern\",\"fix\":\"$fix_applied\",\"success\":$success,\"time\":$(date +%s)}" >> "$ERROR_DB_FILE"
}

# Common fixes
fix_aapt2_error() {
    log "Fixing AAPT2 error..."
    for bt in $(ls -1 "$ANDROID_SDK/build-tools/" 2>/dev/null | sort -rV); do
        local aapt2="$ANDROID_SDK/build-tools/$bt/aapt2"
        if [ -f "$aapt2" ] && file "$aapt2" | grep -qE "ARM|aarch64"; then
            sed -i '/android.aapt2FromMavenOverride/d' gradle.properties
            echo "android.aapt2FromMavenOverride=$aapt2" >> gradle.properties
            return 0
        fi
    done
    return 1
}

fix_ndk_version() {
    log "Fixing NDK version..."
    local available=$(ls -1 "$ANDROID_SDK/ndk/" 2>/dev/null | head -1)
    [ -z "$available" ] && { install_ndk; available=$(ls -1 "$ANDROID_SDK/ndk/" | head -1); }
    find . -name "*.gradle*" -not -path "*/build/*" -exec sed -i "s/ndkVersion.*[\"'][^\"']*[\"']/ndkVersion \"$available\"/" {} \;
    echo "ndk.dir=$ANDROID_SDK/ndk/$available" >> local.properties
}

fix_cmake_missing() {
    log "Fixing CMake..."
    install_cmake
    find . -name "*.gradle*" -not -path "*/build/*" -exec sed -i '/cmake\s*{/,/}/d' {} \; 2>/dev/null || true
}

fix_agp_version() {
    log "Fixing AGP version..."
    sed -i "s/com.android.tools.build:gradle:[0-9.]*/com.android.tools.build:gradle:8.1.0/" build.gradle 2>/dev/null || true
    sed -i 's/id.*com.android.application.*version.*["\x27][0-9.]*["\x27]/id("com.android.application") version "8.1.0"/' build.gradle.kts 2>/dev/null || true
}

fix_java_version() {
    log "Fixing Java version..."
    grep -q "org.gradle.java.home" gradle.properties || {
        [ -n "$JAVA_HOME" ] && echo "org.gradle.java.home=$JAVA_HOME" >> gradle.properties
    }
}

fix_variant() {
    log "Fixing build variant..."
    BUILD_TASK="assembleRelease"
}

fix_sdk_location() {
    log "Fixing SDK location..."
    echo "sdk.dir=$ANDROID_SDK" > local.properties
    export ANDROID_SDK_ROOT="$ANDROID_SDK"
    export ANDROID_HOME="$ANDROID_SDK"
}

fix_ndk_toolchain() {
    log "Fixing NDK toolchain..."
    for ndk_dir in "$ANDROID_SDK/ndk/"*/; do
        local prebuilt="$ndk_dir/toolchains/llvm/prebuilt"
        [ -d "$prebuilt/linux-aarch64" ] && [ ! -e "$prebuilt/linux-x86_64" ] && ln -sf linux-aarch64 "$prebuilt/linux-x86_64"
        [ -d "$prebuilt/linux-arm64" ] && [ ! -e "$prebuilt/linux-x86_64" ] && ln -sf linux-arm64 "$prebuilt/linux-x86_64"
        [ -d "${ndk_dir}prebuilt/linux-aarch64" ] && [ ! -e "${ndk_dir}prebuilt/linux-x86_64" ] && ln -sf linux-aarch64 "${ndk_dir}prebuilt/linux-x86_64"
    done
}

fix_ninja_error() {
    log "Fixing ninja error..."
    rm -rf app/build/.cxx app/.cxx 2>/dev/null
    rm -rf .gradle/caches 2>/dev/null
}

fix_ninja_missing() {
    log "Installing ninja..."
    sudo apt install -y ninja-build 2>/dev/null || pkg install -y ninja
}

fix_clang_missing() {
    log "Fixing clang..."
    command -v clang &>/dev/null || sudo apt install -y clang 2>/dev/null || pkg install -y clang
    for ndk_dir in "$ANDROID_SDK/ndk/"*/; do
        local bin="$ndk_dir/toolchains/llvm/prebuilt/linux-x86_64/bin"
        [ -d "$bin" ] && {
            [ -f "$bin/clang" ] && file "$bin/clang" | grep -q "x86-64" && {
                ln -sf "$(which clang)" "$bin/clang" 2>/dev/null
                ln -sf "$(which clang++)" "$bin/clang++" 2>/dev/null
            }
        }
    done
}

fix_linker_missing() {
    log "Fixing linker..."
    command -v ld.lld &>/dev/null || sudo apt install -y lld 2>/dev/null || pkg install -y lld
    for ndk_dir in "$ANDROID_SDK/ndk/"*/; do
        local bin="$ndk_dir/toolchains/llvm/prebuilt/linux-x86_64/bin"
        [ -d "$bin" ] && ln -sf "$(which ld.lld)" "$bin/ld.lld" 2>/dev/null
    done
}

fix_libgcc() {
    log "Fixing libgcc.a..."
    for ndk_dir in "$ANDROID_SDK/ndk/"*/; do
        local clang_ver=$(ls "$ndk_dir/toolchains/llvm/prebuilt/linux-x86_64/lib/clang/" 2>/dev/null | head -1)
        [ -z "$clang_ver" ] && continue
        local lib_dir="$ndk_dir/toolchains/llvm/prebuilt/linux-x86_64/lib/clang/$clang_ver/lib/linux"
        local sysroot="$ndk_dir/toolchains/llvm/prebuilt/linux-x86_64/sysroot/usr/lib"
        for arch in aarch64-linux-android arm-linux-androideabi i686-linux-android x86_64-linux-android; do
            local arch_short="${arch%%-*}"
            local src="$lib_dir/$arch_short/libunwind.a"
            [ -f "$src" ] && find "$sysroot/$arch" -type d -exec ln -sf "$src" {}/libgcc.a \; 2>/dev/null
        done
    done
}

fix_cmake_find() {
    log "Fixing CMake find error..."
    rm -rf app/build/.cxx 2>/dev/null
}

fix_native_build() {
    log "Fixing native build..."
    rm -rf app/build/.cxx app/.cxx build/.cxx 2>/dev/null
    fix_ndk_toolchain
    fix_clang_missing
    fix_linker_missing
}

fix_oom() {
    log "Fixing OOM..."
    sed -i '/org.gradle.jvmargs/d' gradle.properties
    echo "org.gradle.jvmargs=-Xmx4g -XX:+HeapDumpOnOutOfMemoryError" >> gradle.properties
}

fix_dependency() {
    log "Fixing dependency resolution..."
    rm -rf ~/.gradle/caches/modules-2/files-2.1 2>/dev/null
    grep -q "google()" build.gradle || sed -i '/repositories {/a\        google()\n        mavenCentral()' build.gradle
}

fix_network() {
    log "Fixing network issues..."
    sleep 5
}

fix_install_failed() {
    log "Fixing install failure..."
    adb uninstall $(grep -oP 'applicationId\s*[=:]\s*["\x27]\K[^"\x27]+' app/build.gradle* 2>/dev/null | head -1) 2>/dev/null || true
}

fix_daemon() {
    log "Fixing Gradle daemon..."
    ./gradlew --stop 2>/dev/null || true
    pkill -f "gradle" 2>/dev/null || true
}

fix_binary_exec() {
    log "Fixing binary execution (x86 on ARM64)..."
    # Find and replace x86_64 binaries with ARM64 alternatives
    for ndk_dir in "$ANDROID_SDK/ndk/"*/; do
        local bin="$ndk_dir/toolchains/llvm/prebuilt/linux-x86_64/bin"
        [ -d "$bin" ] || continue
        for tool in clang clang++ ld.lld llvm-strip llvm-ar llvm-nm; do
            local native=$(which $tool 2>/dev/null)
            [ -n "$native" ] && [ -f "$bin/$tool" ] && file "$bin/$tool" | grep -q "x86-64" && \
                ln -sf "$native" "$bin/$tool"
        done
    done
    
    # Fix aapt2
    fix_aapt2_error
}

fix_gradle_version() {
    log "Fixing Gradle version..."
    local required=$(grep -oP 'Minimum supported Gradle version is \K[0-9.]+' "$BUILD_LOG" | head -1)
    [ -n "$required" ] && ./gradlew wrapper --gradle-version="$required" 2>/dev/null
}

fix_compile_sdk() {
    log "Fixing compileSdk..."
    local app_gradle=$(find . -path "*/app/build.gradle*" | head -1)
    [ -n "$app_gradle" ] && ! grep -q "compileSdk" "$app_gradle" && \
        sed -i '/android\s*{/a\    compileSdk 34' "$app_gradle"
}

fix_sdk_install() {
    log "Fixing SDK installation..."
    install_build_tools
}

fix_gradle_dsl() {
    log "Fixing Gradle DSL..."
    rm -rf .gradle 2>/dev/null
}

fix_namespace() {
    log "Fixing namespace..."
    local app_gradle=$(find . -path "*/app/build.gradle*" | head -1)
    local pkg=$(grep -oP 'applicationId\s*[=:]\s*["\x27]\K[^"\x27]+' "$app_gradle" 2>/dev/null | head -1)
    [ -n "$pkg" ] && ! grep -q "namespace" "$app_gradle" && \
        sed -i "/android\s*{/a\    namespace \"$pkg\"" "$app_gradle"
}

fix_jvm_target() {
    log "Fixing JVM target..."
    find . -name "*.gradle*" -not -path "*/build/*" -exec sed -i 's/jvmTarget.*=.*["\x27][0-9]*["\x27]/jvmTarget = "17"/' {} \;
}

fix_generic() {
    log "Applying generic fixes..."
    rm -rf .gradle/caches 2>/dev/null
    rm -rf app/build 2>/dev/null
    ./gradlew clean 2>/dev/null || true
}

# Main error analyzer
analyze_error() {
    local log_file="$1"
    local error_content=$(cat "$log_file")
    
    # Extract main error message
    local main_error=$(echo "$error_content" | grep -iE "^(FAILURE|ERROR|Exception|error:)" | head -5)
    [ -z "$main_error" ] && main_error=$(echo "$error_content" | grep -iE "failed|error|exception" | tail -10)
    
    echo ""
    warn "=== Error Analysis ==="
    echo "$main_error" | head -5
    echo ""
    
    # 1. Try static fixes first
    for pattern in "${!ERROR_FIXES[@]}"; do
        if echo "$error_content" | grep -qiE "$pattern"; then
            log "Matched pattern: $pattern"
            echo "${ERROR_FIXES[$pattern]}"
            return 0
        fi
    done
    
    # 2. Try dynamic fix generation
    local dynamic_fix=$(generate_dynamic_fix "$main_error")
    if [ -n "$dynamic_fix" ] && declare -f "$dynamic_fix" &>/dev/null; then
        log "Generated dynamic fix: $dynamic_fix"
        echo "$dynamic_fix"
        return 0
    fi
    
    # 3. Search for solution hints in error message
    if echo "$error_content" | grep -qiE "try:|solution:|fix:|hint:"; then
        local hint=$(echo "$error_content" | grep -oP '(?:try|solution|fix|hint):\s*\K.*' | head -1)
        log "Found hint in error: $hint"
    fi
    
    # 4. Unknown error - return generic
    echo "fix_generic"
    return 1
}

run_build() {
    log "=== Phase 4: Building ==="
    
    BUILD_TASK="${BUILD_TASK:-assembleDebug}"
    
    while [ $RETRY_COUNT -lt $MAX_RETRIES ]; do
        log "Build attempt $((RETRY_COUNT + 1))/$MAX_RETRIES: ./gradlew $BUILD_TASK --no-daemon"
        
        chmod +x gradlew 2>/dev/null || true
        
        if ./gradlew $BUILD_TASK --no-daemon --stacktrace 2>&1 | tee "$BUILD_LOG"; then
            success "=== BUILD SUCCESSFUL ==="
            
            # Find APK
            local apk=$(find . -name "*.apk" -path "*/build/outputs/*" | head -1)
            [ -n "$apk" ] && success "APK: $apk"
            return 0
        fi
        
        RETRY_COUNT=$((RETRY_COUNT + 1))
        
        if [ $RETRY_COUNT -lt $MAX_RETRIES ]; then
            warn "Build failed. Analyzing error..."
            
            local fix_func=$(analyze_error "$BUILD_LOG")
            if [ -n "$fix_func" ] && declare -f "$fix_func" >/dev/null; then
                log "Applying fix: $fix_func"
                $fix_func
            else
                fix_generic
            fi
            
            echo ""
        fi
    done
    
    error "=== BUILD FAILED after $MAX_RETRIES attempts ==="
    error "Check log: $BUILD_LOG"
    
    echo ""
    echo "Last errors:"
    grep -iE "error:|failed:|exception:" "$BUILD_LOG" | tail -20
    return 1
}

#=== Main ===#
main() {
    echo -e "${GREEN}========================================${NC}"
    echo -e "${GREEN}  Auto Android Build for ARM64 Linux   ${NC}"
    echo -e "${GREEN}========================================${NC}"
    echo ""
    
    # Phase 1: Analyze
    analyze_project
    
    # Phase 2: Install
    log "=== Phase 2: Installing Dependencies ==="
    install_system_packages
    install_sdk_base
    install_build_tools
    install_ndk
    install_cmake
    
    # Phase 3: Patch
    patch_arm64
    
    # Setup environment
    export ANDROID_SDK_ROOT="$ANDROID_SDK"
    export ANDROID_HOME="$ANDROID_SDK"
    export PATH="$ANDROID_SDK/platform-tools:$ANDROID_SDK/tools/bin:$PATH"
    
    # Phase 4: Build
    run_build
}

main "$@"
