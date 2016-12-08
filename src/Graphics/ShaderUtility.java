
package Graphics;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderUtility
{
    public static int defaultShader = 0;
    public static int spinShader = 0;
    public static int bubblesShader = 0;
    public static int duneShader = 0;
    public static int hexShader = 0;
    public static int blackandwhiteShader = 0;

    public static void createDefaultShaders()
    {
    	int defaultVert = 0;
    	int defaultFrag = 0;
    	int spinFrag = 0;
    	int bubblesFrag = 0;
    	int duneFrag = 0;
    	int hexFrag = 0;
    	int blackandwhiteFrag = 0;
    	
    	try
    	{
    		defaultVert = createShader("res/shaders/default.vert", GL20.GL_VERTEX_SHADER);
    		defaultFrag = createShader("res/shaders/default.frag", GL20.GL_FRAGMENT_SHADER);
            
    		spinFrag = createShader("res/shaders/spin.frag", GL20.GL_FRAGMENT_SHADER);
            bubblesFrag = createShader("res/shaders/bubbles.frag", GL20.GL_FRAGMENT_SHADER);
            duneFrag = createShader("res/shaders/dune.frag", GL20.GL_FRAGMENT_SHADER);
            hexFrag = createShader("res/shaders/hex.frag", GL20.GL_FRAGMENT_SHADER);
            blackandwhiteFrag = createShader("res/shaders/blackandwhite.frag", GL20.GL_FRAGMENT_SHADER);

    	}
    	catch(Exception exc) 
    	{
    		exc.printStackTrace();
    		return;
    	}
    	finally
    	{
    		if(defaultVert == 0 || defaultFrag == 0 || spinFrag == 0 || bubblesFrag == 0)
    			System.exit(-1);
    	}

    	defaultShader = GL20.glCreateProgram();
    	if(defaultShader == 0)
        	System.exit(-1);
        GL20.glAttachShader(defaultShader, defaultVert);
        GL20.glAttachShader(defaultShader, defaultFrag);
        GL20.glLinkProgram(defaultShader);
        
        spinShader = GL20.glCreateProgram();
        if (spinShader == 0)
        	System.exit(-1);
        GL20.glAttachShader(spinShader, defaultVert);
        GL20.glAttachShader(spinShader, spinFrag);
        GL20.glLinkProgram(spinShader);
        
        bubblesShader = GL20.glCreateProgram();
        if (bubblesShader == 0)
        	System.exit(-1);
        GL20.glAttachShader(bubblesShader, defaultVert);
        GL20.glAttachShader(bubblesShader, bubblesFrag);
        GL20.glLinkProgram(bubblesShader);
        
        duneShader = GL20.glCreateProgram();
        if (duneShader == 0)
        	System.exit(-1);
        GL20.glAttachShader(duneShader, defaultVert);
        GL20.glAttachShader(duneShader, duneFrag);
        GL20.glLinkProgram(duneShader);
        
        hexShader = GL20.glCreateProgram();
        if (hexShader == 0)
        	System.exit(-1);
        GL20.glAttachShader(hexShader, defaultVert);
        GL20.glAttachShader(hexShader, hexFrag);
        GL20.glLinkProgram(hexShader);
        
        blackandwhiteShader = GL20.glCreateProgram();
        if (blackandwhiteShader == 0)
        	System.exit(-1);
        GL20.glAttachShader(blackandwhiteShader, defaultVert);
        GL20.glAttachShader(blackandwhiteShader, blackandwhiteFrag);
        GL20.glLinkProgram(blackandwhiteShader);
    }
   
    private static int createShader(String filename, int shaderType) throws Exception
    {
    	int shader = 0;
    	try
    	{
	        shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
	        
	        if(shader == 0)
	        	return 0;
	        
	        ARBShaderObjects.glShaderSourceARB(shader, readShaderFile(filename));
	        ARBShaderObjects.glCompileShaderARB(shader);
	        
	        if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
	            throw new RuntimeException("Error creating shader: " + getLogInfo(shader));
	        
	        return shader;
    	}
    	catch(Exception e)
    	{
    		ARBShaderObjects.glDeleteObjectARB(shader);
    		throw e;
    	}
    }
    
    public static String getLogInfo(int obj)
    {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }
    
    private static String readShaderFile(String filename) throws Exception
    {
        StringBuilder source = new StringBuilder();
        
        FileInputStream in = new FileInputStream(filename);
        
        Exception exception = null;
        
        BufferedReader reader;
        try
        {
            reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            
            Exception innerExc= null;
            try 
            {
            	String line;
                while((line = reader.readLine()) != null)
                    source.append(line).append('\n');
            }
            catch(Exception exc)
            {
            	exception = exc;
            }
            finally
            {
            	try
            	{
            		reader.close();
            	}
            	catch(Exception exc) 
            	{
            		if(innerExc == null)
            			innerExc = exc;
            		else
            			exc.printStackTrace();
            	}
            }
            
            if(innerExc != null)
            	throw innerExc;
        }
        catch(Exception exc)
        {
        	exception = exc;
        }
        finally
        {
        	try 
        	{
        		in.close();
        	}
        	catch(Exception exc) 
        	{
        		if(exception == null)
        			exception = exc;
        		else
					exc.printStackTrace();
        	}
        	
        	if(exception != null)
        		throw exception;
        }
        
        return source.toString();
    }
}