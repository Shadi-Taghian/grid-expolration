import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Explore {
    public static ArrayList<Snapshot> grid = new ArrayList<Snapshot>();
    public static ArrayList<Edge> staticEdges = new ArrayList<>();
    public static ArrayList<Agent> agents = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        fillStaticEdges(n);
        createGrid(n);
        for(int i=0; i<grid.size(); i++){
            System.out.println(grid.get(i).time);
        }
        //createAgents(n);
    }

    public static void createGrid(int n){
        int lifeTime = (int) Math.pow(n,2);

        for(int i=1; i<lifeTime; i++){
            Snapshot s = new Snapshot(n, i);
            Snapshot msTree = mst(n);
            s.addMST(msTree.getAllEdges());
            s.randomEdgeAdder(findRemainingEdges(msTree));
            grid.add(s);
        }
    }

    public static ArrayList<Edge> findRemainingEdges(Snapshot snapshot){
        ArrayList<Edge> arr = new ArrayList<>(staticEdges);
        ArrayList<Edge> snapshotEdges = snapshot.getAllEdges();
        for(int i=0; i<snapshotEdges.size(); i++){
            for(int j=0; j<arr.size(); j++){
                if((snapshotEdges.get(i).getU() == arr.get(j).getU()
                        && snapshotEdges.get(i).getV() == arr.get(j).getV())
                        || (snapshotEdges.get(i).getU() == arr.get(j).getV()
                        && snapshotEdges.get(i).getV() == arr.get(j).getU())){
                    arr.remove(j);
                }
            }
        }
        return arr;
    }

    public static void fillStaticEdges(int n){
        for(int i=0; i<n-1; i++){
            if(i%2 == 1){
                Edge e = new Edge(i, i+1);
                staticEdges.add(e);
            }
        }
        for(int j = 0; j < (n - 2); j++){
            Edge eh = new Edge(j, j+2);
            staticEdges.add(eh);
        }
    }


    public static void createAgents(int n){
        int count = (int) Math.log(n) * 4;
        for(int i=0; i<count; i++){
            Agent a = new Agent();
            agents.add(a);
        }
    }

    public static Snapshot mst(int n){
        Random rand = new Random();
        for(int i=0; i<staticEdges.size(); i++){
            int w = rand.nextInt(staticEdges.size());
            staticEdges.get(i).setW(w);
        }
        Collections.sort(staticEdges);
        Snapshot msTree = new Snapshot(n,0);
        DisjointSet dSet = new DisjointSet(n);
        for(int j=0; j<staticEdges.size(); j++){
            if(!dSet.check(staticEdges.get(j).getU(), staticEdges.get(j).getV())){
                msTree.addEdge(staticEdges.get(j));
                dSet.join(staticEdges.get(j).getU(), staticEdges.get(j).getV());
            }
        }
        return msTree;
    }
}
