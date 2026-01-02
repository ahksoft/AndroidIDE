package com.itsaky.androidide.ai

import com.itsaky.androidide.projects.GradleProject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * AI Code Assistant using Gemini AI CLI
 */
class AiCodeAssistant(
    private val geminiCli: GeminiAiCli = GeminiAiCli(),
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    
    fun setApiKey(key: String) {
        geminiCli.setApiKey(key)
    }
    
    fun generateCode(
        prompt: String,
        project: GradleProject? = null,
        language: String = "kotlin",
        onResult: (AiResponse) -> Unit
    ) {
        scope.launch {
            try {
                val context = project?.let { "Android project: ${it.name}" } ?: ""
                val response = geminiCli.generateCode(prompt, context, language)
                onResult(response)
            } catch (e: Exception) {
                onResult(AiResponse(false, "", "Failed to generate code: ${e.message}"))
            }
        }
    }
    
    fun fixBuildError(
        errorMessage: String,
        project: Project? = null,
        buildFile: String? = null,
        sourceCode: String? = null,
        onResult: (AiResponse) -> Unit
    ) {
        scope.launch {
            try {
                val response = geminiCli.fixBuildError(errorMessage, buildFile, sourceCode)
                onResult(response)
            } catch (e: Exception) {
                onResult(AiResponse(false, "", "Failed to fix build error: ${e.message}"))
            }
        }
    }
    
    fun explainCode(
        code: String,
        language: String = "kotlin",
        onResult: (AiResponse) -> Unit
    ) {
        scope.launch {
            try {
                val response = geminiCli.explainCode(code, language)
                onResult(response)
            } catch (e: Exception) {
                onResult(AiResponse(false, "", "Failed to explain code: ${e.message}"))
            }
        }
    }
    
    fun optimizeCode(
        code: String,
        language: String = "kotlin",
        onResult: (AiResponse) -> Unit
    ) {
        scope.launch {
            try {
                val response = geminiCli.optimizeCode(code, language)
                onResult(response)
            } catch (e: Exception) {
                onResult(AiResponse(false, "", "Failed to optimize code: ${e.message}"))
            }
        }
    }
    
    fun assistTerminal(
        command: String,
        workingDir: String = "",
        error: String? = null,
        onResult: (AiResponse) -> Unit
    ) {
        scope.launch {
            try {
                val response = geminiCli.terminalAssist(command, workingDir, error)
                onResult(response)
            } catch (e: Exception) {
                onResult(AiResponse(false, "", "Failed to assist with terminal: ${e.message}"))
            }
        }
    }
}
