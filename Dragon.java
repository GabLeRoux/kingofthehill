import processing.core.PApplet;
public class Dragon extends Mob {
	public static int correctionX = 0;
	public static int correctionY = 44;
	private float amplitude = 75.0f; // Height of wave

	private float x;
	private float y;

	/**
	 * Constructeur de dragon
	 * 
	 * @param parent
	 */
	public Dragon(PApplet parent) {
		super(parent);
		anim.loadPicturesFromStrings(new String[] { "data/dragon/dragon1.png",
				"data/dragon/dragon2.png", "data/dragon/dragon3.png",
				"data/dragon/dragon4.png", "data/dragon/dragon5.png",
				"data/dragon/dragon6.png", });
	}

	public void animate() {

		if (this.x < 0) {
			this.anim.facingRight = true;
		}
		if (this.x > parent.width) {
			this.anim.facingRight = false;
		}

		if (this.anim.facingRight && anim.canDraw())
			x++;
		if (!this.anim.facingRight && anim.canDraw())
			x--;
		
		this.y = ((float)Math.sin(x * 0.02f) * 15.0f) + anim.centerY;
		anim.draw(x, y);
		this.setPosition(x, y);
	}
}
