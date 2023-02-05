package application;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ClientController {
	
	@FXML
	private Button send;
	
	@FXML
	private TextField nick,answer;
	
	InetAddress adres;
	
	@FXML
	private void initialize() {
		
		try
		{
			adres = InetAddress.getByName("localhost");
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void onSendClick()
	{
		try(Socket socket = new Socket(adres, 7777))
		{
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(new Answer(nick.getText(),answer.getText()));
			answer.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
