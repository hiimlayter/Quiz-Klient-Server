package application;

public class Pytanie {
	
	private String pytanie;
	private String odpowiedz;
	
	Pytanie(String pytanie, String odpowiedz)
	{
		this.pytanie = pytanie;
		this.odpowiedz = odpowiedz;
	}

	public Boolean sprawdzOdpowiedz(String n)
	{
		if(n.equals(this.odpowiedz)) return true;
		return false;
	}
	
	public String getPytanie()
	{
		return pytanie;
	}
}
