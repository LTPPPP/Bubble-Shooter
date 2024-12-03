package bubbleshooter;

import java.awt.Color;
import java.util.Random;

public class Bubble {

    private int xBub;
    private int yBub;
    private Color colorBubbles;

    public Bubble(int xBub, int yBub, Color colorBubbles) {
        this.xBub = xBub;
        this.yBub = yBub;
        this.colorBubbles = colorBubbles;
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

    public void setRandomColor() {
        Color[] bubbleColors = {
            new Color(222, 214, 193), Color.BLUE, Color.ORANGE, Color.RED
        };
        Random random = new Random();
        this.colorBubbles = bubbleColors[random.nextInt(bubbleColors.length)];
    }
}