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
import java.lang.*;

public class NameNode extends UnicastRemoteObject implements NameNodeIF {

    public NameNode() throws RemoteException{
        super();
    }
    

    public int GetDataNode(String nameA) {
        

        Integer data = hashCode(nameA);
        data = Math.abs(data) + 1;
        return data;
        
    }

    public int hashCode(String str){
        return str.hashCode()%3;
    }
    
    
    public static void main(String args[])
    {
        try {
            
            NameNode servidor = new NameNode();
            Naming.bind("Namenode", servidor);
            System.err.println("NameNode pronto para uso.");
            
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }




}