package com.example.diffuser.Model;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class RecodingBR extends BroadcastReceiver {

        private static MediaRecorder myAudioRecorder;

    @Override
        public void onReceive(Context context, Intent intent) {
            processPhoneState(intent, context);
        }

        private void processPhoneState(Intent intent, Context context){

            TelephonyManager tm = (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);
            switch (tm.getCallState()) {
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    //전화 받았을때 녹음시작
                    startRecording();
                    Toast.makeText(context, "녹음시작 " , Toast.LENGTH_SHORT).show();
                    break;

                case TelephonyManager.CALL_STATE_IDLE:
                    //전화를 끊었으면 녹음 종료
                    stopRecording();
                    Toast.makeText(context, "녹음끝" , Toast.LENGTH_SHORT).show();
                    break;
            }//end switch
    }//end processPhone

    //녹음
    public void startRecording() {

        String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+UUID.randomUUID().toString()+"_audio_record.3gp";
        //String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_audio_record.3mp3";
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        //myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        myAudioRecorder.setOutputFile(outputFile);
        myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            myAudioRecorder.prepare();
        }catch (IOException e) {
            Log.i("LOG_TAG", "IOException is cant");
        }//end try catch
        myAudioRecorder.start();
    }//end startRecording


    public void stopRecording(){
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder = null;
    }//end stopRecording

}
