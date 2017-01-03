
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javafx.scene.input.KeyCode; 
import javafx.scene.input.KeyEvent; 

import javafx.event.EventHandler; 

import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import javafx.beans.value.ChangeListener; 
public class Main extends JFrame {

	private FaceFactory f;
	private JPanel contentPane;
	private Client c;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		f = new FaceFactory("0","0","0","0");
		c = new Client();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize.width, screenSize.height);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setUndecorated(true);
		
		int maxWidth = screenSize.width - 100;
		int maxHeight = screenSize.height - 100;
		int startX = (screenSize.width - maxWidth)/2;
		int startY = (screenSize.height - maxHeight)/2;
		int padding = (int) Math.round(maxHeight*0.025);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setSize(screenSize.width, screenSize.height);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel menuPanel = new JPanel();
		menuPanel.setBackground(contentPane.getBackground());
		menuPanel.setBounds( startX, startY, maxWidth, (int) Math.round(maxHeight*0.05) );
		contentPane.add(menuPanel);
		menuPanel.setLayout(null);
		
		JButton btnExit = new JButton("X");
		btnExit.setBounds(maxWidth - (menuPanel.getHeight()), 0, menuPanel.getHeight(), menuPanel.getHeight());
		btnExit.setFont(new Font("Segoe UI Light", Font.PLAIN, (int) Math.round(btnExit.getHeight()*0.25)));
		menuPanel.add(btnExit);
		
		JLabel lblPepeRizal = new JLabel("Pepe Rizal");
		lblPepeRizal.setBounds(0, 0, maxWidth/2, menuPanel.getHeight());
		lblPepeRizal.setFont(new Font("Segoe UI", Font.PLAIN, (int) Math.round(menuPanel.getHeight()*0.8)));
		menuPanel.add(lblPepeRizal);
		
		
		ClassLoader cl = this.getClass().getClassLoader();
		JLayeredPane facePanel = new JLayeredPane();
		facePanel.setBounds( startX+100, menuPanel.getY() + menuPanel.getHeight() + padding, maxWidth,(int) Math.round(maxHeight*0.7) );
		contentPane.add(f.display(facePanel));
		
		
		JTextArea response = new JTextArea();
		response.setLineWrap(true);
		response.setWrapStyleWord(true);
		response.setText("This is a sample text.");
		response.setBackground(contentPane.getBackground());
		response.setBounds( startX, facePanel.getY()+100, maxWidth/2, facePanel.getHeight() - 100);
		response.setFont(new Font("Segoe UI Light", Font.PLAIN, (int) Math.round(response.getWidth()*0.05)));
		response.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		contentPane.add(response);
		
		
		JTextField input = new JTextField();
		input.setText("This is a sample text.");
		input.setBounds( startX, facePanel.getY() + facePanel.getHeight() + padding, (int) Math.round(maxWidth*0.9), (int) Math.round(maxHeight*0.08));
		input.setFont(new Font("Segoe UI Light", Font.PLAIN, (int) Math.round(input.getHeight()*0.35)));
		contentPane.add(input);
		
		JButton btnVoice = new JButton("Voice");
		btnVoice.setBounds( startX + input.getWidth() + padding, input.getY() , maxWidth - (input.getWidth() + padding), input.getHeight());
		btnVoice.setFont(new Font("Segoe UI Light", Font.PLAIN, (int) Math.round(btnVoice.getHeight()*0.25)));
		
		contentPane.add(btnVoice);
		
		this.pack();
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnVoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		@SuppressWarnings("serial")
		Action action = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String received = c.send(input.getText());
					String responseTxt = f.update(received);
					response.setText(responseTxt);
					
					if(received.charAt(0)=='!'){
						facePanel.removeAll();
						contentPane.add(f.display(facePanel));
						repaint();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("OH NO");
				}
				input.setText("");
			}

		};

		input.addActionListener( action );
		
	}
	private void changeDisplay(String[] draw) {
		
		
		for (int i = 0; i < draw.length; i++){
		}
		
	}
	
   
}
