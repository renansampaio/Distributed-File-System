import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.IOException;
import java.io.FileNotFoundException;

public interface DataNodeIF extends Remote {

	public String ReadArchive(String nameA, String datanode) throws RemoteException, IOException, FileNotFoundException;

	public boolean WriteArchive(String nameA, String text, boolean append, String datanode) throws RemoteException, IOException;

	public boolean CreateArchive(String nameA, String datanode) throws RemoteException, IOException;

	public boolean DeleteArchive(String nameA, String datanode) throws RemoteException, IOException;
}