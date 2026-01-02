LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := c++_shared
ifeq ($(TARGET_ARCH_ABI),arm64-v8a)
    LOCAL_SRC_FILES := $(NDK_ROOT)/toolchains/llvm/prebuilt/linux-aarch64/sysroot/usr/lib/aarch64-linux-android/libc++_shared.so
else ifeq ($(TARGET_ARCH_ABI),armeabi-v7a)
    LOCAL_SRC_FILES := $(NDK_ROOT)/toolchains/llvm/prebuilt/linux-aarch64/sysroot/usr/lib/arm-linux-androideabi/libc++_shared.so
else ifeq ($(TARGET_ARCH_ABI),x86_64)
    LOCAL_SRC_FILES := $(NDK_ROOT)/toolchains/llvm/prebuilt/linux-aarch64/sysroot/usr/lib/x86_64-linux-android/libc++_shared.so
endif
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_LDLIBS := -llog
LOCAL_MODULE := local-socket
LOCAL_SRC_FILES := local-socket.cpp
LOCAL_CPP_FEATURES := exceptions rtti
LOCAL_SHARED_LIBRARIES := c++_shared
ifeq ($(TARGET_ARCH_ABI),armeabi-v7a)
    LOCAL_LDLIBS += -latomic
endif
include $(BUILD_SHARED_LIBRARY)
