/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {

    private LineSegment[] segment = new LineSegment[0];

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        ArrayList<LineSegment> lineSegments = new ArrayList<>();
        checkPoints(points);
        Point[] originalPoints = points.clone();
        ArrayList<Point> listPoints = new ArrayList<>();

        double newSlope;
        if (originalPoints.length >= 4) {
            for (int i = 0; i < originalPoints.length; i++) {
                Arrays.sort(points, originalPoints[i].slopeOrder());
                newSlope = originalPoints[i].slopeTo(points[1]);
                listPoints.add(originalPoints[i]);
                listPoints.add(points[1]);
                for (int j = 2; j < originalPoints.length; j++) {
                    if (newSlope != originalPoints[i].slopeTo(points[j])
                            || j == points.length - 1) {
                        if (j == points.length - 1 && newSlope == originalPoints[i].slopeTo(
                                points[j])) {
                            listPoints.add(points[j]);
                        }
                        if (listPoints.size() >= 4) {
                            Collections.sort(listPoints);
                            if (originalPoints[i].compareTo(listPoints.get(0))
                                    <= 0) {
                                lineSegments.add(new LineSegment(listPoints.get(0),
                                                                 listPoints.get(
                                                                         listPoints.size() - 1)));
                            }
                        }
                        newSlope = originalPoints[i].slopeTo(points[j]);
                        listPoints.clear();
                        listPoints.add(originalPoints[i]);
                        listPoints.add(points[j]);
                    }
                    else {
                        listPoints.add(points[j]);
                    }
                }
                listPoints.clear();
            }
        }
        segment = new LineSegment[lineSegments.size()];
        int index = 0;
        for (LineSegment point : lineSegments) {
            segment[index] = point;
            index++;
        }
    }

    private void checkPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {

                if (points[i] == null || points[j] == null) {
                    throw new IllegalArgumentException();
                }

                if (i != j && points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segment.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segment;
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
