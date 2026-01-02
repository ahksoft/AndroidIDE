#!/bin/bash
# Android ARM64 Build Fixer - Configures project for Linux ARM64 builds

set -e

# Find Android SDK
ANDROID_SDK="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-$HOME/android-sdk}}"
[ ! -d "$ANDROID_SDK" ] && { echo "Error: Android SDK not found"; exit 1; }

# Find project root
[ ! -f "./build.gradle" ] && [ ! -f "./build.gradle.kts" ] && { echo "Error: Not an Android project"; exit 1; }

echo "=== Android ARM64 Build Fixer ==="
echo "SDK Path: $ANDROID_SDK"
echo ""

# Find app/build.gradle
APP_GRADLE=$(find . -path "*/app/build.gradle*" | head -1)
[ -z "$APP_GRADLE" ] && { echo "Error: app/build.gradle not found"; exit 1; }

# Extract required versions
COMPILE_SDK=$(grep -oP 'compileSdk\s*[=:]?\s*\K\d+' "$APP_GRADLE" 2>/dev/null | head -1)
TARGET_SDK=$(grep -oP 'targetSdk\s*[=:]?\s*\K\d+' "$APP_GRADLE" 2>/dev/null | head -1)
NDK_VERSION=$(grep -oP 'ndkVersion\s*[=:]?\s*["\x27]\K[^"\x27]+' "$APP_GRADLE" 2>/dev/null | head -1)
CURRENT_BUILD_TOOLS=$(grep -oP 'buildToolsVersion\s*[=:]?\s*["\x27]\K[^"\x27]+' "$APP_GRADLE" 2>/dev/null | head -1)

# Get AGP version
ROOT_GRADLE="./build.gradle"
AGP_VERSION=$(grep -oP "com.android.tools.build:gradle:\K[0-9.]+" "$ROOT_GRADLE" 2>/dev/null | head -1)
[ -z "$AGP_VERSION" ] && AGP_VERSION=$(grep -oP "id\s*['\"]com.android.application['\"]\s*version\s*['\"]?\K[0-9.]+" "$ROOT_GRADLE" 2>/dev/null | head -1)

echo "=== Project Requirements ==="
echo "compileSdk: ${COMPILE_SDK:-not found}"
echo "targetSdk: ${TARGET_SDK:-not found}"
echo "AGP version: ${AGP_VERSION:-not found}"
echo "Current buildToolsVersion: ${CURRENT_BUILD_TOOLS:-not set}"
echo "Required NDK: ${NDK_VERSION:-not specified}"
echo ""

# Determine required SDK version (use compileSdk)
REQUIRED_SDK="${COMPILE_SDK:-34}"

# Find matching build-tools for required SDK
echo "=== Checking Build Tools ==="
AVAILABLE_BUILD_TOOLS=$(ls -1 "$ANDROID_SDK/build-tools/" 2>/dev/null | sort -V)

if [ -z "$AVAILABLE_BUILD_TOOLS" ]; then
    echo "Error: No build-tools installed"
    exit 1
fi

echo "Available build-tools:"
echo "$AVAILABLE_BUILD_TOOLS" | while read v; do echo "  - $v"; done

# Find build-tools matching required SDK version
MATCHING_BUILD_TOOLS=$(echo "$AVAILABLE_BUILD_TOOLS" | grep "^${REQUIRED_SDK}\." | tail -1)

if [ -z "$MATCHING_BUILD_TOOLS" ]; then
    echo ""
    echo "Warning: No build-tools found for SDK $REQUIRED_SDK"
    echo "Install with: sdkmanager \"build-tools;${REQUIRED_SDK}.0.0\""
fi

# Check all aapt2 binaries and their architectures
echo ""
echo "=== Available AAPT2 Binaries ==="
AAPT2_OPTIONS=()
i=1

for bt in $AVAILABLE_BUILD_TOOLS; do
    AAPT2_FILE="$ANDROID_SDK/build-tools/$bt/aapt2"
    if [ -f "$AAPT2_FILE" ]; then
        ARCH=$(file "$AAPT2_FILE" | grep -oE "ARM|aarch64|x86-64|x86_64" | head -1)
        case "$ARCH" in
            ARM|aarch64) ARCH_LABEL="ARM64 ✓" ;;
            x86-64|x86_64) ARCH_LABEL="x86_64 ✗" ;;
            *) ARCH_LABEL="unknown" ;;
        esac
        
        # Mark recommended (matches required SDK and is ARM64)
        RECOMMENDED=""
        if [[ "$bt" == ${REQUIRED_SDK}.* ]] && [[ "$ARCH" == "ARM" || "$ARCH" == "aarch64" ]]; then
            RECOMMENDED=" [RECOMMENDED]"
        fi
        
        echo "  $i) $bt ($ARCH_LABEL)$RECOMMENDED"
        AAPT2_OPTIONS+=("$bt")
        ((i++))
    fi
done

if [ ${#AAPT2_OPTIONS[@]} -eq 0 ]; then
    echo "Error: No aapt2 binaries found"
    exit 1
fi

echo ""
read -p "Select build-tools version [1-$((i-1))]: " selection

if [[ "$selection" =~ ^[0-9]+$ ]] && [ "$selection" -ge 1 ] && [ "$selection" -lt "$i" ]; then
    MATCHING_BUILD_TOOLS="${AAPT2_OPTIONS[$((selection-1))]}"
else
    echo "Invalid selection, auto-selecting first ARM64 compatible..."
    MATCHING_BUILD_TOOLS=""
    for bt in $AVAILABLE_BUILD_TOOLS; do
        AAPT2_FILE="$ANDROID_SDK/build-tools/$bt/aapt2"
        if [ -f "$AAPT2_FILE" ]; then
            ARCH=$(file "$AAPT2_FILE" | grep -oE "ARM|aarch64" | head -1)
            if [ -n "$ARCH" ]; then
                MATCHING_BUILD_TOOLS="$bt"
                break
            fi
        fi
    done
    [ -z "$MATCHING_BUILD_TOOLS" ] && MATCHING_BUILD_TOOLS="${AAPT2_OPTIONS[-1]}"
fi

AAPT2_PATH="$ANDROID_SDK/build-tools/$MATCHING_BUILD_TOOLS/aapt2"
echo "Selected: $MATCHING_BUILD_TOOLS ($AAPT2_PATH)"

# Show NDK status
echo ""
echo "=== NDK Status ==="
AVAILABLE_NDK=$(ls -1 "$ANDROID_SDK/ndk/" 2>/dev/null | sort -V)

if [ -n "$NDK_VERSION" ]; then
    if [ -d "$ANDROID_SDK/ndk/$NDK_VERSION" ]; then
        echo "Required NDK $NDK_VERSION: installed"
    else
        echo "Required NDK $NDK_VERSION: NOT installed"
        if [ -n "$AVAILABLE_NDK" ]; then
            echo "Available NDK versions:"
            echo "$AVAILABLE_NDK" | while read v; do echo "  - $v"; done
        fi
        echo "Install with: sdkmanager \"ndk;$NDK_VERSION\""
    fi
else
    # Try to find NDK version from other sources
    NDK_VERSION=$(grep -roP 'ndkVersion\s*[=:]?\s*["\x27]\K[^"\x27]+' . --include="*.gradle*" 2>/dev/null | head -1)
    
    if [ -z "$NDK_VERSION" ]; then
        # Check local.properties
        NDK_VERSION=$(grep -oP 'ndk.dir=.*/ndk/\K[0-9.]+' local.properties 2>/dev/null | head -1)
    fi
    
    if [ -z "$NDK_VERSION" ]; then
        # Check for NDK_HOME environment
        [ -n "$NDK_HOME" ] && NDK_VERSION=$(basename "$NDK_HOME" 2>/dev/null)
    fi
    
    if [ -n "$NDK_VERSION" ]; then
        echo "Detected NDK version: $NDK_VERSION"
        if [ -d "$ANDROID_SDK/ndk/$NDK_VERSION" ]; then
            echo "Status: installed"
        else
            echo "Status: NOT installed"
            echo "Install with: sdkmanager \"ndk;$NDK_VERSION\""
        fi
    else
        echo "NDK: not required by project"
        if [ -n "$AVAILABLE_NDK" ]; then
            echo "Available NDK versions:"
            echo "$AVAILABLE_NDK" | while read v; do echo "  - $v"; done
        fi
    fi
fi

echo ""
echo "=== Proposed Changes ==="
echo "buildToolsVersion: $MATCHING_BUILD_TOOLS"
echo "aapt2 path: $AAPT2_PATH"
echo "android.useAndroidX=true"
echo ""

read -p "Apply these changes? [y/N] " confirm
[ "$confirm" != "y" ] && [ "$confirm" != "Y" ] && { echo "Aborted"; exit 0; }

# Update gradle.properties
GRADLE_PROPS="./gradle.properties"
touch "$GRADLE_PROPS"

update_prop() {
    local key=$1 val=$2
    if grep -q "^$key=" "$GRADLE_PROPS"; then
        sed -i "s|^$key=.*|$key=$val|" "$GRADLE_PROPS"
    else
        echo "$key=$val" >> "$GRADLE_PROPS"
    fi
}

update_prop "android.useAndroidX" "true"
update_prop "android.aapt2FromMavenOverride" "$AAPT2_PATH"
echo "Updated gradle.properties"

# Update app/build.gradle - add/update buildToolsVersion
if grep -q "buildToolsVersion" "$APP_GRADLE"; then
    if [[ "$APP_GRADLE" == *.kts ]]; then
        sed -i "s|buildToolsVersion\s*=\s*[\"'][^\"']*[\"']|buildToolsVersion = \"$MATCHING_BUILD_TOOLS\"|" "$APP_GRADLE"
    else
        sed -i "s|buildToolsVersion\s*[\"'][^\"']*[\"']|buildToolsVersion \"$MATCHING_BUILD_TOOLS\"|" "$APP_GRADLE"
    fi
    echo "Updated buildToolsVersion in $APP_GRADLE"
else
    if [[ "$APP_GRADLE" == *.kts ]]; then
        sed -i "/compileSdk/a\\    buildToolsVersion = \"$MATCHING_BUILD_TOOLS\"" "$APP_GRADLE"
    else
        sed -i "/compileSdk/a\\    buildToolsVersion \"$MATCHING_BUILD_TOOLS\"" "$APP_GRADLE"
    fi
    echo "Added buildToolsVersion to $APP_GRADLE"
fi

echo ""
echo "=== Configuration Complete ==="
echo "Run './gradlew build' to build the project"
