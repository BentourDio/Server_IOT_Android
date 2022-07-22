package mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

 //singleton

public class MyCallback implements MqttCallback {

    public void connectionLost(Throwable arg0){

    }

    public void deliveryComplete(IMqttDeliveryToken arg0){

    }

    public void messageArrived(String topic, MqttMessage message) throws Exception{
        //System.out.println(topic+" " +message);
        StringData sData = new StringData();

        String m = message.toString();

        /*
        Algorithm:
        A message arrives and the 1st thing to do is to determine if it is from an IOT or
        Android Device.
         */
        String tpc = topic.substring(0,3);
//        System.out.println("tpc is "+tpc);

        if (tpc.equals("IOT")){
            System.out.println("Message arrived| IOT");
            System.out.println(message);
            sData.getValuesIoT(m,topic);

        }


        if (tpc.equals("AND")){
            System.out.println("messageArrived| Android");
            System.out.println("message");
            sData.getValuesAndroid(m);
        }



    }

}
