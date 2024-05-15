import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	//ATTRIBUTI
	private DatagramSocket socket;
	private String ipServer;
	private int portaServer;

	//COSTRUTTORE
	public Client(String ipServer, int portaServer) {
		try {
			socket = new DatagramSocket();
			socket.setSoTimeout(60000);
			this.ipServer = ipServer;
			this.portaServer = portaServer;
			comunica();
		} 
		catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	//METODI
	public void comunica() {
		Scanner tastiera = new Scanner(System.in);
		System.out.println("Inserisci messaggio da inviare a server: ");
		String msg = tastiera.nextLine();
		try {
			//trasformo indirizzo ip da stringa a oggetto InetAddress
			InetAddress ip = InetAddress.getByName(ipServer);
			//trasformo la stringa del messaggio in una sequenza di byte
			byte mess[] = msg.getBytes();
			//compongo il datagramma da inviare al server con le info necessarie
			DatagramPacket pacchetto = new DatagramPacket(mess, mess.length, ip, portaServer);
			//invio datagramma attraverso socket
			socket.send(pacchetto);
			
			//predispongo un buffer vuoto (array di byte) lungo ad esempio 1024
			byte messRisp[] = new byte[1024];
			//predispongo un datagramma per la ricezione del messaggio con all'interno
			//il buffer vuoto
			DatagramPacket risposta = new DatagramPacket(messRisp, messRisp.length);
			//attendo la ricezione del datagramma di risposta
			socket.receive(risposta);
			
			//estrapolo il messaggio dal datagramma e lo trasformo in una stringa
			String risp = new String(risposta.getData());
			//stampo a video la risposta ricevuta
			System.out.println("Ricevuta risposta: " + risp);
			//se la comunicazione è terminata, chiudo il socket
			socket.close();
		} 
		catch (UnknownHostException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		tastiera.close();
	}
	
	
}