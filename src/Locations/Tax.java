package Locations;

import Main.GameBoard;
import Main.Player;
import UI.UIConsole;

//SHOULD BE DONE
public class Tax extends GameLocation
{
	String name;
	
	public Tax(float x, float y, float width, float height, String textureName, int location, String name) 
	{
		super(x, y, width, height, textureName, location);
		this.name = name;
	}
	
	@Override
	public boolean execute(Player player, GameBoard gameBoard)
	{
		UIConsole.print(player + " has landed on a tax.");
		
		if(name.equals("Income"))
		{
			int tax = (int) (player.getMoney() * .1);
			if(tax < 200){
				player.payBank(tax);
			}
			else{
				player.payBank(200);
			}
	
			return true;
		}
		else if(name.equals("Luxury"))
		{
			player.payBank(100);
			return true;
		}
		else
			UIConsole.print("CLASS TAX: This shouldn't happen");
		
		return true;
	}
}
