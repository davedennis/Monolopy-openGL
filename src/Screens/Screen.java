package Screens;

import UI.UIConsole;

public abstract class Screen // TODO: You should just interface this by now
{
	public void run()
	{
		UIConsole.print("Override this method for every subclass. Run once per frame.");
	}
}
