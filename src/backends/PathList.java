package backends;

//链表用以记录路径(每个路径视为一个个体)
public class PathList {
    public int n=-1;
    public PathList next=null;

    public PathList(){

    }
    public PathList(PathList li){
        this.n=li.n;
        li=li.next;
        while(li!=null)
        {
            this.add(li.n);
            li=li.next;
        }
    }
    public void add(int citynum)
    {
        PathList list=this;
        while(list.next!=null)
        {
            list=list.next;
        }
        PathList list2=new PathList();
        list2.n=citynum;
        list.next=list2;
    }
    public PathList remove(int citynum)
    {
        if(this.n==citynum)
        {
            return this.next;
        }
        else {
            PathList list=this;
            PathList li=list;
            PathList li_next=list.next;
            while(li_next!=null)
            {
                if(li_next.n==citynum)
                {
                    li.next=li_next.next;
                    return list;
                }
                li=li.next;
                li_next=li_next.next;
            }
            list=null;
            return list;
        }
    }
    public int get(int i)
    {
        if(i==0)
        {
            return this.n;
        }
        else
        {
            PathList list_now=this;
            for(int j=0;j<i;j++)
            {
                if(list_now.next!=null)list_now=list_now.next;
            }
            return list_now.n;
        }
    }
    public void set(int i,int temp)
    {
        if(i==0)
        {
            this.n=temp;
        }
        else {
            PathList list_now=this;
            for(int j=0;j<i;j++)
            {
                list_now=list_now.next;
            }
            list_now.n=temp;
        }
    }
    public PathList setfirst(int n)
    {
        if(this.n==n)
        {
            return this;
        }
        else {
            PathList list_copy=this;
            PathList list_first=this;
            while(list_copy.next!=null)
            {
                list_copy=list_copy.next;
            }
            while(list_first.next!=null)
            {
                list_copy.next=new PathList();
                list_copy.next.n=list_first.n;
                list_copy=list_copy.next;
                list_first=list_first.next;
                if(list_first.n==n)
                {
                    return list_first;
                }
                if(list_first.n==this.n)
                {
                    break;
                }
            }
            return new PathList();
        }
    }
}