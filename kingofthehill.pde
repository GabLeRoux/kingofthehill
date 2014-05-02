/***
 * King of the Hill (Roi de la montagne)
 * Realise par Marianne Parent, Eric Bergeron et Gabriel Le Breton
 * Dans le cadre du cours Creation d'images numeriques
 * Juillet 2014
 * 
 * Afin d'utiliser ce code, il est recommande d'installer Proclipsing
 * https://code.google.com/p/proclipsing/
 */

import fisica.util.nonconvex.*;
import fisica.*;

import java.util.Timer;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import controlP5.Button;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Controller;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

/**
 * Classe principale de l'application. On herite de PApplet afin de pouvoir
 * executer les methodes de base de Processing
*/
// Images de fond
PImage picture_background_menu;
PImage picture_background_ingame;
PImage picture_background_win;
PImage picture_background_loose;

PImage picture_ground;
PImage picture_grass;

Button buttonPlay;
PImage button_start_default;
PImage button_start_hover;
PImage button_start_pressed;

Button buttonReplay;
PImage button_restart_default;
PImage button_restart_hover;
PImage button_restart_pressed;

Button buttonMainmenu;
PImage button_mainmenu_default;
PImage button_mainmenu_hover;
PImage button_mainmenu_pressed;

// AnimatedClouds animatedClouds;

// Configuration des sons
Minim minim;
AudioPlayer audio_main_theme;
AudioPlayer audio_sword;

String main_theme_path = "data/roi_montagne.mp3";
String sword_sound_path = "data/sword.mp3";

// Police d'ecriture
PFont f;

// timer setup
public Timer timer;

// Librairie de d'outils visuels pour Processing (boutons, sliders, etc)
ControlP5 controlP5;

GameManager gameManager;

// Methode appele qu'une seule fois
// On initalise les librairies, on charge les sons, images, etc.
@Override
public void setup() {
	initializeMainLibs();
	loadMenuButtonsPictures();
	size(1024, 768);
	noStroke();
	smooth();
	f = createFont("Augusta", 36);

	loadBackgroundPictures();

	audio_main_theme = minim.loadFile(main_theme_path);
	audio_main_theme.loop();

	gameManager = new GameManager(this);

	// uncomment one of these lines to debug ;)
	// gameManager.currentState = GameState.LOADGAME;
	// gameManager.currentState = GameState.GAMEWIN;
	// gameManager.currentState = GameState.GAMEOVER;
}

// boucle de jeu, affichage selon l'etat actuel
@Override
public void draw() {
	switch (gameManager.currentState) {
	case MENU:
		menu();
		break;
	case MENULOADED:
		menuLoaded();
		break;
	case LOADGAME:
		loadgame();
		break;
	case INGAME:
		ingame();
		break;
	case GAMEWIN:
		gamewin();
		break;
	case GAMEOVER:
		gameover();
		break;
	default:
		break;
	}
}

void menuLoaded() {
}

void initializeMainLibs() {
	controlP5 = new ControlP5(this);
	minim = new Minim(this);
}

void loadMenuButtonsPictures() {
	// chargement des images de boutons
	button_start_default = loadImage("data/button/demarrer_default.png");
	button_start_hover = loadImage("data/button/demarrer_hover.png");
	button_start_pressed = loadImage("data/button/demarrer_pressed.png");

	button_restart_default = loadImage("data/button/recommencer_default.png");
	button_restart_hover = loadImage("data/button/recommencer_hover.png");
	button_restart_pressed = loadImage("data/button/recommencer_pressed.png");

	button_mainmenu_default = loadImage("data/button/menu_default.png");
	button_mainmenu_hover = loadImage("data/button/menu_hover.png");
	button_mainmenu_pressed = loadImage("data/button/menu_pressed.png");
}

void loadBackgroundPictures() {
	// chargement des images de boutons
	picture_background_loose = loadImage("data/background/background_loose.jpg");
	picture_background_ingame = loadImage("data/background/background.jpg");
	picture_background_win = loadImage("data/background/background_win.jpg");
	picture_background_menu = loadImage("data/background/background_menu.jpg");

	picture_grass = loadImage("data/ground/grass.png");
	picture_ground = loadImage("data/ground/ground.png");
}

void loadgame() {
	removeMenuButtons();
	gameManager.reset();
	gameManager.currentState = GameState.INGAME;
}

void ingame() {
	background(picture_background_ingame);
	image(picture_ground, 0, 0);

	if (frameCount % 100 == 0 && gameManager.enemiesLeftToAdd > 0)
		gameManager.addArcher();
	else if (gameManager.enemiesLeftToKill <= 0)
		gameManager.currentState = GameState.GAMEWIN;

	gameManager.worldDraw();
	image(picture_grass, 0, 0);
}

void menu() {
	// On dessine le boutton au centre de la fenetre
	background(picture_background_menu);
	if (buttonPlay == null)
		buttonPlay = controlP5.addButton("buttonPlay").setValue(0)
				.setPosition((1024 - button_start_default.width) / 2, 523)
				.setSize(button_start_default)
				.setImage(button_start_default, Controller.DEFAULT)
				.setImage(button_start_hover, Controller.ACTIVE)
				.setImage(button_start_pressed, Controller.ACTION_PRESSED);

	removeMenuButtons();
	buttonPlay.show();

	gameManager.currentState = GameState.MENULOADED;
}

void gameover() {
	background(picture_background_loose);
	if (buttonReplay == null)
		buttonReplay = controlP5
				.addButton("buttonReplay")
				.setValue(0)
				.setPosition(130, 523)
				.setSize(button_restart_default)
				.setImage(button_restart_default, Controller.DEFAULT)
				.setImage(button_restart_hover, Controller.ACTIVE)
				.setImage(button_restart_pressed, Controller.ACTION_PRESSED);

	if (buttonMainmenu == null)
		buttonMainmenu = controlP5
				.addButton("buttonMainmenu")
				.setValue(0)
				.setPosition(130 + 418, 523)
				.setSize(button_mainmenu_default)
				.setImage(button_mainmenu_default, Controller.DEFAULT)
				.setImage(button_mainmenu_hover, Controller.ACTIVE)
				.setImage(button_mainmenu_pressed,
						Controller.ACTION_PRESSED);

	removeMenuButtons();
	buttonReplay.show();
	buttonMainmenu.show();

	gameManager.currentState = GameState.MENULOADED;
}

void gamewin() {
	background(picture_background_win);
}

public void imageCenter(PImage pic, int screen_width, int screen_height) {
	image(pic, (screen_width - pic.width) / 2,
			(screen_height - pic.height) / 2);
}

// valeur par defaut
public void imageCenter(PImage pic) {
	imageCenter(pic, 1024, 768);
}

void removeMenuButtons() {
	try {
		if (buttonPlay != null)
			if (buttonPlay.isVisible())
				buttonPlay.hide();
		if (buttonReplay != null)
			if (buttonReplay.isVisible())
				buttonReplay.hide();
		if (buttonMainmenu != null)
			if (buttonMainmenu.isVisible())
				buttonMainmenu.hide();
	} catch (Exception e) {
		e.printStackTrace();
	}
}

@Override
public void keyPressed() {

	if (gameManager.currentState == GameState.INGAME) {
		if (key == CODED) {
			if (keyCode == LEFT) {
				// On tourne a gauche
				if (Math.abs(gameManager.player.getVelocityX()) < Player.maxVelocity) {
					gameManager.player.addImpulse(-Player.impulse, 0.0f);
					gameManager.player.anim.facingRight = false;
				}
			} else if (keyCode == RIGHT) {
				// On tourne a droite
				if (Math.abs(gameManager.player.getVelocityX()) < Player.maxVelocity) {
					gameManager.player.addImpulse(Player.impulse, 0.0f);
					gameManager.player.anim.facingRight = true;
				}
			} else if (keyCode == UP) {
				// Sauter! (ne fonctionne qu'a partir du sol ;)
				if (gameManager.player.isTouchingBody(gameManager.floor)
						|| gameManager.player
								.isTouchingArchers(gameManager.archers))
					gameManager.player.addImpulse(0.0f,
							-Player.maxJumpVelocity);
			} else if (keyCode == DOWN) {
				// forcer vers le bas! :) (quand on touche des archers et
				// qu'on ne touche pas au sol
				if (gameManager.player
						.isTouchingArchers(gameManager.archers)
						&& !gameManager.player
								.isTouchingBody(gameManager.floor))
					gameManager.player.addImpulse(0.0f,
							Player.maxDownPushVelocity);
			}
		}
	}
}

public void controlEvent(ControlEvent theEvent) {
	playSword();
	println(theEvent.getController().getName());
}

public void buttonPlay(int value) {
	this.buttonPlay.hide();
	this.buttonReplay(0);
}

public void buttonReplay(int value) {
	this.gameManager.GC();
	this.gameManager = null;
	this.gameManager = new GameManager(this);
	this.gameManager.currentState = GameState.LOADGAME;
	this.buttonReplay.hide();
	this.buttonMainmenu.hide();
}

public void buttonMainmenu(int value) {
	gameManager.currentState = GameState.MENU;
	this.buttonReplay.hide();
	this.buttonMainmenu.hide();
}

void playSword() {
	audio_sword = minim.loadFile(sword_sound_path);
	audio_sword.play();
}
