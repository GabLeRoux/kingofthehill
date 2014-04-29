package kingofthehill;

import processing.core.PApplet;
import fisica.FCircle;

public class Archer extends Mob {
	public static int correctionX = 80;
	public static int correctionY = -18;
	public static float inpulse = 300.0f;
	public boolean isAlive = true;

	public Archer(PApplet parent) {
		super(parent);
		anim.loadPicturesFromStrings(new String[] { "data/archer/archer1.png",
				"data/archer/archer2.png", "data/archer/archer3.png",
				"data/archer/archer4.png", "data/archer/archer5.png",
				"data/archer/archer6.png" });
	}
}
