package com.fabianuribe.newssearch.api;

import com.fabianuribe.newssearch.models.Doc;
import com.fabianuribe.newssearch.models.Response;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by uribe on 1/31/17.
 */

public class SearchResponse {

    @SerializedName("response")
    Response response;

    public Response getResponse() {
        return response;
    }

    public List<Doc> getResults() {
        List<Doc> docs = null;
        if (response != null) {
            docs = response.getDocs();
        }
        return docs;
    }

    /*
    public static SearchResponse parseJSON(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();

        Gson gson = gsonBuilder.create();
        SearchResponse searchResponse = gson.fromJson(response, SearchResponse.class);
        return searchResponse;
    }
    */
}
