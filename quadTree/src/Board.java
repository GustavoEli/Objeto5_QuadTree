import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Board extends JPanel{
    public static Board get;
    
    public Board(int width, int height)
    {
        get = this;
        setSize(new Dimension(width, height));
    }
    
    @Override public void paint(Graphics g){
        super.paintComponent(g);
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
        
        //Quadtree.get().draw(g);
        Simulation.get().draw(g);
        
    }
}
