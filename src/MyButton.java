import java.awt.*;
import javax.swing.*;


/**
 * MyButton è una classe pensata per agevolare il display della gui quando si tratta
 * di bottoni ed elementi che dovrebbero esservici disegnati sopra.
 * La classe estende il JButton ed implementa nuovi metodi e campi specializzati principalmente
 * per migliorare l'esperienza grafica.
 * 
 * @authors  Matteo e Francesco, i Leggendari
 * @since   0.5
 */
@SuppressWarnings("serial")
public final class MyButton extends JButton {
    
	//Cose da Display
	private Image immagine;
    private String testo;
    private Color bianco = new Color(230, 230, 255);
    private Font font = new Font("Bahnschrift SemiBold", Font.PLAIN, 20);

    //Informazioni del Bottone
    private Image immagineSpeciale = null;
    private Image Focus = null;
    private int x0 = 0;
    private int y0 = 0;
    private int contenuto;
    private int x0ImgS = 0;
    private  int y0ImgS = 0;

    //Opzionale
    private Image[] immaginiSpeciali;
    private int specialX0;
    private int specialY0;
    private int specialx;
    private int specialy;
    private String testoSpeciale = "";
    private int x0TX = 0;
    private int y0TX = 0;

    /**
     * Overload del costruttore MyButton(String, String, int, int), setta i due int in automatico a 0.
     *
     * @param testo     Il testo da visualizzare sul bottone.
     * @param immagine  Il percorso dell'immagine da utilizzare per il bottone.
     */
    MyButton(String testo, String immagine) {
        this(testo, immagine, 0, 0);
    }

    /**
     * Costruisce un `MyButton` con il testo specificato, l'immagine e le coordinate iniziali.
     *
     * @param testo     Il testo da visualizzare sul bottone.
     * @param immagine  Il percorso dell'immagine da utilizzare per il bottone.
     * @param x0        Spiazzamento X della stringa
     * @param y0        Spiazzamento Y della stringa
     */
    MyButton(String testo, String immagine, int x0, int y0) {
        this.immagine = new ImageIcon(immagine).getImage();
        this.x0 = x0;
        this.y0 = y0;
        this.setText(testo);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusable(false);
    }

    /**
     * Setta lo Spiazzamento della Stringa.
     *
     * @param x0        Spiazzamento X della stringa
     * @param y0        Spiazzamento Y della stringa
     */
    public void setTextSpiazzamento(int x0, int y0) {
    	this.x0 = x0;
    	this.y0 = y0;
    	}
      
    /**
     * Setta lo il Testo displayato sul bottone.
     *
     * @param testo        
     */
    @Override
    public void setText(String testo) {
        this.testo = testo;
        this.repaint();
    }

    /**
     * Imposta il testo speciale e le sue coordinate iniziali.
     *
     * @param testoSpeciale Il testo speciale da visualizzare.
     * @param x0TX          La coordinata X iniziale del testo speciale.
     * @param y0TX          La coordinata Y iniziale del testo speciale.
     */
    public void setSpecialText(String testoSpeciale, int x0TX, int y0TX) {
        this.testoSpeciale = testoSpeciale;
        this.x0TX = x0TX;
        this.y0TX = y0TX;
    }

    /**
     * Imposta il Focus (quello che appare quando passi il cursore sopra)
     *
     * @param Focus
     */
    public void addFocus(Image Focus) {
    	this.Focus = Focus;
    	}
    
    /**
     * Rimuove il Focus
     */
    public void removeFocus() 
    	{
    	this.Focus = null;
    	}
    
    /**
     * Rimuove il Font
     */
    public void removeFont() 
		{
		this.font=new Font("Bahnschrift SemiBold", Font.PLAIN, 20);
		}
    
    /**
     * Imposta il Font del testo.
     * Il font di Default è "Bahnschrift SemiBold", Font.PLAIN, 20
     *
     * @param Font
     */
    public void setFont(Font font) 
    	{
    	this.font=font;
    	}
    
    /**
     * Ridisegna il bottone. Questo metodo viene chiamato ogni volta che il componente deve essere ridisegnato.
     * Oltre a disegnare i vari elementi grafici, posiziona centralmente il testo.
     * (E' un override del paint del JButton)
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g); // paint background
        Graphics2D g2D = (Graphics2D) g;

        //Sfondo Custom
        g2D.drawImage(immagine, 0, 0, null);

        //Immagine Speciale
        if (immagineSpeciale != null) {
            g2D.drawImage(immagineSpeciale, x0ImgS, y0ImgS, null);
        	}
        
        if (Focus != null) {
            g2D.drawImage(Focus, 0, 0, null);
        	}

        //Se c'è, ARRAY immagini speciali
        if (immaginiSpeciali != null) {
            int X0 = specialX0;
            int Y0 = specialY0;
            int x = specialx;
            int y = specialy;

            for (Image Poke : immaginiSpeciali) {
                g2D.drawImage(Poke, X0, Y0, null);
                X0 += x;
                Y0 += y;
	            }
	        }

        //Impostare il font e ottenere FontMetrics
        g2D.setFont(font);
        FontMetrics fm = g2D.getFontMetrics(font);
        
        //Calcolo la posizione necessaria per centrare la scritta nel bottone
        Insets insets = getInsets();
        int availableWidth = getWidth() - insets.left - insets.right;
        int availableHeight = getHeight() - insets.top - insets.bottom;
        int textWidth = fm.stringWidth(testo);
        int textHeight = fm.getHeight();
        int x = insets.left + (availableWidth - textWidth) / 2;
        int y = insets.top + (availableHeight - textHeight) / 2 + fm.getAscent();

        //Testo
        g2D.setColor(bianco);
        g2D.drawString(testo, x+x0, y+y0);

        if (!testoSpeciale.isEmpty()) {
            //Testo speciale
            Font specialFont = new Font("Bahnschrift SemiBold", Font.BOLD, 16);
            g2D.setFont(specialFont);
            g2D.setColor(bianco);
            g2D.drawString(testoSpeciale, x0TX, y0TX);
        }
    }

    /**
     * Assegna al bottone un intero da conservare, utile per bottoni dedicati a Mosse e squadra Pokemon
     * 
     * @param Contenuto
     */
    public void setContenuto(int contenuto) {
        this.contenuto = contenuto;
    }

    /**
     * Returna il contenuto
     */
    public int getContenuto() {
        return contenuto;
    }

    /**
     * Returna il testo
     */
    @Override
    public String getText() {
        return testo;
    }

    /**
     * Imposta l'icona speciale e le sue coordinate iniziali.
     *
     * @param immagineSpeciale L'immagine speciale da visualizzare.
     * @param x0               La coordinata X iniziale dell'immagine speciale.
     * @param y0               La coordinata Y iniziale dell'immagine speciale.
     */
    public void setSpecialIcon(Image immagineSpeciale, int x0, int y0) {
        this.immagineSpeciale = immagineSpeciale;
        x0ImgS = x0;
        y0ImgS = y0;
    }

    /**
     * Imposta un array di Icone speciali e le sue coordinate iniziali, oltre allo spazio che dovrebbe esserci tra le icone
     * (Usato per displayare squadra pokemon)
     *
     * @param immagineSpeciale L'immagine speciale da visualizzare.
     * @param x0               La coordinata X iniziale dell'immagine speciale.
     * @param y0               La coordinata Y iniziale dell'immagine speciale.
     * @param x                Spiazzamento X tra le varie icone dell'array.
     * @param y                Spiazzamento Y tra le varie icone dell'array.
     */
    public void setSpecialIcon(Image[] immaginiSpeciali, int x0, int y0, int x, int y) {
        this.immaginiSpeciali = immaginiSpeciali;
        specialX0 = x0;
        specialY0 = y0;
        specialx = x;
        specialy = y;
    }

    /**
     * Setta lo sfondo del MyButton
     *
     * @param immagine L'immagine di sfondo da visualizzare.
     */
    public void setSfondo(Image immagine) {
        this.immagine = immagine;
    }

    /**
     * Setta le coordinate iniziali del testo speciale
     *
     * @param x Coordinate iniziali del testo speciale
     * @param y Coordinate iniziali del testo speciale
     */
    public void setX_YImg(int x, int y) {
        x0ImgS = x;
        y0ImgS = y;
    }
}
