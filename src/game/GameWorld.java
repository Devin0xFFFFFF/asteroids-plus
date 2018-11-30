package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class GameWorld extends World
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final int EXPIX = 300, ENPIX = 200, PIXELS = EXPIX + ENPIX;
	public Pixel[] pixels = new Pixel[PIXELS];
	
	public Ship ship;
	public Text score, lives, info, title;
	public int points, health;
	private int spawn, SPAWN_RATE, amount;
	
	public boolean GAME_OVER, FIRST_LAUNCH;
	
	public GameGraphic message, load, start, restart;
	
	public void loadWorld()
	{
		GAME_OVER = true; FIRST_LAUNCH = true;
		setBackground(drawBackground());
		renderMessages();
		addScoreboards();
	}
	
	public void loadLevel()
	{
		ship = new Ship();
		addActor(ship,WIDTH/2,HEIGHT/2);
		
		points = 0; health = 5;
		spawn = 0; SPAWN_RATE = 150;
		amount = 3;
	}
	
	public void loadResources()
	{
		System.out.println("Preloading Game...");
		
		System.out.println("Preloading Pixels...");
		for(int i = 0;i<EXPIX;i++)
		{
			pixels[i] = new Pixel();
			addActor(pixels[i]);
		}
		for(int i = EXPIX;i<PIXELS;i++)
		{
			pixels[i] = new Pixel();
			pixels[i].style(3, 3, Color.red);
			addActor(pixels[i],-10,0);
		}
		
		
		System.out.println("Preloading Sounds...");
		sounds.add(new GameSound("shoot.aiff"));
		sounds.add(new GameSound("level_up.wav"));
		sounds.add(new GameSound("hit.wav"));
		sounds.add(new GameSound("boom_1.aiff"));
		sounds.add(new GameSound("boom_2.aiff"));
		sounds.add(new GameSound("boom_3.aiff"));
		
		message = start;
		decorateIntro();
		repaint();
		
		System.out.println("Preload Finished.");
	}
	
	private void renderMessages()
	{
		load = new GameGraphic(550,300);
		load.setColor(Color.white);
		load.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
		load.drawString("LOADING");
		start = new GameGraphic(550,300);
		start.setColor(Color.white);
		start.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		start.drawString("Press SPACE to Start");
		restart = new GameGraphic(550,300);
		restart.setColor(Color.white);
		restart.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		restart.drawString("Press R to Restart");
		
		message = load;
	}
	
	private void addScoreboards()
	{
		score = new Text("Score: 0");
		score.setStyle(Color.white, new Font(Font.SANS_SERIF, Font.BOLD, 30));
		score.setSize(200, 200);
		addActor(score,50,10);
		
		lives = new Text("Lives: 3");
		lives.setStyle(Color.white, new Font(Font.SANS_SERIF, Font.BOLD, 30));
		lives.setSize(200, 200);
		addActor(lives,50,50);
	}
	
	private void decorateIntro()
	{
		getSound("level_up.wav").play();
		int ranS, ranX = 0, ranY = 0;
		for(int i=0;i<6;i++)
		{
			ranS = getRandomNumber(25,100);
			ranX = getRandomNumber(0,WIDTH);
			ranY = getRandomNumber(0,HEIGHT);
			addActor(new Asteroid(3,ranS,ranS, getRandomNumber(0,360), getRandomNumber(3,7), getRandomNumber(1,4)), ranX,ranY);
		}
	}
	
	public void act()
	{
		check();
		addAsteroids();
		updateText();
	}
	
	private void check()
	{
		if(FIRST_LAUNCH)
		{
			if(GAME_OVER && isKeyPressed("space"))
			{
				reboot();
				FIRST_LAUNCH = false;
			}
		}
		else
		{
			if(GAME_OVER && isKeyPressed("r"))
			{
				reboot();
			}
		}
		
	}
	
	private void reboot()
	{
		GAME_OVER = false; message.clearImage();
		ArrayList<Actor> asts = getActors("Asteroid");
		for(Actor a : asts)
		{
			Asteroid ast = (Asteroid)a;
			ast.destroy();
		}
		loadLevel();
	}
	
	public void addPixel(boolean ex,int direction, int x, int y)
	{
		if(ex)
		{
			for(int i=0;i<EXPIX;i++)
			{
				if(!pixels[i].active)
				{
					pixels[i].activate(direction,x,y);
					return;
				}
			}
		}
		else
		{
			for(int i=EXPIX;i<PIXELS;i++)
			{
				if(!pixels[i].active)
				{
					pixels[i].activate(direction,x,y);
					return;
				}
			}
		}
	}
	
	private void addAsteroids()
	{
		if(!GAME_OVER)
		{
			Actor asteroid = getActor("Asteroid");
			if(asteroid == null)
			{
				spawn++;
			}
			
			if(spawn >= SPAWN_RATE)
			{
				getSound("level_up.wav").play();
				spawnAsteroids();
				amount ++;
				spawn = 0;
			}
		}
	}
	
	private void spawnAsteroids()
	{
		int place = 0, ranX = 0, ranY = 0;
		
		for(int i=0;i<amount;i++)
		{
			place = getRandomNumber(0,4);
			switch(place)
			{
				case 0 : ranX = 0; ranY = getRandomNumber(0,HEIGHT);
				break;
				case 1 : ranX = getRandomNumber(0,WIDTH); ranY = 0;
				break;
				case 2 : ranX = WIDTH; ranY = getRandomNumber(0,HEIGHT);
				break;
				case 3 : ranX = getRandomNumber(0,WIDTH); ranY = HEIGHT;
			}
			addActor(new Asteroid(3,100,100, getRandomNumber(0,360), getRandomNumber(3,7), getRandomNumber(1,4)), ranX,ranY);
		}
	}
	
	private void updateText()
	{
		score.update("Score: " + points);
		lives.update("Lives: " + health);
	}
	
	public void changeScore(int amt)
	{
		points += amt;
	}
	
	public void changeLives(int amt)
	{
		health = amt;
	}
	
	public void endGame()
	{
		GAME_OVER = true;
		restart = new GameGraphic(550,300);
		restart.setColor(Color.white);
		restart.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		restart.drawString("Press R to Restart");
		message = restart;
	}
	
	private BufferedImage drawBackground()
	{
		GameGraphic gg = new GameGraphic(WIDTH,HEIGHT);
		gg.fillRect();
		return gg.getImage();
	}
	
}
