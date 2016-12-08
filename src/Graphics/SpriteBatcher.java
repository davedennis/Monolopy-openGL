package Graphics;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import Resources.Helper;
import Resources.Settings;

public class SpriteBatcher
{
	// private static float[] defaultTexCoords = new float[] { 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0 };
	private static int draws = 0;
	private static int maxDraws = 1000;
	private static float[] vertArray = new float[maxDraws*2*6];
	private static float[] texArray = new float[maxDraws*2*6];

	private static int vertIndex = 0;
	private static int texIndex = 0;
	private static int currentTex = 0;
   
	protected static int vertices = 6;
	protected static int vertexSize = 2;
	protected static int textureSize = 2;
	   
	private static FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertices * vertexSize * maxDraws);;
	private static FloatBuffer textureData = BufferUtils.createFloatBuffer(vertices * textureSize * maxDraws);;
	private static int vbo_vertex_handle = GL15.glGenBuffers();
	private static int vbo_texture_handle= GL15.glGenBuffers();
	
	public SpriteBatcher(int size)
	{
		vertArray = new float[size*2*6]; //number of objects * 2 floats per object * 6 vertices per object
		texArray = new float[size*2*6];
		vertexData = BufferUtils.createFloatBuffer(vertices * vertexSize * size);
		textureData = BufferUtils.createFloatBuffer(vertices * textureSize * size);
		vertIndex = 0;
		texIndex = 0;
		maxDraws = size;
		draws = 0; 
		
		vbo_vertex_handle = GL15.glGenBuffers();
		vbo_texture_handle = GL15.glGenBuffers();
	}
	   
	   public static void begin()
	   {
	      glEnableClientState(GL11.GL_VERTEX_ARRAY);
	      glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	   }   
	   public static void end()
	   {
	      render();    
	      glDisableClientState(GL11.GL_VERTEX_ARRAY);
	      glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	   }
	   
	   public static void beginDune()
	   {
		   glEnableClientState(GL11.GL_VERTEX_ARRAY);
		   glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	   }	   
	   public static void endDune()
	   {
		   renderDune();  
		   glDisableClientState(GL11.GL_VERTEX_ARRAY);
		   glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	   }
	  
	   public static void beginSpin()
	   {
		   glEnableClientState(GL11.GL_VERTEX_ARRAY);
		   glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	   }
	   public static void endSpin()
	   {
		   renderSpin();  
		   glDisableClientState(GL11.GL_VERTEX_ARRAY);
		   glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	   }
	   
	   public static void beginBubbles()
	   {
		   glEnableClientState(GL11.GL_VERTEX_ARRAY);
		   glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	   }
	   public static void endBubbles()
	   {
		   renderBubbles();  
		   glDisableClientState(GL11.GL_VERTEX_ARRAY);
		   glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	   }
	   
	   public static void beginHex()
	   {
		   glEnableClientState(GL11.GL_VERTEX_ARRAY);
		   glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	   }
	   public static void endHex()
	   {
		   renderHex();  
		   glDisableClientState(GL11.GL_VERTEX_ARRAY);
		   glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	   }
	   
	   private static void render()
	   {
		   //UIConsole.print("Draws: " + draws + "\tTexture : " + TextureManager.getTextureName(currentTex));
		   
		   	GL20.glUseProgram(ShaderUtility.defaultShader);
		   	
		   	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		   	GL20.glUseProgram(ShaderUtility.defaultShader);
			int colorUniform = GL20.glGetUniformLocation(ShaderUtility.defaultShader, "inputColor");
			GL20.glUniform4f(colorUniform, 1f, 1f, 1f, 1f);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			TextureManager.getTexture(currentTex).bind();
			int textureUniform = GL20.glGetUniformLocation(ShaderUtility.defaultShader, "texture1");
			GL20.glUniform1i(textureUniform, 0);
			
			vertexData.put(vertArray);
			textureData.put(texArray);
		   	vertexData.flip();
		   	textureData.flip();
		   	
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexData, GL15.GL_DYNAMIC_DRAW);
			GL11.glVertexPointer(vertexSize, GL11.GL_FLOAT, 0, 0);
	
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_texture_handle);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureData, GL15.GL_DYNAMIC_DRAW);
			GL11.glTexCoordPointer(textureSize, GL11.GL_FLOAT, 0, 0);
	
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertices * draws);
	        
	        vertexData.clear();
	        textureData.clear();
	        
	        vertIndex = 0;
	        texIndex = 0;
	        draws = 0;
	        GL20.glUseProgram(0);
	   }
	   
	   private static void renderDune() 
	   {
		   //UIConsole.print("Draws: " + draws + "\tTexture : " + TextureManager.getTextureName(currentTex));
		   
		   	GL20.glUseProgram(ShaderUtility.duneShader);
		   	
		   	GL20.glUseProgram(ShaderUtility.duneShader);
			int resolutionUniform = GL20.glGetUniformLocation(ShaderUtility.duneShader, "resolution");
			GL20.glUniform2f(resolutionUniform, Settings.getRES_X(), Settings.getRES_Y());
			int timeUniform = GL20.glGetUniformLocation(ShaderUtility.duneShader, "time");
			GL20.glUniform1f(timeUniform, (float) Helper.getTick() / 100);
			int mouseUniform = GL20.glGetUniformLocation(ShaderUtility.duneShader, "mouse");
			GL20.glUniform2f(mouseUniform, (float) Mouse.getX() / Settings.getRES_X(), (float) Mouse.getY() / Settings.getRES_Y());
			
			vertexData.put(vertArray);
			textureData.put(texArray);
		   	vertexData.flip();
		   	textureData.flip();
		   	
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexData, GL15.GL_DYNAMIC_DRAW);
			GL11.glVertexPointer(vertexSize, GL11.GL_FLOAT, 0, 0);
	
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_texture_handle);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureData, GL15.GL_DYNAMIC_DRAW);
			GL11.glTexCoordPointer(textureSize, GL11.GL_FLOAT, 0, 0);
	
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertices * draws);
	        
	        vertexData.clear();
	        textureData.clear();
	        
	        vertIndex = 0;
	        texIndex = 0;
	        draws = 0; 
	        GL20.glUseProgram(0);
	   }
	   
	   private static void renderSpin() 
	   {
		   //UIConsole.print("Draws: " + draws + "\tTexture : " + TextureManager.getTextureName(currentTex));
		   
		   	GL20.glUseProgram(ShaderUtility.spinShader);
		   	
		   	GL20.glUseProgram(ShaderUtility.spinShader);
			int resolutionUniform = GL20.glGetUniformLocation(ShaderUtility.spinShader, "resolution");
			GL20.glUniform2f(resolutionUniform, Settings.getRES_X(), Settings.getRES_Y());
			int timeUniform = GL20.glGetUniformLocation(ShaderUtility.spinShader, "time");
			GL20.glUniform1f(timeUniform, (float) Helper.getTick() / 100);
			int mouseUniform = GL20.glGetUniformLocation(ShaderUtility.spinShader, "mouse");
			GL20.glUniform2f(mouseUniform, (float) Mouse.getX() / Settings.getRES_X(), (float) Mouse.getY() / Settings.getRES_Y());
			
			vertexData.put(vertArray);
			textureData.put(texArray);
		   	vertexData.flip();
		   	textureData.flip();
		   	
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexData, GL15.GL_DYNAMIC_DRAW);
			GL11.glVertexPointer(vertexSize, GL11.GL_FLOAT, 0, 0);
	
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_texture_handle);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureData, GL15.GL_DYNAMIC_DRAW);
			GL11.glTexCoordPointer(textureSize, GL11.GL_FLOAT, 0, 0);
	
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertices * draws);
	        
	        vertexData.clear();
	        textureData.clear();
	        
	        vertIndex = 0;
	        texIndex = 0;
	        draws = 0; 
	        GL20.glUseProgram(0);
	   }
	   
	   private static void renderBubbles() 
	   {
		   //UIConsole.print("Draws: " + draws + "\tTexture : " + TextureManager.getTextureName(currentTex));
		   
		   	GL20.glUseProgram(ShaderUtility.bubblesShader);
		   	
		   	GL20.glUseProgram(ShaderUtility.bubblesShader);
			int resolutionUniform = GL20.glGetUniformLocation(ShaderUtility.bubblesShader, "resolution");
			GL20.glUniform2f(resolutionUniform, Settings.getRES_X(), Settings.getRES_Y());
			int timeUniform = GL20.glGetUniformLocation(ShaderUtility.bubblesShader, "time");
			GL20.glUniform1f(timeUniform, (float) Helper.getTick() / 100);
			int mouseUniform = GL20.glGetUniformLocation(ShaderUtility.bubblesShader, "mouse");
			GL20.glUniform2f(mouseUniform, (float) Mouse.getX() / Settings.getRES_X(), (float) Mouse.getY() / Settings.getRES_Y());
			
			vertexData.put(vertArray);
			textureData.put(texArray);
		   	vertexData.flip();
		   	textureData.flip();
		   	
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexData, GL15.GL_DYNAMIC_DRAW);
			GL11.glVertexPointer(vertexSize, GL11.GL_FLOAT, 0, 0);
	
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_texture_handle);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureData, GL15.GL_DYNAMIC_DRAW);
			GL11.glTexCoordPointer(textureSize, GL11.GL_FLOAT, 0, 0);
	
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertices * draws);
	        
	        vertexData.clear();
	        textureData.clear();
	        
	        vertIndex = 0;
	        texIndex = 0;
	        draws = 0; 
	        GL20.glUseProgram(0);
	   }
	   
	   private static void renderHex() 
	   {
		   //UIConsole.print("Draws: " + draws + "\tTexture : " + TextureManager.getTextureName(currentTex));
		   
		   	GL20.glUseProgram(ShaderUtility.hexShader);
		   	
		   	GL20.glUseProgram(ShaderUtility.hexShader);
			int resolutionUniform = GL20.glGetUniformLocation(ShaderUtility.hexShader, "resolution");
			GL20.glUniform2f(resolutionUniform, Settings.getRES_X(), Settings.getRES_Y());
			int timeUniform = GL20.glGetUniformLocation(ShaderUtility.hexShader, "time");
			GL20.glUniform1f(timeUniform, (float) Helper.getTick() / 100);
			int mouseUniform = GL20.glGetUniformLocation(ShaderUtility.hexShader, "mouse");
			GL20.glUniform2f(mouseUniform, (float) Mouse.getX() / Settings.getRES_X(), (float) Mouse.getY() / Settings.getRES_Y());
			
			vertexData.put(vertArray);
			textureData.put(texArray);
		   	vertexData.flip();
		   	textureData.flip();
		   	
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexData, GL15.GL_DYNAMIC_DRAW);
			GL11.glVertexPointer(vertexSize, GL11.GL_FLOAT, 0, 0);
	
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_texture_handle);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureData, GL15.GL_DYNAMIC_DRAW);
			GL11.glTexCoordPointer(textureSize, GL11.GL_FLOAT, 0, 0);
	
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertices * draws);
	        
	        vertexData.clear();
	        textureData.clear();
	        
	        vertIndex = 0;
	        texIndex = 0;
	        draws = 0; 
	        GL20.glUseProgram(0);
	   }
	   
	   public static void draw(float x, float y, float width, float height, int texID)
	   {
	      if(texID != currentTex)
	      {
	  		  render();
	  		  currentTex = texID; 
	  		  GL20.glUseProgram(0);
	      }
	      if(draws == maxDraws)
	      {
	    	  render();
	      }
	      
	      texArray[texIndex] = 0;
	      texArray[texIndex+1] = 1f;
	      texArray[texIndex+2] = 1f;
	      texArray[texIndex+3] = 1f;
	      texArray[texIndex+4] = 1f;
	      texArray[texIndex+5] = 0;
	      texArray[texIndex+6] = 0;
	      texArray[texIndex+7] = 1f;
	      texArray[texIndex+8] = 0;
	      texArray[texIndex+9] = 0;
	      texArray[texIndex+10] = 1f;
	      texArray[texIndex+11] = 0;
	      
	      vertArray[vertIndex] = x;
	      vertArray[vertIndex+1] = y;      
	      vertArray[vertIndex+2] = x + width;
	      vertArray[vertIndex+3] = y;     
	      vertArray[vertIndex+4] = x + width;
	      vertArray[vertIndex+5] = y + height;      
	      vertArray[vertIndex+6] = x;
	      vertArray[vertIndex+7] = y;    
	      vertArray[vertIndex+8] = x;
	      vertArray[vertIndex+9] = y + height;      
	      vertArray[vertIndex+10] = x + width;
	      vertArray[vertIndex+11] = y + height;
	       
	      vertIndex+=12;
	      texIndex+=12;
	      draws++; 
	   }
	   
	   public static void draw(float x, float y, float width, float height, int texID, float[] texCoords)
	   {
	      if(texID != currentTex)
	      {
	  		  render();
	  		  currentTex = texID; 
	  		  GL20.glUseProgram(0);
	      }
	      if(draws == maxDraws)
	      {
	    	  render();
	      }
	      
	      texArray[texIndex] = texCoords[0];
	      texArray[texIndex+1] = texCoords[1];
	      texArray[texIndex+2] = texCoords[2];
	      texArray[texIndex+3] = texCoords[3];
	      texArray[texIndex+4] = texCoords[4];
	      texArray[texIndex+5] = texCoords[5];
	      texArray[texIndex+6] = texCoords[6];
	      texArray[texIndex+7] = texCoords[7];
	      texArray[texIndex+8] = texCoords[8];
	      texArray[texIndex+9] = texCoords[9];
	      texArray[texIndex+10] = texCoords[10];
	      texArray[texIndex+11] = texCoords[11];
	      
	      vertArray[vertIndex] = x;
	      vertArray[vertIndex+1] = y;      
	      vertArray[vertIndex+2] = x + width;
	      vertArray[vertIndex+3] = y;     
	      vertArray[vertIndex+4] = x + width;
	      vertArray[vertIndex+5] = y + height;      
	      vertArray[vertIndex+6] = x;
	      vertArray[vertIndex+7] = y;    
	      vertArray[vertIndex+8] = x;
	      vertArray[vertIndex+9] = y + height;      
	      vertArray[vertIndex+10] = x + width;
	      vertArray[vertIndex+11] = y + height;
	       
	      vertIndex+=12;
	      texIndex+=12;
	      draws++; 
	   }
	   
	   public static void draw(Drawable object)
	   {
		      if(object.textureIndex != currentTex)
		      {
		  		  render();
		  		  currentTex = object.textureIndex; 
		  		  GL20.glUseProgram(0);
		      }
		      if(draws == maxDraws)
		      {
		    	  render();
		      }
		     		      
		      texArray[texIndex] = 0;
		      texArray[texIndex+1] = 1f;
		      texArray[texIndex+2] = 1f;
		      texArray[texIndex+3] = 1f;
		      texArray[texIndex+4] = 1f;
		      texArray[texIndex+5] = 0;
		      texArray[texIndex+6] = 0;
		      texArray[texIndex+7] = 1f;
		      texArray[texIndex+8] = 0;
		      texArray[texIndex+9] = 0;
		      texArray[texIndex+10] = 1f;
		      texArray[texIndex+11] = 0;
		      
		      vertArray[vertIndex] = object.x;
		      vertArray[vertIndex+1] = object.y;      
		      vertArray[vertIndex+2] = object.x + object.width;
		      vertArray[vertIndex+3] = object.y;     
		      vertArray[vertIndex+4] = object.x + object.width;
		      vertArray[vertIndex+5] = object.y + object.height;      
		      vertArray[vertIndex+6] = object.x;
		      vertArray[vertIndex+7] = object.y;    
		      vertArray[vertIndex+8] = object.x;
		      vertArray[vertIndex+9] = object.y + object.height;      
		      vertArray[vertIndex+10] = object.x + object.width;
		      vertArray[vertIndex+11] = object.y + object.height;
		       
		      vertIndex+=12;
		      texIndex+=12;
		      draws++; 
	   }
	   
	   public static void drawDune(Drawable object)
	   {
		      if(object.textureIndex != currentTex)
		      {
		  		  renderDune();
		  		  currentTex = object.textureIndex; 
		  		  GL20.glUseProgram(0);
		      }
		      if(draws == maxDraws)
		      {
		    	  renderDune();
		      }
		     		      
		      texArray[texIndex] = 0;
		      texArray[texIndex+1] = 1f;
		      texArray[texIndex+2] = 1f;
		      texArray[texIndex+3] = 1f;
		      texArray[texIndex+4] = 1f;
		      texArray[texIndex+5] = 0;
		      texArray[texIndex+6] = 0;
		      texArray[texIndex+7] = 1f;
		      texArray[texIndex+8] = 0;
		      texArray[texIndex+9] = 0;
		      texArray[texIndex+10] = 1f;
		      texArray[texIndex+11] = 0;
		      
		      vertArray[vertIndex] = object.x;
		      vertArray[vertIndex+1] = object.y;      
		      vertArray[vertIndex+2] = object.x + object.width;
		      vertArray[vertIndex+3] = object.y;     
		      vertArray[vertIndex+4] = object.x + object.width;
		      vertArray[vertIndex+5] = object.y + object.height;      
		      vertArray[vertIndex+6] = object.x;
		      vertArray[vertIndex+7] = object.y;    
		      vertArray[vertIndex+8] = object.x;
		      vertArray[vertIndex+9] = object.y + object.height;      
		      vertArray[vertIndex+10] = object.x + object.width;
		      vertArray[vertIndex+11] = object.y + object.height;
		       
		      vertIndex+=12;
		      texIndex+=12;
		      draws++; 
	   }
	   
	   public static void draw(Drawable object, double rotation)
	   {
		      if(object.textureIndex != currentTex)
		      {
		  		  render();
		  		  currentTex = object.textureIndex; 
		  		  GL20.glUseProgram(0);
		      }
		      if(draws == maxDraws)
		      {
		    	  render();
		      }
		      
		      final float p1x = -object.width / 2;
		      final float p1y = -object.height / 2;
		      final float p2x = object.width / 2;
		      final float p2y = -object.height / 2;
		      final float p3x = object.width / 2;
		      final float p3y = object.height / 2;
		      final float p4x = -object.width / 2;
		      final float p4y = -object.height / 2;
		      final float p5x = -object.width / 2;
		      final float p5y = object.height / 2;
		      final float p6x = object.width / 2;
		      final float p6y = object.height / 2;
		      
		      final float widthOffset = object.width / 2;
		      final float heightOffset = object.height / 2;
		      
		      rotation = rotation * 0.0174532925;
		      
		      if (rotation != 0) 
		      {
		          final float cos = (float) Math.cos(rotation);
		          final float sin = (float) Math.sin(rotation);	          
			      
			      texArray[texIndex] = 0;
			      texArray[texIndex+1] = 1f;
			      texArray[texIndex+2] = 1f;
			      texArray[texIndex+3] = 1f;
			      texArray[texIndex+4] = 1f;
			      texArray[texIndex+5] = 0;
			      texArray[texIndex+6] = 0;
			      texArray[texIndex+7] = 1f;
			      texArray[texIndex+8] = 0;
			      texArray[texIndex+9] = 0;
			      texArray[texIndex+10] = 1f;
			      texArray[texIndex+11] = 0;
			      
			      vertArray[vertIndex] = object.x + (cos * p1x) - (sin * p1y) + widthOffset;
			      vertArray[vertIndex+1] = object.y + (sin * p1x) + (cos * p1y) + heightOffset;      
			      vertArray[vertIndex+2] = object.x + (cos * p2x) - (sin * p2y) + widthOffset;
			      vertArray[vertIndex+3] = object.y + (sin * p2x) + (cos * p2y) + heightOffset; 
			      vertArray[vertIndex+4] = object.x + (cos * p3x) - (sin * p3y) + widthOffset;
			      vertArray[vertIndex+5] = object.y + (sin * p3x) + (cos * p3y) + heightOffset;     
			      vertArray[vertIndex+6] = object.x + (cos * p4x) - (sin * p4y) + widthOffset;
			      vertArray[vertIndex+7] = object.y + (sin * p4x) + (cos * p4y) + heightOffset;  
			      vertArray[vertIndex+8] = object.x + (cos * p5x) - (sin * p5y) + widthOffset;
			      vertArray[vertIndex+9] = object.y + (sin * p5x) + (cos * p5y) + heightOffset;       
			      vertArray[vertIndex+10] = object.x + (cos * p6x) - (sin * p6y) + widthOffset;
			      vertArray[vertIndex+11] = object.y + (sin * p6x) + (cos * p6y) + heightOffset;
		      }
		      else
		      {
		    	  texArray[texIndex] = 0;
			      texArray[texIndex+1] = 1f;
			      texArray[texIndex+2] = 1f;
			      texArray[texIndex+3] = 1f;
			      texArray[texIndex+4] = 1f;
			      texArray[texIndex+5] = 0;
			      texArray[texIndex+6] = 0;
			      texArray[texIndex+7] = 1f;
			      texArray[texIndex+8] = 0;
			      texArray[texIndex+9] = 0;
			      texArray[texIndex+10] = 1f;
			      texArray[texIndex+11] = 0;
			      
			      vertArray[vertIndex] = object.x;
			      vertArray[vertIndex+1] = object.y;      
			      vertArray[vertIndex+2] = object.x + object.width;
			      vertArray[vertIndex+3] = object.y;     
			      vertArray[vertIndex+4] = object.x + object.width;
			      vertArray[vertIndex+5] = object.y + object.height;      
			      vertArray[vertIndex+6] = object.x;
			      vertArray[vertIndex+7] = object.y;    
			      vertArray[vertIndex+8] = object.x;
			      vertArray[vertIndex+9] = object.y + object.height;      
			      vertArray[vertIndex+10] = object.x + object.width;
			      vertArray[vertIndex+11] = object.y + object.height;
		      }
		      
		      vertIndex+=12;
		      texIndex+=12;
		      draws++; 
	   }
}