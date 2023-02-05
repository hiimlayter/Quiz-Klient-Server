package application;

import java.io.Serializable;

public class Answer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String autor;
	private String odpowiedz;
	
	public Answer(String autor, String odpowiedz) {
		this.autor = autor;
		this.odpowiedz = odpowiedz;
	}
	public String getAutor() {
		return autor;
	}

	public String getOdpowiedz() {
		return odpowiedz;
	}
}
