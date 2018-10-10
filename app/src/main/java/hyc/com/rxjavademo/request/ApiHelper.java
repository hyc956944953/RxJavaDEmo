package hyc.com.rxjavademo.request;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2018/9/13 0013.
 */

public class ApiHelper {
    private static ApiService apiService;
    private static Retrofit retrofit;
    private static OkHttpClient client;
    public static ApiService getApiService(){
        if (apiService == null) {
            apiService=getRetrofit().create(ApiService.class);
        }
        return apiService;
    }


    public static Retrofit getRetrofit(){
        client=new OkHttpClient();
        if (retrofit == null) {
             retrofit=new Retrofit.Builder()
                     .baseUrl("https://www.imooc.com/")
                     .client(client)
                     .addConverterFactory(ScalarsConverterFactory.create())
                     .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                     .build();
        }
        return retrofit;
    }
}
