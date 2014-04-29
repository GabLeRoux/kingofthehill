package kingofthehill;

import processing.core.PApplet;
import processing.core.PImage;

public class AnimatedClouds {
	int x;
	int y;
	float sizeW;
	float sizeH;
	int speed;
	PImage nuage;
	PApplet parent;

	// Constructeur
	AnimatedClouds(PImage _nuage, int _x, int _y, int _size, int _speed) {
		x = _x;
		y = _y;
		sizeH = _size;
		sizeW = (_size / (float) (_nuage.height)) * _nuage.width;
		speed = _speed;
		nuage = _nuage;
	}

	// Affichage
	void drawCloud(PApplet p) {
		p.image(nuage, x, y, sizeW, sizeH);
	}

	// Mouvement
	void calculatePosition(int screenWidth) {

		x = x + speed;

		if (x > screenWidth) {
			x = (int) (0 - sizeW);
			y = (int) Math.random() * 200;
		}
	}
}
