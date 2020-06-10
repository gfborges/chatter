package br.com.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientInfo {
	
	public Server server;
	public Socket conn;
	public BufferedReader reader;
	public PrintWriter writer;
	public String name;
	
	public ClientInfo(Socket conn, Server server) throws IOException {
		this.conn = conn;
		this.server = server;
		this.reader = new BufferedReader(
			new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
		);
		this.writer = new PrintWriter(
				new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8), true);
	}
	
	public void disconnect() {
		try {
			conn.close();
		} catch (IOException e) {
			System.out.println("Falha ao encerrar conexão com o cliente: " + e.getMessage());
		} // fecha a conexão
		
		// remove este cliente da lista
		server.clients.remove(this);
	}
}
