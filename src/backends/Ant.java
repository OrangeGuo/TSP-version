package backends;

public class Ant {
    public PathList list;//��¼·��(�Ѿ��߹��ĳ���)
    public PathList list_allow;//δ�߹��ĳ���
    public double distance;//��¼����
    public int length;//��¼���߹��ĳ�������
    public int city_left;//��¼ʣ���������
    public int citynum;//��ǰ���б��
    public int start;//��¼��������б��
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
