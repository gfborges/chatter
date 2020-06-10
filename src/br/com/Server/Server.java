package br.com.Server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {
	private static ServerSocket socket;
	private static ArrayList<PrintWriter> clients;
	public static final int PORT = 5001;
	private Socket conn;
	private OutputStream ou;
	private PrintWriter out;
	private InputStream in;
	private InputStreamReader inr;
	private BufferedReader bfr;
	
	public Server(Socket conn){
	   this.conn = conn;
	   try {
		   this.in  = conn.getInputStream();
		   this.inr = new InputStreamReader(in);
		   this.bfr = new BufferedReader(inr);
		   this.ou = conn.getOutputStream();
		   this.out = new PrintWriter(ou, true);
		   
		   clients.add(out);
	   } catch (Exception e) {
		   e.printStackTrace();
	   }                          
	}
	
	public static void main(String[] args) throws Exception {
		socket = new ServerSocket(PORT);
		clients = new ArrayList<>();
		System.out.println("Server running (V0.4)");
		while(true) {
			Socket conn = socket.accept();
			Thread thr = new Server(conn);
			System.out.println("Connetion established: " + conn);
			System.out.println(clients.size() + " client(s) connected");
			thr.start();
		}
		
	}
	
	public void run() {
		String msg = "something";
		try {
			while(!msg.isEmpty()) {
				if(bfr.ready()) {
					msg = bfr.readLine();
					System.out.println(conn.getPort()+":"+ msg);
					sendToAll(msg);
				}
			}
			clients.remove(out);
			System.out.println("Connection ended:" + conn);
			conn.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void sendToAll(String msg) throws Exception {
		for(PrintWriter pw: clients) {
			if(!(pw == out)) {
				System.out.print(".");
				pw.println(msg);
			}
		}
		System.out.println();
	}
	

	
}
