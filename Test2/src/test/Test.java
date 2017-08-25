
package test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import static test.Test.btn_1;

//--------------------------------------------------------------------------------
class MyPanel extends JPanel {
    public MyPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        }

    public Dimension getPreferredSize() {
        return new Dimension(640,640);
        }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);      
        g.drawString("This is my custom Panel!",10,20);
        g.setColor(Color.red);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5.0f));
        g2.drawLine(10, 10,  630, 630);
        g2.drawLine(10, 630, 630, 10);
        
        g.setColor(Color.blue);
        g2.drawRect(20, 20, 600, 600);
        }  
}
//--------------------------------------------------------------------------------
class TestActionListener implements ActionListener {
     public void actionPerformed(ActionEvent e) {
         String btn = e.getActionCommand();
         if(btn == Test.btn_0.getActionCommand())
            Test.btn_0.setLocation(Test.btn_0.getLocation().x, Test.btn_0.getLocation().y+64);
         if(btn == Test.btn_1.getActionCommand())
            Test.btn_1.setLocation(Test.btn_1.getLocation().x, Test.btn_1.getLocation().y+64);
     }
}
//--------------------------------------------------------------------------------
class MyButton extends JButton {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Shape s = g.getClip();
        String text = "test";
        
        Graphics2D g2 = (Graphics2D) g;
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setFont(new Font("Serif", Font.PLAIN, 24));
        FontRenderContext frc = g2.getFontRenderContext();
        Rectangle2D bounds = g2.getFont().getStringBounds(text, frc);
//        Font f = g.getFont();
//        Rectangle2D bounds = f.getStringBounds(text, frc);
    
        g.drawString(text, (int) (s.getBounds().width/2 - bounds.getWidth()/2), s.getBounds().height/2-10);
        
        Rectangle2D r = g2.getClipBounds();
        g2.drawRect((int) (r.getX()+5), (int) (r.getY()+5), (int) (r.getWidth()-10), (int) (r.getHeight()-10));
    }
}
//--------------------------------------------------------------------------------
public class Test {
    static JButton btn_0;
    static MyButton btn_1;
    
    public static void read() {
        try {
            TestRead t = new TestRead();
            t.test("test.txt");
        } catch (Exception e) {
            System.err.println("Exception: "+e.toString());
        }
    }
    
    public static void write() {
        try {
            TestWrite t = new TestWrite();
            t.test("test.txt");
        } catch (IOException e) {
            System.err.println("Exception: "+e.toString());
        }
    }
    
    public static void test_frame() {
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = ge.getScreenDevices();
        DisplayMode dmode = devices[0].getDisplayMode();
        int screen_w = dmode.getWidth();
        int screen_h = dmode.getHeight();
        int frame_w = 640;
        int frame_h = 640;

        btn_0 = new JButton();
        btn_0.setIcon(null);
        btn_0.setSize(64, 64);
        btn_0.setText("0");
        
        btn_1 = new MyButton();
        btn_1.setSize(64, 64);
        btn_1.setText("1");
        btn_1.setLocation(64, 0);
 
        JPanel panel = new JPanel();
        //panel.setLayout(new GridLayout(2,2,1,1));
        panel.setLayout(null);
        panel.add(btn_0);
        panel.add(btn_1);
        
        ActionListener actionListener = new TestActionListener();
        btn_0.addActionListener(actionListener);
        btn_1.addActionListener(actionListener);

        MyPanel main_panel = new MyPanel();
        main_panel.setPreferredSize(new Dimension(640, 640));
        
        int x = screen_w/2-frame_w/2;
        int y = screen_h/2-frame_h/2;
        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setTitle("пробный фрейм");
        frame.setBounds(x, y, frame_w, frame_h);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.add(main_panel);
        frame.add(panel);
        //frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        //write();
        // read();
        test_frame();
    }
}
//--------------------------------------------------------------------------------
