/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bubbleshooter;

/**
 *
 * @author Gigabyte
 */
public class Bubble {

    private int xBub;
    private int yBub;

    public Bubble(int xBub, int yBub) {
        this.xBub = xBub;
        this.yBub = yBub;
    }

    // Getters and setters
    public int getX() {
        return xBub;
    }

    public int getY() {
        return yBub;
    }

    public void setX(int xBub) {
        this.xBub = xBub;
    }

    public void setY(int yBub) {
        this.yBub = yBub;
    }

}
