package br.com.App;

import br.com.Negocio.Controle;
import br.com.Server.Server;

public class Main {
	
	public static void main(String[] args) throws Exception {
		Controle ctrl = new Controle();
		Client conn = new Client("127.0.0.1", Server.DOOR);
		
		int op;
		while(true) {
			menu();
			op = ctrl.readInt();
			switch(op) {
			case 1:
				String msg = ctrl.readLine();
				conn.send(msg);
				System.out.println("Eu: " + msg);
				break;
			case 2:
				System.out.println("Other: messages");
				break;
			case 3:
				return;
			default:
				break;
			}
			ctrl.await();
		}
	}
	static void menu() {
		System.out.println("1. Mandar mensagem");
		System.out.println("2. Ler mensagem");
		System.out.println("3. Sair");
	}

}
