package br.com.App;

import br.com.Negocio.Controle;
import br.com.Server.Server;

public class Main {
	
	public static void main(String[] args) throws Exception {
		Controle ctrl = new Controle();
		Client conn = new Client("127.0.0.1", Server.PORT);
		
		int op;
		System.out.println("Bem vindo ao chatter!(V0.4)");
		while(true) {
			menu();
			System.out.print("opcao: ");
			op = ctrl.readInt();
			switch(op) {
			case 1:
				String msg = ctrl.readLine();
				conn.send(msg);
				break;
			case 2:
				conn.listen();
				ctrl.await();
				break;
			case 3:
				conn.close();
				return;
			default:
				System.out.println("opcao invalida");
				break;
			}	
		}
	}
	static void menu() {
		System.out.println("1. Mandar mensagem");
		System.out.println("2. Ler mensagem");
		System.out.println("3. Sair");
	}

}
