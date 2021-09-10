import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

/**
 *
 * @author Lucas Saldanha Couto
 */

public class BoundingVolume {
    
    public float left;
    public float top;
    public float right;
    public float bottom;
    
    public Particle particle;
    
    public float getWidth()
    {
        return right - left;
    }
    public float getHeight()
    {
        return bottom - top;
    }
    
    public BoundingVolume(float l, float t, float r, float b)
    {
        this.left = l;
        this.top = t;
        this.right = r;
        this.bottom = b;
    }
    
    public boolean intersects(BoundingVolume other)
    {
        if (this.left > other.right || this.right < other.left ||
                this.top > other.bottom || this.bottom < other.top)
            return false;
        return true;
    }
    
    public static BoundingVolume boundingGroup(LinkedList<BoundingVolume> group)
    {
        float left = Board.WIDTH, top = Board.HEIGHT, right = 0, bottom = 0;
        for (BoundingVolume b : group)
        {
            left = Math.min(left, b.left);
            top = Math.min(top, b.top);
            right = Math.max(right, b.right);
            bottom = Math.max(bottom, b.bottom);
        }
        
        return new BoundingVolume(left, top, right, bottom);
    }
    
    public void draw(Graphics g)
    {
        /*if (this.particle == null)
        {
            //
        }
        else
        {
            //
        }*/
        
        //draw
        //Color c = new Color(0, 1, 0, 0.1f);
        g.setColor(Color.GREEN);
        g.drawRect((int)this.left, (int)this.top, (int)this.getWidth(), (int)this.getHeight());
    }
    
}
