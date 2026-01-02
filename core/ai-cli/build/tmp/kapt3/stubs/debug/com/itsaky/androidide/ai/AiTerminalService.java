package com.itsaky.androidide.ai;

/**
 * Background service for AI terminal integration
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u0016B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J*\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u000e0\u0013J\u000e\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0010R\u0012\u0010\u0003\u001a\u00060\u0004R\u00020\u0000X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/itsaky/androidide/ai/AiTerminalService;", "Landroid/app/Service;", "()V", "binder", "Lcom/itsaky/androidide/ai/AiTerminalService$AiTerminalBinder;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "terminalAi", "Lcom/itsaky/androidide/ai/TerminalAiIntegration;", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "processAiCommand", "", "command", "", "workingDir", "callback", "Lkotlin/Function1;", "setApiKey", "key", "AiTerminalBinder", "ai-cli_debug"})
public final class AiTerminalService extends android.app.Service {
    @org.jetbrains.annotations.NotNull()
    private final com.itsaky.androidide.ai.AiTerminalService.AiTerminalBinder binder = null;
    @org.jetbrains.annotations.NotNull()
    private final com.itsaky.androidide.ai.TerminalAiIntegration terminalAi = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    
    public AiTerminalService() {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
        return null;
    }
    
    public final void processAiCommand(@org.jetbrains.annotations.NotNull()
    java.lang.String command, @org.jetbrains.annotations.NotNull()
    java.lang.String workingDir, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> callback) {
    }
    
    public final void setApiKey(@org.jetbrains.annotations.NotNull()
    java.lang.String key) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/itsaky/androidide/ai/AiTerminalService$AiTerminalBinder;", "Landroid/os/Binder;", "(Lcom/itsaky/androidide/ai/AiTerminalService;)V", "getService", "Lcom/itsaky/androidide/ai/AiTerminalService;", "ai-cli_debug"})
    public final class AiTerminalBinder extends android.os.Binder {
        
        public AiTerminalBinder() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.itsaky.androidide.ai.AiTerminalService getService() {
            return null;
        }
    }
}