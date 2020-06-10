package br.com.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	
	private ServerSocket socket;
	public ArrayList<ClientInfo> clients;
	public static final int PORT = 5001;
	
	// Construtor da classe Server
	public Server() {
		
		// try-catch para abrir o socket do servidor
		try {
			this.socket = new ServerSocket(PORT);
		} catch (IOException e) {
			System.out.println("Erro ao abrir socket do servidor: " + e.getMessage());
			System.exit(1); // Encerra a execução do programa
		}
		
		this.clients = new ArrayList<>();
	}
	
	// Método que replica a mensagem para todos os clientes conectados
	// src_client é o cliente que enviou a mensagem, pode ser null, caso seja uma mensagem do servidor
	public void broadcast(ClientInfo src_client, String msg) {
		for(ClientInfo c: clients)
			if(c != src_client)
				c.writer.println(msg);
	}
	
	// Envia uma mensagem ao cliente solicitando o seu nome
	public void readClientName(ClientInfo client) {
		try {
			client.name = client.reader.readLine();
		}
		catch(Exception e) {
			client.writer.println("Desculpe, ocorreu uma falha ao ler seu nome. Nomeado como \"Anonymous\"!");
			client.name = "Anonymous";
		}
	}
	
	public static void main(String[] args) throws IOException {
		Server s = new Server();
		
		System.out.println("Server iniciado e esperando por conexões!");
		
		while(true) {
			Socket conn;
			
			// tenta estabelecer conexão com um cliente
			try {
				conn = s.socket.accept(); // aceita a conexão
			} catch (IOException e) {
				System.out.println("Não foi possível estabelecer conexão com cliente: " + e.getMessage());
				continue;
			}
			
			// log de entrada
			System.out.println("Cliente conectado: " + conn.getRemoteSocketAddress());
			
			// cria o cliente apartir da conexão
			ClientInfo client = new ClientInfo(conn, s);
			
			// cria uma thread de HandleClient que ficará responsável por gerenciar a conexão com o cliente
			Thread thr = new HandleClient(client, s);
			thr.start(); // inicia a thread para lidar com a conexão
		}

	}
}