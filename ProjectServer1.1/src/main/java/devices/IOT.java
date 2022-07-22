package devices;

public class IOT {
//    public String name;
    public String topic;//topic acts also as id for device
    public int gas_sensor;//
    public int smoke_sensor;
    public int temp_sensor;
    public int UV_sensor;
    public double data_x_coord;
    public double data_y_coord;
    public float battery;



    public double latest_data_gas;
    public double latest_data_smoke;
    public double latest_data_temp;
    public double latest_data_UV;
    public int latest_danger;

    //Every IOT has the number of it's sensors as a counter. If an IOT has more than
    //one type of sensor active and sends multiple measurements then the higher value is kept.


    //constructor
    public IOT(String t, int s, int g, int h, int U) {
        topic = t;
        smoke_sensor   = s;
        temp_sensor    = h;
        gas_sensor     = g;
        UV_sensor      = U;
        //some default values to help with testing
        data_x_coord=37.96809452684323;
        data_y_coord=23.76630586399502;
        latest_data_gas=0;
        latest_data_smoke=0;
        latest_data_temp =0;
        latest_data_UV=0;
        latest_danger = 0;
        battery=0;
    }

    @Override
    public String toString(){
        return String.format("|%f gas| %f smoke |%f UV |%f temp |%f battery | %f X_COORD| %f Y-COORD",
                latest_data_gas, latest_data_gas, latest_data_UV, latest_data_temp, battery, data_x_coord, data_y_coord);
    }
}
