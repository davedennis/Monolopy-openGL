package Locations;

import java.util.ArrayList;

import Main.GameBoard;
import Main.Player;
import UI.UIConsole;

public class Jail extends GameLocation
{
    private ArrayList<Player> players;

    public Jail(float x, float y, float width, float height, String textureName, int location)
    {
        super(x, y, width, height, textureName, location);
        players = new ArrayList<Player>();
    }
    
    @Override
    public boolean execute(Player player, GameBoard gameBoard)
    {
    	UIConsole.print(player + " was just visiting jail.");
    	return true;
    }

    public void addPlayer(Player player)
    {
    	players.add(player);
    }

    public void removePlayer(Player player)
    {
        if (players.remove(player))
        	player.removeFromJail();
        else
            UIConsole.print("LOGIC ERROR?: This shouldn't happen, we removed a player from jail that wasn't there");
    }

    public boolean playerInJail(Player player)
    {
        if (players.contains(player))
            return true;
        return false;
    }

    @Override
    public String toString()
    {
        return "Jail";
    }
}
