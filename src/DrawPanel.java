
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class DrawPanel extends javax.swing.JPanel {
int nball=5;
int i=0;
double time=0;
static double mu=.8;
static double gravity=9.87;
static Dimension full=Toolkit.getDefaultToolkit().getScreenSize();
    /** Creates new form DrawPanel */
Ball from[];
Table tab;
    public DrawPanel(){
        initComponents();
    }
     public void setNoOfBalls(int i){
        this.nball=i;
        this.from=new Ball[i];
     }
      boolean firsttime=true;
    public void simulateTo(Ball[] from1,double t,Table tab1){
           tab=tab1;
           this.from=from1;
           this.time=t;
           this.repaint(0,0,full.width,full.height);
      }
      @SuppressWarnings("unchecked")
 BufferedImage img = null;
       @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(51, 204, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 396, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
double t1=0;
    @Override
    public void paintComponent(Graphics g){
     int n=0;
     if(tab==null)
         return;
     if(t1>time||from[0]==null){
         t1=0;
         tab.resume();
         return;
    }
     super.paintComponent(g);
     n=nball+6-1;
     g.setColor(Color.black);
     while(n>=0){
             g.setColor(from[n].getColor());
         if(from[n].exists())
             g.fillOval((int)(this.from[n].getPosition().getX()-from[n].getRadius()+this.from[n].getVelocity().getX()*t1), (int)(this.from[n].getPosition().getY()-from[n].getRadius()+this.from[n].getVelocity().getY()*t1), (int)from[n].getRadius()*2, (int)from[n].getRadius()*2);
         n--;
         }
     t1+=1;
     this.repaint(0, 0, full.width, full.height);
          }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
