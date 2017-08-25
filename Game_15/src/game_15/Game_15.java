
package game_15;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

//--------------------------------------------------------------------------------
class TestActionListener implements ActionListener {
     public void actionPerformed(ActionEvent e) {
         String btn = e.getActionCommand();
         for(int n=0; n<15; n++) {
             if(btn == Game_15.btn[n].getActionCommand()) {
                Game_15.btn[n].setLocation(Game_15.btn[n].getLocation().x, Game_15.btn[n].getLocation().y+64);
             }
         }
     }
}
//--------------------------------------------------------------------------------
public class Game_15 {
    public static JFrame main_frame;
    public static JButton[] btn = new JButton[15]; 
    static final int SIZE = 64;
    
    public static void main(String[] args) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = ge.getScreenDevices();
        DisplayMode dmode = devices[0].getDisplayMode();
        int screen_w = dmode.getWidth();
        int screen_h = dmode.getHeight();
        int frame_w = SIZE*4;
        int frame_h = SIZE*4;
        int frame_x = screen_w/2-frame_w/2;
        int frame_y = screen_h/2-frame_h/2;
        
        int n = 0;
        ActionListener actionListener = new TestActionListener();
        for(int y=0; y<4; y++) {
            for(int x=0; x<4; x++) {
                if(n<15) {
                    btn[n] = new JButton();
                    btn[n].setSize(SIZE, SIZE);
                    btn[n].setText(Integer.toString(n));
                    btn[n].setLocation(x*SIZE, y*SIZE);
                    btn[n].addActionListener(actionListener);
                    n++;
                }
            }
        }
        
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(SIZE*4, SIZE*4));
        panel.setLayout(null);
        for(n=0; n<15; n++) {
            panel.add(btn[n]);
        }
        
        main_frame = new JFrame();
        main_frame.setTitle("15");
        main_frame.setResizable(false);
        main_frame.setBounds(frame_x, frame_y, frame_w, frame_h);
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_frame.add(panel);
        main_frame.pack();
        main_frame.setVisible(true);
    }
}
//--------------------------------------------------------------------------------
