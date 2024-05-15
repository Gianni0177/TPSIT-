import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servizio {
	
	//ATTRIBUTI
	private String nomeServizio;
	private ServerSocket socketAscolto;
	private Socket socketComunicazione;
	private DataInputStream canaleInput;
	private DataOutputStream canaleOutput;
	
	//COSTRUTTORE
	public Servizio(String nome, int portaAscolto) {
		try {
			this.nomeServizio = nome;
			//mettiamo il server in ascolto
			socketAscolto = new ServerSocket(portaAscolto);//mette il server in ascoloto sulla porta specificata
			socketComunicazione = socketAscolto.accept();  //accetta UNA richiesta di connessione e
														   //genera il socket di comunicazione
			//una volta instaurata la connessione, recupero i due canali di comunicazione
			canaleInput = new DataInputStream(socketComunicazione.getInputStream());
			canaleOutput = new DataOutputStream(socketComunicazione.getOutputStream());
			//completata la fase di connessione, inizio la comunicazione con client
			paperino();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	//METODI
	public void paperino() {
		try {
			//ricevo msg da client
			String msgRicevuto = canaleInput.readUTF();
			System.out.println("Ricevuto dal client il messaggio " + msgRicevuto);
			//elaboro risposta da inviare a client
			String msgRisposta = msgRicevuto.toUpperCase();
			//invio risposta a client
			canaleOutput.writeUTF(msgRisposta);
			System.out.println("Risposta inviata a client");
			//quando fisco di comunicare, chiudo la connessione con client
			socketComunicazione.close();
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}