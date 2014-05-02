package kingofthehill;

import processing.core.PApplet;
import fisica.FCircle;

public class Mob extends FCircle {

	PicturesAnimator anim;
	PApplet parent;
	public static float maxVelocity = 10000.0f;
	public static float impulse = 5000.0f;
	public static float size = 130.0f;

	/**
	 * Constructeur de mob (mechant)
	 * @param parent lien vers processing
	 */
	public Mob(PApplet parent) {
		super(size);
		this.parent = parent;
		anim = new PicturesAnimator(parent);
		this.setNoFill();
		this.setNoStroke();
	}

	public boolean leftOf(fisica.FBody body) {
		return (this.getX() < body.getX());
	}

	public boolean rightOf(fisica.FBody body) {
		return (this.getX() > body.getX());
	}
}
