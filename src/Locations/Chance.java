package Locations;

import java.util.Random;

import Main.GameBoard;
import Main.Player;
import UI.UIConsole;

public class Chance extends GameLocation
{
	public Chance(int x, int y, int width, int height, String textureName, int location) 
	{
		super(x, y, width, height, textureName, location);
	}

	public boolean execute(Player player, GameBoard gameBoard)
	{
		Random chanceRoll = new Random();
		int roll = chanceRoll.nextInt(12) +1;
		//roll = 1;
		UIConsole.print(player + " has landed on a chance space and will draw a random chance card.");
		
		if (roll == 1) // Move to Go square and receive $200
		{
			player.moveAbsolute(0, gameBoard);
			UIConsole.print(player + " advanced to GO (Collect $200)."); 
		}
		
		else if (roll == 2)
		{
			UIConsole.print(player + " has been given a get out of jail free card!");
			player.addGetOutOfJailCard();
		}
		
		else if (roll == 3)
		{
			UIConsole.print(player + " Go directly to jail, do not pass GO and do not collect $200");
			player.moveToJail(gameBoard);
		}
		
		else if (roll == 4)
		{
			UIConsole.print(player + "'s building and loan matures! Collect $150!");
			player.addMoney(150); 
		}
		
		else if (roll == 5)
		{
			UIConsole.print(player + " has won a crossword competition! Collect $100!");
			player.addMoney(100); 
		}
		
		else if (roll == 6)
		{
			UIConsole.print(player + " is paid a dividend of $50 from the bank");
			player.addMoney(50); 
		}
		
		else if (roll == 7)
		{
			UIConsole.print(player + " moves back 3 spaces.");
			player.moveBackwards((player.getLocation() - 3), gameBoard);
			return gameBoard.getLocation(player.getLocation()).execute(player, gameBoard);
		}
		
		else if (roll == 8)
		{
			UIConsole.print(player + " Advance to St. Charles Avenue. If you pass GO collect $200");
			if (player.getLocation() < 11){
				player.moveAbsolute(11, gameBoard);
				return gameBoard.getLocation(11).execute(player, gameBoard);
			}
			else{
				player.moveAbsolute(11, gameBoard);
				return gameBoard.getLocation(11).execute(player, gameBoard);
			}
		}
		
		else if (roll == 9)
		{
			UIConsole.print(player + " pays a poor tax of $15");
			player.payBank(15);
		}

		else if (roll == 10)
		{
			UIConsole.print(player + " takes a trip to Reading Railroad. If you pass GO collect $200");
			if(player.getLocation() < 5){
				player.moveAbsolute(5, gameBoard);
				return gameBoard.getLocation(5).execute(player, gameBoard);
			}else{
				player.moveAbsolute(5, gameBoard);
				return gameBoard.getLocation(5).execute(player, gameBoard);
			}
		}
		
		else if (roll == 11)
		{
			UIConsole.print(player + " has been elected Chairman of the Board! Pay each player $50!");
			for(int i = 0; i < gameBoard.getPlayers().size(); i++){
				Player player2 = gameBoard.getPlayers().get(i);
				if(player != player2)
					player.payPlayer(player2, 50);
			}
		}
		
		else if (roll == 12)
		{
			UIConsole.print(player + " take a walk on Boardwalk. Advance to boardwalk");
			player.moveAbsolute(39, gameBoard);
			return gameBoard.getLocation(39).execute(player, gameBoard);
		}
		
		return true;
	}
	
//	public int pickACard() {
//		Random chanceRoll = new Random();
//		int roll = chanceRoll.nextInt(10) +1;
//		return roll;
//	}
}
