package client;

import java.net.Socket;

public class CAHClient {

	public CAHClient(){
		HostAndPortGUI hapGUI = new HostAndPortGUI();
		Socket socket = hapGUI.getSocket();
    	new ClientFrame(socket);   
	}
	
	public static void main(String [] args){
		new CAHClient();
	}
}
