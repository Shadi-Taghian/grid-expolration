import java.util.ArrayList;

public class Agent {

    ArrayList<Move> moves;
    private int node;

    public Agent(){
        moves = new ArrayList<Move>();
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }
}
