# Free AI Integration Setup for AndroidIDE

This guide shows how to integrate free AI providers into AndroidIDE without running OpenCode binary on Termux.

## Why This Approach?

OpenCode binary doesn't work on Termux/Android due to:
- Android 10+ executable restrictions
- Go runtime incompatibilities with Android
- Missing system libraries in Termux environment

## Solution: Native Integration

Instead of running OpenCode binary, we integrate AI capabilities directly into AndroidIDE using free AI providers.

## Free AI Providers Supported

### 1. OpenRouter (Recommended)
- **Free models**: `nousresearch/hermes-3-llama-3.1-405b:free`
- **Setup**: Get free API key from [openrouter.ai](https://openrouter.ai)
- **Rate limits**: Generous free tier

### 2. Groq
- **Free models**: `llama-3.1-8b-instant`
- **Setup**: Get free API key from [console.groq.com](https://console.groq.com)
- **Rate limits**: Very fast inference, good free tier

### 3. Hugging Face
- **Free models**: Various open models
- **Setup**: Get free API key from [huggingface.co](https://huggingface.co)
- **Rate limits**: Free inference API

## Setup Instructions

### 1. Get API Keys

**OpenRouter (Recommended):**
```bash
# Visit https://openrouter.ai
# Sign up and get free API key
export OPENROUTER_API_KEY="your-key-here"
```

**Groq:**
```bash
# Visit https://console.groq.com
# Sign up and get free API key
export GROQ_API_KEY="your-key-here"
```

**Hugging Face:**
```bash
# Visit https://huggingface.co/settings/tokens
# Create token with inference permissions
export HUGGINGFACE_API_KEY="your-key-here"
```

### 2. Build AndroidIDE

The AI integration is already included in the codebase:

```bash
cd /root/AndroidIDE
./gradlew assembleDebug
```

### 3. Use AI Features

In AndroidIDE editor menu, you'll find:
- **Fix Build Error with Free AI** - Automatically fixes build errors
- **Generate Code with Free AI** - Generates code from descriptions
- **Optimize Project with Free AI** - Suggests project improvements

## Features

### Build Error Fixing
- Analyzes Android build errors
- Suggests specific fixes for Gradle, NDK, dependency issues
- Works with the complex build problems you've encountered

### Code Generation
- Generates Kotlin/Java code from natural language
- Follows Android best practices
- Creates Activities, Fragments, Adapters, etc.

### Project Optimization
- Reviews build.gradle files
- Suggests dependency optimizations
- Recommends architecture improvements

## Advantages Over OpenCode Binary

1. **Native Android compatibility** - No Termux/Android restrictions
2. **Free AI providers** - No paid API keys required
3. **Integrated with AndroidIDE** - Direct editor integration
4. **Lightweight** - No external binary dependencies
5. **Reliable** - Works consistently on Android devices

## Cost Comparison

| Provider | Free Tier | Rate Limits |
|----------|-----------|-------------|
| OpenRouter | Yes | 200 requests/day |
| Groq | Yes | 30 requests/minute |
| Hugging Face | Yes | 1000 requests/month |

## Usage Examples

### Fix Build Error
```kotlin
// Automatically detects and fixes errors like:
// - "Unresolved reference"
// - "Duplicate class found"
// - NDK compilation errors
// - Gradle configuration issues
```

### Generate Code
```kotlin
// Input: "Create a RecyclerView adapter for user list"
// Output: Complete adapter with ViewHolder, data binding, etc.
```

### Optimize Project
```kotlin
// Analyzes build.gradle.kts and suggests:
// - Unused dependency removal
// - Build performance improvements
// - Architecture recommendations
```

This approach gives you OpenCode-like capabilities without the compatibility issues, using free AI providers that work reliably on Android/Termux.
