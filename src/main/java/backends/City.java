package backends;


import lombok.Data;

@Data
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
    public int getdistence(City p){
        int dx=this.x-p.getX();
        int dy=this.y-p.getY();
        return dx*dx+dy*dy;
    }
}
