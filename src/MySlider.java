import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

/**
 * Una classe personalizzata che estende JSlider per creare uno slider con estetica migliorata.
 * Questa classe è stata importata da sorgenti esterne ed adattata allo scopo necessario del programma
 * solo per motivi estetici.
 * 
 * @authors  Matteo e Francesco, i Leggendari (E le leggende che hanno costribuito al post su StackOverflow)
 * @since   0.26
 */
@SuppressWarnings("serial")
public final class MySlider extends JSlider{
	
		public MySlider() {}

		/**
	     * Costruttore che inizializza lo slider con parametri specifici.
	     *
	     * @param verticale Indica l'orientamento dello slider (JSlider.VERTICAL o JSlider.HORIZONTAL).
	     * @param inizio    Il valore minimo dello slider.
	     * @param fine      Il valore massimo dello slider.
	     * @param start     Il valore iniziale dello slider.
	     */
		public MySlider(int verticale, int inizio, int fine, int start){
			super(verticale,inizio,fine,start);
			}
		
		/**
	     * Override del metodo updateUI per impostare una UI personalizzata.
	     * La UI personalizzata è la parte ispirata. Pertanto il javadoc non tratterà questa classe interna.
	     */
		@Override
        public void updateUI() {
            setUI(new CustomSliderUI(this));
			}
		
	    private static class CustomSliderUI extends BasicSliderUI {

	    	private static final Color ColoreCursore = new Color(76, 153, 0);
	    	private static final Color ColoreVuoto = new Color(100, 0 , 0);
	    	private static final Color ColorePieno = new Color(49, 57 ,66);
	    	
	    	
	    	
	        private static final int TRACK_HEIGHT = 8;
	        private static final int TRACK_WIDTH = 8;
	        private static final int TRACK_ARC = 5;
	        private static final Dimension THUMB_SIZE = new Dimension(20, 20);
	        private final RoundRectangle2D.Float trackShape = new RoundRectangle2D.Float();

	        public CustomSliderUI(final JSlider b) {
	            super(b);
	        }

	        @Override
	        protected void calculateTrackRect() {
	            super.calculateTrackRect();
	            if (isHorizontal()) {
	                trackRect.y = trackRect.y + (trackRect.height - TRACK_HEIGHT) / 2;
	                trackRect.height = TRACK_HEIGHT;
	            } else {
	                trackRect.x = trackRect.x + (trackRect.width - TRACK_WIDTH) / 2;
	                trackRect.width = TRACK_WIDTH;
	            }
	            trackShape.setRoundRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height, TRACK_ARC, TRACK_ARC);
	        }

	        @Override
	        protected void calculateThumbLocation() {
	            super.calculateThumbLocation();
	            if (isHorizontal()) {
	                thumbRect.y = trackRect.y + (trackRect.height - thumbRect.height) / 2;
	            } else {
	                thumbRect.x = trackRect.x + (trackRect.width - thumbRect.width) / 2;
	            }
	        }

	        @Override
	        protected Dimension getThumbSize() {
	            return THUMB_SIZE;
	        }

	        private boolean isHorizontal() {
	            return slider.getOrientation() == JSlider.HORIZONTAL;
	        }

	        @Override
	        public void paint(final Graphics g, final JComponent c) {
	            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            super.paint(g, c);
	        }

	        @Override
	        public void paintTrack(final Graphics g) {
	            Graphics2D g2 = (Graphics2D) g;
	            Shape clip = g2.getClip();

	            boolean horizontal = isHorizontal();
	            boolean inverted = slider.getInverted();

	            // Paint shadow.
	            g2.setColor(new Color(170, 170 ,170));
	            g2.fill(trackShape);

	            // Paint track background.
	            g2.setColor(ColoreVuoto);
	            g2.setClip(trackShape);
	            trackShape.y += 1;
	            g2.fill(trackShape);
	            trackShape.y = trackRect.y;

	            g2.setClip(clip);

	            // Paint selected track.
	            if (horizontal) {
	                boolean ltr = slider.getComponentOrientation().isLeftToRight();
	                if (ltr) inverted = !inverted;
	                int thumbPos = thumbRect.x + thumbRect.width / 2;
	                if (inverted) {
	                    g2.clipRect(0, 0, thumbPos, slider.getHeight());
	                } else {
	                    g2.clipRect(thumbPos, 0, slider.getWidth() - thumbPos, slider.getHeight());
	                }

	            } else {
	                int thumbPos = thumbRect.y + thumbRect.height / 2;
	                if (inverted) {
	                    g2.clipRect(0, 0, slider.getHeight(), thumbPos);
	                } else {
	                    g2.clipRect(0, thumbPos, slider.getWidth(), slider.getHeight() - thumbPos);
	                }
	            }
	            g2.setColor(ColorePieno);
	            g2.fill(trackShape);
	            g2.setClip(clip);
	        }

	        @Override
	        public void paintThumb(final Graphics g) {
	            g.setColor(ColoreCursore);
	            g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
	        }

	        @Override
	        public void paintFocus(final Graphics g) {}
	    }
	}
	

