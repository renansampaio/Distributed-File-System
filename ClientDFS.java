import java.rmi.Naming;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientDFS{

	public static void main(String args[])
	{
		String ope = null;
		boolean invalid = false;
		Scanner sc = new Scanner(System.in);
		String text = null;
		String nameArchive = null;
		boolean append = true;
		String answer = null;
		boolean find = false;
		String data = null;
		
		try
		{
			ProxyIF obj = (ProxyIF) Naming.lookup("//"+""+"/Proxy");
			do
			{
				System.out.println("Qual funcao voce quer executar?(R -> READ / W -> WRITE / C -> CREATE / D -> DELETE):");
				ope = sc.nextLine();
				if(ope.equalsIgnoreCase("R")||ope.equalsIgnoreCase("W")||ope.equalsIgnoreCase("C")||ope.equalsIgnoreCase("D"))
				{
					do{
						try{
							System.out.println("Digite o nome do arquivo:");
							
							if(invalid == true){
								sc.nextLine();
							}
							nameArchive = sc.nextLine();
							invalid = false;
						}catch(InputMismatchException e)
						{
							invalid = true;
							System.out.println("\nValor invalido, tente novamente.\n");
						}
					}while(invalid == true);

					if(ope.equalsIgnoreCase("R"))

					{
						data = obj.Read(nameArchive);
						if(data == null){
							System.out.println("\nArquivo nao encontrado. Tente Novamente.\n");
						}else
							System.out.println(data);
						
					}

					else if(ope.equalsIgnoreCase("W"))
					{
						do{
							try{
								System.out.println("Digite o texto a ser escrito:");
								if(invalid == true){
									sc.nextLine();
								}
								text = sc.nextLine();
								invalid = false;
							}
							catch(InputMismatchException e){
								invalid = true;
								System.out.println("\nValor invalido, tente novamente.\n");
							}
						}while(invalid==true);

						do{
							try{
								boolean loop = true;
								int i = 0;
								while(loop){
									if(i == 0){
										System.out.println("Sobrescrever arquivo?  ->  S/N :");
									}
									if(invalid == true){
										sc.nextLine();
									}
									answer = sc.nextLine();
									invalid = false;
									if(answer.equalsIgnoreCase("S") || answer.equalsIgnoreCase("N")){
										loop = false;
									}else {
										System.out.println("Digite um caractere valido  ->  S/N ");
										i = i+1;
									 }
								}
							}
							catch(InputMismatchException e){
								invalid = true;
								System.out.println("\nValor invalido, tente novamente.\n");
							}
						}while(invalid==true);

						if(answer.equalsIgnoreCase("S")){
							append = false;
						}else if(answer.equalsIgnoreCase("N")){
							append = true;
						}

						find = obj.Write(nameArchive,text,append);
						if (find == false){
							System.out.println("\nArquivo nao encontrado, tente novamente.\n");
						}else 
							System.out.println("\nArquivo modificado com sucesso.\n");

					}
					else if(ope.equalsIgnoreCase("C"))
					{
						find = obj.Create(nameArchive);
						if (find == false){
							System.out.println("\nFalha ao criar arquivo.\n");
						}else
							System.out.println("\nArquivo criado com sucesso.\n");
					}
					else if(ope.equalsIgnoreCase("D"))
					{
						find = obj.Delete(nameArchive);
						if (find == false){
							System.out.println("\nArquivo nao encontrado, tente novamente.\n");
						}else
							System.out.println("\nArquivo deletado com sucesso.\n");
					}
				}
				else if(!ope.equalsIgnoreCase("exit"))
				{
					System.out.println("\nComando invalido, por favor, verifique os comandos validos e tente novamente.\n");
				}
			}while(!ope.equals("exit"));

		}catch(Exception e)
		{
			System.out.println("FALHA NA CONEXAO COM O SERVIDOR.");
			System.out.println(e.getMessage());
		}

		System.out.println("Fim da execucao do cliente.");

		sc.close();
	}

}