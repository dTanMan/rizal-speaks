
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.ConnectException;
import java.util.UUID;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;

import javafx.scene.input.KeyCode; 
import javafx.scene.input.KeyEvent; 

import javafx.event.EventHandler;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import javafx.beans.value.ChangeListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent; 
public class Main extends JFrame {

	private JPanel contentPane;
	private JLayeredPane facePanel;
	private Client c;
	private static boolean replyInit;
	private JPanel chatPanel;
	JTextArea responseTextArea, sentTextArea;
	JPanel responsePanel, sentPanel;
	Timer timer;
	Response r;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		replyInit = true;
		System.out.println("HELLO");
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
		
//		f = new FaceFactory("0","0","0","0");
		c = new Client();
		ColorScheme colors = new ColorScheme();
		Fonts fonts = new Fonts();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize.width, screenSize.height);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setUndecorated(true);
		
		int maxWidth = (int) (screenSize.width*0.9);
		int maxHeight = (int) (screenSize.height*0.9);
		int startX = (screenSize.width - maxWidth)/2;
		int startY = (screenSize.height - maxHeight)/2;
		int padding = (int) Math.round(maxHeight*0.07);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(colors.bg);
		contentPane.setSize(screenSize.width, screenSize.height);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel menuPanel = new JPanel();
		menuPanel.setBackground(colors.highlight);
		menuPanel.setBounds( 0, 0, screenSize.width, (int) Math.round(maxHeight*0.06) );
		contentPane.add(menuPanel);
		menuPanel.setLayout(null);
		
		JButton btnExit = new JButton("X");
		btnExit.setBounds((int)(screenSize.width - menuPanel.getHeight()), 0, menuPanel.getHeight(), menuPanel.getHeight());
		btnExit.setFont(new Font("Segoe UI Light", Font.PLAIN, (int) Math.round(btnExit.getHeight()*0.25)));
		btnExit.setBorderPainted(false);
		btnExit.setFocusPainted(false);
		btnExit.setBackground(colors.primary);
		menuPanel.add(btnExit);
		
		JButton btnSettings = new JButton("Settings");
		btnSettings.setBounds((int)(screenSize.width - (menuPanel.getHeight()*5)), 0, (menuPanel.getHeight()*4), menuPanel.getHeight());
		btnSettings.setFont(fonts.sub.deriveFont(Font.PLAIN, (int) Math.round(btnSettings.getHeight()*0.6)));
		btnSettings.setBorderPainted(false);
		btnSettings.setFocusPainted(false);
		btnSettings.setBackground(colors.primary);
		menuPanel.add(btnSettings);
		
		JLabel lblPepeRizal = new JLabel("You are now chatting with... Pepe Rizal");
		lblPepeRizal.setForeground(colors.bg);
		lblPepeRizal.setBounds((int) (screenSize.width * 0.02), 0, maxWidth/2, menuPanel.getHeight());
		lblPepeRizal.setFont(fonts.title.deriveFont(Font.PLAIN, (int) Math.round(menuPanel.getHeight()*0.6)));
		menuPanel.add(lblPepeRizal);
		
		
		ClassLoader cl = this.getClass().getClassLoader();
		facePanel = new JLayeredPane();
		facePanel.setBackground(Color.BLACK);
		facePanel.setBounds( startX+ width(contentPane, 5), menuPanel.getY() + menuPanel.getHeight() + padding, maxWidth,(int) Math.round(maxHeight*0.7) );
		r = new Response(facePanel);
		contentPane.add(r.getFacePanel());
		
		chatPanel = new JPanel();
		chatPanel.setBackground(colors.bg);
		chatPanel.setLayout(null);
		chatPanel.setBounds( startX, menuPanel.getY()+menuPanel.getHeight()+padding, maxWidth/2, (int) Math.round(maxHeight*0.7));
		contentPane.add(chatPanel);
		
		sentPanel = new JPanel();
		sentPanel.setLayout(new FlowLayout());
		sentPanel.setBackground(colors.primary);
		sentPanel.setBorder(new EmptyBorder(height(chatPanel, 4), width(chatPanel, 4), height(chatPanel, 4), width(chatPanel, 4)));
		sentPanel.setBounds(height(chatPanel, 8), height(chatPanel, 8), width(chatPanel, 80), height(chatPanel, 8));

		sentTextArea = new JTextArea();
		sentTextArea.setFont(fonts.body.deriveFont(Font.PLAIN, (int) Math.round(menuPanel.getHeight()*0.6)));
		sentTextArea.setLineWrap(true);
		sentTextArea.setWrapStyleWord(true);
		sentTextArea.setBackground(colors.primary);
		sentTextArea.setText("This is a sample text. trolol. I'm cute hehe. Love is a word  I can't describe.\nI'm amazing.");
		sentTextArea.setSize((int)(sentPanel.getWidth()*0.9), 1);
		sentTextArea.setSize(sentTextArea.getPreferredSize().width, sentTextArea.getPreferredSize().height + 10);
		System.out.println("5% = "+ (chatPanel.getHeight()*0.05));
		sentPanel.setBounds(height(chatPanel, 5), height(chatPanel, 5), width(chatPanel, 80), sentTextArea.getSize().height + height(chatPanel, 8));
		sentPanel.add(sentTextArea);
		chatPanel.add(sentPanel);

		responsePanel = new JPanel();
		responsePanel.setLayout(new FlowLayout());
		responsePanel.setBackground(colors.highlight);
		responsePanel.setBorder(new EmptyBorder(height(chatPanel, 4), width(chatPanel, 4), height(chatPanel, 4), width(chatPanel, 4)));
		responsePanel.setBounds(10, 10, (int)(chatPanel.getWidth()*0.8), 10);
		
		responseTextArea = new JTextArea();
		responseTextArea.setFont(fonts.body.deriveFont(Font.PLAIN, (int) Math.round(menuPanel.getHeight()*0.6)));
		responseTextArea.setForeground(Color.WHITE);
		responseTextArea.setLineWrap(true);
		responseTextArea.setWrapStyleWord(true);
		responseTextArea.setBackground(colors.highlight);
		responseTextArea.setText("This is a sample text. trolol. I'm cute hehe. Love is a word  I can't describe.\nI'm amazing.");
		responseTextArea.setSize((int)(sentPanel.getWidth()*0.9), 1);
		responseTextArea.setSize(responseTextArea.getPreferredSize().width, responseTextArea.getPreferredSize().height + 10);
		responsePanel.setBounds((int) (chatPanel.getWidth() - (chatPanel.getWidth()*0.8 + 30)), (sentPanel.getY() + sentPanel.getHeight() + height(chatPanel, 8)), width(chatPanel, 80), responseTextArea.getSize().height + height(chatPanel, 8));
		responsePanel.add(responseTextArea);
		chatPanel.add(responsePanel);
		
		JTextField input = new JTextField();
		input.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(replyInit){
					replyInit = false;
					input.selectAll();
				}
			}
		});


		input.setText("Type a reply...");
		input.setBackground(colors.primary);
		input.setBounds( startX, facePanel.getY() + facePanel.getHeight() + padding, maxWidth, (int) Math.round(maxHeight*0.08));
		input.setFont(new Font("Segoe UI Light", Font.PLAIN, (int) Math.round(input.getHeight()*0.35)));
		contentPane.add(input);
		
		
		timer =  new Timer((1000*60), new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {
		    	System.out.println("1 min. has passed. Restarting user...");
		    		try {
						c.send(":reset " + c.getBotname());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		c.setUserId(Math.random() + "");
		    		timer.restart();
		        }    
		    });
		
		
		this.pack();
		
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		@SuppressWarnings("serial")
		Action action = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String sendText = input.getText();
				String responseTxt = "";
				try {
					String received = c.send(sendText);
					responseTxt = r.update(received);
					contentPane.add(r.getFacePanel());
					repaint();
				} catch (ConnectException c){
					responseTxt = "You are not connected to the server. Try again!";
				} catch (IOException e1) {
					e1.printStackTrace();
					System.out.println("OH NO");
				}

				sentTextArea.setText(sendText);
				sentTextArea.setSize((int)(sentPanel.getWidth()*0.9), 1);
				sentTextArea.setSize(sentTextArea.getPreferredSize().width, sentTextArea.getPreferredSize().height + 10);
				sentPanel.setBounds(30, 30, (int)(chatPanel.getWidth()*0.8), sentTextArea.getSize().height + 10);
				
				responseTextArea.setText(responseTxt);
				responseTextArea.setSize(responseTextArea.getPreferredSize().width, responseTextArea.getPreferredSize().height + 10);
				responsePanel.setBounds((int) (chatPanel.getWidth() - (chatPanel.getWidth()*0.8 + 30)), (sentPanel.getY() +sentPanel.getHeight() + 30), (int)(chatPanel.getWidth()*0.8), responseTextArea.getSize().height +10);
				
				input.setText("");
				timer.start(); 
			}

		};

		input.addActionListener( action );
	
		
	}
	
	public JLayeredPane getFacePanel(){
		return facePanel;
	}

	class ColorScheme{
		public Color bg = Color.WHITE;
		public Color primary = new Color(234, 234, 234);
		public Color secondary = new Color(60, 60, 60);
		public Color highlight = new Color(35, 102, 201);
	}
	
	class Fonts{
		public Font title, sub, body;
		public Fonts(){
			title = null;
			sub = null;
			body = null;
			loadFonts();
		}
		public void loadFonts(){
			try {
				title = Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/Roboto-Medium.ttf"));
				sub = Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/Roboto-Regular.ttf"));
				body = Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/Roboto-Light.ttf"));
			} 
			catch (FontFormatException | IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NullPointerException e){
				System.out.println("File not Found at :");
			}
		};
	}
	private int height(JPanel p, double x){
	    Double d = p.getHeight() * (x/100);
		return d.intValue();
	}
	private int width(JPanel p, double x){
        Double d = p.getWidth() * (x/100);
        return d.intValue();
	}
}

