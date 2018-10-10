package hyc.com.rxjavademo.request;

import android.provider.ContactsContract;

import hyc.com.rxjavademo.DataBean;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/9/13 0013.
 */

public interface ApiService {

@GET("api/teacher")
    Observable<String> getData(@Query("type") int type , @Query("num") int num);
}
