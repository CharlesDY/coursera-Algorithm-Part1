import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {

    // Set<String> a = new HashSet<String>();
    private List<LineSegment> colinearSegments = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points)  {   // finds all line segments containing 4 or more points
        if (points == null || Arrays.asList(points).contains(null)) {
            throw new java.lang.IllegalArgumentException("argument is null");
        }

        int N = points.length;

        Point[] pointsClone = points.clone();
        Arrays.sort(pointsClone);
        Point[] points2 = new Point[N];


        for (int p = 0; p < N; p++) { // if points.length - 3, we won't catch if 2 points are the same at the end.
            for (int i = p; i < N; i++)
                points2[i] = pointsClone[i];

            Point pointP = pointsClone[p];
            Arrays.sort(points2, p+1, N, pointP.slopeOrder());
            Arrays.sort(points2, 0, p, pointP.slopeOrder());

            // go through the array:
            int ct = 0;
            double previousSlope = Double.NEGATIVE_INFINITY;
            for (int i = p+1; i < N; i++) {
                double slope = pointP.slopeTo(points2[i]);

                // System.out.println(pointP.toString() + points2[i].toString());
                // System.out.println("slope:" + slope);
                if (slope == Double.NEGATIVE_INFINITY) {
                    throw new java.lang.IllegalArgumentException("same points");
                }
                if (slope == previousSlope) {
                    ct++;
                }
                // System.out.println("ct:" + ct + " i:" + i);
                // System.out.println("slope != previousSlope" + Boolean.toString(slope != previousSlope));
                // System.out.println("i == points.length - 1:" + Boolean.toString(i == points.length - 1));
                if (ct >= 2 && (slope != previousSlope || i == N - 1)) {
                    // System.out.println("CHANGING:");
                    Arrays.sort(points2, i - ct, i);
                    Point minPt = points2[i - ct];
                    Point maxPt = points2[i - 1];
                    if (slope == previousSlope) {
                        maxPt = points2[i];
                    }

                    if (pointP.compareTo(minPt) < 0) {
                        minPt = pointP;
                    }
                    if (pointP.compareTo(maxPt) > 0) {
                        maxPt = pointP;
                    }
                    // System.out.println(pointP.toString() + points[i - ct].toString() +  points[i - 1].toString());

                    // check if there is a point in the first array with the same slope

                    boolean insert = true;
                    // System.out.println(slope);

                    for (int j = 0; j < p; j++) {
                        double pastSlope = pointP.slopeTo(points2[j]);
                        if (pastSlope >= previousSlope) {
                            if (pastSlope == previousSlope) {
                                insert = false;
                            }
                            break;
                        }
                    }
                    

                    if (insert) {
                        LineSegment lineSeg = new LineSegment(minPt, maxPt);
                        colinearSegments.add(lineSeg);
                    }

                }
                if (slope != previousSlope) {
                    ct = 0;
                    previousSlope = slope;
                }
            }

        }

    }


    public int numberOfSegments()  {      // the number of line segments
        return colinearSegments.size();
    }

    public LineSegment[] segments()    {            // the line segments
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}