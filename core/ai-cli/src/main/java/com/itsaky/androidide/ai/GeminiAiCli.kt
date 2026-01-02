package com.itsaky.androidide.ai

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.util.concurrent.TimeUnit

/**
 * Gemini AI CLI integration based on PhoneAiCli implementation
 */
class GeminiAiCli(
    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .build(),
    private val gson: Gson = Gson()
) {
    
    companion object {
        private const val GEMINI_API_BASE = "https://generativelanguage.googleapis.com/v1beta"
        private const val DEFAULT_MODEL = "gemini-1.5-flash"
    }
    
    private var apiKey: String? = null
    
    fun setApiKey(key: String) {
        apiKey = key
    }
    
    suspend fun generateCode(
        prompt: String,
        context: String = "",
        language: String = "kotlin"
    ): AiResponse = withContext(Dispatchers.IO) {
        
        val systemPrompt = """
            You are an expert Android developer assistant integrated into AndroidIDE.
            Generate clean, efficient $language code following Android best practices.
            
            Context: $context
            
            Rules:
            - Use AndroidX libraries when applicable
            - Include proper error handling
            - Follow Kotlin/Java conventions
            - Add necessary imports
            - Keep code concise and readable
        """.trimIndent()
        
        return@withContext callGeminiApi(systemPrompt, prompt)
    }
    
    suspend fun fixBuildError(
        errorMessage: String,
        buildFile: String? = null,
        sourceCode: String? = null
    ): AiResponse = withContext(Dispatchers.IO) {
        
        val prompt = buildString {
            append("Fix this Android build error:\n\n")
            append("Error: $errorMessage\n\n")
            
            buildFile?.let {
                append("Build file content:\n```gradle\n$it\n```\n\n")
            }
            
            sourceCode?.let {
                append("Source code:\n```kotlin\n$it\n```\n\n")
            }
            
            append("Provide the exact fix needed and explain the solution.")
        }
        
        return@withContext callGeminiApi("You are an Android build expert. Fix build errors with precise solutions.", prompt)
    }
    
    suspend fun explainCode(
        code: String,
        language: String = "kotlin"
    ): AiResponse = withContext(Dispatchers.IO) {
        
        val prompt = """
            Explain this $language code:
            
            ```$language
            $code
            ```
            
            Provide a clear explanation of what the code does, its purpose, and any important details.
        """.trimIndent()
        
        return@withContext callGeminiApi("You are a code explanation expert.", prompt)
    }
    
    suspend fun optimizeCode(
        code: String,
        language: String = "kotlin"
    ): AiResponse = withContext(Dispatchers.IO) {
        
        val prompt = """
            Optimize this $language code for better performance and readability:
            
            ```$language
            $code
            ```
            
            Provide the optimized version with explanations of improvements made.
        """.trimIndent()
        
        return@withContext callGeminiApi("You are a code optimization expert.", prompt)
    }
    
    suspend fun terminalAssist(
        command: String,
        workingDir: String = "",
        error: String? = null
    ): AiResponse = withContext(Dispatchers.IO) {
        
        val prompt = buildString {
            if (error != null) {
                append("Help fix this terminal command error:\n")
                append("Command: $command\n")
                append("Error: $error\n")
                append("Working directory: $workingDir\n\n")
                append("Provide the correct command and explanation.")
            } else {
                append("Explain this terminal command and suggest improvements:\n")
                append("Command: $command\n")
                append("Working directory: $workingDir\n\n")
                append("Explain what it does and suggest any optimizations.")
            }
        }
        
        return@withContext callGeminiApi("You are a terminal and command-line expert.", prompt)
    }
    
    private suspend fun callGeminiApi(systemPrompt: String, userPrompt: String): AiResponse {
        val key = apiKey ?: return AiResponse(false, "", "API key not set")
        
        try {
            val requestBody = JsonObject().apply {
                add("contents", gson.toJsonTree(listOf(
                    mapOf(
                        "parts" to listOf(
                            mapOf("text" to "$systemPrompt\n\n$userPrompt")
                        )
                    )
                )))
                add("generationConfig", JsonObject().apply {
                    addProperty("temperature", 0.7)
                    addProperty("topK", 40)
                    addProperty("topP", 0.95)
                    addProperty("maxOutputTokens", 8192)
                })
            }
            
            val request = Request.Builder()
                .url("$GEMINI_API_BASE/models/$DEFAULT_MODEL:generateContent?key=$key")
                .post(requestBody.toString().toRequestBody("application/json".toMediaType()))
                .build()
                
            val response = httpClient.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""
            
            if (response.isSuccessful) {
                val jsonResponse = gson.fromJson(responseBody, JsonObject::class.java)
                val candidates = jsonResponse.getAsJsonArray("candidates")
                
                if (candidates != null && candidates.size() > 0) {
                    val content = candidates[0].asJsonObject
                        .getAsJsonObject("content")
                        .getAsJsonArray("parts")[0].asJsonObject
                        .get("text").asString
                        
                    return AiResponse(true, content)
                } else {
                    return AiResponse(false, "", "No response from Gemini API")
                }
            } else {
                return AiResponse(false, "", "Gemini API error: $responseBody")
            }
        } catch (e: Exception) {
            return AiResponse(false, "", "Request failed: ${e.message}")
        }
    }
}

data class AiResponse(
    val success: Boolean,
    val content: String,
    val error: String = ""
)
