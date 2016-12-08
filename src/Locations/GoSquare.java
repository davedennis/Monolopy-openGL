package Locations;

import Main.GameBoard;
import Main.Player;
import UI.UIConsole;

//SHOULD BE DONE
public class GoSquare extends GameLocation
{
    public GoSquare(float x, float y, float width, float height, String textureName, int location)
    {
        super(x, y, width, height, textureName, location);
    }

    @Override
    public boolean execute(Player player, GameBoard gameBoard)
    {
        UIConsole.print("Player " + player + " has landed on GO. Nothing happens.");
		return true;

    }

    @Override
    public String toString()
    {
        return "Go: ";
    }
}
