package com.ka.gradle.plugin.notifier

import org.gradle.api.*
import org.gradle.api.tasks.*
import com.ka.gradle.plugin.notifier.*

class NotifierTask extends DefaultTask {
	@Optional
	String recipient = "kobyahron@gmail.com"
	
	@Optional
	String senderMail = null
	
	@Optional
	String senderPass = null
	
	@Optional
	String message = "Build failed"
	
	GTalkClient client
	
	public NotifierTask() {
		super()
		
		client = new GTalkClient(senderMail, senderPass)
	}
	
	
	@TaskAction
	def notifyGTalk() {
		
		if (!senderPass || !senderMail) {
			def config = new ConfigSlurper().parse(
				new File("${project.rootProject.rootDir}/authentication.properties").toURL())
			setSenderMail(config.sender.mail)
			setSenderPass(config.sender.password)
		}
		
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