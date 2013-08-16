package com.ka.gradle.plugin.notifier

import org.gradle.api.*
import org.gradle.api.tasks.*
import com.ka.gradle.plugin.notifier.*

class NotifierTask extends DefaultTask {

	@Optional
	Task notifiedTask = null
	
	GTalkClient client
	
	public NotifierTask() {
		super()
		
		client = new GTalkClient()
	}
	
	
	@TaskAction
	def notifyGTalk() {
	
		def pluginExtension = project.imNotifier
		
		if (!pluginExtension.senderPass || !pluginExtension.senderMail ||
			!pluginExtension.recipient) {
			
			System.err.println('One of senderMail, senderPass or recipient is missing. Exit notifier.') 
			return
		}
			
		if (notifiedTask?.state?.failure) {
			client.sendMessage(pluginExtension, "Running task ${notifiedTask.name} failed")
		} else {
			client.sendMessage(pluginExtension, "Running task ${notifiedTask.name} finished successfully")
		}
	}
}