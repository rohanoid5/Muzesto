package com.rohan.app.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rohan.app.MusicPlayer;
import com.rohan.app.R;
import com.rohan.app.adapters.SongsListAdapter;
import com.rohan.app.dataloaders.SongLoader;
import com.rohan.app.models.Song;
import com.rohan.app.utils.TimberUtils;
import com.rohan.app.widgets.DividerItemDecoration;

import java.util.List;

import static android.R.attr.description;
import static android.os.Build.VERSION_CODES.M;
import static com.rohan.app.dataloaders.SongLoader.getAllSongs;

/**
 * Created by rohan on 16-02-2017.
 */

public class SlideUpNowPlayingFragment extends Fragment {

    String title;
    ViewPager viewPager;
    List<Song> songsList;
    private long[] songIDs;

    public static SlideUpNowPlayingFragment newInstance(String title) {
        SlideUpNowPlayingFragment fragmentFirst = new SlideUpNowPlayingFragment();
        Bundle args = new Bundle();
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new loadSongs().execute("");
        //title = getArguments().getString("someTitle");
//        songIDs = getSongIds();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sliding_now_playing, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.songs_pager);
        new loadSongs().execute("");
        Log.e(SlideUpNowPlayingFragment.class.getSimpleName(), getActivity().toString());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                MusicPlayer.playOrPause();
                MusicPlayer.playAll(getActivity(), songIDs, position, -1, TimberUtils.IdType.NA, false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    public void setCurrSongs(int position) {
        viewPager.setCurrentItem(position);
    }

    public long[] getSongIds() {
        long[] ret = new long[songsList.size()];
        for (int i = 0; i < songsList.size(); i++) {
            ret[i] = songsList.get(i).id;
        }

        return ret;
    }

    private void reloadAdapter() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... unused) {
                songsList = SongLoader.getAllSongs(getActivity());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

            }
        }.execute();
    }

    class SongViewPagerAdapter extends FragmentPagerAdapter {

        public SongViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ViewPagerSongsFragment.newInstance(songsList.get(position), position);
        }

        @Override
        public int getCount() {
            return songsList.size();
        }
    }

    private class loadSongs extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            if (getActivity() != null) {
                songsList = SongLoader.getAllSongs(getActivity());
                songIDs = getSongIds();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            viewPager.setAdapter(new SongViewPagerAdapter(getFragmentManager()));
            //setCurrSongs(9);
        }

        @Override
        protected void onPreExecute() {
        }
    }

}