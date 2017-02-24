package com.me.azcs.reviewbooks.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.me.azcs.reviewbooks.BookReviewApp;
import com.me.azcs.reviewbooks.R;
import com.me.azcs.reviewbooks.adapter.BooksAdapter;
import com.me.azcs.reviewbooks.models.ImageLinks;
import com.me.azcs.reviewbooks.models.Item;
import com.me.azcs.reviewbooks.models.Response;
import com.me.azcs.reviewbooks.models.VolumeInfo;
import com.me.azcs.reviewbooks.services.ApiService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.schedulers.Schedulers;

import static com.me.azcs.reviewbooks.Constant.BOOK_AUTHOR;
import static com.me.azcs.reviewbooks.Constant.BOOK_COVER;
import static com.me.azcs.reviewbooks.Constant.BOOK_ID;
import static com.me.azcs.reviewbooks.Constant.BOOK_NAME;
import static com.me.azcs.reviewbooks.Constant.CONTENT_URI;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.my_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.no_data)
    TextView mNoData;

    RecyclerView.LayoutManager mLayoutManager;
    BooksAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.favorite));
        mToolbar.setTitle(getString(R.string.favorite));
        mToolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.colorTextIcon));

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        new RetrieveDataTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search :
                startActivity(new Intent(this,SearchActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void showProgress(boolean isShowin){
        mProgressBar.setVisibility(isShowin?View.VISIBLE:View.GONE);
        mRecyclerView.setVisibility(isShowin?View.GONE:View.VISIBLE);
    }

    private class RetrieveDataTask extends AsyncTask<Void,Void,List<Item>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }

        @Override
        protected List<Item> doInBackground(Void... params) {
            Uri Books = CONTENT_URI ;
            Cursor cursor = getContentResolver().query(Books,null,null,null,null);
            List<Item> items = new ArrayList();
            while(cursor.moveToNext()){
                items.add(addNewItem(
                        cursor.getString(cursor.getColumnIndex(BOOK_ID)),
                        cursor.getString(cursor.getColumnIndex(BOOK_NAME)),
                        cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR)),
                        cursor.getString(cursor.getColumnIndex(BOOK_COVER))
                ));
            }

            return items;
        }

        @Override
        protected void onPostExecute(List<Item> itemList) {
            super.onPostExecute(itemList);
            showProgress(false);
            if (itemList.isEmpty())
                showNoData();
            else {
                mAdapter = new BooksAdapter(itemList, getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }

    private void showNoData() {
        mRecyclerView.setVisibility(View.GONE);
        mNoData.setVisibility(View.VISIBLE);
    }

    private Item addNewItem(String id,String name,String author,String cover) {
        return new Item(id,
                new VolumeInfo(name, author,
                        new ImageLinks(cover)));
    }
}
