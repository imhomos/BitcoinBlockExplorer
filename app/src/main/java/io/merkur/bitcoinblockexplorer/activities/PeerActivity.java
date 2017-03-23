package io.merkur.bitcoinblockexplorer.activities;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.bitcoinj.core.Peer;

import java.util.HashMap;
import java.util.LinkedHashMap;

import io.merkur.bitcoinblockexplorer.R;
import io.merkur.bitcoinblockexplorer.adapters.ItemAdapter;

import static io.merkur.bitcoinblockexplorer.MyApplication.peerGroup;

public class PeerActivity extends AppCompatActivity {



    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private final LinkedHashMap<String, String> mDataset = new LinkedHashMap<>();
    private Peer peer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(this, R.drawable.divider_grey);
        horizontalDecoration.setDrawable(horizontalDivider);
        mRecyclerView.addItemDecoration(horizontalDecoration);

        // specify an adapter (see also next example)
        mAdapter = new ItemAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        try {
            getPeer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPeer() throws Exception {
        String peer_address;
        try {
            peer_address = getIntent().getStringExtra("peer");
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception();
        }

        peer=null;
        for(Peer p : peerGroup.getConnectedPeers()){
            if (p.getAddress().toString().equals(peer_address)){
                peer=p;
            }
        }

        mDataset.put("Address",peer.getAddress().toString());
        mDataset.put("BestHeight",String.valueOf(peer.getBestHeight()));
        mDataset.put("PingTime",String.valueOf(peer.getPingTime()));
        mDataset.put("LastPingTime",String.valueOf(peer.getLastPingTime()));
        mDataset.put("PeerBlockHeightDifference",String.valueOf(peer.getPeerBlockHeightDifference()));
        mDataset.put("PeerVersionMessage",peer.getPeerVersionMessage().toString());
        mAdapter.notifyDataSetChanged();
    }
}
