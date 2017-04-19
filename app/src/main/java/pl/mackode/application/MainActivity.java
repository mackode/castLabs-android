package pl.mackode.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "pl.mackode.application.URL";

    @BindView(R.id.listview)
    ListView listView;

    String[] values = new String[] {
            "http://demo.unified-streaming.com/video/smurfs/smurfs.ism/smurfs.mpd",
            "http://dash.edgesuite.net/envivio/dashpr/clear/Manifest.mpd" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    @OnItemClick(R.id.listview)
    void onItemSelected(int position) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(EXTRA_URL, values[position]);
        startActivity(intent);
    }
}
