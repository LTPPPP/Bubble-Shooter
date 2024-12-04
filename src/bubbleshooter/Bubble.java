package bubbleshooter;

import java.awt.Color;
import java.util.Random;

public class Bubble {

    private int xBub, yBub;
    private final int diameter = 30;
    private Color colorBubbles;

    public Bubble(int xBub, int yBub, Color colorBubbles) {
        this.xBub = xBub;
        this.yBub = yBub;
        this.colorBubbles = colorBubbles;
    }

    public void setRandomColor() {
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PINK};
        Random random = new Random();
        this.colorBubbles = colors[random.nextInt(colors.length)];
    }

    public int getxBub() {
        return xBub;
    }

    public void setxBub(int xBub) {
        this.xBub = xBub;
    }

    public int getyBub() {
        return yBub;
    }

    public void setyBub(int yBub) {
        this.yBub = yBub;
    }

    public Color getColorBubbles() {
        return colorBubbles;
    }

    public void setColorBubbles(Color colorBubbles) {
        this.colorBubbles = colorBubbles;
    }

    public int getDiameter() {
        return diameter;
    }

    @Override
    public String toString() {
        return "Bubble{" + "xBub=" + xBub + ", yBub=" + yBub + ", diameter=" + diameter + ", colorBubbles=" + colorBubbles + '}' + "\n";
    }

}
