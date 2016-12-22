import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class FaceFactory {

	private ArrayList<String> display;
	
	public FaceFactory(String body, String face, String mouth, String eyes){
		display = new ArrayList<>();
		display.add(0, body);
		display.add(1, face);
		display.add(2, mouth);
		display.add(3, eyes);
		display.add(4, "null");
	}

	@Override
	public String toString() {
		String ret = "";
		for( String i : display){
			ret += i + " ";
		}
		return ret;
	}

	public String update(String message){
		if (message.equals("") || message == null) return "Server is down. Please contact maintenance. Sorry for the inconvenience.";
		if(message.charAt(0) == '!'){
			String[] draw = message.substring(1, message.substring(1).indexOf('!')+1).split(" ");
			for (int i = 0; i < draw.length ; i++){
				if(!draw[i].equals("null")){
					System.out.println("draw[" + i + "] = "+ draw[i]);
					display.set(i, draw[i]);
				}
			}
			toString();
			return message.substring(message.substring(1).indexOf('!')+3);
		} else
		return message;
	}

	public JLayeredPane display(JLayeredPane p){
		System.out.println(toString());
		JPanel bodyPanel = new JPanel();
		bodyPanel.setBounds(p.getBounds());
		bodyPanel.setOpaque(false);
		
		JPanel facePanel = new JPanel();
		facePanel.setBounds(p.getBounds());
		facePanel.setOpaque(false);
		
		JPanel mouthPanel = new JPanel();
		mouthPanel.setBounds(p.getBounds());
		mouthPanel.setOpaque(false);
		
		JPanel eyesPanel = new JPanel();
		eyesPanel.setBounds(p.getBounds());
		eyesPanel.setOpaque(false);

		JPanel othersPanel = new JPanel();
		othersPanel.setBounds(p.getBounds());
		othersPanel.setOpaque(false);
		
		ImageIcon bodyPic = new ImageIcon(Main.class.getResource("body_"+display.get(0)+".png"));
		JLabel body = new JLabel("", bodyPic, JLabel.CENTER);
		
		bodyPanel.add(body);
		
		ImageIcon facePic = new ImageIcon(Main.class.getResource("face_"+display.get(1)+".png"));
		JLabel face = new JLabel("", facePic, JLabel.CENTER);
		
		facePanel.add(face);
		
		ImageIcon mouthPic = new ImageIcon(Main.class.getResource("mouth_"+display.get(2)+".png"));
		JLabel mouth = new JLabel("", mouthPic, JLabel.CENTER);
		
		mouthPanel.add(mouth);
		
		ImageIcon eyesPic = new ImageIcon(Main.class.getResource("eyes_"+display.get(3)+".png"));
		JLabel eyes = new JLabel("", eyesPic, JLabel.CENTER);
		
		eyesPanel.add(eyes);
		
		if(!display.get(4).equals("null")){
			ImageIcon othersPic = new ImageIcon(Main.class.getResource("others_"+display.get(4)+".png"));
			JLabel others = new JLabel("", othersPic, JLabel.CENTER);
			
			othersPanel.add(others);
		}
		
		p.add(bodyPanel, new Integer(0),0);
		p.add(facePanel, new Integer(1),0);
		p.add(mouthPanel, new Integer(2),0);
		p.add(eyesPanel, new Integer(4),0);
		p.add(othersPanel, new Integer(3),0);
		
		
		
		return p;		
	}
	
}


