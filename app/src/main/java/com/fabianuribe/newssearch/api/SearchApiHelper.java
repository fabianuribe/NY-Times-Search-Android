package com.fabianuribe.newssearch.api;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by uribe on 1/31/17.
 */

public class SearchApiHelper {
    public static final String API_BASE_URL = "https://api.nytimes.com";
    public static final String API_KEY = "541a4bc97d8a43f5b11872815cf824b7";

    private static SearchApiHelper INSTANCE;

    private Retrofit retrofit;
    private SearchService searchService;

    public static synchronized SearchApiHelper getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new SearchApiHelper();
        }
        return INSTANCE;
    }

    private SearchApiHelper() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url()
                        .newBuilder()
                        .addQueryParameter("api-key", API_KEY)
                        .build();

                request = request.newBuilder().url(url).build();

                Log.d("OKHttp =====> ", request.toString());
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        searchService = retrofit.create(SearchService.class);
    }

    public SearchService getSearchService() {
        return searchService;
    }
}
