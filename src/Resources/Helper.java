package Resources;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import Graphics.Drawable;
import Main.Player;
import UI.UIConsole;

public class Helper // Monopoly version
{
	private static double lastTime = 0;
	private static double delta = 0;
	private static int tick = 0;
	
	// Monopoly Specific
	private static String winner = "";

	public static void initializeDisplay()
	{
		try
		{	 
	        Display.setDisplayMode(new DisplayMode(Settings.getRES_X(), Settings.getRES_Y()));
			Display.setFullscreen(Settings.isFULLSCREEN());
			Display.setVSyncEnabled(Settings.isV_SYNC());
			Display.setTitle(Settings.getWINDOW_TITLE());
			Display.setResizable(true);
			Display.create(new PixelFormat(8,0,0,8));
						
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
						
			UIConsole.print("Created display: " + Display.getDisplayMode());
			UIConsole.print("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION) + "\n");
		}
		catch  (LWJGLException e)
		{
			e.printStackTrace();
			UIConsole.print("Display failed to create.");
			Display.destroy();
			System.exit(-1);
		}
	}
	
	public static void resizeDisplay()
	{
		try 
		{				
			Display.setDisplayMode(new DisplayMode(Settings.getRES_X(), Settings.getRES_Y()));
			Display.setFullscreen(Settings.isFULLSCREEN());
			Display.setVSyncEnabled(Settings.isV_SYNC());
			Display.setTitle(Settings.getWINDOW_TITLE());
			Display.setResizable(false);
			
			UIConsole.print("Resized display: " + Display.getDisplayMode());
		} 
		catch (LWJGLException e)
		{
			e.printStackTrace();
			UIConsole.print("Display failed to resize.");
			Display.destroy();
			System.exit(-1);
		}
	}
	
	public static void setGameProjection()
	{
		GL11.glViewport(0, 0, Settings.getRES_X(), Settings.getRES_Y());			
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Settings.getRES_X(), 0, Settings.getRES_Y(), -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
	
	public static void setTextProjection()
	{
		GL11.glViewport(0, 0, Settings.getRES_X(), Settings.getRES_Y());			
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Settings.getRES_X(), Settings.getRES_Y(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
	
	
	public static void dimDisplay(float time, float amount)
	{
		GL11.glColor4f(1.0f, 1.0f, 1.0f, amount);
	}
	
	public static void updateDelta()
	{
		double theTime = System.nanoTime();
		double newTime = theTime;
		double frameTime = newTime - lastTime;
		lastTime = theTime;
		
		delta = (frameTime / 1000000.0);
	}
	
	public static double getDelta()
	{
		return delta;
	}
	
	public static void updateTick()
	{
		tick++; // total number of frames since start
	}
	
	public static int getTick()
	{
		return tick;
	}
	
	////
	//// Monopoly Specific
	////
	
	public static void setOwnerColor(Player owner, Drawable ownerColor)
	{
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
	}
	
	public static boolean checkGameOver(ArrayList<Player> players)
	{
		int numPlayers = players.size();
    	Player ghetto = null;
    	for (Player player : players)
    	{
    		if (player.isBankrupt())
    		{
    			numPlayers--;
    		}
    		else {
				ghetto = player;
			}
    	}
    	if (numPlayers == 1)
    	{
    		UIConsole.print("Game over bitch. " + ghetto + " won the game.");
    		Helper.setWinner(ghetto + " won the game with $" + ghetto.getMoney());
    		return true;
    	}
    	
    	return false;
	}
	
	public static void setWinner(String theWinner) {
		winner = theWinner;
	}
	
	public static String getWinner() {
		return winner;
	}
}
