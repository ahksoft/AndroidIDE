package com.itsaky.androidide.ai

import java.io.File

/**
 * Terminal AI integration for AndroidIDE/Termux
 */
class TerminalAiIntegration(
    private val aiAssistant: AiCodeAssistant = AiCodeAssistant()
) {
    
    fun setApiKey(key: String) {
        aiAssistant.setApiKey(key)
    }
    
    /**
     * Process terminal command with AI assistance
     */
    fun processCommand(
        command: String,
        workingDir: String,
        onResult: (String) -> Unit
    ) {
        // Check if command needs AI assistance
        when {
            command.startsWith("ai ") -> {
                handleAiCommand(command.substring(3), workingDir, onResult)
            }
            command.startsWith("explain ") -> {
                handleExplainCommand(command.substring(8), workingDir, onResult)
            }
            command.startsWith("fix ") -> {
                handleFixCommand(command.substring(4), workingDir, onResult)
            }
            else -> {
                onResult("Unknown AI command. Use: ai <prompt>, explain <code>, fix <error>")
            }
        }
    }
    
    private fun handleAiCommand(prompt: String, workingDir: String, onResult: (String) -> Unit) {
        aiAssistant.generateCode(prompt, null, detectLanguage(workingDir)) { response ->
            if (response.success) {
                onResult("AI Generated:\n${response.content}")
            } else {
                onResult("AI Error: ${response.error}")
            }
        }
    }
    
    private fun handleExplainCommand(code: String, workingDir: String, onResult: (String) -> Unit) {
        aiAssistant.explainCode(code, detectLanguage(workingDir)) { response ->
            if (response.success) {
                onResult("Explanation:\n${response.content}")
            } else {
                onResult("Explain Error: ${response.error}")
            }
        }
    }
    
    private fun handleFixCommand(error: String, workingDir: String, onResult: (String) -> Unit) {
        aiAssistant.fixBuildError(error) { response ->
            if (response.success) {
                onResult("Fix Suggestion:\n${response.content}")
            } else {
                onResult("Fix Error: ${response.error}")
            }
        }
    }
    
    private fun detectLanguage(workingDir: String): String {
        val dir = File(workingDir)
        return when {
            dir.listFiles()?.any { it.name.endsWith(".kt") } == true -> "kotlin"
            dir.listFiles()?.any { it.name.endsWith(".java") } == true -> "java"
            dir.listFiles()?.any { it.name.endsWith(".py") } == true -> "python"
            dir.listFiles()?.any { it.name.endsWith(".js") } == true -> "javascript"
            dir.listFiles()?.any { it.name.endsWith(".cpp") || it.name.endsWith(".c") } == true -> "cpp"
            else -> "kotlin"
        }
    }
    
    /**
     * Get AI help for terminal commands
     */
    fun getCommandHelp(command: String, onResult: (String) -> Unit) {
        aiAssistant.assistTerminal(command) { response ->
            if (response.success) {
                onResult("Command Help:\n${response.content}")
            } else {
                onResult("Help Error: ${response.error}")
            }
        }
    }
}
