package pl.mackode.library;

import android.content.Context;
import android.net.Uri;
import android.view.SurfaceView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

public class TinyPlayer {

    private Context context;
    private SurfaceView surfaceView;
    private SimpleExoPlayer player;
    private DashMediaSource videoSource;

    public TinyPlayer(Context context, SurfaceView view) {
        this.context = context;
        this.surfaceView = view;
        adaptiveStreaming(true);
    }

    public void openDashUrl(String url) {
        videoSource.replaceManifestUri(Uri.parse(url));
    }

    public void play() {
        player.setPlayWhenReady(true);
    }

    public void pause() {
        player.setPlayWhenReady(false);
    }

    public void adaptiveStreaming(boolean enable) {
        if (enable) {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            player.setVideoSurfaceView(surfaceView);
        } else {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            player.setVideoSurfaceView(surfaceView);
        }

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, "TinyPlayer");
        DashChunkSource.Factory dashFactory = new DefaultDashChunkSource.Factory(dataSourceFactory);
        videoSource = new DashMediaSource(Uri.parse(null), dataSourceFactory, dashFactory, null, null);
        player.prepare(videoSource);
    }

}
