import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.List;
import java.util.TreeSet;
import java.util.LinkedList;
import java.util.Iterator;

public class PointSET {

    private TreeSet<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new TreeSet<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("illegal argument");
        }
        set.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("illegal argument");
        }
        return set.contains(p);
    }

    public void draw(){
        for (Point2D p: set) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("illegal argument");
        }
        return new PointsIterable(rect);
    }

    private class PointsIterable implements Iterable<Point2D> {
        List<Point2D> insideList;
        public PointsIterable(RectHV rect) {
            insideList = new LinkedList<>();
            for (Point2D temp: set) {
                if (temp.x() >= rect.xmin() && temp.x() <= rect.xmax() && temp.y() >= rect.ymin()
                        && temp.y() <= rect.ymax()) {
                    insideList.add(temp);
                }
            }
        }
        public Iterator<Point2D> iterator() {
            Iterator<Point2D> setIter = insideList.iterator();
            return new Iterator<Point2D>() {
                @Override
                public boolean hasNext() {
                    return setIter.hasNext();
                }
                @Override
                public Point2D next() {
                    return setIter.next();
                }
            };
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("illegal argument");
        }
        if (set.isEmpty()) {
            return null;
        }
        Point2D nearest = set.first();
        double nearestDistance = nearest.distanceTo(p);
        for (Point2D point: set) {
            if (point.distanceTo(p) < nearestDistance) {
                nearestDistance = point.distanceTo(p);
                nearest = point;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {
        PointSET s = new PointSET();
        s.insert(new Point2D(1,2));
        s.insert(new Point2D(1,4));
        s.insert(new Point2D(2,5));
        s.insert(new Point2D(3,6));
        for (Point2D p: s.range(new RectHV(1,1, 4,5))) {
            System.out.println(p);
        }
    }
}
