package UI;

import java.util.ArrayList;
import org.newdawn.slick.Color;

public class UIConsole 
{
	private static ArrayList<UIText> messages = new ArrayList<UIText>();
	private static Color fontColor = Color.black;
	private static int fontSize = 24;
	
	private static int x = 115;
	private static int y = 700;
	private static int numDisplayed = 10;
	private static int currentIndex = 0;
	private static String lastMessage = "lastMessage.ThisWillGetOverwrittenAndAllThat. INeedToMakeADecentUISometime. ThisIsTerrible";
	
	/*
	public UIConsole(int x, int y, int numDisplayed, int fontSize)
	{
		this.x = x;
		this.y = y;
		numDisplayed = numDisplayed;
		this.fontSize = fontSize;
	}
	*/
	
	public static void print(String message)
	{
		if (!message.equals(lastMessage))
		{
			messages.add(new UIText(0, 0, message, "res/fonts/calibrib.ttf", fontSize, fontColor, "console"));
			System.out.println(message);
			lastMessage = message;
			if (currentIndex < (messages.size() - numDisplayed))
				currentIndex++;
		}
	}
	
	public static void display()
	{
		if (messages.size() > 0)
		{
			for (int i = 0; i < numDisplayed; i++)
			{
				if (i < messages.size())
				{
					int loc = currentIndex + i;
					messages.get(loc).setX(x);
					messages.get(loc).setY(y + ((i * fontSize) + 5)); // 5 adds some space between them
					messages.get(loc).draw();
				}
			}
		}
	}
	
	public static void scrollDown()
	{
		if (currentIndex < (messages.size() - numDisplayed))
			currentIndex++;		
	}
	
	public static void scrollUp()
	{
		if (currentIndex > 0)
			currentIndex--;		
	}
	
	public static void clear()
	{
		messages.clear();
		currentIndex = 0;
	}
}
