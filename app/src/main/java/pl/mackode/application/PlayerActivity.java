package pl.mackode.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.mackode.library.TinyPlayer;

public class PlayerActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.surface)
    SurfaceView surface;

    boolean paused;
    TinyPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.EXTRA_URL);

        player = new TinyPlayer(this, surface);
        paused = false;
        player.openUrl(url);
        player.play();
    }

    @OnClick(R.id.fab)
    void onPlayPauseClicked() {
        if (!paused) {
            Snackbar.make(fab, "Stream paused", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            fab.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.ic_media_play));
            player.pause();
        } else {
            Snackbar.make(fab, "Stream is playing", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            fab.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.ic_media_pause));
            player.play();
        }

        paused = !paused;
    }

}
