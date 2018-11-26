import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.IOException;

public interface ProxyIF extends Remote {

	public String Read(String nameA) throws RemoteException;

	public boolean Write(String nameA, String text, boolean append) throws RemoteException;

	public boolean Create(String nameA) throws RemoteException;

	public boolean Delete(String nameA) throws RemoteException;
}