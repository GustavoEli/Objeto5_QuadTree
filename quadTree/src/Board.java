import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
    
    // tempo em segundos que dura a simulação.
    private final int END_TIME = 10;
    
    // variáveis que controlam o fluxo de tempo na simulação
    // e auxiliam na divulgação dos resultados.
    private long previousTime;
    private long amountTime;
    private long lag;
    private int ticks = 0;
    
    // tempo mínimo entre cada frame em milissegundos.
    // 0 = o mais rápido possível.
    private final int UPDATE_RATE = 0;
    
    // tempo mínimo entre cada atualização da interface gráfica.
    private final int PAINT_RATE = 16;
    
    // efetua o game loop.
    private final Timer timer;
    
    Simulation sim;
    
    public Board(int width, int height)
    {
        setSize(new Dimension(width, height));
        
        sim = Simulation.get();
        
        // inicializando o game loop.
        this.previousTime = System.currentTimeMillis();
        timer = new Timer(UPDATE_RATE, this);
        timer.start();
    }
    
    // desenha na interface gráfica.
    @Override public void paint(Graphics g){
        super.paintComponent(g);
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
        
        sim.draw(g);
        
        java.awt.Toolkit.getDefaultToolkit().sync();
        
    }

    // é chamado a cada atualização de acordo com o timer.
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // calculam o deltaTime que auxilia no game loop e no log de resultados
        ticks++;
        long currentTime = System.currentTimeMillis();
        long elapsed = currentTime - this.previousTime;
        amountTime += elapsed;
        this.previousTime = currentTime;
        
        // roda a simulação o mais rápido possível
        sim.simulate(elapsed);
        //repaint();
        
        // desenha o board a um frame rate fixo, e não a cada frame,
        // o que diminui a interferência na simulação
        lag += elapsed;
        while (lag >= PAINT_RATE)
        {
            repaint();
            lag -= PAINT_RATE;
        }
        
        // termina a simulação no tempo pré determinado
        if (amountTime >= END_TIME * 1000)
        {
            timer.stop();
            String log = "Teste " + Main.TYPE + " com " + sim.COUNT + " objetos: Média de " + ((amountTime / (float)ticks) * 1000) + " microssegundos por frame.";
            System.out.println(log);
            Main.get.setTitle(log);
        }
    }
}
