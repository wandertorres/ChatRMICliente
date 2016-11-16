import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Cliente {
	private static Scanner teclado = new Scanner(System.in);
	private static String nickname;
	
	public static void main(String[] args) throws IOException {
		try {
			ChatClient cliente = (ChatClient) new ChatClientImpl();
			ChatServer servidor = (ChatServer) Naming.lookup("//localhost/chat");
			entrar(cliente, servidor);
			//sair();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean nickDisponivel(ChatServer servidor) throws RemoteException {
		System.out.print("Nickname: ");
		nickname = teclado.nextLine();
		String[] nicks = servidor.naSessao();
		for(int i=0; i<nicks.length; i++)
			if(nicks[i].equals(nickname))
				return false;
		return true;
	}
	
	private static void entrar(ChatClient cliente, ChatServer servidor) throws RemoteException, MalformedURLException, NotBoundException {
		while(!(nickDisponivel(servidor)))
			System.out.println("Nick não disponível. Tente outro.");
		((ChatClientImpl) cliente).setNickname(nickname);
		((ChatClientImpl) cliente).setServidor(servidor);
		servidor.conectar(nickname, cliente);
		cliente.evento("Você entrou na sala como " +nickname);
		while(falar(servidor));
	}
	
	private static boolean falar(ChatServer servidor) throws RemoteException {
		String msg = teclado.nextLine();
		if(msg.equals("Sair"))
			return false;
		servidor.falar(nickname, msg);
		return true;
	}
}
