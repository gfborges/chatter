package br.com.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ChatWindow {
	private Client conn;
	private JFrame frame;
	private JPanel panel;
	private JLabel labelName;
	private JTextField msgField;
	private JButton btnSend;
	private JTextArea msgHistory;
	private JScrollPane msgHistScroll;
	
	public ChatWindow(JFrame frame, JPanel panel, JTextField msgField, JTextArea msgHistory, JScrollPane msgHistScroll, JButton btnSend, Client conn, JLabel labelName) {
		this.frame = frame;
		this.msgHistory = msgHistory;
		this.panel = panel;
		this.labelName = labelName;
		this.msgField = msgField;
		this.btnSend = btnSend;
		this.conn = conn;
	}
	
	public static ChatWindow open(Client conn, final String name) {
		// frame da janela
		JFrame frame = new JFrame("chatter - " + name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,400);
		
		// display das mensagens e scrollbar
		final JTextArea msgHistory = new JTextArea();
		msgHistory.setEditable(false);
		msgHistory.setLineWrap(true);
		msgHistory.setWrapStyleWord(true);
		final JScrollPane msgHistScroll = new JScrollPane(msgHistory);
		
		// caixa de texto e botão para enviar
		JPanel panel = new JPanel();
		JLabel labelName = new JLabel(name+":");
		JTextField msgField = new JTextField(15);
		JButton btnSend  = new JButton("SEND");
		// evento do botÃ£o de enviar
		btnSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// formata mensagem "nome: msg"
					String msg = String.format("%s %s", labelName.getText() ,msgField.getText().trim());
					conn.send(msg); // envia a mensagem para o servidor e criptografa
					// coloca mensagem no painel de mensagens
					msgHistory.append(msg + System.lineSeparator());
					msgField.setText(""); // limpa a caixa de texto
					// mover scrollbar para baixo
					JScrollBar scroll = msgHistScroll.getVerticalScrollBar();
					scroll.setValue(scroll.getMaximum());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}		
		});
		// adiciona para o painel
		panel.add(labelName);
		panel.add(msgField);
		panel.add(btnSend);
		
		// adiciona para a janela
		frame.getContentPane().add(BorderLayout.CENTER, msgHistScroll);
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		
		// botão padrÃ£o da tela, ENTER executa o evento dele
		frame.getRootPane().setDefaultButton(btnSend);
		// evento para quando fechar
		frame.addWindowListener(new WindowAdapter() {
		    public void WindowClosing(WindowEvent e) throws Exception {
		    	conn.close();
		        frame.dispose();
		    }
		});
		// evento para quando abrir
		frame.addWindowListener(new WindowAdapter() {
		    public void WindowOpened(WindowEvent e) {
		    	msgField.requestFocus();
		    }
		});
		// abre a janela
		frame.setVisible(true);
		return new ChatWindow(frame, panel, msgField, msgHistory, msgHistScroll, btnSend, conn, labelName);
	}

    public JTextArea getMsgHistory(){
        return msgHistory;
    }
    
    // thread para escutar a mensagens do servidor
    Thread listener = new Thread(new Runnable() {
		@Override
		public void run() {
			String msg = null;
			while(true) {
				try {
					msg = conn.listen();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(msg != null) {
					msgHistory.append(msg + System.lineSeparator());
				}
				
			}
		}
	});
    
}
