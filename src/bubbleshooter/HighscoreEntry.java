package bubbleshooter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;

public class HighscoreEntry implements Serializable, Comparable<HighscoreEntry>, Comparator<HighscoreEntry> {

    private String name;
    private long score;
    private int rows;
    private int color;

    public HighscoreEntry(String name, long score, int rows, int color) {
        this.name = name;
        this.score = score;
        this.rows = rows;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void writeObject(ObjectOutputStream o) throws IOException {
        o.writeObject(name);
        o.writeLong(score);
        o.writeInt(rows);
        o.writeInt(color);
    }

    public void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException {
        name = (String) o.readObject();
        score = (long) o.readLong();
        rows = (int) o.readInt();
        color = (int) o.readInt();
    }

    @Override
    public int compareTo(HighscoreEntry other) {
        if (score > other.getScore()) {
            return -1;
        }
        if (score < other.getScore()) {
            return 1;
        }
        return 0;
    }

    @Override
    public int compare(HighscoreEntry h1, HighscoreEntry h2) {
        if (h1.score > h2.getScore()) {
            return -1;
        }
        if (h1.score < h2.getScore()) {
            return 1;
        }
        return 0;
    }
}
