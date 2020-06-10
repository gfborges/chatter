package br.com.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	private final static int SECRET = 13;
	private Socket socket;
	private InputStream in;
	private InputStreamReader inr;
	private BufferedReader bfr;
	private PrintWriter pw;

	public Client(String ip, int door) throws Exception {
		this.socket = new Socket(ip, door);
		this.in = socket.getInputStream();
		this.inr = new InputStreamReader(in);
		this.bfr = new BufferedReader(inr);
		this.pw = new PrintWriter(socket.getOutputStream(), true);
	}
	
	public void send(String msg) throws Exception {
		String msgEncoded = encrypt(msg);
		pw.println(msgEncoded);
	}

	private static String encrypt(String msg) {
		return secret(msg, SECRET);
	}
	
	private static String decrypt(String msg) {
		return secret(msg, -SECRET);
	}
	
	private static String secret(String msg, int method) {
		char[] msgEncoded = new char[msg.length()];
		int i = 0;
		for(char ch : msg.toCharArray()) {
			msgEncoded[i++] = (char) ((int) ch + method); 
		}
		String s = new String(msgEncoded);
		return s;
	}

	public void listen() throws IOException{
		String msg;
		this.bfr = new BufferedReader(inr);
//		while(!"Sair".equalsIgnoreCase(msg)) {                               
			if(bfr.ready()){
				msg = bfr.readLine();
				if(msg.equals("Sair"))
					System.out.println("Servidor caiu!");
				else
					System.out.println(decrypt(msg));         
			}
//		}
	}
	
	public void close() {
		pw.println("");
		pw.close();
	}
}
