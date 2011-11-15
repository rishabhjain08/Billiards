public class Begin{

    public static void main(String args[]){
        Table table = new Table();
        draw draw = new draw();
        table.setParam(draw);
        table.start();
    }
}
