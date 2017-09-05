package com.hackhaton.androidbase.client;


import com.hackhaton.androidbase.client.entity.request.TokenRequest;
import com.hackhaton.androidbase.client.entity.response.TokenResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author : hafiq on 23/01/2017.
 */

public interface RestApi {

    @POST("token")
    Observable<TokenResponse> getToken(@Body TokenRequest request);


}
