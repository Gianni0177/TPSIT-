import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Servizio {
	
	//ATTRIBUTI
	private DatagramSocket socket;
	
	//COSTRUTTORE
	public Servizio(int porta) {
		try {
			socket = new DatagramSocket(porta);
			comunica();
		} 
		catch (SocketException e) {
			e.printStackTrace();
		}
	}

	//METODI
	public void comunica() {
		while(true) {
			byte mess[] = new byte[1024];
			DatagramPacket pacchettoRicevuto = new DatagramPacket(mess, mess.length);
			//in alternativa fare direttemente:
			//DatagramPacket pacchettoRicevuto = new DatagramPacket(new byte[1024], 1024);
			try {
				socket.receive(pacchettoRicevuto);
				String messaggio = new String(pacchettoRicevuto.getData());
				System.out.println("Ricevuto dal client: " + messaggio);
				String messRisposta = messaggio.toUpperCase();
				byte risp[] = messRisposta.getBytes();
				InetAddress ip = pacchettoRicevuto.getAddress();
				int porta = pacchettoRicevuto.getPort();
				DatagramPacket pacchettoRisposta = new DatagramPacket(risp, risp.length, ip, porta);
				//in alternativa fare direttemente:
				//DatagramPacket pacchettoRisposta = new DatagramPacket(messRisposta.getBytes(), messRisposta.getBytes().length, ip, porta);
				socket.send(pacchettoRisposta);
				System.out.println("Inviata risposta al client");
				//socket.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
}