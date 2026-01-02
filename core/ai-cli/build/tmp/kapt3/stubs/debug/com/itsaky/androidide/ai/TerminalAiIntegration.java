package com.itsaky.androidide.ai;

/**
 * Terminal AI integration for AndroidIDE/Termux
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J\"\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00062\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\fJ,\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\fH\u0002J,\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\fH\u0002J,\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\fH\u0002J*\u0010\u0013\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\fJ\u000e\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/itsaky/androidide/ai/TerminalAiIntegration;", "", "aiAssistant", "Lcom/itsaky/androidide/ai/AiCodeAssistant;", "(Lcom/itsaky/androidide/ai/AiCodeAssistant;)V", "detectLanguage", "", "workingDir", "getCommandHelp", "", "command", "onResult", "Lkotlin/Function1;", "handleAiCommand", "prompt", "handleExplainCommand", "code", "handleFixCommand", "error", "processCommand", "setApiKey", "key", "ai-cli_debug"})
public final class TerminalAiIntegration {
    @org.jetbrains.annotations.NotNull()
    private final com.itsaky.androidide.ai.AiCodeAssistant aiAssistant = null;
    
    public TerminalAiIntegration(@org.jetbrains.annotations.NotNull()
    com.itsaky.androidide.ai.AiCodeAssistant aiAssistant) {
        super();
    }
    
    public final void setApiKey(@org.jetbrains.annotations.NotNull()
    java.lang.String key) {
    }
    
    /**
     * Process terminal command with AI assistance
     */
    public final void processCommand(@org.jetbrains.annotations.NotNull()
    java.lang.String command, @org.jetbrains.annotations.NotNull()
    java.lang.String workingDir, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onResult) {
    }
    
    private final void handleAiCommand(java.lang.String prompt, java.lang.String workingDir, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onResult) {
    }
    
    private final void handleExplainCommand(java.lang.String code, java.lang.String workingDir, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onResult) {
    }
    
    private final void handleFixCommand(java.lang.String error, java.lang.String workingDir, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onResult) {
    }
    
    private final java.lang.String detectLanguage(java.lang.String workingDir) {
        return null;
    }
    
    /**
     * Get AI help for terminal commands
     */
    public final void getCommandHelp(@org.jetbrains.annotations.NotNull()
    java.lang.String command, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onResult) {
    }
    
    public TerminalAiIntegration() {
        super();
    }
}