package com.dtmweb.etrendapp.apis;

import android.graphics.Bitmap;

import com.dtmweb.etrendapp.models.SellerObject;
import com.dtmweb.etrendapp.models.StoreObject;
import com.dtmweb.etrendapp.utils.GlobalUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by mdmunirhossain on 6/4/18.
 */

public interface ApiService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("/api/v1/auth/registration/")
    Call<SellerObject> registerUser(@Body Map<String, String> body, @Part("image") File photo);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("/api/v1/seller/")
    Call<StoreObject> registerStore(@Body Map<String, String> body, @Header("Authorization") String token);
}
