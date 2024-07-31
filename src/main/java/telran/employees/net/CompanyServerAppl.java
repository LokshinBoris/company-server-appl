package telran.employees.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import telran.employees.Company;
import telran.employees.CompanyMapsImpl;
import telran.io.Persistable;
import telran.net.*;

public class CompanyServerAppl {

	private static final String FILE_NAME = "employeesTest.data";
	private static final int PORT = 5000;

	public static void main(String[] args) throws IOException, InterruptedException 
	{
		Company company = new CompanyMapsImpl();
		try
		{
			((Persistable) company).restore(FILE_NAME);
		}
		catch(Exception e)
		{
		
		}
		Protocol protocol=new CompanyProtocol(company);
		TcpServer tcpServer = new TcpServer(protocol,PORT);
		//FIXME need to start TCPServer as a thread
		tcpServer.start();
		System.out.println("Enter Sutdown to stoping server\n");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String inputStr=null;
        while( inputStr==null )
        {
        	inputStr = reader.readLine();
        	if(inputStr.compareTo("Shutdown")!=0) inputStr=null;
        }
     
        ((Persistable) company).save(FILE_NAME);
        tcpServer.shutdown();
	     
		// TODO
		// cycle with asking a user to enter shutdown for exit from the server
		// regular while cycle with no using cli-view
		// by entering "shutdown" you should call method shutdown of the TcpServer
		// after shutdown you should perform saving the data into the file
	}

}
