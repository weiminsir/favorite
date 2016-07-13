package coder.wick.net;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Weimin on 4/7/2016.
 */
public interface APIClient {
    /**
     * BaseURL
     * APIInstance
     */
    //登陆
    @FormUrlEncoded
    @POST("http://114.55.65.20/littlebits/api/login.php")
    Observable<String> login(@Field("username") String username,
                             @Field("password") String password);


}
