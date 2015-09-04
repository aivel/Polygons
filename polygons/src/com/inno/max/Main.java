package com.inno.max;


import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * Created by max on 01.09.15.
 */
public class Main {

    static final class DrawPanel extends JPanel {
        final List<SuperPolygonList> polygons;
        final Point center;
        final int diameter;


        public DrawPanel(final java.util.List<Point> circle, final Point ceneter,
                         final int radius, final List<Point> polygon,
                         final List<Point> ray) {
            this.polygons = new SuperPolygonList<SuperPolygonList>();
            this.center = ceneter;
            this.diameter = 2 * radius;

            polygons.add((SuperPolygonList) circle);
            polygons.add((SuperPolygonList) polygon);
            polygons.add((SuperPolygonList) ray);
        }

        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);

            for (int i = 0; i < polygons.size(); i++) {
                final List<Point> polygon = polygons.get(i);

                g.setColor(polygon.size() == 2 ? Color.red : Color.black);

                for (int j = 0; j < polygon.size(); j++) {
                    final Point from = polygon.get(j);
                    final Point to = polygon.get(j == polygon.size() - 1 ? 0 : j + 1);

                    g.drawLine(from.x, from.y, to.x, to.y);
                }
            }

            g.setColor(Color.black);

            g.drawOval(center.x, center.y, diameter, diameter);
        }
    }

    public static float theta(final int t, final int vertices) {
        return (float) ((Math.PI * 2.0 * t) / vertices);
    }

    private static List<Point> makeCircle(final Point center, final int vertices,
                                          final int radius) {
        final List<Point> circle = new SuperPolygonList<Point>();

        for (int i = 0; i < vertices; i++) {
            final int x = (int) (radius * Math.cos(theta(i, vertices)));
            final int y = (int) (radius * Math.sin(theta(i, vertices)));

            circle.add(new Point(center.x + radius + x, center.y + radius + y));
        }

        return circle;
    }


    private static List<Point> makeRandomPolygon(final int vertices) {
        final int MAX_X = 150;
        final int MAX_Y = 150;
        final Random random = new Random();
        List<Point> polygon = new SuperPolygonList<Point>();

        for (int i = 0; i < vertices; i++) {
            final int randomX = Math.abs(random.nextInt() % MAX_X);
            final int randomY = Math.abs(random.nextInt() % MAX_Y);

            polygon.add(new Point(randomX, randomY));
        }

        return polygon;
    }

    private static boolean intersects(final Point a, final Point b,
                               final Point c, final Point d) {
        // if AB intersects CD

        double[][] A = new double[2][2];
        A[0][0] = b.getX() - a.getX();
        A[1][0] = b.getY() - a.getY();
        A[0][1] = c.getX() - d.getX();
        A[1][1] = c.getY() - d.getY();

        double det0 = A[0][0] * A[1][1] - A[1][0] * A[0][1];

        double detU = (c.getX() - a.getX()) * A[1][1] - (c.getY() - a.getY()) * A[0][1];
        double detV = A[0][0] * (c.getY() - a.getY()) - A[1][0] * (c.getX() - a.getX());

        double u = detU / det0;
        double v = detV / det0;

        return u > 0 && u < 1 && v > 0 && v < 1;
    }

    public static boolean isPointInside(final List<Point> polygon, final Point point,
                                        final Point rayEnd) {
        int intersections = 0;

        for (int j = 0; j < polygon.size(); j++) {
            final Point from = polygon.get(j);
            final Point to = polygon.get(j == polygon.size() - 1 ? 0 : j + 1);

            if (intersects(from, to, point, rayEnd)) {
                intersections++;
            }
        }

        return intersections % 2 == 1;
    }

    private static Point genRandomPoint() {
        final Random random = new Random();
        final int MIN_X = 0;
        final int MIN_Y = 0;
        final int MAX_X = 400;
        final int MAX_Y = 400;
        final int random_x = Math.abs((random.nextInt() + MIN_X) % MAX_X);
        final int random_y = Math.abs((random.nextInt() + MIN_Y) % MAX_Y);
        final Point rayEnd = new Point(random_x, random_y);

        return rayEnd;
    }

    public static void main(String[] args) {
        final int HEIGHT = 400, WIDTH = 400;
        final int VERTICES = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the number of vertices:"));
        final int R = 90;
        final Point CENTER = new Point(WIDTH / 2 - R, HEIGHT / 2 - R);

        final List<Point> circle = makeCircle(CENTER, VERTICES, R);
        final List<Point> polygon = makeRandomPolygon(VERTICES);

        final List<Point> ray = new SuperPolygonList<Point>();
        final Point rayStart = (new Random().nextBoolean() ? new Point(CENTER.x + R, CENTER.y + R) : genRandomPoint());
        final Point rayEnd = genRandomPoint();

        ray.add(rayStart);
        ray.add(rayEnd);

        List<Point> chosenPolygon = (new Random().nextBoolean() ? circle : polygon);

        JOptionPane.showMessageDialog(null,
                "The intersection will be tested on the "
                        + (chosenPolygon == circle ? "circle" : "randomly generated polygon") + "\r\n" +
                "The point (" + rayStart.x + "; "
                + rayStart.y + ") is " + (isPointInside(chosenPolygon, rayStart, rayEnd)
                ? "INSIDE" : "OUTSIDE") + " of the polygon\r\n" +
        "The end of the ray is the point (" + rayEnd.x + "; " + rayEnd.y + ")");

        JFrame frame = new JFrame("Polygon");
        JPanel panel = new DrawPanel(circle, CENTER, R, polygon, ray);
        panel.setSize(new Dimension(WIDTH, HEIGHT));

        frame.add(panel);
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
