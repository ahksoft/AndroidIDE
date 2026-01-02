#!/bin/bash
# Android ARM64 Setup - Analyze, Install, and Patch with user confirmation

set -e

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

ANDROID_SDK="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-$HOME/android-sdk}}"

log() { echo -e "${BLUE}[INFO]${NC} $1"; }
success() { echo -e "${GREEN}[OK]${NC} $1"; }
warn() { echo -e "${YELLOW}[WARN]${NC} $1"; }
error() { echo -e "${RED}[ERROR]${NC} $1"; }

ask() {
    echo ""
    read -p "$1 [y/N]: " confirm
    [[ "$confirm" =~ ^[Yy] ]]
}

#=== ARM64 Tool Sources ===#
declare -A SDK_URLS=(
    ["33.0.1"]="https://github.com/AndroidIDEOfficial/androidide-tools/releases/download/v33.0.1/build-tools-33.0.1-aarch64.tar.xz"
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
)

CMAKE_URL="https://github.com/nicholasng1998/cmake-aarch64/releases/download/v3.31.4/cmake-3.31.4-linux-aarch64.tar.gz"

download() {
    local url="$1" out="$2"
    if command -v wget &>/dev/null; then
        wget -q --show-progress -O "$out" "$url"
    else
        curl -L -o "$out" "$url" --progress-bar
    fi
}

#=== PHASE 1: Analyze Project ===#
analyze_project() {
    echo -e "${GREEN}========================================${NC}"
    echo -e "${GREEN}    Android ARM64 Setup Tool           ${NC}"
    echo -e "${GREEN}========================================${NC}"
    echo ""
    log "=== Phase 1: Analyzing Project ==="
    
    # Find project root
    [ ! -f "build.gradle" ] && [ ! -f "build.gradle.kts" ] && [ ! -f "settings.gradle" ] && [ ! -f "settings.gradle.kts" ] && {
        error "Not an Android project directory"
        exit 1
    }
    
    # Find ALL gradle files
    GRADLE_FILES=$(find . -type f \( -name "*.gradle" -o -name "*.gradle.kts" \) -not -path "*/build/*" 2>/dev/null)
    ALL_GRADLE_CONTENT=$(cat $GRADLE_FILES 2>/dev/null)
    
    # Extract versions - multiple patterns for different gradle syntaxes
    COMPILE_SDK=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'compileSdk\s*[=:(]?\s*\K\d+' | head -1)
    [ -z "$COMPILE_SDK" ] && COMPILE_SDK=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'compileSdkVersion\s*[=:(]?\s*\K\d+' | head -1)
    
    TARGET_SDK=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'targetSdk\s*[=:(]?\s*\K\d+' | head -1)
    [ -z "$TARGET_SDK" ] && TARGET_SDK=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'targetSdkVersion\s*[=:(]?\s*\K\d+' | head -1)
    
    MIN_SDK=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'minSdk\s*[=:(]?\s*\K\d+' | head -1)
    [ -z "$MIN_SDK" ] && MIN_SDK=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'minSdkVersion\s*[=:(]?\s*\K\d+' | head -1)
    
    NDK_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'ndkVersion\s*[=:]?\s*["\x27]?\K[0-9.]+' | head -1)
    BUILD_TOOLS=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'buildToolsVersion\s*[=:]?\s*["\x27]?\K[0-9.]+' | head -1)
    
    AGP_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP "com\.android\.application.*version\s*['\"]?\K[0-9.]+" | head -1)
    [ -z "$AGP_VERSION" ] && AGP_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'com\.android\.tools\.build:gradle:\K[0-9.]+' | head -1)
    
    KOTLIN_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP "org\.jetbrains\.kotlin\.android.*version\s*['\"]?\K[0-9.]+" | head -1)
    [ -z "$KOTLIN_VERSION" ] && KOTLIN_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'org\.jetbrains\.kotlin[^:]*:\K[0-9.]+' | head -1)
    
    JAVA_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'jvmToolchain\s*\(\s*\K\d+' | head -1)
    [ -z "$JAVA_VERSION" ] && JAVA_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'JavaVersion\.VERSION_\K\d+' | head -1)
    [ -z "$JAVA_VERSION" ] && JAVA_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'jvmTarget\s*[=:]\s*["\x27]?\K[0-9]+' | head -1)
    
    CMAKE_VERSION=$(echo "$ALL_GRADLE_CONTENT" | grep -oP 'cmake\s*\{[^}]*version\s*["\x27]\K[^"\x27]+' | head -1)
    GRADLE_VERSION=$(grep -oP 'gradle-\K[0-9.]+' gradle/wrapper/gradle-wrapper.properties 2>/dev/null | head -1)
    
    # Native code detection
    HAS_CMAKE=$(find . -name "CMakeLists.txt" -not -path "*/build/*" 2>/dev/null | head -1)
    HAS_NDK_BUILD=$(find . -name "Android.mk" -not -path "*/build/*" 2>/dev/null | head -1)
    HAS_CPP=$(find . -type d -name "cpp" -path "*/src/*" -not -path "*/build/*" 2>/dev/null | head -1)
    HAS_JNI=$(find . -type d -name "jni" -not -path "*/build/*" 2>/dev/null | head -1)
    
    USES_NATIVE="no"
    [ -n "$HAS_CMAKE" ] || [ -n "$HAS_NDK_BUILD" ] || [ -n "$HAS_CPP" ] || [ -n "$HAS_JNI" ] && USES_NATIVE="yes"
    
    echo ""
    echo "====================================="
    echo "       PROJECT REQUIREMENTS"
    echo "====================================="
    echo ""
    echo "SDK/Build:"
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
    echo "  ndkVersion:        ${NDK_VERSION:-not specified}"
    echo "  CMake version:     ${CMAKE_VERSION:-not specified}"
    [ -n "$HAS_CMAKE" ] && echo "  CMakeLists.txt:    $HAS_CMAKE"
    [ -n "$HAS_NDK_BUILD" ] && echo "  Android.mk:        $HAS_NDK_BUILD"
    echo "====================================="
}

#=== PHASE 2: Check Installed ===#
check_installed() {
    echo ""
    log "=== Phase 2: Checking Installed Components ==="
    echo ""
    
    TO_INSTALL=()
    
    # Check Java
    echo "Java:"
    if command -v java &>/dev/null; then
        INSTALLED_JAVA=$(java -version 2>&1 | head -1)
        success "  Installed: $INSTALLED_JAVA"
    else
        warn "  Not installed"
        TO_INSTALL+=("java")
    fi
    
    # Check Android SDK
    echo ""
    echo "Android SDK:"
    if [ -d "$ANDROID_SDK" ]; then
        success "  Path: $ANDROID_SDK"
    else
        warn "  Not installed"
        TO_INSTALL+=("sdk")
    fi
    
    # Check Build Tools
    echo ""
    echo "Build Tools:"
    REQUIRED_BT="${BUILD_TOOLS:-${COMPILE_SDK:-34}.0.0}"
    if [ -d "$ANDROID_SDK/build-tools" ]; then
        INSTALLED_BT=$(ls -1 "$ANDROID_SDK/build-tools/" 2>/dev/null | tr '\n' ', ' | sed 's/,$//')
        [ -n "$INSTALLED_BT" ] && success "  Installed: $INSTALLED_BT" || warn "  None installed"
        
        # Check if required version exists and is ARM64
        local bt_match=$(ls -1 "$ANDROID_SDK/build-tools/" 2>/dev/null | grep "^${COMPILE_SDK:-34}" | head -1)
        if [ -n "$bt_match" ]; then
            local aapt2="$ANDROID_SDK/build-tools/$bt_match/aapt2"
            if [ -f "$aapt2" ] && file "$aapt2" | grep -qE "ARM|aarch64"; then
                success "  ARM64 aapt2: $bt_match ✓"
            else
                warn "  Need ARM64 build-tools for SDK ${COMPILE_SDK:-34}"
                TO_INSTALL+=("build-tools:${COMPILE_SDK:-34}")
            fi
        else
            warn "  Need build-tools for SDK ${COMPILE_SDK:-34}"
            TO_INSTALL+=("build-tools:${COMPILE_SDK:-34}")
        fi
    else
        warn "  Not installed"
        TO_INSTALL+=("build-tools:${COMPILE_SDK:-34}")
    fi
    
    # Check NDK
    echo ""
    echo "NDK:"
    if [ "$USES_NATIVE" = "yes" ] || [ -n "$NDK_VERSION" ]; then
        if [ -d "$ANDROID_SDK/ndk" ]; then
            INSTALLED_NDK=$(ls -1 "$ANDROID_SDK/ndk/" 2>/dev/null | tr '\n' ', ' | sed 's/,$//')
            [ -n "$INSTALLED_NDK" ] && success "  Installed: $INSTALLED_NDK" || warn "  None installed"
            
            if [ -n "$NDK_VERSION" ] && [ ! -d "$ANDROID_SDK/ndk/$NDK_VERSION" ]; then
                warn "  Need NDK $NDK_VERSION"
                TO_INSTALL+=("ndk:$NDK_VERSION")
            fi
        else
            warn "  Not installed"
            [ -n "$NDK_VERSION" ] && TO_INSTALL+=("ndk:$NDK_VERSION") || TO_INSTALL+=("ndk:27.1.12297006")
        fi
    else
        echo "  Not required (no native code)"
    fi
    
    # Check CMake
    echo ""
    echo "CMake:"
    if [ -n "$HAS_CMAKE" ]; then
        if command -v cmake &>/dev/null; then
            success "  Installed: $(cmake --version | head -1)"
        elif [ -d "$ANDROID_SDK/cmake" ] && ls "$ANDROID_SDK/cmake/"*/bin/cmake &>/dev/null 2>&1; then
            success "  Installed in SDK"
        else
            warn "  Not installed"
            TO_INSTALL+=("cmake")
        fi
    else
        echo "  Not required (no CMakeLists.txt)"
    fi
    
    # Check system tools for native builds
    echo ""
    echo "System Tools (for native builds):"
    for tool in clang lld llvm-strip ninja make; do
        if command -v $tool &>/dev/null; then
            success "  $tool: installed"
        else
            warn "  $tool: not installed"
            TO_INSTALL+=("sys:$tool")
        fi
    done
}

#=== PHASE 3: Install Missing ===#
install_missing() {
    [ ${#TO_INSTALL[@]} -eq 0 ] && { success "All components already installed!"; return; }
    
    echo ""
    echo "====================================="
    echo "       PACKAGES TO INSTALL"
    echo "====================================="
    for pkg in "${TO_INSTALL[@]}"; do
        echo "  - $pkg"
    done
    echo "====================================="
    
    ask "Install these packages?" || { log "Skipping installation"; return; }
    
    echo ""
    log "=== Phase 3: Installing ==="
    
    for pkg in "${TO_INSTALL[@]}"; do
        case "$pkg" in
            sys:*)
                tool="${pkg#sys:}"
                log "Installing $tool..."
                sudo apt install -y "$tool" 2>/dev/null || pkg install -y "$tool" 2>/dev/null || true
                ;;
            java)
                log "Installing Java..."
                sudo apt install -y openjdk-17-jdk 2>/dev/null || pkg install -y openjdk-17 2>/dev/null
                ;;
            sdk)
                log "Installing Android SDK base..."
                mkdir -p "$ANDROID_SDK"/{build-tools,ndk,platforms,cmake}
                ;;
            build-tools:*)
                sdk_ver="${pkg#build-tools:}"
                log "Installing ARM64 build-tools for SDK $sdk_ver..."
                
                # Find matching version
                local bt_ver=""
                for v in "${!SDK_URLS[@]}"; do
                    [[ "$v" == ${sdk_ver}.* ]] && { bt_ver="$v"; break; }
                done
                [ -z "$bt_ver" ] && bt_ver="34.0.4"
                
                local url="${SDK_URLS[$bt_ver]}"
                download "$url" "/tmp/bt.archive"
                mkdir -p "$ANDROID_SDK/build-tools/$bt_ver"
                
                if [[ "$url" == *.zip ]]; then
                    unzip -q /tmp/bt.archive -d /tmp/bt-extract
                    cp -r /tmp/bt-extract/*/* "$ANDROID_SDK/build-tools/$bt_ver/" 2>/dev/null || \
                    cp -r /tmp/bt-extract/* "$ANDROID_SDK/build-tools/$bt_ver/"
                    rm -rf /tmp/bt-extract
                else
                    tar -xf /tmp/bt.archive -C "$ANDROID_SDK"
                fi
                rm -f /tmp/bt.archive
                success "Build-tools $bt_ver installed"
                ;;
            ndk:*)
                ndk_ver="${pkg#ndk:}"
                log "Installing ARM64 NDK $ndk_ver..."
                
                local ndk_info="${NDK_URLS[$ndk_ver]}"
                [ -z "$ndk_info" ] && ndk_info="${NDK_URLS[27.1.12297006]}" && ndk_ver="27.1.12297006"
                
                local url="${ndk_info%%|*}"
                local rver="${ndk_info##*|}"
                
                download "$url" "/tmp/ndk.archive"
                mkdir -p "$ANDROID_SDK/ndk"
                cd /tmp
                
                if [[ "$url" == *.zip ]]; then
                    unzip -q ndk.archive
                else
                    tar -xf ndk.archive
                fi
                
                mv "android-ndk-$rver" "$ANDROID_SDK/ndk/$ndk_ver" 2>/dev/null || \
                mv android-ndk-* "$ANDROID_SDK/ndk/$ndk_ver"
                rm -f ndk.archive
                cd - >/dev/null
                success "NDK $ndk_ver installed"
                ;;
            cmake)
                log "Installing ARM64 CMake..."
                download "$CMAKE_URL" "/tmp/cmake.tar.gz"
                mkdir -p "$ANDROID_SDK/cmake/3.31.4"
                tar -xf /tmp/cmake.tar.gz -C "$ANDROID_SDK/cmake/3.31.4" --strip-components=1
                rm -f /tmp/cmake.tar.gz
                success "CMake installed"
                ;;
        esac
    done
}

#=== PHASE 4: Patch for ARM64 ===#
show_patches() {
    echo ""
    log "=== Phase 4: ARM64 Patches ==="
    echo ""
    echo "====================================="
    echo "       PATCHES TO APPLY"
    echo "====================================="
    
    PATCHES=()
    
    # Check NDK binaries - are they ARM64 or x86?
    for ndk_dir in "$ANDROID_SDK/ndk/"*/; do
        [ ! -d "$ndk_dir" ] && continue
        local ndk_name=$(basename "$ndk_dir")
        local prebuilt="$ndk_dir/toolchains/llvm/prebuilt"
        
        # Check if linux-aarch64 exists but no symlink
        if [ -d "$prebuilt/linux-aarch64" ] && [ ! -e "$prebuilt/linux-x86_64" ]; then
            PATCHES+=("NDK $ndk_name: symlink linux-x86_64 -> linux-aarch64")
        fi
        if [ -d "$prebuilt/linux-arm64" ] && [ ! -e "$prebuilt/linux-x86_64" ]; then
            PATCHES+=("NDK $ndk_name: symlink linux-x86_64 -> linux-arm64")
        fi
        
        # Check if binaries are x86_64 (need replacement with system ARM64 tools)
        local clang_bin="$prebuilt/linux-x86_64/bin/clang"
        if [ -f "$clang_bin" ] || [ -L "$clang_bin" ]; then
            local real_clang=$(readlink -f "$clang_bin" 2>/dev/null || echo "$clang_bin")
            if [ -f "$real_clang" ] && file "$real_clang" | grep -q "x86-64"; then
                PATCHES+=("NDK $ndk_name: replace x86_64 clang with system ARM64 clang")
                PATCHES+=("NDK $ndk_name: replace x86_64 lld with system ARM64 lld")
                PATCHES+=("NDK $ndk_name: replace x86_64 llvm-strip with system ARM64")
            fi
        fi
        
        # CMake toolchain
        for f in "$ndk_dir/build/cmake/"*.cmake; do
            [ -f "$f" ] && grep -q "set(ANDROID_HOST_TAG linux-x86_64)" "$f" && \
                PATCHES+=("NDK $ndk_name: patch $(basename $f) for ARM64 host")
        done
    done
    
    # Check aapt2 override
    local aapt2_arm64=""
    for bt in $(ls -1 "$ANDROID_SDK/build-tools/" 2>/dev/null | sort -rV); do
        local aapt2="$ANDROID_SDK/build-tools/$bt/aapt2"
        [ -f "$aapt2" ] && file "$aapt2" | grep -qE "ARM|aarch64" && { aapt2_arm64="$aapt2"; break; }
    done
    [ -n "$aapt2_arm64" ] && ! grep -q "android.aapt2FromMavenOverride" gradle.properties 2>/dev/null && \
        PATCHES+=("gradle.properties: android.aapt2FromMavenOverride=$aapt2_arm64")
    
    # Check local.properties
    [ ! -f "local.properties" ] || ! grep -q "sdk.dir" local.properties && \
        PATCHES+=("local.properties: sdk.dir=$ANDROID_SDK")
    
    # Check useAndroidX
    ! grep -q "android.useAndroidX=true" gradle.properties 2>/dev/null && \
        PATCHES+=("gradle.properties: android.useAndroidX=true")
    
    if [ ${#PATCHES[@]} -eq 0 ]; then
        success "No patches needed!"
        return 1
    fi
    
    for patch in "${PATCHES[@]}"; do
        echo "  - $patch"
    done
    echo "====================================="
    
    return 0
}

apply_patches() {
    ask "Apply these patches?" || { log "Skipping patches"; return; }
    
    echo ""
    log "Applying patches..."
    
    # NDK patches
    for ndk_dir in "$ANDROID_SDK/ndk/"*/; do
        [ ! -d "$ndk_dir" ] && continue
        local ndk_name=$(basename "$ndk_dir")
        local prebuilt="$ndk_dir/toolchains/llvm/prebuilt"
        local bin_dir="$prebuilt/linux-x86_64/bin"
        
        # Symlinks for ARM64 NDK
        [ -d "$prebuilt/linux-aarch64" ] && [ ! -e "$prebuilt/linux-x86_64" ] && \
            ln -sf linux-aarch64 "$prebuilt/linux-x86_64" && success "Created symlink in $ndk_name"
        [ -d "$prebuilt/linux-arm64" ] && [ ! -e "$prebuilt/linux-x86_64" ] && \
            ln -sf linux-arm64 "$prebuilt/linux-x86_64" && success "Created symlink in $ndk_name"
        
        [ -d "${ndk_dir}prebuilt/linux-aarch64" ] && [ ! -e "${ndk_dir}prebuilt/linux-x86_64" ] && \
            ln -sf linux-aarch64 "${ndk_dir}prebuilt/linux-x86_64"
        
        # Replace x86_64 binaries with system ARM64 tools
        if [ -d "$bin_dir" ]; then
            local clang_bin="$bin_dir/clang"
            local real_clang=$(readlink -f "$clang_bin" 2>/dev/null || echo "$clang_bin")
            
            if [ -f "$real_clang" ] && file "$real_clang" | grep -q "x86-64"; then
                # Find system clang
                local sys_clang=$(which clang 2>/dev/null)
                local sys_lld=$(which ld.lld 2>/dev/null)
                local sys_strip=$(which llvm-strip 2>/dev/null)
                
                if [ -n "$sys_clang" ]; then
                    # Backup and replace clang
                    [ ! -f "$bin_dir/clang.x86_64.bak" ] && mv "$bin_dir/clang" "$bin_dir/clang.x86_64.bak" 2>/dev/null
                    [ ! -f "$bin_dir/clang++.x86_64.bak" ] && mv "$bin_dir/clang++" "$bin_dir/clang++.x86_64.bak" 2>/dev/null
                    ln -sf "$sys_clang" "$bin_dir/clang"
                    ln -sf "$sys_clang" "$bin_dir/clang++"
                    success "Replaced clang in $ndk_name"
                fi
                
                if [ -n "$sys_lld" ]; then
                    [ ! -f "$bin_dir/ld.lld.x86_64.bak" ] && mv "$bin_dir/ld.lld" "$bin_dir/ld.lld.x86_64.bak" 2>/dev/null
                    [ ! -f "$bin_dir/lld.x86_64.bak" ] && mv "$bin_dir/lld" "$bin_dir/lld.x86_64.bak" 2>/dev/null
                    ln -sf "$sys_lld" "$bin_dir/ld.lld"
                    ln -sf "$sys_lld" "$bin_dir/lld"
                    success "Replaced lld in $ndk_name"
                fi
                
                if [ -n "$sys_strip" ]; then
                    [ ! -f "$bin_dir/llvm-strip.x86_64.bak" ] && mv "$bin_dir/llvm-strip" "$bin_dir/llvm-strip.x86_64.bak" 2>/dev/null
                    ln -sf "$sys_strip" "$bin_dir/llvm-strip"
                    success "Replaced llvm-strip in $ndk_name"
                fi
            fi
        fi
        
        # CMake toolchain
        for f in "$ndk_dir/build/cmake/"*.cmake; do
            [ -f "$f" ] && grep -q "set(ANDROID_HOST_TAG linux-x86_64)" "$f" && {
                sed -i 's/set(ANDROID_HOST_TAG linux-x86_64)/set(ANDROID_HOST_TAG linux-aarch64)/' "$f"
                success "Patched $(basename $f) in $ndk_name"
            }
        done
    done
    
    # gradle.properties
    touch gradle.properties
    local aapt2_arm64=""
    for bt in $(ls -1 "$ANDROID_SDK/build-tools/" 2>/dev/null | sort -rV); do
        local aapt2="$ANDROID_SDK/build-tools/$bt/aapt2"
        [ -f "$aapt2" ] && file "$aapt2" | grep -qE "ARM|aarch64" && { aapt2_arm64="$aapt2"; break; }
    done
    [ -n "$aapt2_arm64" ] && ! grep -q "android.aapt2FromMavenOverride" gradle.properties && {
        echo "android.aapt2FromMavenOverride=$aapt2_arm64" >> gradle.properties
        success "Added aapt2 override"
    }
    
    grep -q "android.useAndroidX=true" gradle.properties || {
        echo "android.useAndroidX=true" >> gradle.properties
        success "Added useAndroidX"
    }
    
    # local.properties
    echo "sdk.dir=$ANDROID_SDK" > local.properties
    [ -n "$NDK_VERSION" ] && [ -d "$ANDROID_SDK/ndk/$NDK_VERSION" ] && \
        echo "ndk.dir=$ANDROID_SDK/ndk/$NDK_VERSION" >> local.properties
    success "Updated local.properties"
    
    echo ""
    success "=== All patches applied ==="
}

#=== Main ===#
analyze_project
check_installed
install_missing
show_patches && apply_patches

echo ""
echo "====================================="
echo "Setup complete! Run: ./gradlew assembleDebug"
echo "====================================="
