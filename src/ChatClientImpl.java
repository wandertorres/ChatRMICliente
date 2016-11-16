import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatClientImpl extends UnicastRemoteObject implements ChatClient {
	protected ChatClientImpl() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;
	private ChatServer servidor;
	private String nickname;
	
	public ChatServer getServidor() {
		return servidor;
	}

	public void setServidor(ChatServer servidor) {
		this.servidor = servidor;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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