
//http://www.edu4java.com/en/game/game1.html

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game2 extends JPanel {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;
    
    @Override
    public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.RED);
            g2d.fillOval(0, 0, 30, 30);
            g2d.drawOval(0, 50, 30, 30);		
            g2d.fillRect(50, 0, 30, 30);
            g2d.drawRect(50, 50, 30, 30);

            g2d.draw(new Ellipse2D.Double(0, 100, 30, 30));
    }

    public static void main(String[] args) {
            JFrame frame = new JFrame("Mini Tennis");
            frame.add(new Game2());
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setLocation(dim.width/2 - WIDTH/2, dim.height/2 - HEIGHT/2);
            frame.setSize(WIDTH, HEIGHT);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}