import java.awt.desktop.SystemEventListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Explore {
    public static ArrayList<Snapshot> grid = new ArrayList<>();
    public static ArrayList<Edge> staticEdges = new ArrayList<>();
    public static ArrayList<Agent> agents = new ArrayList<>();
    public static ArrayList<Agent> usedAgents = new ArrayList<>();


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        fillStaticEdges(n);
        createGrid(n);
        gridWriter();
        createAgents(n);

        ArrayList<Integer> times = new ArrayList<>();
        for(int i=0; i<grid.size(); i++){
            times.add(i);
        }
        explore(0,n-1,times);
    }

    public static void createGrid(int n){
        int lifeTime = (int) Math.pow(n,2);

        for(int i=0; i<lifeTime; i++){
            Snapshot s = new Snapshot(n, i);
            Snapshot msTree = mst(n);
            ArrayList<Edge> f = msTree.getAllEdges();
            s.addMST(f);
            s.randomEdgeAdder(findRemainingEdges(s));
            grid.add(s);
        }
    }

    public static ArrayList<Edge> findRemainingEdges(Snapshot snapshot){
        ArrayList<Edge> arr = copyArray(staticEdges);
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
            if(i%2 == 0){
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
        int count = (int) Math.log(n) * 8;
        for(int i=0; i<count; i++){
            Agent a = new Agent();
            agents.add(a);
        }
    }

    public static void explore(int start, int end, ArrayList<Integer> times){
        System.out.println(ANSI_PURPLE+"------- <<Exploration begins>> -------"+ANSI_RESET);
        if(start+2 >= end-1){
            System.out.println("exploration finished!");
        }
        else {
            Agent al1 = agents.get(0);
            usedAgents.add(al1);
            agents.remove(0);
            Agent al2 = agents.get(0);
            usedAgents.add(al2);
            agents.remove(0);
            Agent ar1 = agents.get(0);
            usedAgents.add(ar1);
            agents.remove(0);
            Agent ar2 = agents.get(0);
            usedAgents.add(ar2);
            agents.remove(0);
            al1.setInitialNode(start);
            al2.setInitialNode(start + 1);
            ar1.setInitialNode(end - 1);
            ar2.setInitialNode(end);
            al1.setCurrentNode(start);
            al2.setCurrentNode(start + 1);
            ar1.setCurrentNode(end - 1);
            ar2.setCurrentNode(end);
            boolean left, right;
            int count =0;
            ArrayList<Integer> badTimes = new ArrayList<>();

            for (int i = 0; i < times.size(); i++) {
                left = false;
                right = false;
                Snapshot currentGrid = grid.get(times.get(i));
                if(al1.getCurrentNode()+2 >= ar1.getCurrentNode()){
                    System.out.println("exploration finished");
                    break;
                }
                else {
                    if (count > ((end - start) / 2)) {
                        explore(al1.getCurrentNode(), ar2.getCurrentNode(), badTimes);
                        break;
                    } else {
                        if (currentGrid.containEdge(al1.getCurrentNode(), al1.getCurrentNode() + 2)
                                && currentGrid.containEdge(al2.getCurrentNode(), al2.getCurrentNode() + 2)) {
                            al1.setCurrentNode(al1.getCurrentNode() + 2);
                            al2.setCurrentNode(al2.getCurrentNode() + 2);
                            System.out.println(currentGrid.time);
                            System.out.println("Left agents moved");
                            left = true;
                        }
                        if (currentGrid.containEdge(ar1.getCurrentNode(), ar1.getCurrentNode() - 2)
                                && currentGrid.containEdge(ar2.getCurrentNode(), ar2.getCurrentNode() - 2)) {
                            ar1.setCurrentNode(ar1.getCurrentNode() - 2);
                            ar2.setCurrentNode(ar2.getCurrentNode() - 2);
                            System.out.println(currentGrid.time);
                            System.out.println("Right agents moved");
                            right = true;
                        }
                        if (!right && !left) {
                            badTimes.add(times.get(i));
                            count++;
                        } else {
                            count = 0;
                        }
                    }
                }
            }
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

    public static ArrayList<Edge> copyArray(ArrayList<Edge> e){
        ArrayList<Edge> newArray = new ArrayList<>();
        for(int i=0; i<e.size(); i++){
            newArray.add(e.get(i));
        }
        return newArray;
    }

    public static void gridWriter(){
        try {
            File myObj = new File("temporal-grid.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter myWriter = new FileWriter("temporal-grid.txt");
            for(int i=0; i<grid.size(); i++){
                myWriter.write("snapshot " + i + "\n");
                for(int j=0; j<grid.get(i).getAllEdges().size(); j++){
                    myWriter.write("u: " + grid.get(i).getAllEdges().get(j).getU() +
                            ", v: " + grid.get(i).getAllEdges().get(j).getV());
                    myWriter.write("\n");
                }
                myWriter.write("................................." + "\n");
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
