package Main;

import Locations.Property;
import Locations.Railroad;
import Locations.Utility;
import Graphics.Drawable;
import Graphics.SpriteBatcher;
import Resources.PlayerColor;
import UI.UIConsole;

public class Player extends Drawable
{
	private int location = 0;
	private int money = 1500;
	private int daysInJail = 0;
	private int railroadsOwned = 0;
	private int utilitiesOwned = 0;
	private int getOutOfJailCards = 0;
	private boolean inJail = false;
	private boolean isBankrupt = false;
	
    PlayerColor playerColor;
    boolean playing = true;
    boolean ai;
    
    // Terrible auction code TODO: encapsulate
    public boolean hasBid = false;
    public boolean removed = false;
	
	public Player(float x, float y, float width, float height, String textureName, int location, PlayerColor playerColor) 
	{
		super(x, y, width, height, textureName);
		
		this.location = location;
		this.playerColor = playerColor;
	}
	
	public Player(float x, float y, float width, float height, String textureName, int location, PlayerColor playerColor, boolean ai) 
	{
		super(x, y, width, height, textureName);
		
		this.location = location;
		this.playerColor = playerColor;
		
		this.ai = ai;
	}
	
	@Override
	public void draw()
	{
		if (playerColor == PlayerColor.RED)
			SpriteBatcher.draw(x, y, width, height, textureIndex);
		else if (playerColor == PlayerColor.BLUE)
			SpriteBatcher.draw(x+25, y, width, height, textureIndex);
		else if (playerColor == PlayerColor.GREEN)
			SpriteBatcher.draw(x+50, y, width, height, textureIndex);
		else if (playerColor == PlayerColor.YELLOW)
			SpriteBatcher.draw(x+75, y, width, height, textureIndex);
	}
	
	public int getGetOutOfJailCards()
	{
		return getOutOfJailCards;
	}
	
	public void addGetOutOfJailCard ()
	{	
		getOutOfJailCards++;
		UIConsole.print(this + " has recieved a get out of jail free card.");
	}
	
	public void useGetOutOfJailCard ()
	{
		if (!inJail)
		{
			UIConsole.print(this + " is not in jail. You can only use the get out of jail free card if you are currently in jail.");
			return;
		}
		else if (getOutOfJailCards < 1)
		{
			UIConsole.print("You do not own a get out of jail free card.");
			return;
		}
		else
		{
			inJail = false;
			daysInJail = 0;
			UIConsole.print(this + " used a get out of jail free card and has been removed from jail.");
			this.getOutOfJailCards--;
		}
	}
	
	public void moveToJail(GameBoard gameBoard)
	{
		gameBoard.addToJail(this);
		inJail = true;
		daysInJail++;
		location = 9;
		UIConsole.print(this + " has been moved to jail.");
		if (this.getOutOfJailCards >0) {
			this.useGetOutOfJailCard();
		}
	}
	
	public void stayInJail()
	{
		if (daysInJail == 2) // 3rd day in jail
			removeFromJail();
		daysInJail++;
	}
	
	public void removeFromJail()
	{
		inJail = false;
		daysInJail = 0;
		payBank(50);
		UIConsole.print(this + " has been removed from jail.");
	}
	
	public void move(int distanceMoved, GameBoard gameBoard)
    {
        int orig = location;
        location = (location + distanceMoved) % 40;
        if (location < orig)
        {
            money += 200;
            UIConsole.print(this + " was given $200 for passing go.");
        }
        
        if(gameBoard.getLocationX(location) == 1000.0 && gameBoard.getLocationY(location) == 0.0)
        {
        	this.moveToJail(gameBoard);
        	//for testing uncomment if you ant to move by one and skip jail.
        	
        	/*setX((float) 900.0);
        	setY((float) 0.0);*/
        } else 
        {
            setX(gameBoard.getLocationX(location));
            setY(gameBoard.getLocationY(location));
        }
    }
	
	boolean execute(GameBoard gameBoard)
	{
		return gameBoard.getLocation(location).execute(this, gameBoard); // TODO: lololol. Obviously some refactoring is in order. Consider setting GameBoard arrayLists as default
	}

	public void moveBackwards(int location, GameBoard gameBoard)
	{
		this.location = location % 40;
		setX(gameBoard.getLocationX(location));
        setY(gameBoard.getLocationY(location));
	}
	
    public void moveAbsolute(int location, GameBoard gameBoard)
    {
    	int orig = this.location;
        this.location = location % 40;
        if(this.location < orig){
        	 money += 200;
             UIConsole.print(this + " was given $200 for passing go.");
        }
        
        setX(gameBoard.getLocationX(location));
        setY(gameBoard.getLocationY(location));
    }
    
    
    public void payBank(int amount)
    {
    	money -= amount;
    	UIConsole.print(this + " has paid the bank $" + amount);
    	checkBankruptcy();
    }
    
    public void addMoney (int amount)
    {
    	money += amount;
    	UIConsole.print(this + " has recieved $" + amount);
    }

    public void payPlayer(Player player, int amount)
    {
        money -= amount;
        player.money += amount;
        UIConsole.print(this + " has payed " + player + " $" + amount);
        checkBankruptcy();
    }
    
    public boolean buyProperty(Property property)
    {
    	if(money - property.getPurchaseCost() >= 0){
    		money-= property.getPurchaseCost();
    		property.setOwner(this);
    		UIConsole.print(this + " has bought " + property + " for $" + property.getPurchaseCost());
        	checkBankruptcy();
        	return false;
    	}else
    	{
    		UIConsole.print("You don't have enough money to buy this property");
    		return true;
    	}
    }
    
    public void buyAuctionProperty(int cost, Property property)
    {
    	money -= cost;
    	property.setOwner(this);
    	UIConsole.print(this + " has bought " + property + " for $" + cost);
    }
    
    public void buyRailroad(Railroad railroad)
    {
    	money -= 200;
    	railroad.setOwner(this);
    	railroadsOwned++;
    	UIConsole.print(this + " has bought " + railroad + " for $200");

    	checkBankruptcy();
    }
    
    public void buyUtility(Utility utility)
    {
    	money -= 150;
    	utility.setOwner(this);
    	utilitiesOwned++;
    	System.out.println(this + " has bought " + utility + " for $150");
    	UIConsole.print(this + " has bought " + utility + " for $150");
    	checkBankruptcy();
    }
    
    public boolean isBankrupt()
    {
    	return isBankrupt;
    }
    
    boolean isInJail()
    {
    	return inJail;
    }
    
    int getdaysInJail()
    {
    	return daysInJail;
    }
    
    void checkBankruptcy() // Call anytime money is paid
    {
    	if(this.money <= 0)
    	{
    		System.out.println(this + " has become bankrupt and has left the game.");
    		UIConsole.print(this + " has become bankrupt and has left the game.");
    		isBankrupt = true;
    	}
    }
    
    public boolean checkIfcanAfford(int amount)
    {
    	if(amount > this.money)
    	{
    		return false;
    	}
    	
    	return true;
    }
    
    public int getMoney()
    { 
    	return money; 
    }
    
    public int getLocation()
    {
    	return location;
    }
    
    public int getUtilitiesOwned()
    {
    	return utilitiesOwned;
    }
    
    public void setUtilitiesOwned(int numOwned) 
    {
    	utilitiesOwned = numOwned;
    }
    
    public int getRailroadsOwned()
    {
    	return railroadsOwned;
    }
    
    public void setRailroadsOwned(int numOwned)
    {
    	railroadsOwned = numOwned;
    }
    
    public PlayerColor getPlayerColor()
    {
    	return playerColor;
    }
    
    @Override
    public String toString()
    {
    	if (ai)
    		return "AI" + playerColor.toString();
    	return playerColor.toString();
    }

	public void setBankrupt() {
		isBankrupt = true;
	}
	
	public boolean isAI() {
		return ai;
	}
}
