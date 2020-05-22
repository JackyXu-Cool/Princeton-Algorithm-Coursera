import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private int number;
    private ArrayList<LineSegment> lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null){
            throw new IllegalArgumentException();
        }
        checkNull(points);
        checkDuplicate(points);
        number = 0;
        lineSegments = new ArrayList<>();
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
                        points[i].slopeTo(points[k]) == points[i].slopeTo(points[l])) {
                            number++;
                            Point[] temp = {points[i], points[j], points[k], points[l]};
                            Arrays.sort(temp);
                            lineSegments.add(new LineSegment(temp[0], temp[3]));
                        } else if (points[i].slopeTo(points[j]) == Double.POSITIVE_INFINITY &&
                                points[i].slopeTo(points[k]) == Double.POSITIVE_INFINITY &&
                                points[i].slopeTo(points[l]) == Double.POSITIVE_INFINITY) {
                            number++;
                            Point[] temp = {points[i], points[j], points[k], points[l]};
                            Arrays.sort(temp);
                            lineSegments.add(new LineSegment(temp[0], temp[3]));
                        }
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

    // the number of line segments
    public int numberOfSegments() {
        return number;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[lineSegments.size()];
        for (int i = 0; i < segments.length; i++) {
            segments[i] = lineSegments.get(i);
        }
        return segments;
    }
}
