package Asteroids;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;
import java.util.Random;
import javax.swing.event.MouseInputAdapter;
import java.lang.Math;
import java.awt.geom.AffineTransform;
import java.awt.Polygon;
import java.awt.Shape;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 800;
    static final int UNIT_SIZE = 40;

    static double shipAngle = 0;
    static Point shipCentroid;
    static Shape ship;
    static Polygon shipPolygon;
    static Point points[] = new Point[3];
    


    Random random;



    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.addMouseListener(new MouseInputAdapter() {
            
            @Override
            public void mouseReleased(MouseEvent e) {
            }           
        });

        start();
    }

    public void start() {
        Timer t = new Timer(0, this);
        t.setDelay(30);
        t.start();
        createShipPolygon();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

            //Draws lines
            g.setColor(new Color(128, 128, 128));
            for (int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            }

            for (int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }


            //Draws ship
            Graphics2D g2d = (Graphics2D) g.create();

            g2d.setPaint(Color.white);
            g2d.draw(ship);
            g2d.dispose();

        }

        public void createShipPolygon() {
    
            points[0] = new Point(100, 100);
            points[1] = new Point(50, 125);
            points[2] = new Point(50, 75);
    
            shipPolygon = toPolygon(points);
            ship = shipPolygon;
        }

        public void rotatePoly(char direction) {

            if (direction == 'l') { shipAngle -= 2; }
            if (direction == 'r') { shipAngle += 2; }

            if (shipAngle > 360) {
                shipAngle = 0;
            } else if (shipAngle < 0) {
                shipAngle = 360;
            }

            AffineTransform transf = AffineTransform.getRotateInstance(Math.toRadians(shipAngle), shipCentroid.x, shipCentroid.y);
            ship = transf.createTransformedShape(shipPolygon);
        }

        public Shape keepPoly() {

            AffineTransform transf = AffineTransform.getRotateInstance(Math.toRadians(shipAngle), shipCentroid.x, shipCentroid.y);
            ship = transf.createTransformedShape(shipPolygon);
            return ship;
        }

        public Polygon toPolygon(Point[] points) {

            Polygon polygon = new Polygon();
    
            for (int i = 0; i < points.length; i++) {
                polygon.addPoint(points[i].x, points[i].y);
            }
            return polygon;
        }

    public void moveForward(double angle, int i) {

        if (shipAngle >= 0 && shipAngle <= 90 || shipAngle >= 270 && shipAngle <= 360) {

            points[0].x += i;
            points[0].y = (int) (Math.tan(Math.toRadians(angle))*i + points[0].y);

            points[1].x += i;
            points[1].y = (int) (Math.tan(Math.toRadians(angle))*i + points[1].y);

            points[2].x += i;
            points[2].y = (int) (Math.tan(Math.toRadians(angle))*i + points[2].y);
        }

        if (shipAngle >= 91 && shipAngle <= 271) {

            points[0].x -= i;
            points[0].y = (int) (Math.tan(Math.toRadians(angle))*i - points[0].y);
    
            points[1].x -= i;
            points[1].y = (int) (Math.tan(Math.toRadians(angle))*i - points[1].y);
    
            points[2].x -= i;
            points[2].y = (int) (Math.tan(Math.toRadians(angle))*i - points[2].y);
        }
        shipPolygon = toPolygon(points);
        ship = keepPoly();

        System.out.println(Arrays.toString(shipPolygon.xpoints));
        System.out.println(Arrays.toString(shipPolygon.ypoints));
        System.out.println("\n\n\n");

}

    //Apparently this thing loops
    public void actionPerformed(ActionEvent e) {
        shipCentroid = new Point((points[0].x + points[1].x + points[2].x)/3, (points[0].y + points[1].y + points[2].y)/3);

        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {

            switch(e.getKeyCode()) {

                case KeyEvent.VK_LEFT:
                    rotatePoly('l');
                        break;

                case KeyEvent.VK_RIGHT:
                    rotatePoly('r');
                        break;

                case KeyEvent.VK_SPACE:
                    moveForward(shipAngle, 5);
                        break;

            }
        }
    }
}