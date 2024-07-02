import java.io.Serializable;
import java.util.Random;


/**
 * La classe Pokemon rappresenta un Pokemon con tutte le sue caratteristiche, statistiche, mosse,
 * e informazioni di evoluzione. Implementa l'interfaccia Serializable per consentire la serializzazione
 * degli oggetti di questa classe.
 * 
 * @authors  Matteo e Francesco, i Leggendari
 * @since   0.3
 */
@SuppressWarnings("serial")
public class Pokemon implements Serializable{

	
	//Generico
	private String nome;
	@SuppressWarnings("unused")
	private int ID = (int) new Salvataggio<>().printData() * (10)^14;
	private String spriteFronte;
	private String spriteRetro;
	private String spritePiccolo;
	private boolean Started = false;
	
	private int livello;
	

	//livello al quale il Pokemon si evolve (se è zero il pokemon non ha evoluzioni)
	private int livelloEvoluzione;
	
	//i punti esperienza equivalgono al valore del livello al cubo
	private int puntiExp;
	
	//i punti individuali per ogni statistica, vanno da un minimo di 0 ad un massimo di 15, vengono definiti randomicamente alla creazione del Pokemon
	private int puntiIndividualiPs;
	private int puntiIndividualiAtk;
	private int puntiIndividualiDif;
	private int puntiIndividualiAtkSp;
	private int puntiIndividualiDifSp;
	private int puntiIndividualiVel;
	//i punti allenamento per ogni stat,da 0 a 65535, all'inizio sono tutti a zero, aumentano sconfiggendo altri Pokemon(si aggiunge al totale la statistisca base corrispondente del Pokemon sconfitto)
	private int puntiAllenamentoPs;
	private int puntiAllenamentoAtk;
	private int puntiAllenamentoDif;
	private int puntiAllenamentoAtkSp;
	private int puntiAllenamentoDifSp;
	private int puntiAllenamentoVel;
	
	private int tipo1;
	private int tipo2;
	
	private int ps;
	private int psMax;
	private int psBase;
	
	private int attacco;
	private int attaccoMax;
	private int attaccoBase;
	private int contatoreBuffAtk;
	
	private int difesa;
	private int difesaMax;
	private int difesaBase;
	private int contatoreBuffDif;
	
	private int attaccoSp;
	private int attaccoSpMax;
	private int attaccoSpBase;
	private int contatoreBuffAtkSp;
	
	private int difesaSp;
	private int difesaSpMax;
	private int difesaSpBase;
	private int contatoreBuffDifSp;
	
	private int velocità;
	private int velocitàMax;
	private int velocitàBase;
	private int contatoreBuffVel;
	
	private int precisione=100;
	private int contatoreBuffPrec;
	
	private Mossa[] mosseInFight=new Mossa[4];
	private int[] livelliMosseApprendibili;
	private Mossa[] mosseApprendibili;
	private String evoluzione;
	
	//Array di chi ha hittato il pokemon (Va resettato ogni fine game)
	private Pokemon[] whoHitMe=new Pokemon[6];
	
	/**
     * Costruisce un nuovo Pokemon con le caratteristiche specificate.
     *
     * @param nome il nome del Pokemon
     * @param spriteFronte il percorso dell'immagine del fronte del Pokemon
     * @param spriteRetro il percorso dell'immagine del retro del Pokemon
     * @param spritePiccolo il percorso dell'immagine piccola del Pokemon
     * @param livello il livello iniziale del Pokemon
     * @param livelloEvoluzione il livello al quale il Pokemon si evolve (0 se non ha evoluzioni)
     * @param tipo1 il primo tipo del Pokemon
     * @param tipo2 il secondo tipo del Pokemon
     * @param psBase i punti salute base del Pokemon
     * @param attaccoBase l'attacco base del Pokemon
     * @param difesaBase la difesa base del Pokemon
     * @param attaccoSpBase l'attacco speciale base del Pokemon
     * @param difesaSpBase la difesa speciale base del Pokemon
     * @param velocitàBase la velocità base del Pokemon
     * @param mosseInFight le mosse attualmente conosciute dal Pokemon
     * @param livelliMosseApprendibili i livelli ai quali il Pokemon apprende nuove mosse
     * @param mosseApprendibili le mosse che il Pokemon può apprendere
     * @param evoluzione il nome del Pokemon in cui questo Pokemon si evolve
     */
	public Pokemon(String nome, String spriteFronte,String spriteRetro,String spritePiccolo, int livello,int livelloEvoluzione, int tipo1,int tipo2, int psBase, int attaccoBase, int difesaBase,int attaccoSpBase,int difesaSpBase, int velocitàBase, Mossa[] mosseInFight, int[] livelliMosseApprendibili,Mossa[]mosseApprendibili,String evoluzione) {
		this.nome=nome;
		this.spriteFronte=spriteFronte;
		this.spriteRetro=spriteRetro;
		this.spritePiccolo=spritePiccolo;
		this.livello=livello;
		this.livelloEvoluzione=livelloEvoluzione;
		this.puntiExp=livello*livello*livello;
		this.tipo1=tipo1;
		this.tipo2=tipo2;
		this.psBase=psBase;
		this.attaccoBase=attaccoBase;
		this.difesaBase=difesaBase;
		this.attaccoSpBase=attaccoSpBase;
		this.difesaSpBase=difesaSpBase;
		this.velocitàBase=velocitàBase;
		this.mosseInFight=mosseInFight;
	    this.livelliMosseApprendibili=livelliMosseApprendibili;
	    this.mosseApprendibili=mosseApprendibili;
		try {for(int i = 0; i < 4; i++) {this.mosseInFight[i] = mosseInFight[i];}}
		catch(Exception e) {} 
		this.evoluzione=evoluzione;
	}
	
	//Inizio dei get
	
		/**
	 * Restituisce il nome del Pokemon.
	 *
	 * @return il nome del Pokemon
	 */
	public String getNome() {
			return nome;
		}
	
	/**
	 * Restituisce il percorso dell'immagine del fronte del Pokemon.
	 *
	 * @return il percorso dell'immagine del fronte del Pokemon
	 */
	public String getSpriteFronte() {
			return spriteFronte;
			}
	
	/**
	 * Restituisce il percorso dell'immagine del retro del Pokemon.
	 *
	 * @return il percorso dell'immagine del retro del Pokemon
	 */
	public String getSpriteRetro() {
			return spriteRetro;
			}
	
	/**
	 * Restituisce il percorso dell'immagine piccola del Pokemon.
	 *
	 * @return il percorso dell'immagine piccola del Pokemon
	 */
	public String getSpritePiccolo() {
			return spritePiccolo;
			}	
	
	/**
	 * Restituisce il livello del Pokemon.
	 *
	 * @return il livello del Pokemon
	 */
	public int getLivello() {
			return livello;
		}
	
	/**
	 * Restituisce il livello al quale il Pokemon si evolve.
	 *
	 * @return il livello di evoluzione del Pokemon, 0 se non ha evoluzioni
	 */
	public int getLivelloEvoluzione() {
			return livelloEvoluzione;
		}
	
	/**
	 * Restituisce i punti esperienza del Pokemon.
	 *
	 * @return i punti esperienza del Pokemon
	 */
	public int getPuntiExp() {
			return puntiExp;
		}
	
	/**
	 * Restituisce i punti salute attuali del Pokemon.
	 *
	 * @return i punti salute attuali del Pokemon
	 */
	public int getPs() {
			return ps;
		}
	
	/**
	 * Restituisce i punti salute massimi del Pokemon.
	 *
	 * @return i punti salute massimi del Pokemon
	 */
	public int getPsMax() {
			return psMax;
		}
	
	/**
	 * Restituisce i punti salute base del Pokemon.
	 *
	 * @return i punti salute base del Pokemon
	 */
	public int getPsBase() {
			return psBase;
		}
	
	/**
	 * Restituisce l'attacco attuale del Pokemon.
	 *
	 * @return l'attacco attuale del Pokemon
	 */
	public int getAttacco() {
			return attacco;
		}
	
	/**
	 * Restituisce l'attacco massimo del Pokemon.
	 *
	 * @return l'attacco massimo del Pokemon
	 */
	public int getAttaccoMax() {
			return attaccoMax;
		}
	
	/**
	 * Restituisce l'attacco base del Pokemon.
	 *
	 * @return l'attacco base del Pokemon
	 */
	public int getAttaccoBase() {
			return attaccoBase;
		}
	
	/**
	 * Restituisce il contatore dei buff dell'attacco del Pokemon.
	 *
	 * @return il contatore dei buff dell'attacco del Pokemon
	 */
	public int getContatoreBuffAtk() {
			return contatoreBuffAtk;
		}
	
	/**
	 * Restituisce la difesa attuale del Pokemon.
	 *
	 * @return la difesa attuale del Pokemon
	 */
	public int getDifesa() {
			return difesa;
		}
	
	/**
	 * Restituisce la difesa massima del Pokemon.
	 *
	 * @return la difesa massima del Pokemon
	 */
	public int getDifesaMax() {
			return difesaMax;
		}
	
	/**
	 * Restituisce la difesa base del Pokemon.
	 *
	 * @return la difesa base del Pokemon
	 */
	public int getDifesaBase() {
			return difesaBase;
		}
	
	/**
	 * Restituisce il contatore dei buff della difesa del Pokemon.
	 *
	 * @return il contatore dei buff della difesa del Pokemon
	 */
	public int getContatoreBuffDif() {
			return contatoreBuffDif;
		}
	
	/**
	 * Restituisce l'attacco speciale attuale del Pokemon.
	 *
	 * @return l'attacco speciale attuale del Pokemon
	 */
	public int getAttaccoSp() {
			return attaccoSp;
		}
	
	/**
	 * Restituisce l'attacco speciale massimo del Pokemon.
	 *
	 * @return l'attacco speciale massimo del Pokemon
	 */
	public int getAttaccoSpMax() {
			return attaccoSpMax;
		}
	
	/**
	 * Restituisce l'attacco speciale base del Pokemon.
	 *
	 * @return l'attacco speciale base del Pokemon
	 */
	public int getAttaccoSpBase() {
			return attaccoSpBase;
		}
	
	/**
	 * Restituisce il contatore dei buff dell'attacco speciale del Pokemon.
	 *
	 * @return il contatore dei buff dell'attacco speciale del Pokemon
	 */
	public int getContatoreBuffAtkSp() {
			return contatoreBuffAtkSp;
		}
	
	/**
	 * Restituisce la difesa speciale attuale del Pokemon.
	 *
	 * @return la difesa speciale attuale del Pokemon
	 */
	public int getDifesaSp() {
			return difesaSp;
		}
	
	/**
	 * Restituisce la difesa speciale massima del Pokemon.
	 *
	 * @return la difesa speciale massima del Pokemon
	 */
	public int getDifesaSpMax() {
			return difesaSpMax;
		}
	
	/**
	 * Restituisce la difesa speciale base del Pokemon.
	 *
	 * @return la difesa speciale base del Pokemon
	 */
	public int getDifesaSpBase() {
			return difesaSpBase;
		}
	
	/**
	 * Restituisce il contatore dei buff della difesa speciale del Pokemon.
	 *
	 * @return il contatore dei buff della difesa speciale del Pokemon
	 */
	public int getContatoreBuffDifSp() {
			return contatoreBuffDifSp;
		}
	
	/**
	 * Restituisce la velocità attuale del Pokemon.
	 *
	 * @return la velocità attuale del Pokemon
	 */
	public int getVelocità() {
			return velocità;
		}
	
	/**
	 * Restituisce la velocità massima del Pokemon.
	 *
	 * @return la velocità massima del Pokemon
	 */
	public int getVelocitàMax() {
			return velocitàMax;
		}
	
	/**
	 * Restituisce la velocità base del Pokemon.
	 *
	 * @return la velocità base del Pokemon
	 */
	public int getVelocitàBase() {
			return velocitàBase;
		}
	
	/**
	 * Restituisce il contatore dei buff della velocità del Pokemon.
	 *
	 * @return il contatore dei buff della velocità del Pokemon
	 */
	public int getContatoreBuffVel() {
			return contatoreBuffVel;
		}
	
	/**
	 * Restituisce la precisione attuale del Pokemon.
	 *
	 * @return la precisione attuale del Pokemon
	 */
	public int getPrecisione() {
			return precisione;
		}
	
	/**
	 * Restituisce il contatore dei buff della precisione del Pokemon.
	 *
	 * @return il contatore dei buff della precisione del Pokemon
	 */
	public int getContatoreBuffPrec() {
			return contatoreBuffPrec;
		}
	
	/**
	 * Restituisce i punti allenamento della salute del Pokemon.
	 *
	 * @return i punti allenamento della salute del Pokemon
	 */
	public int getPuntiAllenamentoPs() {
			return puntiAllenamentoPs;
		}
	
	/**
	 * Restituisce i punti allenamento dell'attacco del Pokemon.
	 *
	 * @return i punti allenamento dell'attacco del Pokemon
	 */
	public int getPuntiAllenamentoAtk() {
			return puntiAllenamentoAtk;
		}
	
	/**
	 * Restituisce i punti allenamento della difesa del Pokemon.
	 *
	 * @return i punti allenamento della difesa del Pokemon
	 */
	public int getPuntiAllenamentoDif() {
			return puntiAllenamentoDif;
		}
	
	/**
	 * Restituisce i punti allenamento dell'attacco speciale del Pokemon.
	 *
	 * @return i punti allenamento dell'attacco speciale del Pokemon
	 */
	public int getPuntiAllenamentoAtkSp() {
			return puntiAllenamentoAtkSp;
		}
	
	/**
	 * Restituisce i punti allenamento della difesa speciale del Pokemon.
	 *
	 * @return i punti allenamento della difesa speciale del Pokemon
	 */
	public int getPuntiAllenamentoDifSp() {
			return puntiAllenamentoDifSp;
		}
	
	/**
	 * Restituisce i punti allenamento della velocità del Pokemon.
	 *
	 * @return i punti allenamento della velocità del Pokemon
	 */
	public int getPuntiAllenamentoVel() {
			return puntiAllenamentoVel;
		}
	
	/**
	 * Restituisce il primo tipo del Pokemon.
	 *
	 * @return il primo tipo del Pokemon
	 */
	public int getTipo1(){
			return tipo1;
		}
	
	/**
	 * Restituisce il secondo tipo del Pokemon.
	 *
	 * @return il secondo tipo del Pokemon
	 */
	public int getTipo2() {
			return tipo2;
		} 
	
	/**
	 * Restituisce il nome del Pokemon in cui questo Pokemon si evolve.
	 *
	 * @return il nome del Pokemon in cui questo Pokemon si evolve
	 */
	public String getEvoluzione() {
			return evoluzione;
		}
		
	/**
	 * Restituisce l'array dei Pokemon che hanno colpito questo Pokemon, ponendo sulla sinistra dell'array i Pokemon e sulla destra i null.
	 *
	 * @return un array di Pokemon che hanno colpito questo Pokemon
	 */
	public Pokemon[] getWhoHitMe() {
		//metto a sinistra di "temp" tutti i non null e a destra i null
	    int indextemp=0;
	    Pokemon [] temp = new Pokemon[6];
	    for (int i = 0; i < 6; i++) {
	        if (whoHitMe[i]!=null) {
	            temp[indextemp] = whoHitMe[i];
	            indextemp++;
	        }
	    }
	    //creo un'array grande per contenere tutti i non null e lo riempio
	    Pokemon[]ritorno=new Pokemon[indextemp];
	    for (int i=0;i<indextemp;i++) {
	        ritorno[i]=whoHitMe[i];
	    }
	    return ritorno;
	}
	
	/**
	 * Restituisce i livelli delle mosse apprendibili dal Pokemon.
	 *
	 * @return un array dei livelli delle mosse apprendibili
	 */
	public int[] getLivelliMosseApprendibili() {
			return livelliMosseApprendibili;
		}
	
	/**
	 * Restituisce la prossima mossa apprendibile dal Pokemon, se disponibile, e aggiorna le liste delle mosse e dei livelli apprendibili.
	 *
	 * @return la prossima mossa apprendibile dal Pokemon, altrimenti null
	 */
	public Mossa getMosseApprendibili() {
		//try {
		if (livelliMosseApprendibili[0]==livello) {
		
			int lunghezzaMosse=livelliMosseApprendibili.length;
			Mossa mossa =  mosseApprendibili[0];
			
			Mossa[] mosseTemporanee=new Mossa[lunghezzaMosse-1];
			int[] livelliTemporanei=new int[lunghezzaMosse-1];
			for (int i=1;i<lunghezzaMosse;i++) {
				mosseTemporanee[i-1]=mosseApprendibili[i];
				livelliTemporanei[i-1]=livelliMosseApprendibili[i];
			}
			if (livelliTemporanei.length == 0) {livelliTemporanei = new int[]{0};} //Livello irraggiungibile, settato se non hai più nulla da imparare
			mosseApprendibili=mosseTemporanee;
			livelliMosseApprendibili=livelliTemporanei;
		
			return mossa;
		//	}
		}
		//catch (Exception e) 
			//{
			//e.printStackTrace();
			//}
		return null;
	}
	
	
	//Inizio dei set
	
	/**
	 * Imposta il livello del Pokemon.
	 *
	 * @param livello il nuovo livello del Pokemon
	 */
	public void setLivello(int livello) {
			this.livello=livello;
		}
	
	/**
	 * Imposta i punti esperienza del Pokemon.
	 *
	 * @param puntiExp i nuovi punti esperienza del Pokemon
	 */
	public void setPuntiExp(int puntiExp) {
			this.puntiExp=puntiExp;
		}
	
	/**
	 * Imposta i punti salute attuali del Pokemon.
	 *
	 * @param ps i nuovi punti salute attuali del Pokemon
	 */
	public void setPs(int ps) {
			this.ps=ps;
		}
	
	/**
	 * Imposta i punti salute massimi del Pokemon.
	 *
	 * @param psMax i nuovi punti salute massimi del Pokemon
	 */
	public void setPsMax(int psMax) {
			this.psMax=psMax;
		}
	
	/**
	 * Imposta l'attacco attuale del Pokemon.
	 *
	 * @param attacco il nuovo attacco attuale del Pokemon
	 */
	public void setAttacco(int attacco) {
			this.attacco=attacco;
		}
	
	/**
	 * Imposta l'attacco massimo del Pokemon.
	 *
	 * @param attaccoMax il nuovo attacco massimo del Pokemon
	 */
	public void setAttaccoMax(int attaccoMax) {
			this.attaccoMax=attaccoMax;
		}
	
	/**
	 * Imposta il contatore dei buff dell'attacco del Pokemon.
	 *
	 * @param contatoreBuffAtk il nuovo contatore dei buff dell'attacco del Pokemon
	 */
	public void setContatoreBuffAtk(int contatoreBuffAtk) {
			this.contatoreBuffAtk=contatoreBuffAtk;
		}
	
	/**
	 * Imposta la difesa attuale del Pokemon.
	 *
	 * @param difesa la nuova difesa attuale del Pokemon
	 */
	public void setDifesa(int difesa) {
			this.difesa=difesa;
		}
	
	/**
	 * Imposta la difesa massima del Pokemon.
	 *
	 * @param difesaMax la nuova difesa massima del Pokemon
	 */
	public void setDifesaMax(int difesaMax) {
			this.difesaMax=difesaMax;
		}
	
	/**
	 * Imposta il contatore dei buff della difesa del Pokemon.
	 *
	 * @param contatoreBuffDif il nuovo contatore dei buff della difesa del Pokemon
	 */
	public void setContatoreBuffDif(int contatoreBuffDif) {
			this.contatoreBuffDif=contatoreBuffDif;
		}
	
	/**
	 * Imposta l'attacco speciale attuale del Pokemon.
	 *
	 * @param attaccoSp il nuovo attacco speciale attuale del Pokemon
	 */
	public void setAttaccoSp(int attaccoSp) {
			this.attaccoSp=attaccoSp;
		}
	
	/**
	 * Imposta l'attacco speciale massimo del Pokemon.
	 *
	 * @param attaccoSpMax il nuovo attacco speciale massimo del Pokemon
	 */
	public void setAttaccoSpMax(int attaccoSpMax) {
			this.attaccoSpMax=attaccoSpMax;
		}
	
	/**
	 * Imposta il contatore dei buff dell'attacco speciale del Pokemon.
	 *
	 * @param contatoreBuffAtkSp il nuovo contatore dei buff dell'attacco speciale del Pokemon
	 */
	public void setContatoreBuffAtkSp(int contatoreBuffAtkSp) {
			this.contatoreBuffAtkSp=contatoreBuffAtkSp;
		}
	
	/**
	 * Imposta la difesa speciale attuale del Pokemon.
	 *
	 * @param difesaSp la nuova difesa speciale attuale del Pokemon
	 */
	public void setDifesaSp(int difesaSp) {
			this.difesaSp=difesaSp;
		}
	
	/**
	 * Imposta la difesa speciale massima del Pokemon.
	 *
	 * @param difesaSpMax la nuova difesa speciale massima del Pokemon
	 */
	public void setDifesaSpMax(int difesaSpMax) {
			this.difesaSpMax=difesaSpMax;
		}
	
	/**
	 * Imposta il contatore dei buff della difesa speciale del Pokemon.
	 *
	 * @param contatoreBuffDifSp il nuovo contatore dei buff della difesa speciale del Pokemon
	 */
	public void setContatoreBuffDifSp(int contatoreBuffDifSp) {
			this.contatoreBuffDifSp=contatoreBuffDifSp;
		}
	
	/**
	 * Imposta la velocità attuale del Pokemon.
	 *
	 * @param velocità la nuova velocità attuale del Pokemon
	 */
	public void setVelocità(int velocità) {
			this.velocità=velocità;
		}
	
	/**
	 * Imposta la velocità massima del Pokemon.
	 *
	 * @param velocitàMax la nuova velocità massima del Pokemon
	 */
	public void setVelocitàMax(int velocitàMax) {
			this.velocitàMax=velocitàMax;
		}
	
	/**
	 * Imposta il contatore dei buff della velocità del Pokemon.
	 *
	 * @param contatoreBuffVel il nuovo contatore dei buff della velocità del Pokemon
	 */
	public void setContatoreBuffVel(int contatoreBuffVel) {
			this.contatoreBuffVel=contatoreBuffVel;
		}
	
	/**
	 * Imposta la precisione attuale del Pokemon.
	 *
	 * @param precisione la nuova precisione attuale del Pokemon
	 */
	public void setPrecisione(int precisione) {
			this.precisione=precisione;
		}
	
	/**
	 * Imposta il contatore dei buff della precisione del Pokemon.
	 *
	 * @param contatoreBuffPrec il nuovo contatore dei buff della precisione del Pokemon
	 */
	public void setContatoreBuffPrec(int contatoreBuffPrec) {
			this.contatoreBuffPrec=contatoreBuffPrec;
		}
	
	/**
	 * Imposta i punti allenamento della salute del Pokemon.
	 *
	 * @param puntiAllenamentoPs i nuovi punti allenamento della salute del Pokemon
	 */
	public void setPuntiAllenamentoPs(int puntiAllenamentoPs) {
			this.puntiAllenamentoPs=puntiAllenamentoPs;
		}
	
	/**
	 * Imposta i punti allenamento dell'attacco del Pokemon.
	 *
	 * @param puntiAllenamentoAtk i nuovi punti allenamento dell'attacco del Pokemon
	 */
	public void setPuntiAllenamentoAtk(int puntiAllenamentoAtk) {
			this.puntiAllenamentoAtk=puntiAllenamentoAtk;
		}
	
	/**
	 * Imposta i punti allenamento della difesa del Pokemon.
	 *
	 * @param puntiAllenamentoDif i nuovi punti allenamento della difesa del Pokemon
	 */
	public void setPuntiAllenamentoDif(int puntiAllenamentoDif) {
			this.puntiAllenamentoDif=puntiAllenamentoDif;
		}
	
	/**
	 * Imposta i punti allenamento dell'attacco speciale del Pokemon.
	 *
	 * @param puntiAllenamentoAtkSp i nuovi punti allenamento dell'attacco speciale del Pokemon
	 */
	public void setPuntiAllenamentoAtkSp(int puntiAllenamentoAtkSp) {
			this.puntiAllenamentoAtkSp=puntiAllenamentoAtkSp;
		}
	
	/**
	 * Imposta i punti allenamento della difesa speciale del Pokemon.
	 *
	 * @param puntiAllenamentoDifSp i nuovi punti allenamento della difesa speciale del Pokemon
	 */
	public void setPuntiAllenamentoDifSp(int puntiAllenamentoDifSp) {
			this.puntiAllenamentoDifSp=puntiAllenamentoDifSp;
		}
	
	/**
	 * Imposta i punti allenamento della velocità del Pokemon.
	 *
	 * @param puntiAllenamentoVel i nuovi punti allenamento della velocità del Pokemon
	 */
	public void setPuntiAllenamentoVel(int puntiAllenamentoVel) {
			this.puntiAllenamentoVel=puntiAllenamentoVel;
	}

	/**
	 * Imposta una mossa nel combattimento per il Pokemon all'indice specificato.
	 *
	 * @param mossa la mossa da impostare
	 * @param index l'indice della mossa nel combattimento (deve essere compreso tra 0 e 3)
	 */
	public void setMossa(Mossa mossa, int index) {
	    if (index >= 4 || index < 0) {
	        System.out.println("index sostituzione mossa out of range(Pokemon)");
	        return;
	    } else {
	        mosseInFight[index] = mossa;
	    }
	}
	
	/**
	 * Aggiunge un Pokemon all'array di Pokemon che hanno colpito questo Pokemon.
	 *
	 * @param pokemon il Pokemon che ha colpito questo Pokemon
	 */
	public void setWhoHitMe(Pokemon pokemon) {
	    for (int i = 0; i < 6; i++) {
	        if (whoHitMe[i] == null || whoHitMe[i].equals(pokemon)) {
	            whoHitMe[i] = pokemon;
	            return;
	        }
	    }
	}
	
	/**
	 * Imposta i livelli delle mosse apprendibili dal Pokemon.
	 *
	 * @param livelliMosseApprendibili i nuovi livelli delle mosse apprendibili
	 */
	public void setLivelliMosseApprendibili(int[] livelliMosseApprendibili) {
	    this.livelliMosseApprendibili = livelliMosseApprendibili;
	}
	
	/**
	 * Svuota l'array dei Pokemon che hanno colpito questo Pokemon.
	 */
	public void emptyWhoHitMe() {
	    this.whoHitMe = new Pokemon[6];
	}
	
	/**
	 * Calcola i punti salute massimi del Pokemon in base alle sue statistiche.
	 *
	 * @return i punti salute massimi del Pokemon
	 */
	public int equazionePs() {
	    double b = puntiAllenamentoPs;
	    double e = (((psBase + puntiIndividualiPs) * 2 * livello + ((Math.sqrt(b)) / 4) * livello) / 100) + livello + 10;
	    return (int) e;
	}
	
	/**
	 * Calcola una statistica del Pokemon in base ai valori di base, allenamento e individuali.
	 *
	 * @param statBase il valore base della statistica
	 * @param Allenamento i punti allenamento della statistica
	 * @param ind i punti individuali della statistica
	 * @return il valore calcolato della statistica
	 */
	public int equazioneStats(int statBase, int Allenamento, int ind) {
	    double b = Allenamento;
	    double e = ((((statBase + ind) * 2 * livello + (Math.sqrt(b) / 4) * livello) / 100) + 5);
	    return (int) e;
	}
	
	/**
	 * Inizializza il Pokemon impostando i suoi punti individuali e le statistiche.
	 */
	public void starterPokemon() {
	    if (!Started) {
	        Random r = new Random();
	        ID += (int) new Salvataggio<>().printData();
	
	        puntiIndividualiPs = r.nextInt(0, 16);
	        puntiIndividualiAtk = r.nextInt(0, 16);
	        puntiIndividualiDif = r.nextInt(0, 16);
	        puntiIndividualiAtkSp = r.nextInt(0, 16);
	        puntiIndividualiDifSp = r.nextInt(0, 16);
	        puntiIndividualiVel = r.nextInt(0, 16);
	
	        setPsMax(equazionePs());
	        setAttaccoMax(equazioneStats(getAttaccoBase(), getPuntiAllenamentoAtk(), puntiIndividualiAtk));
	        setDifesaMax(equazioneStats(getDifesaBase(), getPuntiAllenamentoDif(), puntiIndividualiDif));
	        setAttaccoSpMax(equazioneStats(getAttaccoSpBase(), getPuntiAllenamentoAtkSp(), puntiIndividualiAtkSp));
	        setDifesaSpMax(equazioneStats(getDifesaSpBase(), getPuntiAllenamentoDifSp(), puntiIndividualiDifSp));
	        setVelocitàMax(equazioneStats(getVelocitàBase(), getPuntiAllenamentoVel(), puntiIndividualiVel));
	
	        setPs(getPsMax());
	        setAttacco(getAttaccoMax());
	        setDifesa(getDifesaMax());
	        setAttaccoSp(getAttaccoSpMax());
	        setDifesaSp(getDifesaSpMax());
	        setVelocità(getVelocitàMax());
	
	        Started = true;
	    }
	}
	
	/**
	 * Aumenta il livello del Pokemon e aggiorna le sue statistiche di conseguenza.
	 */
	public void livelloSu() {
	    if (getLivello() > 100) {
	        setLivello(100);
	    }
	    setPsMax(equazionePs());
	
	    int AttaccoMaxTemp = attaccoMax;
	    int DifesaMaxTemp = difesaMax;
	    int AttaccoSpMaxTemp = attaccoSpMax;
	    int DifesaSpMaxTemp = difesaSpMax;
	    int VelocitàMaxTemp = velocitàMax;
	    
	    setAttaccoMax(equazioneStats(getAttaccoBase(), getPuntiAllenamentoAtk(), puntiIndividualiAtk));
	    setDifesaMax(equazioneStats(getDifesaBase(), getPuntiAllenamentoDif(), puntiIndividualiDif));
	    setAttaccoSpMax(equazioneStats(getAttaccoSpBase(), getPuntiAllenamentoAtkSp(), puntiIndividualiAtkSp));
	    setDifesaSpMax(equazioneStats(getDifesaSpBase(), getPuntiAllenamentoDifSp(), puntiIndividualiDifSp));
	    setVelocitàMax(equazioneStats(getVelocitàBase(), getPuntiAllenamentoVel(), puntiIndividualiVel));
	
	    //per i debuff, non debuffa troppo bene ma funziona =/
	    setAttacco(attacco + attaccoMax - AttaccoMaxTemp);
	    setDifesa(difesa + difesaMax - DifesaMaxTemp);
	    setAttaccoSp(attaccoSp + attaccoSpMax - AttaccoSpMaxTemp);
	    setDifesaSp(difesaSp + difesaSpMax - DifesaSpMaxTemp);
	    setVelocità(velocità + velocitàMax - VelocitàMaxTemp);
	}
	
	/**
	 * Controlla se il Pokemon è pronto per evolversi.
	 *
	 * @return true se il Pokemon è pronto per evolversi, altrimenti false
	 */
	public boolean checkEvoluzione() {
	    return (livelloEvoluzione != 0 && livello >= livelloEvoluzione);
	}
	
	/**
	 * Evolve il Pokemon se sono soddisfatti i requisiti di evoluzione.
	 */
	public void siEvolve() {
	    if (checkEvoluzione()) {
	        Pokemon EvoluzioneCongelata = new Salvataggio<Pokemon>().getItem("assets/Pokemonz/" + evoluzione + ".ser");
	
	        nome = EvoluzioneCongelata.getNome();
	
	        spriteFronte = EvoluzioneCongelata.getSpriteFronte();
	        spriteRetro = EvoluzioneCongelata.getSpriteRetro();
	        spritePiccolo = EvoluzioneCongelata.getSpritePiccolo();
	
	        livelloEvoluzione = EvoluzioneCongelata.getLivelloEvoluzione();
	
	        tipo1 = EvoluzioneCongelata.getTipo1();
	        tipo2 = EvoluzioneCongelata.getTipo2();
	
	        psBase = EvoluzioneCongelata.getPsBase();
	        attaccoBase = EvoluzioneCongelata.getAttaccoBase();
	        difesaBase = EvoluzioneCongelata.getDifesaBase();
	        attaccoSpBase = EvoluzioneCongelata.getAttaccoSpBase();
	        difesaSp = EvoluzioneCongelata.getDifesaSpBase();
	        velocità = EvoluzioneCongelata.getVelocitàBase();
	
	        setPsMax(equazionePs());
	        setAttaccoMax(equazioneStats(getAttaccoBase(), getPuntiAllenamentoAtk(), puntiIndividualiAtk));
	        setDifesaMax(equazioneStats(getDifesaBase(), getPuntiAllenamentoDif(), puntiIndividualiDif));
	        setAttaccoSpMax(equazioneStats(getAttaccoSpBase(), getPuntiAllenamentoAtkSp(), puntiIndividualiAtkSp));
	        setDifesaSpMax(equazioneStats(getDifesaSpBase(), getPuntiAllenamentoDifSp(), puntiIndividualiDifSp));
	        setVelocitàMax(equazioneStats(getVelocitàBase(), getPuntiAllenamentoVel(), puntiIndividualiVel));
	        setPs(getPsMax());
	        setAttacco(getAttaccoMax());
	        setDifesa(getDifesaMax());
	        setAttaccoSp(getAttaccoSpMax());
	        setDifesaSp(getDifesaSpMax());
	        setVelocità(getVelocitàMax());
	
	        evoluzione = EvoluzioneCongelata.getEvoluzione();
	
	        int mosseDaDimenticare = EvoluzioneCongelata.getLivelliMosseApprendibili().length - livelliMosseApprendibili.length;
	
	        int[] livelliTemporanei = new int[livelliMosseApprendibili.length];
	        for (int i = mosseDaDimenticare; i < EvoluzioneCongelata.getLivelliMosseApprendibili().length; i++) {
	            livelliTemporanei[i - mosseDaDimenticare] = EvoluzioneCongelata.getLivelliMosseApprendibili()[i];
	        }
	        setLivelliMosseApprendibili(livelliTemporanei);
	    }
	}
	
	/**
	 * Restituisce la mossa del Pokemon all'indice specificato.
	 *
	 * @param scelta l'indice della mossa (deve essere compreso tra 0 e 3)
	 * @return la mossa del Pokemon all'indice specificato
	 */
	public Mossa getMossa(int scelta) {
	    if (!(scelta < 4) || (scelta < 0)) {
	        System.out.println("Perchè stai prendendo mossa " + scelta + " se le mosse so da 0 a 3?");
	    }
	    return mosseInFight[scelta];
	}
	
	@Override
	public String toString() {
	    return nome;
	}
}