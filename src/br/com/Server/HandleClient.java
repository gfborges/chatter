package br.com.Server;

// essa classa é responsável por gerenciar a conexão com o cliente, ou seja, receber e replicar suas mensagens, tratar
// possíveis erros e desconectar o cliente
public class HandleClient extends Thread {
	
	private ClientInfo client;
	private Server server;
	
	public HandleClient(ClientInfo client, Server server) {
		this.client = client;
		this.server = server;
	}
	
	public void run() {

		// Envia uma mensagem ao cliente solicitando seu nome
		server.readClientName(client);
		
		// Informa a entrada do cliente à todos os outros conectados
		server.broadcast(null, client.name + " entrou!");
		
		// por fim adiciona o cliente na lista de clients conectados
		server.clients.add(client);
		
		String msg;
		while(true) {
			try {
				msg = client.reader.readLine(); // lê uma mensagem enviada pelo clientes
				msg = client.name + ": " + msg; // concatena à mensagem o nome do cliente
				System.out.println(msg); // log das mensagens enviadas
				server.broadcast(client, msg); // replica a mensagem enviada
			}
			catch(Exception e) {
				break;
			}
		}
		
		// log de saída
		System.out.println("Cliente " + client.conn.getRemoteSocketAddress() + " saiu!");
		
		// disconecta o cliente do servidor
		client.disconnect();

		// Informa a entrada do cliente à todos os outros conectados
		server.broadcast(null, client.name + " saiu!");
		
	}
}
