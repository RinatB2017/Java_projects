/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test3;

import java.awt.Graphics2D;

/**
 *
 * @author boss
 */
public class Ball {
    int x = 0;
    int y = 0;
    int xa = 1;
    int ya = 1;
    private final Main main;

    public Ball(Main main) {
        this.main = main;
    }
    
    void move() {
        if (x + xa < 0)
                xa = 1;
        if (x + xa > main.getWidth() - 30)
                xa = -1;
        if (y + ya < 0)
                ya = 1;
        if (y + ya > main.getHeight() - 30)
            ya = -1;

        x = x + xa;
        y = y + ya;
    }
    
    public void paint(Graphics2D g) {
        g.fillOval(x, y, 30, 30);
    }
}
