import javax.swing.JLayeredPane;

public class Face {
	private String eyes, body, mouth, others, bg;

	@Override
	public String toString() {
		return "Face [eyes=" + eyes + ", body=" + body + ", mouth=" + mouth + ", others=" + others + ", bg=" + bg + "]";
	}

	Face(){
		this.eyes = "0";
		this.body = "0";
		this.mouth = "0";
		this.others = "0";
		this.bg = "0";
	}
	
	public void update(String faceString){
		System.out.println("Updating face...");
		if(faceString.contains(" ")){
			System.out.println("Compiling face...");
			String[] draw = faceString.split(" ");
			this.eyes = (draw[0].equals("-") ? this.eyes : draw[0]);
			this.mouth = (draw[1].equals("-") ? this.eyes : draw[1]);
			this.body = (draw[2].equals("-") ? this.eyes : draw[2]);
			this.others = (draw[3].equals("-") ? this.eyes : draw[3]);
			this.bg = (draw[4].equals("-") ? this.eyes : draw[4]);
			System.out.println(toString());
		} else{
			System.out.println("Extracting face preset...");
			facePreset(faceString);
		}
	}

	public void facePreset(String faceString){
		switch (faceString) {
		case "angry":
			this.eyes = "2";
			this.mouth = "2";
			this.body = "0";
			this.others = "0";
			break;
		case "sad":
			this.eyes = "0";
			this.mouth = "1";
			this.body = "0";
			this.others = "1";
			break;
		case "shocked":
			this.eyes = "0";
			this.mouth = "3";
			this.body = "0";
			this.others = "0";
			break;
		case "happy":
			this.eyes = "0";
			this.mouth = "0";
			this.body = "0";
			this.others = "0";
			break;
		case "annoyed":
			this.eyes = "2";
			this.mouth = "2";
			this.body = "0";
			this.others = "0";
			break;
		default:
			break;
		}
	}

	public String getEyes() {
		return eyes;
	}

	public void setEyes(String eyes) {
		this.eyes = eyes;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getMouth() {
		return mouth;
	}

	public void setMouth(String mouth) {
		this.mouth = mouth;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getBg() {
		return bg;
	}

	public void setBg(String bg) {
		this.bg = bg;
	}
}


