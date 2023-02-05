package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class SerwerQuizController {
	
	private BlockingQueue<Socket> kolejka;
	private Nasluch nas;
	
	private int counter;
	private int ilepytan;
	private Socket socket;
	private List<Pytanie> pytania;
	
	@FXML
	private TextArea info;
	@FXML
	private Button startButton;
	
	public SerwerQuizController()
	{
		
	}
	
	@FXML
	public void initialize()
	{
		pytania = new ArrayList<Pytanie>();
		if(readQuiz())
		{
			info.appendText("Pytania za³adowane poprawnie\nWciœnij przycisk start aby rozpocz¹æ\n");
		}
	}
	
	public void onStartClick()
	{
		kolejka = new ArrayBlockingQueue<Socket>(5);
		
		nas = new Nasluch(kolejka);
		new Thread(nas).start();
		Thread kon = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				counter = 0;
				
				info.appendText(pytania.get(counter).getPytanie()+"\n");
				
				try 
				{
					while(counter<ilepytan)
					{
						socket = kolejka.take();
						ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
						Answer odp = ((Answer) in.readObject());
						
						if(pytania.get(counter).sprawdzOdpowiedz(odp.getOdpowiedz()))
						{
							info.appendText(odp.getAutor() + "(" + socket.getInetAddress().getHostAddress() + ")odpowiedzia³ poprawnie!\n");
							counter++;
							if(counter<ilepytan)
							{
								info.appendText(pytania.get(counter).getPytanie()+"\n");
							}
							kolejka.clear();
						}
						else
						{
							info.appendText("B³êdna odpowiedz \n");
						}
					}
				}
				catch(InterruptedException | IOException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
				
				info.appendText("Koniec quizu!");
				
			}
		});
		
		kon.start();
	}
	
	private Boolean readQuiz()
	{
		try( Stream<String> lines = Files.lines(Paths.get("quiz.txt"))){
			
			lines.forEach(line -> pytania.add(new Pytanie(line.split("-")[0],line.split("-")[1])));
			
		} catch (IOException e) {
			info.appendText("\nNie znaleziono pliku");
			return false;
		}
		
		ilepytan = pytania.size();
		return true;
	}
	
}
