import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * La classe Salvataggio gestisce le operazioni di scrittura e lettura dei file .ser
 * e fornisce metodi di supporto con il quale interagire nel sistema di cartelle
 * 
 * @authors  Matteo e Francesco, i Leggendari
 * @since   0.12
 */
public final class Salvataggio<Generico> 
{

	public Salvataggio() {}
	
	/**
	 * Salva l'oggetto specificato in un file seriale nel percorso specificato.
	 * 
	 * @param item l'oggetto da salvare
	 * @param cartella la cartella in cui salvare il file
	 * @param nomefile il nome del file (senza estensione .ser)
	 */
	public void salvaItem(Generico item, String cartella, String nomefile)
	{	
		try {
		FileOutputStream fileOut = new FileOutputStream(cartella+"/"+nomefile+".ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(item);
		out.close();
		fileOut.close();}
		catch(IOException e) {System.out.println("Non so come nè perchè, ma hai sbagliato a scrivere il path: "+cartella+"/"+nomefile+".ser");}

	}
	
	/**
	 * Carica e restituisce l'oggetto salvato dal percorso specificato.
	 * 
	 * @param target il percorso del file da cui caricare l'oggetto
	 * @return l'oggetto deserializzato, oppure null se si verifica un errore durante il caricamento
	 */
	@SuppressWarnings({ "finally", "unchecked" })
	public Generico getItem(String target)
	{	
		Generico item = null;
		try{
		FileInputStream fileIn = new FileInputStream(target);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		item = (Generico) in.readObject();
		in.close();
		fileIn.close();
		}
		catch(IOException e){System.out.println("Problemi con il path fornito: "+target);}
		finally {return item;}
	}
	
	/**
	 * Legge un oggetto di tipo Generico da un file fornito.
	 *
	 * @param target Il file da cui leggere l'oggetto.
	 * @return L'oggetto di tipo Generico letto dal file, oppure null se si sono verificati problemi durante la lettura.
	 */
	@SuppressWarnings({ "unchecked", "finally" })
	public Generico getItem(File target)
	{
		Generico item = null;
		try{
		FileInputStream fileIn = new FileInputStream(target);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		item = (Generico) in.readObject();
		in.close();
		fileIn.close();
		}
		catch(IOException e){System.out.println("Problemi con il file fornito: "+target);}
		finally {return item;}
	}
	
	/**
	 * Cancella il file situato nel percorso specificato.
	 *
	 * @param pathtarget Il percorso del file da cancellare.
	 */
	public void deleteItem(String pathtarget) 
		{
		try {
		new File(pathtarget).delete();
		}
		catch (Exception f) {System.out.println("Problemi con il path del file che si vuole cancellare: "+pathtarget);}
		}
	
	/**
	 * Cancella il file specificato.
	 *
	 * @param filetarget Il file da cancellare.
	 */
	public void deleteItem(File filetarget) 
		{
		try {
		filetarget.delete();
		}
		catch (Exception f) {System.out.println("Problemi con il file che si vuole cancellare. Path: "+filetarget.getPath());}
		}
	
	/**
	 * Restituisce un array di File contenuti nella cartella specificata.
	 *
	 * @param Folder Il percorso della cartella di cui si vuole ottenere il contenuto.
	 * @return Un array di oggetti File contenenti i file nella cartella specificata, o null se la cartella è vuota o non esiste.
	 */
	public File[] getFileAt(String Folder)
		{
		File test= new File(Folder);
		File[] array = test.listFiles();
		return array;
		}
	
	/**
	 * Genera e restituisce un timestamp in formato long rappresentante la data e l'ora correnti.
	 * (Viene usato solo dallo starter pokemon)
	 *
	 * @return Un timestamp in formato long rappresentante la data e l'ora correnti.
	 */
	public long printData() 
		{		
		var data2 = LocalDateTime.now();
		var data4 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(data2);
		long data5 = Long.parseLong(data4);
		return data5;
		}

	/**
	 * Controlla che il path del file fornito sia corretto
	 *
	 * @param filetarget Il file da controllare.
	 * @return true se il file esiste, false se non esiste
	 */
	public boolean checkFileAt(String pathtarget) {
		return new File(pathtarget).exists();
	}
	
}
