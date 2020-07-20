public class Edge implements Comparable{
    private int v;
    private int u;
    private int w;

    public Edge(int v, int u){
        this.v = v;
        this.u = u;
    }

    public int getU(){
        return this.u;
    }

    public int getV(){
        return this.v;
    }

    public void setW(int w){
        this.w = w;
    }

    public int getW() {
        return w;
    }

    @Override
    public int compareTo(Object o) {
        Edge e = (Edge) o;
        return Integer.compare(this.getW(), e.getW());
    }
}
