import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.TimerTask;
import javax.swing.*;

/**
 * La classe GUI gestisce completamente la GUI del gioco.
 * Questa classe è responsabile della gestione dei bottoni, listeners e qualsiasi altro elemento grafico.
 * Comunica principalmente con la classe `Partita` per aggiornare la GUI in base agli eventi della logica di gioco.
 * 
 * <p>La classe fa uso delle seguenti classi:</p>
 * <ul>
 *   <li>{@link Soundboard}</li>
 *   <li>{@link Salvataggio}</li>
 *   <li>{@link Pokemon}</li>
 *   <li>{@link Mossa}</li>
 *   <li>{@link MyButton}</li>
 *   <li>{@link MySlider}</li>
 *   <li>{@link Partita}</li>
 *   <li>{@link Allenatore}</li>
 * </ul>
 * 
 * <p>Questa classe include metodi per gestire:</p>
 * <ul>
 *   <li>Creazione e disposizione dei componenti grafici</li>
 *   <li>Gestione degli eventi e listeners</li>
 *   <li>Aggiornamento della GUI in risposta agli eventi di gioco</li>
 *   <li>Salvataggio e caricamento dello stato del gioco</li>
 *   <li>Riproduzione di suoni e musiche di gioco</li>
 * </ul>

 * @authors  Matteo e Francesco, i Leggendari
 * @since   0.1
 */
@SuppressWarnings("serial")
public final class GUI extends JFrame 

	{
	private final String Versione = "Versione 1.0";
	
	//Font Usati Comunemente
	//                                      Nome del Font ,Plain/Bold, Grandezza
	private final Font StatFont = new Font("Bahnschrift SemiBold",Font.PLAIN, 18 );
	private final Font GeneralFont = new Font("Bahnschrift SemiBold",Font.PLAIN,25);
	private final Font StatFontPiccolo = new Font("Bahnschrift SemiBold",Font.PLAIN, 15 );
	
	private Soundboard s;
	private int volume = -30;
	
	//Schiavo Partita
	private Partita Francesco; //connessione stabilita dal main

	//Load immagini base
	private ImageIcon logo;
	
	//Pannelli inferiori/superiori
	private JLayeredPane panLowMainMenu;
	private JLayeredPane panHighMainMenu;
	
	private JLayeredPane panLowNewGame;
	private JLayeredPane panHighNewGame;
	
	//JLayeredPane panLowLoad;
	
	private JLayeredPane panLowMultiplayer;
	private JLayeredPane panHighMultiplayer;
	
	private JLayeredPane panScoreBoard;
	
	//Fight Panel
	private JLayeredPane panLowPreFight; //start
	private JLayeredPane panHighPreFight; //alto start
	private JLayeredPane panHighFight; 		//display
	private JLayeredPane panLowCombatTurno;   //Scelta azione
	private JLayeredPane panLowCombatMosse;   //Mosse
	private JLayeredPane panLowCombatSwitch;  //Cambia Poke
	private JLayeredPane panLowCombatWAIT;
	
	private boolean stop;
	private int indexmossa = 4;
	private int Winp1=0;
	private int Winp2=0;
	
	private JLayeredPane panLowPreFightP1;
	private JLayeredPane panLowPreFightP2;
	
	private JLabel labelIMJPK1;
	private JLabel labelIMJPK2;
	private JLabel labelNamePK1;
	private JLabel labelNamePK2;
	private JLabel labelLVPK1;
	private JLabel labelLVPK2;
	private JLabel labelPSPK1;
	private JLabel labelPSPK2;
	
	private JProgressBar BarraG1;
	private JProgressBar BarraG2;
	private JProgressBar BarraG1XP;
	private JProgressBar BarraG2XP;
	private JLabel DisplayTXT;
	
	//Bottoni NewGame  
	private MyButton[] BottoniP;
	
	
	
	private JTextField NomeGiocatore;     //Indovina cosa conterrà!
	private File[] PokemonEsistenti;	  //Serve che sia globale (per ora), lo usano sia pannello alto che basso (Potrei renderli lo stesso metodo
	
	private JLabel imgP;   //img profilo
	private int indexImgP; //settato a 0 ogni volta da creaNewGame
	
	private Pokemon[] listapokemontemp = new Pokemon[6]; //Array pokemon, viene salvato in giocatore
	private int indexlistapokemontemp = 6;	             //Index in cui viene piazzato pokemon in array

	//Bottoni Multigiocatore *************
	private Allenatore[] players = new Allenatore[2];
	private int indexPlayers =2;	 //Usato per selezionare giocatore con cui giocare
	private Allenatore AllenatoreScongelato; //comodo per comunicare tra il pannello superiore e inferiore
	private MyButton[] BottoniPokemon; // i 6 bottoni pokemon che vengono displayati quando premi su un allenatore
	private JLayeredPane PannelloPokemonT;//pannello contenente i 6 pokemon
	

	int gamerGaming= 0;


	/**
	 * Costruttore della classe GUI.
	 * <p>
	 * Questo costruttore crea il frame principale dell'applicazione e chiama i metodi
	 * per la creazione dei vari pannelli. Configura il frame con un titolo, dimensioni
	 * fisse, un'icona, e lo rende non ridimensionabile. Imposta inoltre la posizione del
	 * frame al centro dello schermo e gestisce la chiusura dell'applicazione.
	 * </p>
	 * <p>
	 * Vengono creati e configurati tutti i pannelli necessari per il corretto funzionamento
	 * dell'applicazione e viene inizializzato un oggetto Soundboard con il volume specificato.
	 * </p>
	 */
	GUI()
	
		{
		logo = new ImageIcon("assets/Immagini/IconaPokeball.png");
		this.setTitle("Pokemon "+Versione);           	//Titolo
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); 	//Chiusura
		this.setLayout(null);                         	//Layout Manuale
		this.setResizable(false);                     	//Dim Fissa
		this.setSize(700,820);                       	//Set Dim
		this.setLocationRelativeTo(null);            	//Appare CentroSchermo
		this.setIconImage(logo.getImage());         	//Icona programma
		
		s = new Soundboard();
		s.setVolume(volume);
		
		
		// Tutti i pannelli
		creaMenuAlto();
		creaMenuBasso();
		creaHighNewGame();
		creaNewGame();
		//creaHighNewGame();
		creaMultiplayer();
		creaHighMultiplayer();
		creaScoreBoard();


		//creaHighFight();
		//creaLowCombatMosse(); 
		//creaLowCombatTurno(); 
		//creaLowCombatSwitch();


		
		//Pannello d'attesa
		panLowCombatWAIT =creaPanel(0, 400, 700, 400); panLowCombatWAIT.setBackground(new Color(0,0,0));
		creaLabelImmagine(0, 0, 686, 400, "assets/Immagini/Schermata Wait.png", panLowCombatWAIT);
		//JLabel pokesouls=creaLabelImmagine(180, 80, 339, 202, "assets/Immagini/Pokemon Souls.png");
		//panLowCombatWAIT.add(pokesouls, Integer.valueOf(1));
		
		
		this.setVisible(true);                        //Rende visibile
		this.repaint();
	}
	
	//----------------------------------------------------------------------
	// 							Builder Pannelli Main
	//----------------------------------------------------------------------
	
	/**
	 * Crea il pannello del menu superiore.
	 * <p>
	 * Questo metodo crea e configura il pannello superiore del menu principale,
	 * impostandone lo sfondo e aggiungendo un'etichetta con l'immagine del logo
	 * "Pokemon Souls". Il pannello viene poi aggiunto al frame principale.
	 * </p>
	 */
	private void creaMenuAlto() 
		{
		
		panHighMainMenu = creaPanel(0, 0, 700, 400);
		
		//Sfondo
		creaLabelImmagine(0,20,700,400, "assets/Immagini/TestSfondoBasso.png", panHighMainMenu);
		JLabel pokesouls=creaLabelImmagine(165, 98, 339, 202, "assets/Immagini/Pokemon Souls.png");
		
		//creaLabelImmagine(0,-23,700,448, "assets/Immagini/SCHERMATA MAIN 3.png", panHighMainMenu);
	
		//Scritta pokesouls
		//JLabel pokesouls=creaLabelImmagine(45, 18, 339, 202, "assets/Immagini/Pokemon Souls.png");
		panHighMainMenu.add(pokesouls, Integer.valueOf(1));
		
		JLabel Cornice=creaLabelImmagine(0, 0, 686, 400, "assets/Immagini/CorniceVuota.png");
		panHighMainMenu.add(Cornice, Integer.valueOf(3));
		this.add(panHighMainMenu);
		
		}
	
	
	/**
	 * Crea il pannello del menu inferiore.
	 * <p>
	 * Questo metodo crea e configura il pannello inferiore del menu principale,
	 * impostandone lo sfondo e aggiungendo vari bottoni per le diverse azioni 
	 * (Nuovo Character, Starta Partita, Scoreboard, Impostazioni, Esci dal gioco).
	 * Ogni bottone ha un listener per gestire le azioni di cambio pannello (dal 
	 * menù principale al pannello che corrisponde al proprio nome)
	 * Il bottone Impostazioni in particolare riporta ad un JFrame separato
	 * dal quale è possibile regolare il volume di gioco ed eliminare utenti.
	 * </p>
	 */
	private void creaMenuBasso() 
		{
		
		//s.start("Main menu 1_2",-1);
		s.start("Main menu 1_2",-1);
		
		panLowMainMenu = creaPanel(0, 400, 700, 400);
		panLowMainMenu.setBackground(new Color(0,0,0));
		panLowMainMenu.setLayout(null); 
		this.add(panLowMainMenu);
		
		//Sfondo
		creaLabelImmagine(0,-16,700,400, "assets/Immagini/YetAnotherSfondoTest.png", panLowMainMenu);
		JLabel Cornice=creaLabelImmagine(0, -19, 686, 400, "assets/Immagini/CorniceVuotaBASSO.png");
		panLowMainMenu.add(Cornice, Integer.valueOf(3));
		
		//creaLabelImmagine(0,-17,700,400, "assets/Immagini/BACKGROUND Basso.png", panLowMainMenu);
		
		MyButton BottoneNG = creaBottone("Nuovo Character",50,45,192,75, panLowMainMenu,  "assets/Immagini/Mosse/1.png", "assets/Immagini/MosseScure/1.png","assets/Immagini/F19275.png" );
		MyButton BottoneMP = creaBottone("Starta Partita",50,150,192,75, panLowMainMenu,  "assets/Immagini/Mosse/10.png", "assets/Immagini/MosseScure/10.png","assets/Immagini/F19275.png" );
		MyButton BottoneSC = creaBottone("Scoreboard",50,255,192,75,panLowMainMenu, "assets/Immagini/Mosse/14.png", "assets/Immagini/MosseScure/14.png","assets/Immagini/F19275.png" );
		MyButton BottoneLEAVE = creaBottone("Esci dal gioco",450,255,192,75,panLowMainMenu, "assets/Immagini/Mosse/3.png", "assets/Immagini/MosseScure/3.png","assets/Immagini/F19275.png" );
		MyButton BottoneOP = creaBottone("",540,35,100,100, panLowMainMenu,  "assets/Immagini/IconaImpostazioni.png", "assets/Immagini/IconaImpostazioniScuro.png","assets/Immagini/F100100.png" );
		
		
		BottoneLEAVE.addActionListener(e ->
		{
			s.quickShoot("ButtonEffect");
            System.exit(0);
			});
		
		
		BottoneNG.addActionListener(e ->
			{
			s.quickShoot("ButtonEffect");
			changePanel(panLowMainMenu, panHighMainMenu, panHighNewGame, panLowNewGame);
			});
		
		BottoneMP.addActionListener(e ->
			{
			s.quickShoot("ButtonEffect");
			changePanel(panLowMainMenu, panHighMainMenu, panLowMultiplayer, panHighMultiplayer);
			});
		
		BottoneSC.addActionListener(e ->
			{
			s.quickShoot("ButtonEffect");
			changePanel(panLowMainMenu,panHighMainMenu, panScoreBoard);
			});
		
		BottoneOP.addActionListener(e ->
			{
			s.quickShoot("ButtonEffect");
			//Frame impostazioni
			JFrame PopUp = new JFrame();
			JLayeredPane Fill = creaPanel(0, 0, 400, 400);
			
			//Txt
			creaLabel("Barra Volume del Gioco!", 50, 20, 300, 50, Fill, GeneralFont, 3 , new Color(245,245,245));
			
			//Immagine
			creaLabelImmagine(0,0, 400, 400, "assets/Immagini/background_fight_scelta.png", Fill);
			
			
			Color bianco = new Color(255,255,255);
			
			//Barra volume
			JSlider slideraudio = new JSlider(0,100,74);
			slideraudio.setBounds(40,60,300,50);
			slideraudio.setOpaque(false);
			slideraudio.addChangeListener(null);
			slideraudio.setPaintTicks(true);
			slideraudio.setPaintTrack(true);
			slideraudio.setMinorTickSpacing(5);
			slideraudio.setMajorTickSpacing(10);
			slideraudio.setPaintLabels(true);
			slideraudio.setFont(StatFontPiccolo);
			slideraudio.setForeground(bianco);
			slideraudio.addChangeListener(k -> {volume = slideraudio.getValue()-94; s.setVolume(volume); this.repaint();});
			//6 -94 60
			
			//Bottone di reset
			MyButton reset = creaBottone("", 40, 170-40, 50, 50, "assets/Immagini/Reset.png", "assets/Immagini/ResetScuro.png", "assets/Immagini/F5050.png");
			reset.addActionListener(k -> {
					s.quickShoot("ButtonEffect");
					int i=JOptionPane.showConfirmDialog(null, "Sicuro di voler cancellare tutti i tuoi salvataggi? \n(Il gioco verrà riavviato)","ATTENZIONE! L'operazione necessita di Conferma", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(i==0) {
						System.out.println("Cancellando");
						s.quickShoot("NUKE",10);
						for (File file : new Salvataggio<>().getFileAt("saves")) 
							{
							new Salvataggio<>().deleteItem(file);
							}
						System.out.println("Chiusura");
						new java.util.Timer().schedule(new TimerTask(){
					        @Override
					        public void run() {
					        	System.exit(0);
					    		
					        }
					    },4200); 
						}
					});
			
			//JLabel
			creaLabel("<html> Cancella tutti gli Allenatori salvati della cartella \"saves\". </html>", 130, 140-40, 240, 100, Fill, StatFont, 2, bianco);
			
			//Cancella un singolo giocatore
			JTextField CancellaGiocatore = new JTextField("Inserisci Allenatore da Cancellare...");
			CancellaGiocatore.setFont(StatFontPiccolo);
			CancellaGiocatore.setBounds(40, 300, 320, 30);
			MyButton buttoneInvs = creaBottone("",40, 300, 320, 30, "", null, null);
			buttoneInvs.addActionListener(k -> {s.quickShoot("ButtonEffect");CancellaGiocatore.setText("");Fill.remove(buttoneInvs);});
			
			MyButton conferma = creaBottone("", 40, 220, 50, 50, "assets/Immagini/Crossair.png", "assets/Immagini/CrossairScuro.png", "assets/Immagini/F5050.png");
			conferma.addActionListener(k -> {
					if (!CancellaGiocatore.getText().equals("Inserisci Allenatore da Cancellare...")) {
					int i=JOptionPane.showConfirmDialog(null, "Sicuro di voler cancellare "+CancellaGiocatore.getText()+"? In caso di successo\nil gioco verrà riavviato.", "ATTENZIONE! L'operazione necessita di Conferma" ,JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(i==0) {
						try {
							if (new Salvataggio<>().checkFileAt("saves/"+CancellaGiocatore.getText()+".ser")) {
							s.quickShoot("AWP",10);
							new Salvataggio<>().deleteItem("saves/"+CancellaGiocatore.getText()+".ser");
							new java.util.Timer().schedule(new TimerTask(){
						        @Override
						        public void run() {
						        	System.exit(0);
						    		
						        }
						    },1300); 
							return;
							}
							JOptionPane.showMessageDialog(null, "Il path "+"saves/"+CancellaGiocatore.getText()+".ser non è valido!\n\""+CancellaGiocatore.getText()+"\" non è un allenatore!", "ERRORE! Cancellamento annullato!", JOptionPane.ERROR_MESSAGE);
							}
						catch (Exception l) {
							System.out.println("Problemi durante cancellamento file");
							}
						}
					}});
			
			creaLabel("<html> Inserisci il nome dell'Allenatore da cancellare e premi il bottone di conferma qui sulla sinistra per rimuoverlo completamente dal gioco </html>", 130, 195, 240, 100, Fill, StatFontPiccolo, 2, bianco);


			Fill.add(reset, Integer.valueOf(2));
			Fill.add(buttoneInvs, Integer.valueOf(2));
			Fill.add(CancellaGiocatore, Integer.valueOf(2));
			Fill.add(conferma, Integer.valueOf(2));
			Fill.add(slideraudio, Integer.valueOf(2));
			
			PopUp.setTitle("Pannello di Controllo");           	
			PopUp.setDefaultCloseOperation(DISPOSE_ON_CLOSE); 	
			PopUp.setResizable(false);                     	              
			PopUp.setLocationRelativeTo(this);            	
			PopUp.setIconImage(logo.getImage());         	
			PopUp.setContentPane(Fill);
			PopUp.setPreferredSize(new Dimension(400, 400));
			PopUp.pack();
			PopUp.setVisible(true);                        
			PopUp.repaint();
			});
		}

	//----------------------------------------------------------------------
	// 							Builder Pannelli Secondari (Ricorda di buildare nel main)
	//----------------------------------------------------------------------
	
	/**
	 * Inizializza il pannello di creazione inferiore di "Nuovo Gioco" con vari componenti interattivi.
	 * Questo pannello consente agli utenti di creare un nuovo personaggio selezionando una squadra di Pokemon,
	 * assegnando un nome e confermando la creazione.
	 * 
	 * <p>Il pannello include:
	 * <ul>
	 *   <li>Un'immagine di sfondo per la schermata del nuovo gioco.</li>
	 *   <li>Un pulsante di conferma per salvare il personaggio creato.</li>
	 *   <li>Due sotto-pannelli per visualizzare le statistiche e le mosse del Pokemon selezionato.</li>
	 *   <li>Un elenco di Pokemon disponibili per la selezione, scorrevole tramite un cursore.</li>
	 *   <li>Pulsanti interattivi per passare tra i pannelli delle statistiche e delle mosse.</li>
	 * </ul>
	 * 
	 * <p>I controlli di validazione includono:
	 * <ul>
	 *   <li>Assicurarsi che il giocatore abbia selezionato almeno un Pokemon per la sua squadra.</li>
	 *   <li>Assicurarsi che il giocatore abbia inserito un nome valido e unico per il suo personaggio.</li>
	 * </ul>
	 * 
	 * <p>Se tutte le validazioni vengono superate, il personaggio viene salvato e il pannello torna al menu principale.
	 */
	private void creaNewGame() 
		{
		//Pannello-Frame
		panLowNewGame = creaPanelWB();
		panLowNewGame.setBounds(0, 400, 700, 400);
		indexImgP = 0; //set dell'indice scorrimento immagine profilo a 0, è comodo averlo qui
		
		JLabel poken=creaLabelImmagine(0, 0, 700, 400, "assets/Immagini/background new game.png");
		panLowNewGame.add(poken, Integer.valueOf(0));
		
		//Bottone Conferma, salva il personaggio creato
		MyButton BottoneNC = creaBottone("Conferma",524,328, 150, 40, panLowNewGame, "assets/Immagini/CONFERMA.png",  "assets/Immagini/CONFERMAScuro.png",  "assets/Immagini/F15040.png");
		BottoneNC.addActionListener(e -> 
		{
			
			//Check se hai una squadra di almeno un pokemon
			boolean bool = false;
			for (Pokemon poke : listapokemontemp) { try { if (poke != null) {bool = true;}}catch (java.lang.NullPointerException f) {}}
			if (!bool) {JOptionPane.showMessageDialog(null, "Non hai scelto una squadra!", "ERRORE! Salvataggio Annullato!", JOptionPane.ERROR_MESSAGE);return ;}

			//Check se hai un nome o se il nome utilizzato è già in uso
			if (NomeGiocatore.getText().trim().equals("Inserisci qui il nome del tuo giocatore! (Max 16 Char)") || NomeGiocatore.getText().trim().equals("")) {JOptionPane.showMessageDialog(null, "Non hai scelto un nome!", "ERRORE! Salvataggio Annullato!", JOptionPane.ERROR_MESSAGE);return;}
			if (NomeGiocatore.getText().trim().indexOf("\"")!=-1 ) {JOptionPane.showMessageDialog(null, "Carattere Bandito < \" > !", "ERRORE! Salvataggio Annullato!", JOptionPane.ERROR_MESSAGE);return;}
			if (NomeGiocatore.getText().trim().indexOf("/")!=-1 ) {JOptionPane.showMessageDialog(null, "Carattere Bandito < / > !", "ERRORE! Salvataggio Annullato!", JOptionPane.ERROR_MESSAGE);return;}
			if (NomeGiocatore.getText().trim().indexOf("\\")!=-1 ) {JOptionPane.showMessageDialog(null, "Carattere Bandito < \\ > !", "ERRORE! Salvataggio Annullato!", JOptionPane.ERROR_MESSAGE);return;}

			for (File file : new Salvataggio<>().getFileAt("saves")) {if (file.getName().equals(NomeGiocatore.getText()+".ser")) {JOptionPane.showMessageDialog(null, "Questo nome è già in uso!", "ERRORE! Salvataggio Annullato!", JOptionPane.ERROR_MESSAGE);return ;}} 
			
			
			
			
			String Nome = NomeGiocatore.getText().trim();
			if (Nome.length()>16) {
				Nome = (String) Nome.subSequence(0, 16);  //nome
				}
			
			
			
			String nomeimgprofilo = new Salvataggio<>().getFileAt("assets/Immagini/IconeProfilo")[indexImgP].getName();
			String pathIconaProfilo= "assets/Immagini/IconeProfilo/"+nomeimgprofilo;
			String pathIconaProfiloSmall= "assets/Immagini/IconeProfiloSmall/"+nomeimgprofilo;
			if (!(new Salvataggio<>().checkFileAt(pathIconaProfilo))) 
				{pathIconaProfilo="assets/Immagini/VOID.png";}
			if (!(new Salvataggio<>().checkFileAt(pathIconaProfiloSmall))) 
				{pathIconaProfiloSmall="assets/Immagini/VOID.png";}
			
			//Esecuzione salvataggio
			Allenatore GTemp = new Allenatore(
					listapokemontemp, 		  //squadra
					Nome,  //nome
					new ImageIcon(pathIconaProfilo),  //immaginegrande
					new ImageIcon(pathIconaProfiloSmall)   //immaginepiccola
					); 
			listapokemontemp = new Pokemon[6];
			new Salvataggio<Allenatore>().salvaItem(GTemp, "saves", NomeGiocatore.getText());
			
			//Cambia pagina
			s.quickShoot("ButtonEffect");
			changePanel(panLowNewGame, panHighNewGame, panLowMainMenu, panHighMainMenu);
			
			//reset delle schermate, di fatto aggiornamento schermate.
			creaNewGame();				
			creaHighNewGame();			
			creaMultiplayer();			
			creaHighMultiplayer();
			creaScoreBoard();

			});

		//Pannello generale (Vero contenitore)
		JLayeredPane Pannello2 = creaPanel(20, 20, 645 , 300);
		Color bianco=new Color(255,255,255);
		Pannello2.setOpaque(false);
		
		//Pannello contenente i JLabel delle stat, PAGINA A
		JLayeredPane PannelloA = creaPanel(0, 0, 250, 300); 
		JLabel background=creaLabelImmagine(0, 0, 275, 300, "assets/Immagini/stat schermate pg.png");
		PannelloA.add(background, Integer.valueOf(0));
		JLabel DisplayStatPS = creaLabel("PS",20, 40, 200, 40, PannelloA, StatFont, 2, bianco);
		JLabel DisplayStatATK = creaLabel("ATK",20, 80, 200, 40, PannelloA, StatFont, 2, bianco);
		JLabel DisplayStatDIF = creaLabel("DIF",20, 120, 200, 40, PannelloA, StatFont, 2, bianco);
		JLabel DisplayStatATKSP = creaLabel("ATKSP",20, 160, 200, 40, PannelloA,  StatFont, 2, bianco);
		JLabel DisplayStatDIFSP = creaLabel("DIFSP",20, 200, 200, 40, PannelloA,  StatFont, 2, bianco);
		JLabel DisplayStatVEL = creaLabel("VEL",20, 240, 200, 40, PannelloA,  StatFont, 2, bianco);
		
		//Pannello contenente i JLabel delle stat, PAGINA B
		JLayeredPane PannelloB = creaPanel(0, 0, 250, 300); 
		JLabel background2=creaLabelImmagine(0, 0, 275, 300, "assets/Immagini/mosse schermata pg.png");
		PannelloB.add(background2, Integer.valueOf(0));
		JLabel Mossa1 = creaLabel("Mossa 1",20, 50, 200, 50, PannelloB,  StatFont, 2, bianco);
		JLabel Mossa2 = creaLabel("Mossa 2",20, 100, 200, 50, PannelloB,  StatFont, 2, bianco);
		JLabel Mossa3 = creaLabel("Mossa 3",20, 150, 200, 50, PannelloB,  StatFont, 2, bianco);
		JLabel Mossa4 = creaLabel("Mossa 4",20, 200, 200, 50, PannelloB,  StatFont, 2, bianco);
		

		/*  
		Pokemon esistenti è il numero di bottoni da displayare
		ed è dato dal numero di file.ser nella cartella assets/Pokemonz.
		*/
		PokemonEsistenti = new Salvataggio<>().getFileAt("assets/Pokemonz");
		int Altezza = (PokemonEsistenti.length-3)*100; //Il -3 serve per una visualizzazione più pulita
		//System.out.println(Altezza + " Printato da creaNewGame, è il numero che usa lo Slider");
		
		//Il pannello "elenco" che slitta su e giù a seconda dello slider
		JLayeredPane PannelloPokemon = creaPanel(275, 0, 400, Altezza+300);
		PannelloPokemon.setBackground(Color.DARK_GRAY);
		
		//slider per scorrere elenco selezione pokemon, lo scorrimento avviene col variare dell'Y0 del pannellopokemon
		MySlider Slider = creaSlider(MySlider.VERTICAL,0,100,100);
		
		Slider.setBounds(251,0,25,300);
		Slider.setOpaque(false);
		
		MouseWheelListener Listener = new MouseWheelListener() 
		{
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				
				//SE SCROLLI IN AVANTI
				if (e.getWheelRotation() < 0) {
					Slider.setValue(Slider.getValue()+2);
					
					}
				//SE SCROLLI VERSO DIETRO
				else {
					Slider.setValue(Slider.getValue()-2);
				}
		}};
		
		//Leggero delay per fare in modo che sia davvero collegata
		new java.util.Timer().schedule(new TimerTask(){
	        @Override
	        public void run() {
	        	panLowNewGame.addMouseWheelListener(Listener);
	    		panHighNewGame.addMouseWheelListener(Listener);
	        }
	    },200); 
		

		
		//Per motivi estetici, se ci sono meno di 3 elementi non scorre nulla (funzionerebbe comunque lo scorrimento, ma lo levo ,perchè sì)
		if (Altezza > 0) 
			{
			Slider.addChangeListener(e -> {PannelloPokemon.setBounds(275,-Altezza+(((Altezza)*Slider.getValue())/100),400,Altezza+300);this.repaint();});
			}
		
		//I bottoni nell'elenco che scorre, vengono creati in base al numero di elementi pokemon utilizzabili dal programma (N file in cartella assets/Pokemonz)
		//Ogni bottone è 370x100
		for (int i= 0; i<(Altezza/100+3); i++) 
			
			{
			//Creazione del bottone
			String NomeBottone = PokemonEsistenti[i].getName();
			
			int lenNome = NomeBottone.length();
			MyButton BottoneTarget = new MyButton(NomeBottone.substring(0, lenNome-4),"assets/Immagini/icona pokemon grande.png",-150,-10); 
			BottoneTarget.setTextSpiazzamento(10, 0);
			BottoneTarget.setContenuto(i); //Comando speciale dei MyButton, il bottone contiene un intero a piacere
			BottoneTarget.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
				
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					
					
				}
			});
			removeMouseListener(BottoneTarget, "assets/Immagini/icona pokemon grande.png", "assets/Immagini/icona pokemon grandeScuro.png", "assets/Immagini/F370100.png");
			
			
			BottoneTarget.setBounds(0,000+i*100,370,100);
			PannelloPokemon.add(BottoneTarget);
			
			Pokemon PokemonTarget = new Salvataggio<Pokemon>().getItem(PokemonEsistenti[BottoneTarget.getContenuto()]);
			BottoneTarget.setSpecialIcon(getImage(PokemonTarget.getSpritePiccolo()), 90, 10);
			BottoneTarget.setSpecialText("𝙇𝙫."+PokemonTarget.getLivello(), 40, 55);
			BottoneTarget.addActionListener(e -> 
				{
					s.quickShoot("ButtonEffect");
					//Scongelo il pokemon in assets con lo stesso indice del bottone premuto
					//E lo carico nell'array nella posizione dell'ultimo slot selezionato (Nulla di base)
					File PokemonCongelato = PokemonEsistenti[BottoneTarget.getContenuto()];
					Pokemon PokemonScongelato = new Salvataggio<Pokemon>().getItem(PokemonCongelato);
					PokemonScongelato.starterPokemon(); //Completa lo scongelamento

					//Cambia tutti i vari display di stat
					DisplayStatPS.setText("PS: "+PokemonScongelato.getPs());
					DisplayStatATK.setText("ATK: "+PokemonScongelato.getAttacco());
					DisplayStatDIF.setText("DIF: "+PokemonScongelato.getDifesa());
					DisplayStatATKSP.setText("ATKSP: "+PokemonScongelato.getAttaccoSp());
					DisplayStatDIFSP.setText("DIFSP: "+PokemonScongelato.getDifesaSp());
					DisplayStatVEL.setText("VEL: "+PokemonScongelato.getVelocità());
					
					//display delle mosse (se ci sono)
					if(PokemonScongelato.getMossa(0)!=null) {Mossa1.setText("M1: "+PokemonScongelato.getMossa(0).getNome());}
					else {Mossa1.setText("M1: ");}
					
					if(PokemonScongelato.getMossa(1)!=null) {Mossa2.setText("M2: "+PokemonScongelato.getMossa(1).getNome());}
					else {Mossa2.setText("M2: ");}
					
					if(PokemonScongelato.getMossa(2)!=null) {Mossa3.setText("M3: "+PokemonScongelato.getMossa(2).getNome());}
					else {Mossa3.setText("M3: ");}
				
					if(PokemonScongelato.getMossa(3)!=null)  {Mossa4.setText("M4: "+PokemonScongelato.getMossa(3).getNome());}
					else {Mossa4.setText("M4: ");}
					
					try 
						{
						//se uno slot è selezionato, metti il pokemon scongelato nell'array
						listapokemontemp[indexlistapokemontemp] = PokemonScongelato;
						
						//Cambio il text del bottone premuto, resetto il puntatore a 6 (Nulla)
						BottoniP[indexlistapokemontemp].setText(PokemonScongelato.toString()); BottoniP[indexlistapokemontemp].setSpecialIcon(getImage(PokemonScongelato.getSpritePiccolo()), 0, 0); indexlistapokemontemp = 6 ;
						}
					
					catch(java.lang.ArrayIndexOutOfBoundsException f) {System.out.println("Non hai ancora selezionato uno slot in cui mettere il pokemon");}
				}); 
			}
		
	
		//Bottone che partendo da PannelloA arriva a PannelloB
		MyButton BottoneSwitchToB = creaBottone("",183,0, 50, 50, PannelloA,"assets/Immagini/Pokeback.png", "assets/Immagini/PokebackScura.png", "assets/Immagini/F5050.png");
		BottoneSwitchToB.addActionListener(e -> 
			{
			s.quickShoot("ButtonEffect");
			relativeChangePanel(PannelloA, PannelloB, Pannello2);
			});
		
		//Bottone che partendo da PannelloB arriva a PannelloA
		MyButton BottoneSwitchToA = creaBottone("",183,0, 50, 50, PannelloB, "assets/Immagini/Pokeback.png", "assets/Immagini/PokebackScura.png", "assets/Immagini/F5050.png");
		BottoneSwitchToA.addActionListener(e -> 
			{
			s.quickShoot("ButtonEffect");
			relativeChangePanel(PannelloB, PannelloA, Pannello2);
			});
		
		
		//Tutti gli add necessari
		creaLabel("Confermi Creazione Personaggio?",105,331,400,50, panLowNewGame, GeneralFont,2,bianco);
		Pannello2.add(Slider,Integer.valueOf(2));
		Pannello2.add(PannelloPokemon,Integer.valueOf(2));
		Pannello2.add(PannelloA,Integer.valueOf(2));
		panLowNewGame.add(Pannello2,Integer.valueOf(2));
		}
	
	/**
	 * Inizializza il pannello superiore della schermata "Nuovo Gioco" con vari componenti interattivi.
	 * Questo pannello permette agli utenti di personalizzare l'immagine del profilo del giocatore,
	 * selezionare i Pokemon per la propria squadra e inserire un nome per il giocatore.
	 * 
	 * <p>Il pannello include:
	 * <ul>
	 *   <li>Un'immagine di sfondo per la schermata superiore del nuovo gioco.</li>
	 *   <li>Un pannello contenente l'immagine del profilo del giocatore e i pulsanti per cambiarla.</li>
	 *   <li>Un pannello con 6 slot per i Pokemon, permettendo di selezionare i Pokemon per la squadra.</li>
	 *   <li>Un campo di testo per inserire il nome del giocatore.</li>
	 * </ul>
	 * 
	 * <p>I controlli e le funzionalità interattive includono:
	 * <ul>
	 *   <li>Pulsanti per scorrere le immagini del profilo a sinistra e a destra, con aggiornamento in tempo reale dell'immagine mostrata.</li>
	 *   <li>Pulsanti per ogni slot Pokemon che permettono di selezionare uno slot e aggiornare l'immagine e il testo di sfondo in base all'interazione dell'utente.</li>
	 *   <li>Un campo di testo per il nome del giocatore, con un pulsante invisibile che svuota il campo di testo al clic.</li>
	 * </ul>
	 */
	private void creaHighNewGame() 	
	{

		panHighNewGame = creaPanel(0, 0, 700, 400);
		panHighNewGame.setBackground(Color.BLACK);
		
		JLabel poken=creaLabelImmagine(0, 0, 700, 400, "assets/Immagini/background new game alto.png");
		panHighNewGame.add(poken, Integer.valueOf(0));
		
		//Pannello che contiene immagine player e i bottoni per cambiarle
		JLayeredPane Pannello = creaPanel(20, 90, 150, 200);
		imgP = creaLabel("", 0, 20, 150, 160, Pannello, null); 
		imgP.setIcon(new ImageIcon(new Salvataggio<>().getFileAt("assets/Immagini/IconeProfilo")[0].getPath()));
		
		//Bottone per scorrere le immagini profilo a sinistra
		String ImmagineNormale ="assets/Immagini/FrecciaSX.png";
		String ImmagineScura ="assets/Immagini/FrecciaSXScarso.png";
		
		MyButton BottoneIL = creaBottone("",30,330, 50, 50, panHighNewGame, ImmagineNormale, ImmagineScura, "assets/Immagini/F5050.png"); //rendi funzionanti
		removeMouseListener(BottoneIL,ImmagineNormale,ImmagineScura,"assets/Immagini/F5050.png");
		BottoneIL.addActionListener(e -> {
			s.quickShoot("ButtonEffect");
			//Carica tutte le icone disponibili
			File[] cartellaImgProfilo = new Salvataggio<>().getFileAt("assets/Immagini/IconeProfilo");
			int lungCartella = cartellaImgProfilo.length;

			//Aggiorna l'indice di scorrimento dell'array di img 
			if (indexImgP==0) {indexImgP=lungCartella-1;}
			else {indexImgP--;}
			
			//Set nuova img profilo
			imgP.setIcon(new ImageIcon(cartellaImgProfilo[indexImgP].getPath()));
			this.repaint();

			});
		
		//Bottone per scorrere le immagini profilo a destra
		String ImmagineNormale2 ="assets/Immagini/FrecciaDX.png";
		String ImmagineScura2 ="assets/Immagini/FrecciaDXScura.png";
		
		MyButton BottoneIR = creaBottone("",110,330, 50, 50, panHighNewGame,  ImmagineNormale2,ImmagineScura2, "assets/Immagini/F5050.png");
		removeMouseListener(BottoneIR,ImmagineNormale2,ImmagineScura2,"assets/Immagini/F5050.png");
		BottoneIR.addActionListener(e -> 
		{
			s.quickShoot("ButtonEffect");
			//Carica tutte le icone disponibili
			File[] cartellaImgProfilo = new Salvataggio<>().getFileAt("assets/Immagini/IconeProfilo");
			int lungCartella = cartellaImgProfilo.length;

			//Aggiorna l'indice di scorrimento dell'array di img 
			if (indexImgP+1==lungCartella) {indexImgP=0;}
			else {indexImgP++;}
			
			//Set nuova img profilo
			imgP.setIcon(new ImageIcon(cartellaImgProfilo[indexImgP].getPath()));
			this.repaint();
			});
		
		//Pannello con i 6 slot Pokemon riempibili
		JLayeredPane Pannello2 = creaPanel(192, 90, 475, 290);
		BottoniP = getSimpleButtonGrid(20, 20, 243, 90, 6, Pannello2);
		for (MyButton myButton : BottoniP) {
			defaultBottone(myButton, "assets/Immagini/icona pokemon.png");

			myButton.setEnabled(true);
			myButton.addMouseListener(new MouseAdapter()
	        {
	            public void mouseEntered(MouseEvent evt)
	            {
	            	
	            	myButton.addFocus(getImage("assets/Immagini/F19275.png")); 
	                //System.out.println("SOPRA GRID");
	                myButton.repaint();
	            }
	            public void mouseExited(MouseEvent evt)
	            {
	            	myButton.removeFocus();
	            	//System.out.println("FUORI GRID");
	            	myButton.repaint();
	            }
	            public void mousePressed(MouseEvent evt)
	            {
	            	if (myButton.isEnabled()){
	            		myButton.setSfondo(getImage("assets/Immagini/icona pokemon Scuro.png"));
		            	//System.out.println("PREMUTO");
		            	myButton.repaint();
	            	}
	            }
	            public void mouseReleased(MouseEvent evt)
	            {
	            	if (myButton.isEnabled()){
	            		myButton.setSfondo(getImage("assets/Immagini/icona pokemon.png"));
		            	//System.out.println("RILASCIATO");
		            	myButton.repaint();
	            	}
	            }
	        });
			myButton.addActionListener(e -> {indexlistapokemontemp = myButton.getContenuto(); s.quickShoot("ButtonEffect");});
		}
		
		
		//Text field con nome Giocatore
		NomeGiocatore = new JTextField();
		NomeGiocatore.setBounds(20,20,645,50);
		NomeGiocatore.setFont(GeneralFont);
		NomeGiocatore.setText("Inserisci qui il nome del tuo giocatore! (Max 16 Char)");
		
		//Bottone invisibile che svuota il textfield
		MyButton BottoneJTEXTFIELD = creaBottone("", 20,20,645,50, panHighNewGame,"", null, null);
		BottoneJTEXTFIELD.addActionListener(e -> {NomeGiocatore.setText("");panHighNewGame.remove(BottoneJTEXTFIELD); s.quickShoot("ButtonEffect");});
		
		//Add vari ed eventuali
		panHighNewGame.add(NomeGiocatore,Integer.valueOf(2));
		panHighNewGame.add(Pannello,Integer.valueOf(2));
		panHighNewGame.add(Pannello2,Integer.valueOf(2));
		
	}
	
	/**
	 * Inizializza il pannello inferiore della schermata "Multiplayer" con vari componenti interattivi.
	 * Questo pannello permette agli utenti di visualizzare i Pokemon selezionati dagli allenatori per la battaglia,
	 * nonché le loro statistiche e le mosse disponibili.
	 * 
	 * <p>Il pannello include:
	 * <ul>
	 *   <li>Un'immagine di sfondo per la schermata inferiore del multiplayer.</li>
	 *   <li>Un messaggio di testo per indicare all'utente di caricare gli allenatori per il PVP.</li>
	 *   <li>Un pannello contenente i bottoni dei Pokemon degli allenatori selezionati, disposti in una griglia.</li>
	 *   <li>Per ogni Pokemon, un pannello delle statistiche e delle mosse, che può essere visualizzato interattivamente.</li>
	 *   <li>Un bottone di conferma per iniziare la partita, condizionato alla scelta degli allenatori per entrambi i giocatori.</li>
	 * </ul>
	 * 
	 * <p>I controlli e le funzionalità interattive includono:
	 * <ul>
	 *   <li>Cliccando su un Pokemon, viene visualizzato un pannello con le sue statistiche dettagliate e le mosse disponibili.</li>
	 *   <li>Possibilità di navigare tra le statistiche del Pokemon e le sue mosse utilizzando appositi bottoni.</li>
	 *   <li>Controllo per evitare l'avvio della partita se non sono stati scelti entrambi gli allenatori, con messaggi di errore appropriati.</li>
	 * 	 <li>Controllo per evitare l'avvio della partita qualora entrambi i giocatori abbiano scelto lo stesso allenatore, con messaggio di errore appropriato.</li>
	 * </ul>
	 */
	private void creaMultiplayer() 
		{
		
		//Pannello base
		panLowMultiplayer = creaPanelWB();
		panLowMultiplayer.setBounds(0, 400, 700, 400);
		
		JLabel poken=creaLabelImmagine(0, 0, 700, 400, "assets/Immagini/background multiplayer.png");
		panLowMultiplayer.add(poken, Integer.valueOf(0));
		Color bianco=new Color(255,255,255);
		
		//Nome del pannello
		creaLabel("Carica gli Allenatori per il tuo PVP!",100,331,400,50,panLowMultiplayer, GeneralFont,2,bianco);
		

		//Pannello CONTENENTE pokemon di allenatore cliccato
		PannelloPokemonT = creaPanel(20, 20, 645, 300);
		PannelloPokemonT.setOpaque(false);
		
		//Setting dei bottoni dei pokemon, globali perchè necessitano di cambiare in base alla scelta del pannello superiore (avoidable se merge H e L)
		//i BOTTONI delle mosse sono legati SOLO alle X e Y dei bottoni dei pokemon
		int x0 = 85 ;int x = 192;
		int y0 = 15 ;int y = 75;
		
		//Crea i bottoni dei pokemon
		BottoniPokemon = getButtonGrid(x0, y0, x, y, 6, PannelloPokemonT);
		
		//Cosa fa ogni bottone quando lo premi (Stai premendo un bottone con su scritto un pokemon)
		for (MyButton bottone : BottoniPokemon) 
			{
			bottone.setEnabled(false);
            defaultBottone(bottone, "assets/Immagini/icona pokemon spento.png");
			bottone.addActionListener(e ->
				{
				s.quickShoot("ButtonEffect");
				
				//pokemon su cui si clicca
				Pokemon PokemonAnalisi = AllenatoreScongelato.getPokemonPosizione(bottone.getContenuto());
				
				//Display mosse pokepalle, se premi un bottone qua dentro ti porta a nuova schermata con desc Mossa
				JLayeredPane temppanelMOSSE = creaPanel(20, 20, 645, 300);
				temppanelMOSSE.setOpaque(false);
				
				//Pannello contenente i label con le stat dei pokemon
				JLayeredPane temppanelSTATS = creaPanel(20, 20, 645, 300);
				JLabel background=creaLabelImmagine(0, 0, 645, 300, "assets/Immagini/stat schermata multiplayer.png");
				temppanelSTATS.add(background, Integer.valueOf(0));
				
				JLabel PokemonAnalisiSprite = creaLabelImmagine(450, 20, 288, 288,PokemonAnalisi.getSpriteFronte());
				temppanelSTATS.add(PokemonAnalisiSprite, Integer.valueOf(1));
				
				creaLabel("PS: "+PokemonAnalisi.getPs(), 20, 40, 300, 40, temppanelSTATS,  StatFont, 2, bianco);
				creaLabel("ATK: "+PokemonAnalisi.getAttacco(), 20, 80, 300, 40, temppanelSTATS,  StatFont, 2, bianco);
				creaLabel("DIF: "+PokemonAnalisi.getDifesa(), 20, 120, 300, 40, temppanelSTATS,  StatFont, 2, bianco);
				creaLabel("ATKSP: "+PokemonAnalisi.getAttaccoSp(), 20, 160, 300, 40, temppanelSTATS,  StatFont, 2, bianco);
				creaLabel("DIFSP: "+PokemonAnalisi.getDifesaSp(), 20, 200, 300, 40, temppanelSTATS,  StatFont, 2, bianco);
				creaLabel("VEL: "+PokemonAnalisi.getVelocità(), 20, 240, 300, 40, temppanelSTATS,  StatFont, 2, bianco);
				creaLabel("Livello: "+PokemonAnalisi.getLivello(), 200, 40, 300, 40, temppanelSTATS,  StatFont, 2, bianco);
				creaLabel("EXP: "+PokemonAnalisi.getPuntiExp()+"/"+((PokemonAnalisi.getLivello()+1)*(PokemonAnalisi.getLivello()+1)*(PokemonAnalisi.getLivello()+1)), 200, 80, 300, 40, temppanelSTATS,  StatFont, 2, bianco);
				String tipo1;
				if (PokemonAnalisi.getTipo1()!=0) 
					{
					tipo1=Tipi.values()[PokemonAnalisi.getTipo1()-1].toString();
					}
				else 
					{
					tipo1="";
					}
				
				String tipo2;
				if (PokemonAnalisi.getTipo2()!=0) 
					{
					tipo2=Tipi.values()[PokemonAnalisi.getTipo2()-1].toString();
					}
				else 
					{
					tipo2="";
					}
				creaLabel("Tipi: "+tipo1 +" "+ tipo2, 200, 120, 300, 40, temppanelSTATS,  StatFont, 2, bianco);
				//Crea la grid di bottoni che verranno usati x le mosse
				MyButton[] gruppoBottoniMosse = getButtonGrid(85, 40, x, y, 4, temppanelMOSSE);
				
				//SET DI TUTTI I 4 BOTTONI DELLE MOSSE, Quindi ogni pokemon crea un suo pannello delle mosse (Giuro non fa laggare, sono salvati in memoria)
				for (MyButton M : gruppoBottoniMosse) 
					{
					//Fa i disegni sui bottoni se sono occupati, altrimenti li defaulta e li spegne
					setBottoneMossa(M, PokemonAnalisi,false);
					M.addActionListener(j ->
					
						{
						s.quickShoot("ButtonEffect");
					
						//Mossa sotto analisi (su cui hai premuto)
						Mossa MossaAnalisi = PokemonAnalisi.getMossa(M.getContenuto());
						
						//pannello contenente i label in cui scrivi le stat della mossa 
						JLayeredPane temppanelMOSSEstats = creaPanel(20, 20, 645, 300);
						JLabel background2=creaLabelImmagine(0, 0, 645, 300, "assets/Immagini/stat mosse schermata multiplayer.png");
						temppanelMOSSEstats.add(background2, Integer.valueOf(0));
						
						//Label di informazioni
						creaLabel("Nome: "+MossaAnalisi.getNome(), 20, 40, 645, 40,temppanelMOSSEstats,  StatFont, 2, bianco);
						creaLabel("Potenza: "+MossaAnalisi.getPotenza(), 20, 80, 645, 40,temppanelMOSSEstats,  StatFont, 2, bianco);
						creaLabel("Precisione: "+MossaAnalisi.getPrecisione(), 20, 120, 645, 40,temppanelMOSSEstats,  StatFont, 2, bianco);
						creaLabel("PP: "+MossaAnalisi.getPP(), 20, 160, 645, 40,temppanelMOSSEstats,  StatFont, 2, bianco);
						creaLabel("<html>Descrizione: "+MossaAnalisi.getDescrizione()+"</html>", 235, 80, 400, 120,temppanelMOSSEstats,  StatFont, 2, bianco);
						String tipoM;
						if (MossaAnalisi.getTipo()!=0) 
							{
							tipoM=Tipi.values()[MossaAnalisi.getTipo()-1].toString();
							}
						else 
							{
							tipoM="";
							}
						creaLabel("Tipo: "+tipoM, 235, 40, 300, 40, temppanelMOSSEstats,  StatFont, 2, bianco);
						//la descrizione non c'entra in orizzontale
						
						//Bottone per tornare dietro
						MyButton switchBackTOMosse = creaBottone("", 0, 240, 50, 50, temppanelMOSSEstats, "assets/Immagini/Pokeback.png", "assets/Immagini/PokebackScura.png", "assets/Immagini/F5050.png");
						switchBackTOMosse.addActionListener(z ->
							{	
							s.quickShoot("ButtonEffect");
							relativeChangePanel(temppanelMOSSEstats, temppanelMOSSE, panLowMultiplayer);
							});
						
						
						//Comando del bottone premuto che porta a questi label
						relativeChangePanel(temppanelMOSSE, temppanelMOSSEstats, panLowMultiplayer);
						});
					}
	
				//Bottone che riporta alla lista di pokemon dalla lista di mosse
				MyButton bottoneback = creaBottone("", 0, 240, 50, 50, temppanelMOSSE, "assets/Immagini/Pokeback.png", "assets/Immagini/PokebackScura.png", "assets/Immagini/F5050.png");
				bottoneback.addActionListener(z ->
					{
					s.quickShoot("ButtonEffect");
					relativeChangePanel(temppanelMOSSE,PannelloPokemonT,panLowMultiplayer);
					});
				
				//Bottone che porta alla lista di stat del pokemon cliccato
				MyButton switchtostats = creaBottone("", 595, 0, 50, 50, temppanelMOSSE, "assets/Immagini/Pokeback.png", "assets/Immagini/PokebackScura.png", "assets/Immagini/F5050.png");
				switchtostats.addActionListener(z ->
					{
					s.quickShoot("ButtonEffect");
					relativeChangePanel(temppanelMOSSE,temppanelSTATS,panLowMultiplayer);
					});
				
				//Porta dalla lista di stat del pokemon alla sua lista mosse
				MyButton switchtoMosse = creaBottone("", 595, 0, 50, 50, temppanelSTATS, "assets/Immagini/Pokeback.png", "assets/Immagini/PokebackScura.png", "assets/Immagini/F5050.png");
				switchtoMosse.addActionListener(z ->
					{
					s.quickShoot("ButtonEffect");
					relativeChangePanel(temppanelSTATS,temppanelMOSSE,panLowMultiplayer);
					});
				
				
				//Di default quando premi un pokemon mostrerà la sua lista mosse
				relativeChangePanel(PannelloPokemonT,temppanelMOSSE,panLowMultiplayer);
				}); 
			}
		
		//Bottone conferma per entrare nel pregame, non parte se non sono stati scelti entrambi i giocatori
		MyButton BottoneLGC = creaBottone("Conferma",524,328, 150, 40, panLowMultiplayer, "assets/Immagini/CONFERMA.png", "assets/Immagini/CONFERMAScuro.png",  "assets/Immagini/F15040.png");
		BottoneLGC.addActionListener(e -> 
			{
			
			s.quickShoot("ButtonEffect");
			if (players[0]==null) {JOptionPane.showMessageDialog(null, "Giocatore 1 non ha scelto l'allenatore!", "ERRORE! Inizio Partita Annullato!", JOptionPane.ERROR_MESSAGE);return;} 
			if (players[1]==null) {JOptionPane.showMessageDialog(null, "Giocatore 2 non ha scelto l'allenatore!", "ERRORE! Inizio Partita Annullato!", JOptionPane.ERROR_MESSAGE);return;} 
			if (players[1].getNome().equals(players[0].getNome())) {JOptionPane.showMessageDialog(null, "Giocatore 1 e Giocatore 2 devono avere\nAllenatori diversi! Cambia Giocatore 1 o 2!", "ERRORE! Inizio Partita Annullato!", JOptionPane.ERROR_MESSAGE);return;}
			s.stop();
			creaHighPreFight();
			creaLowPreFight();
			
			changePanel(panHighMultiplayer, panLowMultiplayer, panLowPreFight, panHighPreFight);
			});
		
		
		//add vari ed eventuali
		panLowMultiplayer.add(PannelloPokemonT,Integer.valueOf(2));
		}
	
	/**
	 * Inizializza il pannello superiore della schermata "Multiplayer" con vari componenti interattivi.
	 * Questo pannello permette agli utenti di selezionare gli allenatori per il multiplayer, visualizzare le loro statistiche
	 * e scegliere i loro Pokemon per la battaglia.
	 * 
	 * <p>Il pannello include:
	 * <ul>
	 *   <li>Un'immagine di sfondo per la schermata superiore del multiplayer.</li>
	 *   <li>Due icone di pokeball che indicano la scelta dell'allenatore per due giocatori.</li>
	 *   <li>Un pannello generale che contiene le informazioni dell'allenatore selezionato, inclusi nome, statistiche e immagine del profilo.</li>
	 *   <li>Un pannello per scorrere l'elenco degli allenatori disponibili, con la possibilità di selezionarne uno per il gioco.</li>
	 *   <li>Un controllo di scorrimento verticale per navigare l'elenco degli allenatori.</li>
	 * </ul>
	 * 
	 * <p>I controlli e le funzionalità interattive includono:
	 * <ul>
	 *   <li>Pulsanti per selezionare l'allenatore per i due giocatori, con aggiornamento visuale delle pokeball e delle statistiche dell'allenatore selezionato.</li>
	 *   <li>Un elenco di bottoni per ogni allenatore disponibile, con visualizzazione della squadra Pokemon dell'allenatore quando selezionato.</li>
	 *   <li>Supporto per lo scorrimento dell'elenco degli allenatori tramite mouse wheel.</li>
	 * </ul>
	 */
	private void creaHighMultiplayer() 
		{
		panHighMultiplayer = creaPanel(0, 00, 700, 400);
		panHighMultiplayer.setBackground(Color.RED);
		players = new Allenatore[2]; //reset dell'array dei giocatori
		indexPlayers = 2; //reset
		
		JLabel poken=creaLabelImmagine(0, 0, 700, 400, "assets/Immagini/background multiplayer alto.png");
		panHighMultiplayer.add(poken, Integer.valueOf(0));
		
		//Displayano se il giocatore ha scelto l'allenatore, sono attaccate al pannello generale
		JLabel Pokepalla1 = creaLabelImmagine(700-20-50-20-80, 20, 50, 50, "assets/Immagini/pokepalla_rossa.png", panHighMultiplayer);
		JLabel Pokepalla2 = creaLabelImmagine( 700-20-80, 20, 50, 50, "assets/Immagini/pokepalla_rossa.png", panHighMultiplayer);

		//Pannello generale
		JLayeredPane PannelloAppoggio = creaPanel(20, 80, 645, 300);
		PannelloAppoggio.setOpaque(false);
		
		
		//Pannello generale stats
		JLayeredPane PannelloAppoggioStats = creaPanel(0, 0, 250, 300); PannelloAppoggioStats.setOpaque(false);
		JLabel poken3=creaLabelImmagine(0, 0, 250, 300, "assets/Immagini/schermata info pg.png");
		PannelloAppoggioStats.add(poken3,Integer.valueOf(0));
		Color bianco=new Color(255,255,255);
		
		JLabel labelstat1 = creaLabel("Nome:"         , 20, 180 -5, 260, 30,PannelloAppoggioStats, StatFont,2,bianco);
		JLabel labelstat2 = creaLabel("Wins:"         , 20, 210 -5, 100, 30, PannelloAppoggioStats, StatFont,2,bianco);
		JLabel labelstat3 = creaLabel("Losses:"   , 20, 240 -5, 100, 30,PannelloAppoggioStats, StatFont,2,bianco);
		JLabel labelstat4 = creaLabel("Numero Pokemon:"     , 20, 270 -5, 260, 30, PannelloAppoggioStats, StatFont,2,bianco);
		JLabel labelstat5 = creaLabel("Kills:"       , 120, 210-5, 120, 30,PannelloAppoggioStats, StatFont,2,bianco);
		JLabel labelstat6 = creaLabel("Deaths:"     , 120, 240-5, 120, 30, PannelloAppoggioStats, StatFont,2,bianco);		
		
		//Pannello che si appoggia a PannelloAppoggioStats (Pannello sopra questo). Serve come contenitore alla ProPic dell'allenatore (ImgProfilo)
		JLayeredPane PannelloIMG = creaPanel(20, 10, 150, 160);
		PannelloAppoggioStats.add(PannelloIMG,Integer.valueOf(2));
		PannelloIMG.setBackground(new Color(49-15, 57-15, 66-15));
		JLabel imgProfilo = creaLabelImmagine( 0, 0,  150, 160, "", PannelloIMG); //Non toccare questo
		
		//Pannello con propic piccola dell'allenatore
		JLayeredPane PannelloIMG2 = creaPanel(190, 60, 48, 48);
		PannelloIMG2.setBackground(new Color(49-15, 57-15, 66-15));
		PannelloAppoggioStats.add(PannelloIMG2,Integer.valueOf(2));
		JLabel imgProfiloMini = creaLabelImmagine( 0, 0,  48, 48, "", PannelloIMG2); 
		
		
		//Bottoni seleziona giocatore
		MyButton b1 = creaBottone("Giocatore1", 20, 20, 150, 50, panHighMultiplayer, "assets/Immagini/bottone g1.png",  "assets/Immagini/bottone g1Scuro.png",  "assets/Immagini/F15050.png");
		MyButton b2 = creaBottone("Giocatore2", 190, 20, 150, 50, panHighMultiplayer, "assets/Immagini/bottone g2.png",  "assets/Immagini/bottone g2Scuro.png",  "assets/Immagini/F15050.png");
		b1.setFont(new Font("Bahnschrift SemiBold", Font.BOLD, 17));
		b2.setFont(new Font("Bahnschrift SemiBold", Font.BOLD, 17));
		removeMouseListener(b1,"assets/Immagini/bottone g1.png", "assets/Immagini/bottone g1Scuro.png","assets/Immagini/F15050.png");
		removeMouseListener(b2,"assets/Immagini/bottone g2.png", "assets/Immagini/bottone g2Scuro.png","assets/Immagini/F15050.png");
		b1.addActionListener(e -> {indexPlayers=0;s.quickShoot("ButtonEffect");}); //setta l'indice a 0 (quindi la scelta di allenatore del player1)
		b2.addActionListener(e -> {indexPlayers=1;s.quickShoot("ButtonEffect");}); //setta l'indice a 1 (quindi la scelta di allenatore del player2)
		
		
		//SUPPORTO ELENCO, definisce la lunghezza dell'elenco
		File[] AllenatoriEsistenti = new Salvataggio<>().getFileAt("saves");
		int Altezza = (AllenatoriEsistenti.length-3)*100; //Il -3 serve per una visualizzazione più pulita
		//System.out.println(Altezza + " Printato da creaHighMultiplayer, è il numero che usa lo Slider");
		
		//Il pannello "elenco" che slitta su e giù a seconda dello slider
		JLayeredPane PannelloAllenatore = creaPanel(275, 0, 400, Altezza+300);
		PannelloAllenatore.setBackground(Color.CYAN);
		
		
		//slider per scorrere elenco selezione allenatori, lo scorrimento avviene col variare dell'Y0 del PannelloAllenatore
		MySlider Slider = creaSlider(MySlider.VERTICAL,0,100,100);
		Slider.setBounds(251,0,25,300);
		Slider.setOpaque(false);
		PannelloAppoggio.setOpaque(false);
		
		MouseWheelListener Listener = new MouseWheelListener() 
		{
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				
				//SE SCROLLI IN AVANTI
				if (e.getWheelRotation() < 0) {
					Slider.setValue(Slider.getValue()+2);
					
					}
				//SE SCROLLI VERSO DIETRO
				else {
					Slider.setValue(Slider.getValue()-2);
				}
		}};
		
		//Leggero delay per fare in modo che sia davvero collegata
		new java.util.Timer().schedule(new TimerTask(){
	        @Override
	        public void run() {
	        	panHighMultiplayer.addMouseWheelListener(Listener);
	        	panLowMultiplayer.addMouseWheelListener(Listener);
	        }
	    },200); 
		
		if (Altezza > 0) 
			{
			Slider.addChangeListener(e -> {PannelloAllenatore.setBounds(275,-Altezza+(((Altezza)*Slider.getValue())/100),400,Altezza+300);this.repaint();});
			}
		
		//I bottoni nell'elenco che scorre, vengono creati in base al numero di elementi allenatore utilizzabili dal programma (N file in cartella assets/Pokemonz)
		//Ogni bottone è 370x100
		for (int i= 0; i<(Altezza/100+3); i++) 
			
			{
			//Ripetizione di Allenatore Scongelato, fatto per sicurezza, temporaneo
			Allenatore AllenatoreAnalisi = new Salvataggio<Allenatore>().getItem(AllenatoriEsistenti[i]);
		
			//t è ogni singolo bottone che si trova nello slider, ovvero tutti gli allenatori premibili
			MyButton t = new MyButton(AllenatoreAnalisi.getNome(),"assets/Immagini/icona giocatore grande.png", -20, 10); 
			t.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
				
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					
					
				}
			});
			removeMouseListener(t, "assets/Immagini/icona giocatore grande.png", "assets/Immagini/icona giocatore grandeScuro.png", "assets/Immagini/F370100.png");
			t.setSpecialIcon(AllenatoreAnalisi.getImageIcon()[1].getImage(),268,25);
			t.setContenuto(i); //Comando speciale dei MyButton, il bottone contiene un intero a piacere
			t.setTextSpiazzamento(-75, -23);
			t.setBounds(0,000+i*100,370,100); 
			PannelloAllenatore.add(t);
			t.addActionListener(e -> 
				{
					s.quickShoot("ButtonEffect");
					//Scongelo l'allenatore in assets con lo stesso indice del bottone premuto
					//E lo carico nello slot giocatore precedentemente selezionato dai bottoni p1 o p2
					File AllenatoreCongelato = AllenatoriEsistenti[t.getContenuto()];
					AllenatoreScongelato = new Salvataggio<Allenatore>().getItem(AllenatoreCongelato);
					
					
					//Cambio display dei vari label
					imgProfilo.setIcon(AllenatoreScongelato.getImageIcon()[0]); //No touching on this
					imgProfiloMini.setIcon(AllenatoreScongelato.getImageIcon()[1]); //No touching on this
		
					labelstat1.setText("Nome: "+AllenatoreScongelato.getNome());
					labelstat2.setText("Wins: "+AllenatoreScongelato.getStats()[0]);
					labelstat3.setText("Losses: "+AllenatoreScongelato.getStats()[1]);
					labelstat4.setText("Numero Pokemon: "+AllenatoreScongelato.getPokemonTotali());
					labelstat5.setText("Kills: "+AllenatoreScongelato.getStats()[2]);
					labelstat6.setText("Deaths: "+AllenatoreScongelato.getStats()[3]);
					
					//cambia i display dei bottoni nella schermata bassa di multiplayer (se ci sono pokemon da displayare)
					for (MyButton bottonez : BottoniPokemon) {setBottonePokemon(bottonez, AllenatoreScongelato);}
					
					//Ogni volta che selezioni un nuovo allenatore resetti qualsiasi schermata si trovi sotto riportandola a pokemon
					panLowMultiplayer.remove(panLowMultiplayer.getComponentAt(21, 21));
					panLowMultiplayer.add(PannelloPokemonT);
					this.repaint();
					
					//assegna l'allenatore al giocatore e resetta il puntatore, se  il puntatore è settato
					try 
					{
						players[indexPlayers]=AllenatoreScongelato; //set della scelta
						if (indexPlayers==0) {Pokepalla1.setIcon(new ImageIcon("assets/Immagini/pokepalla_verde.png"));b1.setText(AllenatoreScongelato.getNome());} //colora la pokeball corrispondente al giocatore che ha scelto la mossa
						if (indexPlayers==1) {Pokepalla2.setIcon(new ImageIcon("assets/Immagini/pokepalla_verde.png"));b2.setText(AllenatoreScongelato.getNome());}
						indexPlayers=2; //reset del puntatore
						this.repaint();
					}
					catch(java.lang.ArrayIndexOutOfBoundsException f) {System.out.println("Non hai ancora selezionato a chi assegnare l'Allenatore");}
				});
			
			//Disegno sul bottone dell'allenatore la sua squadra (Ci troviamo ancora nel for di ogni bottone dello slider)
			Image[] squad = new Image[6];
			Pokemon[] squadtrue = AllenatoreAnalisi.getPokemonLista();
			for (int b = 0; b < 6; b++ ) {if (squadtrue[b] != null) {squad[b] = getImage(squadtrue[b].getSpritePiccolo());}}
			t.setSpecialIcon(squad , 15, 30, 40, 0);
			}
		
		//Add vari dei pannelli standard
		PannelloAppoggio.add(PannelloAppoggioStats,Integer.valueOf(2));
		PannelloAppoggio.add(Slider,Integer.valueOf(2));
		PannelloAppoggio.add(PannelloAllenatore,Integer.valueOf(2));
		panHighMultiplayer.add(PannelloAppoggio,Integer.valueOf(2));
		
		}
	
	/**
	 * Inizializza il pannello della "ScoreBoard" per visualizzare le statistiche dei giocatori.
	 * Include etichette per i nomi e i punteggi dei giocatori, nonché bottoni interattivi per
	 * visualizzare le statistiche come vittorie, sconfitte, partite giocate, ecc.
	 * In caso di pareggio in una stat vengono displayati in ordine alfabetico.
	 * 
	 * <p>Il pannello include:
	 * <ul>
	 *   <li>Un pannello principale con un'area di visualizzazione delle statistiche.</li>
	 *   <li>Etichette per "Nome", "Wins", "Losses", "Games", "Kills", "Deaths".</li>
	 *   <li>Bottoni interattivi per modificare l'ordinamento dei giocatori secondo la statistica da mostrare (vittorie, sconfitte, ecc.).</li>
	 * </ul>
	 * 
	 * <p>Le funzionalità interattive includono:
	 * <ul>
	 *   <li>Aggiornamento dinamico delle statistiche mostrate quando si selezionano i bottoni corrispondenti.</li>
	 *   <li>Caricamento e visualizzazione delle statistiche dei giocatori salvate su file.</li>
	 * </ul>
	 */
	private void creaScoreBoard() 
	{
		panScoreBoard = creaPanelWBScore();
		panScoreBoard.setBounds(0, 0, 700, 800);
		JLabel poken=creaLabelImmagine(0, 0, 700, 800, "assets/Immagini/background scoreboardNewT.png");
		panScoreBoard.add(poken, Integer.valueOf(0));
		
		Color bianco=new Color(255,255,255);
		JLayeredPane pannello = creaPanel(55,100, 581, 601);
		pannello.setOpaque(false);
		
		JLabel nome=creaLabel("Nome",80, 40+5, 90, 60, panScoreBoard, StatFontPiccolo,2,bianco);
		nome.setHorizontalAlignment(JLabel.CENTER);
		
		//JLabel LabelWins= creaLabel("Wins",280, 60, 40, 60, panScoreBoard, StatFontPiccolo);
		//JLabel LabelLose= creaLabel("Losses",330, 60, 50, 60, panScoreBoard, StatFontPiccolo);
		//JLabel LabelGames= creaLabel("Games",390, 60, 50, 60, panScoreBoard, StatFontPiccolo);
		//JLabel LabelKills= creaLabel("Kills",460, 60, 40, 60, panScoreBoard, StatFontPiccolo);
		//JLabel LabelDeaths= creaLabel("Deaths",510, 60, 50, 60, panScoreBoard, StatFontPiccolo);
		
		Font test = new Font("Bahnschrift SemiBold",Font.BOLD, 13 );
		
		MyButton BottoneVittoria=creaBottone("Wins"      ,243+30+55, 40+6, 57, 60, panScoreBoard, "",  "",  "assets/Immagini/F5760.png");
		BottoneVittoria.setFont(test);
		MyButton BottoneSconfitte=creaBottone("Losses"   ,300+30+55, 40+6, 57, 60,panScoreBoard, "",  "",  "assets/Immagini/F5760.png");
		BottoneSconfitte.setFont(test);
		MyButton BottonePartite=creaBottone("Games"      ,357+30+55, 40+6, 57, 60,panScoreBoard, "",  "",  "assets/Immagini/F5760.png");
		BottonePartite.setFont(test);
		MyButton BottonePokemonUccisi=creaBottone("Kills",414+30+55, 40+6, 57, 60, panScoreBoard, "",  "",  "assets/Immagini/F5760.png");
		BottonePokemonUccisi.setFont(test);
		MyButton BottonePokemonPersi=creaBottone("Deaths",471+30+55, 40+6, 57, 60,panScoreBoard, "",  "",  "assets/Immagini/F5760.png");
		BottonePokemonPersi.setFont(test);
		
		File[] arrayFileAllenatori=new Salvataggio<>().getFileAt("saves");
		
		Allenatore[] arrayAllenatori=new Allenatore[arrayFileAllenatori.length];
		
		Allenatore[] vittorie=new Allenatore[10];
		Allenatore[] sconfitte=new Allenatore[10];
		Allenatore[] partite=new Allenatore[10];
		Allenatore[] kills=new Allenatore[10];
		Allenatore[] morti=new Allenatore[10];
		
		for(int i= 0; i<arrayFileAllenatori.length; i++) {
			Allenatore allenatoreTemp = new Salvataggio<Allenatore>().getItem(arrayFileAllenatori[i]);
			arrayAllenatori[i]=allenatoreTemp;
			
		}
		
		//fare un metodo di questa cosa, con parametri arrayAllenatori,.getStats()[ cambia solo questo qua],vittorie(array di uscita) return void
		//ATTENZIONE SE NUM ESECUZIONI<10 IN VITTORIA CI SONO NULL 
		riempiArrayScoreboard(arrayAllenatori,0,vittorie);
		riempiArrayScoreboard(arrayAllenatori,1,sconfitte);
		riempiArrayScoreboard(arrayAllenatori,2,kills);
		riempiArrayScoreboard(arrayAllenatori,3,morti);
		riempiArrayPartiteScoreboard(arrayAllenatori, 0, 1, partite);
		
		JLayeredPane pannelloWin=creaScoreboardArray(vittorie);
		pannelloWin.setOpaque(false);
		JLayeredPane pannelloLose=creaScoreboardArray(sconfitte);
		pannelloLose.setOpaque(false);
		JLayeredPane pannelloGames=creaScoreboardArray(partite);
		pannelloGames.setOpaque(false);
		JLayeredPane pannelloKils=creaScoreboardArray(kills);
		pannelloKils.setOpaque(false);
		JLayeredPane pannelloDeaths=creaScoreboardArray(morti);
		pannelloDeaths.setOpaque(false);
		
		pannello.add(pannelloWin);
		
		BottoneVittoria.addActionListener
		(e -> {
			  s.quickShoot("ButtonEffect");
			  
			  pannello.remove(pannelloWin);
			  pannello.remove(pannelloLose);
			  pannello.remove(pannelloGames);
			  pannello.remove(pannelloKils);
			  pannello.remove(pannelloDeaths);
			  
			  pannello.add(pannelloWin);
			  this.repaint();
			  });
		BottoneSconfitte.addActionListener
		(e -> {
			  s.quickShoot("ButtonEffect");
			  
			  pannello.remove(pannelloWin);
			  pannello.remove(pannelloLose);
			  pannello.remove(pannelloGames);
			  pannello.remove(pannelloKils);
			  pannello.remove(pannelloDeaths);
			  
			  pannello.add(pannelloLose);
			  this.repaint();

			  });
		BottonePartite.addActionListener
		(e -> {
			  s.quickShoot("ButtonEffect");
			  
			  pannello.remove(pannelloWin);
			  pannello.remove(pannelloLose);
			  pannello.remove(pannelloGames);
			  pannello.remove(pannelloKils);
			  pannello.remove(pannelloDeaths);
			  
			  pannello.add(pannelloGames);
			  this.repaint();

			  });
		BottonePokemonUccisi.addActionListener
		(e -> {
			  s.quickShoot("ButtonEffect");
			  
			  pannello.remove(pannelloWin);
			  pannello.remove(pannelloLose);
			  pannello.remove(pannelloGames);
			  pannello.remove(pannelloKils);
			  pannello.remove(pannelloDeaths);
			  
			  pannello.add(pannelloKils);
			  this.repaint();

			  });
		BottonePokemonPersi.addActionListener
		(e -> {
			  s.quickShoot("ButtonEffect");
			  
			  pannello.remove(pannelloWin);
			  pannello.remove(pannelloLose);
			  pannello.remove(pannelloGames);
			  pannello.remove(pannelloKils);
			  pannello.remove(pannelloDeaths);
			  
			  pannello.add(pannelloDeaths);
			  this.repaint();

			  });
		
		panScoreBoard.add(pannello,Integer.valueOf(2));
		}	

	//----------------------------------------------------------------------
	// 							Builder Pannello FIGHT
	//----------------------------------------------------------------------
	
	/**
	 * Inizializza il pannello "High Pre Fight" nella quale è possibile visualizzare le informazioni degli Allenatori prima di un combattimento.
	 * Include dettagli aggiornati sui giocatori come nome, vittorie, sconfitte, uccisioni e morti.
	 * Mostra anche immagini decorative come pokeball verdi e rosse per indicare le vittorie dei giocatori.
	 * Indica chiaramente il formato della partita ("alla meglio di 3") e visualizza una Coppa per il giocatore
	 * che ha vinto il numero necessario di partite.
	 * 
	 * <p>Il pannello include:
	 * <ul>
	 *   <li>Dettagli dei giocatori con nome, vittorie, sconfitte, uccisioni e morti.</li>
	 *   <li>Decorazioni visive come immagini di pokeball verdi e rosse per le vittorie.</li>
	 *   <li>Indicazione chiara del formato del match ("alla meglio di 3").</li>
	 *   <li>Possibilità di visualizzare la Coppa per il giocatore
	 *       che ha vinto il numero necessario di partite.</li>
	 * </ul>
	 * 
	 * <p>Questo pannello è progettato per fornire un'anteprima visiva delle statistiche e dello stato attuale dei giocatori
	 * prima di iniziare un combattimento nel gioco. Ad ogni fine combattimento si tornerà a questa schermata
	 */
	private void creaHighPreFight() 
		{
			panHighPreFight = creaPanel(0, 0, 700, 400);
			panHighPreFight.setBackground(new Color(150,72,85));
			

			JLabel poken=creaLabelImmagine(0, 0, 700, 400,"assets/Immagini/background prefight alto.png");
			panHighPreFight.add(poken, Integer.valueOf(0));
			
			Color Chiaretto = new Color(90,90,90);
			Color Bianco = new Color(250,250,250);
			
			//playerz
			JLayeredPane pannellorobo = creaPanel(20, 40, 200, 310);
			JLabel G1=creaLabelImmagine(0, 0, 200, 310, "assets/Immagini/schermata info pg prefight g1.png");
			pannellorobo.add(G1, Integer.valueOf(0)); 
			panHighPreFight.add(pannellorobo, Integer.valueOf(2));
			creaLabel("Nome: "+players[0].getNome()          , 15, 35, 200, 50, pannellorobo, StatFont, 2, Bianco);
			creaLabel("Vittorie: "+players[0].getStats()[0], 15, 50+20+15, 200, 50, pannellorobo, StatFont, 2, Bianco);
			creaLabel("Sconfitte: "+players[0].getStats()[1], 15, 100+20+15, 200, 50, pannellorobo, StatFont, 2, Bianco);
			creaLabel("PokeKills: "+players[0].getStats()[2], 15, 150+20+15, 200, 50, pannellorobo, StatFont, 2, Bianco);
			creaLabel("PokeDeaths: "+players[0].getStats()[3], 15, 200+20+15, 200, 50, pannellorobo, StatFont, 2, Bianco);
			
			JLayeredPane pannellorobo2 = creaPanel(464, 40, 200, 310);
			JLabel G2=creaLabelImmagine(0, 0, 200, 310, "assets/Immagini/schermata info pg prefight g2.png");
			pannellorobo2.add(G2, Integer.valueOf(0)); 
			panHighPreFight.add(pannellorobo2,Integer.valueOf(2));
			creaLabel("Nome: "+players[1].getNome()          , 15, 35, 200, 50, pannellorobo2, StatFont, 2, Bianco);
			creaLabel("Vittorie: "+players[1].getStats()[0], 15, 50+20+15, 200, 50, pannellorobo2, StatFont, 2, Bianco);
			creaLabel("Sconfitte: "+players[1].getStats()[1], 15, 100+20+15, 200, 50, pannellorobo2, StatFont, 2, Bianco);
			creaLabel("PokeKills: "+players[1].getStats()[2], 15, 150+20+15, 200, 50, pannellorobo2, StatFont, 2, Bianco);
			creaLabel("PokeDeaths: "+players[1].getStats()[3], 15, 200+20+15, 200, 50, pannellorobo2, StatFont, 2, Bianco);
			
			//Scritta di cosa stai facendo(lol)
			JLayeredPane disegnino = creaPanel(230, 40+130, 224, 50);
			panHighPreFight.add(disegnino,Integer.valueOf(2));
			disegnino.setBackground(Chiaretto);
			creaLabel("Partita alla meglio di 3!", 20, 0, 224, 50, disegnino, StatFont, 2, Bianco);
			JLabel M3 =creaLabelImmagine(0, 0, 224, 50, "assets/Immagini/schermata meglio 3.png");
			disegnino.add(M3,Integer.valueOf(0));
			
			//Pannelli di supporto estetici
			JLayeredPane disegnino2 = creaPanel(220, 240-170, 220-70, 50);
			disegnino2.setOpaque(false);
			JLayeredPane disegnino3 = creaPanel(240+70, 320-50, 224-70, 50);
			disegnino3.setOpaque(false);
			//win playerz
			int j = Winp1;
			for (int i = 0; i < 2; i++) 	
				{
				if (j != 0) 
					{
					
					creaLabelImmagine(15+i*70, 0, 50, 50, "assets/Immagini/pokepalla_verde.png", disegnino2);
					j -= 1;
					}
				else 
					{
					creaLabelImmagine(15+i*70, 0, 50, 50, "assets/Immagini/pokepalla_rossa.png", disegnino2);
					}
				}
			
			j = Winp2;
			for (int i = 0; i < 2; i++) 	
				{
				if (j != 0) 
					{
					
					creaLabelImmagine(15+(1-i)*70, 0, 50, 50, "assets/Immagini/pokepalla_verde.png", disegnino3);
					j -= 1;
					}
				else 
					{
					creaLabelImmagine(15+(1-i)*70, 0, 50, 50, "assets/Immagini/pokepalla_rossa.png", disegnino3);
					}
				}
			
			if (Winp1 == 2) {
				JLabel imj1 = creaLabelImmagine(220+15+2*70, 0+240-170, 50, 50, "assets/Immagini/Coppa.png");
				panHighPreFight.add(imj1, Integer.valueOf(2));
				}
			if (Winp2 == 2) {
				JLabel imj2 = creaLabelImmagine(240+70+15+(1-2)*70, 0+320-50, 50, 50, "assets/Immagini/Coppa.png");
				panHighPreFight.add(imj2, Integer.valueOf(2));
				}
			
			panHighPreFight.add(disegnino2,Integer.valueOf(2));
			//disegnino.setBackground(Chiaro);
			//creaLabel("Vittorie P1: "+Winp1, 50, 0, 224, 50, disegnino2, StatFont, 2, Bianco);

			
			panHighPreFight.add(disegnino3,Integer.valueOf(2));
			//disegnino.setBackground(Chiaro);
			//creaLabel("Vittorie P2: "+Winp2, 50, 0, 224, 50, disegnino3, StatFont, 2, Bianco);

		}
	
	/**
	 * Questo metodo inizializza e configura la schermata di pre-fight del gioco,
	 * includendo la visualizzazione delle informazioni di evoluzione dei Pokemon
	 * per entrambi i giocatori.
	 * 
	 * Durante la configurazione, vengono creati pannelli per i giocatori P1 e P2,
	 * ognuno contenente un'area dedicata alla visualizzazione delle possibilità di
	 * evoluzione dei rispettivi Pokemon.
	 * 
	 * Per il giocatore P1, viene configurata l'area di evoluzione nella parte sinistra
	 * della schermata, mentre per il giocatore P2 nella parte destra, in entrambi i casi
	 * utilizzando il metodo creaZonaEvoluzionePreview().
	 * 
	 * <p>Questo metodo è progettato per preparare la logica necessaria prima di avviare o concludere un combattimento
	 * 
	 * Il metodo gestisce l'abilitazione dei pulsanti "Evolvi" per entrambi i giocatori
	 * in base alla presenza di Pokemon che possono evolversi. Quando viene premuto un pulsante
	 * "Evolvi", si apre un'interfaccia che consente ai giocatori di selezionare un Pokemon
	 * da evolvere, mostrando le informazioni dettagliate prima e dopo l'evoluzione.

	 * Guarda #creaZonaEvoluzionePreview(int, int, int, String) per la configurazione
	 * dell'area di evoluzione dei Pokemon per il giocatore P2.
	 */
	private void creaLowPreFight() 
	{
		if (!s.checkWav("prefight") && !s.checkWav("Win")) {
			s.start("prefight", -1);}
		panLowPreFight = creaPanel(0, 400, 700, 400);
		JLabel poken=creaLabelImmagine(0, 0, 700, 400, "assets/Immagini/background prefight basso.png");
		panLowPreFight.add(poken, Integer.valueOf(0));
		gamerGaming = 0; //RESET GAMERGAMING
		
		//Pannello X EVOLUZIONE
		panLowPreFightP1 = creaZonaEvoluzionePreview(20, 40+25, 0, "P1");
		JLabel G1=creaLabelImmagine(0, 0, 200,250, "assets/Immagini/icona evoluzione g1.png");
		panLowPreFightP1.add(G1, Integer.valueOf(0));
		
		//PANNELLO X EVOLUZIONE P2
		panLowPreFightP2 = creaZonaEvoluzionePreview(464, 40+25, 1, "P2");
		JLabel G2=creaLabelImmagine(0, 0, 200, 250, "assets/Immagini/icona evoluzione g2.png");
		panLowPreFightP2.add(G2, Integer.valueOf(0));
	
		if (Winp1 < 2 && Winp2 < 2)
			{	
				
				MyButton BottonePFI = creaBottone("Inizia il Combattimento!",220,85, 244, 200, panLowPreFight,  "assets/Immagini/schermataStartAndFinish.png",  "assets/Immagini/schermataStartAndFinishScuro.png",  "assets/Immagini/F244200.png");
				BottonePFI.addActionListener
				(e -> {
					  s.quickShoot("ButtonEffect");
					  //starta partita su un thread a parte
					  var Thread2 = new Thread(new JPokeBattle(), "ThreadPartita");
					  Thread2.start();
					  
					  creaLowCombatTurno();
					  creaHighFight();
					  s.stop();
					  s.start("Fight", -1);
					  
					  if (DisplayTXT!= null)
						{
						DisplayTXT.setFont(StatFont);
						DisplayTXT.setText("E' il turno di "+players[gamerGaming].getNome()+"!");
						}
					  
					  this.remove(panHighPreFight);
					  this.remove(panLowPreFight);
					  this.add(panLowCombatTurno);
					  this.add(panHighFight);
					  this.repaint();
					  });
				
				//BTM BACK TO MAIN PERSONALIZZATO DEI PREFIGHT
				MyButton AJAJAJ = creaBottone("",15,320,50,50, panLowPreFight,  "assets/Immagini/Pokeback.png", "assets/Immagini/PokebackScura.png", "assets/Immagini/F5050.png");
				AJAJAJ.addActionListener(f -> 
					{
						s.stop();
						s.quickShoot("ButtonEffect");
						s.start("Main menu 1_2",-1);
						this.remove(panLowPreFight);
						this.remove(panHighPreFight);
						
						
						//temp svuoto, ma probs rimane
						creaHighMultiplayer();
						creaMultiplayer();
						creaNewGame();
						creaHighNewGame();
						creaScoreBoard();
						
						Winp1 = 0;
						Winp2 = 0;
						
						this.add(panHighMainMenu);
						this.add(panLowMainMenu);
						this.repaint();
					});
			
				
				
			}
		else
			{
				//MAX DELLA VITTORIA!!!
				if (!s.checkWav("Win")) {
					s.stop();
					s.start("Win", -1);}
				
				
				if(Winp1==2) 
					{
					players[0].addStats(1, 0, 0, 0);
					players[1].addStats(0, 1, 0, 0);
					}
				else 
					{
					players[1].addStats(1, 0, 0, 0);
					players[0].addStats(0, 1, 0, 0);
					}
				
				new Salvataggio<Allenatore>().salvaItem(players[0], "saves", players[0].getNome());
				new Salvataggio<Allenatore>().salvaItem(players[1], "saves", players[1].getNome());
				
				//BTM BACK TO MAIN PERSONALIZZATO DEI PREFIGHT
				MyButton AHAHAAH = creaBottone("Torna al Menù!",220,85, 244, 200, panLowPreFight,  "assets/Immagini/schermataStartAndFinish.png",  "assets/Immagini/schermataStartAndFinishScuro.png",  "assets/Immagini/F244200.png");
				AHAHAAH.addActionListener(f -> 
					{
						s.quickShoot("ButtonEffect");
						
						this.remove(panLowPreFight);
						this.remove(panHighPreFight);
							
						s.stop();
						s.start("Main menu 1_2",-1);
						//temp svuoto, ma probs rimane
						creaHighMultiplayer();
						creaMultiplayer();
						creaNewGame();
						creaHighNewGame();
						creaScoreBoard();
						
						Winp1 = 0;
						Winp2 = 0;
						
						this.add(panHighMainMenu);
						this.add(panLowMainMenu);
						this.repaint();
					});
				
			}
		
	}
	
	/**
	 * Crea e configura il pannello di combattimento in alto che mostra i due Pokemon che combattono.
	 * Il pannello visualizza le immagini dei Pokemon, le barre della vita e dell'esperienza, e altre informazioni rilevanti
	 * sui Pokemon in campo.
	 * 
	 * <p>Metodi utilizzati:
	 * 	<li> creaPanel(int, int, int, int) per creare il pannello di base.</li>
	 * 	<li> creaLabelImmagine(int, int, int, int, String) per creare etichette con immagini di sfondo.</li>
	 * 	<li> creaLabel(String, int, int, int, int, JComponent, Font, int, Color) per creare etichette con testo.</li>
	 * 	<li> updateLabelHighFight(Pokemon, int) per aggiornare le etichette con le informazioni dei Pokemon.</li>
	 * 
	 */
	private void creaHighFight() 
	{	
		
		panHighFight = creaPanel(0, 0, 700, 400);
		JLabel BACKGROUND=creaLabelImmagine(0, 0, 686, 400, "assets/Immagini/BACKGROUND.png");
		panHighFight.add(BACKGROUND,Integer.valueOf(0));
		
		Pokemon Poke1Default = Francesco.getPokemonGiocatore1();
		Pokemon Poke2Default = Francesco.getPokemonGiocatore2();
		
		Color bianco=new Color(230,230,255);
		
		//test
		JLayeredPane AppoggioDisplay = creaPanel(38, 360, 600+10, 30);
		DisplayTXT = creaLabel("E' il turno di "+players[gamerGaming].getNome()+"!", 10, 0, 580+10, 30, AppoggioDisplay, GeneralFont,2,bianco);
		JLabel background=creaLabelImmagine(0, 0, 600+10, 30, "assets/Immagini/INFO battle.png");
		DisplayTXT.setFont(StatFont);
		AppoggioDisplay.add(background,Integer.valueOf(0));
		
		//DA LEVARE, SOLO PER REFERENCE
		JLayeredPane t1 = creaPanel(90, 140, 192, 192); t1.setBackground(null); //Display P1
		JLayeredPane t2 = creaPanel(405, 30, 192, 192); t2.setBackground(null); //Display P2
		
		labelIMJPK1 = creaLabel("" , 90, 140, 192, 192, panHighFight, GeneralFont,4);
		labelIMJPK1.setIcon(new ImageIcon(Poke1Default.getSpriteRetro()));
		
		labelIMJPK2 = creaLabel("" ,405, 30, 192, 192, panHighFight, GeneralFont,4);
		labelIMJPK2.setIcon(new ImageIcon(Poke2Default.getSpriteFronte()));
		
		//DA TENERE
		JLabel background1=creaLabelImmagine(0, 0, 272, 75, "assets/Immagini/stat pokemon fight.png");
		JLabel background2=creaLabelImmagine(0, 0, 272, 75, "assets/Immagini/stat pokemon fight.png");
		JLayeredPane t4 = creaPanel(50, 30, 272, 75);    //Display G2
		t4.add(background2,Integer.valueOf(0));
		JLayeredPane t3 = creaPanel(365,257, 272, 75);  //Display G1
		t3.add(background1,Integer.valueOf(0));
		
		int x0 = 10; int y0 = 35; int x = 200; int y = 10;
		Font MiniFont = new Font("Bahnschrift SemiBold",Font.BOLD,15);
		Font MiniBiggerFont = new Font("Bahnschrift SemiBold",Font.BOLD,20);
		Color Azzurro = new Color(23+20,171+20,225+20);
		Color Grigio = new Color(50+100,50+100,50+100);
		Color Verde = new Color(50,255,50);
		
		//Barra della Vita G1
		BarraG1 = new JProgressBar(0, 100);
		BarraG1.setBounds(x0, y0, x, y);
		BarraG1.setBackground(Grigio);
		BarraG1.setValue(100);
		BarraG1.setForeground(Verde);
		
		//Barra degli XP G1
		BarraG1XP = new JProgressBar(0, 100);
		BarraG1XP.setBounds(x0, y0+15, x+50, y);
		BarraG1XP.setBackground(Grigio);
		BarraG1XP.setValue(0);
		BarraG1XP.setForeground(Azzurro);
		
		//Barra della Vita G2
		BarraG2 = new JProgressBar(0, 100);
		BarraG2.setBounds(x0, y0, x, y);
		BarraG2.setBackground(Grigio);
		BarraG2.setValue(100);
		BarraG2.setForeground(Verde);
		
		//Barra degli XP G2
		BarraG2XP = new JProgressBar(0, 100);
		BarraG2XP.setBounds(x0, y0+15, x+50, y);
		BarraG2XP.setBackground(Grigio);
		BarraG2XP.setValue(0);
		BarraG2XP.setForeground(Azzurro);	
		
		
		labelNamePK1 = creaLabel(Poke1Default.getNome()      , 10 , 10, 200, 20, t3, MiniBiggerFont,2,bianco);
		labelLVPK1 = creaLabel("𝙇𝙫."+Poke1Default.getLivello(), 215, 10, 60 , 20, t3, MiniFont,2,bianco);
		
		labelNamePK2 = creaLabel(Poke2Default.getNome()       , 10 , 10, 200, 15, t4, MiniBiggerFont,2,bianco);
		labelLVPK2 = creaLabel("𝙇𝙫."+Poke2Default.getLivello(), 215, 10, 60 , 15, t4, MiniFont,2,bianco);
		
		labelPSPK1 = creaLabel("𝙋𝙎:"+Poke1Default.getPs(), 215, 30, 60 , 15, t3, MiniFont,2,bianco);
		labelPSPK2 = creaLabel("𝙋𝙎:"+Poke2Default.getPs(), 215, 30, 60 , 15, t4, MiniFont,2,bianco);
		
		updateLabelHighFight(Poke1Default, 0);
		updateLabelHighFight(Poke2Default, 1);
		
		t3.add(BarraG1,Integer.valueOf(2));
		t3.add(BarraG1XP,Integer.valueOf(2));
		
		t4.add(BarraG2,Integer.valueOf(2));
		t4.add(BarraG2XP,Integer.valueOf(2));
		
		panHighFight.add(t1);
		panHighFight.add(t2);
		panHighFight.add(t3,Integer.valueOf(2));
		panHighFight.add(t4,Integer.valueOf(2));
		panHighFight.add(AppoggioDisplay,Integer.valueOf(2));
		
	}
	
	/**
	 * Crea e configura il pannello di combattimento per il turno corrente del giocatore.
	 * Questo pannello include i pulsanti per eseguire mosse, cambiare Pokemon, aprire la borsa (con un messaggio di errore),
	 * e fuggire dal combattimento, assegnando la vittoria all'avversario.
	 * 
	 * <p>Il pannello mostra il nome del giocatore corrente e aggiorna la visualizzazione in base alle interazioni dell'utente.
	 * 
	 * <p>Metodi utilizzati:
	 * 	<li> creaPanel(int, int, int, int) per creare il pannello di base.</li>
	 * 	<li> creaBottone(String, int, int, int, int, JPanel, String, String, String) per creare i bottoni con specifiche immagini.</li>
	 * 	<li> creaLowCombatMosse() per creare il pannello delle mosse del Pokemon in campo.</li>
	 * 	<li> creaLowCombatSwitch() per creare il pannello per cambiare il Pokemon.</li>
	 * 	<li> updateDisplayWin(int, int, Allenatore, Allenatore) per aggiornare la visualizzazione della vittoria.</li>
	 * 	<li> s.quickShoot(String) per riprodurre effetti sonori.</li>
	 * 
	 */
	private void creaLowCombatTurno() 
		{
		panLowCombatTurno = creaPanel(0, 400, 700, 400);
		
		//E' sottointeso
		//Giocatore Corrente = players[gamerGaming];
		
		//Display di chi gioca (TEMP) (serve observer)
		//JLabel Text = creaLabel("",80,120,400,50);
		//Text.setText("Giocatore "+gamerGaming+" , Nome: "+players[gamerGaming].getNome());
		//panLowCombatTurno.add(Text, Integer.valueOf(6));
		if (DisplayTXT!= null)
			{
			DisplayTXT.setFont(StatFont);
			DisplayTXT.setText("E' il turno di "+players[gamerGaming].getNome()+"!");
			}
		
		//Bottone che porta alle mosse del pokemon in campo 686-60
		MyButton BottoneUsaMossa = creaBottone("",110,76,465,200, panLowCombatTurno,  "", "" ,"assets/Immagini/F465200.png");
		BottoneUsaMossa.addActionListener(e -> 
			{
			s.quickShoot("ButtonEffect");
			creaLowCombatMosse();
			this.remove(panLowCombatTurno);
			this.add(panLowCombatMosse);
			this.repaint();
			});	
		
		//Bottone che porta alla schermata di cambio pokemon
		MyButton BottoneCambiaPokemon = creaBottone("",533,282,150,100, panLowCombatTurno,  "assets/Immagini/CAMBIO.png", "assets/Immagini/CAMBIOScuro.png", "assets/Immagini/F150100.png");
		BottoneCambiaPokemon.addActionListener(e -> 
			{
			s.quickShoot("ButtonEffect");
			creaLowCombatSwitch();
			this.remove(panLowCombatTurno);
			this.add(panLowCombatSwitch);
			this.repaint();
			});
		
		//Bottone finto
		MyButton BottoneBag = creaBottone("",0,282,150,100, panLowCombatTurno,  "assets/Immagini/BAG.png",  "assets/Immagini/BAGScuro.png",  "assets/Immagini/F150100.png");
		BottoneBag.addActionListener(e -> {s.quickShoot("ButtonEffect");JOptionPane.showMessageDialog(null, "THERE'S NO ITEMS!\nGET GOOD AND FIGHT!!!", "ERRORE! Gli Oggetti NON Sono Ammessi!", JOptionPane.ERROR_MESSAGE);});
		
		//Annulla in automatico il fight dando la vittoria all'avversario
		MyButton Fuga = creaBottone("",269,302,150,80, panLowCombatTurno,  "assets/Immagini/RUN.png",  "assets/Immagini/RUNScuro.png",  "assets/Immagini/F15080.png");
		Fuga.addActionListener(e -> {
			s.quickShoot("ButtonEffect");
			s.quickShoot("FUGA",10);
			this.remove(panLowCombatTurno);
			this.add(panLowCombatWAIT);

			int gamerwin = 1;
			int gamerlost = 0;
			if (gamerGaming == 1) {gamerlost = 1; gamerwin = 0;} 
			updateDisplayWin(gamerwin, gamerlost, players[gamerwin], players[gamerlost]);
			});
	
		//img backround, temp, farebbe comodo un observer?
		JLabel immaginez = creaLabel("",0,0,686,400);
		if (gamerGaming == 0) {immaginez.setIcon(new ImageIcon("assets/Immagini/background fight g1.png"));} //IMG BLU G1
		else{immaginez.setIcon(new ImageIcon("assets/Immagini/background fight g2.png"));}  //IMG BLU G2
		//Add vari
		panLowCombatTurno.add(immaginez, Integer.valueOf(0));
		}

	/**
	 * Crea e configura il pannello delle mosse del Pokemon in campo.
	 * Il pannello visualizza i bottoni delle mosse del Pokemon e permette al giocatore di selezionare una mossa da utilizzare.
	 * Se il Pokemon non ha PP disponibili per nessuna delle sue mosse, viene visualizzato un bottone speciale per la mossa "Scontro".
	 * 
	 *<p> Funzionalità:
	 * 	<li> Visualizza un pannello con i bottoni delle mosse del Pokemon in campo.</li>
	 * 	<li> Permette al giocatore di selezionare una mossa da utilizzare.</li>
	 * 	<li> Se il Pokemon non ha PP disponibili per nessuna delle sue mosse, visualizza un bottone per la mossa "Scontro".</li>
	 * 	<li> Invia la mossa selezionata al metodo appropriato per il giocatore corrente.</li>
	 * 	<li> Aggiorna l'interfaccia grafica per passare al turno del giocatore successivo.</li>
	 * 	<li> Include un bottone per tornare alla schermata del turno di combattimento.</li>
	 */
	private void creaLowCombatMosse() 
		{
		panLowCombatMosse = creaPanel(0, 400, 700, 400);
		
		//farebbe comodo un observer?
		Pokemon IlPokemonACuiVedereMosse;
		if (gamerGaming == 0) {IlPokemonACuiVedereMosse = Francesco.getPokemonGiocatore1();} //Mosse di Pokemon G1
		else {IlPokemonACuiVedereMosse = Francesco.getPokemonGiocatore2();}					 //Mosse di Pokemon G2
		
		
		//Se il pokemon ha almeno una mossa con + di 0 PP allora schermata normale
		if (checkMossePokemon(IlPokemonACuiVedereMosse)) {
			
			MyButton[] GruppoMosse = getSimpleButtonGrid(150,90,20+192 ,20+75, 4 , panLowCombatMosse);
			for (MyButton myButton : GruppoMosse) {
			//Setto il design dei bottoni e setto l'ActionListener
			setBottoneMossa(myButton,IlPokemonACuiVedereMosse, false);
			//Mostra i pp
			if (IlPokemonACuiVedereMosse.getMossa(myButton.getContenuto())!=null) myButton.setSpecialText(IlPokemonACuiVedereMosse.getMossa(myButton.getContenuto()).getPP()+"/"+IlPokemonACuiVedereMosse.getMossa(myButton.getContenuto()).getPPMax(), 120, 65);
			//Setta la scelta mossa
			myButton.addActionListener(e -> 
				{
				s.quickShoot("ButtonEffect");
				//Mossa selezionata
				Mossa sceltamossa = IlPokemonACuiVedereMosse.getMossa(myButton.getContenuto());
				//Comunico a Partita
				if (gamerGaming == 0) 
					{
					Francesco.mossaGiocatore1(sceltamossa); //Mando a partita la scelta di G1
					gamerGaming = 1;						//Cambio il giocatore in GUI
					}
				
				else{
					Francesco.mossaGiocatore2(sceltamossa); //Mando a partita la scelta di G2
					gamerGaming = 0;						//Cambio il giocatore in GUI
					}
				
				//Completato il turno passo a quello del giocatore successivo
				this.remove(panLowCombatMosse);
				
				if (gamerGaming == 0) {
					this.add(panLowCombatWAIT);
					this.repaint();
					return;
					}
				creaLowCombatTurno();
				this.add(panLowCombatTurno);
				this.repaint();
				});
			
		}} 
		//Altrimenti se non ha alcuna mossa con più di 1 pp ha solo LA MOSSA
		else 
			{
			Mossa mossatragica = new Salvataggio<Mossa>().getItem("assets/Mosse/Scontro.ser");
			MyButton BottoneSecret = creaBottone(mossatragica.getNome(), 245, 162,192,75, panLowCombatMosse, "assets/Immagini/Mosse/1.png", "assets/Immagini/MosseScure/1.png","assets/Immagini/F19275.png" );
			BottoneSecret.addActionListener(e -> 
				{
					s.quickShoot("ButtonEffect");
					//Mossa selezionata
					Mossa sceltamossa = mossatragica;
					//Comunico a Partita
					if (gamerGaming == 0) 
						{
						Francesco.mossaGiocatore1(sceltamossa); //Mando a partita la scelta di G1
						gamerGaming = 1;						//Cambio il giocatore in GUI
						}
					
					else{
						Francesco.mossaGiocatore2(sceltamossa); //Mando a partita la scelta di G2
						gamerGaming = 0;						//Cambio il giocatore in GUI
						}
					
					//Completato il turno passo a quello del giocatore successivo
					this.remove(panLowCombatMosse);
					
					if (gamerGaming == 0) {
						this.add(panLowCombatWAIT);
						this.repaint();
						return;
						}
					creaLowCombatTurno();
					this.add(panLowCombatTurno);
					this.repaint();
				});
			}
		
		//img backround, temp
		JLabel immaginez = new JLabel();
		if (gamerGaming == 0) {immaginez.setIcon(new ImageIcon("assets/Immagini/background mosse g1.png"));} //IMG BLU G1
		else{immaginez.setIcon(new ImageIcon("assets/Immagini/background mosse g2.png"));}  //IMG BLU G2
		immaginez.setBounds(0,0,686,400);
		panLowCombatMosse.add(immaginez, Integer.valueOf(0));
		
		//bottone back to schermata fight
		MyButton BTF = creaBottone("",15,320,50,50, panLowCombatMosse, "assets/Immagini/Pokeback.png", "assets/Immagini/PokebackScura.png", "assets/Immagini/F5050.png");
		BTF.addActionListener
			(  e -> 
				{
				s.quickShoot("ButtonEffect");
				this.remove(panLowCombatMosse);
				this.add(panLowCombatTurno);
				this.repaint();}
			);
		
		this.repaint();
		}
		
	/**
	 * Crea e configura il pannello per la selezione del Pokemon da cambiare durante il combattimento.
	 * Il pannello visualizza i bottoni per i Pokemon disponibili del giocatore, consentendo di selezionare un nuovo Pokemon da inviare in campo.
	 * 
	 * Funzionalità:
	 *  <li>Visualizza un pannello con i bottoni dei Pokemon disponibili del giocatore.</li>
	 *  <li>Permette al giocatore di selezionare un Pokemon da inviare in campo.</li>
	 *  <li>Visualizza le statistiche complete del Pokemon selezionato con {@link #getStatFullSystem}.</li>
	 *  <li>Visualizza le mosse complete del Pokemon selezionato con {@link #getMossaFullSystem}.</li>
	 *  <li>Aggiorna l'interfaccia grafica per passare al turno del giocatore successivo.</li>
	 *  <li>Include un bottone per tornare alla schermata del turno di combattimento.</li>
	 */
	private void creaLowCombatSwitch() 
		{
		panLowCombatSwitch = creaPanel(0, 400, 700, 400);
		
		
		//img backround, temp
		JLabel immaginez = creaLabel("",0,0,686,400);
		if (gamerGaming == 0) {immaginez.setIcon(new ImageIcon("assets/Immagini/background scelta pokemon g1.png"));} //IMG BLU G1
		else{immaginez.setIcon(new ImageIcon("assets/Immagini/background scelta pokemon g2.png"));}  //IMG BLU G2
		panLowCombatSwitch.add(immaginez, Integer.valueOf(0));
		
		//Gruppo di 6 Bottoni corrispondenti ai Pokemon
		int x0 = 100; int x = 192;
		int y0 = 40; int y = 75;
		MyButton[] GruppoPokemonz = getButtonGrid(x0, y0, x, y, 6, panLowCombatSwitch);
		
		
		//Per ogni bottone della griglia che mostra i pokemon disponibili del giocatore, colora il bottone e dagli funzionalità
		for (MyButton myButton : GruppoPokemonz) 
			{
			setBottonePokemon(myButton,players[gamerGaming]);
			
			
			
			//spegne il bottone del pokemon in campo, lo colora di azzurro
			if (gamerGaming == 0) {if (Francesco.getPokemonGiocatore1().equals(players[0].getPokemonPosizione(myButton.getContenuto()))){myButton.setEnabled(false);myButton.setSfondo(getImage("assets/Immagini/icona pokemon CORRENTE.png"));removeMouseListener(myButton, "assets/Immagini/icona pokemon CORRENTE.png", "assets/Immagini/icona pokemon CORRENTE.png", "F19275");}}
			else {if (Francesco.getPokemonGiocatore2().equals(players[1].getPokemonPosizione(myButton.getContenuto()))){myButton.setEnabled(false);myButton.setSfondo(getImage("assets/Immagini/icona pokemon CORRENTE.png"));removeMouseListener(myButton, "assets/Immagini/icona pokemon CORRENTE.png", "assets/Immagini/icona pokemon CORRENTE.png", "F19275");}}
			
			myButton.addActionListener(e -> 
				{
				s.quickShoot("ButtonEffect");
				//Schermata Conferma Cambio
				JLayeredPane CambioPokemon = creaPanel(0, 400, 700, 400);
				JLabel poken1=creaLabelImmagine(0, 0, 686, 400,"assets/Immagini/background scelta pokemon g1.png");
				JLabel poken2=creaLabelImmagine(0, 0, 686, 400,"assets/Immagini/background scelta pokemon g2.png");
				if (gamerGaming == 0) {CambioPokemon.add(poken1,Integer.valueOf(0));} //IMG BLU G1
				else{CambioPokemon.add(poken2,Integer.valueOf(0));}  //IMG BLU G2
				
				//Label + Bottone Conferma displayano il pokemon selezionato x il cambio
				//                                QUESTE COORDINATE SONO QUASI LE STESSE DATE A CONFERMA        
				JLabel microlabel = creaLabel("CONFERMA CAMBIO CON:",  165, -15, 450, 200,  CambioPokemon, GeneralFont,  4);
				microlabel.setForeground(Color.WHITE);
				JLabel microlabel2 = creaLabel(players[gamerGaming].getPokemonPosizione(myButton.getContenuto()).getNome(),  165, 50, 450, 200, CambioPokemon,GeneralFont, 4);
				microlabel2.setForeground(Color.WHITE);
				
				//Prende il pokemon, il suo nome e la sua immagine, mette come immagine nel bottone e setta il bottone
				Pokemon pokemondesignato = players[gamerGaming].getPokemonPosizione(myButton.getContenuto());
				MyButton Conferma = creaBottone("", 125, 50, 450, 200, CambioPokemon, "assets/Immagini/schermataCambiopokemon.png", "assets/Immagini/schermataCambiopokemonScuro.png" , "assets/Immagini/F450200.png"); //serve qui
				Conferma.setSpecialIcon(getImage(pokemondesignato.getSpriteFronte()), 250, 20);
				Conferma.addActionListener(f -> 
					{

					s.quickShoot("ButtonEffect");
					
					//segna che il giocatore ha cambiato pokemon, cambia il giocatore che deve giocare
					if(gamerGaming == 0) {gamerGaming = 1;Francesco.switchPokemonG1(myButton.getContenuto());}
					else {gamerGaming = 0; Francesco.switchPokemonG2(myButton.getContenuto());}
					
					//Rimuove pannello e torna a scelta per nuovo turno
					this.remove(CambioPokemon);
					if (gamerGaming == 0) {this.add(panLowCombatWAIT);this.repaint();}
					
					else {
					creaLowCombatTurno(); //Aggiorna Combat turno, observer?
					this.add(panLowCombatTurno);
					this.repaint();
					}});
				
				//Back to cambio pokemon
				MyButton BackToLowCombatSwitch = creaBottone("", 20, 383-20-50, 50, 50 , CambioPokemon, "assets/Immagini/Pokeback.png", "assets/Immagini/PokebackScura.png", "assets/Immagini/F5050.png");
				BackToLowCombatSwitch.addActionListener(j -> 
					{
					s.quickShoot("ButtonEffect");
					this.remove(CambioPokemon);
					this.add(panLowCombatSwitch);
					this.repaint();
					});
				
				//SUPER COMANDO, crea l'intero sistema di stat del pokemon, displayato dal bottone BottoneStats (observer?)
				MyButton BottoneStats = creaBottone("STATS", 683-192-20-192-20, 383-20-75, 192, 75, CambioPokemon, "assets/Immagini/BottoneMainMenuV2.png","assets/Immagini/TestScuro.png", "assets/Immagini/F19275.png");
				getStatFullSystem(pokemondesignato, CambioPokemon, BottoneStats, 0, 400, 700, 400);
				
				//SUPER COMANDO, crea l'intero sistema di mosse del pokemon, displayato dal bottone BottoneStats (observer?)
				MyButton BottoneMosse = creaBottone("MOSSE", 683-192-20, 383-20-75, 192, 75, CambioPokemon, "assets/Immagini/BottoneMainMenuV2.png","assets/Immagini/TestScuro.png","assets/Immagini/F19275.png");
				getMossaFullSystem(x0, y0+20, x,  y, pokemondesignato,  CambioPokemon, BottoneMosse);
				
				
				//Cosa fa il bottone originario (tutto questo è uno switch case che porta alla schermata di CambioPokemon)
				this.remove(panLowCombatSwitch);
				this.add(CambioPokemon);
				this.repaint();
				});
			}

		
		
		//bottone back to schermata fight
		MyButton BTF = creaBottone("",15,320,50,50, panLowCombatSwitch, "assets/Immagini/Pokeback.png", "assets/Immagini/PokebackScura.png", "assets/Immagini/F5050.png");
		BTF.addActionListener
			(  e -> 	
				{
				s.quickShoot("ButtonEffect");
				this.remove(panLowCombatSwitch);
				this.add(panLowCombatTurno);
				this.repaint();}
			);
		}
	
	//----------------------------------------------------------------------
	// 							Metodi PreFight
	//----------------------------------------------------------------------
	
	/*
	 Il metodo gestisce l'abilitazione dei pulsanti "Evolvi" per entrambi i giocatori
	 * in base alla presenza di Pokemon che possono evolversi. Quando viene premuto un pulsante
	 * "Evolvi", si apre un'interfaccia che consente ai giocatori di selezionare un Pokemon
	 * da evolvere, mostrando le informazioni dettagliate prima e dopo l'evoluzione.
	 */
	private JLayeredPane creaZonaEvoluzionePreview(int x0, int y0, int Giocatore, String Nome) 
		{
		//Pannello sx	
		JLayeredPane pannello = creaPanel(x0, y0, 200, 310-60);
		panLowPreFight.add(pannello, Integer.valueOf(2));
		MyButton bottonep = creaBottone("Evolvi", 54, 180, 96, 38, pannello, "assets/Immagini/MossePiccole/0.png", "" ,"");
		
		pannello.setBackground(Color.DARK_GRAY);
		
		JLabel imj = creaLabelImmagine(75, 10, 50, 50, "assets/Immagini/pokepalla_rossa.png", pannello);
		JLabel textnotifica = creaLabel("<html>Il giocatore "+ players[Giocatore].getNome()+" non ha evoluzioni Pokemon disponibili.</html>", 20, 60, 180, 120,pannello, StatFont, 2 , Color.WHITE);
		
		
		
		
		//Accende il bottone che porta alla zona evolutiva se ALMENO UN POKEMON può evolversi
		bottonep.setEnabled(false);
		for (Pokemon poke : players[Giocatore].getPokemonLista()) {
			if (poke!=null) 
				{
				if (poke.checkEvoluzione()) 
					{
					pannello.remove(imj);
					imj = creaLabelImmagine(75, 10, 50, 50, "assets/Immagini/pokepalla_verde.png", pannello);
					textnotifica.setText("<html>Il giocatore "+ players[Giocatore].getNome()+" può evolvere un/dei Pokemon!</html>");
					//System.out.println("Individuata evoluzione");
					bottonep.setEnabled(true);
					bottonep.setSfondo(getImage("assets/Immagini/MossePiccole/14.png"));
					removeMouseListener(bottonep,"assets/Immagini/MossePiccole/14.png","assets/Immagini/MossePiccole/13.png","assets/Immagini/F10040.png");

					this.repaint();
					break;
					}
					
				}
			}
		
		//Bottone zona evolutiva
		bottonep.addActionListener(e -> 	
			{
			s.quickShoot("ButtonEffect");
			int SpiazzamY = 50;
			//I due pannelli dell'evoluzione
			JLayeredPane panHighEvolutivo = creaPanel(0, 0, 700, 400); 
			panHighEvolutivo.setBackground(new Color(10, 10, 10));
			JLayeredPane panLowEvolutivo = creaPanel(0, 400, 700, 400); 
			panLowEvolutivo.setBackground(new Color(10, 10, 10));
			
			//PAVIMENTO EVOLUZIONE
			creaLabelImmagine(50, 60+SpiazzamY, 192, 192, "assets/Immagini/faro.png", panHighEvolutivo);
			
			JLabel Imj1 = creaLabel("", 50, 60+SpiazzamY, 192, 192); 
			panHighEvolutivo.add(Imj1, Integer.valueOf(3));
			
			MyButton bottoneconferma = creaBottone("CONFERMA", 245, 270+SpiazzamY, 192, 75, panHighEvolutivo, "assets/Immagini/icona pokemon CORRENTE.png", "assets/Immagini/CorrenteScuro.png", "assets/Immagini/F19275.png");
			bottoneconferma.setEnabled(false);
			
			//Test
			panHighEvolutivo.remove(bottoneconferma);
			
			// LabelStats
			JLabel testo1 = creaLabel("<html><pre>Nome  :</pre></html>", 215+50, 60 +SpiazzamY, 370, 20,panHighEvolutivo, StatFont, 2, Color.WHITE);
			JLabel testo2 = creaLabel("<html><pre>TipoN1:</pre></html>", 215+50, 80 +SpiazzamY, 370, 20,panHighEvolutivo, StatFont, 2, Color.WHITE);
			JLabel testo3 = creaLabel("<html><pre>TipoN2:</pre></html>", 215+50, 100+SpiazzamY, 370, 20,panHighEvolutivo, StatFont, 2, Color.WHITE);
			JLabel testo4 = creaLabel("<html><pre>PS    :</pre></html>", 215+50, 130+SpiazzamY, 370, 20,panHighEvolutivo, StatFont, 2, Color.WHITE);
			JLabel testo5 = creaLabel("<html><pre>ATK   :</pre></html>", 215+50, 150+SpiazzamY, 370, 20,panHighEvolutivo, StatFont, 2, Color.WHITE);
			JLabel testo6 = creaLabel("<html><pre>DIF   :</pre></html>", 215+50, 170+SpiazzamY, 370, 20,panHighEvolutivo, StatFont, 2, Color.WHITE);
			JLabel testo7 = creaLabel("<html><pre>ATKSP :</pre></html>", 215+50, 190+SpiazzamY, 370, 20,panHighEvolutivo, StatFont, 2, Color.WHITE);
			JLabel testo8 = creaLabel("<html><pre>DIFSP :</pre></html>", 215+50, 210+SpiazzamY, 370, 20,panHighEvolutivo, StatFont, 2, Color.WHITE);
			JLabel testo9 = creaLabel("<html><pre>VEL   :</pre></html>", 215+50, 230+SpiazzamY, 370, 20,panHighEvolutivo, StatFont, 2, Color.WHITE);
			
			JLabel testo10 = creaLabel("<html><pre>"+"Lv. Necessario per Evoluzione: "+" </pre></html>", 0, 40, 686, 30,panHighEvolutivo, new Font("Bahnschrift SemiBold",Font.PLAIN,22), 2, Color.WHITE);
			testo10.setHorizontalAlignment(SwingConstants.CENTER);
			
			//Griglia di bottoni dedicata a pokemon
			MyButton[] arraybottonipokemon = getButtonGrid(100, 40, 192, 75, 6, panLowEvolutivo); 
			Allenatore gamerselezionato = players[Giocatore];
			
			//Array di un elemento per tenere traccia dell'ultimo Pokemon selezionato
			final Pokemon[] ultimoPokemonSelezionato = {null};
			
			//I bottoni dei pokemon in basso, se premuti aggiornano i label presenti in panHighEvolutivo
			for (MyButton myButton : arraybottonipokemon) {
			    setBottonePokemon(myButton, gamerselezionato);
			    myButton.removeMouseListener(myButton.getMouseListeners()[1]);
			    myButton.addMouseListener(new MouseAdapter() {
			        public void mouseEntered(MouseEvent evt) {
			            myButton.addFocus(getImage("assets/Immagini/F19275.png")); 
			            //System.out.println("SOPRA GRID");
			            myButton.repaint();
			        }
			        public void mouseExited(MouseEvent evt) {
			            myButton.removeFocus();
			            //System.out.println("FUORI GRID");
			            myButton.repaint();
			        }
			        public void mousePressed(MouseEvent evt) {
			            if (myButton.isEnabled()){
			                myButton.setSfondo(getImage("assets/Immagini/icona pokemon Scuro.png"));
			                //System.out.println("PREMUTO");
			                myButton.repaint();
			            }
			        }
			        public void mouseReleased(MouseEvent evt) {
			            if (myButton.isEnabled()){
			                myButton.setSfondo(getImage("assets/Immagini/icona pokemon.png"));
			                //System.out.println("RILASCIATO");
			                myButton.repaint();
			            }
			        }
			    });
			    
			    myButton.addActionListener(j -> {
			        s.quickShoot("ButtonEffect");
			        //Pokemon
			        Pokemon pokemonselezionato = gamerselezionato.getPokemonPosizione(myButton.getContenuto());
			        if (pokemonselezionato != null) {
			            ultimoPokemonSelezionato[0] = pokemonselezionato;
			            String NomeATT = pokemonselezionato.getNome();
			            int lv = pokemonselezionato.getLivello();
			
			            //Tipi attuali
			            String tipoA = pokemonselezionato.getTipo1() != 0 ? Tipi.values()[pokemonselezionato.getTipo1()-1].toString() : "";
			            String tipoB = pokemonselezionato.getTipo2() != 0 ? Tipi.values()[pokemonselezionato.getTipo2()-1].toString() : "";
			
			            //Stat pokemon attuale
			            int Psmax = pokemonselezionato.getPsMax();
			            int Atkmax = pokemonselezionato.getAttaccoMax();
			            int Defmax = pokemonselezionato.getDifesaMax();
			            int AtkSPmax = pokemonselezionato.getAttaccoSpMax();
			            int DefSPax = pokemonselezionato.getDifesaSpMax();
			            int Velmax = pokemonselezionato.getVelocitàMax();
			
			            if (pokemonselezionato.getEvoluzione() != null && !pokemonselezionato.getEvoluzione().isEmpty() && new Salvataggio<>().checkFileAt("assets/Pokemonz/"+pokemonselezionato.getEvoluzione()+".ser")) {
			                Pokemon EvoluzioneCongelata = new Salvataggio<Pokemon>().getItem("assets/Pokemonz/" + pokemonselezionato.getEvoluzione() + ".ser");
			                String NomeFUT = EvoluzioneCongelata.getNome();
			
			                //Tipi futuri
			                String tipo1 = EvoluzioneCongelata.getTipo1() != 0 ? Tipi.values()[EvoluzioneCongelata.getTipo1()-1].toString() : "";
			                String tipo2 = EvoluzioneCongelata.getTipo2() != 0 ? Tipi.values()[EvoluzioneCongelata.getTipo2()-1].toString() : "";
			
			                //Stat poke futuro evoluto
			                int PsBaseFUT = EvoluzioneCongelata.getPsBase();
			                int AtkBaseFUT = EvoluzioneCongelata.getAttaccoBase();
			                int DefBaseFUT = EvoluzioneCongelata.getDifesaBase();
			                int AtkSPBaseFUT = EvoluzioneCongelata.getAttaccoSpBase();
			                int DefSPBaseFUT = EvoluzioneCongelata.getDifesaSpBase();
			                int VelBaseFUT = EvoluzioneCongelata.getVelocitàBase();
			
			                int PsIND = EvoluzioneCongelata.getPuntiAllenamentoPs();
			                int AtkIND = EvoluzioneCongelata.getPuntiAllenamentoAtk();
			                int DefIND = EvoluzioneCongelata.getPuntiAllenamentoDif();
			                int AtkSPIND = EvoluzioneCongelata.getPuntiAllenamentoAtkSp();
			                int DefSPIND = EvoluzioneCongelata.getPuntiAllenamentoDifSp();
			                int VelIND = EvoluzioneCongelata.getPuntiAllenamentoVel();
			
			                if (pokemonselezionato.checkEvoluzione()) {
			                    testo10.setText("<html><pre>" + "Lv. Necessario per Evoluzione: " + pokemonselezionato.getLivelloEvoluzione() + " RAGGIUNTO!</pre></html>");
			                    bottoneconferma.setEnabled(true);
			                   
			                    
			                    //Test
			        			try{panHighEvolutivo.add(bottoneconferma);}catch(Exception f) {}
			        			
			        			this.repaint();
			        			
			                    //Rimuovi tutti i listener precedenti
			                    for (ActionListener al : bottoneconferma.getActionListeners()) {
			                        bottoneconferma.removeActionListener(al);
			                    }
			
			                    //Aggiungi il nuovo listener a conferma, se premuto avvia evoluzione
			                    bottoneconferma.addActionListener(k -> {
			                    	
				        			try{panHighEvolutivo.remove(bottoneconferma);}catch(Exception f) {}
			                        s.quickShoot("ButtonEffect");
			                        Imj1.setIcon(new ImageIcon(""));                        
			                        testo1.setText(String.format("<html><pre>Nome  :</pre></html>", "Nome"));
			                        testo2.setText(String.format("<html><pre>TipoN1:</pre></html>", "TipoN1"));
			                        testo3.setText(String.format("<html><pre>TipoN2:</pre></html>", "TipoN2"));
			                        testo4.setText(String.format("<html><pre>PS    :</pre></html>", "PS" ));
			                        testo5.setText(String.format("<html><pre>ATK   :</pre></html>", "ATK" ));
			                        testo6.setText(String.format("<html><pre>DIF   :</pre></html>", "DIF" ));
			                        testo7.setText(String.format("<html><pre>ATKSP :</pre></html>", "ATKSP" ));
			                        testo8.setText(String.format("<html><pre>DIFSP :</pre></html>", "DIFSP"));
			                        testo9.setText(String.format("<html><pre>VEL   :</pre></html>", "VEL" ));
			                        testo10.setText("<html><pre>"+"Lv. Necessario per Evoluzione: "+" </pre></html>");
			
			                        //Evolvi il Pokemon selezionato e salvalo in memoria
			                        ultimoPokemonSelezionato[0].siEvolve();
			                        new Salvataggio<Allenatore>().salvaItem(gamerselezionato, "saves", gamerselezionato.getNome());
			                        setBottonePokemon(myButton, gamerselezionato);
			                        this.repaint();
			                    });
			
			                    //panHighEvolutivo
			                } else {
			                	
			        			try{panHighEvolutivo.remove(bottoneconferma);}catch(Exception f) {}
			                    lv = pokemonselezionato.getLivelloEvoluzione();
			                    testo10.setText("<html><pre>" + "Lv. Necessario per Evoluzione: " + pokemonselezionato.getLivelloEvoluzione() + " Non Raggiunto 🙁</pre></html>");
			                    bottoneconferma.setEnabled(false);
			                    this.repaint();
			                }
								//Applico la formula
								int PsmaxFUT = formulapsup(PsBaseFUT, PsIND, lv);
								int AtkmaxFUT = formulalevelup(AtkBaseFUT, AtkIND, lv);
								int DefmaxFUT = formulalevelup(DefBaseFUT, DefIND, lv);
								int AtkSPmaxFUT = formulalevelup(AtkSPBaseFUT, AtkSPIND, lv);
								int DefSPaxFUT = formulalevelup(DefSPBaseFUT, DefSPIND, lv);
								int VelmaxFUT = formulalevelup(VelBaseFUT, VelIND, lv); 
								
								//Set dei label
								Imj1.setIcon(new ImageIcon(pokemonselezionato.getSpriteFronte()));
								testo1.setText(String.format("<html><pre>%-6s: %-10s -> %-10s</pre></html>"   ,"Nome",NomeATT, NomeFUT));
								testo2.setText(String.format("<html><pre>%-6s: %-10s -> %-10s</pre></html>"   ,"TipoN1",tipoA, tipo1));
								testo3.setText(String.format("<html><pre>%-6s: %-10s -> %-10s</pre></html>"   ,"TipoN2",tipoB, tipo2));
								testo4.setText(String.format("<html><pre>%-6s: %-10d -> %-10d</pre></html>"   ,"PS",Psmax,PsmaxFUT));
								testo5.setText(String.format("<html><pre>%-6s: %-10d -> %-10d</pre></html>"   ,"ATK",Atkmax,AtkmaxFUT));
								testo6.setText(String.format("<html><pre>%-6s: %-10d -> %-10d</pre></html>"   ,"DIF",Defmax,DefmaxFUT));
								testo7.setText(String.format("<html><pre>%-6s: %-10d -> %-10d</pre></html>"   ,"ATKSP",AtkSPmax,AtkSPmaxFUT));
								testo8.setText(String.format("<html><pre>%-6s: %-10d -> %-10d</pre></html>"   ,"DIFSP",DefSPax, DefSPaxFUT));
								testo9.setText(String.format("<html><pre>%-6s: %-10d -> %-10d</pre></html>"   ,"VEL",Velmax,VelmaxFUT));
								
								}//Chiusura if pokemonselezionato.getEvoluzione()!= null && pokemonselezionato.getEvoluzione()!= ""
								else 
								{
									
				        			try{panHighEvolutivo.remove(bottoneconferma);}catch(Exception f) {}
									testo10.setText("<html><pre>"+"Il Pokemon non ha altre EVOLUZIONI!</pre></html>");
									Imj1.setIcon(new ImageIcon(pokemonselezionato.getSpriteFronte()));
									testo1.setText(String.format("<html><pre>%-6s: %-10s -> %-10s</pre></html>"   ,"Nome",NomeATT,NomeATT));
									testo2.setText(String.format("<html><pre>%-6s: %-10s -> %-10s</pre></html>"   ,"TipoN1",tipoA,tipoA));
									testo3.setText(String.format("<html><pre>%-6s: %-10s -> %-10s</pre></html>"   ,"TipoN2",tipoB,tipoB));
									testo4.setText(String.format("<html><pre>%-6s: %-10d -> %-10d</pre></html>"   ,"PS",Psmax,Psmax));
									testo5.setText(String.format("<html><pre>%-6s: %-10d -> %-10d</pre></html>"   ,"ATK",Atkmax,Atkmax));
									testo6.setText(String.format("<html><pre>%-6s: %-10d -> %-10d</pre></html>"   ,"DIF",Defmax,Defmax));
									testo7.setText(String.format("<html><pre>%-6s: %-10d -> %-10d</pre></html>"   ,"ATKSP",AtkSPmax,AtkSPmax));
									testo8.setText(String.format("<html><pre>%-6s: %-10d -> %-10d</pre></html>"   ,"DIFSP",DefSPax,DefSPax));
									testo9.setText(String.format("<html><pre>%-6s: %-10d -> %-10d</pre></html>"   ,"VEL",Velmax,Velmax));
									bottoneconferma.setEnabled(false);
									this.repaint();
								}
							
							}

						
					});
					
					
				}
				
				
				
				//TORNA BACK TO CHIAMANTE, MA PRIMA LO RESETTA, ATTENZIONE (x aggiornare)
				MyButton BottoneBack = creaBottone("", 15, 320, 50, 50, panLowEvolutivo, "assets/Immagini/Pokeback.png", "assets/Immagini/PokebackScura.png", "assets/Immagini/F5050.png");
				BottoneBack.addActionListener(j -> {
					
					s.quickShoot("ButtonEffect");
					//Torna dietro e ricrea il pannello di prefight
					this.remove(panLowEvolutivo);
					this.remove(panHighEvolutivo);
					creaLowPreFight();
					creaHighPreFight();
					this.add(panLowPreFight);
					this.add(panHighPreFight);
					this.repaint();
					});
				
				//Cambio da PreFight a PannelloEvolutivo
				this.remove(panLowPreFight);
				this.remove(panHighPreFight);
				this.add(panLowEvolutivo);
				this.add(panHighEvolutivo);
				this.repaint();
				
			});
		
		return pannello;
		
		}
	
	//Supporto evoluzione (tutto ciò che non è ps)
	private int formulalevelup(int puntiBase, int puntiIndividuali, int livello) 
		{
		double e=((((puntiBase+puntiIndividuali)*2*livello+1*livello)/100)+5);
		return (int) e;
		}
	
	//Supporto evoluzione (ps)
	private int formulapsup(int puntiBasePs, int puntiIndividualiPs,int livello) 
		{
		double e= (((puntiBasePs+puntiIndividualiPs)*2*livello+1*livello)/100)+livello+10;
		return (int) e;
		}
	
	/**
	 * Controlla se almeno una delle mosse del Pokemon ha ancora punti PP disponibili.
	 * 
	 * @param target Il Pokemon di cui verificare le mosse.
	 * @return true se almeno una mossa ha ancora PP disponibili, false altrimenti.
	 */
	public boolean checkMossePokemon(Pokemon target)
		{
		for (int i = 0; i < 4; i++)
			{
			if (target.getMossa(i) != null) 
				{
				if (target.getMossa(i).getPP() > 0) 
					{return true;}
				}
			}
		return false;
		}
	
	//----------------------------------------------------------------------
	// 							Metodi LG Personaggio
	//----------------------------------------------------------------------
	
	//getPersonaggi returna gli allenatori da giocare (settati da multigiocatore) (Chiamato dal run del main)
	public Allenatore[] getPersonaggi() 
	{
		return players; 
	}
	
	/**
	 * Imposta le impostazioni predefinite per un oggetto MyButton specificato.
	 *(Text resettato, Sfondo da input, IconaSpeciale resettata e Enable Off)
	 * 
	 * @param BottoneTarget Il bottone da defaultare.
	 * @param ImgDefault Il percorso dell'immagine di default da applicare al bottone.
	 */
	private void defaultBottone(MyButton BottoneTarget, String ImgDefault) 
	{	
		//default del bottone, ImgDefault è il disegno che ha il bottone di default
		BottoneTarget.setEnabled(false); 
		BottoneTarget.setText(""); 
		BottoneTarget.setSfondo(getImage(ImgDefault));
		BottoneTarget.setSpecialIcon(getImage(""), 0, 0);
	}
	
	/**
	 * Configura visivamente un bottone che rappresenta un Pokemon associato a un determinato allenatore.
	 * Se il Pokemon è presente nell'allenatore, imposta il nome del Pokemon e l'icona speciale nel bottone.
	 * Se il Pokemon è vivo (ossia ha punti vita diversi da zero), il bottone viene abilitato e il suo sfondo cambia.
	 * Aggiunge un listener per i movimenti del mouse che gestisce gli eventi di entrata nel bottone, uscita dal bottone,
	 * pressione del bottone e rilascio del bottone.
	 * Durante l'interazione con il bottone, vengono visualizzati feedback visivi sul cambio di stato del bottone stesso.
	 * 
	 * @param BottoneTarget Il bottone che rappresenta il Pokemon e che verrà configurato.
	 * @param AllenatoreTarget L'allenatore a cui appartiene il Pokemon.
	 */
	private void setBottonePokemon(MyButton BottoneTarget, Allenatore AllenatoreTarget) {
		
		//default del bottone, imposta il default per i pokemon in ""
		defaultBottone(BottoneTarget,"assets/Immagini/icona pokemon spento.png");
		
		//spostamento scritta più a destra 
		BottoneTarget.setX_YImg(-30, 0);
		
		
		//Se è qualcosa mettici l'immagine di cos'è, else lascia bottone spento
		if (!(AllenatoreTarget.getPokemonPosizione(BottoneTarget.getContenuto())== null)) 
			{
			
			//Mette il nome del pokemon e l'icona speciale
			BottoneTarget.setText(AllenatoreTarget.getPokemonPosizione(BottoneTarget.getContenuto()).getNome());
			BottoneTarget.setSpecialIcon(getImage(AllenatoreTarget.getPokemonPosizione(BottoneTarget.getContenuto()).getSpritePiccolo()), 0, 0);
			
			if (AllenatoreTarget.getPokemonPosizione(BottoneTarget.getContenuto()).getPs()!=0) 
				{
				//Se il pokemon è vivo accende il bottone e ne cambia lo sfondo
				BottoneTarget.setEnabled(true);
				BottoneTarget.setSfondo(getImage("assets/Immagini/icona pokemon.png"));
				}
			}
		BottoneTarget.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent evt)
            {
            	
            	BottoneTarget.addFocus(getImage("assets/Immagini/F19275.png")); 
                //System.out.println("SOPRA GRID");
                BottoneTarget.repaint();
            }
            public void mouseExited(MouseEvent evt)
            {
            	BottoneTarget.removeFocus();
            	//System.out.println("FUORI GRID");
            	BottoneTarget.repaint();
            }
            public void mousePressed(MouseEvent evt)
            {
            	if (BottoneTarget.isEnabled()){
            		BottoneTarget.setSfondo(getImage("assets/Immagini/icona pokemon Scuro.png"));
	            	//System.out.println("PREMUTO");
	            	BottoneTarget.repaint();
            	}
            }
            public void mouseReleased(MouseEvent evt)
            {
            	if (BottoneTarget.isEnabled()){
            		BottoneTarget.removeFocus();
            		BottoneTarget.setSfondo(getImage("assets/Immagini/icona pokemon.png"));
	            	//System.out.println("RILASCIATO");
	            	BottoneTarget.repaint();
            	}
            }
        });
		this.repaint();
		
	}
	
	/**
	 * Imposta le caratteristiche di un bottone rappresentante una mossa di un Pokemon,
	 * includendo il nome della mossa, l'immagine associata al tipo della mossa, e le
	 * azioni da eseguire durante l'interazione del mouse.
	 * <p>
	 * Se la mossa specificata per il bottone è nulla, il bottone rimane disabilitato
	 * e non mostra alcuna immagine o testo.
	 * <p>
	 * Durante l'interazione del mouse, il bottone cambia lo sfondo per rappresentare
	 * visivamente l'effetto di hover, click e rilascio, variando l'immagine di sfondo
	 * in base al tipo di mossa.
	 * 
	 * @param BottoneTarget Il bottone da configurare.
	 * @param Subject Il Pokemon di cui rappresentare una mossa.
	 * @param Focus Specifica se il bottone deve essere attivato anche se la mossa ha 0 PP.
	 */
	private void setBottoneMossa(MyButton BottoneTarget, Pokemon Subject, boolean Focus){
		
		//Defaulta a spento e senza nome, il "" deve diventare l'indirizzo dell'immagine default della mossa
		defaultBottone(BottoneTarget, "assets/Immagini/Mosse/0.png");
		BottoneTarget.setX_YImg(-5, 0);
		
		//Se è qualcosa mettici il bottone giusto in base al tipo
		if (!(Subject.getMossa(BottoneTarget.getContenuto())==null)) 
			{
			//Setta il nome della mossa
			BottoneTarget.setText(Subject.getMossa(BottoneTarget.getContenuto()).getNome());
			//Solo se è ha i pp il bottone si accende
			if (Subject.getMossa(BottoneTarget.getContenuto()).getPP()!=0 || Focus)
				{
				BottoneTarget.setEnabled(true);
				BottoneTarget.setSfondo(getImage("assets/Immagini/Mosse/"+Subject.getMossa(BottoneTarget.getContenuto()).getTipo()+".png" ));
				//Questo mouse listener a differenza di quello sopra ha l'ordine di mettere lo sfondo 
				//leggermente più scuro se clicchi il bottone (Varia a seconda del tipo mossa)
				BottoneTarget.addMouseListener(new MouseAdapter()
		        {
		            public void mouseEntered(MouseEvent evt)
		            {
		            	
		            	BottoneTarget.addFocus(getImage("assets/Immagini/F19275.png")); 
		                //System.out.println("SOPRA GRID ACCESA");
		                BottoneTarget.repaint();
		            }
		            public void mouseExited(MouseEvent evt)
		            {
		            	BottoneTarget.removeFocus();
		            	//System.out.println("FUORI GRID ACCESA");
		            	BottoneTarget.repaint();
		            }
		            public void mousePressed(MouseEvent evt)
		            {
		            	
		            	BottoneTarget.setSfondo(getImage("assets/Immagini/MosseScure/"+Subject.getMossa(BottoneTarget.getContenuto()).getTipo()+".png"));
		            	//System.out.println("PREMUTO GRID ACCESA");
		            	BottoneTarget.repaint();
		            	
		            }
		            public void mouseReleased(MouseEvent evt)
		            {
		            	if (!Focus) {
			            	BottoneTarget.removeFocus();
			            	}
		            	BottoneTarget.setSfondo(getImage("assets/Immagini/Mosse/"+Subject.getMossa(BottoneTarget.getContenuto()).getTipo()+".png"));
			            //System.out.println("RILASCIATO GRID ACCESA");
			            BottoneTarget.repaint();
		            }
		        });
				this.repaint();
				return;
				}
			}
		
		//Mouse listener con focus acceso ma click inutile
		BottoneTarget.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent evt)
            {
            	
            	BottoneTarget.addFocus(getImage("assets/Immagini/F19275.png")); 
                //System.out.println("SOPRA GRID");
                BottoneTarget.repaint();
            }
            public void mouseExited(MouseEvent evt)
            {
            	BottoneTarget.removeFocus();
            	//System.out.println("FUORI GRID");
            	BottoneTarget.repaint();
            }
            public void mousePressed(MouseEvent evt)
            {
            	
	           // System.out.println("PREMUTO GRID");
            	
            }
            public void mouseReleased(MouseEvent evt)
            {
            	BottoneTarget.removeFocus();
	           // System.out.println("RILASCIATO GRID");
            	
            }
        });
		this.repaint();
	}
	
	//----------------------------------------------------------------------
	// 							Metodi Scoreboard
	//----------------------------------------------------------------------
	
	/**
	 * Copia i primi 10 elementi dell'array degli allenatori fornito, ordinati in base al valore
	 * dell'attributo di statistiche specificato dal parametro "index", nell'array di uscita.
	 * <p>
	 * Se l'array degli allenatori contiene meno di 10 elementi, il metodo copia tutti gli elementi
	 * disponibili.
	 * 
	 * @param arrayAllenatori L'array di Allenatori da cui copiare gli elementi.
	 * @param index L'indice dell'attributo di statistiche da utilizzare per l'ordinamento.
	 * @param arrayUscita L'array di Allenatori in cui verranno copiati i primi 10 elementi ordinati.
	 */
	private void riempiArrayScoreboard( Allenatore[] arrayAllenatori,int index,Allenatore[] arrayUscita) 
	{
	Allenatore[] arrayAllenatoriCopia=arrayAllenatori.clone();
	//for (Allenatore allen:arrayAllenatori) {System.out.println(allen.getNome());}
	
	int numEsec=10;
	if (arrayAllenatori.length<10) {
		numEsec=arrayAllenatoriCopia.length;
	}
	for(int i= 0; i<numEsec; i++) {
		 int max=0;
		 for(int t= 0; t<arrayAllenatoriCopia.length; t++) {
			 if(arrayAllenatoriCopia[t]==null) {
				 continue;}
			 if(arrayAllenatoriCopia[max]==null) {
				 max=t;
				 continue;
			 }
			 if((arrayAllenatoriCopia[t].getStats()[index])>arrayAllenatoriCopia[max].getStats()[index]) {
				 max=t;}
		}
		 arrayUscita[i]=arrayAllenatoriCopia[max]; //alcuni elementi potrebbero essere null
		 arrayAllenatoriCopia[max]=null;
	}
		
		
	}
	
	/**
	 * Copia i primi 10 elementi dell'array degli allenatori fornito, ordinati in base alla somma dei
	 * valori degli attributi di statistiche specificati dagli indici "index1" e "index2", nell'array di uscita.
	 * (Usato nel codice per ottenere il numero di Partite giocate, essendo somma di vittorie e sconfitte)
	 * <p>
	 * Se l'array degli allenatori contiene meno di 10 elementi, il metodo copia tutti gli elementi
	 * disponibili.
	 * 
	 * @param arrayAllenatori L'array di Allenatori da cui copiare gli elementi.
	 * @param index1 Il primo indice dell'attributo di statistiche da utilizzare per l'ordinamento.
	 * @param index2 Il secondo indice dell'attributo di statistiche da utilizzare per l'ordinamento.
	 * @param arrayUscita L'array di Allenatori in cui verranno copiati i primi 10 elementi ordinati.
	 */
	private void riempiArrayPartiteScoreboard( Allenatore[] arrayAllenatori,int index1,int index2,Allenatore[] arrayUscita) 
		{
		Allenatore[] arrayAllenatoriCopia=arrayAllenatori.clone();
		
		int numEsec=10;
		if (arrayAllenatori.length<10) {
			numEsec=arrayAllenatoriCopia.length;
		}
		for(int i= 0; i<numEsec; i++) {
			 int max=0;
			 for(int t= 0; t<arrayAllenatoriCopia.length; t++) {
				 if(arrayAllenatoriCopia[t]==null) {
					 continue;}
				 if(arrayAllenatoriCopia[max]==null) {
					 max=t;
					 continue;
				 }
				 if((arrayAllenatoriCopia[t].getStats()[index1]+arrayAllenatoriCopia[t].getStats()[index2])>(arrayAllenatoriCopia[max].getStats()[index1]+arrayAllenatoriCopia[max].getStats()[index2])) {
					 max=t;}
			}
			 arrayUscita[i]=arrayAllenatoriCopia[max]; //alcuni elementi potrebbero essere null
			 arrayAllenatoriCopia[max]=null;
		}
			
			
		}
	
	/**
	 * Crea e restituisce un pannello JLayeredPane che rappresenta una scoreboard contenente le informazioni degli allenatori.
	 * Ogni riga della scoreboard include l'immagine dell'allenatore, il nome dell'allenatore, il numero di vittorie, 
	 * il numero di sconfitte, il totale delle partite disputate, il numero di Pokemon sconfitti e il numero di Pokemon persi.
	 * Le informazioni sono allineate verticalmente al centro all'interno di colonne distinte.
	 * 
	 * @param arrayAllenatori L'array di Allenatori da cui ottenere le informazioni da visualizzare nella scoreboard.
	 * @return Un pannello JLayeredPane contenente la scoreboard con le informazioni degli allenatori.
	 */
	private JLayeredPane creaScoreboardArray(Allenatore[] arrayAllenatori) 
	{
		JLayeredPane pannello = creaPanel(0,0, 580, 600);

		for(int i=0;i<arrayAllenatori.length;i++) {
			if((arrayAllenatori[i]==null)) {break;} 
	
			Color bianco=new Color(255,255,255);
	
			creaLabelImmagine(5, 60*i+5+6, 50, 50, arrayAllenatori[i].getImageIcon()[1], pannello);
			JLabel LabelNome= creaLabel(arrayAllenatori[i].getNome(),50, 60*i+6, 223, 60 ,pannello, StatFont,2,bianco);
			JLabel LabelVittorie= creaLabel(""+arrayAllenatori[i].getStats()[0],243+30, 60*i+6, 57, 60, pannello, StatFont,2,bianco);
			JLabel LabelSconfitte= creaLabel(""+arrayAllenatori[i].getStats()[1],300+30, 60*i+6, 57, 60, pannello, StatFont,2,bianco);
			JLabel LabelPartite= creaLabel(""+(arrayAllenatori[i].getStats()[0]+arrayAllenatori[i].getStats()[1]),357+30, 60*i+6, 57, 60, pannello, StatFont,2,bianco);
			JLabel LabelKills= creaLabel(""+arrayAllenatori[i].getStats()[2],414+30, 60*i+6, 57, 60, pannello, StatFont,2,bianco);
			JLabel LabelMorti= creaLabel(""+arrayAllenatori[i].getStats()[3],471+30, 60*i+6, 57, 60, pannello, StatFont,2,bianco);
	
			LabelNome.setHorizontalAlignment(JLabel.CENTER);
			LabelVittorie.setHorizontalAlignment(JLabel.CENTER);
			LabelSconfitte.setHorizontalAlignment(JLabel.CENTER);
			LabelPartite.setHorizontalAlignment(JLabel.CENTER);
			LabelKills.setHorizontalAlignment(JLabel.CENTER);
			LabelMorti.setHorizontalAlignment(JLabel.CENTER);

		/*
		JLayeredPane LabelNomeX= creaPanel(50, 60*i, 223, 60);
		JLayeredPane LabelVittorieX= creaPanel(243+30, 60*i, 57, 60);
		JLayeredPane LabelSconfitteX= creaPanel(300+30, 60*i, 57, 60);
		JLayeredPane LabelPartiteX= creaPanel(357+30, 60*i, 57, 60);
		JLayeredPane LabelKillsX= creaPanel(414+30, 60*i, 57, 60); 
		JLayeredPane LabelMortiX= creaPanel(471+30, 60*i, 57, 60);

		LabelNomeX.setBackground(Color.GREEN);
		LabelVittorieX.setBackground(Color.RED);
		LabelSconfitteX.setBackground(Color.WHITE);
		LabelPartiteX.setBackground(Color.BLACK);
		LabelKillsX.setBackground(Color.BLUE);
		LabelMortiX.setBackground(Color.YELLOW);

		pannello.add(LabelNomeX,Integer.valueOf(0));
		pannello.add(LabelVittorieX,Integer.valueOf(0));
		pannello.add(LabelSconfitteX,Integer.valueOf(0));
		pannello.add(LabelKillsX,Integer.valueOf(0));
		pannello.add(LabelMortiX,Integer.valueOf(0));
		pannello.add(LabelPartiteX,Integer.valueOf(0));
		*/

		}
		return pannello;
	}

	
	//----------------------------------------------------------------------
	// 							Metodi Fight
	//----------------------------------------------------------------------
	
	/**
	 * Cambia il Pokemon per un determinato giocatore e aggiorna di conseguenza lo stato del gioco.
	 * <p>
	 * Questo metodo esegue diversi controlli e aggiorna la musica di sottofondo e l'interfaccia utente in base
	 * allo stato di salute del Pokemon che viene aggiornato e del Pokemon dell'altro giocatore. Se la salute 
	 * del Pokemon che viene aggiornato è inferiore o uguale al 25%, attiva la musica di panico a meno che non 
	 * sia già in esecuzione. Se la salute del Pokemon dell'altro giocatore è superiore al 25% e la musica di 
	 * panico è in esecuzione, interrompe la musica di panico e torna alla musica di combattimento.
	 * </p>
	 * 
	 * @param Giocatore il numero del giocatore (0 o 1) il cui Pokemon viene cambiato
	 * @param PokemonDaAggiornare l'oggetto Pokemon che deve essere aggiornato
	 */
	public void changePokemon(int Giocatore, Pokemon PokemonDaAggiornare) {
		
		boolean PrimoCheck = ((double)PokemonDaAggiornare.getPs()/(double)PokemonDaAggiornare.getPsMax()*100  <= 25.0);
		if (PrimoCheck && !s.checkWav("Panico")) 
			{
			s.stop();
			s.start("Panico", -1);
			}
		
		
		double Secondo_Check;
		if (Giocatore == 0)
			{Secondo_Check = ((double)Francesco.getPokemonGiocatore2().getPs()/(double)Francesco.getPokemonGiocatore2().getPsMax())*100;}
		else
			{Secondo_Check = ((double)Francesco.getPokemonGiocatore1().getPs()/(double)Francesco.getPokemonGiocatore1().getPsMax())*100;}
		
		
		//Fa in modo che la musica di panico se attiva si spenga se il pokemon morto era l'unico ad avere pochi hp
	    if (Secondo_Check>25.0 && s.checkWav("Panico") && !PrimoCheck)
			{
			s.stop();
			s.start("Fight",-1);
			}

		s.quickShoot("CAMBIO");
		updateLabelHighFight(PokemonDaAggiornare,Giocatore);
		this.repaint();
	}
	
	/**
	 * Questo metodo aggiorna la GUI per visualizzare l'azione compiuta durante il turno di combattimento.
	 * Dopo che l'azione è stata eseguita nella classe Partita, questo metodo si occupa di aggiornare
	 * la GUI per mostrare dettagli come l'attacco utilizzato, i danni inflitti, l'efficacia dell'attacco,
	 * i cambiamenti di vita dei Pokemon, e l'applicazione di buff o debuff.
	 * <p>
	 * Il metodo prende in input vari parametri per descrivere l'azione compiuta e i suoi effetti:
	 * <ul>
	 *     <li>{@code vitaInizialeDIFENSORE}: Vita iniziale del Pokemon difensore prima dell'azione.</li>
	 *     <li>{@code vitaInizialeATTACCANTE}: Vita iniziale del Pokemon attaccante prima dell'azione.</li>
	 *     <li>{@code tipo_mossa}: Il tipo di mossa utilizzata.</li>
	 *     <li>{@code efficaciaMossa}: L'efficacia della mossa rispetto al Pokemon difensore.</li>
	 *     <li>{@code attaccante}: Il Pokemon attaccante che ha eseguito l'azione.</li>
	 *     <li>{@code difensore}: Il Pokemon difensore che ha subito l'azione.</li>
	 *     <li>{@code GiocatoreAttaccante}: Indice del giocatore che controlla il Pokemon attaccante (0 per giocatore 1, 1 per giocatore 2).</li>
	 *     <li>{@code buffself}: Flag che indica se sono stati applicati buff al Pokemon attaccante.</li>
	 *     <li>{@code debuffself}: Flag che indica se sono stati applicati debuff al Pokemon attaccante.</li>
	 *     <li>{@code buffenemy}: Flag che indica se sono stati applicati buff al Pokemon difensore.</li>
	 *     <li>{@code debuffenemy}: Flag che indica se sono stati applicati debuff al Pokemon difensore.</li>
	 *     <li>{@code mossausata}: La mossa utilizzata durante l'azione.</li>
	 *     <li>{@code bruttocolpo}: Flag che indica se l'azione è stato un colpo critico.</li>
	 * </ul>
	 * <p>
	 * 
	 * Il metodo gestisce l'aggiornamento della GUI per mostrare l'azione compiuta, applicare gli effetti visivi
	 * per buff e debuff sui Pokemon, eseguire l'animazione del movimento del Pokemon e aggiornare la visualizzazione
	 * delle PS. Infine, comunica alla Partita di interrompere lo standby dopo un secondo.
	 * <p>
	 * @param vitaInizialeDIFENSORE Vita iniziale del Pokemon difensore prima dell'azione.
	 * @param vitaInizialeATTACCANTE Vita iniziale del Pokemon attaccante prima dell'azione.
	 * @param tipo_mossa Il tipo di mossa utilizzata.
	 * @param efficaciaMossa L'efficacia della mossa rispetto al Pokemon difensore.
	 * @param attaccante Il Pokemon attaccante che ha eseguito l'azione.
	 * @param difensore Il Pokemon difensore che ha subito l'azione.
	 * @param GiocatoreAttaccante Indice del giocatore che controlla il Pokemon attaccante (0 per giocatore 1, 1 per giocatore 2).
	 * @param buffself Flag che indica se sono stati applicati buff al Pokemon attaccante.
	 * @param debuffself Flag che indica se sono stati applicati debuff al Pokemon attaccante.
	 * @param buffenemy Flag che indica se sono stati applicati buff al Pokemon difensore.
	 * @param debuffenemy Flag che indica se sono stati applicati debuff al Pokemon difensore.
	 * @param mossausata La mossa utilizzata durante l'azione.
	 * @param bruttocolpo Flag che indica se l'azione è stato un colpo critico.
	 */         				
	public void displayAzione( int vitaInizialeDIFENSORE, // VITA INIZIALE DIFENSORE,
							   int vitaInizialeATTACCANTE,// VITA INIZIALE ATTACCANTE,
							   int tipo_mossa,            // IL TIPO,
							   double efficaciaMossa,     // EFFICACIA,
							   Pokemon attaccante ,       // CHI SONO I PROTAGONISTI,
							   Pokemon difensore,         //
							   int GiocatoreAttaccante,   // IL GIOCATORE,
							   int buffself,              // SE SONO STATI APPLICATI BUFF O DEBUFF,
							   int debuffself,            //
							   int buffenemy,             //
							   int debuffenemy,
							   Mossa mossausata,
							   int bruttocolpo
							   )          
		{
		
		DisplayTXT.setFont(StatFont);	
		DisplayTXT.setText(""+attaccante.getNome()+" usa "+mossausata.getNome()+"!");
		
		try {Thread.sleep(700);} 
		catch (InterruptedException e) {e.printStackTrace();}	
		
		//i range sono messi per evitare errori di display 
		String efficacia = ""; 
		if (efficaciaMossa >= 0.0 && efficaciaMossa < 0.25)      {efficacia = "Il difensore è Immune! ";}
		else if (efficaciaMossa >= 0.25 && efficaciaMossa < 0.5) {efficacia = "L'attacco è poco efficace! ";}
		else if (efficaciaMossa >= 0.5 && efficaciaMossa < 1.0)  {efficacia = "L'attacco è poco efficace! ";}
		else if (efficaciaMossa >= 1.0 && efficaciaMossa < 2.0)  {efficacia = "L'attacco colpisce! ";}
		else if (efficaciaMossa >= 2.0)                          {efficacia = "L'attacco è Super Efficace! ";}
		
		String critico = "";
		if (bruttocolpo==2 && efficaciaMossa!= 0 && vitaInizialeDIFENSORE!=difensore.getPs()) {critico = " (CRITICO!)";}
		
		DisplayTXT.setText(efficacia+difensore.getNome()+" subisce "+(vitaInizialeDIFENSORE-difensore.getPs())+ " danni!"+critico);
		
		//Appaiono i buff e i debuff
        if (buffself!=0) {
        	updateBuffDebuffPokemon(GiocatoreAttaccante,true);
			}
	    if (debuffself!=0) {
	    	updateBuffDebuffPokemon(GiocatoreAttaccante,false);
			}
	 
	    int temp = 0; if (GiocatoreAttaccante == 0) {temp = 1;}
	 
	    if (buffenemy!=0) {
	    	updateBuffDebuffPokemon(temp,true);
			}	
	    if (debuffenemy!=0) {
			updateBuffDebuffPokemon(temp,false);
			}
		
	    
	    //Movimento del pokemon e decremento della vita
	    if ( vitaInizialeDIFENSORE!=difensore.getPs() || vitaInizialeATTACCANTE!=attaccante.getPs()) {
			
	    	updateMovePokemon(GiocatoreAttaccante);
			updateDisplayPS(vitaInizialeDIFENSORE,vitaInizialeATTACCANTE,GiocatoreAttaccante,difensore,attaccante);
			
	    }

		//Dice dopo tot secondi alla partita di fermare lo standby
		stopStandby(1);
		
		//Francesco.stopStandby();
		}
	
	/**
	 * Aggiorna l'animazione del movimento del Pokemon nel pannello di combattimento principale.
	 * Muove visivamente il Pokemon da una posizione all'altra nel pannello di combattimento.
	 * <p>
	 * Utilizza un ciclo di animazione per spostare il Pokemon a partire da una posizione iniziale fino a una
	 * posizione finale determinata. Il movimento avviene con un ritardo configurabile tra ogni frame dell'animazione.
	 * <p>
	 * @param Giocatore L'indice del giocatore (0 per giocatore 1, 1 per giocatore 2) del Pokemon da animare.
	 */
	private void updateMovePokemon(int Giocatore)
		{
		//<>
		int Delay = 3;
		int decX = 5;
		int decY = 2;
		int volumeColpito = 10;

		
		switch(Giocatore) 
			{
		case 0: 
			//labelIMJPK1 = creaLabel("" , 90, 140, 192, 192, panHighFight, GeneralFont);
			
			int x = 90;
			int y = 140;
			for (int i = 0; i < 55; i++)
				{
				
				try {Thread.sleep(Delay);} 
				catch (InterruptedException e) {e.printStackTrace();}
				
				x+=decX;
				if(y>30)
				{y-=decY;}
					
				labelIMJPK1.setBounds(x, y, 192, 192);
				this.repaint();
				}
			s.quickShoot("Colpito",volumeColpito);
			for (int i = 0; i < 55; i++)
				{
				
				try {Thread.sleep(Delay);} 
				catch (InterruptedException e) {e.printStackTrace();}
				
				x-=decX;
				if(y<140)
				{y+=decY;}
				
				labelIMJPK1.setBounds(x, y, 192, 192);
				this.repaint();
				}
			labelIMJPK1.setBounds(90, 140, 192, 192);
			this.repaint();
			
			break;
			
		case 1: 
			int x2 = 405; //arriva a 262
			int y2 = 30;
			//labelIMJPK2 = creaLabel("" ,405, 30, 192, 192, panHighFight, GeneralFont);
			for (int i = 0; i < 55; i++)
				{
				
				try {Thread.sleep(Delay);} 
				catch (InterruptedException e) {e.printStackTrace();}
				
				x2-=decX;
				if(y2<140)
				{y2+=decY;}
				
				labelIMJPK2.setBounds(x2, y2, 192, 192);
				this.repaint();
				}
			s.quickShoot("Colpito",volumeColpito);
			for (int i = 0; i < 55; i++)
				{
				
				try {Thread.sleep(Delay);} 
				catch (InterruptedException e) {e.printStackTrace();}
				
				x2+=decX;
				if(y2>30)
				{y2-=decY;}
				
				labelIMJPK2.setBounds(x2, y2, 192, 192);
				this.repaint();
				}
			labelIMJPK2.setBounds(405, 30, 192, 192);
			this.repaint();
			break;
			}
		
		}
	
	/**
	 * Visualizza un effetto visivo di buff o debuff su un Pokemon in una schermata invisibile di supporto.
	 * L'effetto visivo si muove verticalmente sulla schermata per un periodo di tempo specificato.
	 * <p>
	 * Se l'incremento è negativo, l'effetto parte dal basso verso l'alto; altrimenti, parte dall'alto verso il basso.
	 * 
	 * @param pannelloinvisibilesupporto Il pannello invisibile di supporto su cui visualizzare l'effetto.
	 * @param incremento L'incremento verticale dell'effetto visivo (positivo o negativo).
	 * @param dimypannellosupporto La dimensione verticale del pannello di supporto.
	 * @param mssecondi Il numero di millisecondi per cui l'effetto visivo sarà attivo.
	 * @param img Il percorso dell'immagine da utilizzare per l'effetto visivo.
	 */
	private void buffPokemon(JLayeredPane pannelloinvisibilesupporto, int incremento, int dimypannellosupporto, int mssecondi, String img) 
		{
		int x = -dimypannellosupporto;
		String sound = "DEBUFF";
		//<>
		if (incremento<0)
			{
			x = 0;
			sound = "BUFF";
			}
		int sos = x;
	
		s.quickShoot(sound);
		JLabel immaginez = creaLabelImmagine(0, x, dimypannellosupporto, dimypannellosupporto*2, img);
		pannelloinvisibilesupporto.add(immaginez,Integer.valueOf(5));
		
		while(mssecondi!=0) 
			{
			
			try {
				Thread.sleep(1);
			} catch (Exception e) {
				
			}
			
			sos+=incremento;
			immaginez.setBounds(0, sos, dimypannellosupporto, dimypannellosupporto*2);
			if (incremento<0 && sos==-dimypannellosupporto) 
				{
				sos = x;
				}
			
			if (incremento>0 && sos==0) 
				{
				sos = x;
				}
			this.repaint();
			mssecondi -= 1;
			}
		
		}
	
	/**
	 * Aggiorna l'effetto visivo di buff o debuff su un Pokemon nel pannello di combattimento principale.
	 * Mostra un'animazione visiva e audio per indicare l'attivazione di un buff o debuff.
	 * <p>
	 * Chiamando il metodo buffPokemon(JLayeredPane, int, int, int, String), mostra l'effetto visivo sulla posizione
	 * del Pokemon nel pannello di combattimento.
	 * <p>
	 * @param Giocatore L'indice del giocatore (0 per giocatore 1, 1 per giocatore 2) del Pokemon che subisce l'effetto.
	 * @param Buff True se l'effetto è un buff, False se è un debuff.
	 */
	public void updateBuffDebuffPokemon(int Giocatore, boolean Buff) 
		{
		//System.out.println("***Rumori di buff***");

		int mssecondieffetto = 800;
		int dimpokemon = 192;
		                                    //Effetto se debuff
		int x = 90; int y = 140; int s = 1; String effettoimmagine = "assets/Immagini/DEBUFF.png";
		if (Giocatore == 1)
			{
			x=405;
			y=30;
			}
		if (Buff) 
			{
			//Effetto se buff
			effettoimmagine = "assets/Immagini/BUFF.png";
			s *= -1;
			}
		
		
		JLayeredPane pannelloinvisibilesupporto= creaPanel(x, y, dimpokemon, dimpokemon); pannelloinvisibilesupporto.setOpaque(false);
		panHighFight.add(pannelloinvisibilesupporto, Integer.valueOf(5));
		
		buffPokemon(pannelloinvisibilesupporto,  s,  dimpokemon, mssecondieffetto, effettoimmagine);
		
		panHighFight.remove(pannelloinvisibilesupporto);
		this.repaint();
	
		}
	
	/**
	 * Mostra una schermata interattiva per l'apprendimento di una nuova mossa da parte di un Pokemon.
	 * Se il Pokemon ha uno slot libero per una nuova mossa, la aggiunge automaticamente senza richiedere ulteriori azioni.
	 * Altrimenti, mostra al giocatore le opzioni di mosse attuali del Pokemon e la nuova mossa da apprendere,
	 * permettendo di scegliere quale mossa sostituire.
	 * <p>
	 * Utilizza il metodo stopStandbySecondo(int) di Partita per gestire l'interruzione dello standby
	 * dopo l'apprendimento di una nuova mossa.
	 * 
	 * @param ApprenditoreMossa Il Pokemon che apprende la nuova mossa.
	 * @param Proprietario L'indice del giocatore (0 per giocatore 1, 1 per giocatore 2) che possiede il Pokemon.
	 * @param laMossaAppresa La nuova mossa che il Pokemon sta apprendendo.
	 */
	public void displayNuovaMossa(Pokemon ApprenditoreMossa, int Proprietario, Mossa laMossaAppresa) 
		{
		
		//In caso ci sia uno slot libero non serve sostituire una mossa esistente, quindi fa in auto
		for (int g = 0; g < 4 ; g++) 
			{
			if (ApprenditoreMossa.getMossa(g) == null) 	
				{
				DisplayTXT.setFont(new Font("Bahnschrift SemiBold",Font.BOLD, 15 ));
				System.out.println("Mossa per "+ApprenditoreMossa.getNome()+" di "+players[Proprietario].getNome()+ " scelta Automaticamente ("+laMossaAppresa.getNome()+"), Proseguo (Gui)");
				DisplayTXT.setText(ApprenditoreMossa.getNome()+" di "+players[Proprietario].getNome()+" ha appreso una nuova mossa! ("+laMossaAppresa.getNome()+")");
				ApprenditoreMossa.setMossa(laMossaAppresa, g);
				stopStandbySecondo(1);
				return;
				}
			}
		
		String pathsfondo = "assets/Immagini/schermata stat mosse apprendimento.png";
		
		//Sfondo che varia in base al proprietario del pokemon, VANNO SETTATE LE IMMAGINI *************************
		if (Proprietario == 0) {pathsfondo = "assets/Immagini/schermata stat mosse apprendimentoBlu.png";}
		
		
		DisplayTXT.setFont(new Font("Bahnschrift SemiBold",Font.BOLD, 13 ));
		DisplayTXT.setText("Il Pokemon "+ApprenditoreMossa.getNome()+" di "+players[Proprietario].getNome()+" può apprendere una mossa! ("+laMossaAppresa.getNome()+")");

		indexmossa = 4;
		//VERSIONE VARIATA DI SYSFULLMOSSA, TASTO DI CONFERMA IN ACTION LISTENER DELLE MOSSE
		
		//Parte legata alle Mosse la roba indentata è roba sua
		JLayeredPane POKEMONMOSSE = creaPanel(0, 400, 700, 400);
		JLabel background = creaLabelImmagine(0, 0, 700, 400, "assets/Immagini/background apprendi mosse.png");
		POKEMONMOSSE.add(background,Integer.valueOf(0));		
		
		int AsseXTutto = 5;
		Color bianco=new Color(255,255,255);
		
		
		//Pannello contenente solo label, aggiornato dalla griglia
		JLayeredPane DISPLAYSTATMOSSA = creaPanel(250+AsseXTutto, 15, 411, 254);
		
		
		JLabel schermata = creaLabelImmagine(0, 0, 411, 254, pathsfondo);
		
		DISPLAYSTATMOSSA.add(schermata,Integer.valueOf(0));		
		POKEMONMOSSE.add(DISPLAYSTATMOSSA,Integer.valueOf(2));
			
		//I vari label di display
		JLabel LabelNome = creaLabel("Nome: ", 20, 40, 645, 40, DISPLAYSTATMOSSA,  StatFont,2,bianco);
		JLabel LabelPotenza= creaLabel("Potenza: ", 20, 80, 645, 40, DISPLAYSTATMOSSA,  StatFont,2,bianco);
		JLabel LabelPrecisione = creaLabel("Precisione: ", 20, 120, 645, 40, DISPLAYSTATMOSSA,  StatFont,2,bianco);
		JLabel LabelPP = creaLabel("PPMax: ", 20, 160, 645, 40, DISPLAYSTATMOSSA,  StatFont,2,bianco);
		JLabel LabelTipo = creaLabel("Tipo: ", 200, 40, 645, 40, DISPLAYSTATMOSSA,  StatFont,2,bianco);
		JLabel LabelDescrizione = creaLabel("Descrizione: ", 200, 70, 190, 200, DISPLAYSTATMOSSA,  StatFont,2,bianco);
				
				
		//Creo le 4 MOSSE, SE cliccate ALLORA cambiano label in DISPLAYSTATMOSSA
		MyButton[] GridMosse = getButtonLine(20, 15, 192, 75, 4, POKEMONMOSSE); 
		for (MyButton myButtonZ : GridMosse) 
			{
			//setto il bottone così che displayi le cose giuste
			setBottoneMossa(myButtonZ, ApprenditoreMossa, true);
			//Il set bottone spegne di base tutte le mosse con 0 pp, anche nella schermata di cambio mossa quindi risulterebbe spenta una mossa con 0 pp, per evitare faccio un check per vedere se la mossa esiste, e la accendo
			myButtonZ.addActionListener(d -> 
				{
					s.quickShoot("ButtonEffect");
					DisplayTXT.setFont(new Font("Bahnschrift SemiBold",Font.BOLD, 15 ));
					DisplayTXT.setText("Premi Conferma se vuoi rimpiazzare "+ApprenditoreMossa.getMossa(myButtonZ.getContenuto()).getNome()+" con "+laMossaAppresa.getNome()+"!");

					//Set dei vari display
					LabelNome.setText("Nome: "+ApprenditoreMossa.getMossa(myButtonZ.getContenuto()).getNome());
					LabelPotenza.setText("Potenza: "+ApprenditoreMossa.getMossa(myButtonZ.getContenuto()).getPotenza());
					LabelPrecisione.setText("Precisione: "+ApprenditoreMossa.getMossa(myButtonZ.getContenuto()).getPrecisione());
					LabelPP.setText("PPMax: "+ApprenditoreMossa.getMossa(myButtonZ.getContenuto()).getPPMax());
					String tipoM;
					if (ApprenditoreMossa.getMossa(myButtonZ.getContenuto()).getTipo()!=0) 
						{
						tipoM=Tipi.values()[ApprenditoreMossa.getMossa(myButtonZ.getContenuto()).getTipo()-1].toString();
						}
					else 
						{
						tipoM="";
						}
					LabelTipo.setText("Tipo: "+tipoM);

					LabelDescrizione.setText("<html>Descrizione: "+ApprenditoreMossa.getMossa(myButtonZ.getContenuto()).getDescrizione()+"</html>");
					
					//Se premi il bottone, lui sarà quello che andrà ucciso
					indexmossa = myButtonZ.getContenuto();
					//....
					this.repaint();
				});
			}
		
		//Bottone contenente la nuova mossa
		MyButton BottoneNuovaMossa = creaBottone(laMossaAppresa.getNome(), 320+AsseXTutto-50, 285, 192, 75, POKEMONMOSSE);
		BottoneNuovaMossa.setContenuto(4);
		BottoneNuovaMossa.setSfondo(getImage("assets/Immagini/Mosse/"+laMossaAppresa.getTipo()+".png"));
		//Non avendo voglia di metterlo manualmente uso il metodo del remove, che per funzionare necessita di qualcosa da rimuovere
		BottoneNuovaMossa.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
			}
		});
		removeMouseListener(BottoneNuovaMossa, "assets/Immagini/Mosse/"+laMossaAppresa.getTipo()+".png", "assets/Immagini/MosseScure/"+laMossaAppresa.getTipo()+".png", "assets/Immagini/F19275.png");
		BottoneNuovaMossa.addActionListener(d -> 
		{
			s.quickShoot("ButtonEffect");
			//Set dei vari display
			DisplayTXT.setFont(new Font("Bahnschrift SemiBold",Font.BOLD, 15 ));
			DisplayTXT.setText("Premi Conferma se non vuoi apprendere "+laMossaAppresa.getNome()+"!");

			LabelNome.setText("Nome: "+laMossaAppresa.getNome());
			LabelPotenza.setText("Potenza: "+laMossaAppresa.getPotenza());
			LabelPrecisione.setText("Precisione: "+laMossaAppresa.getPrecisione());
			LabelPP.setText("PP: "+laMossaAppresa.getPP());
			String tipoM;
			if (laMossaAppresa.getTipo()!=0) 
				{
				tipoM=Tipi.values()[laMossaAppresa.getTipo()-1].toString();
				}
			else 
				{
				tipoM="";
				}
			LabelTipo.setText("Tipo: "+tipoM);
			LabelDescrizione.setText("<html>Descrizione: "+laMossaAppresa.getDescrizione()+"</html>");
			
			//Se premi il bottone, lui sarà quello che andrà ucciso
			indexmossa = BottoneNuovaMossa.getContenuto();
			//....
			this.repaint();
		});
			
		MyButton BottoneConferma =  creaBottone("Confirm", 575+AsseXTutto-30, 285, 75, 75, POKEMONMOSSE, "assets/Immagini/CONFERMA nuovamossa.png", "assets/Immagini/CONFERMA nuovamossaScuro.png", "assets/Immagini/F7575.png");
		BottoneConferma.addActionListener(d -> 
		{
			s.quickShoot("ButtonEffect");
			//Il reset avviene ad ogni avvio del metodo
			if (indexmossa != 4) {ApprenditoreMossa.setMossa(laMossaAppresa, indexmossa);}
			stop = false;
			this.remove(POKEMONMOSSE);
			this.add(panLowCombatWAIT);
			this.repaint();
		});
		
		
		///////////////////////////////////////
		
		this.remove(panLowCombatWAIT);
		this.add(POKEMONMOSSE);
		this.repaint();
		
		stop = true;
		while(stop) 
			{
			try {
				Thread.sleep(500);
				} 
			catch (InterruptedException e) {
				e.printStackTrace();
				}
			}
		
		System.out.println("Mossa per NuovaMossa Scelta, Proseguo (Gui)");
		DisplayTXT.setText("");
		DisplayTXT.setFont(StatFont);
		stopStandbySecondo(1);
		//Francesco.stopStandbySecondo();
		}
	
	/**
	 * Gestisce il cambio forzato di Pokemon dopo che un Pokemon è stato sconfitto (PokeDead).
	 * Questo metodo interrompe la musica di panico se il Pokemon morto era l'unico con HP bassi.
	 * Visualizza un messaggio di notifica sul display e consente al giocatore di scegliere un nuovo Pokemon
	 * dalla propria squadra mediante una schermata interattiva.
	 * <p>
	 * Questo metodo crea una schermata di selezione del Pokemon sostitutivo per il giocatore.
	 * Ogni opzione di Pokemon è rappresentata come un bottone interattivo che permette di visualizzare
	 * statistiche dettagliate e mosse disponibili del Pokemon selezionato, oltre a confermare il cambio.
	 * <p>
	 * Utilizza il metodo switchPokemonG1/2(int)} di partita 
	 * per effettuare il cambio del Pokemon per il giocatore 1 o giocatore 2 rispettivamente.
	 * <p>
	 * Utilizza anche il metodo stopStandby(int) per gestire l'interruzione dello standby
	 * dopo il cambio di Pokemon.
	 * <p>
	 * Viene chiamato da pokedead(Pokemon,Pokemon,int) di Partita per gestire la sconfitta del Pokemon durante
	 * il gioco e avviare il processo di cambio forzato.
	 * 
	 * @param GiocatoreCambioForzato Indica il giocatore che deve cambiare Pokemon (0 per Giocatore 1, 1 per Giocatore 2).
	 */
	public void displayCambioPokemon(int GiocatoreCambioForzato) 
			{
			s.quickShoot("morte");
			JLayeredPane panLowCombatSwitch1 = creaPanel(0, 400, 700, 400);
			
			double Secondo_Check;
			if (GiocatoreCambioForzato == 0)
				{Secondo_Check = ((double)Francesco.getPokemonGiocatore2().getPs()/(double)Francesco.getPokemonGiocatore2().getPsMax())*100;
				creaLabelImmagine(0, 0, 686, 400, "assets/Immagini/background scelta pokemon g1.png", panLowCombatSwitch1);}
			else
				{Secondo_Check = ((double)Francesco.getPokemonGiocatore1().getPs()/(double)Francesco.getPokemonGiocatore1().getPsMax())*100;
				creaLabelImmagine(0, 0, 686, 400, "assets/Immagini/background scelta pokemon g2.png", panLowCombatSwitch1);}
			
			
			//Fa in modo che la musica di panico se attiva si spenga se il pokemon morto era l'unico ad avere pochi hp
		    if (Secondo_Check>25.0 && s.checkWav("Panico") )
				{
				s.stop();
				s.start("Fight",-1);
				}
			
		    DisplayTXT.setFont(StatFont);
			DisplayTXT.setText("Il Pokemon di "+players[GiocatoreCambioForzato].getNome()+" è morto! Devi cambiare Pokemon!");

			
			//Gruppo di 6 Bottoni corrispondenti ai Pokemon
			int x0 = 100; int x = 192;
			int y0 = 40; int y = 75;
			MyButton[] GruppoPokemonz = getButtonGrid(x0, y0, x, y, 6, panLowCombatSwitch1);
			
			
			//Per ogni bottone della griglia che mostra i pokemon disponibili del giocatore, colora il bottone e dagli funzionalità
			for (MyButton myButton : GruppoPokemonz) 
				{
				setBottonePokemon(myButton,players[GiocatoreCambioForzato]);
				myButton.addActionListener(e -> 
					{
					s.quickShoot("ButtonEffect");
					//Schermata Conferma Cambio
					JLayeredPane CambioPokemon = creaPanel(0, 400, 700, 400);
					CambioPokemon.setBackground(new Color(60,150,180));
					
					if (GiocatoreCambioForzato == 0)
						{creaLabelImmagine(0, 0, 686, 400, "assets/Immagini/background scelta pokemon g1.png", CambioPokemon);}
					else
						{creaLabelImmagine(0, 0, 686, 400, "assets/Immagini/background scelta pokemon g2.png", CambioPokemon);}
					
					//Label + Bottone Conferma displayano il pokemon selezionato x il cambio
					//                                QUESTE COORDINATE SONO QUASI LE STESSE DATE A CONFERMA        
					
					JLabel microlabel = creaLabel("CONFERMA CAMBIO CON:",  165, -15, 450, 200,  CambioPokemon, GeneralFont,  4);
					microlabel.setForeground(Color.WHITE);
					JLabel microlabel2 = creaLabel(players[GiocatoreCambioForzato].getPokemonPosizione(myButton.getContenuto()).getNome(),  165, 50, 450, 200, CambioPokemon,GeneralFont, 4);
					microlabel2.setForeground(Color.WHITE);
					
					//Prende il pokemon, il suo nome e la sua immagine, mette come immagine nel bottone e setta il bottone
					Pokemon pokemondesignato = players[GiocatoreCambioForzato].getPokemonPosizione(myButton.getContenuto());
					MyButton Conferma = creaBottone("", 125, 50, 450, 200, CambioPokemon, "assets/Immagini/schermataCambiopokemon.png", "assets/Immagini/schermataCambiopokemonScuro.png", "assets/Immagini/F450200.png"); //serve qui
					Conferma.setSpecialIcon(getImage(pokemondesignato.getSpriteFronte()), 250, 20);
					Conferma.addActionListener(f -> 
						{
						s.quickShoot("ButtonEffect");
						
						//segna che il giocatore ha cambiato pokemon
						if(GiocatoreCambioForzato == 0) {Francesco.switchPokemonG1(myButton.getContenuto()); changePokemon(0, pokemondesignato);}
						else {Francesco.switchPokemonG2(myButton.getContenuto());  changePokemon(1, pokemondesignato);}
						
						//Rimuove pannello e torna a scelta per nuovo turno
						
						stop = false;
						
						this.remove(CambioPokemon);
						this.add(panLowCombatWAIT);
						this.repaint();
						});
					
					//Back to cambio pokemon
					MyButton BackToLowCombatSwitch = creaBottone("", 20, 383-20-50, 50, 50 , CambioPokemon, "assets/Immagini/Pokeback.png", "assets/Immagini/PokebackScura.png", "assets/Immagini/F5050.png");
					BackToLowCombatSwitch.addActionListener(j -> 
						{
						s.quickShoot("ButtonEffect");
						this.remove(CambioPokemon);
						this.add(panLowCombatSwitch1);
						this.repaint();
						});
					
					//SUPER COMANDO, crea l'intero sistema di stat del pokemon, displayato dal bottone BottoneStats (observer?)
					MyButton BottoneStats = creaBottone("STATS", 683-192-20-192-20, 383-20-75, 192, 75, CambioPokemon, "assets/Immagini/BottoneMainMenuV2.png", "assets/Immagini/MosseScure/TestScuro.png", "assets/Immagini/F19275.png");
					getStatFullSystem(pokemondesignato, CambioPokemon, BottoneStats, 0, 400, 700, 400);
					
					//SUPER COMANDO, crea l'intero sistema di mosse del pokemon, displayato dal bottone BottoneStats (observer?)
					MyButton BottoneMosse = creaBottone("MOSSE", 683-192-20, 383-20-75, 192, 75, CambioPokemon, "assets/Immagini/BottoneMainMenuV2.png", "assets/Immagini/MosseScure/TestScuro.png", "assets/Immagini/F19275.png");
					getMossaFullSystem(x0, y0+20, x,  y, pokemondesignato,  CambioPokemon, BottoneMosse);
					
					
					//Cosa fa il bottone originario (tutto questo è uno switch case che porta alla schermata di CambioPokemon)
					this.remove(panLowCombatSwitch1);
					this.add(CambioPokemon);
					this.repaint();
					});
				}


			
			this.remove(panLowCombatWAIT);
			this.add(panLowCombatSwitch1);	
			this.repaint();
			
			stop = true;
			while(stop) 
				{
				try {
					Thread.sleep(500);
					} 
				catch (InterruptedException e) {
					e.printStackTrace();
					}
				}
			System.out.println("Pokemon per Pokedead Scelto, Proseguo (Gui)");
			stopStandby(1);
			//Francesco.stopStandby();
			}
	
	/**
	 * Rimuove il pannello di attesa e mostra il pannello del turno di combattimento.
	 * Questo metodo è chiamato alla fine del turno.
	 */
	public void displaybreakWait() 
	{
		creaLowCombatTurno();
		this.remove(panLowCombatWAIT);
		this.add(panLowCombatTurno);
		this.repaint();
	}
	
	/**
	 * Visualizza un messaggio indicando che la mossa del giocatore ha mancato il bersaglio.
	 *
	 * @param Giocatore L'indice del giocatore di cui la mossa ha mancato il bersaglio.
	 */
	public void displayMiss(int Giocatore) 
	{
		s.quickShoot("MAXMAX", -10);
		System.out.println("La mossa del giocatore "+players[Giocatore].getNome()+" ha Missato!");
		DisplayTXT.setFont(StatFont);
		DisplayTXT.setText("La mossa di "+players[Giocatore].getNome()+" ha Missato!");
		try {Thread.sleep(1400);} 
		catch (InterruptedException e) {e.printStackTrace();}	
	}
	
	/**
	 * Mostra un messaggio a schermo mediante una finestra di dialogo JOptionPane.
	 *
	 * @param str Il messaggio da visualizzare.
	 */
	public void displayMess(String str) {
		JOptionPane.showMessageDialog(null, str, "ATTENZIONE! Messaggio dal sistema!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Aggiorna l'interfaccia grafica per visualizzare le informazioni aggiornate di un Pokemon durante il combattimento,
	 * in base al giocatore proprietario specificato.
	 *
	 * @param PokemonDaAggiornare Il Pokemon di cui aggiornare le informazioni grafiche.
	 * @param AllenatoreProprietario Indica l'allenatore proprietario del Pokemon (0 per giocatore 1, 1 per giocatore 2).
	 */
	private void updateLabelHighFight(Pokemon PokemonDaAggiornare, int AllenatoreProprietario) 
	{	
		//calcoli xp
		int xp = PokemonDaAggiornare.getPuntiExp();
		int lv = PokemonDaAggiornare.getLivello();
		int xpNextLevel = ((lv+1)*(lv+1)*(lv+1))-(lv*lv*lv);
		double xpSuNextLevel = ((double)(xp-(lv*lv*lv)+1))/xpNextLevel; //Percent
		
		//calcoli ps
		int ps = PokemonDaAggiornare.getPs();
		int psMax = PokemonDaAggiornare.getPsMax();
		double psSuMax = ( (double)(ps) / (double)(psMax) );
		
		switch(AllenatoreProprietario) 
			{
			case 0: 
					labelIMJPK1.setIcon(new ImageIcon(PokemonDaAggiornare.getSpriteRetro()));
					labelNamePK1.setText(PokemonDaAggiornare.getNome());
					labelLVPK1.setText("𝙇𝙫."+PokemonDaAggiornare.getLivello());
					labelPSPK1.setText("𝙋𝙎:"+PokemonDaAggiornare.getPs());
					
					//barra hp
					BarraG1.setValue( (int) (psSuMax*100) );
					
					//barra xp
					BarraG1XP.setValue((int) (xpSuNextLevel*100));
					DisplayTXT.setFont(StatFont);
					DisplayTXT.setText(players[0].getNome()+ " cambia Pokemon! Scende in campo "+PokemonDaAggiornare.getNome()+"!");
					this.repaint();
					break;
					
			case 1: 
					labelIMJPK2.setIcon(new ImageIcon(PokemonDaAggiornare.getSpriteFronte()));
					labelNamePK2.setText(PokemonDaAggiornare.getNome());
					labelLVPK2.setText("𝙇𝙫."+PokemonDaAggiornare.getLivello());
					labelPSPK2.setText("𝙋𝙎:"+PokemonDaAggiornare.getPs());
					
					//barra hp
					BarraG2.setValue( (int) (psSuMax*100) );
					
					//barra xp
					BarraG2XP.setValue((int) (xpSuNextLevel*100));
					DisplayTXT.setFont(StatFont);
					DisplayTXT.setText(players[1].getNome()+ " cambia Pokemon! Scende in campo "+PokemonDaAggiornare.getNome()+"!");

					this.repaint();
					break;
			}
		
	}
	
	/**
	 * Gestisce l'animazione e l'aggiornamento grafico dell'esperienza (XP) per un Pokemon specifico.
	 * Chiamato dal metodo displayXPChange(int, double) per aggiornare l'XP del Pokemon indicato.
	 *
	 * @param PokemonSoggetto Il Pokemon per il quale aggiornare l'XP.
	 * @param LabelXP Etichetta del livello del Pokemon da aggiornare.
	 * @param XPBar Barra di progresso dell'XP del Pokemon.
	 * @param DELAYINCREMENTO Ritardo in millisecondi tra ciascun incremento di XP.
	 * @param INCREMENTO Quantità di XP da incrementare ad ogni passaggio.
	 * @param aggiuntadiXP Quantità totale di XP da aggiungere al Pokemon.
	 */
	private void XPChange(Pokemon PokemonSoggetto, JLabel LabelXP, JProgressBar XPBar, int DELAYINCREMENTO, int INCREMENTO, double aggiuntadiXP)
			{
			
			double PercentAttuale;
			
			int XpBefore = (int) (PokemonSoggetto.getPuntiExp()-aggiuntadiXP);
			int XpAfter  = PokemonSoggetto.getPuntiExp();
			
			int levelsBefore = Integer.parseInt(LabelXP.getText().substring(5));
			int XpReqNextLevel = ((levelsBefore+1)*(levelsBefore+1)*(levelsBefore+1))-(levelsBefore*levelsBefore*levelsBefore);
			
			int XpDisplay = XpBefore-(levelsBefore*levelsBefore*levelsBefore);
			while (XpBefore <= XpAfter) 
				{
				
				try {
					Thread.sleep(DELAYINCREMENTO);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				XpBefore+=INCREMENTO;
				XpDisplay += INCREMENTO;
				
				PercentAttuale = ((double)XpDisplay/(double)XpReqNextLevel)*100;
				XPBar.setValue((int) PercentAttuale);
				
				if (XpBefore>((levelsBefore+1)*(levelsBefore+1)*(levelsBefore+1))) 
					{
					levelsBefore++;
					XPBar.setValue(0);
					s.quickShoot("LevelUP", 10);
					XpDisplay=0;
					
					XpReqNextLevel = ((levelsBefore+1)*(levelsBefore+1)*(levelsBefore+1))-(levelsBefore*levelsBefore*levelsBefore);
					LabelXP.setText("𝙇𝙫."+levelsBefore);
					}
				
				this.repaint();
				}	
			}
		
	/**
	 * Visualizza e gestisce l'aggiornamento dell'esperienza (XP) per il Pokemon beneficiario.
	 * Questo metodo richiama XPChange(Pokemon, JLabel, JProgressBar, int, int, double) per gestire l'aggiornamento grafico.
	 *<p>
	 * @param GiocatoreBeneficiario Indice del giocatore che riceve l'XP (0 o 1).
	 * @param aggiuntadiXP Quantità di XP da aggiungere.
	 *
	 * Il metodo è esclusivamente chiamato dalla classe Partita
	 * Guarda Partita
	 */
	public void displayXPChange(int GiocatoreBeneficiario, double aggiuntadiXP) 
		{

			Pokemon PokemonSoggetto;
			
			final int DELAYINCREMENTO = 5;
			final int INCREMENTO  = 2;
			
			switch(GiocatoreBeneficiario) 
			{
			case 0: //Giocatore 0 riceve XP
					
					PokemonSoggetto = Francesco.getPokemonGiocatore1();
					XPChange(PokemonSoggetto, labelLVPK1, BarraG1XP, DELAYINCREMENTO, INCREMENTO, aggiuntadiXP);
					break;
					
			case 1: //Giocatore 1 riceve XP
					PokemonSoggetto = Francesco.getPokemonGiocatore2();
					XPChange(PokemonSoggetto, labelLVPK2, BarraG2XP, DELAYINCREMENTO, INCREMENTO, aggiuntadiXP);
					break;
		
			}
		}
	
	/**
	 * Aggiorna l'interfaccia grafica mostrando la variazione dei punti vita (PS) del Pokemon attaccante e difensore
	 * dopo che il Pokemon attaccante ha eseguito una mossa. Gestisce l'animazione delle barre della vita durante
	 * il combattimento. Utilizza un case switch per determinare quale allenatore sta attaccando e aggiorna le
	 * barre di stato globali dedicate ai Pokemon.
	 *<p>
	 * @param vitaInizialeDIFENSORE PS iniziali del Pokemon difensore.
	 * @param vitaInizialeATTACCANTE PS iniziali del Pokemon attaccante.
	 * @param AllenatoreProprietario Indice dell'allenatore proprietario del Pokemon attaccante (0 o 1).
	 * @param difensore Pokemon difensore colpito dalla mossa.
	 * @param attaccante Pokemon che esegue la mossa.
	 *
	 * <p>
	 * Il metodo è esclusivamente chiamato dall'istanza PartitaLogica della classe Partita
	 * Guarda Partita
	 */
	private void updateDisplayPS(int vitaInizialeDIFENSORE, int vitaInizialeATTACCANTE, int AllenatoreProprietario, Pokemon difensore, Pokemon attaccante) 
	{

		int PSfinaliATTACCANTE = attaccante.getPs();
		int PSfinaliDIFENSORE = difensore.getPs();
		//vitaInizialeATTACCANTE
		//vitaInizialeDIFENSORE
		int PSmaxATTACCANTE = attaccante.getPsMax();
		int PSmaxDIFENSORE = difensore.getPsMax();
		
		final int DELAYINCREMENTO = 20; //Prima 170
		
		//double PercentAttuale = vitaInizialeDIFENSORE/PSmaxDIFENSORE;
		double PercentAttuale = 100.0;
		
		//Sperimentale
		double PercentArrivo = ((double)PSfinaliDIFENSORE/(double)PSmaxDIFENSORE)*100;
		
		//attacc
		double PercentAttualeAtt = 100.0;
		
		//Sperimentale def
		double PercentArrivoAtt = ((double)PSfinaliATTACCANTE/(double)PSmaxATTACCANTE)*100;

		
		System.out.println("***");
		System.out.println("HP INIZIALI DI ATTACCANTE "+attaccante.getNome()+ " : "+vitaInizialeATTACCANTE);
		System.out.println("HP FINALI DI ATTACCANTE "+attaccante.getNome()+ " : "+PSfinaliATTACCANTE);
		System.out.println("HP INIZIALI DI DIFENSORE "+difensore.getNome()+ " : "+vitaInizialeDIFENSORE);
		System.out.println("HP FINALI DI DIFENSORE "+difensore.getNome()+ " : "+PSfinaliDIFENSORE);
		System.out.println("***");
		
		switch(AllenatoreProprietario) 
			{
			case 0: 
				{	//Se attacca G1
				    //vitaInizialeATTACCANTE è Vita Iniziale di Francesco.getGiocatorePokemon1();
					//vitaInizialeDIFENSORE è Vita Iniziale di Francesco.getGiocatorePokemon2();
					//Attaccante è P1
					//Difensore  è P2
					
					if (PSfinaliDIFENSORE != vitaInizialeDIFENSORE)
						{
					
					//VITA DEL DIFENSORE
						while (PercentAttuale > PercentArrivo) 
							{
							
							try {
								Thread.sleep(DELAYINCREMENTO);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							

							
							PercentAttuale = BarraG2.getValue()-1;
							if (PSfinaliDIFENSORE < vitaInizialeDIFENSORE) {vitaInizialeDIFENSORE -= 1;}
							
							BarraG2.setValue((int) PercentAttuale);
							labelPSPK2.setText("𝙋𝙎:"+vitaInizialeDIFENSORE);
							
							

							this.repaint();
							}	
						}
					//VITA DELL'ATTACCANTE IN CASO DI CONTRACCOLPO O CURA
					if (vitaInizialeATTACCANTE != PSfinaliATTACCANTE) 
						{
						
						PercentAttualeAtt = BarraG1.getValue();
						
						
							//se la vita iniziale è meno della finale (ha guadagnato vita)
						if (PercentAttualeAtt < PercentArrivoAtt) 
							
							{
							while (PercentAttualeAtt < PercentArrivoAtt) 
								{
								
								try {
									Thread.sleep(DELAYINCREMENTO);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								
								PercentAttualeAtt = BarraG1.getValue()+1;
								if (vitaInizialeATTACCANTE < PSfinaliATTACCANTE) {vitaInizialeATTACCANTE += 1;}
								
								BarraG1.setValue((int) PercentAttualeAtt);
								labelPSPK1.setText("𝙋𝙎:"+vitaInizialeATTACCANTE);
							
								
								this.repaint();
								}	
								
							}
						else {
							while (PercentAttualeAtt > PercentArrivoAtt) 
								{
								
								try {
									Thread.sleep(DELAYINCREMENTO);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								
								PercentAttualeAtt = BarraG1.getValue()-1;
								if (vitaInizialeATTACCANTE > PSfinaliATTACCANTE) {vitaInizialeATTACCANTE -= 1;}
								
								BarraG1.setValue((int) PercentAttualeAtt);
								labelPSPK1.setText("𝙋𝙎:"+vitaInizialeATTACCANTE);
							
								
								this.repaint();
								}	
							}
						}
					

					
					if (((PercentArrivo <= 25.0 &&  PercentArrivo != 0.0) || (PercentArrivoAtt <= 25.0  &&  PercentArrivoAtt != 0.0)) && !s.checkWav("Panico") )
						{
						s.stop();
						s.start("Panico",-1);
						}
					
					//FINE CASO
					break;
					}
					
		
			case 1: 
				{	//Se attacca G2
					//vitaInizialeATTACCANTE è Vita Iniziale di Francesco.getGiocatorePokemon2();
					//vitaInizialeDIFENSORE è Vita Iniziale di Francesco.getGiocatorePokemon1();
					//Attaccante è P2
					//Difensore  è P1 

				
					if (PSfinaliDIFENSORE != vitaInizialeDIFENSORE)
					{
						while (PercentAttuale > PercentArrivo) 
						{
						
						try {
							Thread.sleep(DELAYINCREMENTO);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					
						
						PercentAttuale = BarraG1.getValue()-1;
						if (PSfinaliDIFENSORE < vitaInizialeDIFENSORE) {vitaInizialeDIFENSORE -= 1;}
						
						BarraG1.setValue((int) PercentAttuale);
						labelPSPK1.setText("𝙋𝙎:"+vitaInizialeDIFENSORE);
						
						
						//System.out.println(vitaInizialeDIFENSORE+" PS DIFENSORE("+difensore.getNome()+")");
						//System.out.println(PSfinaliDIFENSORE+" PS veri("+difensore.getNome()+")");
	
						
						this.repaint();
						}	
					}
					//VITA DELL'ATTACCANTE IN CASO DI CONTRACCOLPO O CURA
					if (vitaInizialeATTACCANTE != PSfinaliATTACCANTE) 
						{
					
						PercentAttualeAtt = BarraG2.getValue();
							//se la vita iniziale è meno della finale (ha guadagnato vita)
						if (PercentAttualeAtt < PercentArrivoAtt) 
							
							{

							while (PercentAttualeAtt < PercentArrivoAtt) 
								{
								
								try {
									Thread.sleep(DELAYINCREMENTO);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								
								PercentAttualeAtt = BarraG2.getValue()+1;
								if (vitaInizialeATTACCANTE < PSfinaliATTACCANTE) {vitaInizialeATTACCANTE += 1;}
								
								BarraG2.setValue((int) PercentAttualeAtt);
								labelPSPK2.setText("𝙋𝙎:"+vitaInizialeATTACCANTE);
							
								
								this.repaint();
								}	
								
							}
						else {
							while (PercentAttualeAtt > PercentArrivoAtt) 
								{
								
								try {
									Thread.sleep(DELAYINCREMENTO);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								
								PercentAttualeAtt = BarraG2.getValue()-1;
								if (vitaInizialeATTACCANTE > PSfinaliATTACCANTE) {vitaInizialeATTACCANTE -= 1;}
								
								BarraG2.setValue((int) PercentAttualeAtt);
								labelPSPK2.setText("𝙋𝙎:"+vitaInizialeATTACCANTE);
							
								
								this.repaint();
								}	
							}
						}
					
					if (((PercentArrivo <= 25.0 &&  PercentArrivo != 0.0) || (PercentArrivoAtt <= 25.0  &&  PercentArrivoAtt != 0.0)) && !s.checkWav("Panico") )
						{
						s.stop();
						s.start("Panico",-1);
						}
					//FINE CASO
					break;
				}
					
	
			}
		
	}
	
	/**
	 * Aggiorna l'interfaccia grafica dopo la fine del combattimento, sostituendo il pannello di lotta
	 * alla schermata di pre-fight. Aggiorna anche le statistiche dei giocatori e ripristina
	 * lo stato dei Pokemon. Ferma la musica di combattimento utilizzando {@link Soundboard#stop()}
	 *<p>
	 * @param Vincitore Indice del vincitore (0 o 1).
	 * @param Looser Indice del perdente (0 o 1).
	 * @param AWinner Allenatore vincitore dopo il combattimento.
	 * @param ALooser Allenatore perdente dopo il combattimento.
	 */
	public void updateDisplayWin(int Vincitore, int Looser, Allenatore AWinner, Allenatore ALooser) 
	{
		//AWinner è come è uscito dal fight l'allenatore vincitore, 
		//mentre int è quello nell'array che verrà refeedato 
		//Qui vengono presi i dati per le leaderboard
		int WinnerPRimasti = AWinner.getPokemonRimasti();
		int WinnerPTotali = AWinner.getPokemonTotali();
		
		int LooserPRimasti = ALooser.getPokemonRimasti();
		int LooserPTotali = ALooser.getPokemonTotali();
		
		
		int WinnerPokemonPersi = WinnerPTotali-WinnerPRimasti;
		int WinnerPokemonKills = LooserPTotali-LooserPRimasti;
		
		int LooserPokemonPersi = WinnerPokemonKills;
		int LooserPokemonKills = WinnerPokemonPersi;
		
		if(Vincitore==0) 
			{
			Winp1 += 1;
			}
		
		else 
			{
			Winp2 += 1;
			}
		
		AWinner.addStats(0, 0, WinnerPokemonKills, WinnerPokemonPersi);
		ALooser.addStats(0, 0, LooserPokemonKills, LooserPokemonPersi);
		//new Salvataggio().addScoreboard(ALooser);

		
		//Qui si fanno tornare in vita i pokemon, Btw perchè si può settare la vita Max di un pokemon da fuori?
		//Possibile comando in pokemon che autoresetta il pokemon?
		for (Pokemon Pokemon : AWinner.getPokemonLista()) {
			healPokemon(Pokemon);
		}
		
		for (Pokemon Pokemon : ALooser.getPokemonLista()) {
			healPokemon(Pokemon);
		}
		
		//Si mette come input al prossimo game il vecchio allenatore con i pokemon refillati
		//(Si salvano i progressi dei player di questo game)
		new Salvataggio<Allenatore>().salvaItem(AWinner, "saves", AWinner.getNome());
		new Salvataggio<Allenatore>().salvaItem(ALooser, "saves", ALooser.getNome());
		
		//Rallento il programma per motivi estetici
		try {Thread.sleep(300);} 
		catch (InterruptedException e) {e.printStackTrace();}	
		s.stop();
		
		//Pannello prefight
		creaLowPreFight();
		creaHighPreFight();
		
		//Stacca il pannello del fight e torna al prefight
		this.remove(panLowCombatWAIT);
		this.remove(panHighFight);
		this.add(panLowPreFight);
		this.add(panHighPreFight);
		this.repaint();

	}
	
	/**
	 * Ripristina completamente lo stato del Pokemon specificato ripristinando tutti i suoi attributi,
	 * ripristinando i PP delle sue mosse e azzerando tutti i contatori di modificatori di statistica.
	 *<p>
	 * @param pokemon Il Pokemon da ripristinare.
	 */
	private void healPokemon(Pokemon pokemon) 
	{
		if (pokemon != null) 
		{
			pokemon.setPs(pokemon.getPsMax());
			pokemon.setAttacco(pokemon.getAttaccoMax());
			pokemon.setDifesa(pokemon.getDifesaMax());
			pokemon.setAttaccoSp(pokemon.getAttaccoSpMax());
			pokemon.setDifesaSp(pokemon.getDifesaSpMax());
			pokemon.setVelocità(pokemon.getVelocitàMax());
			pokemon.setContatoreBuffAtk(0);
			pokemon.setContatoreBuffDif(0);
			pokemon.setContatoreBuffAtkSp(0);
			pokemon.setContatoreBuffDifSp(0);
			pokemon.setContatoreBuffVel(0);
			pokemon.setContatoreBuffPrec(100);
			pokemon.emptyWhoHitMe();
			//Resetta i pp delle mosse del pokemon
			for (int k = 0; k <  4; k++) 
				{
				Mossa mossa = pokemon.getMossa(k);
				if (mossa!= null)
					{
					mossa.setPP(mossa.getPPMax());
					}
				}
		}
	}
	
	/**
	 * Collega l'istanza di Partita fornita a questa classe GUI per gestire la partita logica.
	 *
	 * @param Fight L'istanza di {@code Partita} da collegare a questa GUI.
	 */
	public void linkToPartita(Partita Fight) {this.Francesco=Fight;System.out.println("GUI linkata a Partita con successo");}
	
	/**
	 * Avvia un timer che, dopo il numero di secondi specificato, chiama il metodo stopStandby() di Partita.
	 *
	 * @param Secondi Il numero di secondi dopo i quali fermare lo standby.
	 */
	private void stopStandby(int Secondi) 
	{
		//Dice dopo tot secondi alla partita di fermare lo standby
		
		new java.util.Timer().schedule(new TimerTask(){
	        @Override
	        public void run() {
	            Francesco.stopStandby();
	        }
	    },1000*Secondi); 
	}
	
	/**
	 * Avvia un timer che, dopo il numero di secondi specificato, chiama il metodo stopStandbySecondo() di Partita.
	 *
	 * @param Secondi Il numero di secondi dopo i quali fermare lo standby.
	 */
	private void stopStandbySecondo(int Secondi) 
	{
		//Dice dopo tot secondi alla partita di fermare lo standby
	
		new java.util.Timer().schedule(new TimerTask(){
	        @Override
	        public void run() {
	            Francesco.stopStandbySecondo();
	        }
	    },1000*Secondi); 
	}

	
	//----------------------------------------------------------------------
	// 							Metodi Shortcut e Creazione
	//----------------------------------------------------------------------

		
	/**
	 * Crea e restituisce un oggetto MyButton configurato con titolo, posizioni, dimensioni e gestione degli eventi del mouse per vari stati,
	 * utilizzando diverse immagini per l'aspetto normale, lo stato di focus, e lo stato di pressione.
	 *
	 * @param titolo Il testo da visualizzare nel bottone.
	 * @param x0 La coordinata X iniziale del bottone.
	 * @param y0 La coordinata Y iniziale del bottone.
	 * @param x La larghezza del bottone.
	 * @param y L'altezza del bottone.
	 * @param indirizzoImmagine L'indirizzo dell'immagine normale del bottone.
	 * @param indirizzoImmagineScura L'indirizzo dell'immagine scura del bottone quando è premuto.
	 * @param indirizzoFocus L'indirizzo dell'immagine del bottone quando è in stato di focus.
	 * @return Un oggetto MyButton configurato con titolo, posizioni, dimensioni e gestione degli eventi del mouse per vari stati.
	 */
	private MyButton creaBottone(String titolo,int x0,int y0, int x, int y, String indirizzoImmagine, String indirizzoImmagineScura, String indirizzoFocus) 
		{
		MyButton bottone = new MyButton(titolo, indirizzoImmagine);
		bottone.setBounds(x0, y0, x, y);
		bottone.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent evt)
            {
            	
            	bottone.addFocus(getImage(indirizzoFocus)); 
                //System.out.println("SOPRA");
                bottone.repaint();
            }
            public void mouseExited(MouseEvent evt)
            {
            	bottone.removeFocus();
            	//System.out.println("FUORI");
            	bottone.repaint();
            }
            public void mousePressed(MouseEvent evt)
            {
            	if (bottone.isEnabled() && indirizzoImmagineScura!= null && indirizzoImmagineScura != ""){
            		bottone.setSfondo(getImage(indirizzoImmagineScura));
	            	//System.out.println("PREMUTO");
	            	bottone.repaint();
            	}
            }
            public void mouseReleased(MouseEvent evt)
            {
            	if (bottone.isEnabled() && indirizzoImmagineScura!= null && indirizzoImmagineScura != ""){
            		bottone.setSfondo(getImage(indirizzoImmagine));
            		bottone.removeFocus();
	            	//System.out.println("RILASCIATO");
	            	bottone.repaint();
            	}
            }
        });
		return bottone;
		}
	
	/**
	 * Crea e restituisce un oggetto MyButton configurato con titolo, posizioni, dimensioni e immagini per diversi stati,
	 * e lo aggiunge al pannello specificato con un indice di layering predefinito.
	 *
	 * @param titolo Il testo da visualizzare nel bottone.
	 * @param x0 La coordinata X iniziale del bottone.
	 * @param y0 La coordinata Y iniziale del bottone.
	 * @param x La larghezza del bottone.
	 * @param y L'altezza del bottone.
	 * @param target Il pannello JLayeredPane a cui aggiungere il bottone.
	 * @param indirizzoImmagine L'indirizzo dell'immagine normale del bottone.
	 * @param indirizzoImmagineScura L'indirizzo dell'immagine scura del bottone.
	 * @param indirizzoFocus L'indirizzo dell'immagine del bottone quando è in stato di focus.
	 * @return Un oggetto MyButton configurato con titolo, posizioni, dimensioni e immagini specificate, aggiunto al pannello target.
	 */
	private MyButton creaBottone(String titolo,int x0,int y0, int x, int y, JLayeredPane target, String indirizzoImmagine, String indirizzoImmagineScura, String indirizzoFocus) 
		{
		
		MyButton bottone = creaBottone(titolo, x0, y0,  x,  y,  indirizzoImmagine, indirizzoImmagineScura, indirizzoFocus) ;
		target.add(bottone, Integer.valueOf(3));
		
		return bottone;
		}
	
	/**
	 * Crea e restituisce un oggetto MyButton configurato con titolo, posizioni e dimensioni definite,
	 * e lo aggiunge al pannello specificato con un indice di layering predefinito.
	 * Nota bene! A differenza degli altri creaBottone questo non include in automatico il MouseListener
	 * Pertanto va settato manualmente!
	 *
	 * @param titolo Il testo da visualizzare nel bottone.
	 * @param x0 La coordinata X iniziale del bottone.
	 * @param y0 La coordinata Y iniziale del bottone.
	 * @param x La larghezza del bottone.
	 * @param y L'altezza del bottone.
	 * @param target Il pannello JLayeredPane a cui aggiungere il bottone.
	 * @return Un oggetto MyButton configurato con titolo, posizioni e dimensioni specificate, aggiunto al pannello target.
	 */
	private MyButton creaBottone(String titolo,int x0,int y0, int x, int y, JLayeredPane target) 
		{
		MyButton bottone = new MyButton(titolo, "");
		bottone.setBounds(x0, y0, x, y);
		target.add(bottone, Integer.valueOf(3));
		return bottone;
		}
	
	/**
	 * Crea e restituisce un oggetto JLayeredPane configurato con layout nullo, sfondo grigio chiaro e opaco,
	 * e aggiunge un bottone "Back to Main" posizionato di default.
	 *
	 * @return Un oggetto JLayeredPane configurato con layout nullo, sfondo grigio chiaro e opaco, con il bottone "Back to Main" aggiunto.
	 */
	private JLayeredPane creaPanelWB() 
		{
		JLayeredPane pannello = new JLayeredPane();
		pannello.setLayout(null);
		pannello.setBackground(Color.LIGHT_GRAY);
		pannello.setOpaque(true);
		creaBackToMain(pannello);        //attacco il bottone backtomain
		return pannello;
		}
	
	/**
	 * Crea e restituisce un oggetto JLayeredPane configurato con layout nullo, sfondo grigio chiaro e opaco,
	 * e aggiunge un bottone "Back to Main" posizionato alle coordinate specificate.
	 *
	 * @return Un oggetto JLayeredPane configurato con layout nullo, sfondo grigio chiaro e opaco, con il bottone "Back to Main" aggiunto.
	 */
	private JLayeredPane creaPanelWBScore() 
	{
	JLayeredPane pannello = new JLayeredPane();
	pannello.setLayout(null);
	pannello.setBackground(Color.LIGHT_GRAY);
	pannello.setOpaque(true);
	creaBackToMainXY(pannello,15,720);        //attacco il bottone backtomain
	return pannello;
	}
	
	/**
	 * Crea e restituisce un oggetto MySlider configurato con orientamento, valore minimo, massimo e valore iniziale specificati,
	 * e aggiunge un listener per gestire lo scorrimento della rotellina del mouse.
	 *
	 * @param Orientamento L'orientamento del slider (SwingConstants.HORIZONTAL o SwingConstants.VERTICAL).
	 * @param min Il valore minimo del slider.
	 * @param max Il valore massimo del slider.
	 * @param start Il valore iniziale del slider.
	 * @return Un oggetto MySlider configurato con l'orientamento, il valore minimo, massimo e iniziale specificati, con listener per lo scorrimento della rotellina del mouse.
	 */
	private MySlider creaSlider(int Orientamento ,int min, int max, int start) 
		{
		MySlider Slider = new MySlider(Orientamento,min,max,start);
		
		MouseWheelListener Listener = new MouseWheelListener() 
			{
				
				@Override
				public void mouseWheelMoved(MouseWheelEvent e) {
					
					//SE SCROLLI IN AVANTI
					if (e.getWheelRotation() < 0) {
						Slider.setValue(Slider.getValue()+2);
						
						}
					//SE SCROLLI VERSO DIETRO
					else {
						Slider.setValue(Slider.getValue()-2);
					}
			}};
		

		Slider.addMouseWheelListener(Listener);
		
	    return Slider;
		}
	
	/**
	 * Crea e restituisce un oggetto JLayeredPane configurato con dimensioni e posizioni definite,
	 * con layout nullo, sfondo grigio e opaco.
	 *
	 * @param x0 La coordinata X iniziale del JLayeredPane.
	 * @param y0 La coordinata Y iniziale del JLayeredPane.
	 * @param x La larghezza del JLayeredPane.
	 * @param y L'altezza del JLayeredPane.
	 * @return Un oggetto JLayeredPane configurato con le dimensioni, il layout, lo sfondo e l'opacità specificati.
	 */
	private JLayeredPane creaPanel(int x0, int y0, int x, int y) 
		{
		JLayeredPane pannello = new JLayeredPane();
		pannello.setLayout(null);
		pannello.setBackground(Color.GRAY);
		pannello.setOpaque(true);
		pannello.setBounds(x0, y0, x, y);
		return pannello;
		}
	
	/**
	 * Crea e restituisce un oggetto JLabel con il testo specificato, configurato con dimensioni, posizioni, font e aggiunto al pannello specificato.
	 *
	 * @param testo Il testo da visualizzare nel JLabel.
	 * @param x0 La coordinata X iniziale del JLabel.
	 * @param y0 La coordinata Y iniziale del JLabel.
	 * @param x La larghezza del JLabel.
	 * @param y L'altezza del JLabel.
	 * @param target Il pannello JLayeredPane a cui aggiungere il JLabel.
	 * @param fontdesiderato Il font da applicare al JLabel.
	 * @return Un oggetto JLabel configurato con il testo, le dimensioni, il font specificato e aggiunto al pannello target.
	 */
	private JLabel creaLabel(String testo, int x0, int y0, int x, int y, JLayeredPane target, Font fontdesiderato)
 
		{
		JLabel pannello = new JLabel();
		pannello.setFont(fontdesiderato);
		pannello.setText(testo);
		pannello.setBounds(x0,y0,x,y);
		pannello.setBackground(null);
		target.add(pannello);
		return pannello;
		}
	
	/**
	 * Crea e restituisce un oggetto JLabel con il testo specificato, configurato con dimensioni, posizioni, font e indice di layering definiti,
	 * e lo aggiunge al pannello specificato.
	 *
	 * @param testo Il testo da visualizzare nel JLabel.
	 * @param x0 La coordinata X iniziale del JLabel.
	 * @param y0 La coordinata Y iniziale del JLabel.
	 * @param x La larghezza del JLabel.
	 * @param y L'altezza del JLabel.
	 * @param target Il pannello JLayeredPane a cui aggiungere il JLabel.
	 * @param fontdesiderato Il font da applicare al JLabel.
	 * @param index L'indice di layering che determina l'ordine di sovrapposizione nel pannello.
	 * @return Un oggetto JLabel configurato con il testo, le dimensioni, il font e l'indice di layering specificati, aggiunto al pannello target.
	 */
	private JLabel creaLabel(String testo, int x0, int y0, int x, int y, JLayeredPane target, Font fontdesiderato,int index)
			{
			JLabel pannello = new JLabel();
			//pannello.setFont(new Font("Bahnschrift SemiBold",Font.PLAIN,25));
			pannello.setFont(fontdesiderato);
			pannello.setText(testo);
			pannello.setBounds(x0,y0,x,y);
			pannello.setBackground(null);
			target.add(pannello, Integer.valueOf(index));
			return pannello;
			}
	
	/**
	 * Crea e restituisce un oggetto JLabel con il testo specificato, configurato con dimensioni, posizioni, font, indice di layering e colore del testo definiti,
	 * e lo aggiunge al pannello specificato.
	 *
	 * @param testo Il testo da visualizzare nel JLabel.
	 * @param x0 La coordinata X iniziale del JLabel.
	 * @param y0 La coordinata Y iniziale del JLabel.
	 * @param x La larghezza del JLabel.
	 * @param y L'altezza del JLabel.
	 * @param target Il pannello JLayeredPane a cui aggiungere il JLabel.
	 * @param fontdesiderato Il font da applicare al JLabel.
	 * @param index L'indice di layering che determina l'ordine di sovrapposizione nel pannello.
	 * @param colore Il colore del testo del JLabel.
	 * @return Un oggetto JLabel configurato con il testo, le dimensioni, il font, l'indice di layering e il colore del testo specificati, aggiunto al pannello target.
	 */
	private JLabel creaLabel(String testo, int x0, int y0, int x, int y, JLayeredPane target, Font fontdesiderato,int index,Color colore) {
		JLabel pannello=creaLabel(testo,x0,y0,x,y,target,fontdesiderato,index);
		pannello.setForeground(colore);
		return pannello;
	}
	
	/**
	 * Crea e restituisce un oggetto JLabel con il testo specificato e le dimensioni e posizioni definite.
	 *
	 * @param testo Il testo da visualizzare nel JLabel.
	 * @param x0 La coordinata X iniziale del JLabel.
	 * @param y0 La coordinata Y iniziale del JLabel.
	 * @param x La larghezza del JLabel.
	 * @param y L'altezza del JLabel.
	 * @return Un oggetto JLabel configurato con il testo e le dimensioni specificate.
	 */
	private JLabel creaLabel(String testo, int x0, int y0, int x, int y) 
		{
		JLabel pannello = new JLabel();
		//pannello.setFont(new Font("Bahnschrift SemiBold",Font.PLAIN,25));
		pannello.setFont(GeneralFont);
		pannello.setText(testo);
		pannello.setBounds(x0,y0,x,y);
		pannello.setBackground(null);
		return pannello;
		}
	
	/**
	 * Crea e restituisce un oggetto JLabel con un'icona immagine caricata dal percorso specificato,
	 * configurato con dimensioni e posizioni definite.
	 *
	 * @param x0 La coordinata X iniziale del JLabel.
	 * @param y0 La coordinata Y iniziale del JLabel.
	 * @param x La larghezza del JLabel.
	 * @param y L'altezza del JLabel.
	 * @param path Il percorso della risorsa dell'immagine da caricare.
	 * @return Un oggetto JLabel configurato con l'icona immagine dal percorso specificato e le dimensioni specificate.
	 */
	private JLabel creaLabelImmagine( int x0, int y0, int x, int y,String path) {
		JLabel pannello = creaLabel("",x0,y0,x,y); 
		pannello.setIcon(new ImageIcon(path));
		return pannello;

	}
	
	/**
	 * Crea e restituisce un oggetto JLabel con un'icona ImageIcon specificata, configurato con dimensioni e posizioni definite.
	 *
	 * @param x0 La coordinata X iniziale del JLabel.
	 * @param y0 La coordinata Y iniziale del JLabel.
	 * @param x La larghezza del JLabel.
	 * @param y L'altezza del JLabel.
	 * @param ImageIcon L'icona ImageIcon da impostare nel JLabel.
	 * @return Un oggetto JLabel configurato con l'icona ImageIcon e le dimensioni specificate.
	 */
	private JLabel creaLabelImmagine( int x0, int y0, int x, int y,ImageIcon ImageIcon) {
		JLabel pannello = creaLabel("",x0,y0,x,y); 
		pannello.setIcon(ImageIcon);
		return pannello;

	}
	
	/**
	 * Crea e restituisce un oggetto JLabel con un'icona immagine specificata dal percorso fornito,
	 * e lo aggiunge al pannello specificato.
	 *
	 * @param x0 La coordinata X iniziale del JLabel.
	 * @param y0 La coordinata Y iniziale del JLabel.
	 * @param x La larghezza del JLabel.
	 * @param y L'altezza del JLabel.
	 * @param path Il percorso della risorsa dell'immagine da caricare.
	 * @param target Il pannello JLayeredPane a cui aggiungere il JLabel.
	 * @return Un oggetto JLabel configurato con l'icona immagine specificata e aggiunto al pannello target.
	 */
	private JLabel creaLabelImmagine( int x0, int y0, int x, int y,String path,JLayeredPane target) {
		JLabel pannello = creaLabelImmagine(x0,y0,x,y,path); 
		target.add(pannello,0);
		return pannello;
	}
	
	/**
	 * Crea e restituisce un oggetto JLabel con un'icona immagine e lo aggiunge al pannello specificato.
	 *
	 * @param x0 La coordinata X iniziale del JLabel.
	 * @param y0 La coordinata Y iniziale del JLabel.
	 * @param x La larghezza del JLabel.
	 * @param y L'altezza del JLabel.
	 * @param ImageIcon L'icona ImageIcon da visualizzare nel JLabel.
	 * @param target Il pannello JLayeredPane a cui aggiungere il JLabel.
	 * @return Un oggetto JLabel configurato con l'icona immagine e aggiunto al pannello target.
	 */
	private JLabel creaLabelImmagine( int x0, int y0, int x, int y,ImageIcon ImageIcon,JLayeredPane target) {
		JLabel pannello = creaLabelImmagine(x0,y0,x,y,ImageIcon); 
		target.add(pannello,0);
		return pannello;
	}
	
	/**
	 * Crea e aggiunge un bottone per tornare alla schermata principale all'interno del pannello specificato,
	 * posizionandolo alle coordinate predefinite (15, 320).
	 *
	 * @param pannello Il pannello JLayeredPane in cui aggiungere il bottone per tornare alla schermata principale.
	 */
	private void creaBackToMain(JLayeredPane pannello) 
		{
		creaBackToMainXY(pannello,15,320);
		}
	
	/**
	 * Crea e aggiunge un bottone per tornare alla schermata principale all'interno del pannello specificato.
	 * Il bottone è posizionato alle coordinate (x0, y0) nel pannello.
	 * Quando il bottone viene premuto, rimuove diversi pannelli di gioco e aggiunge nuovamente i pannelli principali.
	 * Azzera anche le variabili di vittoria per il multiplayer.
	 *
	 * @param pannello Il pannello JLayeredPane in cui aggiungere il bottone.
	 * @param x0 La coordinata X per il posizionamento del bottone all'interno del pannello.
	 * @param y0 La coordinata Y per il posizionamento del bottone all'interno del pannello.
	 */
	private void creaBackToMainXY(JLayeredPane pannello, int x0, int y0) 
	{
		MyButton BackToMain = creaBottone("", x0,y0,50,50, "assets/Immagini/Pokeback.png", "assets/Immagini/PokebackScura.png", "assets/Immagini/F5050.png");
		BackToMain.addActionListener(e -> 
		{
			s.quickShoot("ButtonEffect");
			this.remove(panLowNewGame);
			this.remove(panHighNewGame);
			
			this.remove(panLowMultiplayer);
			this.remove(panHighMultiplayer);
			
			this.remove(panScoreBoard);
			
			
			if (panLowPreFight != null && panHighPreFight != null) {
				this.remove(panLowPreFight);
				this.remove(panHighPreFight);
				}
			
			//temp svuoto, ma probs rimane
			creaHighMultiplayer();
			creaMultiplayer();
			creaNewGame();
			creaHighNewGame();
			creaScoreBoard();
			
			Winp1 = 0;
			Winp2 = 0;
			
			this.add(panHighMainMenu);
			this.add(panLowMainMenu);
			this.repaint();
		});
		pannello.add(BackToMain, Integer.valueOf(3));                    //aggiungo bottone
	}
	
	//Qui iniziano i get Rapidi
	
	/**
	 * Rimuove un `MouseListener` dal bottone target e aggiunge un nuovo `MouseAdapter`
	 * per gestire gli eventi del mouse, cambiando l'immagine di sfondo e lo stato di focus.
	 *
	 * @param target    Il bottone su cui agire.
	 * @param imjbianca Il percorso dell'immagine di sfondo quando il mouse viene rilasciato.
	 * @param imjnera   Il percorso dell'immagine di sfondo quando il mouse viene premuto.
	 */
	private void removeMouseListener(MyButton target, String imjbianca, String imjnera ,String focus) 
		{
		target.removeMouseListener(target.getMouseListeners()[1]);
		target.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent evt)
            {
            	
            	target.addFocus(getImage(focus)); 
                //System.out.println("SOPRA");
                target.repaint();
            }
            public void mouseExited(MouseEvent evt)
            {
            	target.removeFocus();
            	//System.out.println("FUORI");
            	target.repaint();
            }
            public void mousePressed(MouseEvent evt)
            {
            	if (target.isEnabled() && imjnera!= null && imjnera != ""){
            		target.setSfondo(getImage(imjnera));
	            	//System.out.println("PREMUTO");
	            	target.repaint();
            	}
            }
            public void mouseReleased(MouseEvent evt)
            {
            	if (target.isEnabled() && imjnera!= null && imjnera != ""){
            		target.setSfondo(getImage(imjbianca));
	            	//System.out.println("RILASCIATO");
	            	target.repaint();
            	}
            }
        });
		}
	
	/**
	 * Crea e restituisce un pannello JLayeredPane completo per visualizzare le mosse di un Pokemon.
	 * Il pannello include un pannello di visualizzazione delle statistiche delle mosse e gestisce vari bottoni
	 * per la navigazione tra le schermate delle mosse e per tornare al pannello di origine.
	 *
	 * @param x0Gr La coordinata X iniziale per la griglia delle mosse.
	 * @param y0Gr La coordinata Y iniziale per la griglia delle mosse.
	 * @param xGr La larghezza di ogni bottone nella griglia delle mosse.
	 * @param yGr L'altezza di ogni bottone nella griglia delle mosse.
	 * @param pokemondesignato Il Pokemon di cui visualizzare le mosse.
	 * @param PannelloOrigine Il pannello JLayeredPane di origine da cui è stato attivato il metodo.
	 * @param bottoneOrigine Il bottone MyButton di origine che ha attivato la visualizzazione delle mosse.
	 * @return Un pannello JLayeredPane contenente le mosse del Pokemon e i relativi controlli.
	 */
	private JLayeredPane getMossaFullSystem(int x0Gr,int y0Gr, int xGr,int  yGr, Pokemon pokemondesignato, JLayeredPane PannelloOrigine, MyButton bottoneOrigine ) 
	{
		
		//Parte legata alle Mosse la roba indentata è roba sua
				JLayeredPane POKEMONMOSSE = creaPanel(0, 400, 686, 400);
				JLabel immaginez = creaLabel("",0,0,686,400);
				if (gamerGaming == 0) {immaginez.setIcon(new ImageIcon("assets/Immagini/background scelta pokemon g1.png"));} //IMG BLU G1
				else{immaginez.setIcon(new ImageIcon("assets/Immagini/background scelta pokemon g2.png"));}  //IMG BLU G2
				POKEMONMOSSE.add(immaginez, Integer.valueOf(0));
				
				//Pannello contenente solo label, aggiornato dalla griglia
				JLayeredPane DISPLAYSTATMOSSA = creaPanel(0, 400, 700, 400);
				JLabel background=creaLabelImmagine(0, 0, 700, 400, "assets/Immagini/schermata stat mosse fight.png");
				DISPLAYSTATMOSSA.add(background, Integer.valueOf(0));
				Color bianco=new Color(255,255,255);
					
				//I vari label di display
				JLabel LabelNome = creaLabel("", 20, 90, 645, 40, DISPLAYSTATMOSSA,  StatFont, 2, bianco);
				JLabel LabelPotenza= creaLabel("", 20, 130, 645, 40, DISPLAYSTATMOSSA,  StatFont, 2, bianco);
				JLabel LabelPrecisione = creaLabel("", 20, 170, 645, 40, DISPLAYSTATMOSSA,  StatFont, 2, bianco);
				JLabel LabelPP = creaLabel("", 20, 210, 645, 40, DISPLAYSTATMOSSA,  StatFont, 2, bianco);
				JLabel LabelTipo = creaLabel("", 230, 90, 645, 40, DISPLAYSTATMOSSA,  StatFont, 2, bianco);
				JLabel LabelDescrizione = creaLabel("", 230, 130, 300, 100, DISPLAYSTATMOSSA,  StatFont, 2, bianco);

						
				//Creo le 4 MOSSE, SE cliccate ALLORA portano DISPLAYSTATMOSSA
				MyButton[] GridMosse = getButtonGrid(x0Gr, y0Gr+20, xGr, yGr, 4, POKEMONMOSSE); //manca mossa defult
				for (MyButton myButtonZ : GridMosse) 
					{
					//setto
					setBottoneMossa(myButtonZ, pokemondesignato, false);
					if (pokemondesignato.getMossa(myButtonZ.getContenuto())!=null) myButtonZ.setSpecialText(pokemondesignato.getMossa(myButtonZ.getContenuto()).getPP()+"/"+pokemondesignato.getMossa(myButtonZ.getContenuto()).getPPMax(), 120, 65);

					myButtonZ.addActionListener(d -> 
							{
								s.quickShoot("ButtonEffect");
								//Set dei vari display
								LabelNome.setText("Nome: "+pokemondesignato.getMossa(myButtonZ.getContenuto()).getNome());
								LabelPotenza.setText("Potenza: "+pokemondesignato.getMossa(myButtonZ.getContenuto()).getPotenza());
								LabelPrecisione.setText("Precisione: "+pokemondesignato.getMossa(myButtonZ.getContenuto()).getPrecisione());
								LabelPP.setText("PP: "+pokemondesignato.getMossa(myButtonZ.getContenuto()).getPP()+"/"+pokemondesignato.getMossa(myButtonZ.getContenuto()).getPPMax());
								String tipoM;
								if (pokemondesignato.getMossa(myButtonZ.getContenuto()).getTipo()!=0) 
									{
									tipoM=Tipi.values()[pokemondesignato.getMossa(myButtonZ.getContenuto()).getTipo()-1].toString();
									}
								else 
									{
									tipoM="";
									}
								LabelTipo.setText("Tipo: "+tipoM);
								LabelDescrizione.setText("<html>Descrizione: "+pokemondesignato.getMossa(myButtonZ.getContenuto()).getDescrizione()+"</html>");
								
								this.remove(POKEMONMOSSE);
								this.add(DISPLAYSTATMOSSA);
								this.repaint();
							});
					}

				//Per tornare alla schermata di ORIGINE da quella MOSSE
				MyButton BottoneBackToCambioPokemonFM = creaBottone("", 20, 383-20-50, 50, 50 , POKEMONMOSSE, "assets/Immagini/Pokeback.png", "assets/Immagini/PokebackScura.png", "assets/Immagini/F5050.png");
				BottoneBackToCambioPokemonFM.addActionListener(k -> 
					{	
						s.quickShoot("ButtonEffect");
						this.remove(POKEMONMOSSE);
						this.add(PannelloOrigine);
						this.repaint();
					});	
				
				//Per passare alla schermata MOSSE da ORIGINE
				bottoneOrigine.addActionListener(c -> 
					{	
						s.quickShoot("ButtonEffect");
						this.remove(PannelloOrigine);
						this.add(POKEMONMOSSE);
						this.repaint();
					});	
				
				//Bottone per tornare a MOSSE da DISPLAYSTATMOSSA
				MyButton BackToPokemonMosse = creaBottone("", 0, 330, 50, 50, DISPLAYSTATMOSSA, "assets/Immagini/Pokeback.png", "assets/Immagini/PokebackScura.png", "assets/Immagini/F5050.png");
				BackToPokemonMosse.addActionListener(n -> 
					{
						s.quickShoot("ButtonEffect");
						this.remove(DISPLAYSTATMOSSA);
						this.add(POKEMONMOSSE);
						this.repaint();
					});
					
			return POKEMONMOSSE;
	}
	
	/**
	 * Crea e restituisce un pannello JLayeredPane completo per visualizzare le statistiche di un Pokemon.
	 * Il pannello include un'immagine di sfondo e vari JLabel per visualizzare le statistiche del Pokemon.
	 * Inoltre, gestisce due bottoni: uno per visualizzare le statistiche e uno per tornare al pannello originale.
	 *
	 * @param pokemondesignato Il Pokemon di cui visualizzare le statistiche.
	 * @param PannelloOrigine Il pannello JLayeredPane di origine da cui è stato attivato il metodo.
	 * @param bottoneOrigine Il bottone MyButton di origine che ha attivato la visualizzazione delle statistiche.
	 * @param x0 La coordinata X iniziale per il pannello delle statistiche.
	 * @param y0 La coordinata Y iniziale per il pannello delle statistiche.
	 * @param x La larghezza del pannello delle statistiche.
	 * @param y L'altezza del pannello delle statistiche.
	 * @return Un pannello JLayeredPane contenente le statistiche del Pokemon e i relativi controlli.
	 */
	private JLayeredPane getStatFullSystem(Pokemon pokemondesignato, JLayeredPane PannelloOrigine, MyButton bottoneOrigine, int  x0, int  y0, int  x, int  y) 
	{
		//Parte legata alle stats, la roba indentata è roba sua
				JLayeredPane POKEMONSTATS = creaPanel(x0, y0, x, y) ;
				JLabel background=creaLabelImmagine(0, 0, 700, 400, "assets/Immagini/schermata stat pokemon fight.png");
				POKEMONSTATS .add(background, Integer.valueOf(0));
				Color bianco=new Color(255,255,255);
				
					//I vari label di display
					JLabel labelPS = creaLabel("",  20, 44, 250, 40,POKEMONSTATS, StatFont, 2, bianco);
					JLabel labelATK = creaLabel("",  20, 84, 250, 40,POKEMONSTATS, StatFont, 2, bianco);
					JLabel labelDIF = creaLabel("",  20, 124, 250, 40,POKEMONSTATS, StatFont, 2, bianco);
					JLabel labelATKSP = creaLabel("",  20, 164, 250, 40,POKEMONSTATS, StatFont, 2, bianco);
					JLabel labelDIFSP = creaLabel("",  20, 204, 250, 40,POKEMONSTATS, StatFont, 2, bianco);
					JLabel labelVEL = creaLabel("",  20, 244, 250, 40,POKEMONSTATS, StatFont, 2, bianco);
					JLabel labelLivello = creaLabel("",  200, 44, 250, 40,POKEMONSTATS, StatFont, 2, bianco);
					JLabel labelExp = creaLabel("",  200, 84, 250, 40,POKEMONSTATS, StatFont, 2, bianco);
					JLabel labelTipi = creaLabel("",  200, 124, 250, 40,POKEMONSTATS, StatFont, 2, bianco);

					//QUESTO E' IL BOTTONE CHE CONDUCE QUI
					
					bottoneOrigine.addActionListener(c -> 
						{
							s.quickShoot("ButtonEffect");
							//set dei vari label
							labelPS.setText("PS: "+pokemondesignato.getPs()+"/"+pokemondesignato.getPsMax());
							labelATK.setText("ATK: "+pokemondesignato.getAttacco());
							labelDIF.setText("DIF: "+pokemondesignato.getDifesa());
							labelATKSP.setText("ATKSP: "+pokemondesignato.getAttaccoSp());
							labelDIFSP.setText("DIFSP: "+pokemondesignato.getDifesaSp());
							labelVEL.setText("VEL: "+pokemondesignato.getVelocità());
							labelLivello.setText("Livello: "+pokemondesignato.getLivello());
							labelExp.setText("EXP: "+pokemondesignato.getPuntiExp()+"/"+(pokemondesignato.getLivello()+1)*(pokemondesignato.getLivello()+1)*(pokemondesignato.getLivello()+1));
							String tipo1;
							if (pokemondesignato.getTipo1()!=0) 
								{
								tipo1=Tipi.values()[pokemondesignato.getTipo1()-1].toString();
								}
							else 
								{
								tipo1="";
								}
							
							String tipo2;
							if (pokemondesignato.getTipo2()!=0) 
								{
								tipo2=Tipi.values()[pokemondesignato.getTipo2()-1].toString();
								}
							else 
								{
								tipo2="";
								}
							labelTipi.setText("Tipi: "+tipo1+" "+tipo2);
							
							this.remove(PannelloOrigine);
							this.add(POKEMONSTATS);
							this.repaint();
						});
					
					MyButton BottoneBackToCambioPokemon = creaBottone("", 20, 383-20-50, 50, 50 , POKEMONSTATS, "assets/Immagini/Pokeback.png", "assets/Immagini/PokebackScura.png", "assets/Immagini/F5050.png");
					BottoneBackToCambioPokemon.addActionListener(c -> 
						{	
							s.quickShoot("ButtonEffect");
							this.remove(POKEMONSTATS);
							this.add(PannelloOrigine);
							this.repaint();
						});
					
				return POKEMONSTATS;
	}
	
	/**
	 * Crea e restituisce un array di bottoni MyButton disposti in una linea.
	 * I bottoni sono posizionati lungo l'asse Y con la stessa coordinata X iniziale.
	 *
	 * @param x0 La coordinata X iniziale per il posizionamento dei bottoni.
	 * @param y0 La coordinata Y iniziale per il posizionamento del primo bottone.
	 * @param x La larghezza dei bottoni.
	 * @param y L'altezza dei bottoni.
	 * @param NumberOfButtons Il numero totale di bottoni da creare.
	 * @param target Il pannello JLayeredPane in cui aggiungere i bottoni.
	 * @return Un array di oggetti MyButton creati e posizionati secondo i parametri specificati.
	 */
	private MyButton[] getButtonLine(int x0,int y0,int x,int y, int NumberOfButtons, JLayeredPane target) 
	{
		MyButton[] gruppo =new MyButton[NumberOfButtons];
		
		
		for (int i=0;i < NumberOfButtons; i = i+1) 
		{
			gruppo[i] = creaBottone("", x0, y0+i*(y0+y), x, y, target);
			gruppo[i].setContenuto(i);
		}
		
		return gruppo;
	}
	
	/**
	 * Crea e restituisce un array di bottoni MyButton disposti in una griglia.
	 * Ogni coppia di bottoni condivide la stessa coordinata Y e è posizionata rispetto a X e Y iniziali.
	 *
	 * @param x0 La coordinata X iniziale per il posizionamento dei bottoni.
	 * @param y0 La coordinata Y iniziale per il posizionamento dei bottoni.
	 * @param x La larghezza dei bottoni.
	 * @param y L'altezza dei bottoni.
	 * @param NumberOfButtons Il numero totale di bottoni da creare (deve essere pari per una griglia corretta).
	 * @param target Il pannello JLayeredPane in cui aggiungere i bottoni.
	 * @return Un array di oggetti MyButton creati e posizionati secondo i parametri specificati.
	 */
	private MyButton[] getButtonGrid(int x0,int y0,int x,int y, int NumberOfButtons, JLayeredPane target) 
	{
		
		MyButton[] gruppo =new MyButton[NumberOfButtons];
		
		//(Reminder per il Multiplayer: i BOTTONI delle mosse sono legati SOLO alle X e Y dei bottoni dei pokemon)
		for (int i=0;i < NumberOfButtons; i = i+2) 
		{
			gruppo[i] = creaBottone("", x0, y0+i/2*(y0+y), x, y, target);
			gruppo[i+1] = creaBottone("", x0+x+x0, y0+i/2*(y0+y), x, y, target);
			gruppo[i].setContenuto(i);
			gruppo[i+1].setContenuto(i+1);
		}
		
		return gruppo;
	}
	
	/**
	 * Crea e restituisce un array di bottoni MyButton disposti in una griglia semplice.
	 * Ogni coppia di bottoni condivide la stessa coordinata Y e è posizionata rispetto a X e Y iniziali.
	 *
	 * @param x0 La coordinata X iniziale per il posizionamento dei bottoni.
	 * @param y0 La coordinata Y iniziale per il posizionamento dei bottoni.
	 * @param sx La distanza orizzontale tra due bottoni consecutivi.
	 * @param sy La distanza verticale tra due righe di bottoni consecutivi.
	 * @param NumberOfButtons Il numero totale di bottoni da creare (deve essere pari per una griglia corretta).
	 * @param target Il pannello JLayeredPane in cui aggiungere i bottoni.
	 * @return Un array di oggetti MyButton creati e posizionati secondo i parametri specificati.
	 */
	private MyButton[] getSimpleButtonGrid(int x0,int y0,int sx,int sy, int NumberOfButtons, JLayeredPane target) 
	{
		
		MyButton[] gruppo =new MyButton[NumberOfButtons];
		
		//(Reminder per il Multiplayer: i BOTTONI delle mosse sono legati SOLO alle X e Y dei bottoni dei pokemon)
		for (int i=0;i < NumberOfButtons; i = i+2) 
		{
			gruppo[i] = creaBottone("", x0, y0+i/2*(sy), 192, 75, target);
			gruppo[i+1] = creaBottone("", x0+sx, y0+i/2*(sy), 192, 75, target);
			gruppo[i].setContenuto(i);
			gruppo[i+1].setContenuto(i+1);
		}
		
		return gruppo;
	}
	
	/**
	 * Restituisce un oggetto Image caricato dal percorso specificato.
	 *
	 * @param path Il percorso del file immagine da caricare.
	 * @return L'oggetto Image caricato dal percorso specificato.
	 */
	private Image getImage(String path) {return new ImageIcon(path).getImage();}
	
	/**
	 * Cambia i pannelli all'interno di questo contenitore.
	 * Rimuove i pannelli originelow e originehigh dal contenitore corrente e aggiunge
	 * i pannelli targetlow e targethigh.
	 * Dopo l'operazione di modifica, richiama il metodo repaint per aggiornare
	 * l'interfaccia grafica di questo contenitore.
	 *
	 * @param originelow Il pannello da rimuovere come lower layer dal contenitore corrente.
	 * @param originehigh Il pannello da rimuovere come higher layer dal contenitore corrente.
	 * @param targetlow Il pannello da aggiungere come lower layer al contenitore corrente.
	 * @param targethigh Il pannello da aggiungere come higher layer al contenitore corrente.
	 */
	private void changePanel(JLayeredPane originelow, JLayeredPane originehigh, JLayeredPane targetlow, JLayeredPane targethigh) 
		{
		this.remove(originelow);
		this.remove(originehigh);
		this.add(targetlow);
		this.add(targethigh);
		this.repaint();
		}
	
	/**
	 * Cambia i pannelli all'interno di questo contenitore.
	 * Rimuove i pannelli originelow e originehigh dal contenitore corrente e aggiunge
	 * il pannello target.
	 * Dopo l'operazione di modifica, richiama il metodo repaint per aggiornare
	 * l'interfaccia grafica di questo contenitore.
	 *
	 * @param originelow Il pannello da rimuovere come lower layer dal contenitore corrente.
	 * @param originehigh Il pannello da rimuovere come higher layer dal contenitore corrente.
	 * @param target Il pannello da aggiungere al contenitore corrente (Generalmente un pannello più grande).
	 */
	private void changePanel(JLayeredPane originelow, JLayeredPane originehigh, JLayeredPane target) 
	{
	this.remove(originelow);
	this.remove(originehigh);
	this.add(target);
	this.repaint();
	}
	
	/**
	 * Cambia il pannello all'interno di questo contenitore.
	 * Rimuove il pannello origine dal contenitore corrente e aggiunge il pannello target.
	 * Dopo l'operazione di modifica, richiama il metodo repaint per aggiornare
	 * l'interfaccia grafica di questo contenitore.
	 *
	 * @param origine Il pannello da rimuovere dal contenitore corrente.
	 * @param target Il pannello da aggiungere al contenitore corrente.
	 */
	private void changePanel(JLayeredPane origine, JLayeredPane target) 
		{
		this.remove(origine);
		this.add(target);
		this.repaint();
		}
	
	/**
	 * Cambia il pannello all'interno di un pannello relativo.
	 * Rimuove il pannello origine dal pannellorelativo e aggiunge il pannello target
	 * con profondità di layer pari a 2.
	 * Dopo l'operazione di modifica, richiama il metodo repaint per aggiornare
	 * l'interfaccia grafica.
	 *
	 * @param origine Il pannello da rimuovere dal pannellorelativo.
	 * @param target Il pannello da aggiungere al pannellorelativo.
	 * @param pannellorelativo Il pannello relativo in cui effettuare i cambiamenti.
	 */
	private void relativeChangePanel(JLayeredPane origine, JLayeredPane target, JLayeredPane pannellorelativo) 
		{
		pannellorelativo.remove(origine);
		pannellorelativo.add(target,Integer.valueOf(2));
		this.repaint();
		}	
	
	/**
	 * Cambia i pannelli all'interno di un pannello relativo.
	 * Rimuove i pannelli originelow e originehigh dal pannellorelativo e aggiunge
	 * i pannelli targetlow e targethigh al pannellorelativo.
	 * Dopo l'operazione di modifica, richiama il metodo repaint per aggiornare
	 * l'interfaccia grafica.
	 *
	 * @param originelow Il pannello da rimuovere come lower layer.
	 * @param originehigh Il pannello da rimuovere come higher layer.
	 * @param targetlow Il pannello da aggiungere come lower layer.
	 * @param targethigh Il pannello da aggiungere come higher layer.
	 * @param pannellorelativo Il pannello relativo in cui effettuare i cambiamenti.
	 */
	private void relativeChangePanel(JLayeredPane originelow, JLayeredPane originehigh, JLayeredPane targetlow, JLayeredPane targethigh, JLayeredPane pannellorelativo) 
		{
		pannellorelativo.remove(originelow);
		pannellorelativo.remove(originehigh);
		pannellorelativo.add(targetlow);
		pannellorelativo.add(targethigh);
		this.repaint();
		}	
	
	
}


