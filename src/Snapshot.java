import java.util.ArrayList;
import java.util.Random;

public class Snapshot {
    public int n;
    public int time;
    public ArrayList<ArrayList<Integer>> adjacent;

    public Snapshot(int n, int t){
        this.n = n;
        this.time = t;
        this.adjacent = new ArrayList<>();
        for(int i = 0 ; i < n ; ++i){
            ArrayList<Integer> adjacent_i =  new ArrayList<>();
            this.adjacent.add(adjacent_i);
        }
    }

    public ArrayList<Edge> getAllEdges(){
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < n ; ++i){
            for(int j = 0 ; j < this.adjacent.get(i).size(); ++j){
                int u = this.adjacent.get(i).get(j);
                if(u > i){
                    Edge edge = new Edge(i, u);
                    edges.add(edge);
                }
            }
        }
        return edges;
    }

    public void addMST(ArrayList<Edge> mst){
        for(int i=0; i<mst.size(); i++){
            this.adjacent.get(mst.get(i).getU()).add(mst.get(i).getV());
            this.adjacent.get(mst.get(i).getV()).add(mst.get(i).getU());
        }
    }

    public void addEdge(Edge e){
        this.adjacent.get(e.getU()).add(e.getV());
        this.adjacent.get(e.getV()).add(e.getU());
    }

    public void randomEdgeAdder(ArrayList<Edge> es){
        Random rand = new Random();
        int num = rand.nextInt(es.size());
        for(int i=0; i<num; i++){
            int index = rand.nextInt(es.size());
            this.adjacent.get(es.get(index).getU()).add(es.get(index).getV());
            this.adjacent.get(es.get(index).getV()).add(es.get(index).getU());
            es.remove(index);
        }
    }
}
