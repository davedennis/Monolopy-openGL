package Main;

import Locations.Chance;
import Locations.CommunityChest;
import Locations.FreeParking;
import Locations.GoSquare;
import Locations.GoToJail;
import Locations.Jail;
import Locations.Property;
import Locations.Railroad;
import Locations.Tax;
import Locations.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import Graphics.SpriteBatcher;
import Locations.GameLocation;
import Resources.GameState;
import Resources.Helper;
import Resources.PlayerColor;
import Resources.PropertyColor;
import Resources.Settings;
import UI.UIConsole;
import UI.UIUtility;

public class GameBoard 
{
	private ArrayList<Player> players;
	private ArrayList<GameLocation> locations;
	
	private Dice dice1;
    private Dice dice2;
    private Jail jail;
    
    private int numTurns = 0;
    private int playerTurn = 0;
        
    public GameBoard(int numPlayers)
    {   	
    	players = new ArrayList<Player>();
    	locations = new ArrayList<GameLocation>();
    	
    	dice1 = new Dice(345, 960, 30, 30, "res/images/dice1.png");
    	dice2 = new Dice(385, 960, 30, 30, "res/images/dice1.png");
    	
		UIUtility.createText(1120, 1000, "Turn #" + numTurns, "res/fonts/calibrib.ttf", 36, Color.white, "numturns");

    	
    	if (numPlayers >= 2)
        {
            players.add(new Player(0, 0, 20, 20, "res/images/player1.png", 0, PlayerColor.RED));
            players.add(new Player(0, 0, 20, 20, "res/images/player2.png", 0, PlayerColor.BLUE));
            UIUtility.createText(1120, 900, "Player Red: $", "res/fonts/calibrib.ttf", 36, Color.red, "player1money");
    		UIUtility.createText(1120, 800, "Player Blue: $", "res/fonts/calibrib.ttf", 36, Color.blue, "player2money");
        }
        if (numPlayers >= 3)
        {
            players.add(new Player(0, 0, 20, 20, "res/images/player3.png", 0, PlayerColor.GREEN));
    		UIUtility.createText(1120, 700, "Player Green: $", "res/fonts/calibrib.ttf", 36, Color.green, "player3money");
        }
        if (numPlayers >= 4)
        {       	
            players.add(new Player(0, 0, 20, 20, "res/images/player4.png", 0, PlayerColor.YELLOW));
    		UIUtility.createText(1120, 600, "Player Yellow: $", "res/fonts/calibrib.ttf", 36, Color.yellow, "player4money");        
        }
        UIUtility.createText(120, 950, "Last Dice Roll: ", "res/fonts/calibrib.ttf", 36, Color.black, "lastdiceroll");
        
        loadBoard("res/board.monopoly");
        UIConsole.print("INFO: Starting game with " + players.size() + " players.");
    }
    
    public void addToJail(Player player)
    {
    	jail.addPlayer(player);
    }
    
    public void removeFromJail(Player player)
    {
    	jail.removePlayer(player);
    }
    
    public void draw()
    {    	
    	SpriteBatcher.begin();
    	dice1.draw();
    	dice2.draw();
    	for (GameLocation gameLocation : locations)
    		gameLocation.draw();
    	
       	for (Player player : players)
    	{
    		if (!player.isBankrupt())
    			player.draw();
    	}
       	SpriteBatcher.end();
       	
        UIUtility.UpdateText("numturns", "Turn #" + numTurns);

       	
       	if (players.size() >= 2)
        {
            UIUtility.UpdateText("player1money", "Player 1 Money: $" + players.get(0).getMoney());
            UIUtility.UpdateText("player2money", "Player 2 Money: $" + players.get(1).getMoney());
        }
        if (players.size() >= 3)
            UIUtility.UpdateText("player3money", "Player 3 Money: $" + players.get(2).getMoney());
        if (players.size() >= 4)
            UIUtility.UpdateText("player4money", "Player 4 Money: $" + players.get(3).getMoney());
    }
    
    public void loadBoard(String fileName) // Spent like 2 mins on this so not working yet but hopefully gets the point across
    {
        try
        {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            String loadstate = "default";
            int loc = 0;
            while (scanner.hasNextLine())
            {
                loadstate = scanner.next();
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                int width = scanner.nextInt();
                int height = scanner.nextInt();
                String textureName = scanner.next();
                
                if (loadstate.equals("[Property]"))
                {
                	 String temp = scanner.next();
                	 PropertyColor color = null;
                	 if(temp.equals("LIGHTBLUE")) color = PropertyColor.LIGHTBLUE; 
                	 if(temp.equals("LIGHTPURPLE")) color = PropertyColor.LIGHTPURPLE; 
                	 if(temp.equals("PURPLE")) color = PropertyColor.PURPLE; 
                	 if(temp.equals("RED")) color = PropertyColor.RED; 
                	 if(temp.equals("BLUE")) color = PropertyColor.BLUE; 
                	 if(temp.equals("YELLOW")) color = PropertyColor.YELLOW; 
                	 if(temp.equals("GREEN")) color = PropertyColor.GREEN; 
                	 if(temp.equals("ORANGE")) color = PropertyColor.ORANGE; 
                	 String name = scanner.next().replace('_', ' ');
                     int purchaseCost = scanner.nextInt();
                     int purchaseHouseCost = scanner.nextInt();
                     int payHouseCost = scanner.nextInt();
                     int payHotelCost = scanner.nextInt();
                     
                     locations.add(new Property(x, y, width, height, textureName, loc, color, name, purchaseCost, purchaseHouseCost, payHouseCost, payHotelCost));
                }
                else if (loadstate.equals("[Jail]")) // No error checking but allow only 1
                {
                	
                    jail = new Jail(x, y, width, height, textureName, loc);
                    locations.add(jail);
                }

                else if (loadstate.equals("[Go]")) // No error checking but allow only 1
                {
                    locations.add(new GoSquare(x, y, width, height, textureName, loc));
                }
                else if(loadstate.equals("[Reading_Railroad]"))
                {
                	//String name = scanner.next();
                	locations.add(new Railroad(x, y, width, height, textureName, loc, "Reading Railroad"));
                }
                else if(loadstate.equals("[Pennsylvania_Railroad]"))
                {
                	//String name = scanner.next();
                	locations.add(new Railroad(x, y, width, height, textureName, loc, "Pennsylvania Railroad"));
                }
                else if(loadstate.equals("[B&O_Railroad]"))
                {
                	//String name = scanner.next();
                	locations.add(new Railroad(x, y, width, height, textureName, loc, "B&O Railroad"));
                }
                else if(loadstate.equals("[Short_Line]"))
                {
                	//String name = scanner.next();
                	locations.add(new Railroad(x, y, width, height, textureName, loc, "Short Line"));
                }
                else if(loadstate.equals("[Electric_Company]"))
                {
                	//String name = scanner.next();
                	locations.add(new Utility(x, y, width, height, textureName, loc, "Electric"));
                }
                else if(loadstate.equals("[Water_Works]"))
                {
                	//String name = scanner.next();
                	locations.add(new Utility(x, y, width, height, textureName, loc, "Water"));
                }
                else if(loadstate.equals("[CommunityChest]"))
                {
                	locations.add(new CommunityChest(x, y, width, height, textureName, loc));
                }
                else if(loadstate.equals("[Chance]"))
                {
                	locations.add(new Chance(x, y, width, height, textureName, loc));
                }
                else if(loadstate.equals("[Income_Tax]"))
                {
                	//String name = scanner.next();
                	locations.add(new Tax(x, y, width, height, textureName, loc, "Income"));
                }
                else if(loadstate.equals("[Luxury_Tax]"))
                {
                	//String name = scanner.next();
                	locations.add(new Tax(x, y, width, height, textureName, loc, "Luxury"));
                }
                else if(loadstate.equals("[GoToJail]"))
                {
                	locations.add(new GoToJail(x, y, width, height, textureName, loc));
                }
                else if(loadstate.equals("[FreeParking]"))
                {
                	locations.add(new FreeParking(x, y, width, height, textureName, loc));
                }
                //UIConsole.print("Successfully loaded location: " + loc);
                loc++;
            }

            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            UIConsole.print("ERROR: File '" + fileName + "' not found. The game board could not be loaded.");
            System.exit(-1);
        }
        
        if (jail == null)
        {
        	UIConsole.print("ERROR: Gameboard does not contain a jail.");
        	System.exit(-1);
        }
        if (locations.size() != 40)
        {
        	UIConsole.print("ERROR: Gameboard does not contain exactly fourty spaces.");
        	System.exit(-1);
        }

        UIConsole.print("INFO: GameBoard successfully loaded. " + this.locations.size());
    }
    

    private boolean hasRolled = false;
    private boolean hasExecuted = false;
    private boolean hasEnded = false;
    boolean hasStartedPrint;
    
    public void run()
    {
    	Keyboard.enableRepeatEvents(true);
    	Player activePlayer = players.get(playerTurn);
    	
    	if(!hasStartedPrint){
    		UIConsole.print("It is " + activePlayer + "'s turn");
    		hasStartedPrint = true;
    	}
    	    	
		if (!Helper.checkGameOver(players))		
		{
		    if (!activePlayer.isBankrupt())
		    {
			    if (activePlayer.isInJail())
			    {
			    	UIConsole.print(activePlayer + " is in jail and will roll to move out.");
			    	if (Mouse.isButtonDown(0))
			    	{
			    		int first = dice1.roll();
			    		int second = dice2.roll();
			    		if (first == second) 
			    		{
			        		UIConsole.print(activePlayer + " has rolled doubles and has been removed from jail.");
			    			activePlayer.removeFromJail();
			        		startNextPlayerTurn();
			    		}
			    		else
			    		{
			    			UIConsole.print(activePlayer + " did not roll doubles");
			    			activePlayer.stayInJail();
			    			startNextPlayerTurn();
			    		}
			    	}
			    }
			   	else
			   	{
			    	if (Mouse.isButtonDown(0) || hasRolled)
			    	{
			    		if (!hasRolled)
			    		{
				    		int first = dice1.roll();
				    		int second = dice2.roll();
				    		activePlayer.move(first + second, this);
				    		hasRolled = true;
			    		}
			    		if (!hasExecuted && hasRolled)
			    		{
			    	
			    			hasExecuted = activePlayer.execute(this);
			    		}
			    		if (!hasEnded && hasRolled && hasExecuted)
			    		{
			    			if (Mouse.isButtonDown(1))
			   	    			startNextPlayerTurn();
			   			}			    			
			   		}
			   	}
		   	}
		   	else
		   	{
		   		UIConsole.print("This player is bankrupt");
		   		startNextPlayerTurn();
		   	}
		}
    	else
    		Settings.setGameState(GameState.MAIN_MENU);
    }
    
    private void startNextPlayerTurn()
    {
    	hasRolled = false;
    	hasExecuted = false;
    	hasEnded = false;
    	hasStartedPrint = false;
    	
    	playerTurn++;
    	if (playerTurn >= players.size())
    	{
    		playerTurn = 0;
    		numTurns++;
    		UIConsole.print("***");
    	}
    }
    

    void printBoard() // Test function
    {
        UIConsole.print("INFO: Printing the gameboard.");

        for (GameLocation location : locations)
            UIConsole.print("\t" + location.toString());

    }

    boolean checkWinCondition()
    {
        int count = 0;
        Player winner = null;
        for (Player player : players) // Get the number of active players (those that still have money)
        {
            if (!player.isBankrupt()) 
            {
                count++;
                winner = player;
            }
        }

        if (count == 1) // Only one player is not bankrupt
        {
            UIConsole.print("INFO: The game is over. " + winner + " won with $" + winner.getMoney());
            return true;
        }
        else if (count < 1)
            UIConsole.print("LOGIC ERROR: All players were flagged as not playing or out of money");

        return false;
    }
    
    public Dice getDice1(){
    	return dice1;
    }
    public Dice getDice2(){
    	return dice2;
    }
    
    public ArrayList<Player> getPlayers(){
    	return players;
    }
    
    public GameLocation getLocation(int loc)
    {
    	return locations.get(loc);
    }
    
    public float getLocationX(int loc)
    {
    	return locations.get(loc).getX();
    }
    
    public float getLocationY(int loc)
    {
    	return locations.get(loc).getY();
    }
    public ArrayList<GameLocation> getLocations()
    {
    	return locations;
    }
}
