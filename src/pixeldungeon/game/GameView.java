package pixeldungeon.game;

import java.awt.Graphics2D;

import engine.JavaEngine;

import pixeldungeon.entities.Collideable;
import pixeldungeon.entities.Entity;
import pixeldungeon.graphics.RenderPriority;
import pixeldungeon.math.Vector2D;

public class GameView extends Entity
{
	
	public GameView(int width, int height)
	{
		super(0,0, width, height, false);
	}
	
	double playerEdgeThresholdX = JavaEngine.WIDTH * 0.40;
	double playerEdgeThresholdY = JavaEngine.HEIGHT * 0.40;
	double speedFactor = 3;
	
	@Override
	public void update(double ms)
	{
		super.update(ms);
		
		Vector2D playerPos = Game.game.view.getViewCoords(Game.game.player.position);
		double diffx=0,diffy=0;
		
		if (playerPos.x < playerEdgeThresholdX)
			diffx = playerPos.x - playerEdgeThresholdX;
		else if (playerPos.x > (width - playerEdgeThresholdX))
			diffx = playerEdgeThresholdX - (width - playerPos.x);
		
		if (playerPos.y < playerEdgeThresholdY)
			diffy = playerPos.y - playerEdgeThresholdY;
		else if (playerPos.y > (height - playerEdgeThresholdY))
			diffy = playerEdgeThresholdY - (height - playerPos.y);
		
		
		speed = new Vector2D(diffx * speedFactor, diffy * speedFactor);
		
	}

	public boolean isVisible(Entity e)
	{
		return ((Collideable)e).isColliding(this, this);
	}
	
	public Vector2D getViewCoords(Vector2D pos)
	{
		return new Vector2D(pos.x - position.x, pos.y - position.y);
	}

	@Override
	public RenderPriority getRenderPriority()
	{
		return RenderPriority.BACKGROUND;
	}

	@Override
	public void collision(Collideable other)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g, int x, int y)
	{
		// TODO Auto-generated method stub
		
	}

}
