package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Nasluch implements Runnable {

	private BlockingQueue<Socket> kolejka;
	
	Nasluch(BlockingQueue<Socket> e)
	{
		this.kolejka = e;
	}
	
	@Override
	public void run() {

		try(ServerSocket server = new ServerSocket(7777))
		{
			while(true)
			{
				Socket socket = server.accept();
				kolejka.put(socket);
			}
		}
		catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
	}

}
