package BinaryHeap;

public class BinaryHeap{
   int SIZE=0;
   HeapNode HEAP[];
   int LAST_INDEX=-1;
   public BinaryHeap(int n){
        SIZE=n;
        HEAP=new HeapNode[n];
    }
   public int size(){
       return (LAST_INDEX+1);
   }
    public int getParentLocation(int i){
        return (int) (Math.floor((i+1)/2)-1);
    }
    public int getLeftChildLocation(int i){
        return (2*(i+1)-1);
    }
    public int getRightChildLocation(int i){
        return (2*(i+1));
    }
    public boolean insert(HeapNode o){
         if(LAST_INDEX==SIZE-1||o==null)
            return false;
        this.HEAP[++LAST_INDEX]=o;
        int i=LAST_INDEX;
        HeapNode temp=null;
        while(i>0&&(this.HEAP[this.getParentLocation(i)].getComparingValue()>this.HEAP[i].getComparingValue())){
            temp=this.HEAP[this.getParentLocation(i)];
            this.HEAP[this.getParentLocation(i)]=this.HEAP[i];
            this.HEAP[i]=temp;
            i=this.getParentLocation(i);
           }
        //System.out.println("insert()");
        //this.check();
           return true;
    }
    public boolean deleteMin(){
        if(this.LAST_INDEX==-1)
            return false;
        this.HEAP[0]=this.HEAP[this.LAST_INDEX];
        this.HEAP[this.LAST_INDEX--]=null;
        this.Heapify(0);
        //System.out.println("deleteMin()");
       // this.check();
        return true;
    }
    public HeapNode getMin(){
        return this.HEAP[0];
    }
    private void Heapify(int o){
        HeapNode temp=null;
        int smallchild;

        while(true){
           if(this.getLeftChildLocation(o)>this.HEAP.length||this.HEAP[this.getLeftChildLocation(o)]==null)
               break;
           if(this.HEAP[this.getRightChildLocation(o)]!=null){
                smallchild=0;
                if(this.HEAP[this.getRightChildLocation(o)].getComparingValue()>this.HEAP[this.getLeftChildLocation(o)].getComparingValue())
                        smallchild=this.getLeftChildLocation(o);
                else
                        smallchild=this.getRightChildLocation(o);
            }
             else
                 smallchild=this.getLeftChildLocation(o);
            if(!(this.HEAP[o].getComparingValue()>this.HEAP[smallchild].getComparingValue()))
                break;
            temp=this.HEAP[o];
            this.HEAP[o]=this.HEAP[smallchild];
            this.HEAP[smallchild]=temp;
            o=smallchild;
        }
     //   System.out.println("HEAPIFY");
   //     this.check();
     }
/*    public void check(){
        int i=0;
        while(i<=LAST_INDEX){
            if(this.HEAP[i]==null){
                System.out.println("Ele is NULL");
      //          System.exit(0);
            }
        if(this.getRightChildLocation(i)<=LAST_INDEX&&this.HEAP[this.getLeftChildLocation(i)]==null&&this.HEAP[this.getRightChildLocation(i)]!=null){
            System.out.println("A3");
    //        System.exit(0);
        }
         if(this.getLeftChildLocation(i)<=LAST_INDEX&&this.HEAP[this.getLeftChildLocation(i)]!=null){
             if(this.HEAP[i].getComparingValue()>this.HEAP[this.getLeftChildLocation(i)].getComparingValue()){
                    System.out.println("A1 "+this.HEAP[i].getComparingValue()+","+this.HEAP[this.getLeftChildLocation(i)].getComparingValue());
  //                  System.exit(0);
              }
        }
        if(this.getRightChildLocation(i)<=LAST_INDEX&&this.HEAP[this.getRightChildLocation(i)]!=null){
             if(this.HEAP[i].getComparingValue()>this.HEAP[this.getRightChildLocation(i)].getComparingValue()){
                 System.out.println("A2");
//                    System.exit(0);
              }
        }
        i++;
        }
    }
 * 
 */
}