package pixeldungeon;
import java.awt.Color;
import java.awt.Graphics2D;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import pixeldungeon.entities.Background;
import pixeldungeon.entities.Player;
import pixeldungeon.entities.Renderable;
import pixeldungeon.entities.Solid;
import pixeldungeon.entities.Updateable;
import pixeldungeon.game.Game;
import pixeldungeon.game.GameView;
import pixeldungeon.game.Scene;
import pixeldungeon.game.Scenes;
import pixeldungeon.math.MathTool;
import tools.Numbers;
import engine.JavaEngine;


public class PixelDungeonGame extends JavaEngine
{
	public PixelDungeonGame(int width, int height, double fps)
	{
		super(width, height, fps);
	}


	String XBOX_CONTROLLER = "Controller (XBOX 360 For Windows)";
	String Y_AXIS = "Y Axis";
	String X_AXIS = "X Axis";
	
	public static Controller xboxController;
	public static Component yaxis, xaxis;
	
//		Y Axis
//		X Axis
//		Y Rotation
//		X Rotation
//		Z Axis
//		Button 0
//		Button 1
//		Button 2
//		Button 3
//		Button 4
//		Button 5
//		Button 6
//		Button 7
//		Button 8
//		Button 9
//		Hat Switch		
	
	public void initController()
	{
		Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();

		xboxController = null;
		
		for (Controller c : ca)
		{
			System.out.println(c.getName());
			
			if (c.getName().indexOf("XBOX 360") >= 0)
				xboxController = c;
		}
		
		if (xboxController == null)
		{
			return;
		}
		
		
		
		System.out.println("found XBOX controller!");
		System.out.println("Buttons:");
		
		
		
		
		for (Component comp : xboxController.getComponents())
		{
			System.out.println(comp.getName());
			
			if (comp.getName().equals("Y Axis"))
				yaxis = comp;
			else if (comp.getName().equals("X Axis"))
				xaxis = comp;
		}
		
	}
	
	
	public static void main(String[] args) throws InterruptedException
	{
		Thread t = new Thread(new PixelDungeonGame(1000, 600, 100));
		
		t.start();
	}

	double x = 30;
	double y = 30;
	
	double speed = 400.0; // pixels pr sec
	
	@Override
	public void render(Graphics2D g)
	{
		renderRenderables(g);
		renderStats(g, 200);
	}

	private void pollController()
	{
		xboxController.poll();
	}
	
	double tenE9 = Math.pow(10, 9);
	double tenE6 = Math.pow(10, 6);

	public static double xpoll = 0;
	public static double ypoll = 0;
	
	public static double DEAD_ZONE = 0.2;


	public void updateController(double ns)
	{
		if (xboxController == null)
			return;
		
		pollController();
		
		xpoll = xaxis.getPollData();
		ypoll = yaxis.getPollData();
		
		if (xpoll > 0 && xpoll < DEAD_ZONE 
		||  xpoll < 0 && xpoll > -DEAD_ZONE)
			xpoll = 0;
		
		if (ypoll > 0 && ypoll < DEAD_ZONE 
				||  ypoll < 0 && ypoll > -DEAD_ZONE)
					ypoll = 0;
		
		xpoll = xpoll != 0 ? MathTool.unit(xpoll) * (Math.abs(xpoll) - DEAD_ZONE) / (1.0 - DEAD_ZONE): 0;
		ypoll = ypoll != 0 ? MathTool.unit(ypoll) * (Math.abs(ypoll) - DEAD_ZONE) / (1.0 - DEAD_ZONE) : 0;
	}

	double ms; 
	public void update(double ns)
	{
		updateController(ns);
		ms = ns / Numbers.tenE6;
		updateUpdateables(ms);
		Game.game.checkAllCollisions();
	}

	@Override
	public void preExit()
	{
		// TODO Auto-generated method stub
		
	}


	private void updateUpdateables(double period)
	{
		for (Updateable u : Game.game.currentScene.updateables)
		{
			u.update(period);
		}
	}
	
	private void renderRenderables(Graphics2D g)
	{
		for (Renderable r : Game.game.currentScene.renderables)
		{
			r.render(g, Game.game.view);
		}
	}
	@Override
	public void handlePreCycle()
	{
		// removing and adding game elements
		Game.game.handleRenderables();
		Game.game.handleUpdateables();
		Game.game.handleCollideables();		
	}


	private void initGame()
	{
		Scene main;
		main = Scenes.mainScene();
		
		Game.game.addScene(main);
		
		Game.game.player = new Player(1000, 1000);
		Game.game.addEntity(Game.game.player);
		
		Game.game.addEntity(new Solid(500, 500, 50, 50, true));
		
		Game.game.addView(new GameView(WIDTH, HEIGHT));
		Game.game.addRenderable(new Background(800, 800, "/img/dungeonfloor3.png"));
		//Game.game.addEntity(new Solid(1000,1335,1000,30,false));
	}
	
	@Override
	public void init()
	{
		initGame();
		initController();
	}
}
