import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.IOException;
import java.io.FileNotFoundException;

public interface NameNodeIF extends Remote {

	public int GetDataNode(String nameA) throws RemoteException;
}