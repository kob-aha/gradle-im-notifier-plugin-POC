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
	Task notifiedTask = null
	
	GTalkClient client
	
	public NotifierTask() {
		super()
		
		client = new GTalkClient(senderMail, senderPass)
	}
	
	
	@TaskAction
	def notifyGTalk() {
		
		if (!senderPass || !senderMail) {
			def config = new ConfigSlurper().parse(
				new File("${project.rootProject.rootDir}/authentication.properties").toURI().toURL())
			setSenderMail(config.sender.mail)
			setSenderPass(config.sender.password)
		}
		
		if (notifiedTask?.state?.failure) {
			client.sendMessage(recipient, "Running task ${notifiedTask.name} failed")
		} else {
			client.sendMessage(recipient, "Running task ${notifiedTask.name} finished successfully")
		}
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