package backends;

//链表用以记录路径(每个路径视为一个个体)
public class Path {
    public int no =-1;
    public Path next=null;


    public Path(Path li){
        this.no =li.no;
        li=li.next;
        while(li!=null)
        {
            this.add(li.no);
            li=li.next;
        }
    }

    public Path() {

    }

    public void add(int citynum)
    {
        Path list=this;
        while(list.next!=null)
        {
            list=list.next;
        }
        Path list2=new Path();
        list2.no =citynum;
        list.next=list2;
    }
    public Path remove(int citynum)
    {
        if(this.no ==citynum)
        {
            return this.next;
        }
        else {
            Path list=this;
            Path li=list;
            Path li_next=list.next;
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
            Path list_now=this;
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
            Path list_now=this;
            for(int j=0;j<i;j++)
            {
                list_now=list_now.next;
            }
            list_now.no =temp;
        }
    }
    public Path setfirst(int n)
    {
        if(this.no ==n)
        {
            return this;
        }
        else {
            Path list_copy=this;
            Path list_first=this;
            while(list_copy.next!=null)
            {
                list_copy=list_copy.next;
            }
            while(list_first.next!=null)
            {
                list_copy.next=new Path();
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
            return new Path();
        }
    }
}