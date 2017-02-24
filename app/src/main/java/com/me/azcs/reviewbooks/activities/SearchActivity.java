package com.me.azcs.reviewbooks.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.me.azcs.reviewbooks.BookReviewApp;
import com.me.azcs.reviewbooks.R;
import com.me.azcs.reviewbooks.models.Item;
import com.me.azcs.reviewbooks.models.Response;
import com.me.azcs.reviewbooks.services.ApiService;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import rx.Observable;
import rx.schedulers.Schedulers;

import static com.me.azcs.reviewbooks.Constant.DATA;

public class SearchActivity extends AppCompatActivity {
    static final String TAG = "SearchActivity";
    @BindView(R.id.my_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.bookName)
    EditText mBookName;

    @BindView(R.id.authorName)
    EditText mAuthorName;

    @BindView(R.id.search_button)
    Button mSearchButton;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.adView)
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.search));
        mToolbar.setTitle(getString(R.string.search));
        mToolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.colorTextIcon));

    }

    @OnClick(R.id.search_button)
    public void searchButtonHandler(){
        ApiService api = BookReviewApp.getClient().create(ApiService.class);
        Call<Response> responseCall = api.getBookDetaielsr(mBookName.getText().toString());
        showProgress(true);
        responseCall.enqueue(new Callback<Response>() {

            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.i(TAG, "onNext");
                        showProgress(false);
                        List<Item> items = response.body().getItems();
                        startActivity(new Intent(getApplicationContext(),ResultActivity.class)
                                .putParcelableArrayListExtra(DATA, (ArrayList<? extends Parcelable>) items));

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                        Log.i(TAG, "onError");
                        showProgress(false);
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });



//        Observable<Response> observable = api.getBookDetaiels(mBookName.getText().toString());
//        observable
//                .subscribeOn(Schedulers.io())
//
//                .subscribe(new rx.Subscriber<Response>() {
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                        showProgress(true);
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        Log.i(TAG, "onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i(TAG, "onError");
////                        showProgress(false);
//                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNext(Response data) {
//                        Log.i(TAG, "onNext");
////                        showProgress(false);
//                        List<Item> items = data.getItems();
//                        startActivity(new Intent(getApplicationContext(),ResultActivity.class)
//                                .putParcelableArrayListExtra(DATA, (ArrayList<? extends Parcelable>) items));
//
//                    }
//                });

    }

    public void showProgress(boolean isShown){
            mProgressBar.setVisibility(isShown?View.VISIBLE:View.GONE);
            mSearchButton.setVisibility(isShown?View.GONE:View.VISIBLE);
    }
}
