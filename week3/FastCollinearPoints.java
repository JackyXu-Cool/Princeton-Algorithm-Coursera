import java.util.ArrayList;
import java.util.Arrays;


public class FastCollinearPoints {

    private Point[] p;
    private int N;
    private int lineNumber;
    private ArrayList<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        lineNumber = 0;
        lineSegments = new ArrayList<>();
        N = points.length;
        p = new Point[N];

        checkNull(points);
        checkDuplicate(points);

        for (int i = 0; i < N; i++) {
            p[i] = points[i];
        }
        Arrays.sort(p);
        Point[] temp = Arrays.copyOf(p, N);  // Here, p and temp are the same array with same elements

        for (int i = 0; i < N; i++) {
            Arrays.sort(temp, p[i].slopeOrder());
            Point min = p[i];
            Point max = p[i];
            int count = 2;
            for (int j = 0; j < N - 1; j++) {
                if (p[i].slopeTo(temp[j]) == p[i].slopeTo(temp[j + 1])) {
                    if (temp[j + 1].compareTo(min) < 0) {
                            min = temp[j + 1];
                    }  else if (temp[j + 1].compareTo(max) > 0) {
                            max = temp[j + 1];
                    }
                    count++;
                    // We need to consider the situation that the last element of the Point array
                    // also forms a collinear line. If we do not deal with this situation, the line can
                    // not be detected.
                    if (j == N - 2 && count >= 4 && p[i].compareTo(min) == 0) {
                        lineSegments.add(new LineSegment(min, max));
                        lineNumber++;
                    }
                }
                else {
                    if (count >= 4 && p[i].compareTo(min) == 0) {
                        lineSegments.add(new LineSegment(min, max));
                        lineNumber++;
                    }
                    if (p[i].compareTo(temp[j + 1]) < 0) {
                        max = temp[j];
                        min = p[i];
                        count = 2;
                    } else {
                        min = temp[j];
                        max = p[i];
                        count = 2;
                    }
                }
            }
        }
    }

    private boolean checkNull(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }
        return false;
    }

    private boolean checkDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicated");
                }
            }
        }
        return false;
    }

    public int numberOfSegments() {
        return lineNumber;
    }

    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[lineSegments.size()];
        for (int i = 0; i < segments.length; i++) {
            segments[i] = lineSegments.get(i);
        }
        return segments;
    }
}
