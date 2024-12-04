package bubbleshooter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.table.AbstractTableModel;

public class HighScores extends AbstractTableModel implements Serializable {

    ArrayList<HighscoreEntry> entries = new ArrayList<HighscoreEntry>();

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public int getRowCount() {
        return entries.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        HighscoreEntry entry = entries.get(row);
        switch (col) {
            case 0:
                return entry.getName();
            case 1:
                return entry.getScore();
            case 2:
                return entry.getRows();
            default:
                return entry.getColor();
        }
    }

    @Override
    public String getColumnName(int index) {
        switch (index) {
            case 0:
                return "Name";
            case 1:
                return "Score";
            case 2:
                return "#rows";
            default:
                return "#colors";
        }
    }

    @Override
    public Class getColumnClass(int index) {
        switch (index) {
            case 0:
                return String.class;
            case 1:
                return Long.class;
            case 2:
                return Integer.class;
            default:
                return Integer.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int colIndex) {
        return false;
    }

    public void addEntry(HighscoreEntry e) {
        entries.add(e);
        Collections.sort(entries);
    }

    public void writeObject(ObjectOutputStream o) throws IOException {
        o.writeObject(entries);
    }

    public void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException {
        entries = (ArrayList<HighscoreEntry>) o.readObject();
    }

    public void print() {
        for (HighscoreEntry h : entries) {
            System.out.println(h.getName() + " " + h.getScore() + " " + h.getRows() + " " + h.getColor());
        }
    }
}
