import java.util.ArrayList;

public class Agent {

    ArrayList<Move> moves;
    private int initialNode;
    private int currentNode;

    public Agent(){
        moves = new ArrayList<Move>();
    }

    public int getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(int currentNode) {
        this.currentNode = currentNode;
    }

    public int getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(int initialNode) {
        this.initialNode = initialNode;
    }
}
