import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PShape;
import fisica.FBox;
import fisica.FPoly;
import fisica.FWorld;
import fisica.Fisica;

public class GameManager {

	// Reference vers processing
	public GameState currentState = GameState.MENU;
	PApplet parent;
	private FWorld world;

	// Le sol
	FPoly floor;
	private float floorHeight = 170.0f;

	// les ennemies
	public ArrayList<Archer> archers = new ArrayList<Archer>();
	public Dragon dragon;
	public Player player;

	public int enemiesLeftToAdd = 15;
	public int enemiesLeftToKill = 15;

	/**
	 * Constructeur pour le systeme de gestion du jeu
	 * 
	 * @param parent
	 *            lien vers processing
	 */
	GameManager(PApplet parent) {
		this.parent = parent;
		Fisica.init(parent);
		world = new FWorld();

		createFloor();
		createDragon();
		createPlayer();

		world.add(player);
	}

	/**
	 * Creation du joueur
	 */
	private void createPlayer() {
		player = new Player(parent);
		player.setPosition(parent.width / 2, parent.height - floorHeight
				- Player.playerSize);
	}

	/*
	 * ^\ ^ / \\ / \ /. \\/ \ |\___/|----* / / | \\ \ __/ O O\ | / / / | \\ \_\/
	 * \ \ / /\/ / / | \\ _\/ '@___@ / / / / | \\ _\/ |U | | / / | \\\/ | \ | /_
	 * / | \\ ) \ _|_ \ \ ~-./_ _ | .- ; ( \_ _ _,\' ~ ~. .-~-.|.-* _ {-, \ ~-.
	 * _ .-~ \ /\' \ } { .* ~. '-/ /.-~----. ~- _ / >..----.\\\ ~ - - - - ^}_ _
	 * _ _ _ _ _.-\\\
	 */
	private void createDragon() {
		try {
			this.dragon = new Dragon(parent);

			dragon.setPosition(
					(int) ((Math.random() * (parent.width - 0)) + 0),
					(int) parent.height - floorHeight - Dragon.size);
			dragon.setSize(0);
			world.add(dragon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode pour creer le sol du jeu et ses vertex (a l'aide d'un svg!)
	 */
	private void createFloor() {
		floor = new FPoly();
		floor.setStatic(true);
		floor.setGrabbable(false);
		floor.setNoStroke();
		floor.setNoFill();

		// On dessine les vertex depuis un fichier svg (parce que oui, on est
		// hot comme ca)
		PShape floor_svg = parent.loadShape("data/floor.svg").getChildren()[0];

		for (int i = 0; i < floor_svg.getVertexCount(); i++) {
			floor.vertex(floor_svg.getVertexX(i), floor_svg.getVertexY(i));
		}
		world.add(floor);
	}

	/**
	 * Methode qui dessine le monde (sol,
	 */
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
						&& Math.abs(archer.getVelocityX()) < Player.maxVelocity / 20.0f) {
					archer.addImpulse(-Archer.inpulse, 0.0f);
					archer.anim.facingRight = false;
				} else if (archer.leftOf(player)
						&& Math.abs(archer.getVelocityX()) < Player.maxVelocity / 20.0f) {
					archer.addImpulse(Archer.inpulse, 0.0f);
					archer.anim.facingRight = true;
				}
				archer.anim.draw(archer.getX() + Archer.correctionX
						* ((archer.anim.facingRight) ? 1 : -1), archer.getY()
						+ Archer.correctionY);
				if (isOutOfBound(archer.getX(), archer.getY())
						&& archer.isAlive) {
					enemiesLeftToKill--;
					archer.isAlive = false;
				}
			}

			// Dragon
			dragon.animate();

			world.step();
		}
	}

	/**
	 * Vérifie si la coordonnée est à l'exétirieur de l'écran (avec une marge
	 * d'erreur)
	 * 
	 * @param x
	 * @param y
	 * @return vrai si hors de l'écran, faux sinon
	 */
	private boolean isOutOfBound(float x, float y) {
		return (x > parent.width + player.anim.centerX * 2
				|| x < -player.anim.centerX * 2
				|| y > parent.height + player.anim.centerX * 2 || y < -player.anim.centerX * 2);
	}

	/**
	 * Ajoute un archer dans le monde
	 */
	void addArcher() {
		try {
			Archer archer = new Archer(parent);

			archer.setPosition(
					(int) ((Math.random() * (parent.width - 0)) + 0),
					(int) parent.height - floorHeight - Archer.size);
			world.add(archer);
			archers.add(archer);
			enemiesLeftToAdd--;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * vide la liste de mobs et vide le monde
	 */
	public void reset() {
		try {
			this.archers.clear();
			this.world.clear();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * On supprime les listes (garbage collector si on veut)
	 */
	public void GC() {
		this.archers = null;
		this.dragon = null;
		this.player = null;
		this.world = null;
	}
}
