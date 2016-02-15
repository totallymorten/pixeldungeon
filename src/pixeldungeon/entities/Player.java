package pixeldungeon.entities;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import pixeldungeon.game.Game;
import pixeldungeon.graphics.AnimatedSprite;
import pixeldungeon.graphics.RenderPriority;
import pixeldungeon.math.Vector2D;
import pixeldungeon.types.Direction;

public class Player extends AnimatedSprite
{
	Direction moveDirection = null;

	Rectangle2D collisionBox;
	
	
	public Player(int x, int y)
	{
		super(x, y, "/img/player-anim-walk.png", 50, 50);
		
		// enabling set speed
		setSpeed = new Vector2D(0, 0);
	}

	boolean lastUP = false, lastDOWN = false, lastLEFT = false, lastRIGHT = false;
	
	double absx,absy;
	
	@Override
	public void update(double ms)
	{
		absx = Math.abs(speed.x);
		absy = Math.abs(speed.y);
		
		msDelay = 50 * (Game.game.currentScene.maxSpeedX / (absx > absy? absx : absy));
		msDelay = msDelay > 200 ? 200 : msDelay;
		
		Game.game.currentScene.handleKeys(this,ms);
		super.update(ms);
	}

	@Override
	public void render(Graphics2D g, Vector2D viewPos)
	{
		super.render(g, viewPos);
	}

	@Override
	public RenderPriority getRenderPriority()
	{
		return RenderPriority.ACTOR;
	}

	int collisionOffsetX = 5;
	int collisionOffsetY = 25;
	double collisionW = 40;
	double collisionH = 25;
	
	@Override
	public Rectangle2D getSpecialCollisionBox(Vector2D viewPos)
	{
		return null;
		//return new Rectangle2D.Double(viewPos.x + collisionOffsetX, viewPos.y + collisionOffsetY, collisionW, collisionH);
	}

	@Override
	public void collision(Collideable other)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isAnimating()
	{
		return (speed.x != 0 || speed.y != 0);
	}
}
