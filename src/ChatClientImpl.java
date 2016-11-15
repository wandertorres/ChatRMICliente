import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatClientImpl extends UnicastRemoteObject implements ChatClient {
	protected ChatClientImpl() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;
	private ChatServer servidor;
	private String nickname;

	@Override
	public void disse(String name, String msg) throws RemoteException {
		System.out.println(" " +name+ ":" + "\n" + " - " +msg);
	}

	@Override
	public void evento(String msg) throws NotBoundException, MalformedURLException, RemoteException {
		BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
		
		switch(msg) {
		case "\\conectar":
			servidor = (ChatServer) Naming.lookup("//localhost/chat");
			this.evento("\\escolhernick");
			break;
			
		case "\\escolhernick":
			System.out.print("Nickname: ");
			try {
				nickname = teclado.readLine();
				this.evento("\\limpartela");
				servidor.conectar(nickname, this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "\\nickinvalido":
			System.out.println("Nick inválido. Tente outro.");
			this.evento("\\escolhernick");
			break;
			
		case "\\entrou":
			System.out.println("Você entrou na sala como " +nickname+ ".");
			servidor.falar(nickname, "Entrou...");
			this.evento("\\falar");
			break;
			
		case "\\falar":
			try {
				msg = teclado.readLine();
				if(msg.contains("\\sair")) {
					this.evento("\\sair");
				}else if(msg.contains("\\listar")) {
					this.evento("\\listar");
				}else if(msg.contains("\\para")) {
					String para = msg.substring(7);
					msg = teclado.readLine();
					servidor.falarPara(nickname, para, "[PV] " +msg);
					this.evento("\\falar");
				}else {
					servidor.falar(nickname, msg);
					this.evento("\\falar");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			
		case "\\sair":
			servidor.falar(nickname, "Saiu...");
			servidor.desconectar(nickname, this);
			System.out.println("Você saiu da sala...");
			break;
			
		case "\\listar":
			String lista[] = servidor.naSessao();
			System.out.println("\nPessoas na sala:");
			for(String usuario : lista)
				System.out.println(" " +usuario);
			System.out.println("");
			this.evento("\\falar");
			break;
			
		case "\\limpartela":
			for(int i=0; i<50; i++)
				System.out.println("");
			break;
		}
	}
}