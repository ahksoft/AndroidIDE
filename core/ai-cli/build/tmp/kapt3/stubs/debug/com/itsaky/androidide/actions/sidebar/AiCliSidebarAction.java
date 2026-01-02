package com.itsaky.androidide.actions.sidebar;

/**
 * AI CLI sidebar action for AndroidIDE
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 *2\u00020\u0001:\u0001*B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\'H\u0016J\f\u0010(\u001a\u00020%*\u00020)H\u0016R\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001e\u0010\r\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u000f\u0018\u00010\u000eX\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u0019X\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u0019X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001b\"\u0004\b\u001e\u0010\u001fR\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#\u00a8\u0006+"}, d2 = {"Lcom/itsaky/androidide/actions/sidebar/AiCliSidebarAction;", "Lcom/itsaky/androidide/actions/SidebarActionItem;", "context", "Landroid/content/Context;", "order", "", "(Landroid/content/Context;I)V", "enabled", "", "getEnabled", "()Z", "setEnabled", "(Z)V", "fragmentClass", "Lkotlin/reflect/KClass;", "Landroidx/fragment/app/Fragment;", "getFragmentClass", "()Lkotlin/reflect/KClass;", "icon", "Landroid/graphics/drawable/Drawable;", "getIcon", "()Landroid/graphics/drawable/Drawable;", "setIcon", "(Landroid/graphics/drawable/Drawable;)V", "id", "", "getId", "()Ljava/lang/String;", "label", "getLabel", "setLabel", "(Ljava/lang/String;)V", "getOrder", "()I", "setOrder", "(I)V", "prepare", "", "data", "Lcom/itsaky/androidide/actions/ActionData;", "buildNavigation", "Landroidx/navigation/fragment/FragmentNavigatorDestinationBuilder;", "Companion", "ai-cli_debug"})
public final class AiCliSidebarAction implements com.itsaky.androidide.actions.SidebarActionItem {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ID = "ide.editor.sidebar.aiCli";
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = "ide.editor.sidebar.aiCli";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String label = "AI CLI";
    @org.jetbrains.annotations.Nullable()
    private android.graphics.drawable.Drawable icon;
    private int order;
    @org.jetbrains.annotations.Nullable()
    private final kotlin.reflect.KClass<? extends androidx.fragment.app.Fragment> fragmentClass = null;
    private boolean enabled = true;
    @org.jetbrains.annotations.NotNull()
    public static final com.itsaky.androidide.actions.sidebar.AiCliSidebarAction.Companion Companion = null;
    
    public AiCliSidebarAction(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int order) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String getId() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String getLabel() {
        return null;
    }
    
    @java.lang.Override()
    public void setLabel(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.graphics.drawable.Drawable getIcon() {
        return null;
    }
    
    @java.lang.Override()
    public void setIcon(@org.jetbrains.annotations.Nullable()
    android.graphics.drawable.Drawable p0) {
    }
    
    @java.lang.Override()
    public int getOrder() {
        return 0;
    }
    
    public void setOrder(int p0) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public kotlin.reflect.KClass<? extends androidx.fragment.app.Fragment> getFragmentClass() {
        return null;
    }
    
    @java.lang.Override()
    public boolean getEnabled() {
        return false;
    }
    
    @java.lang.Override()
    public void setEnabled(boolean p0) {
    }
    
    @java.lang.Override()
    public void prepare(@org.jetbrains.annotations.NotNull()
    com.itsaky.androidide.actions.ActionData data) {
    }
    
    @java.lang.Override()
    public void buildNavigation(@org.jetbrains.annotations.NotNull()
    androidx.navigation.fragment.FragmentNavigatorDestinationBuilder $this$buildNavigation) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.view.View createActionView(@org.jetbrains.annotations.NotNull()
    com.itsaky.androidide.actions.ActionData data) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.graphics.ColorFilter createColorFilter(@org.jetbrains.annotations.NotNull()
    com.itsaky.androidide.actions.ActionData data) {
        return null;
    }
    
    @java.lang.Override()
    public void destroy() {
    }
    
    @java.lang.Override()
    public int getItemId() {
        return 0;
    }
    
    @java.lang.Override()
    public int getShowAsActionFlags(@org.jetbrains.annotations.NotNull()
    com.itsaky.androidide.actions.ActionData data) {
        return 0;
    }
    
    @java.lang.Override()
    public void postExec(@org.jetbrains.annotations.NotNull()
    com.itsaky.androidide.actions.ActionData data, @org.jetbrains.annotations.NotNull()
    java.lang.Object result) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/itsaky/androidide/actions/sidebar/AiCliSidebarAction$Companion;", "", "()V", "ID", "", "ai-cli_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}