package Graphics;

public class Drawable
{
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	
	protected int textureIndex;

	public Drawable(float x, float y, float width, float height, String textureName)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		textureIndex = TextureManager.loadTextureIndex(textureName);
	}
	
	public void draw()
	{
		SpriteBatcher.draw(this);
	}
	
	public void setTexture(String textureName)
	{
		textureIndex = TextureManager.loadTextureIndex(textureName);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public int getTextureIndex() {
		return textureIndex;
	}

	public void setTextureIndex(int textureIndex) {
		this.textureIndex = textureIndex;
	}
}
