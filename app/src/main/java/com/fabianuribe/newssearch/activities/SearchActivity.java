package com.fabianuribe.newssearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fabianuribe.newssearch.EndlessRecyclerViewScrollListener;
import com.fabianuribe.newssearch.ItemClickSupport;
import com.fabianuribe.newssearch.R;
import com.fabianuribe.newssearch.adapters.DocumentsAdapter;
import com.fabianuribe.newssearch.api.SearchApiHelper;
import com.fabianuribe.newssearch.api.SearchResponse;
import com.fabianuribe.newssearch.models.Doc;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.rvDocuments)
    RecyclerView rvDocuments;
    ArrayList<Doc> documents;
    DocumentsAdapter adapter;
    StaggeredGridLayoutManager gridLayoutManager;
    String currentQuery;

    private Call<SearchResponse> searchRequest;
    private EndlessRecyclerViewScrollListener scrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViews();
    }

    public void setupViews() {
        ButterKnife.bind(this);
        documents = new ArrayList<>();
        adapter = new DocumentsAdapter(this, documents);
        rvDocuments.setAdapter(adapter);
        gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rvDocuments.setLayoutManager(gridLayoutManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupEventListeners();
    }

    public void setupEventListeners() {
        ItemClickSupport.addTo(rvDocuments).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent i = new Intent(getApplicationContext(), DocumentActivity.class);
                Doc document = documents.get(position);
                i.putExtra("document", document);
                startActivity(i);
            }
        });

        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                makeSearchRequest(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvDocuments.addOnScrollListener(scrollListener);
    }

    public void makeSearchRequest(Integer page) {
        if (currentQuery == null || page == null) {
            return;
        }

        searchRequest = SearchApiHelper.getInstance().getSearchService().getSearchResults(currentQuery, page);

        searchRequest.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.body() == null ||
                        response.body().getResults() == null ||
                        response.body().getResults().size() == 0) {
                    Log.d("The response was empty", response.toString());
                    return;
                }
                int curSize = adapter.getItemCount();
                List<Doc> results = response.body().getResults();

                documents.addAll(results);
                adapter.notifyItemRangeInserted(curSize, results.size());
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SearchActivity.this,
                                "There was a problem loading the Results.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && query != currentQuery) {
                    if (documents.size() > 0) {
                        int curSize = adapter.getItemCount();
                        documents.clear();
                        adapter.notifyItemRangeRemoved(0, curSize);
                    }
                    currentQuery = query;
                    makeSearchRequest(0);
                }
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
