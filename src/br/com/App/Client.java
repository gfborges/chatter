package br.com.App;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	
	public void listen() throws IOException{
        
		   InputStream in = socket.getInputStream();
		   InputStreamReader inr = new InputStreamReader(in);
		   BufferedReader bfr = new BufferedReader(inr);
		   String msg = "";
		                           
		    while(!"Sair".equalsIgnoreCase(msg))
		                                      
		       if(bfr.ready()){
		         msg = bfr.readLine();
		       if(msg.equals("Sair"))
		         System.out.println("Servidor caiu! \r\n");
		        else
		         System.out.println(msg+"\r\n");         
		        }
		}

}
