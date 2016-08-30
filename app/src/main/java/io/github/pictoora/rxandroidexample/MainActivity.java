package io.github.pictoora.rxandroidexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.subjects.PublishSubject;

/**
 * A very very Simple Example
 *  - Rx
 * Created by reza on 25.08.16.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private int mCounter = 0;
    private PublishSubject<Integer> mPublishSubject;

    @BindString(R.string.txt_about_me) String mAboutMe;
    @BindString(R.string.txt_ok) String mOK;
    @BindString(R.string.txt_github_my_page_url) String myGitHubPage;
    @BindView(R.id.txt_counter) TextView mTvCounter;
    @BindView(R.id.btn_decrement) Button mBtnDec;
    @BindView(R.id.btn_increment) Button mBtnInc;
    @BindView(R.id.fab) FloatingActionButton mFab;
    @BindString(R.string.txt_about_alert) String mMessage;
    @BindString(R.string.txt_twitter_my_page_url) String myTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        createPublishSubject();

    }

    @OnClick(R.id.btn_decrement)
    public void setmBtnDec() {
        mCounter--;
        mPublishSubject.onNext(mCounter);
    }

    @OnClick(R.id.btn_increment)
    public void setmBtnInc() {
        mCounter++;
        mPublishSubject.onNext(mCounter);
    }

    @OnClick(R.id.fab)
    public void setOnFabClick(View view){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, myGitHubPage);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }


    public void createPublishSubject() {
        mPublishSubject = PublishSubject.create();
        mPublishSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError " + e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext");
                mTvCounter.setText(String.valueOf(integer));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about_me:
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
                builder.setTitle(mAboutMe);
                builder.setMessage(mMessage);
                builder.setPositiveButton(mOK, null);
                builder.show();
                return true;
            case R.id.action_my_github_page:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(myGitHubPage));
                startActivity(intent);
                return true;
            case R.id.action_my_twitter_page:
                Intent intentTwitter = new Intent(Intent.ACTION_VIEW, Uri.parse(myTwitter));
                startActivity(intentTwitter);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
