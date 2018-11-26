import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.Naming;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Proxy extends UnicastRemoteObject implements ProxyIF
{
	
	String data = null;
	String data2 = null;
	int datanode = 0;
	int replica = 0;

	public Proxy() throws RemoteException{
		super();
	}
	
	public String Read(String nameA){
	try{
		System.out.println("- Solicitação de leitura do arquivo "+nameA+".txt");		

		NameNodeIF name = (NameNodeIF) Naming.lookup("//"+""+"/Namenode");
		datanode = name.GetDataNode(nameA);
		replica = name.GetDataNode(nameA+"1");

		String datanode_str = Integer.toString(datanode);
		String replica_str = Integer.toString(replica);

		DataNodeIF obj = (DataNodeIF) Naming.lookup("//"+""+"/Datanode"+datanode);
		DataNodeIF repl = (DataNodeIF) Naming.lookup("//"+""+"/Datanode"+replica);

		data = obj.ReadArchive(nameA, datanode_str);
		data2 = repl.ReadArchive(nameA, replica_str);

		if(data != null){
			System.out.println("- Arquivo "+nameA+".txt"+" encontrado no Datanode "+datanode);
		}else if (data2!=null){
			System.out.println("- Arquivo "+nameA+".txt"+" encontrado no Datanode "+replica);
		}else{
			System.out.println("- Arquivo "+nameA+".txt"+" não encontrado.");
		}
		
		}catch(Exception e){
			System.out.println("FALHA NA CONEXÃO COM O SERVIDOR.");
			System.out.println(e.getMessage());
		}
		if(data!=null){
			return data;
		}else if(data2!=null){
			return data2;
		}else{
			return null;
		}

	}

	public boolean Write(String nameA, String text, boolean append){
	boolean find_datanode = false;
	boolean find_replica = false;
	try{
		System.out.println("- Solicitação de escrita no arquivo "+nameA+".txt");
		NameNodeIF name = (NameNodeIF) Naming.lookup("//"+""+"/Namenode");
		datanode = name.GetDataNode(nameA);
		replica = name.GetDataNode(nameA+"1");		
		if(datanode == replica){
			if(replica == 3){
				replica = 1;
			}else{
				replica = replica + 1;
			}
		}
		String replica_str = Integer.toString(replica);
		String datanode_str = Integer.toString(datanode);
		DataNodeIF obj = (DataNodeIF) Naming.lookup("//"+""+"/Datanode"+datanode);
		find_datanode = obj.WriteArchive(nameA, text, append, datanode_str);
		DataNodeIF repl = (DataNodeIF) Naming.lookup("//"+""+"/Datanode"+replica);
		find_replica = repl.WriteArchive(nameA, text, append, replica_str);
		if(find_datanode && find_replica){
			System.out.println("- Arquivo "+nameA+".txt"+" atualizado no Datanode "+datanode+" e sua replica no Datanode "+replica);
		}else{
			System.out.println("- Arquivo "+nameA+".txt"+" não encontrado.");
		}
		}catch(Exception e){
			System.out.println("FALHA NA CONEXÃO COM O SERVIDOR.");
			System.out.println(e.getMessage());
		}
		return find_datanode;
	}

	public boolean Create(String nameA){
	boolean find_datanode = false;
	boolean find_replica = false;
	try{
		System.out.println("- Solicitação de criacao do arquivo "+nameA+".txt");
		NameNodeIF name = (NameNodeIF) Naming.lookup("//"+""+"/Namenode");
		datanode = name.GetDataNode(nameA);
		replica = name.GetDataNode(nameA+"1");
		if(datanode == replica){
			if(replica == 3){
				replica = 1;
			}else{
				replica = replica + 1;
			}
		}
		String replica_str = Integer.toString(replica);
		String datanode_str = Integer.toString(datanode);
		DataNodeIF obj = (DataNodeIF) Naming.lookup("//"+""+"/Datanode"+datanode);
		find_datanode = obj.CreateArchive(nameA, datanode_str);
		DataNodeIF repl = (DataNodeIF) Naming.lookup("//"+""+"/Datanode"+replica);
		find_replica = repl.CreateArchive(nameA, replica_str);
		if(find_datanode && find_replica){
			System.out.println("- Arquivo "+nameA+".txt"+" criado no Datanode "+datanode+" e sua replica no Datanode "+replica);
		}else{
			System.out.println("- O arquivo "+nameA+".txt"+" já existe no Datanode "+datanode);
		}
		}catch(Exception e){
			System.out.println("FALHA NA CONEXÃO COM O SERVIDOR.");
			System.out.println(e.getMessage());
		}
		return find_datanode;
	}

	public boolean Delete(String nameA){
	boolean find_datanode = false;
	boolean find_replica = false;
	try{
		System.out.println("- Solicitação de delete do arquivo "+nameA+".txt");
		NameNodeIF name = (NameNodeIF) Naming.lookup("//"+""+"/Namenode");
		datanode = name.GetDataNode(nameA);
		replica = name.GetDataNode(nameA+"1");
		if(datanode == replica){
			if(replica == 3){
				replica = 1;
			}else{
				replica = replica + 1;
			}
		}
		String replica_str = Integer.toString(replica);
		String datanode_str = Integer.toString(datanode);
		DataNodeIF obj = (DataNodeIF) Naming.lookup("//"+""+"/Datanode"+datanode);			
		find_datanode = obj.DeleteArchive(nameA,datanode_str);
		DataNodeIF repl = (DataNodeIF) Naming.lookup("//"+""+"/Datanode"+replica);
		find_replica = repl.DeleteArchive(nameA, replica_str);
		if(find_datanode && find_replica){
			System.out.println("- Arquivo "+nameA+".txt"+" deletado do Datanode "+datanode);
		}else{
			System.out.println("- Arquivo "+nameA+".txt"+" não encontrado.");
		}
		}catch(Exception e){
			System.out.println("FALHA NA CONEXÃO COM O SERVIDOR.");
			System.out.println(e.getMessage());
		}
		return find_datanode;
	}

  	public int hashCode(String str){
    	return str.hashCode()%3;
  	}


	public static void main(String args[])
	{
		try {
			
            Proxy servidor = new Proxy();
            Naming.bind("Proxy", servidor);
            System.err.println("Proxy pronto para uso.");
            
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
	}

}