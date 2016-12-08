package Locations;

import java.util.Random;

import Main.GameBoard;
import Main.Player;
import UI.UIConsole;

public class CommunityChest extends GameLocation
{
	public CommunityChest(float x, float y, float width, float height, String textureName, int location) 
	{
		super(x, y, width, height, textureName, location);
	}
	
	public boolean execute(Player player, GameBoard gameBoard)
	{	
		Random chanceRoll = new Random();
		int roll = chanceRoll.nextInt(12) +1;
		UIConsole.print(player + " has landed on a community chest space and will draw a card.");
		
		if (roll == 1) // Move to Go square and receive $200
		{
			player.moveAbsolute(0, gameBoard);
			UIConsole.print(player + " advanced to GO (Collect $200)"); 
		}
		
		else if (roll == 2)
		{
			UIConsole.print(player + " Get out of Jail Free!");
			player.addGetOutOfJailCard();
		}
		
		else if (roll == 3)
		{
			UIConsole.print(player + " Go directly to jail, do not pass GO and do not collect $200");
			player.moveToJail(gameBoard);
		}
		
		else if (roll == 4)
		{
			UIConsole.print(player + " There was a bank error in your favor!. Collect $200");
			player.addMoney(200);
		}
		
		else if (roll == 5)
		{
			UIConsole.print(player + " Pay a Doctor's fee of $50");
			player.payBank(50); 
		}
		
		else if (roll == 6)
		{
			UIConsole.print(player + " Holiday's Fund matures. Receive $100");
			player.addMoney(100); 
		}
		
		else if (roll == 7)
		{
			UIConsole.print(player + " gets $50 from sale of stock");
			player.addMoney(50); 
		}
		
		else if (roll == 8)
		{
			UIConsole.print(player + " Receive $20 from Income Tax Refund");
			player.addMoney(25); 
		}
		
		else if (roll == 9)
		{
			UIConsole.print(player + " Grand Opera Night. Collect $50 from every player for seats!");
			for(int i = 0; i < gameBoard.getPlayers().size(); i++){
				Player player2 = gameBoard.getPlayers().get(i);
				if(player != player2)
					player2.payPlayer(player, 50);
			}
		}

		else if (roll == 10)
		{
			UIConsole.print(player + " collect $100 for your life insurance maturing");
			player.payBank(150); 
		}
		
		else if (roll == 11)
		{
			UIConsole.print(player + " Pay hospital fees of $100");
			player.payBank(100); // Print a message to the GUI
		}
		
		else if (roll == 12)
		{
			UIConsole.print(player + " Pay school fess of $150");
			player.payBank(150); 
		}
		return true;
	}
}
