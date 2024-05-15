import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servizio {
	
	//ATTRIBUTI
	private ServerSocket socketAscolto;
	private Socket socketComunicazione;
	
	//COSTRUTTORE
	public Servizio(int portaAscolto) {
		try {
			socketAscolto = new ServerSocket(portaAscolto);
			while(true) {
				socketComunicazione = socketAscolto.accept();
				new ThreadComunicazione(socketComunicazione);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}