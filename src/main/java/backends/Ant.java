package backends;

import lombok.Data;

@Data
public class Ant {
    private Path walkOverCities;//��¼·��(�Ѿ��߹��ĳ���)
    private Path toGoCities;//δ�߹��ĳ���
    private double distance;//��¼����
    private int walkOverCityNum;//��¼���߹��ĳ�������
    private int cityNumLeft;//��¼ʣ���������
    private int cityNo;//��ǰ���б��
    private int departCityNo;//��¼��������б��
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
