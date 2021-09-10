import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Main {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static String TYPE;

    public static JFrame frame;
    
    public static void main(String[] args) {
    
        TYPE = Simulation.TOOGLE_QUADTREE ? "usando Quadtree" : "sem Quadtree";
        frame = new JFrame("Teste de colisão " + TYPE);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLayout(null);
        
        Board board = new Board(WIDTH, HEIGHT);
        frame.add(board);
        //board.setLocation(0, 0);
        frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.pack();
        frame.setVisible(true);
        
        // simulação
        Simulation.get().start();
        try {
            Simulation.get().join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
