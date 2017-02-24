package com.me.azcs.reviewbooks.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.me.azcs.reviewbooks.BookReviewApp;
import com.me.azcs.reviewbooks.R;
import com.me.azcs.reviewbooks.content_provider.BooksProvider;
import com.me.azcs.reviewbooks.models.Item;
import com.me.azcs.reviewbooks.services.ApiService;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static com.me.azcs.reviewbooks.Constant.BOOK_AUTHOR;
import static com.me.azcs.reviewbooks.Constant.BOOK_COVER;
import static com.me.azcs.reviewbooks.Constant.BOOK_ID;
import static com.me.azcs.reviewbooks.Constant.BOOK_NAME;
import static com.me.azcs.reviewbooks.Constant.CONTENT_URI;
import static com.me.azcs.reviewbooks.Constant.CONTENT_URI_ITEM;
import static com.me.azcs.reviewbooks.Constant.DATA;

public class ResultBooksActivity extends AppCompatActivity {
    @BindView(R.id.my_toolbar)
    Toolbar mToolbar;
    
    @BindView(R.id.book_cover)
    ImageView mBookCover ;

    @BindView(R.id.bookName)
    TextView mBookName;

    @BindView(R.id.authorName)
    TextView mAuthorName;

    @BindView(R.id.rating)
    TextView mRating;

    @BindView(R.id.description)
    TextView mDescription;

    @BindView(R.id.favorite)
    Button mFavorite;

    @BindView(R.id.book_description)
    LinearLayout mBookLayout;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    Item item;
    BooksProvider mProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_books);
        ButterKnife.bind(this);
        Intent i = getIntent();
        item = i.getParcelableExtra(DATA);
        mProvider = new BooksProvider();
        
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(item.getVolumeInfo().getTitle());
        mToolbar.setTitle(item.getVolumeInfo().getTitle());
        mToolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.colorTextIcon));
        
        if (item.getKind() != null)
            setItem(item);
        else
            loadItem(item.getId());
    }

    private void loadItem(String id) {
        showProgress(true);
        ApiService api = BookReviewApp.getClient().create(ApiService.class);
        Call<Item> itemCall = api.getItemDetailsr(id);
        itemCall.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                setItem(item);
                showProgress(false);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Toast.makeText(getApplicationContext(),getText(R.string.no_response),Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        });

//        Observable observable = api.getItemDetails(id);
//        observable
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Subscriber<Item>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Item item) {
//                        setItem(item);
//                    }
//                });
    }

    @OnClick(R.id.favorite)
    public void onFavoriteClicked(){
        if (!mProvider.isItemExists(item.getId(),getApplicationContext())) {
            ContentValues contentValues = new ContentValues();

            if (!TextUtils.isEmpty(item.getVolumeInfo().getTitle()))
                contentValues.put(BOOK_NAME, item.getVolumeInfo().getTitle());

            if (item.getVolumeInfo().getAuthors() != null)
                if (!TextUtils.isEmpty(item.getVolumeInfo().getAuthors().get(0)))
                    contentValues.put(BOOK_AUTHOR, item.getVolumeInfo().getAuthors().get(0));

            if (item.getVolumeInfo().getImageLinks() != null)
                contentValues.put(BOOK_COVER, item.getVolumeInfo().getImageLinks().getThumbnail());

            contentValues.put(BOOK_ID, item.getId());

            Uri uri = getContentResolver().insert(CONTENT_URI, contentValues);
            Log.i(getLocalClassName(), uri.toString());
            mFavorite.setText(getText(R.string.unfavourite));
        }else {
            int delete = getContentResolver().delete(CONTENT_URI_ITEM,item.getId(),null);
            Log.i(getLocalClassName(), String.valueOf(delete));
            mFavorite.setText(getText(R.string.favorite));
        }
    }

    public void setItem(Item item){
        Picasso.with(this)
                .load(item.getVolumeInfo().getImageLinks().getThumbnail())
                .placeholder(R.drawable.defimage)
                .into(mBookCover);

        if (!TextUtils.isEmpty(item.getVolumeInfo().getTitle()))
            mBookName.setText(item.getVolumeInfo().getTitle());

        if(item.getVolumeInfo().getAuthors() != null)
            if (!TextUtils.isEmpty(item.getVolumeInfo().getAuthors().get(0)))
                mAuthorName.setText(item.getVolumeInfo().getAuthors().get(0));

        if (!String.valueOf(item.getVolumeInfo().getAverageRating()).equals(0.0))
            mRating.setText(String.valueOf(item.getVolumeInfo().getAverageRating()));
        else mRating.setVisibility(View.INVISIBLE);

        if (item.getVolumeInfo().getImageLinks() != null)
            mDescription.setText(item.getVolumeInfo().getDescription());

        if (mProvider.isItemExists(item.getId(),getApplicationContext()))
            mFavorite.setText(getText(R.string.unfavourite));
        else
            mFavorite.setText(getText(R.string.favorite));

    }

    public void showProgress(boolean isShown){
        mBookLayout.setVisibility(isShown?View.GONE:View.VISIBLE);
        mProgressBar.setVisibility(isShown?View.VISIBLE:View.GONE);

    }


}
