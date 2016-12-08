package Locations;

import Main.GameBoard;
import Main.Player;
import UI.UIConsole;

public class GoToJail extends GameLocation
{
	public GoToJail(float x, float y, float width, float height, String textureName, int location) 
	 {
	        super(x, y, width, height, textureName, location);
	 }

	@Override 
	public boolean execute(Player player, GameBoard gameBoard)
	{
		gameBoard.addToJail(player);
		UIConsole.print(player + " has landed on GoToJail. What an amateur.");
		return true;
	}

}
