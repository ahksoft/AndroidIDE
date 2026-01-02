package com.itsaky.androidide.ai

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Background service for AI terminal integration
 */
class AiTerminalService : Service() {
    
    private val binder = AiTerminalBinder()
    private val terminalAi = TerminalAiIntegration()
    private val serviceScope = CoroutineScope(Dispatchers.IO)
    
    inner class AiTerminalBinder : Binder() {
        fun getService(): AiTerminalService = this@AiTerminalService
    }
    
    override fun onBind(intent: Intent?): IBinder = binder
    
    fun processAiCommand(command: String, workingDir: String, callback: (String) -> Unit) {
        serviceScope.launch {
            terminalAi.processCommand(command, workingDir, callback)
        }
    }
    
    fun setApiKey(key: String) {
        terminalAi.setApiKey(key)
    }
}
