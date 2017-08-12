
import edu.princeton.cs.algs4.*;




public class PointSET {
    private RedBlackBST<Point2D,Integer> P_SET;

    public PointSET()
    {
        P_SET=new RedBlackBST<>();
    }// construct an empty set of points
    public boolean isEmpty()
    {
        return P_SET.isEmpty();
    }// is the set empty?
    public int size()
    {
        return P_SET.size();
    }// number of points in the set
    public void insert(Point2D p)
    {
        P_SET.put(p,1);
    }// add the point to the set (if it is not already in the set)
    public           boolean contains(Point2D p)
    {
        return P_SET.contains(p);
    }// does the set contain point p?
    public void draw()
    {
        for(Point2D temp:P_SET.keys())
            temp.draw();
    }// draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect)
    {
        Queue<Point2D> point2DQueue=new Queue<Point2D>();
        for(Point2D temp:P_SET.keys()) {
            if(rect.contains(temp))
                point2DQueue.enqueue(temp);
        }
        return point2DQueue;
    }// all points that are inside the rectangle (or on the boundary)
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        double minDis=2.0;
        Point2D nearestPoint=new Point2D(0,0);
        for(Point2D temp:P_SET.keys())
        {
            if(p.equals(temp))
                continue;
            if(p.distanceSquaredTo(temp)<minDis)
            {
                nearestPoint=temp;
            }
        }
        return nearestPoint;
    }


    public static void main(String[] args)
    {

    }// unit testing of the methods (optional)
}