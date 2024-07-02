import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

public class JPokeBattle implements java.lang.Runnable
{
	private static final String LOCK_FILE = "programlock.txt";
	private final String Versione = "Versione 1.0";
	private static GUI Gui;
	
	
	/**
	 * Avvia la Gui, dal quale verranno create le tutte le istanze delle
	 * altre classi presenti nel gioco.
	 * Dal Main è anche possibile avviare il gioco con settings speciali
	 * (Principalemente per supportare il development)
	 */
	public static void main(String[] args)
	{
		
		//-----------------------------------
		
		//Check audio :D
		audiotesting();
		
		//-----------------------------------
		
		//Cancella tutto e ricrea i salvataggi dei pokemon e delle mosse in caso di corruzione
		//deleteAll();
		
		//Cancella tutti gli Allenatori
		//deleteAllUsers();
		
		//Cancella tutti i Pokemonz
		//deleteAllPokemonz();
		
		//Cancella tutte le Mosse
		//deleteAllMosse();
		
		//Ricrea i salvataggi dei pokemon e delle mosse in caso di corruzione
		//reInizializzaSalvataggi();
		
		//-----------------------------------
		
		//Il metodo crea attualmente DIO(drio)
		//testing();
		
		//Chiusura automatica dopo tempo scelto. If 0 tempo infinito
		//Il boolean serve a bypassare il sistema di controllo che impedisce l'apertura di un secondo gioco in backround
		tempGui(0, false);		
		//Gui = new GUI();
		
		//Messaggio displayato dopo chiusura auto della gui
		System.out.println("\n\n\nFrancesco.equals(Nubetin) is Always True.\n-Main Tzu, l'Arte di Sgamare Nubetinz");
		
	}

	
	//----------------------------------------
	//            Testing area
	//----------------------------------------
	
	//Metodo testing, può contenere qualsiasi cosa
	private static void testing() {
		
		Mossa Ruggito 			= new Mossa("Ruggito",			Tipi.NORMALE.typeNumber , 0, 0,    1, 0, 1, 0, 0, 0,1, 0, 0,1, 1, 0, 0, false,   100, 40,"Il Pokemon ruggisce con cattiveria. Viene ridotto l'attacco dell'avversario.");

		
		Mossa Furia      =new Salvataggio<Mossa>().getItem("assets/Mosse/Furia.ser");
		Mossa Perforbecco=new Salvataggio<Mossa>().getItem("assets/Mosse/Perforbecco.ser");
		Mossa Ira        =new Salvataggio<Mossa>().getItem("assets/Mosse/Ira.ser");
		Mossa Agilità    =new Salvataggio<Mossa>().getItem("assets/Mosse/Agilità.ser");
		
		Ruggito.setPP(1);
		Furia.setPP(1);
		Perforbecco.setPP(1);
		Ira.setPP(1);
		
		Pokemon SuperDodrio= new Pokemon(
										"ApexDodrio",
										"assets/Immagini/Pokemonz/Fronte/Dodrio.png"    , 
										"assets/Immagini/Pokemonz/Retro/Dodrio.png"    , 
										"assets/Immagini/Pokemonz/Piccolo/Dodrio.png"    , 		 
										5,  5, Tipi.NORMALE.typeNumber, Tipi.VOLANTE.typeNumber,  60, 150, 70,  60, 60, 150, 
										new Mossa[]{Ruggito,Furia,Perforbecco,Ira}, 			     
										new int[]{12}, 	 
										new Mossa[] {Agilità},    						  
										"ApexDodrio2"
										);
		
		Pokemon SuperDodrio2= new Pokemon(
				"ApexDodrio2",
				"assets/Immagini/Pokemonz/Fronte/Dodrio.png"    , 
				"assets/Immagini/Pokemonz/Retro/Dodrio.png"    , 
				"assets/Immagini/Pokemonz/Piccolo/Dodrio.png"    , 		 
				5,  5, Tipi.NORMALE.typeNumber, Tipi.VOLANTE.typeNumber,  60, 150, 70,  60, 60, 150, 
				new Mossa[]{Ruggito,Furia,Perforbecco,Ira}, 			     
				new int[]{12}, 	 
				new Mossa[] {Agilità},    						  
				"ApexDodrio3"
				);
		
		Pokemon SuperDodrio3= new Pokemon(
				"ApexDodrio3",
				"assets/Immagini/Pokemonz/Fronte/Dodrio.png"    , 
				"assets/Immagini/Pokemonz/Retro/Dodrio.png"    , 
				"assets/Immagini/Pokemonz/Piccolo/Dodrio.png"    , 		 
				5,  0, Tipi.NORMALE.typeNumber, Tipi.VOLANTE.typeNumber,  60, 150, 70,  60, 60, 150, 
				new Mossa[]{Ruggito,Furia,Perforbecco,Ira}, 			     
				new int[]{12}, 	 
				new Mossa[] {Agilità},    						  
				"ApexDodrio5"
				);
		
		new Salvataggio<Pokemon>().salvaItem(SuperDodrio, "assets/Pokemonz", SuperDodrio.getNome());
		new Salvataggio<Pokemon>().salvaItem(SuperDodrio2, "assets/Pokemonz", SuperDodrio2.getNome());
		new Salvataggio<Pokemon>().salvaItem(SuperDodrio3, "assets/Pokemonz", SuperDodrio3.getNome());
		
		/*Prende i pokemon da dare agli allenatori

		
		
		Pokemon p1A=new Salvataggio<Pokemon>().getItem("assets/Pokemonz/Bulbasaur.ser");
		Pokemon p2A=new Salvataggio<Pokemon>().getItem("assets/Pokemonz/Bulbasaur.ser");
		Pokemon p3A=new Salvataggio<Pokemon>().getItem("assets/Pokemonz/Bulbasaur.ser");
		Pokemon p4A=new Salvataggio<Pokemon>().getItem("assets/Pokemonz/Bulbasaur.ser");
		Pokemon p5A=new Salvataggio<Pokemon>().getItem("assets/Pokemonz/Bulbasaur.ser");
		Pokemon p6A=new Salvataggio<Pokemon>().getItem("assets/Pokemonz/Bulbasaur.ser");
		
		Pokemon p1B=new Salvataggio<Pokemon>().getItem("assets/Pokemonz/Bulbasaur.ser");
		
		p1A.starterPokemon();
		p2A.starterPokemon();
		p3A.starterPokemon();
		p4A.starterPokemon();
		p5A.starterPokemon();
		p6A.starterPokemon();
		
		p1B.starterPokemon();
		*/
		
		//SuperDodrio.starterPokemon();
		
		//Autocreazione G1,G2,G3
		//Allenatore G1 = new Allenatore(new Pokemon[] {SuperDodrio}  , "G1", null, null);
		//Allenatore G2 = new Allenatore(new Pokemon[] {SuperDodrio,SuperDodrio}  , "G2", null, null);
		//Allenatore G3 = new Allenatore(new Pokemon[] {SuperDodrio,SuperDodrio,SuperDodrio}  , "G3", null, null);
		//Allenatore G4 = new Allenatore(new Pokemon[] {SuperDodrio,SuperDodrio,SuperDodrio,SuperDodrio}  , "G4", null, null);
		//Allenatore G2 = new Allenatore(new Pokemon[] {p1B}		, "G2", null, null);
		//Allenatore G3 = new Allenatore(new Pokemon[] {p1A,p2A,p3A,p4A,p5A,p6A} , "G3", null, null);
		//new Salvataggio<Allenatore>().salvaItem(G1,"saves", "G1");
		//new Salvataggio<Allenatore>().salvaItem(G2,"saves", "G2");
		//new Salvataggio<Allenatore>().salvaItem(G3,"saves", "G3");
		//new Salvataggio<Allenatore>().salvaItem(G4,"saves", "G4");
		//new Salvataggio<Allenatore>().salvaItem(G2,"saves", "G2");
		//new Salvataggio<Allenatore>().salvaItem(G3,"saves", "G3");
		
		
	}
	
	//Metodo testing audio
	private static void audiotesting() 
		{
		Soundboard s = new Soundboard();
		s.setVolume(-30);
		s.start("ButtonEffect", 0);
		//s.start("MAXMAX", 0);
		//s.stop(5200);
		}
	
	//----------------------------------------
	//            Dev Tools
	//----------------------------------------
	
	/**
	 * Distrugge tutti i dati esistenti e ricrea l'ambiente di gioco.
	 * Questo metodo elimina tutti gli utenti, Pokemon, mosse e reinizializza i salvataggi.
	 * (Metodo usato solo durante il development)
	 */
	private static void deleteAll() {
		
		deleteAllUsers();
		deleteAllPokemonz();
		deleteAllMosse();
		reInizializzaSalvataggi();
		
	}
	
	/**
	 * Ricrea tutti i salvataggi del gioco, incluse le mosse dei Pokemon e i dati di base dei Pokemon stessi.
	 * Utilizza un elenco predefinito di mosse e Pokemon per inizializzare il gioco.
	 * (Metodo usato solo durante il development)
	 */
	private static void reInizializzaSalvataggi() 
		{

		//STAT TEMPORANEE, FRANCESCO FIXA TE
		Mossa Scontro           = new Mossa("Scontro",          Tipi.NORMALE.typeNumber, 50, 0.50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,    100, 10000,"Una carica con tutto il corpo.");		//creo ogni mossa
		//                                   nome                        t               pw cC    bA bD bASbDsbV bP dA dD dASdDSdV dP eF  pRi       pR  PP   
		Mossa Azione 			= new Mossa("Azione", 			Tipi.NORMALE.typeNumber, 35, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,    95, 35,"Una carica con tutto il corpo.");
		Mossa Ruggito 			= new Mossa("Ruggito",			Tipi.NORMALE.typeNumber , 0, 0,    0, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0, 0, false,   100, 40,"Il Pokemon ruggisce con cattiveria. Viene ridotto l'attacco dell'avversario.");
		Mossa Parassiseme 		= new Mossa("Parassiseme", 		Tipi.ERBA.typeNumber, 	  0, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, false,    90, 10,"Vengono piantati semi sul bersaglio. Questi sottraggono PS permettendo a chi la usa di curarsi.");
		Mossa Frustata 			= new Mossa("Frustata", 		Tipi.ERBA.typeNumber, 	 35, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 10,"Infligge danni al nemico con liane sottili simili a fruste.");
		Mossa Velenpolvere 		= new Mossa("Velenpolvere", 	Tipi.VELENO.typeNumber,   0, 0,    0, 0, 0, 0, 0, 0, 0,-1, 0,-1, 0, 0, 0, false,    75, 35,"Una nube di polvere velenosa è sparsa sul nemico. Può ridurre difesa e difesa speciale.");
		Mossa Foglielama 		= new Mossa("Foglielama", 		Tipi.ERBA.typeNumber, 	 55, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, false,    95, 25,"Foglie taglienti sferzano i nemici intorno. Probabile brutto colpo.");
		Mossa Crescita 			= new Mossa("Crescita", 		Tipi.NORMALE.typeNumber,  0, 0,    0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 12800, 40,"Provoca la crescita del corpo e aumenta l'Att. Sp.");
		Mossa Sonnifero			= new Mossa("Sonnifero", 		Tipi.ERBA.typeNumber,     0, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, false,    75, 15,"Investe il bersaglio con una grande nuvola di polvere soporifera che lo costringe a ritirarsi.");
		Mossa Solaraggio 		= new Mossa("Solaraggio", 		Tipi.ERBA.typeNumber, 	120, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,    80,  5,"Assorbe luce e proietta un raggio intenso.");//funziona come idropompa
		Mossa Graffio 			= new Mossa("Graffio", 			Tipi.NORMALE.typeNumber, 40, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 35,"Graffia con artigli affilati.");
		Mossa Braciere 			= new Mossa("Braciere", 		Tipi.FUOCO.typeNumber, 	 40, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 25,"Un debole attacco di fuoco.");
		Mossa Fulmisguardo		= new Mossa("Fulmisguardo", 	Tipi.NORMALE.typeNumber,  0, 0,    0, 0, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0, false,   100, 30,"Uno sguardo fulminante riduce la Difesa del nemico.");
		Mossa Ira 				= new Mossa("Ira", 				Tipi.NORMALE.typeNumber, 20, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, false,   100, 20,"Si scaglia infuriato da 2 a 5 volte contro il nemico.");
		Mossa Lacerazione 		= new Mossa("Lacerazione", 		Tipi.NORMALE.typeNumber, 70, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, false,   100, 20,"Attacco di artigli e simili: probabile brutto colpo.");
		Mossa Lanciafiamme 		= new Mossa("Lanciafiamme", 	Tipi.FUOCO.typeNumber, 	 95, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 15,"Il nemico viene colpito da intense fiammate.");
		Mossa Turbofuoco		= new Mossa("Turbofuoco", 		Tipi.FUOCO.typeNumber, 	120, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,    80,  5,"Scaglia contro il bersaglio un turbine di fuoco.");//funziona come idropompa
		Mossa Colpocoda 		= new Mossa("Colpocoda", 		Tipi.NORMALE.typeNumber,  0, 0,    0, 0, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0, false,   100, 30,"Agita la coda per ridurre la Difesa del nemico.");
		Mossa Bolla 			= new Mossa("Bolla", 			Tipi.ACQUA.typeNumber,   20, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1, 0, 0, false,   100, 30,"Bolle che possono ridurre la Velocità del nemico.");
		Mossa Pistolacqua 		= new Mossa("Pistolacqua", 		Tipi.ACQUA.typeNumber,   40, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 25,"Il nemico è colpito da un potente getto d'acqua.");
		Mossa Morso 			= new Mossa("Morso", 			Tipi.NORMALE.typeNumber, 60, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 25,"Il nemico viene morso da denti affilatissimi.");
		Mossa Ritirata 			= new Mossa("Ritirata", 		Tipi.ACQUA.typeNumber,    0, 0,    0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 12800, 40,"l corpo si ritira nel suo duro guscio per aumentare la Difesa.");
		Mossa Capocciata		= new Mossa("Capocciata", 		Tipi.NORMALE.typeNumber,100, 0.25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 10,"Abbassa la testa per sferrare una potente carica.");
		Mossa Idropompa			= new Mossa("Idropompa", 		Tipi.ACQUA.typeNumber,  120, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,    80,  5,"Il nemico è travolto da un potente getto d'acqua.");
		Mossa Beccata 			= new Mossa("Beccata", 			Tipi.VOLANTE.typeNumber, 35, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 35,"Colpisce il nemico con un becco appuntito o un corno.");
		Mossa Furia 			= new Mossa("Furia", 			Tipi.NORMALE.typeNumber, 15, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, false,    85, 20,"Corna affilate infilzano il nemico da 2 a 5 volte.");
		Mossa Perforbecco 		= new Mossa("Perforbecco", 		Tipi.VOLANTE.typeNumber, 80, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 20,"Attacco a spirale con un becco aguzzo che fa da trapano.");
		Mossa Tripletta 		= new Mossa("Tripletta", 		Tipi.NORMALE.typeNumber, 80, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 10,"Colpisce il nemico con tre sfere simultanee");
		Mossa Agilità 			= new Mossa("Agilità", 			Tipi.PSICO.typeNumber,    0, 0,    0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, false, 12800, 30,"Chi la usa rilassa e alleggerisce il proprio corpo per far salire di molto la Velocità.");
		Mossa ColpoBasso 		= new Mossa("ColpoBasso", 		Tipi.LOTTA.typeNumber,   50, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 20,"Un calcio che tenta di sbilanciare il nemico.");
		Mossa Colpokarate 		= new Mossa("Colpokarate", 		Tipi.NORMALE.typeNumber, 50, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, false,   100, 25,"Colpo netto. Probabile brutto colpo.");
		Mossa Sfuriate 			= new Mossa("Sfuriate", 		Tipi.NORMALE.typeNumber, 18, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, false,    80, 15,"Colpisce il nemico con artigli o falci affilate da 2 a 5.");
		Mossa MovimentoSismico 	= new Mossa("Mov. Sismico",	    Tipi.LOTTA.typeNumber,    0, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, false,   100, 20,"Fa precipitare a terra il bersaglio. Il danno è pari al livello.");
		Mossa Stridio 			= new Mossa("Stridio", 			Tipi.NORMALE.typeNumber,  0, 0,    0, 0, 0, 0, 0, 0, 0,-2, 0, 0, 0, 0, 0, false,    85, 40,"Stridio assordante che riduce di molto la Difesa del nemico.");
		Mossa Colpo 			= new Mossa("Colpo", 		   	Tipi.NORMALE.typeNumber, 90, 0,    0,-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, false,   100, 20,"Assale e attacca il nemico per 2 o 3 volte, ma riduce la difesa di chi la usa.");
		Mossa AttaccoRapido 	= new Mossa("AttaccoRapido", 	Tipi.NORMALE.typeNumber, 40, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, true,    100, 30,"Attacco rapidissimo che colpisce sempre per primo.");
		Mossa Tuonoshock 		= new Mossa("Tuonoshock", 		Tipi.ELETTRO.typeNumber, 40, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 30,"Danneggia il bersaglio con una scarica elettrica.");
		Mossa Tuonopugno 		= new Mossa("Tuonopugno", 		Tipi.ELETTRO.typeNumber, 75, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 15,"Colpisce il nemico con un pugno elettrico.");
		Mossa Schermoluce 		= new Mossa("Schermoluce", 		Tipi.PSICO.typeNumber,    0, 0,    0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 12800, 30,"Innalza una barriera di luce per aumentare di molto la difesa speciale");
		Mossa Tuono 			= new Mossa("Tuono",			Tipi.ELETTRO.typeNumber,120, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,    70, 10,"Il nemico è colpito da un tuono.");
		Mossa Cinèsi 			= new Mossa("Cinèsi", 			Tipi.PSICO.typeNumber,    0, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1, 0, false,    80, 15,"Distrae il nemico e può ridurne la precisione.");
		Mossa Teletrasporto 	= new Mossa("Teletrasporto",	Tipi.PSICO.typeNumber,    0, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, false, 12800, 20,"L'utilizzatore torna dall'allenatore.");
		Mossa Confusione 		= new Mossa("Confusione",		Tipi.PSICO.typeNumber,   50, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 25,"Colpisce il nemico con una leggera forza telecinetica");
		Mossa Inibitore 		= new Mossa("Inibitore", 		Tipi.NORMALE.typeNumber,  0, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, false,    80, 20,"Forza psichica: disattiva una mossa del nemico.");
		Mossa Psicoraggio 		= new Mossa("Psicoraggio", 		Tipi.PSICO.typeNumber, 	 65, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 20,"Il nemico è attaccato con un raggio psichico.");
		Mossa Ripresa 			= new Mossa("Ripresa",			Tipi.PSICO.typeNumber, 	  0, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, false,     0,  0,"Il Pokemon ripristina i suoi PS fino a metà dei suoi PS massimi.");
		Mossa Psichico 			= new Mossa("Psichico",			Tipi.PSICO.typeNumber ,  90, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 10,"Il nemico viene colpito da una potente forza telecinetica");
		Mossa Riflesso 			= new Mossa("Riflesso", 		Tipi.PSICO.typeNumber, 	  0, 0,    0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 12800, 20,"Innalza una barriera di luce per aumentare di molto la difesa.");
		Mossa Test2 = new Mossa("moss-NULL", 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 0, 0,"");
		Mossa Leccata           = new Mossa("Leccata", 			Tipi.SPETTRO.typeNumber ,20, 0,	   0, 0, 0, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0, false,   100, 30,"Una lingua lunga infligge danni al nemico e lo spaventa diminuendone la difesa.");
		Mossa Stordiraggio      = new Mossa("Stordiraggio", 	Tipi.SPETTRO.typeNumber , 0, 0,	   0, 0, 0, 0, 0, 0,-1, 0, 0, 0,-1, 0, 0, false,   100, 10,"Il nemico è colpito da un raggio sinistro che diminuisce attacco e velocità.");
		Mossa OmbraNotturna     = new Mossa("OmbraNotturna", 	Tipi.SPETTRO.typeNumber , 0, 0,	   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, false,   100, 15,"Fa apparire un orribile miraggio al nemico e infligge un danno pari al livello di chi la usa.");
		Mossa Ipnosi            = new Mossa("Ipnosi", 			Tipi.PSICO.typeNumber ,   0, 0,	   0, 0, 0, 0, 0, 0,-1,-1,-1,-1,-1, 0, 0, false, 	60, 20,"Chi la usa si avvale della suggestione ipnotica e diminusice tutte le statistiche del nemico");
		Mossa Mangiasogni       = new Mossa("Mangiasogni", 		Tipi.PSICO.typeNumber , 100, 0,	   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 15,"Attaccia il subcosncio del nemico rubandone i ricordi.");
		Mossa Canto             = new Mossa("Canto", 			Tipi.NORMALE.typeNumber , 0, 0,	   0, 0, 0, 0, 0, 0,-1,-1,-1,-1,-1, 0, 0, false,   	55, 15,"Una dolce canzone stordisce il nemico, diminusice tutte le statistiche del nemico.");
		Mossa Nebbia       		= new Mossa("Nebbia", 			Tipi.GHIACCIO.typeNumber ,0, 0,	   0, 0, 0, 0, 0,-1, 0, 0, 0, 0, 0,-1, 0, false,    75, 30,"Chi la usa attira una nebbia che diminuisce la precisione dei pokemon in campo.");
		Mossa Corposcontro      = new Mossa("Corposcontro", 	Tipi.NORMALE.typeNumber ,85, 0,	   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 15,"Il Pokémon attacca il bersaglio scagliandosi contro di lui con tutto il corpo.");
		Mossa Geloraggio        = new Mossa("Geloraggio", 		Tipi.GHIACCIO.typeNumber,95, 0,	   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 10,"Il nemico è colpito da un raggio di energia gelida");
		Mossa Focalenergia      = new Mossa("Focalenergia", 	Tipi.NORMALE.typeNumber , 0, 0,	   1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 12800, 30,"Chi la usa fa un profondo respiro e raccoglie le energie per aumentare l'attacco.");
		Mossa AttaccodAla       = new Mossa("AttaccodAla", 		Tipi.VOLANTE.typeNumber ,50, 0,	   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 25,"Colpisce il nemico spiegando le ali.");
		Mossa Danzaspada       	= new Mossa("Danzaspada", 		Tipi.NORMALE.typeNumber , 0, 0,	   2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 12800, 30,"Danza che migliora lo spirito di combattimento. Fa salire di molto l'Attacco.");
		Mossa Doppioteam		= new Mossa("Doppioteam", 		Tipi.NORMALE.typeNumber , 0, 0,	   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1, 0, false, 12800, 15,"Il Pokémon crea copie illusorie di se stesso diminuendo la precisione del nemico");
		Mossa Incornata       	= new Mossa("Incornata", 		Tipi.NORMALE.typeNumber ,65, 0,	   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 25,"Danneggia il nemico infilzandolo con un corno affilato.");
		Mossa Pestone       	= new Mossa("Pestone", 			Tipi.TERRA.typeNumber ,  50, 0,	   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 20,"Colpisce il nemico con un grosso piede facendo tremare la terra.");
		Mossa Riduttore       	= new Mossa("Riduttore", 		Tipi.NORMALE.typeNumber ,90, 0.25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,    85, 15,"Carica spericolata con tutto il corpo contro il nemico. Danneggia un po' anche chi la usa.");
		Mossa Perforcorno       = new Mossa("Perforcorno", 		Tipi.ROCCIA.typeNumber ,100, 0,	   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,    80,  5,"Colpisce il nemico con un corno perforante come un trapano.");
		Mossa Avvolgibotta      = new Mossa("Avvolgibotta", 	Tipi.NORMALE.typeNumber ,15, 0,    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-1, 0, 1, false,    85, 20,"Avvolge e stringe il nemico da 2 a 5 volte.");
		Mossa Tuononda          = new Mossa("Tuononda", 		Tipi.ELETTRO.typeNumber , 0, 0,	   0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-2, 0, 0, false,   100, 20,"Il nemico è colpito da una debole scarica elettrica,diminuisce di molto la velocità.");
		Mossa Schianto	  	    = new Mossa("Schianto", 		Tipi.NORMALE.typeNumber ,70, 0,	   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,   100, 15,"Infligge danni al nemico con una coda.");
		Mossa IraDiDrago        = new Mossa("IraDiDrago", 		Tipi.DRAGO.typeNumber ,   0, 0,	   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,10, false,   100, 10,"Colpisce il nemico con un'onda d'urto generata dall'ira. Il danno è sempre di 40 PS.");
		Mossa IperRaggio        = new Mossa("IperRaggio", 		Tipi.NORMALE.typeNumber,120, 0,	   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false,    80,  5,"Colpisce il nemico con un raggio devastante.");

		
		//Salva ogni Mossa
		Mossa [] ListaMosseTotali = new Mossa[] {Scontro,IperRaggio,IraDiDrago,Schianto,Tuononda,Avvolgibotta,Perforcorno,Riduttore,Pestone,Incornata,Danzaspada,Doppioteam,AttaccodAla,Focalenergia,Geloraggio,Corposcontro,Nebbia,Canto,Mangiasogni,Stordiraggio,Ipnosi,OmbraNotturna,Leccata,Azione, Test2, Ruggito, Parassiseme ,Frustata,Velenpolvere ,Foglielama, Crescita, Sonnifero, Solaraggio, Graffio, Braciere, Fulmisguardo ,Ira, Lacerazione ,Lanciafiamme ,Turbofuoco ,Colpocoda, Bolla ,Pistolacqua ,Morso ,Ritirata ,Capocciata ,Idropompa ,Beccata, Furia ,Perforbecco, Tripletta, Agilità ,ColpoBasso, Colpokarate, Sfuriate ,MovimentoSismico ,Stridio ,Colpo, AttaccoRapido, Tuonoshock, Tuonopugno ,Schermoluce ,Tuono, Cinèsi ,Teletrasporto, Confusione ,Inibitore ,Psicoraggio ,Ripresa ,Psichico ,Riflesso};
		for (Mossa Mossaa : ListaMosseTotali) 
			{
			new Salvataggio<Mossa>().salvaItem(Mossaa,"assets/Mosse", Mossaa.getNome() );
			}
		
		//Crea ogni pokemon
		Pokemon Bulbasaur 	= new Pokemon("Bulbasaur",  "assets/Immagini/Pokemonz/Fronte/Bulbasaur.png" , "assets/Immagini/Pokemonz/Retro/Bulbasaur.png" , "assets/Immagini/Pokemonz/Piccolo/Bulbasaur.png" , 		 5, 16, Tipi.ERBA.typeNumber,    Tipi.VELENO.typeNumber,   45,  49, 49,  65, 65,  45, new Mossa[]{Azione,Ruggito,null,null}, 			 new int[]{7,13,20,27,34,41,48}, new Mossa[]{Parassiseme,Frustata,Velenpolvere,Foglielama,Crescita,Sonnifero,Solaraggio}, "Ivysaur");
		Pokemon Ivysaur 	= new Pokemon("Ivysaur",    "assets/Immagini/Pokemonz/Fronte/Ivysaur.png"   , "assets/Immagini/Pokemonz/Retro/Ivysaur.png"   , "assets/Immagini/Pokemonz/Piccolo/Ivysaur.png"   , 		 5, 32, Tipi.ERBA.typeNumber,    Tipi.VELENO.typeNumber,   60,  62, 63,  80, 80,  60, new Mossa[]{Azione,Ruggito,null,null}, 			 new int[]{7,13,22,30,38,46,54}, new Mossa[]{Parassiseme,Frustata,Velenpolvere,Foglielama,Crescita,Sonnifero,Solaraggio}, "Venusaur");
		Pokemon Venusaur 	= new Pokemon("Venusaur",   "assets/Immagini/Pokemonz/Fronte/Venusaur.png"  , "assets/Immagini/Pokemonz/Retro/Venusaur.png"  , "assets/Immagini/Pokemonz/Piccolo/Venusaur.png"  , 		 5,  0, Tipi.ERBA.typeNumber,    Tipi.VELENO.typeNumber,   80,  82, 83, 100, 100, 80, new Mossa[]{Azione,Ruggito,null,null}, 			 new int[]{7,13,22,30,43,55,65}, new Mossa[]{Parassiseme,Frustata,Velenpolvere,Foglielama,Crescita,Sonnifero,Solaraggio}, "");
		Pokemon Charmander 	= new Pokemon("Charmander", "assets/Immagini/Pokemonz/Fronte/Charmander.png", "assets/Immagini/Pokemonz/Retro/Charmander.png", "assets/Immagini/Pokemonz/Piccolo/Charmander.png",		 5, 16, Tipi.FUOCO.typeNumber,   0,  		               39,  52, 43,  60, 50,  65, new Mossa[]{Graffio,Ruggito,null,null}, 			 new int[]{9,15,22,30,38,46},	 new Mossa[]{Braciere,Fulmisguardo,Ira,Lacerazione,Lanciafiamme,Turbofuoco}, 			  "Charmeleon");
		Pokemon Charmeleon 	= new Pokemon("Charmeleon", "assets/Immagini/Pokemonz/Fronte/Charmeleon.png", "assets/Immagini/Pokemonz/Retro/Charmeleon.png", "assets/Immagini/Pokemonz/Piccolo/Charmeleon.png", 	     5, 36, Tipi.FUOCO.typeNumber,   0,                        58,  64, 58,  80, 65,  80, new Mossa[]{Graffio,Ruggito,null,null}, 			 new int[]{9,15,24,33,42,56},	 new Mossa[]{Braciere,Fulmisguardo,Ira,Lacerazione,Lanciafiamme,Turbofuoco}, 			  "Charizard");
		Pokemon Charizard 	= new Pokemon("Charizard",  "assets/Immagini/Pokemonz/Fronte/Charizard.png" , "assets/Immagini/Pokemonz/Retro/Charizard.png" , "assets/Immagini/Pokemonz/Piccolo/Charizard.png" , 		 5,  0, Tipi.FUOCO.typeNumber,   Tipi.VOLANTE.typeNumber,  78,  84, 78, 109, 85, 100, new Mossa[]{Graffio,Ruggito,null,null}, 			 new int[]{9,15,24,36,46,55},	 new Mossa[]{Braciere,Fulmisguardo,Ira,Lacerazione,Lanciafiamme,Turbofuoco}, 			  "");
		Pokemon Squirtle 	= new Pokemon("Squirtle",   "assets/Immagini/Pokemonz/Fronte/Squirtle.png"  , "assets/Immagini/Pokemonz/Retro/Squirtle.png"  , "assets/Immagini/Pokemonz/Piccolo/Squirtle.png"  , 		 5,  0, Tipi.ACQUA.typeNumber,   0,                        44,  48, 65,  50, 64,  43, new Mossa[]{Azione,Colpocoda,null,null}, 		     new int[]{8,15,22,28,35,42}, 	 new Mossa[] {Bolla,Pistolacqua,Morso,Ritirata,Capocciata,Idropompa}, 					  "Wartortle");
		Pokemon Wartortle 	= new Pokemon("Wartortle",  "assets/Immagini/Pokemonz/Fronte/Wartortle.png" , "assets/Immagini/Pokemonz/Retro/Wartortle.png" , "assets/Immagini/Pokemonz/Piccolo/Wartortle.png" , 		 5,  0, Tipi.ACQUA.typeNumber,   0,                        59,  63, 80,  65, 80,  58, new Mossa[]{Azione,Colpocoda,null,null}, 		     new int[]{8,15,24,31,39,47}, 	 new Mossa[] {Bolla,Pistolacqua,Morso,Ritirata,Capocciata,Idropompa},                     "Blastoise");
		Pokemon Blastoise 	= new Pokemon("Blastoise",  "assets/Immagini/Pokemonz/Fronte/Blastoise.png" , "assets/Immagini/Pokemonz/Retro/Blastoise.png" , "assets/Immagini/Pokemonz/Piccolo/Blastoise.png" , 		 5,  0, Tipi.ACQUA.typeNumber,   0,                        79,  83, 100, 85, 105, 78, new Mossa[]{Azione,Colpocoda,null,null}, 		     new int[]{8,15,24,31,42,52},	 new Mossa[] {Bolla,Pistolacqua,Morso,Ritirata,Capocciata,Idropompa}, 					  "");
		Pokemon Dodrio 		= new Pokemon("Dodrio",     "assets/Immagini/Pokemonz/Fronte/Dodrio.png"    , "assets/Immagini/Pokemonz/Retro/Dodrio.png"    , "assets/Immagini/Pokemonz/Piccolo/Dodrio.png"    , 		 5,  0, Tipi.NORMALE.typeNumber, Tipi.VOLANTE.typeNumber,  60, 110, 70,  60, 60, 110, new Mossa[]{Beccata,null,null,null}, 			     new int[]{20,24,30,39,45,51}, 	 new Mossa[] {Ruggito,Furia,Perforbecco,Ira,Tripletta,Agilità},    						  "");
		Pokemon Primeape 	= new Pokemon("Primeape",   "assets/Immagini/Pokemonz/Fronte/Primeape.png"  , "assets/Immagini/Pokemonz/Retro/Primeape.png"  , "assets/Immagini/Pokemonz/Piccolo/Primeape.png"  , 		 5,  0, Tipi.LOTTA.typeNumber, 	 0,                        65, 105, 60,  60, 70,  95, new Mossa[]{Graffio,Fulmisguardo,null,null}, 		 new int[]{9,15,21,28,37,45,46}, new Mossa[] {ColpoBasso,Colpokarate,Sfuriate,Ira,MovimentoSismico,Stridio,Colpo}, 		  "");
		Pokemon Electabuzz 	= new Pokemon("Electabuzz", "assets/Immagini/Pokemonz/Fronte/Electabuzz.png", "assets/Immagini/Pokemonz/Retro/Electabuzz.png", "assets/Immagini/Pokemonz/Piccolo/Electabuzz.png", 	     5,  0, Tipi.ELETTRO.typeNumber, 0,                        65,  83, 57,  95, 85, 105, new Mossa[]{AttaccoRapido,Fulmisguardo,null,null}, new int[]{34,37,42,49,54}, 	 new Mossa[] {Tuonoshock,Stridio,Tuonopugno,Schermoluce,Tuono}, 						  "");
		Pokemon Kadabra 	= new Pokemon("Kadabra",    "assets/Immagini/Pokemonz/Fronte/Kadabra.png"   , "assets/Immagini/Pokemonz/Retro/Kadabra.png"   , "assets/Immagini/Pokemonz/Piccolo/Kadabra.png"   , 		 5,  0, Tipi.PSICO.typeNumber,   0,                        40,  35, 30, 120, 70, 105, new Mossa[]{Cinèsi,Teletrasporto,Confusione,null}, new int[]{20,27,31,38,42}, 	 new Mossa[]{Inibitore,Psicoraggio,Ripresa,Psichico,Riflesso}, 							  "");
		Pokemon Gastly 		= new Pokemon("Gastly",    "assets/Immagini/Pokemonz/Fronte/Gastly.png"   , "assets/Immagini/Pokemonz/Retro/Gastly.png"   , "assets/Immagini/Pokemonz/Piccolo/Gastly.png"   , 		  	5, 25, Tipi.SPETTRO.typeNumber, Tipi.VELENO.typeNumber,   	30,  35, 30, 100, 35,  80, new Mossa[]{Leccata,Stordiraggio,OmbraNotturna,null}, 		new int[]{27,35}, 	 			new Mossa[]{Ipnosi,Mangiasogni}, 							  "Haunter");
		Pokemon Haunter 	= new Pokemon("Haunter",    "assets/Immagini/Pokemonz/Fronte/Haunter.png"   , "assets/Immagini/Pokemonz/Retro/Haunter.png"   , "assets/Immagini/Pokemonz/Piccolo/Haunter.png"   , 		5,  0, Tipi.SPETTRO.typeNumber, Tipi.VELENO.typeNumber,   	45,  50, 45, 115, 55,  95, new Mossa[]{Leccata,Stordiraggio,OmbraNotturna,null}, 		new int[]{29,38}, 				new Mossa[]{Ipnosi,Mangiasogni},  							  "");
		Pokemon Scyther 	= new Pokemon("Scyther",    "assets/Immagini/Pokemonz/Fronte/Scyther.png"   , "assets/Immagini/Pokemonz/Retro/Scyther.png"   , "assets/Immagini/Pokemonz/Piccolo/Scyther.png"   , 		5,  0, Tipi.COLEOTTERO.typeNumber, Tipi.VOLANTE.typeNumber, 70, 110, 80,  55, 80, 105, new Mossa[]{AttaccoRapido,Fulmisguardo,Focalenergia,null}, 	new int[]{24,29,35,42,50}, 	 	new Mossa[]{AttaccodAla,Lacerazione,Danzaspada,Doppioteam,Agilità}, 							  "");
		Pokemon Rhydon 		= new Pokemon("Rhydon",    "assets/Immagini/Pokemonz/Fronte/Rhydon.png"   , "assets/Immagini/Pokemonz/Retro/Rhydon.png"   , "assets/Immagini/Pokemonz/Piccolo/Rhydon.png"   , 			5,  0, Tipi.TERRA.typeNumber,   Tipi.ROCCIA.typeNumber,    105, 130,120,  45, 45,  40, new Mossa[]{Incornata,null,null,null}, 						new int[]{30,35,40,48,55,64}, 	new Mossa[]{Pestone,Colpocoda,Furia,Perforcorno,Fulmisguardo,Riduttore}, 							  "");
		Pokemon Lapras 		= new Pokemon("Lapras",    "assets/Immagini/Pokemonz/Fronte/Lapras.png"   , "assets/Immagini/Pokemonz/Retro/Lapras.png"   , "assets/Immagini/Pokemonz/Piccolo/Lapras.png"   , 			5,  0, Tipi.ACQUA.typeNumber,   Tipi.GHIACCIO.typeNumber,  130,  85, 80,  85, 95,  60, new Mossa[]{Pistolacqua,Ruggito,null,null}, 					new int[]{16,20,25,31,38,46}, 	new Mossa[]{Canto,Nebbia,Corposcontro,Stordiraggio,Geloraggio,Idropompa}, 							  "");
		Pokemon Dragonair 	= new Pokemon("Dragonair", "assets/Immagini/Pokemonz/Fronte/Dragonair.png"   , "assets/Immagini/Pokemonz/Retro/Dragonair.png"   , "assets/Immagini/Pokemonz/Piccolo/Dragonair.png" , 	5,  0, Tipi.DRAGO.typeNumber,   0,                        	61,  84, 65,  70, 70,  70, new Mossa[]{Avvolgibotta,Fulmisguardo,Tuononda,Agilità}, 	new int[]{35,45,55}, 	 		new Mossa[]{Schianto,IraDiDrago,IperRaggio}, 							  "");
		
		
		//Salva ogni Pokemon
		Pokemon [] ListaPokemonTotali = new Pokemon[] {Gastly,Scyther,Rhydon,Dragonair,Lapras,Haunter,Bulbasaur,Kadabra, Electabuzz,Primeape,Dodrio,Blastoise,Ivysaur,Venusaur,Charmander,Charmeleon,Charizard,Squirtle,Wartortle};
		for (Pokemon Pokee : ListaPokemonTotali) 
			{
			new Salvataggio<Pokemon>().salvaItem(Pokee,"assets/Pokemonz", Pokee.getNome() );
			}
		}
	
	/**
	 * Cancella tutti gli utenti del gioco
	 * (Metodo usato solo durante il development)
	 */
	private static void deleteAllUsers() 
		{	
		for (File file : new Salvataggio().getFileAt("saves")) 
			{
			new Salvataggio().deleteItem(file);
			}
		}
	
	/**
	 * Cancella tutti i pokemon del gioco
	 * (Metodo usato solo durante il development)
	 */
	private static void deleteAllPokemonz() 
		{
			for (File file : new Salvataggio().getFileAt("assets/Pokemonz")) 
			{
			new Salvataggio().deleteItem(file);
			}
		}
	
	/**
	 * Cancella tutte le mosse del gioco
	 * (Metodo usato solo durante il development)
	 */
	private static void deleteAllMosse() 
	{
		for (File file : new Salvataggio().getFileAt("assets/Mosse")) 
		{
		new Salvataggio().deleteItem(file);
		}
	}
	
	/**
	 * Avvia una GUI per un periodo di tempo determinato, con la possibilità di bypassare
	 * il controllo che verifica se il programma è già in esecuzione tramite un file di lock.
	 * In caso il bypass sia false, qualora il programma sia già in corso, questo non potrà
	 * essere nuovamente avviato fino alla chiusura del precedente.
	 *
	 * @param Secondi il numero di secondi dopo i quali chiudere automaticamente la GUI (Se 0, tempo indeterminato)
	 * @param bypass se true, elimina immediatamente il file di lock prima della verifica
	 */
	private static void tempGui(int Secondi, boolean bypass) {
		
		if (bypass){
			deleteLockFile();
			}
		
        //Verifica se il programma è già in esecuzione
        if (!createLockFile()) {
        	JOptionPane.showMessageDialog(null, "Il file è già in esecuzione!", "ERRORE! Avvio annullato!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Elimina il file di lock alla chiusura del programma
        Runtime.getRuntime().addShutdownHook(new Thread(JPokeBattle::deleteLockFile));

        //Se c'è un tempo dopo il quale spegnere la GUI, accendila e chiudi dopo il tempo
        if (Secondi != 0) {
            Gui = new GUI();
            try {
                Thread.sleep(Secondi * 1000);
                Gui.dispose();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Chiusura della GUI automatica dopo i " + Secondi + " secondi definiti del main");
        } else {
            //Altrimenti avviala normalmente
            Gui = new GUI();
        }
        
        //Rimuovi il file di lock quando la GUI viene chiusa
        Gui.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                deleteLockFile();
            }
        });
    }

	/**
	 * Crea un file di lock nel percorso specificato.
	 *
	 * @return true se il file di lock è stato creato con successo, false altrimenti
	 */
    private static boolean createLockFile() {
        try {
            return new File(LOCK_FILE).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina il file di lock se esiste.
     */
    private static void deleteLockFile() {
        try {
            Files.deleteIfExists(Paths.get(LOCK_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    //----------------------------------------
	//       Thread dedicato a partita
	//----------------------------------------
	
    /**
     * Chiamato dalla Gui all'inizio del fight uscendo dal PreFight, questo metodo
     * avvia in un secondo Thread un'istanza della classe Partita
     * che svolgerà la parte logica del gioco.
     * Infine collega la Gui alla propria Partitalogica
     */
	@Override
	public void run() {
		
		Allenatore[] P1P2 = Gui.getPersonaggi();
		Partita P = new Partita(P1P2);
		
		P.linkToGUI(Gui); 
		Gui.linkToPartita(P);
		
		P.startGame();
		
		System.out.println("Chiusura Thread ausiliario dedicato a Partita");
		
	}
}