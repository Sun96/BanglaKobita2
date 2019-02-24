package com.foxappsbd.banglakobita.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.foxappsbd.banglakobita.ContextShow;
import com.foxappsbd.banglakobita.FevrioutText;
import com.foxappsbd.banglakobita.MyListModel;
import com.foxappsbd.banglakobita.PersonDatabase;
import com.foxappsbd.banglakobita.R;
import com.foxappsbd.banglakobita.RecyclerViewAdaptor;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arafat on 19/06/2017.
 */

public class VideoFragment extends Fragment {
    public VideoFragment() {
        // Required empty public constructor
    }


    public static final String TAG = "Recycle View";
    private HomeFragment mContext;
    private List<MyListModel> mList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar toolbar;
    private long lastPressedTime;
    private PublisherInterstitialAd interstitialAd;
    private InterstitialAd interstitial;
    String[] testArray,testtitel;
    int i,j;
    PersonDatabase mydb;
    Cursor data;
    ArrayList<String> theList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mydb = new PersonDatabase(getActivity());
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mList = new ArrayList<MyListModel>();


        //testtitel = getResources().getStringArray(R.array.romantic);

        //populate an ArrayList<String> from the database and then view it
        theList = new ArrayList<>();
         data = mydb.getAllData();
        if(data.getCount() == 0){
            Toast.makeText(getActivity(), "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                theList.add(data.getString(1));
            }
        }
        for(i=0;i<data.getCount();i++){
            MyListModel myString = new MyListModel();
            myString.setName(theList.get(i));
            myString.setViewType(1);
            mList.add(myString);
        }

        //ad showing
        adshow();
        //inst-add
        addListnerOnButton();





        return rootView;
    }
    public void addListnerOnButton() {
        final Context context = getContext();
        interstitialAd = new PublisherInterstitialAd(getContext());
        interstitialAd.setAdUnitId("ca-app-pub-3420675499646666/1093565039");

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();

            }
        });
        requestNewInterstitial();

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerViewAdaptor(getContext(), mList);
        mRecyclerView.setAdapter(mAdapter);
        //oneClickListener.......
        mRecyclerView.addOnItemTouchListener(new HomeFragment.RecyclerTouchListener(getActivity(), mRecyclerView, new HomeFragment.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(getActivity(), FevrioutText.class);
                Bundle bundle = new Bundle();
                bundle.putString("sms", theList.get(position));
                //bundle.putString("titel", testtitel[position]);
                i.putExtras(bundle);
                if(position%3==0) {
                    interstitialAd.isLoaded();
                    interstitialAd.show();
                    requestNewInterstitial();
                }
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }
    //for add..........................
    private void requestNewInterstitial() {
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder()
                .addTestDevice("")
                .build();

        interstitialAd.loadAd(adRequest);
    }



    public void adshow(){
        ConnectivityManager ConnectionManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            for(i=5;i<=data.getCount();i=i+5) {
                //Place two Admob Ads at position index 1 and 5 in recyclerview
                MyListModel myString1 = new MyListModel();
                myString1.setViewType(2);
                mList.add(i, myString1);
            }
        }
    }

    ///for clickListener..........
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private HomeFragment.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final HomeFragment.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
