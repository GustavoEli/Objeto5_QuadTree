import java.awt.Graphics;
import java.util.Random;

public class Simulation extends Thread {

    private static Simulation _get;
    public static Simulation get()
    {
        if (_get == null)
            _get = new Simulation();
        return _get;
    }
    
    // quantidade de partículas na simulação
    public final int COUNT = 500;
    
    // configura o uso ou não de Quadtree
    public static final boolean TOOGLE_QUADTREE = true;
    
    private Particle[] objs;
    private BoundingVolume[] bvs;
    
    private Simulation()
    {
        this.objs = new Particle[COUNT];
        this.bvs = new BoundingVolume[COUNT];
        Random rand = new Random();
        
        for (int i = 0; i < COUNT; i++)
        {
            int x = rand.nextInt(Main.WIDTH);
            int y = rand.nextInt(Main.HEIGHT);
            Particle p = new Particle(x, y);
            this.objs[i] = p;
            this.bvs[i] = p.bv;
        }
    }
    
    public void simulate(long elapsed)
    {
        // atualização a posição das partículas
        for (Particle p : this.objs)
            p.update(elapsed);
        
        // testes de colisão utilizando Quadtree
        if (TOOGLE_QUADTREE)
        {
            // reconstroi a Quadtree com as novas posições das partículas.
            Quadtree.build(this.bvs);
            
            // testes percorrendo toda a Quadtree.
            Quadtree.checkAllCollisions();
            
            // testes por busca na quadtree
            /*for (Particle p : this.objs)
            {
                Quadtree.get().checkCollision(p.bv);
            }*/
        }
        // testes de colisão sem Quadtree.
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
        // desenha as partículas
        for (Particle p : this.objs)
            p.draw(g);
        
        // desenha os bounding volumes da Quadtree
        if (Quadtree.get() != null)
            Quadtree.get().draw(g);
    }    
}
