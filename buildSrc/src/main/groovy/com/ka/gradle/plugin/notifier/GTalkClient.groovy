package com.ka.gradle.plugin.notifier

import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.XMPPConnection
import org.jivesoftware.smack.ConnectionConfiguration

class GTalkClient {

	def connConfig
	def connection
	
	String senderMail
	String senderPass
	
	public GTalkClient(String senderMail, String senderPass) {
		connConfig = new ConnectionConfiguration("talk.google.com", 5222,"gmail.com")
		connection = new XMPPConnection(connConfig)
		this.senderMail = senderMail
		this.senderPass = senderPass
	}

	public void sendMessage(String to, String message) {
		Message msg = new Message(to, Message.Type.chat);
		msg.setBody(message);			
		
		connection.connect()
		this.connection.login(senderMail, senderPass)			
		this.connection.sendPacket(msg)
		disconnect()
	}

	public void disconnect() {
		connection.disconnect()
	}
}