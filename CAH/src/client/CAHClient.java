/*
 * Cards Against Humanity
 * 1. Jamal Moon (­jamalmoo@usc.edu)
 * 2. Corey Chen ­ (coreyche@usc.edu)
 * 3. Peter Lu ­ (peterjlu@usc.edu)
 * 4. Justin Ku ­ (kujustin@usc.edu)
 * 5. Anush Kadoyan  (kadoyan@usc.edu)
 * 6. Vincent Espino ­ (vlespino@usc.edu)
 */

package client;

import java.io.BufferedOutputStream;
import java.io.ObjectOutputStream;
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
