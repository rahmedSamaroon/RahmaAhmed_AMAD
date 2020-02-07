package edu.cmu.tracks;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.cmu.tracks.loder.IData;
import edu.cmu.tracks.loder.JSONData;
import edu.cmu.tracks.model.Track;


public class ItemListActivity extends AppCompatActivity implements IData {
    private List<Track> trackList = JSONData.trackList;
    private ProgressDialog progressDialog;
    private boolean mTwoPane;
    private MediaPlayer mediaPlayer;
    private TracksRecyclerViewAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);


        progressDialog = new ProgressDialog(this);

        if(trackList.isEmpty()){
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            JSONData.getJSON(this);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        setupView();
    }

    @Override
    public void ready() {
        progressDialog.dismiss();
        adapter.notifyDataSetChanged();
    }

    private void setupView() {
        RecyclerView recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        adapter = new TracksRecyclerViewAdapter(this, trackList, mTwoPane);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}


