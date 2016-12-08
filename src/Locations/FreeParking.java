package Locations;

import Main.GameBoard;
import Main.Player;
import UI.UIConsole;

public class FreeParking extends GameLocation
{
	public FreeParking(float x, float y, float width, float height, String textureName, int location) 
	 {
	        super(x, y, width, height, textureName, location);
	 }
	
	@Override
	public boolean execute(Player player, GameBoard gameBoard)
	{
		UIConsole.print(player + " has landed on free parking. Lucky bastard...");
		return true;
	}
}
