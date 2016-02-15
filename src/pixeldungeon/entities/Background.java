package pixeldungeon.entities;

import pixeldungeon.graphics.RenderPriority;
import pixeldungeon.graphics.Sprite;

public class Background extends Sprite
{

	int realWidth;
	int realHeight;
	
	public Background(double x, double y, String imagePath)
	{
		super(x, y, imagePath, true);
		
		realWidth = (int) x;
		realHeight = (int) y;
	}
	
	
/*
	@Override
	public void render(Graphics2D g, GameView view)
	{
		g.drawImage(img.getSubimage((int)view.position.x, (int)view.position.y, view.width, view.height),0,0, null);
	}
*/

	
	

	@Override
	public RenderPriority getRenderPriority()
	{
		return RenderPriority.BACKGROUND;
	}



//	@Override
//	public void draw(Graphics2D g, int x, int y)
//	{
//		for (int row = 0; row * height <= realHeight; row++)
//		{
//			for (int col = 0; col * width <= realWidth; col++)
//			{
//				super.draw(g, row * height, col * width);				
//			}
//		}
//		// TODO Auto-generated method stub
//	}


	@Override
	public void collision(Collideable other)
	{
		// TODO Auto-generated method stub

	}

}
