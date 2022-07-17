package HLPaint;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 800;
    static final int UNIT_SIZE = 80;
    final static int DELAY = 50;
    static int cursorX = 0;
    static int cursorY = 0;
    static String chosenColor = "red";

    static ArrayList<Integer> paintTilePosX = new ArrayList<Integer>();
    static ArrayList<Integer> paintTilePosY = new ArrayList<Integer>();
    static ArrayList<String> paintTileColor = new ArrayList<String>();

    Timer timer;
    Random random;



    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        timer = new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void paintTile() {
        paintTilePosX.add(cursorX);
        paintTilePosY.add(cursorY);
        paintTileColor.add(chosenColor);
    }

    public void clearBoard() {
        paintTilePosX.removeAll(paintTilePosX);
        paintTilePosY.removeAll(paintTilePosY);
        paintTileColor.removeAll(paintTileColor);
    }

    public void draw(Graphics g) {

            //Draws all saved positions of tiles
            for (int i=0; i<paintTilePosX.size(); i++) {
                
                switch (paintTileColor.get(i)) {
                    case "red":
                        g.setColor(Color.red);
                        break;
    
                    case "orange":
                        g.setColor(new Color(255, 95, 21));
                        break;

                    case "yellow":
                        g.setColor(Color.yellow);
                        break;

                    case "green":
                        g.setColor(Color.green);
                        break;

                    case "blue":
                        g.setColor(Color.blue);
                        break;

                    case "purple":
                        g.setColor(Color.magenta);
                        break;

                    case "white":
                        g.setColor(Color.white);
                        break;

                    case "black":
                        g.setColor(Color.black);
                        break;

                    case "random":
                        g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                        break;
                    }

                g.fillRect(paintTilePosX.get(i), paintTilePosY.get(i), UNIT_SIZE, UNIT_SIZE);

            }

            //Draws lines
            g.setColor(new Color(128, 128, 128));
            for (int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            }

            for (int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }

            //Draws cursor
            g.setColor(Color.white);
            g.drawOval(cursorX, cursorY, UNIT_SIZE, UNIT_SIZE);
            g.drawLine(cursorX, cursorY, cursorX+80, cursorY+80);
            g.drawLine(cursorX+80, cursorY, cursorX, cursorY+80);

            //Draws UI
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Selected Color: " + chosenColor.substring(0, 1).toUpperCase() + chosenColor.substring(1), (SCREEN_WIDTH - metrics1.stringWidth("Selected Color: " + chosenColor.substring(0, 1).toUpperCase() + chosenColor.substring(1)))/2, g.getFont().getSize());

        }

    //Apparently this thing loops
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {

            switch(e.getKeyCode()) {

                case KeyEvent.VK_LEFT:
                    if (!(cursorX < 1)) {
                        cursorX -= UNIT_SIZE;
                    }
                        break;

                case KeyEvent.VK_RIGHT:
                    if (!(cursorX > SCREEN_WIDTH - UNIT_SIZE*2)) {
                        cursorX += UNIT_SIZE;
                    }
                        break;

                case KeyEvent.VK_UP:
                    if (!(cursorY < 1)) {
                        cursorY -= UNIT_SIZE;
                    }
                        break;

                case KeyEvent.VK_DOWN:
                    if (!(cursorY > SCREEN_HEIGHT - UNIT_SIZE*2)) {
                        cursorY += UNIT_SIZE;
                    }
                        break;

                case KeyEvent.VK_SPACE:
                    paintTile();
                        break;
                
                case KeyEvent.VK_1:
                    chosenColor = "red";
                        break;

                case KeyEvent.VK_2:
                    chosenColor = "orange";
                        break;

                case KeyEvent.VK_3:
                    chosenColor = "yellow";
                        break;

                case KeyEvent.VK_4:
                    chosenColor = "green";
                        break;

                case KeyEvent.VK_5:
                    chosenColor = "blue";
                        break;

                case KeyEvent.VK_6:
                    chosenColor = "purple";
                        break;

                case KeyEvent.VK_7:
                    chosenColor = "white";
                        break;

                case KeyEvent.VK_8:
                    chosenColor = "black";
                        break;

                case KeyEvent.VK_9:
                    chosenColor = "random";
                        break;    

                case KeyEvent.VK_0:
                    clearBoard();
                        break;  
            }
        }
    }
    
}