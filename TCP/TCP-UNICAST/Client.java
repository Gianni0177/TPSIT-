import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	//ATTRIBUTI
	private Socket socketComunicazione;
	private DataInputStream canaleInput;
	private DataOutputStream canaleOutput;
	
	//COSTRUTTORE
	public Client(String ipServer, int portaServer) {
		//invio una richiesta di connessio al server, istanziando il socket
		//di comunicazione
		try {
			socketComunicazione = new Socket(ipServer, portaServer);
			canaleInput = new DataInputStream(socketComunicazione.getInputStream());
			canaleOutput = new DataOutputStream(socketComunicazione.getOutputStream());
			comunica();
		} 
		catch (UnknownHostException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void comunica() {
		Scanner tastiera = new Scanner(System.in);
		System.out.print("Inserisci messaggio da convertire? ");
		String msg = tastiera.nextLine();
		
		try {
			//invio il messaggio al server
			canaleOutput.writeUTF(msg);
			
			//ricevo la risposta dal server
			String risposta = canaleInput.readUTF();
			System.out.println("Ricevuta dal server la risposta: " + risposta);
			
			//chiudo la connessione con il server
			socketComunicazione.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		tastiera.close();
	}
	
}