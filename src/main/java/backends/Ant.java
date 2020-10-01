package backends;

import lombok.Data;

@Data
public class Ant {
    private Path walkOverCities;//记录路径(已经走过的城市)
    private Path toGoCities;//未走过的城市
    private double distance;//记录距离
    private int walkOverCityNum;//记录已走过的城市数量
    private int cityNumLeft;//记录剩余城市数量
    private int cityNo;//当前城市编号
    private int departCityNo;//记录出发点城市编号
    public Ant(int s, int city){
        this.departCityNo =s;
        this.distance=0;
        walkOverCities =new Path();
        walkOverCities.no =s;
        walkOverCities.next=null;
        this.cityNo =s;
        toGoCities =new Path();
        toGoCities.no =0;
        toGoCities.next=null;
        for(int i=1;i<city;i++)
        {
            toGoCities.add(i);
        }
        toGoCities = toGoCities.remove(s);
        this.walkOverCityNum =1;
        this.cityNumLeft =city-1;
    }
}
