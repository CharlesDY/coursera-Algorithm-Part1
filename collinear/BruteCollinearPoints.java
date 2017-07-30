import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class BruteCollinearPoints {

    private Set<String> setSegments = new HashSet<String>();
    private List<LineSegment> colinearSegments = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points)  {  // finds all line segments containing 4 points
        if (points == null || Arrays.asList(points).contains(null)) {
            throw new java.lang.IllegalArgumentException("argument is null");
        }

        Point[] pointsClone = points.clone();
        Arrays.sort(pointsClone);
        for (int p = 0; p < pointsClone.length; p++) {
            Point pointP = pointsClone[p];
            for (int q = p+1; q < pointsClone.length; q++) {
                Point pointQ = pointsClone[q];
                if (p != q && pointP.compareTo(pointQ) == 0) {
                    throw new java.lang.IllegalArgumentException("same points");
                }
                for (int r = q + 1; r < pointsClone.length; r++) {
                    Point pointR = pointsClone[r];
                    for (int s = r + 1; s < pointsClone.length; s++) {
                        Point pointS = pointsClone[s];
                        if (isColinear(pointP, pointQ, pointR, pointS)) {
                            boolean colFifth = false;
                            for (int t = 0; t < pointsClone.length; t++) {
                                Point pointT = pointsClone[t];
                                if ((pointT.compareTo(pointP) < 0 || pointT.compareTo(pointS) > 0) &&
                                        isColinear(pointP, pointQ, pointR, pointT)) {
                                    colFifth = true;
                                }
                            }
                            if (!colFifth) {
                                // System.out.println(points[p].toString() + points[q].toString() +
                                // points[r].toString() + points[s].toString());
                                String checkDuplicates = Integer.toString(p) + "-" + Integer.toString(s);
                                if (!setSegments.contains(checkDuplicates)) {
                                    LineSegment lineSeg = new LineSegment(pointsClone[p], pointsClone[s]);
                                    colinearSegments.add(lineSeg);
                                    setSegments.add(checkDuplicates);
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    private boolean isColinear(Point p, Point q, Point r, Point s) {
        if (p.slopeTo(q) == p.slopeTo(r)) {
            return p.slopeTo(q) == p.slopeTo(s);
        }
        return false;
    }

    public int numberOfSegments() {       // the number of line segments
        return colinearSegments.size();
    }

    public LineSegment[] segments() {               // the line segments
        LineSegment[] lineSegment = new LineSegment[numberOfSegments()];
        lineSegment =  colinearSegments.toArray(lineSegment);
        return lineSegment;
    }



    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}