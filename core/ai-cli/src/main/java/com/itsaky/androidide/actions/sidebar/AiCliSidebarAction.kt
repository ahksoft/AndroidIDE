package com.itsaky.androidide.actions.sidebar

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import com.itsaky.androidide.actions.ActionData
import com.itsaky.androidide.actions.SidebarActionItem
import com.itsaky.androidide.ai.AiCliSidebarFragment
import kotlin.reflect.KClass

/**
 * AI CLI sidebar action for AndroidIDE
 */
class AiCliSidebarAction(context: Context, order: Int) : SidebarActionItem() {

  companion object {
    const val ID = "ide.editor.sidebar.aiCli"
  }

  override val id: String = ID
  override var label: String = "AI CLI"
  override var icon: Drawable? = context.getDrawable(android.R.drawable.ic_dialog_info)
  override var order: Int = order
  override val fragmentClass: KClass<out Fragment>? = AiCliSidebarFragment::class

  override var enabled: Boolean = true

  override fun prepare(data: ActionData) {
    visible = true
    enabled = true
  }

  override fun FragmentNavigatorDestinationBuilder.buildNavigation() {
    label = this@AiCliSidebarAction.label
  }
}
