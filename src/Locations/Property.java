package Locations;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import Graphics.Drawable;
import Main.GameBoard;
import Main.Player;
import Resources.Helper;
import Resources.PlayerColor;
import Resources.PropertyColor;
import UI.UIConsole;
import UI.UIUtility;

public class Property extends GameLocation
{
    String name;
    private int purchaseCost;
    private int payHouseCost;
    private int payHotelCost;

    int cost;
    int numberOfHouses;

    boolean isHotel;
    boolean isOwned;
    boolean hasPrinted;

    Player owner = null;
    PropertyColor propertyColor;
    
    private Drawable monopolyColor;
    private Drawable ownerColor;
    private Drawable house0, house1, house2, house3, hotel;

    /// Auctions
    private Player currentAuctionPlayer = null;
    private boolean auctionStarted = false;
    private boolean auctionInitialized = false;
    private boolean auctionPrinted = false;
    private boolean auctionReceivedInput = false;
    private boolean auctionFinished = false;
    
    private int auctionPrice;
    private int currentAuctionIndex = 0;
    private int lastPress = 0;
    private int removedThisRound = 0;
    private ArrayList<Player> auctionPlayers;

    public Property(int x, int y, int width, int height, String textureName, int location, PropertyColor propertyColor, String name, int purchaseCost, int purchaseHouseCost, int payHouseCost, int payHotelCost)
    {
        super(x, y, width, height, textureName, location);
        this.propertyColor = propertyColor;
        this.name = name;
        this.purchaseCost = purchaseCost;

        this.setPayHouseCost(payHouseCost);
        this.setPayHotelCost(payHotelCost);
        this.numberOfHouses = 0;

        this.cost = (int) (payHouseCost * 0.3); // Cost of rent without any property is 30% of rent with 1 house
        
        String temp = "this should change";
        if (propertyColor == PropertyColor.BLUE)
        	temp = "res/images/colors/blue.png";
        else if (propertyColor == PropertyColor.LIGHTBLUE)
        	temp = "res/images/colors/lightblue.png";	
        else if (propertyColor == PropertyColor.GREEN)
        	temp = "res/images/colors/green.png";	
        else if (propertyColor == PropertyColor.RED)
        	temp = "res/images/colors/red.png";	
        else if (propertyColor == PropertyColor.ORANGE)
        	temp = "res/images/colors/orange.png";	
        else if (propertyColor == PropertyColor.PURPLE)
        	temp = "res/images/colors/purple.png";	
        else if (propertyColor == PropertyColor.YELLOW)
        	temp = "res/images/colors/yellow.png";	
        else if (propertyColor == PropertyColor.LIGHTPURPLE)
        	temp = "res/images/colors/lightpurple.png";	
        
        house0 = new Drawable(x, y + 75, 25, 25, "res/images/house.png"); // That image doesn't exist... add one, use a temporary image, or a missing texture will be loaded (you will see purple and black checkers).
        house1 = new Drawable(x + 25, y + 75, 25, 25, "res/images/house.png");
        house2 = new Drawable(x + 50, y + 75, 25, 25, "res/images/house.png");
        house3 = new Drawable(x + 75, y + 75, 25, 25, "res/images/house.png");
        hotel = new Drawable(x, y + 75, 40, 40, "res/images/hotel.png");
                
        monopolyColor = new Drawable(x + 5, y, width - 10, 10, temp);
        ownerColor = new Drawable(x + 5, y + 10, width - 10, 10, "res/images/colors/white.png");
		UIUtility.createText(x + 10, y + 50, name, "res/fonts/calibrib.ttf", 10, Color.black, "propertynamedonttryandreferencethisoryouaregoingtohaveabadtime.jpg");
    }
    
    public Property(int x, int y, int width, int height, String textureName, int location) // Replace eventually. test function
    {
    	super(x, y, width, height, textureName, location);
    }
    
    @Override
    public void draw()
    {
    	super.draw();
    	monopolyColor.draw();
    	
    	if (owner == null)
    		ownerColor.setTexture("res/images/colors/white.png");
    	else
    	{
    		PlayerColor temp = owner.getPlayerColor();
        	if (temp == PlayerColor.RED)
        		ownerColor.setTexture("res/images/colors/red.png");
        	else if (temp == PlayerColor.BLUE)
        		ownerColor.setTexture("res/images/colors/blue.png");
        	else if (temp == PlayerColor.GREEN)
        		ownerColor.setTexture("res/images/colors/green.png");
        	else if (temp == PlayerColor.YELLOW)
        		ownerColor.setTexture("res/images/colors/yellow.png");
    	}
    	ownerColor.draw();
    	
    	
    	if (numberOfHouses > 0)
    	{
    		house0.draw();
    	}
    	if (numberOfHouses > 1)
    		house1.draw();
    	if (numberOfHouses > 2)
    		house2.draw();
    	if (numberOfHouses > 3) // Shouldn't ever be greater than 3 but you know this is kinda theoretical
    		house3.draw();
    	
    	if (isHotel){
    		hotel.draw(); // Will draw the image over any houses if numHouses is > 0. If we have a hotel we should have 0 houeses though
    	}
    		
    }
    
    @SuppressWarnings("unchecked")
	public boolean auctionProperty2(Property property, GameBoard gameboard, Player decliner)
    {
    	if (!auctionInitialized)
    	{
    		UIConsole.print("Starting auction of " + property);    		
        	auctionPlayers = (ArrayList<Player>) gameboard.getPlayers().clone();
        	auctionPlayers.remove(decliner);
        		//UIConsole.print("Succesffully removed decliner");

	    	for (Player player: auctionPlayers)
	    		if (player.isBankrupt())
	    			player.removed = true;
	    	
    		currentAuctionPlayer = auctionPlayers.get(currentAuctionIndex);
    		auctionPrice = purchaseCost / 2;
    		auctionInitialized = true;
    	}
    	
    	if (!auctionPrinted)
    	{
    		currentAuctionPlayer = auctionPlayers.get(currentAuctionIndex);
    		UIConsole.print("\tAUCTION: Would " + currentAuctionPlayer + " like to make a bid? Y / N");
    		if(currentAuctionPlayer.isAI())
    		{
    			if(auctionPrice < purchaseCost)
    			{
	    			UIConsole.print("\t\tAUCTION: " + currentAuctionPlayer + " bid on the property");
	    			currentAuctionPlayer.hasBid = true;
	    			auctionReceivedInput = true;
    			}else
    			{
    				UIConsole.print("\t\tAUCTION: " + currentAuctionPlayer + " did not bid and has been removed from the auction");
	    			currentAuctionPlayer.hasBid = true;
	    			currentAuctionPlayer.removed = true;
	    			auctionReceivedInput = true;
	    			removedThisRound++;
    			}
    		}
    		auctionPrinted = true;
    	}
    	if (!auctionReceivedInput)
    	{
    		if (Helper.getTick() > lastPress + 30) // second between button press due to v_sync. 
    		{
	    		if (Keyboard.isKeyDown(Keyboard.KEY_Y))
	    		{
	    			lastPress = Helper.getTick();
	    			if (!currentAuctionPlayer.checkIfcanAfford(auctionPrice))
	    			{
	    				lastPress = Helper.getTick();
	    				UIConsole.print("\t\tAUCTION: " + currentAuctionPlayer + " can't afford to buy the auctioned property and has been removed from the auction.");
		    			currentAuctionPlayer.hasBid = true;
		    			currentAuctionPlayer.removed = true;
		    			auctionReceivedInput = true;
		    			removedThisRound++;
	    			}
	        		UIConsole.print("\t\tAUCTION: " + currentAuctionPlayer + " bid on the property");
	    			currentAuctionPlayer.hasBid = true;
	    			auctionReceivedInput = true;
	    		}
	    		if (Keyboard.isKeyDown(Keyboard.KEY_N))
	    		{
	    			lastPress = Helper.getTick();

	        		UIConsole.print("\t\tAUCTION: " + currentAuctionPlayer + " did not bid and has been removed from the auction");
	    			currentAuctionPlayer.hasBid = true;
	    			currentAuctionPlayer.removed = true;
	    			auctionReceivedInput = true;
	    			removedThisRound++;
	    		}
    		}
    	}
    	if (auctionReceivedInput) // received input
    	{
    		int bidCount = 0;
    		int playerCount = 0;
    		int yesCount = 0;
    		for (Player player : auctionPlayers)
    		{
    			if (player.hasBid) 
    				bidCount++;
    			if (!player.removed)
    			{
    				if (player.hasBid)
    					yesCount++;
    				playerCount++;
    			}
    		}
    		//UIConsole.print("Bid Count: " + bidCount + "\tPlayerCount: " + playerCount + "\tYesCount: " + yesCount);
    		
	    	if (bidCount == playerCount + removedThisRound) // This round of auction is over
	    	{
	    		Player winner = null;
	        	for (Player player : auctionPlayers)
	        	{
	        		if (!player.removed && player.hasBid)
	        			winner = player;
	        	}
	    		if (yesCount == 0)
	    		{
	    			UIConsole.print("AUCTION: All players thought the new price was too high. This is a shitty bank. Fix this");
	    			endAuction(gameboard, winner);
	    			return true;
	    		}
	    		else if (yesCount == 1)
	    		{
	    			UIConsole.print("AUCTION: " + winner + " won the auction at $" + auctionPrice );
	    	    	winner.payBank(auctionPrice);
	    			endAuction(gameboard, winner);
	    			return true;
	    		}
	    		else
	    			nextAuctionPlayer();
    		}
	    	else
	    		nextAuctionPlayer();
    	}
    	
    	return false;
    }
    
    private void nextAuctionPlayer()
    {
    	currentAuctionIndex++;
    	while (currentAuctionIndex < auctionPlayers.size() && auctionPlayers.get(currentAuctionIndex) != null)
    	{
    		 if (auctionPlayers.get(currentAuctionIndex).removed)
    			 currentAuctionIndex++;
    		 else 
    			 break;
    	}

    	auctionPrinted = false;
    	auctionReceivedInput = false;
    	if (currentAuctionIndex == auctionPlayers.size()) 
    	{
    		for (Player player : auctionPlayers)
    		{
        		player.hasBid = false;
    		}
    		currentAuctionIndex = 0;
    		while (currentAuctionIndex < auctionPlayers.size() && auctionPlayers.get(currentAuctionIndex) != null) // In case index 0 declined
        	{
        		 if (auctionPlayers.get(currentAuctionIndex).removed)
        			 currentAuctionIndex++;
        		 else 
        			 break;
        	}
    		
    		removedThisRound = 0;
    		auctionPrice += 50;
    		UIConsole.print("AUCTION: Increasing auction price to $" + auctionPrice);
    	}
    }
    
    private void endAuction(GameBoard gameboard, Player winner)
    {
    	owner = winner;

    	for (Player player : gameboard.getPlayers())
    	{
    		player.hasBid = false;
    		player.removed = false;
    	}
    	
    	auctionPlayers = null;
    	currentAuctionIndex = 0;
    	removedThisRound = 0;
    	
    	currentAuctionPlayer = null;
        auctionStarted = false;
        auctionInitialized = false;
        auctionPrinted = false;
        auctionReceivedInput = false;
        auctionFinished = false;
    }

    @Override
    public boolean execute(Player player, GameBoard gameBoard)
    {	
    	Keyboard.enableRepeatEvents(true);
        if (owner == null)
        {
        	// StartAI
        	if (player.isAI())
        	{
        		if (player.buyProperty(this) == true) // So fucking smart
        			auctionStarted = true;
        		else
        			return true;
        	}
        	//EndAI
        	
        	if (!hasPrinted)
        	{
        		UIConsole.print("Player " + player + " landed on the unowned property " + name);
        		UIConsole.print("Press B to buy or N to not buy");
        		hasPrinted = true;
        	}
        	
        	if (!auctionStarted)
        	{    		
	            System.out.println("kjdskfjdf");
        		if (Keyboard.isKeyDown(Keyboard.KEY_B))
	            { 
	            	auctionStarted = player.buyProperty(this);
	            	hasPrinted = false;
	    			lastPress = Helper.getTick();

	            	return true;
	            }
	            else if (Keyboard.isKeyDown(Keyboard.KEY_N))
	            {
	            	auctionStarted = true;
	    			lastPress = Helper.getTick();
	    			
	            }
        		System.out.println("got past this.....");

        	}
            
            if (auctionStarted)
            {
            	auctionFinished = auctionProperty2(this, gameBoard, player);

            	if (auctionFinished == true)
            	{
            		auctionFinished = false;
            		return true;
            	}
            }
        }
        else
        {
            if (owner == player)
            {
                //this is to check if they can buy a house. 
            	int propColorcounter = 0;
            	PropertyColor propC = this.propertyColor;
            	for(int i = 0; i < gameBoard.getLocations().size(); i++)
            	{
            		if(gameBoard.getLocations().get(i) instanceof Property)
            		{
            			Property tempProp = (Property) gameBoard.getLocations().get(i);
            			if(propC.equals(tempProp.propertyColor))
            			{
            				//if (tempProp) //TODO
            				if(tempProp.owner != null &&
            						tempProp.owner.getPlayerColor().equals(player.getPlayerColor()))
            				{
            					propColorcounter++;
            				}
            			}
            		}
            	}
            	
            	if(propC.equals(PropertyColor.PURPLE) || propC.equals(PropertyColor.BLUE))
            	{             	
            		if(propColorcounter == 2)
            		{
            			if(numberOfHouses < 4)
            			{				
            				// StartAI
                        	if (player.isAI())
                        	{
                        		if (numberOfHouses < 2)
                        		{
	                				purchaseHouse();
		            				hasPrinted = false;
		            				UIConsole.print(player + " has decided to buy a house.");
		            				return true;
                        		}
                        		else
                        		{
		            				UIConsole.print(player + " did not want to buy a house.");
                        			return true;
                        		}
                        	}
                        	//EndAI
                        	
            				if(!hasPrinted)
            				{
            					UIConsole.print(player + " You own all these prop colors would you like to buy a house? Y or N");
            					hasPrinted = true;
            				}
            				if(Keyboard.isKeyDown(Keyboard.KEY_Y))
	            			{
	            				purchaseHouse();
	            				hasPrinted = false;
	            				return true;
	            			} 
            				else if(Keyboard.isKeyDown(Keyboard.KEY_N))
	            			{
	            				hasPrinted = false;
	            				return true;
	            			}
            			} 
            			else if(numberOfHouses == 4)
            			{
            				if(!isHotel)
	            			{
            					// StartAI
            					if (player.isAI())
                            	{
                    				purchaseHouse();
    	            				hasPrinted = false;
    	            				return true;
                            	}
                            	// EndAI
                            	
            					if(!hasPrinted)
            					{
            						UIConsole.print(player + " you own 4 houses. Would you like to buy a hotel? Y or N");
            						hasPrinted = true;
            					}
	            				if(Keyboard.isKeyDown(Keyboard.KEY_Y))
		            			{
		            				UIConsole.print(player + " you just bought a hote!!");
		            				purchaseHotel();
		            				isHotel = true;
		            				System.out.println(isHotel);
		            				hasPrinted = false;
		            				return true;
		            			} else if(Keyboard.isKeyDown(Keyboard.KEY_N))
		            			{
		            				return true;
		            			}
            				} 
            				else 
            				{
            					if(!hasPrinted)
            					{
            						UIConsole.print(player + " you already have a hotel here sorry!");
            						hasPrinted = true;
            					}
            					return true;
            				}
            			}
            		}
            	} else
            	{
            		if(propColorcounter == 3){
            			
            			if(numberOfHouses < 4)
            			{
        					// StartAI
            				if (player.isAI())
            				{
	            				if (numberOfHouses < 2)
	                    		{
	                				purchaseHouse();
		            				hasPrinted = false;
		            				UIConsole.print(player + " has decided to buy a house.");
		            				return true;
	                    		}
	                    		else
	                    		{
		            				UIConsole.print(player + " has did not want to buy a house.");
	                    			return true;
	                    		}
            				}
            				// EndAI
            				
            				if(!hasPrinted){
            					UIConsole.print(player + " You own all these prop colors would you like to buy a house? Y or N");
            					hasPrinted = true;
            				}
            				if(Keyboard.isKeyDown(Keyboard.KEY_Y))
	            			{
	            				purchaseHouse();
	            				hasPrinted = false;
	            				return true;
	            			} else if(Keyboard.isKeyDown(Keyboard.KEY_N))
	            			{
	            				hasPrinted = false;
	            				return true;
	            			}
	
            			} else if(numberOfHouses == 4)
            			{
	            				if(!isHotel)
	            				{
	            					if(!hasPrinted){
	            						UIConsole.print(player + " you own 4 houses. Would you like to buy a hotel? Y or N");
	            						hasPrinted = true;
	            					}
		            				if(Keyboard.isKeyDown(Keyboard.KEY_Y))
			            			{
			            				UIConsole.print(player + " you just bought a hote!!");
			            				purchaseHotel();
			            				isHotel = true;
			            				System.out.println(isHotel);
			            				hasPrinted = false;
			            				return true;
			            			} else if(Keyboard.isKeyDown(Keyboard.KEY_N))
			            			{
			            				hasPrinted = false;
			            				return true;
			            			}
	            				} else 
	            				{
	            					if(!hasPrinted){
	            						UIConsole.print(player + " you already have a hotel here sorry!");
	            						hasPrinted = false;
	            					}
	            					return true;
	            				}
            			}	
            		}
            	}
           
            	return true;
        
            }
            else if (owner != player)
            {
            	if(!hasPrinted){
            		UIConsole.print("You landed on " + owner + "'s property.");
            		UIConsole.print("Press P to pay your rent of $" + cost);
            		hasPrinted = true;
            	}
                if (Keyboard.isKeyDown(Keyboard.KEY_P) || player.isAI())
                {
                	if(player.checkIfcanAfford(cost))
                	{
                		player.payPlayer(owner, cost);
                    	hasPrinted = false;
                    	return true;	
                	}
                	//Mortgaging
                	else 
                	{
                    	int canAffordCount = 0;
                		for(int i = 0; i < gameBoard.getLocations().size(); i++)
                    	{
                    		//For houses                  
                    		
                    		//For property, not houses/hotels
                    		if(gameBoard.getLocations().get(i) instanceof Property)
                    		{                    			
                    		
                    			Property tempProp = (Property) gameBoard.getLocations().get(i);    
                    			System.out.println(tempProp.owner);
                				if(tempProp.owner != null &&
                						tempProp.owner.getPlayerColor().equals(player.getPlayerColor()))
                				{       
                					if(tempProp.isHotel)
                					{   
                      					int propCost = tempProp.getPayHotelCost()/2;
                    					player.addMoney(propCost);
                    					tempProp.isHotel = false;
                					}
                					
                					for(int j = 0; j < tempProp.numberOfHouses; j++)
                					{                			
                      					int propCost = tempProp.getPayHouseCost()/2;
                    					player.addMoney(propCost);
                					}
                					
                					tempProp.numberOfHouses = 0;
                					
                  					int propCost = tempProp.cost/2;
                					player.addMoney(propCost);
                					tempProp.setOwner(null); 
                					canAffordCount++;
                				}
	
                    		}
                    		
        					//For Railroads
        					else if (gameBoard.getLocations().get(i) instanceof Railroad) {
        						
        						int cost = 0;
        						 
        						if (owner.getRailroadsOwned() == 1) {
        							cost = 25;
        						} else if (owner.getRailroadsOwned() == 2) {
        							cost = 50;
        						} else if (owner.getRailroadsOwned() == 3) {
        							cost = 100;
        						} else if (owner.getRailroadsOwned() == 4) {
        							cost = 200;
        						}
        						
        						Railroad tempProp = (Railroad) gameBoard.getLocations().get(i);
        						if (tempProp.owner != null && tempProp.owner.getPlayerColor().equals(player.getPlayerColor())) {
        							owner.setRailroadsOwned(0);
        							int propCost = cost / 2;
        							player.addMoney(propCost);
        							tempProp.setOwner(null);
        							canAffordCount++;
        						}
        					}
        					
        					//For Utilities
        					else if (gameBoard.getLocations().get(i) instanceof Utility) {
        						int result = gameBoard.getDice1().getResult() + gameBoard.getDice2().getResult(); 
        						int utilCost = 0;
        						if(owner.getUtilitiesOwned() == 1)
        						{
        							utilCost = result*4;
        						}
        						else if(owner.getUtilitiesOwned() == 2)
        						{
        							utilCost = result*10;
        						}
        						Utility tempProp = (Utility) gameBoard.getLocations().get(i);
        						if (tempProp.owner != null && tempProp.owner.getPlayerColor().equals(player.getPlayerColor())) {
        							owner.setUtilitiesOwned(0);
        							int propCost = utilCost / 2;
        							player.addMoney(utilCost);
        							tempProp.setOwner(null);
        							canAffordCount++;
        						}
        					}
                    		
                    	}
                		
        				if(canAffordCount == 0) {
        					player.setBankrupt();
        				}
                	}
                }
            }
        }
        System.out.println("jkdsjfkdsjfkd");
        return false;
    }
    
  

    void purchaseHotel()
    {
        isHotel = true;
    }

    void purchaseHouse ()
    {
    	if (numberOfHouses < 4)
        {
            numberOfHouses++;
            if (numberOfHouses == 1)
                cost = getPayHouseCost();
            else if (numberOfHouses == 2)
                cost = getPayHouseCost() * 3;
            else if (numberOfHouses == 3)
                cost = getPayHouseCost() * 9;
            else if (numberOfHouses == 4)
                cost = (int) (getPayHotelCost() * .9);
            
            if(!hasPrinted){
            	UIConsole.print(owner + " has purchased his " + numberOfHouses + " house on the property " + this);
            }
        }
        else
            UIConsole.print("The user cannot purchase any more houses on the property " + this);
    }
    
    public void setOwner(Player player)
    {
    	this.owner = player;
    }
    
    public int getPurchaseCost () {
    	return this.purchaseCost;
    }

    @Override
    public String toString()
    {
        return name;
    }

	public int getPayHotelCost() {
		return payHotelCost;
	}

	public void setPayHotelCost(int payHotelCost) {
		this.payHotelCost = payHotelCost;
	}

	public int getPayHouseCost() {
		return payHouseCost;
	}

	public void setPayHouseCost(int payHouseCost) {
		this.payHouseCost = payHouseCost;
	}
}