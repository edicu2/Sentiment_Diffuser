package com.example.aromind.Model;

public class CheckThread implements Runnable{
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                Mqtt.checkMqtt();
        }//end while
    }//end run
}
