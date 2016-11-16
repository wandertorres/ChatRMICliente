import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Cliente {
	public static void main(String[] args) throws IOException {
		try {
			ChatClient cliente = (ChatClient) new ChatClientImpl();
			ChatClientImpl.entrar(cliente);
			ChatClientImpl.sair(cliente);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
}