import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadComunicazione implements Runnable {

	//ATTRIBUTI
	private Thread t;
	private Socket socketComunicazione;
	private DataInputStream canaleInput;
	private DataOutputStream canaleOutput;
	
	//COSTRUTTORE
	public ThreadComunicazione(Socket s) {
		socketComunicazione = s;
		t = new Thread(this);
		t.start();
	}
	
	//METODI
	public void run() {
		try {
			canaleInput = new DataInputStream(socketComunicazione.getInputStream());
			canaleOutput = new DataOutputStream(socketComunicazione.getOutputStream());
			String msg = canaleInput.readUTF();
			System.out.println("Ricevuto da " + socketComunicazione.getInetAddress() + 
								" il messaggio " + msg);
			String risp = msg.toUpperCase();
			canaleOutput.writeUTF(risp);
			System.out.println("Risposta inviata!");
			socketComunicazione.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}