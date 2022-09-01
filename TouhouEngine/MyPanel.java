package TouhouEngine;

import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JPanel;
import java.util.ArrayList;
import javax.swing.event.MouseInputAdapter;

public class MyPanel extends JPanel implements ActionListener {

    // Declaration of all variables
    static final int SCREEN_WIDTH = 1200;
    static final int SCREEN_HEIGHT = 800;
    final static int DELAY = 10;

    Timer timer;

    static boolean mouseHeld = false;
    static boolean mouseOnScreen = false;
    static Point cursor = new Point (0, 0);
    static Point selectedPos = new Point (SCREEN_WIDTH/2, SCREEN_HEIGHT/2);

    static int timeline;
    static boolean timelineRunning = false;

    static ArrayList<Integer> bulletID = new ArrayList<Integer>();
    static ArrayList<Integer> bulletLifespan = new ArrayList<Integer>();
    static ArrayList<Integer> bulletInitTime = new ArrayList<Integer>();

    static ArrayList<Point> bulletOriginPos = new ArrayList<Point>();
    static ArrayList<Point> bulletEndPos = new ArrayList<Point>();
    static ArrayList<Point> bulletCurrentPos = new ArrayList<Point>();


    // Declaration of all UI objects
    JPanel leftPanel = new JPanel();
    JPanel rightPanel = new JPanel();

    GroupLayout timelineLayout = new GroupLayout(leftPanel);

    Icon subtract = new ImageIcon("TouhouEngine/images/subtract.png");
    Icon add = new ImageIcon("TouhouEngine/images/add.png");
    Icon play = new ImageIcon("TouhouEngine/images/play.png");
    Icon pause = new ImageIcon("TouhouEngine/images/pause.png");
    Icon restart = new ImageIcon("TouhouEngine/images/restart.png");

    JButton subtractButton = new JButton(subtract);
    JButton addButton = new JButton(add);
    JButton playPauseButton = new JButton(play);
    JButton restartButton = new JButton(restart);

    JLabel timelineTickDisplay = new JLabel();


    MyPanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.setLayout(null);
        this.addMouseListener(new MouseInputAdapter() {
            public void mousePressed(MouseEvent e) { mouseHeld = true; }
            public void mouseReleased(MouseEvent e) { 
                mouseHeld = false;
                if (SwingUtilities.isLeftMouseButton(e)) { }
                if (SwingUtilities.isRightMouseButton(e)) { }
            }                   
        });

        this.addMouseMotionListener(new MouseInputAdapter() {

            public void mouseMoved(MouseEvent e) {
                cursor.x = e.getX();
                cursor.y = e.getY(); 
            }   

            public void mouseDragged(MouseEvent e) {
                cursor.x = e.getX();
                cursor.y = e.getY(); 
            }   
        });
        initEngine();
        initUI();
    }

    public void initEngine() {
        timer = new Timer(DELAY,this);
        timer.start();
    }

    // Initializes all UI objects
    public void initUI() {

        timelineLayout.setAutoCreateGaps(true);
        timelineLayout.setAutoCreateContainerGaps(true);

        timelineLayout.setHorizontalGroup(
            timelineLayout.createSequentialGroup()
                .addGap(27)
                .addComponent(subtractButton)
                .addComponent(addButton)
                .addComponent(playPauseButton)
                .addComponent(restartButton)
);

        timelineLayout.setVerticalGroup(
            timelineLayout.createSequentialGroup()
                .addGroup(timelineLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(subtractButton)
                .addComponent(addButton)
                .addComponent(playPauseButton)
                .addComponent(restartButton))
);

        leftPanel.setLocation(0, 0);
        leftPanel.setSize(300, 150);
        leftPanel.setLayout(timelineLayout);
        leftPanel.setBorder(BorderFactory.createTitledBorder("Timeline"));
        this.add(leftPanel);

        rightPanel.setLocation(900, 0);
        rightPanel.setSize(300, 800);
        rightPanel.setLayout(null);
        rightPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        this.add(rightPanel);


        subtractButton.setSize(40, 40);
        leftPanel.add(subtractButton);

        addButton.setSize(40, 40);
        leftPanel.add(addButton);

        playPauseButton.setSize(40, 40);
        leftPanel.add(playPauseButton);

        restartButton.setSize(40, 40);
        leftPanel.add(restartButton);

        timelineTickDisplay.setText("Timeline Tick: " + timeline);
        timelineTickDisplay.setFont(new Font("Dialog", Font.BOLD, 20));
        timelineTickDisplay.setBounds(75, 90, 500, 20);
        leftPanel.add(timelineTickDisplay);


        subtractButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                subtractButtonEvent(e);
            }
        });
        
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addButtonEvent(e);
            }
        });

        playPauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playPauseButtonEvent(e);
            }
        });

        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartButtonEvent(e);
            }
        });
    }

    // Button events
    public void subtractButtonEvent(ActionEvent e) {
        if (!(timeline <= 0)) { timeline--; } 
    }

    public void addButtonEvent(ActionEvent e) {
        timeline++;
    }

    public void playPauseButtonEvent(ActionEvent e) {
        if (!timelineRunning) {
            timelineRunning = true;
            playPauseButton.setIcon(pause);
        } else {
            timelineRunning = false;
            playPauseButton.setIcon(play);
        }
    }

    public void restartButtonEvent(ActionEvent e) {
        timelineRunning = false;
        timeline = 0;
        playPauseButton.setIcon(play);
    
    }
    // =======

    // Paint component
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Main draw tick
    public void draw(Graphics g) {
        FontMetrics metrics2;

        // Cursor
        g.setColor(Color.white);
        g.drawOval(selectedPos.x-10, selectedPos.y-10, 20, 20);
        g.drawLine(selectedPos.x-15, selectedPos.y, selectedPos.x+15, selectedPos.y);
        g.drawLine(selectedPos.x, selectedPos.y-15, selectedPos.x, selectedPos.y+15);

        // Timeline Tick
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        metrics2 = getFontMetrics(g.getFont());
        g.drawString("Timeline Tick: " + timeline, 10, 35);

        // Timeline Status
        g.setFont(new Font("Arial", Font.BOLD, 18));
        metrics2 = getFontMetrics(g.getFont());
        g.drawString("Running: " + timelineRunning, 10, 105);

        // Selected Position
        g.setFont(new Font("Arial", Font.BOLD, 18));
        metrics2 = getFontMetrics(g.getFont());
        g.drawString("Selected Position: (" + selectedPos.x + ", " + selectedPos.y + ")", (SCREEN_WIDTH - metrics2.stringWidth("Selected Position: (" + selectedPos.x + ", " + selectedPos.y + ")"))/2, g.getFont().getSize());
        

        // Bullets
        g.setColor(Color.white);
        for (int i = 0; i < bulletCurrentPos.size(); i++) {
            g.drawOval((int) bulletCurrentPos.get(i).getX(), (int) bulletCurrentPos.get(i).getY(), 3, 3);
        }
    }

    // Initializes all bullet arrays and data
    public void initBullet(Point landPos) {
        bulletID.add(bulletID.size());

        Point temp = new Point (landPos.x, landPos.y);
        bulletEndPos.add(temp);

        bulletSpawn(landPos);
        bulletLifespan.add(200);
    }

    // Spawns the bullet to be drawn
    public void bulletSpawn(Point landPos) {
        Point bulletOrigin = new Point (landPos.x, landPos.y+300);
        bulletCurrentPos.add(bulletOrigin);
    }

    // Main bullet tick function
    public void bulletTick() {
        for (int i = 0; i < bulletID.size(); i++) {
            int math = bulletLifespan.get(i);
            math--;
            bulletLifespan.set(i, math);

            if (math <= 0) {
                bulletID.remove(i);
                bulletEndPos.remove(i);
                bulletCurrentPos.remove(i);
                bulletLifespan.remove(i);
                //System.out.println("Bullet expired!");
            }
        }

        for (int i = 0; i < bulletCurrentPos.size(); i++) {
            Point math = bulletCurrentPos.get(i);
            Point math2 = bulletEndPos.get(i);

            if (!(math.y <= math2.y)) {
                math.y -= 5;
            }

            bulletCurrentPos.set(i, math);
        }

    }
    
    // Main engine tick function
    public void engineTick() {
        if (timelineRunning) { timeline++; }
        timelineTickDisplay.setText("Timeline Tick: " + timeline);
    }

    // Main panel loop
    public void actionPerformed(ActionEvent e) {
        engineTick();
        repaint();
    }

    // Keyboard Inputs
    public class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {

                case KeyEvent.VK_LEFT:
                    timeline--;
                        break;

                case KeyEvent.VK_RIGHT:
                    timeline++;
                        break;

                case KeyEvent.VK_SPACE:
                    Point temp = new Point(cursor.x, cursor.y);
                    selectedPos = temp;

                    if (selectedPos.x < 300) {
                        selectedPos.x = 300;
                    }

                    if (selectedPos.x > 900) {
                        selectedPos.x = 900;
                    }
                        break;
           
            }
        }
    } 
}