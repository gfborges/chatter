package br.com.App;

import javax.swing.JOptionPane;
import br.com.Server.Server;

public class Main {
	
	public static void main(String[] args) throws Exception {
		String addr;
		if(args.length > 0) {
			addr = args[1];
		} else {
			addr = "127.0.0.1";
		}
		// conexao socket tcp com servidor
		final Client conn = new Client(addr, Server.PORT);
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
