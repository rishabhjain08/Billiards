
import java.awt.Color;


public class Ball{
    double x=0,y=0;
    double vx=0,vy=0;
    int dirofwall,index;
    double radius;
    boolean state=true;
    boolean highlited=false;
    Color color=null,hcolor=null;
    public void delete(){
        state=false;
    }
    public void setHighlightColor(boolean b,Color color1){
        highlited=b;
        hcolor=color1;
    }
    public Color getHighlightedColor(){
        if(hcolor!=null)
            return hcolor;
        return this.getColor();
    }
    public void setColor(Color c){
        color=c;
    }
    public Color getColor(){
        return color;
    }
    public void setRadius(double rad){
        radius=rad;
    }
    public double getRadius(){
        return radius;
    }
    public boolean exists(){
        return state;
    }
    public void setIndex(int i){
        index=i;
    }
    public int getIndex(){
        return index;
    }

    public void setPosition(double x1,double y1){
        x=x1;
        y=y1;
    }
    public void setVelocity(double vx1,double vy1){
        vx=vx1;
        vy=vy1;
    }
    public Components getPosition(){
        return new Components(x,y);
    }
    public Components getVelocity(){
        return new Components(vx,vy);
    }
    public Components getVelocityUnitVector(){
        double theta=Math.atan(vy/vx);
        return new Components(vx*Math.cos(theta)/(Math.sqrt(vx*vx+vy*vy)),vy*Math.sin(theta)/(Math.sqrt(vx*vx+vy*vy)));
    }
    public Components VelocityAlongUnitVector(Components comp){
        double dotproduct=vx*comp.getX()+vy*comp.getY();
        return new Components(comp.getX()*dotproduct,comp.getY()*dotproduct);
    }
    public Components VelocityPerpendicularTOUnitVector(Components comp){
        Components along=this.VelocityAlongUnitVector(comp);
        return new Components(vx-along.getX(),vy-along.getY());
    }
    public void setDirectionofWall(int i){
        dirofwall=i;
    }
    public int getDirectionofWall(){
        return dirofwall;
    }

}