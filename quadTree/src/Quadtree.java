
import java.awt.Graphics;
import java.util.LinkedList;

/**
 * @author Marcelino Andrade
 */
public class Quadtree {
    
    public static final int MIN_SPLIT = 5;
    private static int MIN_SIZE;
    
    private static Quadtree _get;
    public static Quadtree get()
    {
        return _get;
    }
    
    // BV que delimita este nó da Quadtree
    public BoundingVolume bv;
    
    // armazena os BVs das partículas caso este nó seja folha.
    public LinkedList<BoundingVolume> leafs;
    
    // Quadtrees filhas deste nó
    public Quadtree[] children;
    
    // retorna true se este nó for folha.
    public boolean isLeaf()
    {
        return (leafs != null && leafs.size() > 0);
    }
    
    private Quadtree(float l, float t, float r, float b)
    {
        // cria o BV deste nó.
        this.bv = new BoundingVolume(l, t, r, b);
    }
    
    // testa intersecção com este nó da Quadtree.
    public boolean intersects(BoundingVolume other)
    {
        return this.bv.intersects(other);
    }
    
    // constroi a Quadtree com os objetos passados.
    public static Quadtree build(BoundingVolume[] objs)
    {
        if (_get == null){
            _get = new Quadtree(0, 0, Main.WIDTH, Main.HEIGHT);
            double min = Main.WIDTH;
            for (int i = 0; i < MIN_SPLIT; i++)
                min *= 0.5f;
            MIN_SIZE = (int)min;
        }
        
        _get.children = null;
        _get.leafs = null;
        
        for (BoundingVolume bv : objs)
            _get.add(bv);
        
        return _get;
    }
    
    // adiciona objetos a Quadtree.
    public void add(BoundingVolume bv)
    {
        if (this.bv == bv)
            return;
        
        //checar se está dentro da atual quadtree
	//se não estiver, dar return
	if (!this.intersects(bv)){
            return;
	}
        
        //verifica se a quad não tem filhos
        if (this.children == null)
        {
            //se não tem filho nem node, atribui o bv ao node e finaliza
            if (this.leafs == null)
                this.leafs = new LinkedList<>();
            
            if (this.leafs.size() < 2){
		this.leafs.add(bv);
            }
            //se não tem filho mas tem node, cria uma nova quad e add o bv e o node atual
            else
            {
                //checar se atingiu o tamanho mínimo
                float width = this.bv.getWidth() * 0.5f;
                if (width < MIN_SIZE)
                {
                    this.leafs.add(bv);
                }
                else
                {
                    // criar os quads filhos
                    float left = this.bv.left;
                    float top = this.bv.top;
                    float centerX = left + this.bv.getWidth() * 0.5f;
                    float centerY = top + this.bv.getHeight() * 0.5f;
                    float right = this.bv.right;
                    float bottom = this.bv.bottom;

                    this.children = new Quadtree[4];

                    this.children[0] = new Quadtree(left, top, centerX, centerY);
                    this.children[1] = new Quadtree(centerX, top, right, centerY);
                    this.children[2] = new Quadtree(left, centerY, centerX, bottom);
                    this.children[3] = new Quadtree(centerX, centerY, right, bottom);
                    
                    for (Quadtree c : this.children)
                    {
                        c.add(bv);
                        
                        // coloca as folhas nos quads filhos
                        for (BoundingVolume b : this.leafs)
                            c.add(b);
                    }
                    this.leafs.clear();
                }
            }
        }
        // se tem filhos
        else
        {
            for (Quadtree c : this.children)
            {
                c.add(bv);
            }
        }
    }
    
    // verifica se o BV passado colide com algum objeto contido.
    public void checkCollision(BoundingVolume bv)
    {
        if (!bv.intersects(this.bv))
            return;
        
        if (this.isLeaf())
        {
            for (BoundingVolume l : this.leafs)
            {
                if (bv == l)
                    continue;
                if (bv.intersects(l))
                    bv.particle.onCollision();
            }
        }
        else if (this.children != null)
        {
            for (Quadtree c : this.children)
            {
                c.checkCollision(bv);
            }
        }
    }
    
    // Testa todas as colisões entre objetos contidos na Quadtree passada.
    // Percorre toda a arvore e testa colisão entre os objetos dentro de um
    // mesmo nó folha.
    public void simulateCollisions(Quadtree _this)
    {
        if (_this.isLeaf())
        {
            for (BoundingVolume bv1 : _this.leafs)
            {
                for (BoundingVolume bv2 : _this.leafs)
                {
                    if (bv1 == bv2)
                        continue;
                    
                    if (bv1.intersects(bv2))
                        bv1.particle.onCollision();
                }
            }
        }
        else if (_this.children != null)
        {
            for (Quadtree c : _this.children)
                simulateCollisions(c);
        }
    }
    public static void simulateCollisions()
    {
        _get.simulateCollisions(_get);
    }
    
    // Desenha os BVs das Quads na árvore.
    public void draw(Graphics g)
    {
        this.bv.draw(g);
        
        if (this.children != null && this.children.length > 0)
        {
            for (Quadtree q : this.children)
                q.draw(g);
        }
        
        /*if (this.leafs != null && this.leafs.size() > 0)
        {
            for (BoundingVolume b : this.leafs)
                b.draw(g);
        }*/
    }
}
