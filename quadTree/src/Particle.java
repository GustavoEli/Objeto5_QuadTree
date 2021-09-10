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
    public boolean isDinamic;
    public boolean isColliding;
    
    public Particle(float x, float y, boolean isDinamic)
    {
        this.posX = x;
        this.posY = y;
        this.bv = new BoundingVolume(x - SIZE, y - SIZE, x + SIZE, y + SIZE);
        this.bv.particle = this;
        this.isDinamic = isDinamic;
    }
    
    public void setPos(float x, float y)
    {
        this.posX = x;
        this.posY = y;
        this.bv.left = x - SIZE;
        this.bv.top = y - SIZE;
        this.bv.right = x + SIZE;
        this.bv.bottom = y + SIZE;
    }
    
    private void move(float x, float y)
    {
        setPos(this.posX + x, this.posY + y);
    }
    
    public void update()
    {
        float x = (float)( Math.random() - 0.5f );
        float y = (float)( Math.random() - 0.5f );
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
        
        //draw
        g.setColor(c);
        //g.fillOval((int)this.bv.left, (int)this.bv.top, (int)this.bv.getWidth(), (int)this.bv.getHeight());
        g.fillRect((int)this.bv.left, (int)this.bv.top, (int)this.bv.getWidth(), (int)this.bv.getHeight());
        
        //this.isColliding = false;
    }
    
}
