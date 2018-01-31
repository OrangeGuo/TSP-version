package backends;

public class Ant {
    public PathList list;//记录路径(已经走过的城市)
    public PathList list_allow;//未走过的城市
    public double distance;//记录距离
    public int length;//记录已走过的城市数量
    public int city_left;//记录剩余城市数量
    public int citynum;//当前城市编号
    public int start;//记录出发点城市编号
    public Ant(int s, int city){
        this.start=s;
        this.distance=0;
        list=new PathList();
        list.n=s;
        list.next=null;
        this.citynum=s;
        list_allow=new PathList();
        list_allow.n=0;
        list_allow.next=null;
        for(int i=1;i<city;i++)
        {
            list_allow.add(i);
        }
        list_allow=list_allow.remove(s);
        this.length=1;
        this.city_left=city-1;
    }
}
