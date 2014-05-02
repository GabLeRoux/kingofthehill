package kingofthehill;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class PicturesAnimator {
	public ArrayList<PImage> pictures;
	PApplet parent;
	public int index = 0;
	public boolean reverseLoop = true;
	public boolean hasReachedEndOfLoop = false;
	public boolean paused = false;
	public float centerX;
	public float centerY;
	public int frameSkip = 3;
	public int frame = 1;
	public boolean facingRight = false;

	/**
	 * Constructeur depuis processing à l'aide d'images
	 * 
	 * @param parent   Lien vers Processing
	 * @param pictures Tableau d'images
	 */
	public PicturesAnimator(PApplet parent, ArrayList<PImage> pictures) {
		this.parent = parent;
		this.pictures = pictures;
		calculateCenter();
	}

	/**
	 * Constructeur depuis processing sans l'initialisation depuis un
	 * tableau d'images
	 * 
	 * @param parent Lien vers Processing
	 */
	public PicturesAnimator(PApplet parent) {
		this.parent = parent;
	}

	/**
	 * Cré un tableau d'images depuis un tableau de chemin vers les images
	 * 
	 * @param pictures
	 */
	public void loadPicturesFromStrings(String[] pictures) {
		this.pictures = new ArrayList<PImage>();
		for (String path : pictures) {
			this.pictures.add(parent.loadImage(path));
		}
		calculateCenter();
	}
	
	/**
	 * Trouve le centre de la premiere image du tableau
	 */
	private void calculateCenter() {
		this.centerX = this.pictures.get(0).width / 2;
		this.centerY = this.pictures.get(0).height / 2;
	}

	/**
	 * Dessine l'image courante de l'animation en cours dans processing
	 * Toute la magie est ici, mais c'est complexe 
	 * 
	 * @param x
	 * @param y
	 */
	public void draw(float x, float y) {
		if (facingRight) {
			parent.pushMatrix();
			parent.scale(-1.0f, 1.0f);
			parent.image(pictures.get(index), -x - centerX, y - centerY);
			parent.popMatrix();
		} else {
			parent.image(pictures.get(index), x - centerX, y - centerY);
		}

		if (canDraw()) {
			frame++;
		} else {
			frame = 1;
			if (!paused) {
				if (reverseLoop && hasReachedEndOfLoop && index != 0)
					index--;
				else if (!reverseLoop && hasReachedEndOfLoop) {
					index = 0;
				} else if (index == 0) {
					index++;
					hasReachedEndOfLoop = false;
				} else if (index >= pictures.size() - 1) {
					hasReachedEndOfLoop = true;
					index--;
				} else if (index < pictures.size() - 1
						&& !hasReachedEndOfLoop) {
					index++;
				}
			}
		}
	}
	
	public boolean canDraw() {
		return frame <= frameSkip;
	}
}
