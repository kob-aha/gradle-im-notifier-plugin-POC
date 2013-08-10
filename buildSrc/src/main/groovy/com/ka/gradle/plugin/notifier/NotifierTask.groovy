package com.ka.gradle.plugin.notifier

import org.gradle.api.*
import org.gradle.api.tasks.*
import com.ka.gradle.plugin.notifier.*

class NotifierTask extends DefaultTask {
	@Optional
	String recipient = "kobyahron@gmail.com"
	
	@Optional
	String senderMail = "redcat.build@gmail.com"
	
	@Optional
	String senderPass = "redkatec13"
	
	@Optional
	String message = "Build failed"
	
	GTalkClient client
	
	public NotifierTask() {
		super()
		
		client = new GTalkClient(senderMail, senderPass)
	}
	
	
	@TaskAction
	def notifyGTalk() {
		client.sendMessage(recipient, message)
	}
	
	public void setSenderPass(String senderPass) {
		this.senderPass = senderPass
		client.senderPass = senderPass 
	}
	
	public void setSenderMail(String senderMail) {
		this.senderMail = senderMail
		client.senderMail = senderMail
	}
}