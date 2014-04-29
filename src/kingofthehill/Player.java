package kingofthehill;

import processing.core.PApplet;
import fisica.FCircle;

/**
 * classe principale du joueur
 */
public class Player extends FCircle {

	PicturesAnimator anim;
	PApplet parent;
	public static float maxVelocity = 10000.0f;
	public static float impulse = 5000.0f;
	public static float playerSize = 250.0f;
	public static int correctionX = 80;
	public static int correctionY = 15;

	/**
	 * Le joueur avec sa taille
	 * 
	 * @param parent
	 *            lien vers l'instance de l'applet processing
	 */
	public Player(PApplet parent) {
		super(playerSize);
		this.setNoFill();
		this.setNoStroke();

		anim = new PicturesAnimator(parent);
		// Les frames du joueur
		anim.loadPicturesFromStrings(new String[] { "data/roi/roi1.png",
				"data/roi/roi2.png", "data/roi/roi3.png", "data/roi/roi4.png",
				"data/roi/roi5.png", "data/roi/roi6.png", "data/roi/roi7.png" });
	}
}
