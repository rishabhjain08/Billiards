
import BinaryHeap.HeapNode;

public class Event implements HeapNode{

    static int HORIZONTAL=-1;
    static int VERTICAL=1;
    private int dir=0;
    private double time;
    private Ball A=null,B=null;
    private boolean collwall=false;
    public void setComparingParameter(double t) {
        time=t;
    }

    public double getComparingValue() {
        return time;
    }
    public Ball getFirstBall(){
        return A;
    }
    public Ball getSecondBall(){
        return B;
    }
    public void setBalls(Ball a,Ball b){
        A=a;
        B=b;
    }
    public void setTime(double t){
        time =t;
    }
    public double getTime(){
        return time;
    }
    public void setCollidingWithWall(boolean b,int dir1){
        collwall=b;
        dir=dir1;
    }
    public boolean isCollidingWithWall(){
        return collwall;
    }
   public int getDirectionofWall(){
       return dir;
    }
}
