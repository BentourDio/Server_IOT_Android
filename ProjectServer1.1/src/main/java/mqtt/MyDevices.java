package mqtt;

import devices.Android;
import devices.IOT;

import java.util.ArrayList;

public class MyDevices {
    public ArrayList<IOT> listIOT;
    public int counterIOT;
    //public IOT IOT1;
    //public IOT IOT2;
    public Android ANDR;
    public String ip;

    public MyDevices(String i,int n){

        ip = i;
//        IOT1 = new IOT("IOT1",0,0,0,0);
//        IOT2 = new IOT("IOT2",0,0,0,0);
        listIOT = new ArrayList<>();

        for (int j =0; j < n; j ++){
            listIOT.add(new IOT("IOT"+(j+1),0,0,0,0));
        }
        counterIOT = listIOT.size();

        ANDR = new Android("ANDR",0,0,0);
    }

    public int getIndexTopic(String topic, MyDevices d){
//        System.out.println("entering getIndex");
        int i=0;
        while (!(d.listIOT.get(i).topic).equals(topic)) i++;

        return i;
    }


    public  void output(MyDevices d){
        //System.out.println(d.listIOT.get(0).latest_data_smoke);
        //System.out.println(d.listIOT.get(1).latest_data_smoke);
        System.out.println("OUTPUT BITCH");
        for (int i=0; i < d.listIOT.size(); i++){
            IOT t = d.listIOT.get(i);
            System.out.println("*********************\nThis is device:"+t.topic+" with sensors:");
            System.out.println("gas sensor: "+ t.gas_sensor+" smoke:"+t.smoke_sensor+" UV sensor:"+t.UV_sensor+" tempe sensor:" + t.temp_sensor);
            System.out.println("And latest data:");
            System.out.println("gas:" +t.latest_data_gas+" smoke:"+t.latest_data_smoke+" UV:"+t.latest_data_UV+
                    " temp:"+ t.latest_data_temp+" x:"+t.data_x_coord+" y:"+t.data_y_coord+ "battery:"+t.battery);
        }
    }

}

