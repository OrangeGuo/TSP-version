package backends;

//链表用以记录路径(每个路径视为一个个体)
public class PathList {
    public int no =-1;
    public PathList next=null;

    public PathList(){

    }
    public PathList(PathList li){
        this.no =li.no;
        li=li.next;
        while(li!=null)
        {
            this.add(li.no);
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
        list2.no =citynum;
        list.next=list2;
    }
    public PathList remove(int citynum)
    {
        if(this.no ==citynum)
        {
            return this.next;
        }
        else {
            PathList list=this;
            PathList li=list;
            PathList li_next=list.next;
            while(li_next!=null)
            {
                if(li_next.no ==citynum)
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
            return this.no;
        }
        else
        {
            PathList list_now=this;
            for(int j=0;j<i;j++)
            {
                if(list_now.next!=null)list_now=list_now.next;
            }
            return list_now.no;
        }
    }
    public void set(int i,int temp)
    {
        if(i==0)
        {
            this.no =temp;
        }
        else {
            PathList list_now=this;
            for(int j=0;j<i;j++)
            {
                list_now=list_now.next;
            }
            list_now.no =temp;
        }
    }
    public PathList setfirst(int n)
    {
        if(this.no ==n)
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
                list_copy.next.no =list_first.no;
                list_copy=list_copy.next;
                list_first=list_first.next;
                if(list_first.no ==n)
                {
                    return list_first;
                }
                if(list_first.no ==this.no)
                {
                    break;
                }
            }
            return new PathList();
        }
    }
}