public class draw extends Thread{

    Ball[] ball;
    double time;
    BilliardsTable drawTable;
    Table table;
    public void initiatedrawtable(int i){
        drawTable=new BilliardsTable();
        drawTable.setBalls(i);
        drawTable.setVisible(true);
      }
    public void setParam(Ball[] balls,double t,Table tab){
        ball=balls;
        time=t;
        table=tab;
//        System.out.println("HERE 1 : "+table.getName());
        this.run();
        table.suspend();
     }
    @Override
    public void run(){
  //      System.out.println("HERE 2 : "+this.getName());
        drawTable.simulateBalls(ball,time,table);
    }
}

