package Locations;

import Graphics.Drawable;
import Graphics.SpriteBatcher;
import Main.GameBoard;
import Main.Player;
import UI.UIConsole;

public class GameLocation extends Drawable
{	
	private int location; // between 0 and 39
		
	public GameLocation(float x, float y, float width, float height, String textureName, int location)
	{
		super(x, y, width, height, textureName);

		this.location = location;
	}
	
	public void draw()
	{
		SpriteBatcher.draw(this);
	}
	

    public int getLocation() {
    	return this.location;
    }
    
    public boolean execute(Player player, GameBoard gameBoard)
    {
        UIConsole.print("Override this method for when a player lands on this location.");
        return true;
    }
}

