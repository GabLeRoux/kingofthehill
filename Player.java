import java.util.ArrayList;

import processing.core.PApplet;
import fisica.FBody;
import fisica.FCircle;

/**
 * classe principale du joueur
 */
public class Player extends FCircle {

	PicturesAnimator anim;
	PApplet parent;
	public static float maxVelocity = 15000.0f;
	public static float impulse = 5000.0f;
	public static float playerSize = 250.0f;
	public static int correctionX = 80;
	public static int correctionY = 15;
	public static float maxJumpVelocity = 50000.0f;
	public static float maxDownPushVelocity = 50000.0f;

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

	public boolean isTouchingArchers(ArrayList<Archer> archers) {
		for (FBody archer : archers) {
			if (this.isTouchingBody(archer)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isTouchingDragons(ArrayList<Dragon> dragons) {
		for (FBody archer : dragons) {
			if (this.isTouchingBody(archer)) {
				return true;
			}
		}
		return false;
	}
}
