package com.itsaky.androidide.ai

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.itsaky.androidide.projects.IProjectManager

/**
 * AI CLI sidebar fragment for AndroidIDE
 */
class AiCliSidebarFragment : Fragment() {
    
    private lateinit var inputField: EditText
    private lateinit var outputText: TextView
    private val aiAssistant = AiCodeAssistant()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
        }
        
        inputField = EditText(requireContext()).apply {
            hint = "Enter prompt, code, or command..."
            minLines = 3
        }
        
        outputText = TextView(requireContext()).apply {
            text = "AI CLI Ready"
            setPadding(0, 16, 0, 16)
        }
        
        val generateButton = Button(requireContext()).apply {
            text = "Generate Code"
            setOnClickListener { generateCode() }
        }
        
        val explainButton = Button(requireContext()).apply {
            text = "Explain Code"
            setOnClickListener { explainCode() }
        }
        
        layout.addView(inputField)
        layout.addView(generateButton)
        layout.addView(explainButton)
        layout.addView(outputText)
        
        return layout
    }
    
    private fun generateCode() {
        val prompt = inputField.text.toString()
        if (prompt.isNotEmpty()) {
            outputText.text = "Generating..."
            val project = IProjectManager.getInstance().rootProject
            aiAssistant.generateCode(prompt, project, "kotlin") { response ->
                requireActivity().runOnUiThread {
                    outputText.text = if (response.success) response.content else "Error: ${response.error}"
                }
            }
        }
    }
    
    private fun explainCode() {
        val code = inputField.text.toString()
        if (code.isNotEmpty()) {
            outputText.text = "Explaining..."
            aiAssistant.explainCode(code, "kotlin") { response ->
                requireActivity().runOnUiThread {
                    outputText.text = if (response.success) response.content else "Error: ${response.error}"
                }
            }
        }
    }
}
