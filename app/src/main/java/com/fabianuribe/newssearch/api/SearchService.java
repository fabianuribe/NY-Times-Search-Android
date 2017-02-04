package com.fabianuribe.newssearch.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by uribe on 1/31/17.
 */

public interface SearchService {
    @GET("/svc/search/v2/articlesearch.json")
    public Call<SearchResponse> getSearchResults(@Query("q") String query, @Query("page") Integer page);
}
