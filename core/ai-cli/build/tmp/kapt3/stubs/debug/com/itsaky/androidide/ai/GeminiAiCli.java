package com.itsaky.androidide.ai;

/**
 * Gemini AI CLI integration based on PhoneAiCli implementation
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u0000 !2\u00020\u0001:\u0001!B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\bH\u0082@\u00a2\u0006\u0002\u0010\rJ \u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\b2\b\b\u0002\u0010\u0010\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\rJ.\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\b2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\bH\u0086@\u00a2\u0006\u0002\u0010\u0015J*\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\b2\b\b\u0002\u0010\u0018\u001a\u00020\b2\b\b\u0002\u0010\u0010\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0015J \u0010\u0019\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\b2\b\b\u0002\u0010\u0010\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\bJ,\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\b2\b\b\u0002\u0010\u001f\u001a\u00020\b2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\bH\u0086@\u00a2\u0006\u0002\u0010\u0015R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/itsaky/androidide/ai/GeminiAiCli;", "", "httpClient", "Lokhttp3/OkHttpClient;", "gson", "Lcom/google/gson/Gson;", "(Lokhttp3/OkHttpClient;Lcom/google/gson/Gson;)V", "apiKey", "", "callGeminiApi", "Lcom/itsaky/androidide/ai/AiResponse;", "systemPrompt", "userPrompt", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "explainCode", "code", "language", "fixBuildError", "errorMessage", "buildFile", "sourceCode", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateCode", "prompt", "context", "optimizeCode", "setApiKey", "", "key", "terminalAssist", "command", "workingDir", "error", "Companion", "ai-cli_debug"})
public final class GeminiAiCli {
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.OkHttpClient httpClient = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String GEMINI_API_BASE = "https://generativelanguage.googleapis.com/v1beta";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String DEFAULT_MODEL = "gemini-1.5-flash";
    @org.jetbrains.annotations.Nullable()
    private java.lang.String apiKey;
    @org.jetbrains.annotations.NotNull()
    public static final com.itsaky.androidide.ai.GeminiAiCli.Companion Companion = null;
    
    public GeminiAiCli(@org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient httpClient, @org.jetbrains.annotations.NotNull()
    com.google.gson.Gson gson) {
        super();
    }
    
    public final void setApiKey(@org.jetbrains.annotations.NotNull()
    java.lang.String key) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object generateCode(@org.jetbrains.annotations.NotNull()
    java.lang.String prompt, @org.jetbrains.annotations.NotNull()
    java.lang.String context, @org.jetbrains.annotations.NotNull()
    java.lang.String language, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.itsaky.androidide.ai.AiResponse> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object fixBuildError(@org.jetbrains.annotations.NotNull()
    java.lang.String errorMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String buildFile, @org.jetbrains.annotations.Nullable()
    java.lang.String sourceCode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.itsaky.androidide.ai.AiResponse> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object explainCode(@org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    java.lang.String language, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.itsaky.androidide.ai.AiResponse> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object optimizeCode(@org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    java.lang.String language, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.itsaky.androidide.ai.AiResponse> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object terminalAssist(@org.jetbrains.annotations.NotNull()
    java.lang.String command, @org.jetbrains.annotations.NotNull()
    java.lang.String workingDir, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.itsaky.androidide.ai.AiResponse> $completion) {
        return null;
    }
    
    private final java.lang.Object callGeminiApi(java.lang.String systemPrompt, java.lang.String userPrompt, kotlin.coroutines.Continuation<? super com.itsaky.androidide.ai.AiResponse> $completion) {
        return null;
    }
    
    public GeminiAiCli() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/itsaky/androidide/ai/GeminiAiCli$Companion;", "", "()V", "DEFAULT_MODEL", "", "GEMINI_API_BASE", "ai-cli_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}