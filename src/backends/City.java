package backends;

public class City {
    private int x=0;
    private int y=0;
    private int n=0;
    public City(){}
    public void setdata(int x,int y,int n){
        this.x=x;
        this.y=y;
        this.n=n;
    }
    public int getx(){
        return x;
    }
    public int gety(){
        return y;
    }
    public int getn(){
        return n;
    }
    public int getdistence(City p){
        int dx=this.x-p.getx();
        int dy=this.y-p.gety();
        return dx*dx+dy*dy;
    }
}
