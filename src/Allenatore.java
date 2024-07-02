import javax.swing.ImageIcon;
import java.io.Serializable;

/**
 * Classe che rappresenta un Allenatore nel gioco, contiene al suo interno la squadra e le proprie stats.
 * 
 * 
 * @authors  Matteo e Francesco, i Leggendari
 * @since   0.4
 */
public class Allenatore implements Serializable {

    //info
    private String nome;
    private ImageIcon PFGrande;
    private ImageIcon PFPiccola;

    //scoreboard shit
    private int vittorie = 0;
    private int sconfitte = 0;
    private int pokemonuccisi = 0; //(Malvagio.)
    private int pokemonpersi = 0; //if 0 then I AM MALENIA

    //Lista pokemon
    private Pokemon[] listapokemon = new Pokemon[6];

    /**
     * Costruttore dell'allenatore che inizializza la squadra di Pokemon e altre informazioni.
     *
     * @param squadra   La squadra di Pokemon dell'allenatore.
     * @param nome      Il nome dell'allenatore.
     * @param PFGrande  L'icona grande dell'allenatore.
     * @param PFPiccola L'icona piccola dell'allenatore.
     */
    Allenatore(Pokemon[] squadra, String nome, ImageIcon PFGrande, ImageIcon PFPiccola) {

        this.nome = nome;
        this.PFGrande = PFGrande;
        this.PFPiccola = PFPiccola;
        for (int i = 0; i < 6; i++) {
            try {
                listapokemon[i] = squadra[i];
            } catch (Exception e) {
            } //gli puoi feedare un array sia più grande che più piccolo, sempre 6 max ne prende senza errori
        }
    }

    /**
     * Restituisce le statistiche dell'allenatore.
     *
     * @return Un array di interi contenente le vittorie, le sconfitte, i Pokemon uccisi e i Pokemon persi.
     */
    public int[] getStats() {
        return new int[]{this.vittorie, this.sconfitte, this.pokemonuccisi, this.pokemonpersi};
    }

    /**
     * Restituisce il nome dell'allenatore.
     *
     * @return Il nome dell'allenatore.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce le icone dell'allenatore.
     *
     * @return Un array di ImageIcon contenente l'icona grande e l'icona piccola dell'allenatore.
     */
    public ImageIcon[] getImageIcon() {
        return new ImageIcon[]{PFGrande, PFPiccola};
    }

    /**
     * Aggiunge le statistiche dell'allenatore.
     *
     * @param incrementoVittorie      Il numero di vittorie da aggiungere.
     * @param incrementoSconfitte     Il numero di sconfitte da aggiungere.
     * @param incrementoPokemonUccisi Il numero di Pokemon uccisi da aggiungere.
     * @param incrementoPokemonMorti  Il numero di Pokemon persi da aggiungere.
     */
    public void addStats(int incrementoVittorie, int incrementoSconfitte, int incrementoPokemonUccisi, int incrementoPokemonMorti) {
        this.vittorie += incrementoVittorie;
        this.sconfitte += incrementoSconfitte;
        this.pokemonuccisi += incrementoPokemonUccisi;
        this.pokemonpersi += incrementoPokemonMorti;
    }

    /**
     * Restituisce il numero di Pokemon rimasti in squadra.
     *
     * @return Il numero di Pokemon vivi in squadra.
     */
    public int getPokemonRimasti() {
        int returnz = 0;
        for (int i = 0; i < 6; i++) {
            if (listapokemon[i] != null && !(getPokemonPosizione(i).getPs() <= 0)) {
                returnz++;
            }
        }
        return returnz;
    }

    /**
     * Restituisce il Pokemon alla posizione specificata.
     *
     * @param posiz La posizione del Pokemon nella squadra (da 0 a 5).
     * @return Il Pokemon alla posizione specificata.
     */
    public Pokemon getPokemonPosizione(int posiz) {
        return listapokemon[posiz];
    }

    /**
     * Restituisce il numero totale di Pokemon in squadra.
     *
     * @return Il numero totale di Pokemon presenti in squadra.
     */
    public int getPokemonTotali() {
        int returnz = 0;
        for (int i = 0; i < 6; i++) {
            if (listapokemon[i] != null) {
                returnz++;
            }
        }
        return returnz;
    }

    /**
     * Restituisce la lista di Pokemon dell'allenatore.
     *
     * @return Un array di Pokemon rappresentante la squadra dell'allenatore.
     */
    public Pokemon[] getPokemonLista() {
        return listapokemon;
    }

}
