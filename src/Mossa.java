import java.io.Serializable;

/**
 * Classe che rappresenta una Mossa di un Pokemon.
 * Implementa l'interfaccia Serializable per permettere la serializzazione degli oggetti.
 * 
 * @authors  Matteo e Francesco, i Leggendari
 * @since   0.3
 */
@SuppressWarnings("serial")
public class Mossa implements Serializable
	{
	//Generic
	private String nome;
	private int tipo;
	private String descrizione;
	
	//Danno
	private int potenza;
	private double contraccolpo;
	
	//Buff all'attaccante
	private int buffAtk;
	private int buffDif;
	private int buffAtkSp;
	private int buffDifSp;
	private int buffVel;
	private int buffPrec;
	
	//Debuff all'attaccato
	private int debuffAtk;
	private int debuffDif;
	private int debuffAtkSp;
	private int debuffDifSp;
	private int debuffVel;
	private int debuffPrec;
	
	//Altro
	private int effetto;
	private int precisione; 
	private int PP;
	private int PPMax;
	private int priorità;
	
	 /**
     * Costruttore della classe Mossa.
     *
     * @param nome          Il nome della mossa.
     * @param tipo          Il tipo della mossa.
     * @param potenza       La potenza della mossa.
     * @param contraccolpo  Il danno di contraccolpo della mossa.
     * @param buffAtk       Il buff all'attacco conferito dalla mossa.
     * @param buffDif       Il buff alla difesa conferito dalla mossa.
     * @param buffAtkSp     Il buff all'attacco speciale conferito dalla mossa.
     * @param buffDifSp     Il buff alla difesa speciale conferito dalla mossa.
     * @param buffVel       Il buff alla velocità conferito dalla mossa.
     * @param buffPrec      Il buff alla precisione conferito dalla mossa.
     * @param debuffAtk     Il debuff all'attacco subito dalla mossa.
     * @param debuffDif     Il debuff alla difesa subito dalla mossa.
     * @param debuffAtkSp   Il debuff all'attacco speciale subito dalla mossa.
     * @param debuffDifSp   Il debuff alla difesa speciale subito dalla mossa.
     * @param debuffVel     Il debuff alla velocità subito dalla mossa.
     * @param debuffPrec    Il debuff alla precisione subito dalla mossa.
     * @param effetto       L'effetto aggiuntivo della mossa.
     * @param priorità      La priorità della mossa.
     * @param precisione    La precisione della mossa.
     * @param PP            I Punti Mossa della mossa.
     * @param descrizione   La descrizione della mossa.
     */
	public Mossa(String nome, int tipo, int potenza,double contraccolpo, int buffAtk,int buffDif, int buffAtkSp,int buffDifSp,int buffVel,int buffPrec,int debuffAtk,int debuffDif,int debuffAtkSp,int debuffDifSp,int debuffVel,int debuffPrec,int effetto,boolean priorità, int precisione,int PP,String descrizione) {
		this.nome=nome;
		this.tipo=tipo;
		this.descrizione=descrizione;
		this.potenza=potenza;
		this.contraccolpo=contraccolpo;
		this.buffAtk=buffAtk;
		this.buffDif=buffDif;
		this.buffAtkSp=buffAtkSp;
		this.buffDifSp=buffDifSp;
		this.buffVel=buffVel;
		this.debuffAtk=debuffAtk;
		this.debuffDif=debuffDif;
		this.debuffAtkSp=debuffAtkSp;
		this.debuffDifSp=debuffDifSp;
		this.debuffVel=debuffVel;
		this.effetto=effetto;
		if (priorità) {this.priorità=1;} else {this.priorità=0;}
		this.precisione=precisione;
		this.PP=PP;
		this.PPMax=PP;
		this.buffPrec=buffPrec;
		this.debuffPrec=debuffPrec;
	}
		
	/**
	 * Restituisce il nome della mossa.
	 *
	 * @return Il nome della mossa.
	 */
	public String getNome() {
	    return nome;
	}
	
	/**
	 * Restituisce il tipo della mossa.
	 *
	 * @return Il tipo della mossa.
	 */
	public int getTipo() {
	    return tipo;
	}
	
	/**
	 * Restituisce la descrizione della mossa.
	 *
	 * @return La descrizione della mossa.
	 */
	public String getDescrizione() {
	    return descrizione;
	}
	
	/**
	 * Restituisce la potenza della mossa.
	 *
	 * @return La potenza della mossa.
	 */
	public int getPotenza() {
	    return potenza;
	}
	
	/**
	 * Restituisce il valore del contraccolpo della mossa.
	 *
	 * @return Il valore del contraccolpo della mossa.
	 */
	public double getContraccolpo() {
	    return contraccolpo;
	}
	
	/**
	 * Restituisce il buff all'attacco conferito dalla mossa.
	 *
	 * @return Il buff all'attacco conferito dalla mossa.
	 */
	public int getBuffAtk() {
	    return buffAtk;
	}
	
	/**
	 * Restituisce il buff alla difesa conferito dalla mossa.
	 *
	 * @return Il buff alla difesa conferito dalla mossa.
	 */
	public int getBuffDif() {
	    return buffDif;
	}
	
	/**
	 * Restituisce il buff all'attacco speciale conferito dalla mossa.
	 *
	 * @return Il buff all'attacco speciale conferito dalla mossa.
	 */
	public int getBuffAtkSp() {
	    return buffAtkSp;
	}
	
	/**
	 * Restituisce il buff alla difesa speciale conferito dalla mossa.
	 *
	 * @return Il buff alla difesa speciale conferito dalla mossa.
	 */
	public int getBuffDifSp() {
	    return buffDifSp;
	}
	
	/**
	 * Restituisce il buff alla velocità conferito dalla mossa.
	 *
	 * @return Il buff alla velocità conferito dalla mossa.
	 */
	public int getBuffVel() {
	    return buffVel;
	}
	
	/**
	 * Restituisce il buff alla precisione conferito dalla mossa.
	 *
	 * @return Il buff alla precisione conferito dalla mossa.
	 */
	public int getBuffPrec() {
	    return buffPrec;
	}
	
	/**
	 * Restituisce il debuff all'attacco subito dalla mossa.
	 *
	 * @return Il debuff all'attacco subito dalla mossa.
	 */
	public int getDebuffAtk() {
	    return debuffAtk;
	}
	
	/**
	 * Restituisce il debuff alla difesa subito dalla mossa.
	 *
	 * @return Il debuff alla difesa subito dalla mossa.
	 */
	public int getDebuffDif() {
	    return debuffDif;
	}
	
	/**
	 * Restituisce il debuff all'attacco speciale subito dalla mossa.
	 *
	 * @return Il debuff all'attacco speciale subito dalla mossa.
	 */
	public int getDebuffAtkSp() {
	    return debuffAtkSp;
	}
	
	/**
	 * Restituisce il debuff alla difesa speciale subito dalla mossa.
	 *
	 * @return Il debuff alla difesa speciale subito dalla mossa.
	 */
	public int getDebuffDifSp() {
	    return debuffDifSp;
	}
	
	/**
	 * Restituisce il debuff alla velocità subito dalla mossa.
	 *
	 * @return Il debuff alla velocità subito dalla mossa.
	 */
	public int getDebuffVel() {
	    return debuffVel;
	}
	
	/**
	 * Restituisce il debuff alla precisione subito dalla mossa.
	 *
	 * @return Il debuff alla precisione subito dalla mossa.
	 */
	public int getDebuffPrec() {
	    return debuffPrec;
	}
	
	/**
	 * Restituisce l'effetto aggiuntivo della mossa.
	 *
	 * @return L'effetto aggiuntivo della mossa.
	 */
	public int getEffetto() {
	    return effetto;
	}
	
	/**
	 * Restituisce la precisione della mossa.
	 *
	 * @return La precisione della mossa.
	 */
	public int getPrecisione() {
	    return precisione;
	}
	
	/**
	 * Restituisce i Punti Mossa (PP) attuali della mossa.
	 *
	 * @return I Punti Mossa (PP) attuali della mossa.
	 */
	public int getPP() {
	    return PP;
	}
	
	/**
	 * Restituisce i Punti Mossa (PP) massimi della mossa.
	 *
	 * @return I Punti Mossa (PP) massimi della mossa.
	 */
	public int getPPMax() {
	    return PPMax;
	}
	
	/**
	 * Restituisce la priorità della mossa.
	 *
	 * @return La priorità della mossa.
	 */
	public int getPriorità() {
	    return priorità;
	}
	
	/**
	 * Imposta i Punti Mossa (PP) attuali della mossa.
	 *
	 * @param PP I nuovi Punti Mossa (PP) da impostare.
	 */
	public void setPP(int PP) {
	    this.PP = PP;
	}

}