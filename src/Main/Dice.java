package Main;

import java.util.Random;

import Graphics.Drawable;


public class Dice extends Drawable
{
    Random random;
    int result;

    public Dice(int x, int y, int width, int height, String textureName)
    {
    	super(x, y, width, height, textureName);
        random = new Random();
    }

    public int roll()
    {
        int randomNum = random.nextInt(6) + 1;
        result = randomNum;
        
        if (result == 1)
        	setTexture("res/images/dice1.png");
        else if (result == 2)
        	setTexture("res/images/dice2.png");
        else if (result == 3)
        	setTexture("res/images/dice3.png");
        else if (result == 4)
        	setTexture("res/images/dice4.png");
        else if (result == 5)
        	setTexture("res/images/dice5.png");
        else if (result == 6)
        	setTexture("res/images/dice6.png");
        
        return result;
    }
    
    public int getResult(){
    	return result;
    }
    public int rollRigged()
    {
    	result = 1;
    	if (result == 1)
        	setTexture("res/images/dice1.png");
    	
    	return result;
    }
}
