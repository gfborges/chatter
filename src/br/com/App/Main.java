package br.com.App;

import javax.swing.JOptionPane;
import br.com.Server.Server;

public class Main {
	
	public static void main(String[] args) throws Exception {
		final Client conn = new Client("127.0.0.1", Server.PORT);
		String name = "";
		while(name.isEmpty()) {
			name = JOptionPane.showInputDialog("Nome:");
		}
		final ChatWindow cw = ChatWindow.open(conn, name);
		
		cw.listener.start();
		
	}
	static void menu() {
		System.out.println("1. Mandar mensagem");
		System.out.println("2. Ler mensagem");
		System.out.println("3. Sair");
	}

}
