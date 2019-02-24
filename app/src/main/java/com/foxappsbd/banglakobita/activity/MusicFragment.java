package com.foxappsbd.banglakobita.activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foxappsbd.banglakobita.MyListModel;
import com.foxappsbd.banglakobita.R;
import com.foxappsbd.banglakobita.RecyclerViewAdaptor;

import java.util.ArrayList;
import java.util.List;

import com.foxappsbd.banglakobita.R;

/**
 * Created by Arafat on 19/06/2017.
 */

public class MusicFragment extends Fragment {
    public MusicFragment() {
        // Required empty public constructor
    }



    public static final String TAG = "Recycle View";
    private MusicFragment mContext;
    private List<MyListModel> mList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mContext = this;
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mList = new ArrayList<MyListModel>();

        for(int i=0;i<20;i++){
            MyListModel myString = new MyListModel();
            myString.setName(i+"- Blank Item");
            myString.setViewType(1);
            mList.add(myString);
        }

        //ad showing
        adshow();

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerViewAdaptor(getContext(), mList);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public void adshow(){
        ConnectivityManager ConnectionManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            //Place two Admob Ads at position index 1 and 5 in recyclerview
            MyListModel myString1 = new MyListModel();
            myString1.setViewType(2);
            mList.add(1,myString1);

            MyListModel myString2 = new MyListModel();
            myString2.setViewType(2);
            mList.add(5,myString2);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

