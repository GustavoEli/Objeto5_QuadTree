import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Lucas Saldanha Couto
 */

public class Particle {
    
    public static float SIZE = 4;
    
    public BoundingVolume bv;
    public float posX, posY;
    public boolean isColliding;
    
    public Particle(float x, float y)
    {
        this.posX = x;
        this.posY = y;
        this.bv = new BoundingVolume(x - SIZE, y - SIZE, x + SIZE, y + SIZE);
        this.bv.particle = this;
    }
    
    public void setPos(float posX, float posY)
    {
        if (posX < 0) posX = 0;
	if (posY < 0) posY = 0;
	if (posX > Main.WIDTH) posX = Main.WIDTH;
	if (posY > Main.HEIGHT) posY = Main.HEIGHT;
        
        this.posX = posX;
        this.posY = posY;
        this.bv.left = posX - SIZE;
        this.bv.top = posY - SIZE;
        this.bv.right = posX + SIZE;
        this.bv.bottom = posY + SIZE;
    }
    
    private void move(float dirX, float dirY)
    {
        setPos(this.posX + dirX, this.posY + dirY);
    }
    
    public void update(long elapsedTime)
    {
        this.isColliding = false;
        
        float x = (float)( Math.random() - 0.5f ) * elapsedTime;
        float y = (float)( Math.random() - 0.5f ) * elapsedTime;
        move(x, y);
    }
    
    public void onCollision()
    {
        this.isColliding = true;
    }
    
    public void draw(Graphics g)
    {
        Color c;
        if (this.isColliding)
            c = Color.red;
        else
            c = Color.blue;
        
        g.setColor(c);
        g.fillRect((int)this.bv.left, (int)this.bv.top, (int)this.bv.getWidth(), (int)this.bv.getHeight());
    }
    
}
