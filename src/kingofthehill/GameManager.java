package kingofthehill;

import java.util.ArrayList;

import processing.core.PApplet;
import fisica.FBox;
import fisica.FWorld;
import fisica.Fisica;

public class GameManager {

	// Reference vers processing
	public GameState currentState = GameState.MENU;
	PApplet parent;
	private FWorld world;

	// Le sol
	private FBox floor;
	private FBox leftCorner;
	private FBox rightCorner;

	// les ennemies
	private ArrayList<Archer> archers = new ArrayList<Archer>();
	private ArrayList<Dragon> dragons = new ArrayList<Dragon>();

	// Vous vous souvenez de pytagore?
	// a^2 + b^2 = c^2
	// racine(a^2 + b^2) = c
	// c = la hauteur des triangles du trapeze (le sol du jeu)
	private float cornerHeight = (float) (Math.sqrt(Math.pow(200.0f, 2)
			+ Math.pow(200.0f, 2)));
	private float floorWidth = 800.0f;
	private float floorHeight = 200.0f;
	public Player player;

	public int enemiesLeftToAdd = 15;
	public int enemiesLeftToKill = 15;

	// Constructeur
	GameManager(PApplet parent) {
		this.parent = parent;
		Fisica.init(parent);
		world = new FWorld();

		createFloor();
		createPlayer();

		world.add(player);
	}

	private void createPlayer() {
		player = new Player(parent);
		player.setPosition(parent.width / 2, parent.height - floorHeight
				- Player.playerSize);
	}

	private void createFloor() {
		floor = new FBox(floorWidth, floorHeight);
		floor.setPosition(parent.width / 2, parent.height - floorHeight / 2);
		floor.setStatic(true);
		floor.setFill(0, 100, 0);
		floor.setGrabbable(false);
		floor.setStroke(0);

		leftCorner = new FBox(cornerHeight, cornerHeight);
		leftCorner.setStatic(true);
		leftCorner.setRotation((float) Math.PI / 4);
		leftCorner.setPosition((parent.width - floorWidth) / 2, parent.height);
		leftCorner.setFill(0, 0, 0);
		leftCorner.setGrabbable(false);
		leftCorner.setStroke(0);

		rightCorner = new FBox(cornerHeight, cornerHeight);
		rightCorner.setStatic(true);
		rightCorner.setRotation((float) (Math.PI / 4));
		rightCorner.setPosition((parent.width - floorWidth) / 2 + floorWidth,
				parent.height);
		rightCorner.setFill(255, 255, 255);
		rightCorner.setGrabbable(false);
		rightCorner.setStroke(0);

		world.add(floor);
		world.add(leftCorner);
		world.add(rightCorner);
	}

	void worldDraw() {

		if (isOutOfBound(player.getX(), player.getY())) {
			this.currentState = GameState.GAMEOVER;
		} else {
			world.draw();
			player.anim.draw(player.getX() + Player.correctionX
					* ((player.anim.facingRight) ? 1 : -1), player.getY()
					+ Player.correctionY);
			
			// Archers
			for (Archer archer : archers) {
				if (archer.rightOf(player)
						&& Math.abs(archer.getVelocityX()) < Player.maxVelocity / 10.0f) {
					archer.addImpulse(-Archer.inpulse, 0.0f);
					archer.anim.facingRight = false;
				} else if (archer.leftOf(player)
						&& Math.abs(archer.getVelocityX()) < Player.maxVelocity / 10.0f) {
					archer.addImpulse(Archer.inpulse, 0.0f);
					archer.anim.facingRight = true;
				}
				archer.anim.draw(archer.getX() + Archer.correctionX * ((archer.anim.facingRight) ? 1 : -1),
						archer.getY() + Archer.correctionY);
				if (isOutOfBound(archer.getX(), archer.getY())
						&& archer.isAlive) {
					enemiesLeftToKill--;
					archer.isAlive = false;
				}
			}

			// Dragons
			for (Dragon dragon : dragons) {
				dragon.anim.draw(dragon.getX() + Dragon.correctionX,
						dragon.getY() + Dragon.correctionY);
			}

			world.step();
		}
	}

	private boolean isOutOfBound(float x, float y) {
		return (x > parent.width + player.anim.centerX * 2
				|| x < -player.anim.centerX * 2
				|| y > parent.height + player.anim.centerX * 2 || y < -player.anim.centerX * 2);
	}

	void addArcher() {
		try {
			Archer archer = new Archer(parent);

			archer.setPosition(
					(int) ((Math.random() * (parent.width - 0)) + 0),
					(int) parent.height - 200.0f - Archer.size);
			world.add(archer);
			archers.add(archer);
			enemiesLeftToAdd--;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void addDragon() {
		try {
			Dragon dragon = new Dragon(parent);

			dragon.setPosition(
					(int) ((Math.random() * (parent.width - 0)) + 0),
					(int) parent.height - 200.0f - Archer.size);
			dragon.setSize(0);
			world.add(dragon);
			dragons.add(dragon);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void reset() {
		try {
			this.archers.clear();
			this.dragons.clear();
			this.world.clear();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void die() {
		this.archers = null;
		this.dragons = null;
		this.world = null;
	}
}
