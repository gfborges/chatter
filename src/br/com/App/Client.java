package br.com.App;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
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
		if(socket.isClosed()) {
			System.out.println("Socket is Closed");
			return;
		}
		bfw.write(msg + System.lineSeparator());
		bfw.flush();
	}

}
