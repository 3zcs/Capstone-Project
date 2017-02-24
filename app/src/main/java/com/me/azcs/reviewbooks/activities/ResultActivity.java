package com.me.azcs.reviewbooks.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.me.azcs.reviewbooks.R;
import com.me.azcs.reviewbooks.adapter.BooksAdapter;
import com.me.azcs.reviewbooks.models.Item;
import com.me.azcs.reviewbooks.models.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.me.azcs.reviewbooks.Constant.DATA;

public class ResultActivity extends AppCompatActivity {
    @BindView(R.id.my_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.book_recycler_view)
    RecyclerView mBookRecyclerView;

    RecyclerView.LayoutManager mLayoutManager ;
    private RecyclerView.Adapter mAdapter;
    List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            itemList = intent.getParcelableArrayListExtra(DATA);
        }else {
            itemList = savedInstanceState.getParcelableArrayList(DATA);
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(String.valueOf(itemList.size()) + getText(R.string.result));
        mToolbar.setTitle(String.valueOf(itemList.size()) + getText(R.string.result));
        mToolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.colorTextIcon));

        mLayoutManager = new LinearLayoutManager(this);
        mBookRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new BooksAdapter(itemList,getApplicationContext());
        mBookRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(DATA, (ArrayList<? extends Parcelable>) itemList);
        super.onSaveInstanceState(outState);
    }
}
