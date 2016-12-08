package Locations;

import org.lwjgl.input.Keyboard;

import Graphics.Drawable;
import Main.GameBoard;
import Main.Player;
import Resources.Helper;
import UI.UIConsole;

public class Railroad extends GameLocation
{
	String name;
	Player owner;
	Drawable ownerColor;

	int purchasePrice = 200;
	int rentPrice;
	boolean hasPrinted;
	
	 public Railroad(float x, float y, float width, float height, String textureName, int location, String name) 
	 {
		 super(x, y, width, height, textureName, location);
		 this.name = name;
		 
		 ownerColor = new Drawable(x + 5, y, width - 10, 20, "res/images/colors/white.png");
	 }
	 
	 @Override
	 public void draw()
	 {
		 super.draw();
		 Helper.setOwnerColor(owner, ownerColor);
		 ownerColor.draw();
	 }
	 
	 @Override
	 public boolean execute(Player player, GameBoard gameBoard)
	 {
		 if (owner == null)
		 {
			 // StartAI
			 if (player.isAI())
			 {
				 player.buyRailroad(this);
				 return true;
			 }
			 //EndAI
			 
			 if(!hasPrinted){
				 UIConsole.print(player + " has landed on the unowned railroad " + this);
				 UIConsole.print("Press B to buy or N to not buy");
				 hasPrinted = true;
			 }
			 if (Keyboard.isKeyDown(Keyboard.KEY_B))
			 {
				 int cost = 0;
				 
				 if (player.getRailroadsOwned() == 1) {
					 cost = 25;
				 } else if (player.getRailroadsOwned() == 2) {
					 cost = 50;
				 } else if (player.getRailroadsOwned() == 3) {
					 cost = 100;
				 } else if (player.getRailroadsOwned() == 4) {
					 cost = 200;
				 }
					 
				 if (player.checkIfcanAfford(cost)) {
					 player.buyRailroad(this);
					 hasPrinted = false;
					 return true;
				 }
				 else 
				 {
					 UIConsole.print("You don't have enough money to buy this property");
					 return true;
				 }
				 

			 }
			 else  if (Keyboard.isKeyDown(Keyboard.KEY_N))
			 {
				 // Auction shit
				 hasPrinted = false;
				 return true;
			 }
		 }
		 else if (owner == player)
		 {
			if(!hasPrinted){
				UIConsole.print(player + " has landed on their own " + this);
			}
			return true;
		 }
		 else
		{
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
			 
			if (player.checkIfcanAfford(cost)) {
				
				player.payPlayer(owner, cost);
				UIConsole.print("Player " + player + " has payed " + owner + " $" + cost);
				return true;
			}
			// Mortgaging
			else {
				int canAffordCount = 0;
				for (int i = 0; i < gameBoard.getLocations().size(); i++) {
					// For houses

					// For property, not houses/hotels
					if (gameBoard.getLocations().get(i) instanceof Property) {

						Property tempProp = (Property) gameBoard.getLocations().get(i);
						System.out.println(tempProp.owner);
						if (tempProp.owner != null && tempProp.owner.getPlayerColor().equals(player.getPlayerColor())) {
							if (tempProp.isHotel) {
								int propCost = tempProp.getPayHotelCost() / 2;
								player.addMoney(propCost);
								tempProp.isHotel = false;
							}

							for (int j = 0; j < tempProp.numberOfHouses; j++) {
								int propCost = tempProp.getPayHouseCost() / 2;
								player.addMoney(propCost);
							}

							tempProp.numberOfHouses = 0;

							int propCost = tempProp.getPurchaseCost() / 2;
							player.addMoney(propCost);
							tempProp.setOwner(null);
							canAffordCount++;
						}

					}
					
					//For Railroads
					else if (gameBoard.getLocations().get(i) instanceof Railroad) {
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
				
				if (canAffordCount == 0) {
					player.setBankrupt();
				}
				
			}

		}
		return false;
	 }
	 
	 public void setOwner(Player player)
	 {
		 owner = player;
	 }
	 
	 @Override
	 public String toString()
	 {
		 return name;
	 }
}
