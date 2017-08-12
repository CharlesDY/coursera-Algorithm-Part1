import edu.princeton.cs.algs4.*;

import java.util.Comparator;


public class KdTree {
    private BST<Point2D,Integer> kdPointSET;
    public  KdTree()
    {
        kdPointSET=new BST<Point2D,Integer>();
    }// construct an empty set of points
    public           boolean isEmpty()
    {
        return kdPointSET.isEmpty();
    }// is the set empty?
    public               int size()
    {
        return kdPointSET.size();
    }// number of points in the set
    public void insert(Point2D p)
    {

    }// add the point to the set (if it is not already in the set)
    public           boolean contains(Point2D p)
    {
        return kdPointSET.contains(p);
    }// does the set contain point p?
    public              void draw()
    {
        for(Point2D temp:kdPointSET.keys())
            temp.draw();
    }// draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args)
}
