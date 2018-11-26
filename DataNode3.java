import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.Naming;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import java.io.FileNotFoundException;

public class DataNode3 extends UnicastRemoteObject implements DataNodeIF {

	public DataNode3() throws RemoteException{
		super();
	}
	

	public String ReadArchive(String nameA, String datanode) throws IOException, FileNotFoundException {
		String currentDir = System.getProperty("user.dir");
		File file = new File(currentDir+"/Datanode"+datanode+"/"+nameA+".txt");
		
		if(file.exists()){
			String data = "";
	       	FileReader fileReader = new FileReader(file);
	   		BufferedReader reader = new BufferedReader(fileReader);
	   		String linha = null;
	   		
	   		while((linha = reader.readLine()) != null){
				data = data + linha + "\n";
			}
			fileReader.close();
			reader.close();
			return data;
        }
       	return null;
		
	}

	public boolean WriteArchive(String nameA, String text, boolean append, String datanode) throws IOException{
		String currentDir = System.getProperty("user.dir");
		File file = new File(currentDir+"/Datanode"+datanode+"/"+nameA+".txt");

        if(file.exists()){

	       	FileWriter arq = new FileWriter(currentDir+"/Datanode"+datanode+"/"+nameA+".txt",append);
	   		PrintWriter gravarArq = new PrintWriter(arq);
	   		gravarArq.println(text);
	   		gravarArq.close();
	   		arq.close();
	   		return true;
        }	
		return false;
	}

	public boolean CreateArchive(String nameA, String datanode) throws IOException{
		String currentDir = System.getProperty("user.dir");
		File file = new File(currentDir+"/Datanode"+datanode+"/"+nameA+".txt");

        if(!file.exists()){

	       	FileWriter arq = new FileWriter(currentDir+"/Datanode"+datanode+"/"+nameA+".txt");
	       	arq.close();
	   		return true;

        }	
		return false;
	}

	public boolean DeleteArchive(String nameA, String datanode) throws IOException{
		String currentDir = System.getProperty("user.dir");
		File file = new File(currentDir+"/Datanode"+datanode+"/"+nameA+".txt");

        if(file.exists()){
	       	if(file.delete()){
	       		return true;	
	       	}   		

        }
		return false;
	}
	
	
	public static void main(String args[])
	{
		try {
			
            DataNode3 servidor = new DataNode3();
            Naming.bind("Datanode3", servidor);
            System.err.println("Datanode3 pronto para uso.");
            
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
	}




}