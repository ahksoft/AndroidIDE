package com.itsaky.androidide.ai;

/**
 * AI Code Assistant using Gemini AI CLI
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J8\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\n2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\b0\u000eJ,\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\n2\b\b\u0002\u0010\u0012\u001a\u00020\n2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\b0\u000eJI\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\n2\b\b\u0002\u0010\u0015\u001a\u00020\u00162\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\n2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\b0\u000e\u00a2\u0006\u0002\u0010\u0019J8\u0010\u001a\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\n2\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u001c2\b\b\u0002\u0010\u0012\u001a\u00020\n2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\b0\u000eJ,\u0010\u001d\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\n2\b\b\u0002\u0010\u0012\u001a\u00020\n2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\b0\u000eJ\u000e\u0010\u001e\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/itsaky/androidide/ai/AiCodeAssistant;", "", "geminiCli", "Lcom/itsaky/androidide/ai/GeminiAiCli;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "(Lcom/itsaky/androidide/ai/GeminiAiCli;Lkotlinx/coroutines/CoroutineScope;)V", "assistTerminal", "", "command", "", "workingDir", "error", "onResult", "Lkotlin/Function1;", "Lcom/itsaky/androidide/ai/AiResponse;", "explainCode", "code", "language", "fixBuildError", "errorMessage", "project", "error/NonExistentClass", "buildFile", "sourceCode", "(Ljava/lang/String;Lerror/NonExistentClass;Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)V", "generateCode", "prompt", "Lcom/itsaky/androidide/projects/GradleProject;", "optimizeCode", "setApiKey", "key", "ai-cli_debug"})
public final class AiCodeAssistant {
    @org.jetbrains.annotations.NotNull()
    private final com.itsaky.androidide.ai.GeminiAiCli geminiCli = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    
    public AiCodeAssistant(@org.jetbrains.annotations.NotNull()
    com.itsaky.androidide.ai.GeminiAiCli geminiCli, @org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineScope scope) {
        super();
    }
    
    public final void setApiKey(@org.jetbrains.annotations.NotNull()
    java.lang.String key) {
    }
    
    public final void generateCode(@org.jetbrains.annotations.NotNull()
    java.lang.String prompt, @org.jetbrains.annotations.Nullable()
    com.itsaky.androidide.projects.GradleProject project, @org.jetbrains.annotations.NotNull()
    java.lang.String language, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.itsaky.androidide.ai.AiResponse, kotlin.Unit> onResult) {
    }
    
    public final void fixBuildError(@org.jetbrains.annotations.NotNull()
    java.lang.String errorMessage, @org.jetbrains.annotations.Nullable()
    error.NonExistentClass project, @org.jetbrains.annotations.Nullable()
    java.lang.String buildFile, @org.jetbrains.annotations.Nullable()
    java.lang.String sourceCode, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.itsaky.androidide.ai.AiResponse, kotlin.Unit> onResult) {
    }
    
    public final void explainCode(@org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    java.lang.String language, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.itsaky.androidide.ai.AiResponse, kotlin.Unit> onResult) {
    }
    
    public final void optimizeCode(@org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    java.lang.String language, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.itsaky.androidide.ai.AiResponse, kotlin.Unit> onResult) {
    }
    
    public final void assistTerminal(@org.jetbrains.annotations.NotNull()
    java.lang.String command, @org.jetbrains.annotations.NotNull()
    java.lang.String workingDir, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.itsaky.androidide.ai.AiResponse, kotlin.Unit> onResult) {
    }
    
    public AiCodeAssistant() {
        super();
    }
}