package com.ka.gradle.plugin.notifier

import org.gradle.api.*
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.XMPPConnection
import org.jivesoftware.smack.ConnectionConfiguration

class GTalkClient {

	def connConfig
	def connection
	
	public GTalkClient() {
		connConfig = new ConnectionConfiguration("talk.google.com", 5222,"gmail.com")
		connection = new XMPPConnection(connConfig)
	}

	public void sendMessage(IMNotifierPluginExtension pluginExtension, String message) {
		Message msg = new Message(pluginExtension.recipient, Message.Type.chat);
		msg.setBody(message);			
		
		connection.connect()
		this.connection.login(pluginExtension.senderMail, pluginExtension.senderPass)			
		this.connection.sendPacket(msg)
		disconnect()
	}

	public void disconnect() {
		connection.disconnect()
	}
}