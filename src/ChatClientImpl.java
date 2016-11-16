import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatClientImpl extends UnicastRemoteObject implements ChatClient {
	private static final long serialVersionUID = 1L;
	private static ChatServer servidor;
	private static String nickname;
	private static Scanner teclado = new Scanner(System.in);
	
	protected ChatClientImpl() throws RemoteException {
		super();
	}

	public static void entrar(ChatClient cliente) throws MalformedURLException, RemoteException, NotBoundException {
		servidor = (ChatServer) Naming.lookup("//localhost/chat");
		while(!(nickDisponivel(servidor)))
			System.out.println("Nick não disponível. Tente outro.");
		servidor.conectar(nickname, cliente);
		limparTela();
		cliente.evento("Você entrou na sala como " +nickname+ "\n");
		while(falar(servidor));
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
	
	private static void limparTela() {
		for(int i=0; i<50; i++)
			System.out.println("\n");
	}
	
	private static boolean falar(ChatServer servidor) throws RemoteException {
		String msg = teclado.nextLine();
		if(msg.equals("Sair"))
			return false;
		servidor.falar(nickname, msg);
		return true;
	}
	
	public static void sair(ChatClient cliente) throws RemoteException, MalformedURLException, NotBoundException {
		servidor.desconectar(nickname, cliente);
		cliente.evento("Você saiu da sala.");
		System.exit(0);
	}
	
	@Override
	public void disse(String name, String msg) {
		System.out.println(" " +name+ ":" + "\n" + " - " +msg);
	}

	@Override
	public void evento(String msg) {
		System.out.println(msg);
	}
}