package bubbleshooter;

import java.util.ArrayList;

public class RowList extends ArrayList<Bubble> {

    private boolean full;

    public RowList(boolean full) {
        this.full = full;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull() {
        full = true;
    }

}
