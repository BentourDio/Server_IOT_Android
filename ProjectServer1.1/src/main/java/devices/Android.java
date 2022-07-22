package devices;

public class Android{
    public String Topic;
    //TBD rest data
    public float battery;
    public double x_coord;
    public double y_coord;


    public Android(String s, double x, double y, float b){
        Topic = s;
        x_coord = x;
        y_coord = y;
        battery = b;
    }
}
