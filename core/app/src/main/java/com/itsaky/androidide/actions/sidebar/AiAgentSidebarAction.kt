/*
 *  This file is part of AndroidIDE.
 *
 *  AndroidIDE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  AndroidIDE is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with AndroidIDE.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.itsaky.androidide.actions.sidebar

import android.content.Context
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import com.itsaky.androidide.actions.ActionData
import com.itsaky.androidide.actions.SidebarActionItem
import com.itsaky.androidide.fragments.sidebar.AiAgentSidebarFragment

/**
 * Sidebar action for AI Agent.
 *
 * @author Akash Yadav
 */
class AiAgentSidebarAction(context: Context, order: Int) : SidebarActionItem() {

  companion object {
    const val ID = "ide.editor.sidebar.aiAgent"
  }

  override val id: String = ID
  override var label: String = "AI Agent"
  override val icon = android.R.drawable.ic_dialog_info
  override var order: Int = order
  override val fragmentClass = AiAgentSidebarFragment::class.java

  init {
    label = "AI Agent"
  }

  override fun prepare(data: ActionData) {
    visible = true
    enabled = true
  }

  override fun FragmentNavigatorDestinationBuilder.buildNavigation() {
    label = this@AiAgentSidebarAction.label
  }
}
