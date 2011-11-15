import BinaryHeap.BinaryHeap;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Table extends Thread{
	static Dimension SCREEN_SIZE=Toolkit.getDefaultToolkit().getScreenSize();
	Ball[] holes;
	double currtime;
	int nball=0;
	Ball[] balls;
	static double radius=20;
	private draw draw=null;
	Table(){
	}
	@Override
	public void run(){
		System.err.println("WIDTH OF SCREEN = "+SCREEN_SIZE.width+" HEIGHT OF SCREEN = "+SCREEN_SIZE.height);
		Scanner scanfile=null;
		try{
			scanfile = new Scanner(new File("C:/Users/ci/Desktop/ballinfo2.txt"));
		} catch (FileNotFoundException ex){
			Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
		}
		int size=Integer.parseInt(scanfile.nextLine());
		balls=new Ball[size+6];
		int com1,com2,com3;
		String g=null;
		int i=0;
		while(scanfile.hasNextLine()){
			g=scanfile.nextLine();
			com1=g.indexOf(',');
			com2=g.indexOf(',', com1+1);
			com3=g.indexOf(',', com2+1);
			balls[i]=new Ball();
			balls[i].setPosition(Double.parseDouble(g.substring(0, com1)), Double.parseDouble(g.substring(com1+1, com2)));
			balls[i].setVelocity(Double.parseDouble(g.substring(com2+1, com3)), Double.parseDouble(g.substring(com3+1)));
			balls[i].setIndex(i);
			balls[i].setRadius(radius);
			balls[i].setColor(Color.red);
			i++;
		}
		nball=i;
		int remi=i;
		while(i<balls.length){
			balls[i]=new Ball();
			if(i-remi==0)
				balls[i].setPosition(0, 0);
			else if(i-remi==1)
				balls[i].setPosition(SCREEN_SIZE.width / 2, 0);
			else if(i-remi==2)
				balls[i].setPosition(SCREEN_SIZE.width, 0);
			else if(i-remi==3)
				balls[i].setPosition(0, SCREEN_SIZE.height);
			else if(i-remi==4)
				balls[i].setPosition(SCREEN_SIZE.width / 2, SCREEN_SIZE.height);
			else if(i-remi==5)
				balls[i].setPosition(SCREEN_SIZE.width, SCREEN_SIZE.height);
			balls[i].setVelocity(0, 0);
			balls[i].setIndex(i);
			balls[i].setRadius(radius*1.2);
			balls[i].setColor(Color.black);
			i++;
		}
		scanfile.close();
		BinaryHeap heap=new BinaryHeap(nball*nball*nball);
		draw.initiatedrawtable(nball);
		i=0;
		while(i<nball){
			heap.insert(this.calculatenextEvent(balls[i],true));
			i++;
		}
		int max=-1;
		Event currevent=null,preEvent=null;
		Ball A=null,B=null;
		int remcond=0;
		while(true){
			if(heap.size()>max)
				max=heap.size();
			currevent=(Event)heap.getMin();
			heap.deleteMin();
			if(currevent==null){
				i=0;
				boolean insert=false;
				System.err.println("XOR");
				while(i<nball){
					insert = heap.insert(this.calculatenextEvent(balls[i], true));
					i++;
				}
				if(!insert){
					draw.setParam(balls,1,this);
					System.out.println("MAX : "+max);
					Thread.currentThread().suspend();
				}
				continue;
			}
			System.out.println("currevent is : "+currevent);
			if(!((remcond=this.checkIfOccuring(currevent))>0)){
				if(remcond==-4){
					heap.insert(this.calculatenextEvent(currevent.getFirstBall(), false));
					heap.insert(this.calculatenextEvent(currevent.getSecondBall(), false));
				}
				continue;
			}
			if(this.areEventsEqual(currevent,preEvent)){
				continue;
			}
			preEvent=currevent;
			draw.setParam(balls, currevent.getComparingValue()-currtime,this);
			this.simulate(currevent);
			A=currevent.getFirstBall();
			B=currevent.getSecondBall();
			heap.insert(this.calculatenextEvent(A,false));
			heap.insert(this.calculatenextEvent(B,false));
		}
	}

	public void setParam(draw draw1){
		draw=draw1;
	}
	private int checkIfOccuring(Event e){
		System.out.println("check for event at t = "+e.getComparingValue());
		double movetime=e.getComparingValue()-currtime;
		if(movetime<0)
			return -1;
		double corrfactor=2;
		Ball a=e.getFirstBall();
		Ball b=e.getSecondBall();
		if(e.isCollidingWithWall()){
			double xpos= a.getPosition().getX()+a.getVelocity().getX()*movetime;
			double ypos=a.getPosition().getY()+a.getVelocity().getY()*movetime;
			Ball a1=e.getFirstBall();
			if(e.getDirectionofWall()==Event.VERTICAL){
				if(((xpos+a1.getRadius()>SCREEN_SIZE.width+corrfactor)||(xpos-a1.getRadius()<-corrfactor))||((xpos+a1.getRadius()<SCREEN_SIZE.width-corrfactor)&&(xpos-a1.getRadius()>corrfactor))){
					System.out.println("xpos : "+xpos+" ypos : "+ypos);
					System.out.println("CASE 1");
					return -2;
				}
			}
			else if(((ypos+a1.getRadius()>SCREEN_SIZE.height+corrfactor)||(ypos-a1.getRadius()<-corrfactor))||((ypos+a1.getRadius()<SCREEN_SIZE.height-corrfactor)&&(ypos-a1.getRadius()>corrfactor))){
				System.out.println("xpos : "+xpos+" ypos : "+ypos);
				System.out.println("CASE 2 : ");
				return -3;
			}
			System.out.println("xpos : "+xpos+" ypos : "+ypos);
		}
		else{
			System.out.println("a.height : "+a.getPosition().getY()+" b.height : "+b.getPosition().getY());
			System.out.println("a.width : "+a.getPosition().getX()+" b.width : "+b.getPosition().getX());
			double ycomp=a.getPosition().getY()+a.getVelocity().getY()*movetime-b.getPosition().getY()-b.getVelocity().getY()*movetime;
			double xcomp=a.getPosition().getX()+a.getVelocity().getX()*movetime-b.getPosition().getX()-b.getVelocity().getX()*movetime;
			if(Math.abs(Math.sqrt(xcomp*xcomp+ycomp*ycomp)-(a.getRadius()+b.getRadius()))>corrfactor)
				return -4;
		}
		return 1;
	}
	private void updateCoordinates(double time){
		int i=0;
		while(i<nball){
			if(balls[i]!=null)
				balls[i].setPosition((double)((double)balls[i].getPosition().getX()+time*balls[i].getVelocity().getX()),(double)( (double)balls[i].getPosition().getY()+time*balls[i].getVelocity().getY()));
			i++;
		}
	}

	public boolean simulate(Event e){
		if(e==null)
			return false;
		this.updateCoordinates(e.getComparingValue()-currtime);
		Ball a=e.getFirstBall(),b=e.getSecondBall();
		if(e.isCollidingWithWall()){
			if(e.getDirectionofWall()==Event.HORIZONTAL){
				a.setVelocity(a.getVelocity().getX(), a.getVelocity().getY()*(-1));
			}
			else if(e.getDirectionofWall()==Event.VERTICAL){
				a.setVelocity(a.getVelocity().getX()*(-1), a.getVelocity().getY());
			}
			else{
				System.err.println("WHAT AN ABSURD DIRECTION OF WALL IS THIS");
				System.exit(0);
			}
			currtime=e.getComparingValue();
			System.out.println("currtime with wall collide : "+currtime);
			return true;
		}
		if(e.getSecondBall()!=null&&e.getSecondBall().getIndex()>=nball){
			System.out.println("Second ball is a hole");
		}
		double xrel=a.getPosition().getX()-b.getPosition().getX();
		double yrel=a.getPosition().getY()-b.getPosition().getY();
		double dist=Math.sqrt(xrel*xrel+yrel*yrel);
		Components alongvector=new Components(xrel/dist,yrel/dist);
		Components apara=a.VelocityAlongUnitVector(alongvector);
		Components aperp=a.VelocityPerpendicularTOUnitVector(alongvector);
		Components bpara=b.VelocityAlongUnitVector(alongvector);
		Components bperp=b.VelocityPerpendicularTOUnitVector(alongvector);
		a.setVelocity(aperp.getX()+bpara.getX(), aperp.getY()+bpara.getY());
		b.setVelocity(bperp.getX()+apara.getX(), bperp.getY()+apara.getY());
		currtime=e.getComparingValue();
		System.out.println("currtime with ball collide : "+currtime);
		if(b.getIndex()>=nball){
			b.setVelocity(0,0);
			System.out.println("currtime with ball collide : "+currtime);
			System.out.println("NULLING BALL : "+a.getIndex());
			a.delete();
			return true;
		}
		return true;
	}
	public Event calculatenextEvent(Ball a,boolean bool){
		if(a==null||a.getIndex()>=nball||!a.exists())
			return null;
		Ball temp=null,c=null;
		Event newEvent=null;
		boolean firsttime=true;
		int i=0;
		double root,cos,vx,vy,ttime=0,remtime=0,dist,velo,rnot;
		double xcomp,ycomp;
		int remme=-1;
		System.out.println("H1");
		while(i<nball+6){
			temp=balls[i];
			if(!temp.exists()){
				i++;
				continue;
			}
			if(a.equals(temp)){
				remme=i;
				i++;
				continue;
			}
			ycomp=a.getPosition().getY()-temp.getPosition().getY();
			xcomp=a.getPosition().getX()-temp.getPosition().getX();
			vx=a.getVelocity().getX()-temp.getVelocity().getX();
			vy=a.getVelocity().getY()-temp.getVelocity().getY();
			if(!((vx==0 && vy==0 )|| ycomp*vy+xcomp*vx>0)){
				cos=(xcomp*vx+ycomp*vy)/Math.pow(((xcomp*xcomp+ycomp*ycomp)*(vx*vx+vy*vy)),.5);
				root=(a.getRadius()+temp.getRadius())*(a.getRadius()+temp.getRadius())-(xcomp*xcomp+ycomp*ycomp)*(1-cos*cos);
				if (root<=0){
					i++;
					continue;
				}
				dist=Math.sqrt(xcomp*xcomp+ycomp*ycomp);
				velo=Math.sqrt(vx*vx+vy*vy);
				ttime=Math.abs((dist*cos+Math.sqrt((a.getRadius()+temp.getRadius())*(a.getRadius()+temp.getRadius())-dist*dist*(1-cos*cos)))/velo);
				if(a.getPosition().getX()+a.getVelocity().getX()*ttime+a.getRadius()>SCREEN_SIZE.width+2||a.getPosition().getX()+a.getVelocity().getX()*ttime-a.getRadius()<-2||a.getPosition().getY()+a.getVelocity().getY()*ttime+a.getRadius()>SCREEN_SIZE.height+2||a.getPosition().getY()+a.getVelocity().getY()*ttime-a.getRadius()<-2){
					i++;
					continue;
				}
				if(bool&&remme==-1){
					i++;
					continue;
				}
				if(firsttime){
					remtime=ttime;
					firsttime=false;
				}
				if(remtime>=ttime){
					remtime=ttime;
					c=temp;
					System.out.println("ttime : "+ttime+" and ball xposition is : "+c.getPosition().getX()+" yposition is : "+c.getPosition().getY()+" c : "+c);
				}
			}
			i++;
		}
		System.out.println("H2 , c : "+c);
		if(c!=null){//colliding with a ball
			newEvent=new Event();
			newEvent.setBalls(a,c);
			newEvent.setComparingParameter(currtime+Math.abs(remtime));
			System.out.println("Colliding with ball and the time is : "+(currtime+remtime));
		}
		else{//colliding with a wall
			newEvent=this.getWallEvent(a);
		}
		System.out.println("H3");
		return newEvent;
	}

	private Event getWallEvent(Ball a){
		Event newEvent=new Event();
		newEvent.setBalls(a, null);
		double dx=(a.getVelocity().getX()>0)?(SCREEN_SIZE.width-a.getPosition().getX()-a.getRadius()):(a.getPosition().getX()-a.getRadius());
		double dy=(a.getVelocity().getY()>0)?(SCREEN_SIZE.height-a.getPosition().getY()-a.getRadius()):(a.getPosition().getY()-a.getRadius());
		double colltime=0;
		System.out.println(Math.abs(dx*a.getVelocity().getY())>Math.abs(dy*a.getVelocity().getX()));
		if(Math.abs(dx*a.getVelocity().getY())>Math.abs(dy*a.getVelocity().getX())){
			colltime=Math.abs(dy/a.getVelocity().getY());
			System.err.println("xcord : "+a.getPosition().getX()+" ycord : "+a.getPosition().getY()+"dy="+dy+" colltime="+colltime+" currtime : "+currtime+" colltime+currtime="+(currtime+colltime)+" old a.vy : "+a.getVelocity().getY()+" new y : "+(a.getVelocity().getY()*colltime+a.getPosition().getY())+" new x : "+(a.getVelocity().getX()*colltime+a.getPosition().getX()));
			newEvent.setCollidingWithWall(true, Event.HORIZONTAL);
		}
		else{
			colltime=Math.abs(dx/a.getVelocity().getX());
			//             return null;
			System.err.println("dx="+dx+" colltime="+colltime+" currtime : "+currtime+" colltime+currtime="+(currtime+colltime)+" a.vx : "+a.getVelocity().getX()+" new x : "+(a.getVelocity().getX()*colltime+a.getPosition().getX())+" new y : "+(a.getVelocity().getY()*colltime+a.getPosition().getY()));
			newEvent.setCollidingWithWall(true, Event.VERTICAL);
		}
		System.out.println("Colliding with wall and the time is : "+(currtime+colltime));
		newEvent.setComparingParameter(currtime+colltime);
		return newEvent;
	}

	private boolean areEventsEqual(Event currevent, Event preEvent) {
		//throw new UnsupportedOperationException("Not yet implemented");

		if(preEvent==null)
			return false;
		if(currevent.isCollidingWithWall()==preEvent.isCollidingWithWall()){
			if(currevent.getComparingValue()==preEvent.getComparingValue()){
				if(!currevent.isCollidingWithWall()){
					if((currevent.getFirstBall().getIndex()==preEvent.getFirstBall().getIndex()&&currevent.getSecondBall().getIndex()==preEvent.getSecondBall().getIndex())||(currevent.getFirstBall().getIndex()==preEvent.getSecondBall().getIndex()&&currevent.getSecondBall().getIndex()==preEvent.getFirstBall().getIndex())){
						return true;
					}
				}
				else{
					if(currevent.getFirstBall().getIndex()==preEvent.getFirstBall().getIndex())
						return true;
				}
			}
		}
		return false;
	}
}
