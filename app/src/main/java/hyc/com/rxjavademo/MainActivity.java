package hyc.com.rxjavademo;



import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.AndroidException;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import hyc.com.rxjavademo.request.ApiHelper;
import hyc.com.rxjavademo.request.ApiService;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private TextView mText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) findViewById(R.id.test);

        /*
        * retrofit
        * 
        * */
//        Call<String> data = ApiHelper.getApiService().getData(4, 30);
//        data.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Log.e(TAG, Thread.currentThread().getName() );
//                mText.setText(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//
//            }
//        });


       /*retrofit+Rxjava*/
//        Observable<String> observable = ApiHelper.getApiService().getData(4, 30);
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onNext(String value) {
//                        mText.setText(value);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
////                        Toast.makeText(MainActivity.this, "加载完毕", Toast.LENGTH_SHORT).show();
//                    }
//                });


        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String string = getJsonString("https://www.imooc.com/api/teacher?type=4&num=30");
                emitter.onNext(string);
                emitter.onComplete();
            }
        });
        //创建一个下游 Observer
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "subscribe");
            }

            @Override
            public void onNext(String value) {
                mText.setText(value);
                Log.e(TAG, "" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "error");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "complete");
            }
        };
        //建立连接
        observable.subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(observer);

    }



    public String getJsonString(String urlString) {

        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            InputStream inputStream = url.openConnection().getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
//增加一行注释
}
