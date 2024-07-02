import java.io.File;
import java.util.TimerTask;
import javax.sound.sampled.*;

/**
 * La classe Soundboard gestisce tutti gli effetti audio del gioco.
 * Questa classe è responsabile della gestione dell'audio usato dalla Gui durante il gioco.
 * Comunica principalmente con la classe GUI.
 * 
 * E' pensata per avere un "main audio" in corso per le colonne sonore
 * e dei subaudio (quickshoot) per gli effetti immediati (come premere um bottone)
 * 
 * 
 * @authors  Matteo e Francesco, i Leggendari
 * @since   0.25
 */
public final class Soundboard{

	
	private int loop;
	private String nomeClip;
	private AudioInputStream audioStream;
	private Clip clip;
	private int volume = 0;
	
	//Costruttore vuoto =D
	public Soundboard(){}
	
	//------------------------------------------------------
	//                 Metodi principali
	//------------------------------------------------------
	
	/**
	 * Carica un file audio specificato e prepara il clip audio per la riproduzione.
	 * Il metodo gestisce l'audio tramite Java Sound API.
	 *
	 * @param filepath il percorso relativo del file audio da caricare, senza estensione
	 * @param loop il numero di volte che il clip audio deve essere riprodotto:
	 *             -1 per riprodurre in loop continuo, 0 per riprodurre una volta, 
	 *             un valore positivo per riprodurre il numero specificato di volte
	 */
	public void select(String filepath , int loop) 	
		{
		try {
			audioStream = AudioSystem.getAudioInputStream(new File("assets/Soundboard/"+filepath+".wav"));
			clip = AudioSystem.getClip();
			nomeClip = filepath;
			
			if (loop == -1) {this.loop = javax.sound.sampled.Clip.LOOP_CONTINUOUSLY;}
			else {this.loop = loop;}
			
			System.out.println("SB Select: "+nomeClip);
			} 
		catch (java.io.FileNotFoundException e) {System.out.println("Problemi col path fornito "+"assets/Soundboard/"+filepath+".wav");}
		catch (Exception e) {System.out.println("PROBLEMI PROBLEMI CON LE CLIP");e.printStackTrace();}
		}
	
	/**
	 * Avvia la riproduzione della clip audio precedentemente selezionata.
	 * Questo metodo apre la clip audio, imposta il volume desiderato e avvia la riproduzione.
	 * Se è stato specificato un numero di cicli diverso da zero, la clip viene riprodotta in loop.
	 */
	public void start() 
		{
		try {
			
			//Accendi la clip
			clip.open(audioStream);
			//Settala al volume deciso
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue((float)volume); // Reduce volume by 10 decibels.
			clip.start();
			//Cliclala per il numero di cicli deciso
			clip.loop(loop);
			
			//Accendi la clip fr fr
			
			System.out.println("SB Start : "+nomeClip);
			} 
		catch (java.lang.IllegalStateException e) {System.out.println("Impossibile startare (open) la soundboard, in quanto probabilmente già in corso");}
		catch (Exception e) {e.printStackTrace();}
		}
	
	/**
	 * Ferma l'esecuzione della clip audio in corso
	 */
	public void stop() 
		{
		clip.stop();
		System.out.println("SB Stop  : "+nomeClip);

		}
	
	/**
	 * Ferma temporaneamente l'esecuzione della clip audio in corso per poi riavviarla dopo il delay specificato
	 */
	public void tempstop(int delay) 
		{
		stop();
		new java.util.Timer().schedule(new TimerTask(){
	        @Override
	        public void run() {
	        	clip.start();
	        }
    	},delay); 
		}
	
	/**
	 * Ferma l'esecuzione della clip audio in corso dopo il delay specificato
	 */
	public void stop(int delay) 
		{
		new java.util.Timer().schedule(new TimerTask(){
		        @Override
		        public void run() {
		        	stop();
		        }
	    	},delay); 
		}
	
	/**
	 * Imposta il volume della clip audio attualmente in riproduzione.
	 * Se la clip è attiva, il volume viene modificato utilizzando il controllo di guadagno (gain control).
	 * Se il valore del volume è inferiore a -80.0 dB, viene impostato a -80.0 dB per evitare valori non supportati.
	 * Se il valore del volume è superiore a 6.0 dB, viene impostato a a 6.0 dB per evitare valori non supportati.
	 * Dopo aver impostato il volume, la clip viene riavviata per applicare le modifiche.
	 *
	 * @param volume Il nuovo valore del volume da impostare, espresso in decibel (dB).
	 */
	public void setVolume(int volume) {
		this.volume= volume;
		if (clip!=null) {
			
			if (clip.isActive()) {
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				if (this.volume<=-80.0) {this.volume = -80;}
				if (this.volume>=6.0) {this.volume = 6;}
				gainControl.setValue((float)this.volume); //
				clip.start();
				}
			}
		}
	
	/**
	 * Esegue rapidamente la riproduzione di un file audio specificato, aumentando il volume se richiesto.
	 * Questo metodo è ottimizzato per effetti audio visivi e non va in conflitto con clip in corso allo stesso tempo.
	 *
	 * @param nomefile Il nome del file audio da riprodurre senza estensione, situato nella cartella "assets/Soundboard/".
	 * @param Increase L'incremento del volume da applicare al volume corrente. Se il volume aumentato supera 6 dB, viene limitato a 6 dB.
	 */
	public void quickShoot(String nomefile, int Increase) 	
		{
			
			int volumeEffettivo = volume;
			volumeEffettivo+=Increase;
			if (volumeEffettivo>6) {volumeEffettivo=6;}

        	try {
        	AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(new File("assets/Soundboard/"+nomefile+".wav"));
			Clip clip2 = AudioSystem.getClip();
			clip2.open(audioStream2);
			FloatControl gainControl2 = (FloatControl) clip2.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl2.setValue((float)volumeEffettivo); // Reduce volume by 10 decibels.
			clip2.start();
        	}
        	catch (Exception e) {}
	       
    	
		}
	
	/**
	 * Controlla se il nome del file audio della clip corrente corrisponde al nome specificato.
	 *
	 * @param clipconfronto Il nome del file audio da confrontare con il nome della clip corrente.
	 * @return true se il nome del file audio della clip corrente corrisponde a clipconfronto, false altrimenti.
	 */
	public boolean checkWav(String clipconfronto){
		return clipconfronto.equals(nomeClip);
		}
	
	//------------------------------------------------------
	//                 Overload metodi
	//------------------------------------------------------

	/**
	 * Esegue rapidamente la riproduzione di un file audio della soundboard con il volume corrente.
	 * richiama il metodo quickShoot(String, int) con int settato a 0.
	 *
	 * @param nomefile Il nome del file audio da riprodurre senza l'estensione (.wav).
	 */
	public void quickShoot(String nomefile) 	
	{
		quickShoot(nomefile, 0);
	}
	 
	/**
	 * Seleziona un file audio della soundboard e imposta automaticamente il loop a 0.
	 *
	 * @param filepath Il percorso relativo del file audio senza estensione (.wav) nella soundboard.
	 */
	public void select(String filepath)	
		{
		select(filepath , 0);
		}
	
	/**
	 * Avvia la riproduzione di un file audio della soundboard specificato con un loop definito.
	 * Questo metodo prima seleziona il file audio specificato e imposta il loop desiderato, quindi avvia la riproduzione.
	 *
	 * @param filepath Il percorso relativo del file audio senza estensione (.wav) nella soundboard.
	 * @param loop     Il numero di volte che il file audio deve essere riprodotto in loop. Usare -1 per riprodurre continuamente.
	 */
	public void start(String filepath , int loop) 
		{
		select(filepath ,loop);
		start();
		}


}
