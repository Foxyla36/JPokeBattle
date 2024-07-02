import java.util.Random;

/**
 * La classe Partita rappresenta una battaglia tra due giocatori con i loro rispettivi Pokemon.
 * Gestisce l'intero flusso di gioco, dall'inizio alla fine della partita, includendo l'esecuzione 
 * dei turni, l'applicazione delle mosse, e l'aggiornamento dello stato del gioco.
 * 
 * 
 * @authors  Matteo e Francesco, i Leggendari
 * @since   0.4
 */
public final class Partita 
{
private final String Versione = "Versione 1.0";

private boolean InCorso = true;
GUI Gui;

private boolean SceltaGiocatore1 = false; //verifica mossa scelta 1
private boolean SceltaGiocatore2 = false; //verifica mossa scelta 2

private Allenatore Giocatore1; //sempre giocatore
private Allenatore Giocatore2; //può essere bot o giocatore
private Allenatore[] Giocatori;

private Pokemon PokemonGiocatore1; //poke selezionato 1
private Pokemon PokemonGiocatore2; //poke selezionato 2

private Mossa[] mp = {null,null}; //mossa selezionata 1


private boolean standby=false;//utile per stoppare la partita, durante pokedead e esecuzione grafica

private boolean standbySecondo=false;//utile per stoppare la partita,durante apprendimento mossa

/**
 * Costruisce una nuova partita tra due giocatori specificati.
 *
 * @param Giocatori Un array di due oggetti Allenatore rappresentanti i giocatori della partita.
 */
public Partita(Allenatore[] Giocatori) 
{

    //set dei giocatori
    this.Giocatore1 = Giocatori[0];
    this.Giocatore2 = Giocatori[1];
    this.Giocatori=Giocatori;
    
    //primo Pokemon in lista preso in automatico ERRORE ERRORE
    for (int i = 0; i < 6; i++) {
        if((Giocatore1.getPokemonPosizione(i) != null )&&( PokemonGiocatore1 == null)) 
            {
            PokemonGiocatore1=Giocatore1.getPokemonPosizione(i);
            }
        if((Giocatore2.getPokemonPosizione(i) != null )&&( PokemonGiocatore2 == null)) 
            {
            PokemonGiocatore2=Giocatore2.getPokemonPosizione(i);
            }
    }

}

/**
 * Collega l'istanza corrente di Partita a un'istanza specifica di GUI.
 *
 * @param Gui L'oggetto GUI a cui collegare l'istanza di Partita.
 */
public void linkToGUI(GUI Gui) {this.Gui=Gui; System.out.println("Partita linkata a Gui con successo");}

/**
 * Avvia la partita tra due giocatori, gestendo i turni e le azioni dei loro Pokemon finché la partita non finisce.
 * Il metodo implementa un loop principale che controlla il flusso del gioco, includendo l'attesa delle mosse dei giocatori,
 * la determinazione dell'ordine di attacco basato sulla velocità dei Pokemon, e l'esecuzione dei turni di battaglia.
 * (Nel raro caso di parità della velocità dei due Pokemon viene effettuato un coinflip per decidere chi inizia per primo)
 *
 * @return true se la partita è stata avviata e completata con successo, altrimenti false.
 */
public boolean startGame() {
	
	//finchè partita non finisce
	while(InCorso != false) 
		{
		//aggiorna whoHitMe
		PokemonGiocatore1.setWhoHitMe(PokemonGiocatore2);
		PokemonGiocatore2.setWhoHitMe(PokemonGiocatore1);
		
		
		//Aspetto mossa Giocatore1 
		while (SceltaGiocatore1==false) 
			{
			try {
				Thread.sleep(1);
				} 
			catch (InterruptedException e) {e.printStackTrace();}
			}
		//reset del while d'attesa x il prossimo turno
		SceltaGiocatore1=false;
		
		
		//Aspetto mossa Giocatore2
		while (SceltaGiocatore2==false) 
			{
			try {
				Thread.sleep(1);
				} 
			catch (InterruptedException e) {e.printStackTrace();}
			}
		
		//reset del while d'attesa x il prossimo turno
		SceltaGiocatore2=false;
	
		//Se hanno la stessa speed allora coinflip
		if (PokemonGiocatore1.getVelocità()+mp[0].getPriorità()==PokemonGiocatore2.getVelocità()+mp[1].getPriorità())
			{
			Random r = new Random();
			int randomio = r.nextInt(0, 2);
			if (randomio == 0) {eseguiTurno(mp[0], mp[1] ,PokemonGiocatore1,PokemonGiocatore2, 0, 1);}
			else {eseguiTurno(mp[1], mp[0] ,PokemonGiocatore2,PokemonGiocatore1, 1, 0);}
			continue;
			}
		
		
		//Inizia a dare le sberle
		if(PokemonGiocatore1.getVelocità()+mp[0].getPriorità()>PokemonGiocatore2.getVelocità()+mp[1].getPriorità()) 
			{
			eseguiTurno(mp[0], mp[1] ,PokemonGiocatore1,PokemonGiocatore2, 0, 1);
			
			}
		
		else 
			{
			
			eseguiTurno(mp[1], mp[0] ,PokemonGiocatore2,PokemonGiocatore1, 1, 0);
			}
			
		
		
		} 
		return true;
	} 

/**
 * Restituisce il Pokemon attualmente assegnato al Giocatore 1 in questa partita.
 *
 * @return il Pokemon attualmente assegnato al Giocatore 1.
 */
public Pokemon getPokemonGiocatore1() {return PokemonGiocatore1;}  

/**
 * Restituisce il Pokemon attualmente assegnato al Giocatore 2 in questa partita.
 *
 * @return il Pokemon attualmente assegnato al Giocatore 2.
 */
public Pokemon getPokemonGiocatore2() {return PokemonGiocatore2;}

/**
 * Assegna una mossa al Giocatore 1 durante il turno di gioco.
 * Questo comando è ciò che la Gui chiama dopo che l'utente decide quale
 * mossa svolgere. Successivamente alla chiamata di questo metodo, il while
 * della partita logica col compito di attendere l'input mossa viene rotto.
 *
 * @param mossa la mossa da assegnare al Giocatore 1.
 */
public void mossaGiocatore1(Mossa mossa) { if(mossa.getPP()>0) {mp[0] = mossa;SceltaGiocatore1 = true;}}

/**
 * Assegna una mossa al Giocatore 2 durante il turno di gioco.
 * Questo comando è ciò che la Gui chiama dopo che l'utente decide quale
 * mossa svolgere. Successivamente alla chiamata di questo metodo, il while
 * della partita logica col compito di attendere l'input mossa viene rotto.
 *
 * @param mossa la mossa da assegnare al Giocatore 2.
 */
public void mossaGiocatore2(Mossa mossa) { if(mossa.getPP()>0) {mp[1] = mossa;SceltaGiocatore2 = true;}}

/**
 * Cambia il Pokemon attivo per Giocatore1 e esegue i reset necessari.
 * Questo comando è ciò che la Gui chiama dopo che l'utente decide con quale
 * Pokemon cambiare l'attuale in campo. Successivamente alla chiamata di questo metodo, il while
 * della partita logica col compito di attendere l'input mossa viene rotto.
 *
 * @param indexDaCambiare L'indice del Pokemon da cambiare.
 */
public void switchPokemonG1(int indexDaCambiare) {
										PokemonGiocatore1.setAttacco(PokemonGiocatore1.getAttaccoMax());
										PokemonGiocatore1.setDifesa(PokemonGiocatore1.getDifesaMax());
										PokemonGiocatore1.setAttaccoSp(PokemonGiocatore1.getAttaccoSpMax());
										PokemonGiocatore1.setDifesaSp(PokemonGiocatore1.getDifesaSpMax());
										PokemonGiocatore1.setVelocità(PokemonGiocatore1.getVelocitàMax());
										PokemonGiocatore1.setContatoreBuffAtk(0);
										PokemonGiocatore1.setContatoreBuffDif(0);
										PokemonGiocatore1.setContatoreBuffAtkSp(0);
										PokemonGiocatore1.setContatoreBuffDifSp(0);
										PokemonGiocatore1.setContatoreBuffVel(0);
										PokemonGiocatore1.setContatoreBuffPrec(100);
										PokemonGiocatore1 = Giocatore1.getPokemonPosizione(indexDaCambiare); 
										mp[0] = new Salvataggio<Mossa>().getItem("assets/Mosse/moss-NULL.ser"); 
										SceltaGiocatore1 = true;}

/**
 * Cambia il Pokemon attivo per Giocatore2 e esegue i reset necessari.
 * Questo comando è ciò che la Gui chiama dopo che l'utente decide con quale
 * Pokemon cambiare l'attuale in campo. Successivamente alla chiamata di questo metodo, il while
 * della partita logica col compito di attendere l'input mossa viene rotto.
 *
 * @param indexDaCambiare L'indice del Pokemon da cambiare.
 */
public void switchPokemonG2(int indexDaCambiare) {
										PokemonGiocatore2.setAttacco(PokemonGiocatore2.getAttaccoMax());
										PokemonGiocatore2.setDifesa(PokemonGiocatore2.getDifesaMax());
										PokemonGiocatore2.setAttaccoSp(PokemonGiocatore2.getAttaccoSpMax());
										PokemonGiocatore2.setDifesaSp(PokemonGiocatore2.getDifesaSpMax());
										PokemonGiocatore2.setVelocità(PokemonGiocatore2.getVelocitàMax());
										PokemonGiocatore2.setContatoreBuffAtk(0);
										PokemonGiocatore2.setContatoreBuffDif(0);
										PokemonGiocatore2.setContatoreBuffAtkSp(0);
										PokemonGiocatore2.setContatoreBuffDifSp(0);
										PokemonGiocatore2.setContatoreBuffVel(0);
										PokemonGiocatore2.setContatoreBuffPrec(100);
										PokemonGiocatore2 = Giocatore2.getPokemonPosizione(indexDaCambiare); 
										mp[1] = new Salvataggio<Mossa>().getItem("assets/Mosse/moss-NULL.ser"); 
										SceltaGiocatore2 = true;}

/**
 * Esegue un turno di combattimento tra due Pokemon, gestendo le mosse e le eventuali
 * conseguenze come la sconfitta di uno dei Pokemon.
 *
 * @param PrimaMossa La prima mossa da eseguire.
 * @param SecondaMossa La seconda mossa da eseguire, se applicabile.
 * @param PrimoAttaccante Il Pokemon che esegue la prima mossa.
 * @param PrimoDifensore Il Pokemon che subisce la prima mossa.
 * @param AllenatoreAttaccante L'indice dell'allenatore del primo Pokemon.
 * @param AllenatoreDifensore L'indice dell'allenatore del secondo Pokemon.
 */
private void eseguiTurno(Mossa PrimaMossa, Mossa SecondaMossa, Pokemon PrimoAttaccante, Pokemon PrimoDifensore, int AllenatoreAttaccante, int AllenatoreDifensore) 
{
	System.out.println("\n=====\nINIZIA PRIMA "+Giocatori[AllenatoreAttaccante].getNome()+"\n=====");
	
	//Se giocatore 1 ha eseguito un cambio pokemon displayalo
	if (mp[0].getNome().equals("moss-NULL"))
		{
		Gui.changePokemon(0, PokemonGiocatore1);
		try {
			Thread.sleep(2000);
			} 
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
	
	//Se giocatore 2 ha eseguito un cambio pokemon displayalo
	if (mp[1].getNome().equals("moss-NULL"))
		{
		Gui.changePokemon(1, PokemonGiocatore2);
		try {
			Thread.sleep(2000);
			} 
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
	
	//Se giocatore in attacco non ha eseguito un cambio pokemon esegui mossa
	if (!(mp[AllenatoreAttaccante].getNome().equals("moss-NULL"))) {
		eseguiMossa(PrimaMossa,PrimoAttaccante,PrimoDifensore, AllenatoreAttaccante);
		}
	//Esegui mossa avrà dei comandi GUI dentro, STANDBY ATTIVO COSICCHE' FINITO IL DISPLAY DELL'ATTACCO SI POSSA PROCEDERE SU 
	//SU UN EVENTUALE SECONDO ATTACCO / POKEDEAD SENZA OVERRIDE DI SCHERMATE
	
	//StandBy disattivato da GUI APPENA FINISCE IL DISPLAY della Mossa
	
	//Controllo se P1 Vivo (Da contraccolpo)
	if (PrimoAttaccante.getPs()==0) 
		{
		//      killer          il killato       prop del killato
		pokedead(PrimoDifensore, PrimoAttaccante, AllenatoreAttaccante);

			}
		
	//Controllo se P2 Vivo (Da attacco)
	if (PrimoDifensore.getPs()>0) {
		
		//Se giocatore in difesa non ha eseguito un cambio pokemon esegui mossa
		if (!(mp[AllenatoreDifensore].getNome().equals("moss-NULL"))) {
			eseguiMossa(SecondaMossa, PrimoDifensore, PrimoAttaccante, AllenatoreDifensore);
			}
		
		//Dopo la mossa di P2, controlla se qualcuno è morto
		if (PrimoDifensore.getPs()==0) 
			{
			pokedead(PrimoAttaccante, PrimoDifensore, AllenatoreDifensore);
			}
		
		if (PrimoAttaccante.getPs()==0) 
			{
			pokedead(PrimoDifensore,PrimoAttaccante,AllenatoreAttaccante);
			}
	}
	//(runna pokedead se P2 morto)
	else {
		pokedead(PrimoAttaccante, PrimoDifensore, AllenatoreDifensore);
		}
	
	if (InCorso) 
		{
		Gui.displaybreakWait();
		}
}

/**
 * 
 *Questo metodo esegue una mossa di un Pokemon attaccante su un Pokemon difensore, applicando vari effetti, calcolando il danno e gestendo le modifiche alle statistiche.
 *
 *La mossa eseguita consuma un Punto Potenza (PP). Se il PP della mossa è maggiore di zero,
 *viene decrementato di uno. Successivamente, la probabilità di successo della mossa viene determinata
 *dalla precisione della mossa e del Pokemon attaccante.
 *Se la mossa ha successo, vengono calcolati i seguenti parametri:
 *<ul>
 *<li>Potenza della mossa.</li>
 *<li>Livello del Pokemon attaccante.</li>
 *<li>Statistiche di attacco e difesa di entrambi i Pokemon.</li>
 *<li>Probabilità di un brutto colpo basata sulla velocità dell'attaccante.</li>
 *<li>Valore di cura per le mosse che curano il Pokemon attaccante.</li>
 *<li>RNG (Random Number Generator) per variare leggermente il danno.</li>
 *<li>STAB (Same Type Attack Bonus) se il tipo della mossa coincide con il tipo del Pokemon attaccante.</li>
 *<li>Efficacia della mossa basata sui tipi del Pokemon difensore.</li>
 *<li>Il danno viene calcolato usando una formula che tiene conto di tutti questi fattori.</li>
 *</ul>
 *<p>
 *A seconda dell'effetto della mossa, possono essere applicate ulteriori modifiche:
 *<ul>
 *<li>Moltiplicatore di colpi per mosse che colpiscono più volte.</li>
 *<li>Danni costanti basati sul livello del Pokemon attaccante.</li>
 *<li>Aumento delle possibilità di brutto colpo.</li>
 *<li>Guarigione del Pokemon attaccante.</li>
 *<li>Danno da contraccolpo per alcune mosse.</li>
 *<li>Viene anche gestita la modifica delle statistiche dei Pokemon attaccante e difensore basata
 *sui buff e debuff della mossa.</li>
 *</ul>
 *
 *
 *
 * <p>Collegamenti a metodi di classi esterne:</p>
 * <ul>
     *<li>{@link #Mossa.getPP()} : per ottenere e impostare i Punti Potenza della mossa.
	 *<li>{@link #Mossa.getPrecisione()}: per ottenere la precisione della mossa.
	 *<li>{@link #Pokemon.getPrecisione()}: per ottenere la precisione del Pokemon attaccante.
	 *<li>{@link #Mossa.getPotenza()}: per ottenere la potenza della mossa.
	 *<li>{@link #Pokemon.getLivello()}: per ottenere il livello del Pokemon attaccante.
	 *<li>{@link #Pokemon.getAttacco()}, attaccante.getAttaccoSp(), attaccante.getDifesa(), attaccante.getDifesaSp(): per ottenere le statistiche di attacco e difesa del Pokemon attaccante.
	 *<li>{@link #Pokemon.getDifesa()}, difensore.getDifesaSp(), difensore.getTipo1(), difensore.getTipo2(), difensore.getPs(), difensore.setPs(): per ottenere e impostare le statistiche di difesa, i tipi e i punti salute del Pokemon difensore.
	 *<li>{@link #Pokemon.getVelocitàBase()}: per ottenere la velocità base del Pokemon attaccante.
	 *<li>{@link #Pokemon.getPsMax()}, attaccante.getPs(), attaccante.setPs(): per ottenere e impostare i punti salute massimi e attuali del Pokemon attaccante.
	 *<li>{@link #Mossa.getTipo()}, mossa.getEffetto(), mossa.getContraccolpo(), mossa.getBuffAtk(), mossa.getDebuffAtk(), mossa.getBuffDif(), mossa.getDebuffDif(): per ottenere i dettagli della mossa.
	 *<li>{@link #GUI.displayMess()}: per visualizzare i messaggi a schermo.
 * </ul>
 *
 *Parametri:
 *@param mossa La mossa che il Pokemon attaccante utilizza.
 *@param attaccante Il Pokemon che esegue la mossa.
 *@param difensore Il Pokemon che subisce la mossa.
 *@param GiocatoreAttaccante L'identificatore del giocatore che controlla il Pokemon attaccante.
 */
private void eseguiMossa(Mossa mossa,Pokemon attaccante,Pokemon difensore, int GiocatoreAttaccante)
	{
	
	
	
	//usando una mossa viene consumato un PP
	if (mossa.getPP()>0) {
		mossa.setPP(mossa.getPP()-1);
	
		int precisione=mossa.getPrecisione();
		Random r=new Random();
		int probabilità=r.nextInt(0,101);
		if (probabilità<=precisione*attaccante.getPrecisione()/100 || mossa.getPrecisione()==12800) 
		{
			//la mossa va a segno
			
			// stat del pokemon per calcolare il danno
			int potenza=mossa.getPotenza();
			int livello=attaccante.getLivello();
			int attacco=attaccante.getAttacco();
			int attaccoSp=attaccante.getAttaccoSp();
			int difesa=difensore.getDifesa();
			int difesaSp=difensore.getDifesaSp();
			
			//calcolare la probabilità di brutto colpo
			int R=r.nextInt(0,256);
			int T=attaccante.getVelocitàBase()/2;
			if (T>255) {T=255;}
			int bruttoColpo=1;
			if (R<T) {bruttoColpo=2;}
			
			//quanti ps vengono recuperati con mosse di cura
			int puntiCura=0;
			
			//calcolare l'RNG
			double numeratoreRNG=r.nextInt(217,256);
			double RNG=numeratoreRNG/255;
			
			//calcolo della STAB se il pokemon è dello stesso tipo della mossa
			double STAB=1;
			if (attaccante.getTipo1()==mossa.getTipo() || attaccante.getTipo2()==mossa.getTipo()) {
				STAB=1.5;}
			
			//efficacia della mossa default ad uno, e poi calcolo dell'efficacia per ogni tipo: 0==inefficacia; 0.5==poca efficacia; 2=super efficacia
			double efficaciaMossa= 1.0;
			switch(mossa.getTipo()) {
			//TIPO NORMALE 
			case 1: if (difensore.getTipo1()==8 || difensore.getTipo2()==8) {efficaciaMossa*=0;}
					if (difensore.getTipo1()==6 || difensore.getTipo2()==6) {efficaciaMossa*=0.5;}
					break;
			//TIPO LOTTA
			case 2: if (difensore.getTipo1()==8 || difensore.getTipo2()==8) {efficaciaMossa*=0;} 
					if(difensore.getTipo1()==3 || difensore.getTipo2()==3 ) {efficaciaMossa*=0.5;}
					if(difensore.getTipo1()==4 || difensore.getTipo2()==4 ) {efficaciaMossa*=0.5;}
					if(difensore.getTipo1()==7 || difensore.getTipo2()==7 ) {efficaciaMossa*=0.5;}
					if(difensore.getTipo1()==13 || difensore.getTipo2()==13 ) {efficaciaMossa*=0.5;}	
					if(difensore.getTipo1()==1 || difensore.getTipo2()==1 ) {efficaciaMossa*=2;}	
					if(difensore.getTipo1()==6 || difensore.getTipo2()==6 ) {efficaciaMossa*=2;} 
					if(difensore.getTipo1()==14 || difensore.getTipo2()==14 ) {efficaciaMossa*=2;}
					break;
			//TIPO VOLANTE
			case 3: if (difensore.getTipo1()==6 || difensore.getTipo2()==6) {efficaciaMossa*=0.5;} 
					if(difensore.getTipo1()==12 || difensore.getTipo2()==12 ) {efficaciaMossa*=0.5;}	
					if(difensore.getTipo1()==2 || difensore.getTipo2()==2 ) {efficaciaMossa*=2;}	
					if(difensore.getTipo1()==7 || difensore.getTipo2()==7 ) {efficaciaMossa*=2;} 
					if(difensore.getTipo1()==11 || difensore.getTipo2()==11 ) {efficaciaMossa*=2;}
					break;
			//TIPO VELENO
			case 4: if (difensore.getTipo1()==4 || difensore.getTipo2()==4) {efficaciaMossa*=0.5;} 
					if(difensore.getTipo1()==5 || difensore.getTipo2()==5 ) {efficaciaMossa*=0.5;}
					if(difensore.getTipo1()==6 || difensore.getTipo2()==6 ) {efficaciaMossa*=0.5;}	
					if(difensore.getTipo1()==8 || difensore.getTipo2()==8 ) {efficaciaMossa*=0.5;}		
					if(difensore.getTipo1()==7 || difensore.getTipo2()==7 ) {efficaciaMossa*=2;} 
					if(difensore.getTipo1()==11 || difensore.getTipo2()==11 ) {efficaciaMossa*=2;}
					break;
			//TIPO TERRA
			case 5:  if (difensore.getTipo1()==3 || difensore.getTipo2()==3) {efficaciaMossa*=0;} 
					 if(difensore.getTipo1()==7 || difensore.getTipo2()==7 ) {efficaciaMossa*=0.5;}
					 if(difensore.getTipo1()==11 || difensore.getTipo2()==11 ) {efficaciaMossa*=0.5;}	
					 if(difensore.getTipo1()==4 || difensore.getTipo2()==4 ) {efficaciaMossa*=2;}		
					 if(difensore.getTipo1()==6 || difensore.getTipo2()==6 ) {efficaciaMossa*=2;} 
					 if(difensore.getTipo1()==9 || difensore.getTipo2()==9 ) {efficaciaMossa*=2;}
					 if(difensore.getTipo1()==12 || difensore.getTipo2()==12 ) {efficaciaMossa*=2;}
					break;
			//TIPO ROCCIA
			case 6: if (difensore.getTipo1()==2 || difensore.getTipo2()==2) {efficaciaMossa*=0.5;} 
					if(difensore.getTipo1()==5 || difensore.getTipo2()==5 ) {efficaciaMossa*=0.5;}
					if(difensore.getTipo1()==3 || difensore.getTipo2()==3 ) {efficaciaMossa*=2;}	
					if(difensore.getTipo1()==7 || difensore.getTipo2()==7 ) {efficaciaMossa*=2;}		
					if(difensore.getTipo1()==9 || difensore.getTipo2()==9 ) {efficaciaMossa*=2;} 
					if(difensore.getTipo1()==14 || difensore.getTipo2()==14 ) {efficaciaMossa*=2;}
					break;
			//TIPO COLEOTTERO
			case 7: if (difensore.getTipo1()==2 || difensore.getTipo2()==2) {efficaciaMossa*=0.5;} 
					if(difensore.getTipo1()==3 || difensore.getTipo2()==3 ) {efficaciaMossa*=0.5;}
					if(difensore.getTipo1()==8 || difensore.getTipo2()==8 ) {efficaciaMossa*=0.5;}	
					if(difensore.getTipo1()==9 || difensore.getTipo2()==9 ) {efficaciaMossa*=0.5;}		
					if(difensore.getTipo1()==4 || difensore.getTipo2()==4 ) {efficaciaMossa*=2;} 
					if(difensore.getTipo1()==11 || difensore.getTipo2()==11 ) {efficaciaMossa*=2;}
					if(difensore.getTipo1()==13 || difensore.getTipo2()==13 ) {efficaciaMossa*=2;}
					break;
			//TIPO SPETTRO
			case 8: if (difensore.getTipo1()==1 || difensore.getTipo2()==1) {efficaciaMossa*=0;} 
					if(difensore.getTipo1()==13 || difensore.getTipo2()==13 ) {efficaciaMossa*=0;}
					if(difensore.getTipo1()==8 || difensore.getTipo2()==8 ) {efficaciaMossa*=2;}	
					break;
			//TIPO FUOCO
			case 9: if (difensore.getTipo1()==6 || difensore.getTipo2()==6) {efficaciaMossa*=0.5;} 
					if(difensore.getTipo1()==9 || difensore.getTipo2()==9 ) {efficaciaMossa*=0.5;}
					if(difensore.getTipo1()==10 || difensore.getTipo2()==10 ) {efficaciaMossa*=0.5;}	
					if(difensore.getTipo1()==15 || difensore.getTipo2()==15 ) {efficaciaMossa*=0.5;}		
					if(difensore.getTipo1()==7 || difensore.getTipo2()==7 ) {efficaciaMossa*=2;} 
					if(difensore.getTipo1()==11 || difensore.getTipo2()==11 ) {efficaciaMossa*=2;}
					if(difensore.getTipo1()==14 || difensore.getTipo2()==14 ) {efficaciaMossa*=2;}
					break;
			//TIPO ACQUA
			case 10:if (difensore.getTipo1()==10 || difensore.getTipo2()==10) {efficaciaMossa*=0.5;} 
					if(difensore.getTipo1()==11 || difensore.getTipo2()==11 ) {efficaciaMossa*=0.5;}
					if(difensore.getTipo1()==15 || difensore.getTipo2()==15 ) {efficaciaMossa*=0.5;}	
					if(difensore.getTipo1()==5 || difensore.getTipo2()==5 ) {efficaciaMossa*=2;}		
					if(difensore.getTipo1()==6 || difensore.getTipo2()==6 ) {efficaciaMossa*=2;} 
					if(difensore.getTipo1()==9 || difensore.getTipo2()==9 ) {efficaciaMossa*=2;}
					break;
			//TIPO ERBA 
			case 11:if (difensore.getTipo1()==3 || difensore.getTipo2()==3) {efficaciaMossa*=0.5;} 
					if(difensore.getTipo1()==4 || difensore.getTipo2()==4 ) {efficaciaMossa*=0.5;}
					if(difensore.getTipo1()==7 || difensore.getTipo2()==7 ) {efficaciaMossa*=0.5;}	
					if(difensore.getTipo1()==9 || difensore.getTipo2()==9 ) {efficaciaMossa*=0.5;}		
					if(difensore.getTipo1()==11 || difensore.getTipo2()==11 ) {efficaciaMossa*=0.5;} 
					if(difensore.getTipo1()==15 || difensore.getTipo2()==15 ) {efficaciaMossa*=0.5;}
					if(difensore.getTipo1()==5 || difensore.getTipo2()==5 ) {efficaciaMossa*=2;}
					if(difensore.getTipo1()==6 || difensore.getTipo2()==6 ) {efficaciaMossa*=2;}
					if(difensore.getTipo1()==10 || difensore.getTipo2()==10 ) {efficaciaMossa*=2;}
					break;
			//TIPO ELETTRO
			case 12:if (difensore.getTipo1()==5 || difensore.getTipo2()==5) {efficaciaMossa*=0;} 
					if(difensore.getTipo1()==11 || difensore.getTipo2()==11 ) {efficaciaMossa*=0.5;}
					if(difensore.getTipo1()==12 || difensore.getTipo2()==12 ) {efficaciaMossa*=0.5;}	
					if(difensore.getTipo1()==15 || difensore.getTipo2()==15 ) {efficaciaMossa*=0.5;}		
					if(difensore.getTipo1()==3 || difensore.getTipo2()==3 ) {efficaciaMossa*=2;} 
					if(difensore.getTipo1()==10 || difensore.getTipo2()==10 ) {efficaciaMossa*=2;}
					break;
			//TIPO PSICO
			case 13:if (difensore.getTipo1()==13 || difensore.getTipo2()==13) {efficaciaMossa*=0.5;} 
					if(difensore.getTipo1()==2 || difensore.getTipo2()==2 ) {efficaciaMossa*=2;}
					if(difensore.getTipo1()==4 || difensore.getTipo2()==4 ) {efficaciaMossa*=2;}	
					break;
			//TIPO GHIACCIO
			case 14:if (difensore.getTipo1()==10 || difensore.getTipo2()==10) {efficaciaMossa*=0.5;}  
					if(difensore.getTipo1()==14 || difensore.getTipo2()==14 ) {efficaciaMossa*=0.5;} 
					if(difensore.getTipo1()==3 || difensore.getTipo2()==3 ) {efficaciaMossa*=2;}	
					if(difensore.getTipo1()==5 || difensore.getTipo2()==5 ) {efficaciaMossa*=2;}		
					if(difensore.getTipo1()==11 || difensore.getTipo2()==11 ) {efficaciaMossa*=2;}
					if(difensore.getTipo1()==15 || difensore.getTipo2()==15 ) {efficaciaMossa*=2;}
					break;
			//TIPO DRAGO
			case 15:if (difensore.getTipo1()==15 || difensore.getTipo2()==15) {efficaciaMossa*=2;} 
					break;
			
			default: break;
			}
			
			//QUESTO E' CLEVER DESIGN
			int danno;
			try 
				{
				if (mossa.getTipo()>=1 && mossa.getTipo()<9) {				
					danno=(int)(((((2*livello/5)+2)*potenza*attacco/difesa)/50+2)*bruttoColpo*RNG*STAB*efficaciaMossa);
				}
				else {
					danno=(int)(((((2*livello/5)+2)*potenza*attaccoSp/difesaSp)/50+2)*bruttoColpo*RNG*STAB*efficaciaMossa);
					}
				}
			catch (java.lang.ArithmeticException e) 
				{	
				danno=0;
				}
			
			if (potenza==0){danno=0;}
			
			//effetti delle mosse
			switch(mossa.getEffetto()) {
			
			//colpisce più volte fino a 5
			case 1: 
				Random ran = new Random();
				double probHit=ran.nextDouble(0,101);
				int moltiplicatoreDanno=0;
				if(probHit>=0 && probHit<37.5) {moltiplicatoreDanno=2;}
				else if(probHit>=37.5 && probHit<75) {moltiplicatoreDanno=3;}
				else if (probHit>=75 && probHit<87.5) {moltiplicatoreDanno=4;}
				else if (probHit>=87.5 && probHit<=100) {moltiplicatoreDanno=5;}
				if(precisione<100)
					{int lung= moltiplicatoreDanno+1;
					for(int i=1;i<lung;i++) {
						if (((double)(ran.nextDouble(0,101))/100)>precisione){
							moltiplicatoreDanno-=1;
							if (moltiplicatoreDanno==1) {break;}
						}
					}
				}
				danno*=moltiplicatoreDanno;
				break;
					//teletrasporto:fa tornare il pokemon dall'allenatore
					
			 case 2:
	                Gui.displayMess(mossa.getNome()+" non esiste, non ha fatto nulla");
	                break;
	            
	         
	            case 3:
	            	 Gui.displayMess(mossa.getNome()+" non esiste, non ha fatto nulla");
	                break;
	            
	            
	            case 4:
	            	 Gui.displayMess(mossa.getNome()+" non esiste, non ha fatto nulla");
	                break;
	                
                
            //mosse che usano il livello per i danni   
            case 5: 
               danno=attaccante.getLivello();
			   break;
           
			//colpisce più volte fino a 3
            case 6:
            	Random ran2 = new Random();
            	int moltiplicatoreDanno2=0;
            	double probHit2=ran2.nextDouble(0,101);
				if(probHit2>=0 && probHit2<63.5) {moltiplicatoreDanno2=1;}
				else if(probHit2>=63.5 && probHit2<88.5) {moltiplicatoreDanno2=2;}
				else if(probHit2>=88.5 && probHit2<100) {moltiplicatoreDanno2=3;}
				if(precisione<100 && moltiplicatoreDanno2!=1)
				{
					int lung= moltiplicatoreDanno2+1;
					for(int i=1;i<lung;i++) {
						if (((double)(ran2.nextDouble(0,101))/100)>precisione){
							moltiplicatoreDanno2-=1;
							if (moltiplicatoreDanno2==1) {break;}
					    }
					}
				}
				danno*=moltiplicatoreDanno2;
				break;
			//cura di metà ps
            case 7:
            	puntiCura=attaccante.getPsMax()/2;
            	attaccante.setPs(attaccante.getPs()+puntiCura);
            	if (attaccante.getPs()>attaccante.getPsMax()) {attaccante.setPs(attaccante.getPsMax());}
            	break;
            	
            //parassiseme
            case 8:
            	danno=difensore.getPsMax()/5;
            	puntiCura=danno;
            	attaccante.setPs(attaccante.getPs()+puntiCura);
            	if (attaccante.getPs()>attaccante.getPsMax()) {attaccante.setPs(attaccante.getPsMax());}
            	break;
            	
			//aumenta chance di critico
            case 9:
    			T*=8;
    			if (T>255) {T=255;}
    			if (R<T) {bruttoColpo=2;}
    			
            	if (mossa.getTipo()>=1 && mossa.getTipo()<9) {				
    				danno=(int)(((((2*livello/5)+2)*potenza*attacco/difesa)/50+2)*bruttoColpo*RNG*STAB*efficaciaMossa);
    			}
    			else {
    				danno=(int)(((((2*livello/5)+2)*potenza*attaccoSp/difesaSp)/50+2)*bruttoColpo*RNG*STAB*efficaciaMossa);
    				}
            	break;
            	
            	
            case 10:
                danno=40;
                break;
			}
		
			
			//setti il danno, if minore 0 resetti a 0
			int primadisubiredanno = difensore.getPs();
			difensore.setPs(difensore.getPs()-danno);
			if (difensore.getPs()<0) {difensore.setPs(0);}		
			
			//contraccolpo, se non è zero il pokemon attaccante subisce del danno
			attaccante.setPs(attaccante.getPs()-((int)(danno*mossa.getContraccolpo())));
            if (attaccante.getPs()<0) {attaccante.setPs(0);}
			
			//set Hp a 0 se contraccolpo manda Hp sotto 0
			if (attaccante.getPs()<0) {attaccante.setPs(0);}	
		
			//aumento o diminuzione delle statistiche
			if(mossa.getBuffAtk()!=0) {
				attaccante.setContatoreBuffAtk(mossa.getBuffAtk()+attaccante.getContatoreBuffAtk());
				if (attaccante.getContatoreBuffAtk()>6) {attaccante.setContatoreBuffAtk(6);}
				else if (attaccante.getContatoreBuffAtk()<-6) {attaccante.setContatoreBuffAtk(-6);}
				if(attaccante.getContatoreBuffAtk()>=0) {
					attaccante.setAttacco(attaccante.getAttaccoMax()+(attaccante.getAttaccoMax()*attaccante.getContatoreBuffAtk())/2);
				}
				else {
					attaccante.setAttacco(attaccante.getAttaccoMax()+(attaccante.getAttaccoMax()*attaccante.getContatoreBuffAtk()/(2-attaccante.getContatoreBuffAtk())));
				}
			}
			
			if(mossa.getDebuffAtk()!=0) {
				
				difensore.setContatoreBuffAtk(mossa.getDebuffAtk()+difensore.getContatoreBuffAtk());
				if (difensore.getContatoreBuffAtk()>6) {difensore.setContatoreBuffAtk(6);}
				if (difensore.getContatoreBuffAtk()<-6) {difensore.setContatoreBuffAtk(-6);}
				if(difensore.getContatoreBuffAtk()>=0) {
					difensore.setAttacco(difensore.getAttaccoMax()+(difensore.getAttaccoMax()*difensore.getContatoreBuffAtk())/2);
				}
				else {
					difensore.setAttacco(difensore.getAttaccoMax()+(difensore.getAttaccoMax()*difensore.getContatoreBuffAtk()/(2-difensore.getContatoreBuffAtk())));
					
				}
			}

			if(mossa.getBuffDif()!=0) {
				attaccante.setContatoreBuffDif(mossa.getBuffDif()+attaccante.getContatoreBuffDif());
				if (attaccante.getContatoreBuffDif()>6) {attaccante.setContatoreBuffDif(6);}
				else if (attaccante.getContatoreBuffDif()<-6) {attaccante.setContatoreBuffDif(-6);}
				if(attaccante.getContatoreBuffDif()>=0) {
					attaccante.setDifesa(attaccante.getDifesaMax()+(attaccante.getDifesaMax()*attaccante.getContatoreBuffDif())/2);
				}
				else {
					attaccante.setDifesa(attaccante.getDifesaMax()+(attaccante.getDifesaMax()*attaccante.getContatoreBuffDif()/(2-attaccante.getContatoreBuffDif())));
				}
			}

			if(mossa.getDebuffDif()!=0) {
				difensore.setContatoreBuffDif(mossa.getDebuffDif()+difensore.getContatoreBuffDif());
				if (difensore.getContatoreBuffDif()>6) {difensore.setContatoreBuffDif(6);}
				else if (difensore.getContatoreBuffDif()<-6) {difensore.setContatoreBuffDif(-6);}
				if(difensore.getContatoreBuffDif()>=0) {
					difensore.setDifesa(difensore.getDifesaMax()+(difensore.getDifesaMax()*difensore.getContatoreBuffDif())/2);
				}
				else {
					difensore.setDifesa(difensore.getDifesaMax()+(difensore.getDifesaMax()*difensore.getContatoreBuffDif()/(2-difensore.getContatoreBuffDif())));
				}
			}

			if(mossa.getBuffAtkSp()!=0) {
				attaccante.setContatoreBuffAtkSp(mossa.getBuffAtkSp()+attaccante.getContatoreBuffAtkSp());
				if (attaccante.getContatoreBuffAtkSp()>6) {attaccante.setContatoreBuffAtkSp(6);}
				else if (attaccante.getContatoreBuffAtkSp()<-6) {attaccante.setContatoreBuffAtkSp(-6);}
				if(attaccante.getContatoreBuffAtkSp()>=0) {
					attaccante.setAttaccoSp(attaccante.getAttaccoSpMax()+(attaccante.getAttaccoSpMax()*attaccante.getContatoreBuffAtkSp())/2);
				}
				else {
					attaccante.setAttaccoSp(attaccante.getAttaccoSpMax()+(attaccante.getAttaccoSpMax()*attaccante.getContatoreBuffAtkSp()/(2-attaccante.getContatoreBuffAtkSp())));
				}
			}

			if(mossa.getDebuffAtkSp()!=0) {
				difensore.setContatoreBuffAtkSp(mossa.getDebuffAtkSp()+difensore.getContatoreBuffAtkSp());
				if (difensore.getContatoreBuffAtkSp()>6) {difensore.setContatoreBuffAtkSp(6);}
				else if (difensore.getContatoreBuffAtkSp()<-6) {difensore.setContatoreBuffAtkSp(-6);}
				if(difensore.getContatoreBuffAtkSp()>=0) {
					difensore.setAttaccoSp(difensore.getAttaccoSpMax()+(difensore.getAttaccoSpMax()*difensore.getContatoreBuffAtkSp())/2);
				}
				else {
					difensore.setAttaccoSp(difensore.getAttaccoSpMax()+(difensore.getAttaccoSpMax()*difensore.getContatoreBuffAtkSp()/(2-difensore.getContatoreBuffAtkSp())));
				}
			}

			if(mossa.getBuffDifSp()!=0) {
				attaccante.setContatoreBuffDifSp(mossa.getBuffDifSp()+attaccante.getContatoreBuffDifSp());
				if (attaccante.getContatoreBuffDifSp()>6) {attaccante.setContatoreBuffDifSp(6);}
				else if (attaccante.getContatoreBuffDifSp()<-6) {attaccante.setContatoreBuffDifSp(-6);}
				if(attaccante.getContatoreBuffDifSp()>=0) {
					attaccante.setDifesaSp(attaccante.getDifesaSpMax()+(attaccante.getDifesaSpMax()*attaccante.getContatoreBuffDifSp())/2);
				}
				else {
					attaccante.setDifesaSp(attaccante.getDifesaSpMax()+(attaccante.getDifesaSpMax()*attaccante.getContatoreBuffDifSp()/(2-attaccante.getContatoreBuffDifSp())));
				}
			}

			if(mossa.getDebuffDifSp()!=0) {
				difensore.setContatoreBuffDifSp(mossa.getDebuffDifSp()+difensore.getContatoreBuffDifSp());
				if (difensore.getContatoreBuffDifSp()>6) {difensore.setContatoreBuffDifSp(6);}
				else if (difensore.getContatoreBuffDifSp()<-6) {difensore.setContatoreBuffDifSp(-6);}
				if(difensore.getContatoreBuffDifSp()>=0) {
					difensore.setDifesaSp(difensore.getDifesaSpMax()+(difensore.getDifesaSpMax()*difensore.getContatoreBuffDifSp())/2);
				}
				else {
					difensore.setDifesaSp(difensore.getDifesaSpMax()+(difensore.getDifesaSpMax()*difensore.getContatoreBuffDifSp()/(2-difensore.getContatoreBuffDifSp())));
				}
			}

			if(mossa.getBuffVel()!=0) {
				attaccante.setContatoreBuffVel(mossa.getBuffVel()+attaccante.getContatoreBuffVel());
				if (attaccante.getContatoreBuffVel()>6) {attaccante.setContatoreBuffVel(6);}
				else if (attaccante.getContatoreBuffVel()<-6) {attaccante.setContatoreBuffVel(-6);}
				if(attaccante.getContatoreBuffVel()>=0) {
					attaccante.setVelocità(attaccante.getVelocitàMax()+(attaccante.getVelocitàMax()*attaccante.getContatoreBuffVel())/2);
				}
				else {
					attaccante.setVelocità(attaccante.getVelocitàMax()+(attaccante.getVelocitàMax()*attaccante.getContatoreBuffVel()/(2-attaccante.getContatoreBuffVel())));
				}
			}

			if(mossa.getDebuffVel()!=0) {
				difensore.setContatoreBuffVel(mossa.getDebuffVel()+difensore.getContatoreBuffVel());
				if (difensore.getContatoreBuffVel()>6) {difensore.setContatoreBuffVel(6);}
				else if (difensore.getContatoreBuffVel()<-6) {difensore.setContatoreBuffVel(-6);}
				if(difensore.getContatoreBuffVel()>=0) {
					difensore.setVelocità(difensore.getVelocitàMax()+(difensore.getVelocitàMax()*difensore.getContatoreBuffVel())/2);
				}
				else {
					difensore.setVelocità(difensore.getVelocitàMax()+(difensore.getVelocitàMax()*difensore.getContatoreBuffVel()/(2-difensore.getContatoreBuffVel())));
				}
			}

			if(mossa.getBuffPrec()!=0) {
				attaccante.setContatoreBuffPrec(mossa.getBuffPrec()+attaccante.getContatoreBuffPrec());
				if (attaccante.getContatoreBuffPrec()>6) {attaccante.setContatoreBuffPrec(6);}
				else if (attaccante.getContatoreBuffPrec()<-6) {attaccante.setContatoreBuffPrec(-6);}
				if(attaccante.getContatoreBuffPrec()>=0) {
					attaccante.setPrecisione(100+(100*attaccante.getContatoreBuffPrec())/2);
				}
				else {
					attaccante.setPrecisione(100+(100*attaccante.getContatoreBuffPrec()/(2-attaccante.getContatoreBuffPrec())));
				}
			}

			if(mossa.getDebuffPrec()!=0) {
				difensore.setContatoreBuffPrec(mossa.getDebuffPrec()+difensore.getContatoreBuffPrec());
				if (difensore.getContatoreBuffPrec()>6) {difensore.setContatoreBuffPrec(6);}
				else if (difensore.getContatoreBuffPrec()<-6) {difensore.setContatoreBuffPrec(-6);}
				if(difensore.getContatoreBuffPrec()>=0) {
					difensore.setPrecisione(100+(100*difensore.getContatoreBuffPrec())/2);
				}
				else {
					difensore.setPrecisione(100+(100*difensore.getContatoreBuffPrec()/(2-difensore.getContatoreBuffPrec())));
				}
			}
			
			//-------------------------------------------------------------------------------
			//Roba utile per display
			danno *=1; 							//danno inflitto
			int tipo_mossa = mossa.getTipo();   //tipo mossa (se facciamo sprite d'attacco diverse a seconda tipo)
			efficaciaMossa *=1;                 //se facciamo un label che indica il critico/efficacia
			GiocatoreAttaccante *=1;;           //chi attacca (serve allo switchcase del display)
			
			int buffself = 0;					//se ci si è buffati     (se applichiamo effetti grafici ai buff)
			int debuffself = 0;					//o se ci si è debuffati (se applichiamo effetti grafici ai buff)
			
			int[] Buff = {mossa.getBuffAtk(),mossa.getBuffAtkSp(),mossa.getBuffDif(),mossa.getBuffDifSp(),mossa.getBuffPrec(),mossa.getBuffVel()}; 
			for (int i : Buff) {
				if (i<0) {debuffself+=i;}
				else {buffself+=i;}
				
			}
			int buffenemy = 0;					//se hai buffato il difensore    (se applichiamo effetti grafici ai debuff)
			int debuffenemy = 0;					//o se hai debuffato il difensore  (se applichiamo effetti grafici ai debuff)
			
			Buff = new int[] {mossa.getDebuffAtk(),mossa.getDebuffAtkSp(),mossa.getDebuffDif(),mossa.getDebuffDifSp(),mossa.getDebuffPrec(),mossa.getDebuffVel()}; 
			for (int i : Buff) {
				if (i<0) {debuffenemy+=i;}
				else {buffenemy+=i;}
				
			}
			
			int Contraccolpo = (int)(danno*mossa.getContraccolpo());
			
			standby=true;
			//Comunico a gui: VITA INIZIALE DIFENSORE, VITA INIZIALE ATTACCANTE                            , IL TIPO   , EFFICACIA     , CHI SONO I PROTAGONIST, IL GIOCATORE      , SE SONO STATI APPLICATI BUFF O DEBUFF
			Gui.displayAzione(primadisubiredanno, Contraccolpo+attaccante.getPs()-puntiCura, tipo_mossa, efficaciaMossa, attaccante, difensore, GiocatoreAttaccante, buffself, debuffself ,buffenemy, debuffenemy, mossa, bruttoColpo);
			startStandby();
			
		
		}//Fine applicazione effetti
		
		else {
			 //la mossa manca il bersaglio, non succede nulla
			 Gui.displayMiss(GiocatoreAttaccante);
			 }
		
		} //Fine if che determina se la mossa ha abbastanza PP (primo if)
	
	}//Fine esecuzione

/**
 * Assegna punti esperienza a tutti i Pokemon che hanno sconfitto il Pokemon morto e gestisce
 * l'eventuale aumento di livello.
 *
 * @param morto Il Pokemon sconfitto.
 * @param attaccante Il Pokemon che ha sconfitto il Pokemon morto.
 * @param GiocatoreBeneficiario L'indice del giocatore beneficiario degli eventi.
 */
private void assegnaExpDaKill(Pokemon morto, Pokemon attaccante, int GiocatoreBeneficiario) {
	//pokemon scesi in campo e sopravvissuti contro il pokemon morto
	int s=morto.getWhoHitMe().length;

	double a=1.5;

	int[]statisticheBase={morto.getAttaccoBase(),morto.getDifesaBase(),morto.getAttaccoSpBase(),morto.getDifesaSpBase(),morto.getVelocitàBase()};
	//b è la statistica base maggiore del pokemon morto
	int b=morto.getPsBase();	
	for (int statisticaBase : statisticheBase) {
		if (b<statisticaBase) {b=statisticaBase;}
	}
	// ad ogni pokemon sceso in campo assegni i punti exp e in caso salgono di livello 
	for (Pokemon killer : morto.getWhoHitMe()) {
		if (killer.getPs()>0) {
			killer.setPuntiExp((int)(killer.getPuntiExp()+(a*b*morto.getLivello()*10/(7*s))));
			}
		if (attaccante.equals(killer)) {
			Gui.displayXPChange(GiocatoreBeneficiario, (a*b*morto.getLivello()*10/(7*s)));
			}
		while (killer.getPuntiExp()>((killer.getLivello()+1)*(killer.getLivello()+1)*(killer.getLivello()+1))) {
			killer.setLivello(killer.getLivello()+1);
			killer.livelloSu();
			Mossa mossaapprendibile = killer.getMosseApprendibili();
			if (mossaapprendibile!=null) {

				//METODO GUI che dica alla GUI di scegliere la mossa------------------
				//IF POKEMON DI G1, RUNNA METODO G1, ELSE G2
				//INFINE SPEGNE LO STANDBYSECONDO a lavoro finito
				standbySecondo=true;
				Gui.displayNuovaMossa(killer,GiocatoreBeneficiario, mossaapprendibile);
				startStandbySecondo();
				mossaapprendibile = null;
			}
		}		
	}
	morto.emptyWhoHitMe();
}

/**
 * Assegna al Pokemon attaccante i punti di allenamento ottenuti per aver sconfitto il Pokemon difensore.
 * I punti di allenamento sono moltiplicati per 100 per aumentare la loro influenza sulle statistiche del Pokemon.
 *
 * @param attaccante Il Pokemon che ha sconfitto il Pokemon difensore e riceve i punti di allenamento.
 * @param difensore Il Pokemon difensore sconfitto da cui il Pokemon attaccante ottiene i punti di allenamento.
 */
private void assegnaPuntiAllenamento(Pokemon attaccante,Pokemon difensore) {
	attaccante.setPuntiAllenamentoPs(attaccante.getPuntiAllenamentoPs()+difensore.getPsBase()*100);
	attaccante.setPuntiAllenamentoAtk(attaccante.getPuntiAllenamentoAtk()+difensore.getAttaccoBase()*100);
	attaccante.setPuntiAllenamentoDif(attaccante.getPuntiAllenamentoDif()+difensore.getDifesaBase()*100);
	attaccante.setPuntiAllenamentoAtkSp(attaccante.getPuntiAllenamentoAtkSp()+difensore.getAttaccoSpBase()*100);
	attaccante.setPuntiAllenamentoDifSp(attaccante.getPuntiAllenamentoDifSp()+difensore.getDifesaSpBase()*100);
	attaccante.setPuntiAllenamentoVel(attaccante.getPuntiAllenamentoVel()+difensore.getVelocitàBase()*100);
}

/**
 * Metodo che gestisce l'attesa durante la fase di standby.
 * Durante questa fase, il metodo mette in pausa l'esecuzione per 1 secondo ciclicamente
 * fino a quando non viene interrotto dall'esterno (quando la condizione di standbySecondo diventa falsa).
 * (Questo standby viene usato esclusivamente per l'apprendimento delle mosse)
 */
private void startStandbySecondo()
{
	
	while(standbySecondo!=false) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
}}

/**
 * Metodo che interrompe la fase di standby impostando la variabile
 * standbySecondo a false e stampando un messaggio di conferma.
 */
public void stopStandbySecondo() {
	standbySecondo=false;
	System.out.println("--------------(2)");
	
}

/**
 * Metodo che gestisce l'attesa durante la fase di standby.
 * Durante questa fase, il metodo mette in pausa l'esecuzione per 1 secondo ciclicamente
 * fino a quando non viene interrotto dall'esterno (quando la condizione di standbySecondo diventa falsa).
 */
private void startStandby() {
	
	while(standby!=false) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
}}

/**
 * Metodo che interrompe la fase di standby impostando la variabile
 * standbySecondo a false e stampando un messaggio di conferma.
 */
public void stopStandby() {
	standby=false;
	System.out.println("--------------(1)");
}

/**
 * Gestisce l'evento di eliminazione di un Pokemon durante il combattimento.
 * Questo metodo assegna esperienza al Pokemon che ha effettuato il KO, 
 * aggiunge punti allenamento al Pokemon attaccante e gestisce il cambio Pokemon
 * se il giocatore difensore ha ancora Pokemon disponibili. Se il giocatore difensore
 * non ha più Pokemon disponibili, termina il combattimento e determina il vincitore.
 * 
 * @param attaccante il Pokemon che ha effettuato l'attacco
 * @param difensore il Pokemon che è stato eliminato
 * @param GiocatoreDifensore l'indice del giocatore che possiede il Pokemon difensore
 */
private void pokedead(Pokemon attaccante,Pokemon difensore,int GiocatoreDifensore) 
{	
	 //assegnaExpDaKill(difensore);
	 switch(GiocatoreDifensore) 
	 	{
	 	case(0):	
	 		assegnaExpDaKill(difensore, attaccante, 1);
        	break;
	 	case(1):	
	 		assegnaExpDaKill(difensore, attaccante, 0);
        	break;
	 	}
	//Assegna Punti allenamento al killer
    assegnaPuntiAllenamento(attaccante, difensore); 
    
    //Se allenatore che ha subito pokedead ha pokemon, starta un cambio pokemon, ELSE CONCLUDI
    if (Giocatori[GiocatoreDifensore].getPokemonRimasti()>0) {
       
    	//RUNNI UN CAMBIO DI POKEMON e da là levi lo STANDBY
    	standby=true;
    	Gui.displayCambioPokemon(GiocatoreDifensore);
    	startStandby();
    	if (GiocatoreDifensore==0) {SceltaGiocatore1=false;}
    	else {SceltaGiocatore2=false;}

    }
    else {

        InCorso=false;
        int Winner;
        if (GiocatoreDifensore == 0) 
        	{
        	Winner=1;
        	}
        else 
        	{
        	Winner=0;
        	}
        
       System.out.println(Giocatori[GiocatoreDifensore].getNome()+" PERDE!!!");
       System.out.println(Giocatori[Winner].getNome()+" VINCE!!!");
       Gui.updateDisplayWin(Winner, GiocatoreDifensore, Giocatori[Winner], Giocatori[GiocatoreDifensore]);
    }

}

}