package TouhouEngine;

import javax.swing.JFrame;

public class MyFrame extends JFrame {

    MyFrame() {

        MyPanel panel = new MyPanel();
        this.add(panel);
        this.setTitle("Touhou Engine");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    

    
}
