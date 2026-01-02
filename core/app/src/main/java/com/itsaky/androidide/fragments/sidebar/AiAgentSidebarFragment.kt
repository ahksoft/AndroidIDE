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

package com.itsaky.androidide.fragments.sidebar

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.itsaky.androidide.ai.AndroidIdeAIAssistant
import com.itsaky.androidide.ai.AIProvider
import com.itsaky.androidide.databinding.FragmentAiAgentSidebarBinding
import com.itsaky.androidide.fragments.FragmentWithBinding
import com.itsaky.androidide.projects.ProjectManager

/**
 * Fragment for AI Agent sidebar.
 *
 * @author Akash Yadav
 */
class AiAgentSidebarFragment : FragmentWithBinding<FragmentAiAgentSidebarBinding>(
  FragmentAiAgentSidebarBinding::inflate
) {

  private val aiAssistant = AndroidIdeAIAssistant()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    binding.btnFixBuildError.setOnClickListener {
      fixBuildError()
    }
    
    binding.btnGenerateCode.setOnClickListener {
      generateCode()
    }
    
    binding.btnOptimizeProject.setOnClickListener {
      optimizeProject()
    }
  }

  private fun fixBuildError() {
    val project = ProjectManager.getInstance().rootProject
    if (project == null) {
      showToast("No project opened")
      return
    }
    
    showToast("Analyzing build errors...")
    aiAssistant.fixBuildError(project, "Sample build error", AIProvider.OPENROUTER) { response ->
      requireActivity().runOnUiThread {
        if (response.success) {
          binding.tvResult.text = "Build Error Fix:\n${response.content}"
        } else {
          binding.tvResult.text = "Error: ${response.error}"
        }
      }
    }
  }

  private fun generateCode() {
    val project = ProjectManager.getInstance().rootProject
    if (project == null) {
      showToast("No project opened")
      return
    }
    
    val description = binding.etCodeDescription.text.toString().trim()
    if (description.isEmpty()) {
      showToast("Enter code description")
      return
    }
    
    showToast("Generating code...")
    aiAssistant.generateCode(project, description, AIProvider.GROQ) { response ->
      requireActivity().runOnUiThread {
        if (response.success) {
          binding.tvResult.text = "Generated Code:\n${response.content}"
        } else {
          binding.tvResult.text = "Error: ${response.error}"
        }
      }
    }
  }

  private fun optimizeProject() {
    val project = ProjectManager.getInstance().rootProject
    if (project == null) {
      showToast("No project opened")
      return
    }
    
    showToast("Optimizing project...")
    aiAssistant.optimizeProject(project, AIProvider.OPENROUTER) { response ->
      requireActivity().runOnUiThread {
        if (response.success) {
          binding.tvResult.text = "Optimization Suggestions:\n${response.content}"
        } else {
          binding.tvResult.text = "Error: ${response.error}"
        }
      }
    }
  }

  private fun showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
  }
}
