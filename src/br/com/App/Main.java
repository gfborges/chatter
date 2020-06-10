package br.com.App;

import javax.swing.JOptionPane;
import br.com.Server.Server;

public class Main {
	
	public static void main(String[] args) throws Exception {
		// conexao socket tcp com servidor
		final Client conn = new Client("127.0.0.1", Server.PORT);
		// nome do cliente
		String name = "";
		while((name == null)|| name.length() == 0) { // le o nome
			name = JOptionPane.showInputDialog("Nome:");
		}
		name = name.trim(); // limpa espaços do começo e fim
		conn.send(name, false); // manda para o servidor sem criptografar
        // abre a janela do chat
		final ChatWindow cw = ChatWindow.open(conn, name);
		// thread para ouvir mensagens
		cw.listener.start();
		
	}

}
