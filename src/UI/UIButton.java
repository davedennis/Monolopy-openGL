package UI;

import org.lwjgl.input.Mouse;
import Graphics.Drawable;

class UIButton extends Drawable
{	 
	private String function;
	
	UIButton(float x, float y, float width, float height, String textureName, String buttonFunction)
	{
		super(x, y, width, height, textureName);
		this.function = buttonFunction;
	}
	
	boolean inBounds()
	{	
		if ((Mouse.getX() > x) && (Mouse.getX() < (x + width)) && (Mouse.getY() > y) && (Mouse.getY() < (y + height)))
			return true;
		else 
			return false;
	}
	
	String getFunction() {
		return function;
	}
}
