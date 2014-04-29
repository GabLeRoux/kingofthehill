package kingofthehill;

import processing.core.PApplet;
import fisica.FCircle;

public class Dragon extends Mob {
	public static int correctionX = 0;
	public static int correctionY = 44;

	public Dragon(PApplet parent) {
		super(parent);
		anim.loadPicturesFromStrings(new String[] { 
			"data/dragon/dragon1.png",
			"data/dragon/dragon2.png",
			"data/dragon/dragon3.png",
			"data/dragon/dragon4.png",
			"data/dragon/dragon5.png",
			"data/dragon/dragon6.png", 
		});
	}
}
