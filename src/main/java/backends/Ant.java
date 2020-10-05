package backends;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class Ant {
    private List<Integer> walkOverCities;//记录路径(已经走过的城市)
    private List<Integer> toGoCities;//未走过的城市
    private double distance;//记录距离
    private int walkOverCityNum;//记录已走过的城市数量
    private int cityNumLeft;//记录剩余城市数量
    private Integer cityNo;//当前城市编号
    private Integer departCityNo;//记录出发点城市编号
    public Ant(int start, int cityNum){
        this.departCityNo =start;
        this.distance=0;
        walkOverCities = Lists.newArrayList();
        walkOverCities.add(start);
        this.cityNo =start;
        toGoCities =Lists.newArrayList();
        for(int i=0;i<cityNum;i++)
        {
            toGoCities.add(i);
        }
        toGoCities.remove(departCityNo);
        this.walkOverCityNum =1;
        this.cityNumLeft =cityNum-1;
    }
}
