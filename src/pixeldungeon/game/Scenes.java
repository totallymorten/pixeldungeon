package pixeldungeon.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import pixeldungeon.PixelDungeonGame;
import pixeldungeon.entities.Collideable;
import pixeldungeon.entities.Entity;
import pixeldungeon.entities.Player;
import pixeldungeon.graphics.AnimatedSprite.AnimState;
import pixeldungeon.graphics.RenderPriority;
import engine.Keys;



public abstract class Scenes
{
	public enum SceneName {Main};
	
	enum ControlMode {Keys, Controller};
	
	static ControlMode cm = ControlMode.Controller;
	
	public static PlayerKeyHandler mainHandler = new PlayerKeyHandler()
	{
		// player configuration
		public int walkAcc = 950; // pixels pr. sec
		public double rotAcc = 0.0005;

		
		
		@Override
		public void handleKeys(Player p, double ms)
		{
			//speed.x = 0;
			
			p.state = AnimState.STOPPED;
			
			
			if (cm == ControlMode.Keys)
			{
				p.setSpeed.x = 0;
				p.setSpeed.x += Keys.check(KeyEvent.VK_RIGHT) ? Game.game.currentScene.maxSpeedX : 0;
				p.setSpeed.x += Keys.check(KeyEvent.VK_LEFT) ? -Game.game.currentScene.maxSpeedX : 0;
				
				p.setSpeed.y = 0;
				p.setSpeed.y += Keys.check(KeyEvent.VK_DOWN) ? Game.game.currentScene.maxSpeedY : 0;
				p.setSpeed.y += Keys.check(KeyEvent.VK_UP) ? -Game.game.currentScene.maxSpeedY : 0;

				p.acc.x = 0;
				p.acc.x += Keys.check(KeyEvent.VK_RIGHT) ? walkAcc : 0;
				p.acc.x += Keys.check(KeyEvent.VK_LEFT) ? -walkAcc : 0;

				p.acc.y = 0;
				p.acc.y += Keys.check(KeyEvent.VK_UP) ? -walkAcc : 0;
				p.acc.y += Keys.check(KeyEvent.VK_DOWN) ? walkAcc : 0;				
			}
			else if (cm == ControlMode.Controller)
			{
				p.setSpeed.x = 0;
				p.setSpeed.x += (Game.game.currentScene.maxSpeedX * PixelDungeonGame.xpoll);
				
				p.acc.x = 0;
				p.acc.x += (PixelDungeonGame.xpoll != 0) ? (walkAcc * PixelDungeonGame.xpoll) : 0;
				
				p.setSpeed.y = 0;
				p.setSpeed.y += (Game.game.currentScene.maxSpeedY * PixelDungeonGame.ypoll);

				p.acc.y = 0;
				p.acc.y += (PixelDungeonGame.ypoll != 0) ? (walkAcc * PixelDungeonGame.ypoll) : 0;
			}
			
			
			if (Keys.check(KeyEvent.VK_Q))
			{
				p.rotSpeed -= rotAcc;			
			}
			
			if (Keys.check(KeyEvent.VK_E))
			{
				p.rotSpeed += rotAcc;			
			}
			/*
			if (Keys.Q)
			{
				p.rotation -= rotSpeed;			
			}
			
			if (Keys.E)
			{
				p.rotation += rotSpeed;			
			}
			*/

		}
	};

	public static Scene mainScene()
	{
		Scene main = new Scene(SceneName.Main, mainHandler)
		{
			@Override
			public void initScene()
			{
				Game.game.player.rotation = 0;
			}
		};
		main.gravityY = 0;
		main.maxSpeedY = 250;
		main.maxSpeedX = 250;
		main.frictionX = 8;
		main.frictionY = 8;

		Entity background =  new Entity(0,0,3000,3000,true)
		{
			@Override
			public void collision(Collideable other)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public RenderPriority getRenderPriority()
			{
				return RenderPriority.BACKGROUND;
			}
			
			@Override
			public void draw(Graphics2D g, int x, int y)
			{
				g.setColor(Color.black);
				g.fillRect(x, y, width, height);	
			}
		};
		
		main.addEntity(background);

		return main;
	}
	
}
