import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Response {
	private String message;
	private Face face;
	private JLayeredPane p;
	private JPanel facePanel, mouthPanel, eyesPanel, bodyPanel, othersPanel, bgPanel;
	private ImageIcon bodyPic, mouthPic, eyesPic, othersPic, bgPic;
	
	
// Initialize Response class with a default face in the layered pane
	Response(JLayeredPane p){
		this.p = p;
		this.face = new Face();
		bodyPanel = new JPanel();
		bodyPanel.setBounds(p.getBounds());
		bodyPanel.setOpaque(false);
		
		facePanel = new JPanel();
		facePanel.setBounds(p.getBounds());
		facePanel.setOpaque(false);
		
		mouthPanel = new JPanel();
		mouthPanel.setBounds(p.getBounds());
		mouthPanel.setOpaque(false);
		
		eyesPanel = new JPanel();
		eyesPanel.setBounds(p.getBounds());
		eyesPanel.setOpaque(false);

		othersPanel = new JPanel();
		othersPanel.setBounds(p.getBounds());
		othersPanel.setOpaque(false);
		
		bgPanel = new JPanel();
		bgPanel.setBounds(p.getBounds());
		bgPanel.setOpaque(false);
		
		bodyPic = new ImageIcon(Main.class.getResource("images/body_"+ face.getBody() +".png"));
		JLabel body = new JLabel("", bodyPic, JLabel.CENTER);
		
		bodyPanel.add(body);
		
		ImageIcon headPic = new ImageIcon(Main.class.getResource("images/head_0.png"));
		JLabel head = new JLabel("", headPic, JLabel.CENTER);
		
		facePanel.add(head);
		
		mouthPic = new ImageIcon(Main.class.getResource("images/mouth_"+ face.getMouth() +".png"));
		JLabel mouth = new JLabel("", mouthPic, JLabel.CENTER);
		
		mouthPanel.add(mouth);
		
		eyesPic = new ImageIcon(Main.class.getResource("images/eyes_"+ face.getEyes() +".png"));
		JLabel eyes = new JLabel("", eyesPic, JLabel.CENTER);
		
		eyesPanel.add(eyes);
		
		bgPic = new ImageIcon(Main.class.getResource("images/bg_"+ face.getBg() +".png"));
		JLabel bg = new JLabel("", eyesPic, JLabel.CENTER);
		
		bgPanel.add(bg);
		othersPic = new ImageIcon(Main.class.getResource("images/others_"+ face.getOthers() +".png"));
		JLabel others = new JLabel("", othersPic, JLabel.CENTER);
		othersPanel.add(others);

		p.add(bgPanel, new Integer(0),0);
		p.add(bodyPanel, new Integer(1),0);
		p.add(facePanel, new Integer(2),0);
		p.add(mouthPanel, new Integer(3),0);
		p.add(eyesPanel, new Integer(5),0);
		p.add(othersPanel, new Integer(4),0);
	}

	public Face getFace() {
		return face;
	}
	
	public JLayeredPane getFacePanel() {
		return p;
	}


	public void setFace(Face f) {
		this.face = f;
	}

	public String update(String message){
		if (message.equals("") || message == null) return "Server is down. Please contact maintenance. Sorry for the inconvenience.";
		if(message.charAt(0) == '!'){
			System.out.println("Processing text: " + message);
			face.update(message.substring(1, message.substring(1).indexOf('!')+1));
			p.removeAll();
			try{
			bodyPic = new ImageIcon(Main.class.getResource("images/body_"+ face.getBody() +".png"));
			eyesPic = new ImageIcon(Main.class.getResource("images/eyes_"+ face.getEyes() +".png"));
			mouthPic = new ImageIcon(Main.class.getResource("images/mouth_"+ face.getMouth() +".png"));
			if(!face.getOthers().equals("0")){
				othersPic = new ImageIcon(Main.class.getResource("images/others_"+ face.getOthers() +".png"));
			}
			bgPic = new ImageIcon(Main.class.getResource("images/others_"+ face.getOthers() +".png"));
			} catch (NullPointerException e){
				System.out.println("WARNING! Some image files not found. Reverting to default face...");
				defaultFace();
			}
			p.repaint();
			return this.message = message.substring(message.substring(1).indexOf('!')+3);
		} else return message;
	}

	private void defaultFace() {
		bodyPic = new ImageIcon(Main.class.getResource("images/body_0.png"));
		eyesPic = new ImageIcon(Main.class.getResource("images/eyes_0.png"));
		mouthPic = new ImageIcon(Main.class.getResource("images/mouth_0.png"));
		if(!face.getOthers().equals("0")){
			othersPic = new ImageIcon(Main.class.getResource("images/others_0.png"));
		}
		bgPic = new ImageIcon(Main.class.getResource("images/others_0.png"));
	}
}
