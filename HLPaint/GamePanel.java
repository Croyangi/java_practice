package HLPaint;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.event.MouseInputAdapter;
import java.lang.Math;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 800;
    static final int UNIT_SIZE = 40;
    final static int DELAY = 10;
    static int cursorX = 0;
    static int cursorY = 0;
    static int mouseX = 0;
    static int mouseY = 0;

    static String chosenColor = "red";
    static int colorRow = 1;

    static ArrayList<Integer> paintTilePosX = new ArrayList<Integer>();
    static ArrayList<Integer> paintTilePosY = new ArrayList<Integer>();
    static ArrayList<String> paintTileColor = new ArrayList<String>();

    static ArrayList<Integer> paintTilePosXUndo = new ArrayList<Integer>();
    static ArrayList<Integer> paintTilePosYUndo = new ArrayList<Integer>();
    static ArrayList<String> paintTileColorUndo = new ArrayList<String>();

    Timer timer;
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

                mouseX = e.getX();
                mouseY = e.getY();

                if (SwingUtilities.isLeftMouseButton(e)) {
                    calculateTile();
                }

                if (SwingUtilities.isRightMouseButton(e)) {
                    eraseTile();
                }
            }           
        });

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

    public void calculateTile() {
        int x = mouseX / UNIT_SIZE;
        x = x * UNIT_SIZE;
        cursorX = x;

        int y = mouseY / UNIT_SIZE;
        y = y * UNIT_SIZE;
        cursorY = y;

        paintTile();
    }

    public void eraseTile() {
        int x = mouseX / UNIT_SIZE;
        x = x * UNIT_SIZE;
        cursorX = x;

        int y = mouseY / UNIT_SIZE;
        y = y * UNIT_SIZE;
        cursorY = y;

        for (int i=0; i<paintTilePosX.size(); i++) {
            int posX = paintTilePosX.get(i);
            int posY = paintTilePosY.get(i);

            if (posX == cursorX && posY == cursorY) {
                paintTilePosX.remove(i);
                paintTilePosY.remove(i);
                paintTileColor.remove(i);
            }
        }
    }

    public void paintTile() {
        saveRevertChanges();

        paintTilePosX.add(cursorX);
        paintTilePosY.add(cursorY);
        paintTileColor.add(chosenColor);
    }

    public void saveRevertChanges() {
        paintTilePosXUndo.clear();
        paintTilePosYUndo.clear();
        paintTileColorUndo.clear();

        paintTilePosXUndo.addAll(paintTilePosX);
        paintTilePosYUndo.addAll(paintTilePosY);
        paintTileColorUndo.addAll(paintTileColor);
    }

    public void revertChanges() {
        paintTilePosX.clear();
        paintTilePosY.clear();
        paintTileColor.clear();

        paintTilePosX.addAll(paintTilePosXUndo);
        paintTilePosY.addAll(paintTilePosYUndo);
        paintTileColor.addAll(paintTileColorUndo);
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

                    case "grey":
                    g.setColor(new Color(128, 128, 128));
                        break;

                    case "black":
                        g.setColor(Color.black);
                        break;

                    case "peach":
                        g.setColor(new Color(255, 195, 177));
                        break;

                    case "brown":
                        g.setColor(new Color(75, 50, 0));
                        break;
    
                    case "dark_red":
                        g.setColor(new Color(67, 0, 10));
                        break;

                    case "dark_orange":
                        g.setColor(new Color(150, 64, 0));
                        break;

                    case "dark_yellow":
                        g.setColor(new Color(82, 80, 0));
                        break;

                    case "dark_green":
                        g.setColor(new Color(3, 70, 0));
                        break;

                    case "turquoise":
                        g.setColor(new Color(48, 213, 200));
                        break;

                    case "dark_blue":
                        g.setColor(new Color(0, 25, 106));
                        break;

                    case "dark_purple":
                        g.setColor(new Color(43, 0, 66));
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
            g.drawLine(cursorX, cursorY, cursorX+UNIT_SIZE, cursorY+UNIT_SIZE);
            g.drawLine(cursorX+UNIT_SIZE, cursorY, cursorX, cursorY+UNIT_SIZE);

            //Draws UI
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Selected Color: " + chosenColor.substring(0, 1).toUpperCase() + chosenColor.substring(1), (SCREEN_WIDTH - metrics1.stringWidth("Selected Color: " + chosenColor.substring(0, 1).toUpperCase() + chosenColor.substring(1)))/5, g.getFont().getSize());
            g.drawString("Selected Color Row: " + colorRow, (SCREEN_WIDTH - metrics1.stringWidth("Selected Color Row: " + colorRow))/2+170, g.getFont().getSize());

        }

    //Apparently this thing loops
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {

            if (colorRow == 1) {

                switch(e.getKeyCode()) {
                    
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
                            chosenColor = "grey";
                            break;
    
                    case KeyEvent.VK_9:
                            chosenColor = "brown";
                            break;
                    }
            }

            if (colorRow == 2) {

                switch(e.getKeyCode()) {
                    
                    case KeyEvent.VK_1:
                            chosenColor = "dark_red";
                            break;
    
                    case KeyEvent.VK_2:
                            chosenColor = "dark_orange";
                            break;
    
                    case KeyEvent.VK_3:
                            chosenColor = "dark_yellow";
                            break;
    
                    case KeyEvent.VK_4:
                            chosenColor = "dark_green";
                            break;
    
                    case KeyEvent.VK_5:
                            chosenColor = "turquoise";
                            break;
    
                    case KeyEvent.VK_6:
                            chosenColor = "dark_blue";
                            break;
    
                    case KeyEvent.VK_7:
                            chosenColor = "dark_purple";
                            break;
    
                    case KeyEvent.VK_8:
                            chosenColor = "peach";
                            break;
    
                    case KeyEvent.VK_9:
                            chosenColor = "black";
                            break;
                    }
            }

            switch(e.getKeyCode()) {

                case KeyEvent.VK_0:
                    if (colorRow == 1) {
                        colorRow = 2;
                    } else 
                        colorRow = 1;
                        break;

                case KeyEvent.VK_BACK_SPACE:
                    clearBoard();
                        break;
                
                case KeyEvent.VK_EQUALS:
                    chosenColor = "random";
                        break;  

                case KeyEvent.VK_Z:
                    revertChanges();
                        break; 
            }
        }
    }
    
    
}