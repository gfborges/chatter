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
		
		final Client conn = new Client(addr, Server.PORT);
		String name = "";
		while((name == null)|| name.length() == 0) {
			name = JOptionPane.showInputDialog("Nome:");
		}
		name = name.trim();
		conn.send(name, false);
		final ChatWindow cw = ChatWindow.open(conn, name);
		
		cw.listener.start();
		
	}

}
