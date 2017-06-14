package nitimcorp.com.golfstrokesscoreboard.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import nitimcorp.com.golfstrokesscoreboard.R;
import nitimcorp.com.golfstrokesscoreboard.adapters.HoleAdapter;
import nitimcorp.com.golfstrokesscoreboard.scorecard.Hole;
import nitimcorp.com.golfstrokesscoreboard.scorecard.ScoreCard;

/**
 * @file        MainActivity.java
 * @copyright   (C) 2017 NitimCorp, Inc
 * @brief
 */

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private static final String PREFS_FILE = "nitimcorp.com.scoreboard.ui.preferences";
    private static final String KEY_STROKECOUNT = "key_strokecount";

    private Hole[] mHoles = new Hole[18];

    HoleAdapter mHoleAdapter;
    ScoreCard mScoreCard;

    // This is to save the data even if the app is put in the background.
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        ButterKnife.bind(this);

        mScoreCard = scorecardData();

        updateRecyclerListView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (int i = 0; i < mHoles.length; i++) {
            mEditor.putInt(KEY_STROKECOUNT + i, mHoles[i].getStrokesTaken());
        }
        mEditor.apply();
    }

    private ScoreCard scorecardData() {
        ScoreCard scoreCard = new ScoreCard();

        scoreCard.setHoles(getScoreCardData());

        return scoreCard;
    }

    public Hole[] getScoreCardData() {
        int strokes;
        for (int i = 0; i < mHoles.length; i++) {
            Hole hole = new Hole();
            strokes = mSharedPreferences.getInt(KEY_STROKECOUNT + i, 0);
            hole.setHoleNumber("Hole " + (i + 1) + " :");
            hole.setStrokesTaken(strokes);

            mHoles[i] = hole;
        }
        return mHoles;
    }

    private void updateRecyclerListView() {
        Parcelable[] parcelables = mHoles;
        mHoles = Arrays.copyOf(parcelables, parcelables.length, Hole[].class);

        mHoleAdapter = new HoleAdapter(this, mHoles);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mHoleAdapter);

        mRecyclerView.setHasFixedSize(true); // This is for performance sake.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clearSrokes:
                // Remove saved value and apply to save changes.
                mEditor.clear();
                mEditor.apply();
                for (Hole mHole : mHoles) {
                    mHole.setStrokesTaken(0);
                }
                break;
        }
        mHoleAdapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }
}


