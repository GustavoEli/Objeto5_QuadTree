import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main extends JFrame {
    
    public static Main get;

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 720;
    public static String TYPE;
    
    public Main()
    {
        get = this;
        
        TYPE = Simulation.TOOGLE_QUADTREE ? "usando Quadtree" : "sem Quadtree";
        setTitle("Teste de colisão " + TYPE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Board board = new Board(WIDTH, HEIGHT);
        add(board);
        getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
        pack();
    }

    public static void main(String[] args) {
    
        EventQueue.invokeLater(() -> {
            JFrame main = new Main();
            main.setVisible(true);
        });
    }
}
