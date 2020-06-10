package br.com.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ChatWindow {
	private Client conn;
	private JFrame frame;
	private JPanel panel;
	private JLabel labelName;
	private JTextField msgField;
	private JButton btnSend;
	private JTextArea msgHistory;
	
	public ChatWindow(JFrame frame, JPanel panel, JTextField msgField, JTextArea msgHistory, JButton btnSend, Client conn, JLabel labelName) {
		this.frame = frame;
		this.msgHistory = msgHistory;
		this.panel = panel;
		this.labelName = labelName;
		this.msgField = msgField;
		this.btnSend = btnSend;
		this.conn = conn;
	}
	
	public static ChatWindow open(Client conn, final String name) {
		// window frame
		JFrame frame = new JFrame("chatter - " + name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,400);
		
		// msg history
		final JTextArea msgHistory = new JTextArea();
		msgHistory.setEditable(false);
		msgHistory.setLineWrap(true);
		msgHistory.setWrapStyleWord(true);
		JScrollPane msgHistScroll = new JScrollPane(msgHistory);
		
		// lower panel txt field + send btn
		JPanel panel = new JPanel();
		JLabel labelName = new JLabel(name+":");
		JTextField msgField = new JTextField(15);
		JButton btnSend  = new JButton("SEND");
		
		btnSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String msg = String.format("%s %s", labelName.getText() ,msgField.getText());
					conn.send(msg);
					msgHistory.append(msg + System.lineSeparator());
					msgField.setText("");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}		
		});
		panel.add(labelName);
		panel.add(msgField);
		panel.add(btnSend);
		
		// add to window frame
		frame.getContentPane().add(BorderLayout.CENTER, msgHistory);
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		frame.getRootPane().setDefaultButton(btnSend);
		frame.addWindowListener(new WindowAdapter() {
		    public void WindowClosing(WindowEvent e) throws Exception {
		    	conn.close();
		        frame.dispose();
		    }
		});
		frame.addWindowListener(new WindowAdapter() {
		    public void WindowOpened(WindowEvent e) {
		    	msgField.requestFocus();
		    }
		});
		frame.setVisible(true);
		return new ChatWindow(frame, panel, msgField, msgHistory, btnSend, conn, labelName);
	}

    public JTextArea getMsgHistory(){
        return msgHistory;
    }
    
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
