package com.example.aromind.Face;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.aromind.facebook.afslkdfj;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MyJobService extends JobService {

    private static Context mContext;
    private String imageUrl="no";

    //서버애서 사진 가져오기
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.i(TAG, "onStartJob: " + params);
        final Uri[] uri = params.getTriggeredContentUris();

        new  AsyncTask< Void ,  Void ,  Void >()  {
            @Override
            protected  Void  doInBackground ( Void ...  parameters )  {
                Log.i("jobService", String.valueOf(uri[0]));  // 실제 작업을 수행
                File file = null;
                //if(String.valueOf(uri[0]).equals("content://media/external"){
                if(uri.length <= 2){
                    if(!String.valueOf(uri[uri.length-1]).equals("content://media/external")) {
                        Log.i("jobService", String.valueOf(uri[uri.length - 1]));
                        uploadImage(uri[uri.length - 1]);
                        file = new File(String.valueOf(getRealPathFromURI(uri[uri.length - 1])));
                    }
                }else {
                    Log.i("jobService", String.valueOf(uri[0]));
                    if(!String.valueOf(uri[0]).equals("content://media/external")){
                        uploadImage(uri[0]);
                        file = new File(String.valueOf(getRealPathFromURI(uri[0])));
                    }else{
                        return null;
                    }
                }
                if(!String.valueOf(uri[uri.length-1]).equals("content://media/external")) {
                    imageUrl = "{\"url\":\"http://ec2-54-180-103-228.ap-northeast-2.compute.amazonaws.com:8000/storage/albumImage/" + file.getName() + "\"}";
                }
//                imageUrl = "{\"url\":\"http://ec2-54-180-103-228.ap-northeast-2.compute.amazonaws.com/storage/albumImage/"+file.getName()+"\"}";
                return  null;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected  void  onPostExecute ( Void  result )  {
                //이 작업을 종료시키는
                jobFinished ( params ,  false );

                // 다시 모니터링 작업을 만들
                startJob ( getApplicationContext());
            }
        }.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob: " + params);
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public  static  void  startJob (Context  context)  {
        mContext = context;
        Log . i ( TAG ,  "startJob" );

        // 스케줄러의 취득
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE );
        // 빌더 작성
        JobInfo.Builder builder = new JobInfo.Builder(
                998 , // 이번에는 한가지 작업이 실행되고 있으면 좋기 때문에 고정 ID를 사용
                new  ComponentName(context, MyJobService.class ));
        // Content URI 모니터링
        builder.addTriggerContentUri (
                new  JobInfo.TriggerContentUri(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI ,  // 외부 미디어 스토리지 모니터링
                        JobInfo.TriggerContentUri.FLAG_NOTIFY_FOR_DESCENDANTS ));
        // 작업 투입
        scheduler.schedule(builder.build());
    }




    // image server 업로드
    public void uploadImage(Uri uri){

        UploadService service = MyRetrofit2.getRetrofit2().create(UploadService.class);
        File file = new File(getRealPathFromURI(uri));
        MultipartBody.Part body1 = prepareFilePart("image", uri);
        RequestBody description = createPartFromString("hello, this is description speaking");
        Call<ResponseBody> call = service.uploadFile(description, body1);
        Log.i("Select",call.request().url().toString()); //todo 디버깅용
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("Test","성공");
//                Intent sendIntent = new Intent("com.dwfox.myapplication.SEND_BROAD_CAST");
//                sendIntent.putExtra("imageUrl", imageUrl);
//                sendBroadcast(sendIntent);
                Face f = new Face(imageUrl);
                f.connectAF(mContext);
                afslkdfj afslkdfj= new afslkdfj(mContext);
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Test","실패");
                Intent sendIntent = new Intent("com.dwfox.myapplication.SEND_BROAD_CAST");
                sendIntent.putExtra("imageUrl", imageUrl);
                sendBroadcast(sendIntent);
            }
        });
    }
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        File file = new File(getRealPathFromURI(fileUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    private String getRealPathFromURI(Uri contentURI) {
        if(String.valueOf(Uri.parse(contentURI.toString())).equals("content://media/external")){
            return null;
        }
        Cursor c = getContentResolver().query(Uri.parse(contentURI.toString()), null,null,null,null);
        c.moveToNext();
        String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
        Log.i("Select",absolutePath);
        return absolutePath;
    }


}
