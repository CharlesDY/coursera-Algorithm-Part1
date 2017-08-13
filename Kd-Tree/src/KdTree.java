import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import static edu.princeton.cs.algs4.Point2D.X_ORDER;
import static edu.princeton.cs.algs4.Point2D.Y_ORDER;


public class KdTree {
    private BST kdPointSET;

    public KdTree()
    {
        kdPointSET=new BST();

    }// construct an empty set of points
    public boolean isEmpty()
    {
        return kdPointSET.isEmpty();
    }// is the set empty?
    public int size()
    {
        return kdPointSET.size();
    }// number of points in the set
    public void insert(Point2D p)
    {
        kdPointSET.put(p);
    }// add the point to the set (if it is not already in the set)
    public boolean contains(Point2D p)
    {
        return kdPointSET.contains(p);
    }// does the set contain point p?
    public  void draw()
    {

    }// draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect)
    {
        Queue<Point2D> pointQueue;
        pointQueue=kdPointSET.range(rect);
        return pointQueue;
    }// all points that are inside the rectangle (or on the boundary)
    public Point2D nearest(Point2D p)
    {
            return kdPointSET.nearest(p);
    }// a nearest neighbor in the set to point p; null if the set is empty


    private class BST{
        private Node root;             // root of BST
        private int SIZE;
        private Queue<Point2D> PointQue=new Queue<>();

        private class Node {
            private Point2D key;           // sorted by key
            private int val;         // associated data
            private Node left, right;  // left and right subtrees
            private int size;          // number of nodes in subtree
            private Queue<Point2D> equalQue;
            public Node(Point2D key, int val, int size) {
                this.key = key;
                this.val = val;
                this.size = size;
            }
        }

        /**
         * Initializes an empty symbol table.
         */
        public BST() {
            SIZE=0;
        }

        public Queue<Point2D> range(RectHV rect)
        {
            recurRange(root,rect);
            return PointQue;
        }

        private void recurRange(Node x,RectHV rect)
        {
            if(x==null)
                return;
            if(rect.contains(x.key))
                PointQue.enqueue(x.key);
            if(x.equalQue!=null)
                for(Point2D temp:x.equalQue)
                {
                    if(rect.contains(temp))
                        PointQue.enqueue(temp);
                }
            if(x.val%2!=0)
            {
                if(rect.xmax()<x.key.x())
                    recurRange(x.left,rect);
                else if(rect.xmin()>x.key.x())
                    recurRange(x.right,rect);
                else
                {
                    recurRange(x.left,rect);
                    recurRange(x.right,rect);
                }
            }
            else if(root.val%2==0)
            {
                if(rect.ymax()<x.key.y())
                    recurRange(x.left,rect);
                else if(rect.ymin()>x.key.y())
                    recurRange(x.right,rect);
                else
                {
                    recurRange(x.left,rect);
                    recurRange(x.right,rect);
                }
            }
        }

        public Point2D nearest(Point2D q)
        {
            Point2D nearestPoint=root.key;
            double dis=root.key.distanceSquaredTo(q);
            if(q.x()<root.key.x())
                nearestPoint=searchNearest(root.left,q,nearestPoint,dis);
            else if(q.x()>root.key.x())
                nearestPoint=searchNearest(root.right,q,nearestPoint,dis);
            else if(q.x()==root.key.x())
            {
                if(root.equalQue!=null)
                for(Point2D temp:root.equalQue)
                {
                    if(q.distanceSquaredTo(temp)<dis)
                    {
                        dis=q.distanceSquaredTo(temp);
                        nearestPoint=temp;
                        if(dis==0)
                            return nearestPoint;
                    }
                }
                nearestPoint=searchNearest(root.left,q,nearestPoint,dis);
                nearestPoint=searchNearest(root.right,q,nearestPoint,dis);
            }
            return nearestPoint;
        }

        private Point2D searchNearest(Node root,Point2D q,Point2D nearestPoint,double dis)
        {
                if(root==null)
                    return nearestPoint;
                if(q.distanceSquaredTo(root.key)<dis)
                {
                    dis=q.distanceSquaredTo(root.key);
                    nearestPoint=root.key;
                }

                if(root.val%2!=0)
                {
                    if(q.x()<root.key.x())
                        nearestPoint=searchNearest(root.left,q,nearestPoint,dis);
                    else if(q.x()>root.key.x())
                        nearestPoint=searchNearest(root.right,q,nearestPoint,dis);
                    else if(q.x()==root.key.x())
                    {
                        if(root.equalQue!=null)
                        for(Point2D temp:root.equalQue)
                        {
                            if(q.distanceSquaredTo(temp)<dis)
                            {
                                dis=q.distanceSquaredTo(temp);
                                nearestPoint=temp;
                                if(dis==0)
                                    return nearestPoint;
                            }
                        }
                        nearestPoint=searchNearest(root.left,q,nearestPoint,dis);
                        nearestPoint=searchNearest(root.right,q,nearestPoint,dis);
                    }
                }
                else if(root.val%2==0)
                {
                    if(q.y()<root.key.y())
                        nearestPoint=searchNearest(root.left,q,nearestPoint,dis);
                    else if(q.y()>root.key.y())
                        nearestPoint=searchNearest(root.right,q,nearestPoint,dis);
                    else if(q.y()==root.key.y())
                    {
                        if(root.equalQue!=null)
                        for(Point2D temp:root.equalQue)
                        {
                            if(q.distanceSquaredTo(temp)<dis)
                            {
                                dis=q.distanceSquaredTo(temp);
                                nearestPoint=temp;
                                if(dis==0)
                                    return nearestPoint;
                            }
                        }
                        nearestPoint=searchNearest(root.left,q,nearestPoint,dis);
                        nearestPoint=searchNearest(root.right,q,nearestPoint,dis);
                    }
                }
                return nearestPoint;
        }

        /**
         * Returns true if this symbol table is empty.
         * @return {@code true} if this symbol table is empty; {@code false} otherwise
         */
        public boolean isEmpty() {
            return size() == 0;
        }

        /**
         * Returns the number of key-value pairs in this symbol table.
         * @return the number of key-value pairs in this symbol table
         */
        public int size() {
            //return size(root);
            return SIZE;
        }

        // return number of key-value pairs in BST rooted at x
        private int size(Node x) {
            if (x == null)
                return 0;
            else return x.size;
        }

        /**
         * Does this symbol table contain the given key?
         *
         * @param  key the key
         * @return {@code true} if this symbol table contains {@code key} and
         *         {@code false} otherwise
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public boolean contains(Point2D key) {
            if (key == null) throw new IllegalArgumentException("argument to contains() is null");
            return get(key) != -1;
        }

        /**
         * Returns the value associated with the given key.
         *
         * @param  key the key
         * @return the value associated with the given key if the key is in the symbol table
         *         and {@code null} if the key is not in the symbol table
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public int get(Point2D key) {
            return get(root, key);
        }

        private int get(Node x, Point2D key) {
            if (key == null) throw new IllegalArgumentException("called get() with a null key");
            if (x == null) return -1;
            //StdOut.println(x.key);
            int cmp=0;
            if(x.val%2!=0)
            {
                cmp=X_ORDER.compare(key,x.key);
            }
            else if(x.val%2==0)
            {

                cmp=Y_ORDER.compare(key,x.key);
            }
            //StdOut.println(cmp);
            //int cmp = key.compareTo(x.key);
            if      (cmp < 0) return get(x.left, key);
            else if (cmp > 0) return get(x.right, key);
            else
            {
                if(key.equals(x.key))
                    return x.val;
                if(x.equalQue!=null)
                for(Point2D temp:x.equalQue)
                {
                    if(temp.equals(key))
                        return x.val;
                }
                return -1;
                /*cmp = key.compareTo(x.key);
                if      (cmp < 0) return get(x.left, key);
                else if (cmp > 0) return get(x.right, key);
                else              return x.val;*/
            }
        }


        public void put(Point2D key) {
            if (key == null) throw new IllegalArgumentException("called put() with a null key");
            int val=1;
            if(!this.contains(key))
            root = put(root, key, val);
        }

        private Node put(Node x, Point2D key, int val){
            if (x == null)
            {
                SIZE=SIZE+1;
                return new Node(key, val, 1);
            }
            int cmp=0;
            if(x.val%2!=0)
            {
                cmp= X_ORDER.compare(key,x.key);
            }
            else if(x.val%2==0)
            {
                cmp=Y_ORDER.compare(key,x.key);
            }
            if      (cmp < 0) x.left  = put(x.left,  key, val+1);
            else if (cmp > 0) x.right = put(x.right, key, val+1);
            else
            {

                if(x.equalQue==null)
                    x.equalQue=new Queue<>();
                x.equalQue.enqueue(key);
                SIZE=SIZE+1;
                /*cmp = key.compareTo(x.key);
                if (cmp < 0)
                {
                    SIZE=SIZE+1;
                    Node insert=new Node(key,val,size(x.left)+1);
                    insert.left=x.left;
                    insert.right=x.right;
                    x.left=insert;
                    //x.left  = put(x.left,  key, val);
                }
                else if (cmp > 0)
                {
                    SIZE=SIZE+1;
                    Node insert=new Node(key,val,size(x.right)+1);
                    insert.right=x.right;
                    insert.left=x.left;
                    x.right=insert;
                    //x.right = put(x.right, key, val);
                }
                else
                    x.val   = val;*/
            }

            x.size = 1 + size(x.left) + size(x.right);
            return x;
        }

        /**
         * Returns all keys in the symbol table as an {@code Iterable}.
         * To iterate over all of the keys in the symbol table named {@code st},
         * use the foreach notation: {@code for (Point2D key : st.keys())}.
         *
         * @return all keys in the symbol table
         */





        /**
         * Unit tests the {@code BST} data type.
         *
         * @param args the command-line arguments
         */
        public void main(String[] args) {

        }
    }

    private int getlevel(Point2D p)
    {
        return kdPointSET.get(p);
    }

    public static void main(String[] args)
    {
        String filename = args[0];
        In in = new In(filename);

        //StdDraw.enableDoubleBuffering();

        // initialize the two data structures with point from standard input
       PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        //StdOut.println(kdtree.isEmpty());
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);

            kdtree.insert(p);
            brute.insert(p);


            //StdOut.println(kdtree.contains(p));
            if(!kdtree.contains(p))
                StdOut.println(p);
            if(!kdtree.nearest(p).equals(brute.nearest(p)))
            {
                StdOut.println("Attention!!");
                StdOut.println(p);
                StdOut.println(brute.nearest(p));
                StdOut.println(kdtree.nearest(p));
                StdOut.println(kdtree.size());
                StdOut.println(kdtree.getlevel(p));

            }
        }

        RectHV testRec=new RectHV(0.6356048583984375, 0.4850921630859375,0.88555908203125, 0.662872314453125);
        for(Point2D temp:kdtree.range(testRec))
        {
            StdOut.println(testRec.contains(temp));
            if(!testRec.contains(temp))
                StdOut.println(temp);
        }
        Point2D p=new Point2D(0.8597564697265625, 0.7216644287109375);
        StdOut.println(testRec.contains(p));
        kdtree.insert(p);
        //StdOut.println(kdtree.contains(p));
        //StdOut.println(testRec.contains(p));
        brute.insert(p);

        //StdOut.println(kdtree.nearest(p));
        //StdOut.println(brute.nearest(p));
        //StdOut.println(kdtree.nearest(new Point2D(0.2,0.3)));
        //StdOut.println(brute.nearest(new Point2D(0.2,0.3)));
        StdOut.println(kdtree.size());
        StdOut.println(brute.size());

    }
}
