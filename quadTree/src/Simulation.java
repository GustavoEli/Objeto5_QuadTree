import java.awt.Graphics;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Simulation extends Thread {

    private static Simulation _get;
    public static Simulation get()
    {
        if (_get == null)
            _get = new Simulation();
        return _get;
    }
    
    private final int COUNT = 500;
    public static final boolean TOOGLE_QUADTREE = true;
    private Quadtree quadtree;
    private Particle[] objs;
    private BoundingVolume[] bvs;
    
    private final int END_TIME = 10;
    private long previousTime;
    private long amountTime;
    
    private Simulation()
    {
        long previous = System.currentTimeMillis();
        
        if (TOOGLE_QUADTREE){
            quadtree = Quadtree.get();
        }
        
        this.objs = new Particle[COUNT];
        this.bvs = new BoundingVolume[COUNT];
        Random rand = new Random();
        
        for (int i = 0; i < COUNT; i++)
        {
            int x = rand.nextInt(Main.WIDTH);
            int y = rand.nextInt(Main.HEIGHT);
            Particle p = new Particle(x, y, false);
            this.objs[i] = p;
            this.bvs[i] = p.bv;
            if (quadtree != null)
                quadtree.add(p.bv);
        }
        
        /*if (TOOGLE_QUADTREE){
            //quadtree = Quadtree.get();
            quadtree = Quadtree.build(this.bvs);
        }*/
        
        long after = System.currentTimeMillis();
        System.out.println("Inicialização " + Main.TYPE + " com " + COUNT +
                " objetos: " + (after - previous) + " milisegundos.");
    }
    
    @Override public void run()
    {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int ticks = 0;
        this.previousTime = System.currentTimeMillis();
        while (amountTime < END_TIME * 1000) {
            
            ticks++;
            long currentTime = System.currentTimeMillis();
            long elapsed = currentTime - this.previousTime;
            amountTime += elapsed;
            this.previousTime = currentTime;
            //System.out.println(amountTime / 1000);
            
            //
            simulate();
            
            //
            //Board.get.repaint();
            
            /*try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }
        Board.get.repaint();
        //TODO: média performance
        System.out.println("Teste " + Main.TYPE + " com " + COUNT + " objetos: Média de " + (amountTime / (float)ticks) + " milissegundos por frame.");
    }
    /*@Override public void run()
    {
        long previous = System.currentTimeMillis();
        
        simulate();
        
        long current = System.currentTimeMillis();
        long elapsed = current - previous;
        System.out.println("Teste " + Main.TYPE + " com " + COUNT + " objetos: " + elapsed + " milissegundos.");
        
        Board.get.repaint();
    }*/
    
    private void simulate()
    {
        //TODO: percorrer this.objs e checar colisão utilizando quadtree com o método check
        if (TOOGLE_QUADTREE)
        {
            // testes por percurso na quadtree
            quadtree.simulateCollisions(quadtree);
            
            // testes por busca na quadtree
            /*for (Particle p : this.objs)
            {
                quadtree.checkCollision(p.bv);
            }*/
        }
        else
        {
            // testes por busca padrão
            for (Particle p1 : this.objs)
            {
                for (Particle p2 : this.objs)
                {
                    if (p1 == p2)
                        continue;
                    
                    if (p1.bv.intersects(p2.bv))
                    {
                        p1.onCollision();
                    }
                }
            }
        }
    }
    
    public void draw(Graphics g)
    {
        for (Particle p : this.objs)
            p.draw(g);
        
        if (quadtree != null)
            quadtree.draw(g);
    }    
}
