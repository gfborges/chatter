package br.com.Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {
	private static ServerSocket socket;
	private static ArrayList<BufferedWriter> clients;
	public static final int DOOR = 5001;
	private String nome;
	private Socket conn;
	private InputStream in;  
	private InputStreamReader inr;  
	private BufferedReader bfr;
	
	public Server(Socket conn){
	   this.conn = conn;
	   try {
		   in  = conn.getInputStream();
		   inr = new InputStreamReader(in);
		   bfr = new BufferedReader(inr);
	   } catch (Exception e) {
		   e.printStackTrace();
	   }                          
	}
	
	public static void main(String[] args) throws Exception {
		socket = new ServerSocket(DOOR);
		clients = new ArrayList<BufferedWriter>();
		System.out.println("Server running");
		while(true) {
			Socket conn = socket.accept();
			System.out.println("Connetion established: " + conn);
			Thread thr = new Server(conn);
			thr.start();
		}
		
	}
	
	public void sendToAll(BufferedWriter bfw, String msg) throws Exception {
		
		BufferedWriter bwS;
		System.out.println(nome + " -> " +  msg);
		for(BufferedWriter bw : clients) {
			bwS = (BufferedWriter)bw;
			if(!(bfw == bwS)) {
				bw.write(nome + " : " + msg + System.lineSeparator());
				bw.flush();
			}
		}
	}
	
	public void run() {
		String msg;
		try {
			OutputStream ou = conn.getOutputStream();
			Writer ouw = new OutputStreamWriter(ou);
			BufferedWriter bfw = new BufferedWriter( ouw );
			clients.add(bfw);
			nome = msg = bfr.readLine();
			System.out.println(nome + ":" + msg);
			while(msg != null && !msg.equalsIgnoreCase("sair")) {
				if(bfr.ready()) {
					msg = bfr.readLine();
					sendToAll(bfw, msg);
				}
			}
			System.out.println("Connection ended:" + nome);
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
