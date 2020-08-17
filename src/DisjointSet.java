import java.util.ArrayList;

public class DisjointSet {
    private final int[] parent;
    private final int[] rank;

    public DisjointSet(int n){
        this.parent = new int[n];
        this.rank = new int[n];
        for(int i = 0 ; i < n ; ++i){
            this.parent[i] = i;
            this.rank[i] = 0;
        }
    }

    public void join(int x, int y){
        int ancestorX = this.findAncestor(x);
        int ancestorY = this.findAncestor(y);

        if(ancestorX == ancestorY) return;
        if(this.rank[ancestorX] < this.rank[ancestorY]){
            this.parent[ancestorX] = ancestorY;
        }else if(this.rank[ancestorY] < this.rank[ancestorX]){
            this.parent[ancestorY] = ancestorX;
        }else{
            this.parent[ancestorY] = ancestorX;
            this.rank[ancestorX]++;
        }
    }

    public int findAncestor(int node){
        if(this.parent[node] == node) return node;
        else {
            this.parent[node] = this.findAncestor(this.parent[node]);
            return this.parent[node];
        }
    }

    public boolean check(int x, int y){
        return this.findAncestor(x) == this.findAncestor(y);
    }

}
