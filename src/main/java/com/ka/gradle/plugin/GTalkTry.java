package com.ka.gradle.plugin;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class GTalkTry {
	private static String username = "redcat.build@gmail.com";
    private static String password = "redkatec13";
    ConnectionConfiguration connConfig;
    XMPPConnection connection;

    public GTalkTry() throws XMPPException {
    connConfig = new ConnectionConfiguration("talk.google.com", 5222,"gmail.com");
            connection = new XMPPConnection(connConfig);
            connection.connect();
            connection.login(username, password);
    }

    public void sendMessage(String to, String message) {
            Message msg = new Message(to, Message.Type.chat);
            msg.setBody(message);
            connection.sendPacket(msg);
    }

    public void disconnect() {
            connection.disconnect();
    }

    public static void main(String[] args) throws XMPPException {
    		GTalkTry gtalkChat = new GTalkTry();
            gtalkChat.sendMessage("kobyahron@gmail.com",
            "Hi this message is send using Java and Google talk integration.");
            gtalkChat.disconnect();
    }
}
