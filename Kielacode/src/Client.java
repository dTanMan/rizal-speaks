import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

	private int port;
	private String userId;
	private String botname;
	private String server;
	private String message;
	
	public Client(){
		this.server = "localhost";
		this.port = 1024;
		this.userId = "Andre";
		this.botname = "Pepe";
		
	}
	
	
	public String getBotname() {
		return botname;
	}


	public void setBotname(String botname) {
		this.botname = botname;
	}


	public String getServer() {
		return server;
	}


	public void setServer(String server) {
		this.server = server;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public String toString() {
		return "Client [port=" + port + ", userId=" + userId + ", botname=" + botname + ", server=" + server
				+ ", message=" + message + "]";
	}


	public String send(String message) throws IOException {
        final byte[] nul = new byte[]{0};
        byte[] out = new byte[0];
        
        toString();
        
        // using default encoding for string to bytes
        // Protocol is userid, followed by botname followed by message, all three being \0-terminated
        if (this.userId != null) out = append(out,this.userId.getBytes());
        out = append(out,nul);
        if (this.botname != null) out = append(out,this.botname.getBytes());
        out = append(out,nul);
        if (message != null) out = append(out,message.getBytes());
        out = append(out,nul);

        // send, receive, close, no persistent connection
        Socket socket = new Socket(this.server, this.port);
        OutputStream oStream = socket.getOutputStream();
        oStream.write(out);
        
        InputStream iStream = socket.getInputStream();
        byte[] in = new byte[0];
        byte[] buff = new byte[1024];
        int readBytes = iStream.read(buff);
        while (readBytes > 0) {
            in = append(in, buff, readBytes);
            readBytes = iStream.read(buff);
        }
        
        socket.close();
        
        return new String(in);
    }


    private static byte[] append(byte[] out, byte[] add) {
        return append(out, add, add.length);
    }
    // https://dl.dropboxusercontent.com/u/56550238/ChatClient.java
    private static byte[] append(byte[] out, byte[] add, int addLen) {
        byte[] ret = new byte[out.length + addLen];
        
        int ii = 0;
        for (; ii < out.length; ++ii) ret[ii] = out[ii];
        for (int jj = 0; jj < addLen; ++jj) ret[ii+jj] = add[jj];

        return ret;
    }
}
