package br.com.App;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import br.com.Server.Server;

public class Client {
	private final static int SECRET = 13;
	private Socket socket;
	private OutputStream ou;
	private OutputStreamWriter ouw;
	private BufferedWriter bfw;
	
	public Client(String ip, int door) throws Exception {
		this.socket = new Socket(ip, door);
		this.ou = socket.getOutputStream();
		this.ouw = new OutputStreamWriter(ou);
		this.bfw = new BufferedWriter(ouw);
	}
	
	public void send(String msg) throws Exception {
		String msgEncoded = encrypt(msg);
		if(socket.isClosed()) {
			System.out.println("Socket is Closed");
			return;
		}
		bfw.write(msgEncoded + System.lineSeparator());
		bfw.flush();
	}
	
	private static String encrypt(String msg) {
		return secret(msg, 1);
	}
	
	private static String decrypt(String msg) {
		return secret(msg, -1);
	}
	
	private static String secret(String msg, int method) {
		char[] msgEncoded = new char[msg.length()];
		int i = 0;
		for(char ch : msg.toCharArray()) {
			msgEncoded[i++] = (char) (Character.getNumericValue(ch) + (SECRET * method) ); 
		}
		return new String(msgEncoded);
	}

}
