# AndroidIDE AI CLI Integration

This branch contains experimental AI CLI integration for AndroidIDE.

## Features Added
- AI CLI module with Gemini integration
- Terminal AI assistance
- Code generation and explanation
- Build error fixing suggestions
- Sidebar UI integration

## Build Status
⚠️ **Currently failing** - Integration needs fixes

## Build with GitHub Actions
The project includes automated builds via GitHub Actions that:
- Build APKs for all architectures
- Upload artifacts on success
- Provide build reports on failure

## Local Build
```bash
./gradlew assembleDebug
```

## AI CLI Components
- `core/ai-cli/` - Main AI CLI module
- `GeminiAiCli.kt` - Gemini API integration
- `AiCodeAssistant.kt` - High-level AI wrapper
- `AiCliSidebarFragment.kt` - UI integration
