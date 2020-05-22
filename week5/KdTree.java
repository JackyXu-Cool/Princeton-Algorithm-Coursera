import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class KdTree {
    private class Node<T> {
        private T val;
        private Node<T> left;
        private Node<T> right;
        public Node(T val) {
            this.val = val;
            left = null;
            right = null;
        }
        public void setVal(T val) {
            this.val = val;
        }
    }

    private Node<Point2D> root;
    private int size;

    public KdTree() {
        // No need to implement this
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Illegal argument");
        }
        if (!contains(p)) {
            root = rInsert(root, p, 0);
        }
    }

    private Node<Point2D> rInsert(Node<Point2D> curr, Point2D p, int level) {
        if (curr == null) {
            size++;
            return new Node<>(p);
        } else {
            if (level % 2 == 0) {
                if (p.x() < curr.val.x()) {
                    curr.left = rInsert(curr.left, p, level + 1);
                } else {
                    curr.right = rInsert(curr.right, p, level + 1);
                }
            } else {
                if (p.y() < curr.val.y()) {
                    curr.left = rInsert(curr.left, p, level + 1);
                } else {
                    curr.right = rInsert(curr.right, p, level + 1);
                }
            }
            return curr;
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Illegal argument");
        }
        int level = 0;
        Node<Point2D> curr = root;
        while (curr != null) {
            if (curr.val.equals(p)) return true;
            if (level % 2 == 0) {
                if (curr.val.x() > p.x()) {
                    curr = curr.left;
                } else {
                    curr = curr.right;
                }
                level++;
            } else {
                if (curr.val.y() > p.y()) {
                    curr = curr.left;
                } else {
                    curr = curr.right;
                }
                level++;
            }
        }
        return false;
    }

    public void draw() {

    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("illegal argument");
        }
        if (size == 0) return null;
        return new PointsIterable(rect);
    }

    private class PointsIterable implements Iterable<Point2D> {
        private List<Point2D> insideList;
        private RectHV rect;
        public PointsIterable(RectHV rect) {
            insideList = new LinkedList<>();
            this.rect = rect;
            rFind(root, 0, insideList);
        }

        private void rFind(Node<Point2D> curr, int level, List<Point2D> list) {
            if (curr == null) return;
            if (curr.val.x() >= rect.xmin() && curr.val.x() <= rect.xmax()
                && curr.val.y() >= rect.ymin() && curr.val.y() <= rect.ymax()) {
                list.add(curr.val);
            }
            if (level % 2 == 0) {
                if (curr.val.x() > rect.xmax()) {
                    rFind(curr.left, level + 1, list);
                } else if (curr.val.x() < rect.xmin()) {
                    rFind(curr.right, level + 1, list);
                } else {
                    rFind(curr.left, level + 1, list);
                    rFind(curr.right, level + 1, list);
                }
            } else {
                if (curr.val.y() > rect.ymax()) {
                    rFind(curr.left, level + 1, list);
                } else if (curr.val.y() < rect.ymin()) {
                    rFind(curr.right, level + 1, list);
                } else {
                    rFind(curr.left, level + 1, list);
                    rFind(curr.right, level + 1, list);
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
            throw new IllegalArgumentException("Illegal argument");
        }
        if (size == 0) return null;
        Node<Point2D> dummy = new Node<>(null);
        rNearest(root, p, dummy, 0);
        return dummy.val;
    }

    private double rNearest(Node<Point2D> curr, Point2D target, Node<Point2D> dummy, int level) {
        if (curr.left == null && curr.right == null) {
            if (dummy.val == null || dummy.val.distanceSquaredTo(target) > curr.val.distanceSquaredTo(target)) {
                dummy.setVal(curr.val);
            }
            return curr.val.distanceSquaredTo(target);
        }
        if (curr.left == null) {
            double goRight = rNearest(curr.right, target, dummy, level + 1);
            if (curr.val.distanceSquaredTo(target) < goRight) {
                if (dummy.val == null || dummy.val.distanceSquaredTo(target) > curr.val.distanceSquaredTo(target)) {
                    dummy.setVal(curr.val);
                }
                return curr.val.distanceSquaredTo(target);
            }
            return goRight;
        } else if (curr.right == null) {
            double goLeft = rNearest(curr.left, target, dummy, level + 1);
            if (curr.val.distanceSquaredTo(target) < goLeft) {
                if (dummy.val == null || dummy.val.distanceSquaredTo(target) > curr.val.distanceSquaredTo(target)) {
                    dummy.setVal(curr.val);
                }
                return curr.val.distanceSquaredTo(target);
            }
            return goLeft;
        } else {
            double temp1;
            double temp2;
            boolean levelX = level % 2 == 0;
            Point2D compareP = levelX ? new Point2D(curr.val.x(), target.y()) : new Point2D(target.x(), curr.val.y());
            if ((levelX && target.x() < curr.val.x() )|| (!levelX && target.y() < curr.val.y())) {
            temp1 = rNearest(curr.left, target, dummy, level + 1);
            if (temp1 < target.distanceSquaredTo(compareP)) {
                if (temp1 < curr.val.distanceSquaredTo(target)) return temp1;
                if (dummy.val == null || curr.val.distanceSquaredTo(target) < dummy.val.distanceSquaredTo(target)) {
                    dummy.setVal(curr.val);
                }
                return curr.val.distanceSquaredTo(target);
            }
            temp2 = rNearest(curr.right, target, dummy, level + 1);
            return distanceHelper(temp1, temp2, curr, dummy, target);
        } else {
            temp2 = rNearest(curr.right, target, dummy, level + 1);
            if (temp2 < target.distanceSquaredTo(compareP)) {
                if (temp2 < curr.val.distanceSquaredTo(target)) return temp2;
                if (dummy.val == null || curr.val.distanceSquaredTo(target) < dummy.val.distanceSquaredTo(target)) {
                    dummy.setVal(curr.val);
                }
                return curr.val.distanceSquaredTo(target);
            }
            temp1 = rNearest(curr.left, target, dummy,level + 1);
            return distanceHelper(temp1, temp2, curr, dummy, target);
            }
        }
    }

    /**
     * Compare temp1, temp2, and the point in curr node to target. Reset dummy node if needed
     * @param temp1 nearest distance from left branch
     * @param temp2 nearest distance from right branch
     * @param curr curr node
     * @param dummy dummy node, used for storing the point with nearest distance
     * @param target query point
     * @return the nearest distance, (either temp1, or temp2, or the distance from curr node to target)
     */
    private double distanceHelper(double temp1, double temp2, Node<Point2D> curr, Node<Point2D> dummy, Point2D target) {
        if (temp1 < temp2) {
            if (curr.val.distanceSquaredTo(target) < temp1) {
                if (dummy.val == null || dummy.val.distanceSquaredTo(target) > curr.val.distanceSquaredTo(target)) {
                    dummy.setVal(curr.val);
                }
                return curr.val.distanceSquaredTo(target);
            }
            return temp1;
        } else {
            if (curr.val.distanceSquaredTo(target) < temp2) {
                if (dummy.val == null || dummy.val.distanceSquaredTo(target) > curr.val.distanceSquaredTo(target)) {
                    dummy.setVal(curr.val);
                }
                return curr.val.distanceSquaredTo(target);
            }
            return temp2;
        }
    }

    public static void main(String[] args) {
        KdTree s = new KdTree();
        s.insert(new Point2D(0.7,0.2));
        s.insert(new Point2D(0.5,0.4));
        s.insert(new Point2D(0.2,0.3));
        s.insert(new Point2D(0.4,0.7));
        s.insert(new Point2D(0.9,0.6));
        System.out.println(s.nearest(new Point2D(0.076, 0.564)));
    }
}

