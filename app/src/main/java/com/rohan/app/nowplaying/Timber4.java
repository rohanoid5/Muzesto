package com.rohan.app.nowplaying;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rohan.app.MusicPlayer;
import com.rohan.app.R;
import com.rohan.app.adapters.Timber4QueueAdapter;
import com.rohan.app.dataloaders.QueueLoader;
import com.rohan.app.utils.ImageUtils;
import com.rohan.app.yalantis.waves.util.Horizon;
import com.yalantis.audio.lib.AudioUtil;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

public class Timber4 extends BaseNowplayingFragment {

    ImageView mBlurredArt;
    RecyclerView horizontalRecyclerview;
    Timber4QueueAdapter horizontalAdapter;
    Toolbar mToolbar;
    ImageView mSongs, mEq;

    private static final int REQUEST_PERMISSION_RECORD_AUDIO = 1;

    private static final int RECORDER_SAMPLE_RATE = 44100;
    private static final int RECORDER_CHANNELS = 1;
    private static final int RECORDER_ENCODING_BIT = 16;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private static final int MAX_DECIBELS = 120;

    private AudioRecord audioRecord;
    private Horizon mHorizon;
    private GLSurfaceView glSurfaceView;

    private Thread recordingThread;
    private byte[] buffer;

    /**
     * this listener helps us to synchronise real time
     * and actual drawing
     */
    private AudioRecord.OnRecordPositionUpdateListener recordPositionUpdateListener = new AudioRecord.OnRecordPositionUpdateListener() {
        @Override
        public void onMarkerReached(AudioRecord recorder) {
            //empty for now
        }

        @Override
        public void onPeriodicNotification(AudioRecord recorder) {
            if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING
                    && audioRecord.read(buffer, 0, buffer.length) != -1) {
                mHorizon.updateView(buffer);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_timber4, container, false);

        setMusicStateListener();
        setSongDetails(rootView);

        mSongs = (ImageView) rootView.findViewById(R.id.songs);
        mEq = (ImageView) rootView.findViewById(R.id.eq);
        mBlurredArt = (ImageView) rootView.findViewById(R.id.album_art_blurred);
        horizontalRecyclerview = (RecyclerView) rootView.findViewById(R.id.queue_recyclerview_horizontal);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mToolbar.getBackground().setAlpha(0);

        glSurfaceView = (GLSurfaceView) rootView.findViewById(R.id.gl_surface);
        mHorizon = new Horizon(glSurfaceView, getResources().getColor(R.color.colorAccent),
                RECORDER_SAMPLE_RATE, RECORDER_CHANNELS, RECORDER_ENCODING_BIT);
        mHorizon.setMaxVolumeDb(MAX_DECIBELS);

        setupHorizontalQueue();

        mEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_album_white_24dp);
                Bitmap bm1 = changeBitmapColor(b1, ContextCompat.getColor(getActivity(), R.color.white));
                mSongs.setImageBitmap(bm1);

                Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_graphic_eq_white_24dp);
                Bitmap bm2 = changeBitmapColor(b2, ContextCompat.getColor(getActivity(), R.color.colorAccent));
                mEq.setImageBitmap(bm2);
//                if (savedInstanceState == null) {
//                    glSurfaceView.setVisibility(View.INVISIBLE);
//
//                    ViewTreeObserver viewTreeObserver = glSurfaceView.getViewTreeObserver();
//                    if (viewTreeObserver.isAlive()) {
//                        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                            @Override
//                            public void onGlobalLayout() {
                circularRevealActivity(glSurfaceView, horizontalRecyclerview);
//                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//                                    glSurfaceView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                                } else {
//                                    glSurfaceView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                                }
//                            }
//                        });
//                    }
//                }
            }
        });

        mSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_album_white_24dp);
                Bitmap bm1 = changeBitmapColor(b1, ContextCompat.getColor(getActivity(), R.color.colorAccent));
                mSongs.setImageBitmap(bm1);

                Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_graphic_eq_white_24dp);
                Bitmap bm2 = changeBitmapColor(b2, ContextCompat.getColor(getActivity(), R.color.white));
                mEq.setImageBitmap(bm2);

                exitReveal(glSurfaceView);
            }
        });

        return rootView;
    }

    private Bitmap changeBitmapColor(Bitmap sourceBitmap, int color) {

        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0,
                sourceBitmap.getWidth() - 1, sourceBitmap.getHeight() - 1);
        Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(color, 1);
        p.setColorFilter(filter);
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, p);
        return resultBitmap;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void circularRevealActivity(View v1, View v2) {

        int cx = v1.getWidth() / 2;
        int cy = v1.getHeight() / 2;

        float finalRadius = Math.max(v1.getWidth(), v1.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(v1, cx, cy, 0, finalRadius);
        circularReveal.setDuration(1000);

        // make the view visible and start the animation
        v2.setVisibility(View.INVISIBLE);
        v1.setVisibility(View.VISIBLE);
        circularReveal.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void exitReveal(final View myView) {
        // previously visible view
        //final View myView = findViewById(R.id.my_view);

        // get the center for the clipping circle
        int cx = myView.getMeasuredWidth() / 2;
        int cy = myView.getMeasuredHeight() / 2;

        // get the initial radius for the clipping circle
        int initialRadius = myView.getWidth() / 2;

        // create the animation (the final radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
                horizontalRecyclerview.setVisibility(View.VISIBLE);
            }
        });

        // start the animation
        anim.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        checkPermissionsAndStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (audioRecord != null) {
            audioRecord.release();
        }
        AudioUtil.disposeProcessor();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_RECORD_AUDIO:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissionsAndStart();
                } else {
                    getActivity().finish();
                }
        }

    }

    private void checkPermissionsAndStart() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_PERMISSION_RECORD_AUDIO);
        } else {
            initRecorder();
            if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
                startRecording();
            }
        }
    }

    private void initRecorder() {
        final int bufferSize = 2 * AudioRecord.getMinBufferSize(RECORDER_SAMPLE_RATE,
                RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, RECORDER_SAMPLE_RATE,
                RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING, bufferSize);
        AudioUtil.initProcessor(RECORDER_SAMPLE_RATE, RECORDER_CHANNELS, RECORDER_ENCODING_BIT);

        recordingThread = new Thread("recorder") {
            @Override
            public void run() {
                super.run();
                buffer = new byte[bufferSize];
                Looper.prepare();
                audioRecord.setRecordPositionUpdateListener(recordPositionUpdateListener, new Handler(Looper.myLooper()));
                int bytePerSample = RECORDER_ENCODING_BIT / 8;
                float samplesToDraw = bufferSize / bytePerSample;
                audioRecord.setPositionNotificationPeriod((int) samplesToDraw);
                //We need to read first chunk to motivate recordPositionUpdateListener.
                //Mostly, for lower versions - https://code.google.com/p/android/issues/detail?id=53996
                audioRecord.read(buffer, 0, bufferSize);
                Looper.loop();
            }
        };
    }

    private void startRecording() {
        if (audioRecord != null) {
            audioRecord.startRecording();
        }
        recordingThread.start();
    }

    @Override
    public void updateShuffleState() {
        if (shuffle != null && getActivity() != null) {
            MaterialDrawableBuilder builder = MaterialDrawableBuilder.with(getActivity())
                    .setIcon(MaterialDrawableBuilder.IconValue.SHUFFLE)
                    .setSizeDp(30);

            if (MusicPlayer.getShuffleMode() == 0) {
                builder.setColor(Color.WHITE);
            } else builder.setColor(accentColor);

            shuffle.setImageDrawable(builder.build());
            shuffle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MusicPlayer.cycleShuffle();
                    updateShuffleState();
                    updateRepeatState();
                }
            });
        }
    }

    @Override
    public void updateRepeatState() {
        if (repeat != null && getActivity() != null) {
            MaterialDrawableBuilder builder = MaterialDrawableBuilder.with(getActivity())
                    .setIcon(MaterialDrawableBuilder.IconValue.REPEAT)
                    .setSizeDp(30);

            if (MusicPlayer.getRepeatMode() == 0) {
                builder.setColor(Color.WHITE);
            } else builder.setColor(accentColor);

            repeat.setImageDrawable(builder.build());
            repeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MusicPlayer.cycleRepeat();
                    updateRepeatState();
                    updateShuffleState();
                }
            });
        }
    }

    @Override
    public void doAlbumArtStuff(Bitmap loadedImage) {
        setBlurredAlbumArt blurredAlbumArt = new setBlurredAlbumArt();
        blurredAlbumArt.execute(loadedImage);
    }

    private void setupHorizontalQueue() {
        horizontalRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        horizontalAdapter = new Timber4QueueAdapter(getActivity(), QueueLoader.getQueueSongs(getActivity()));
        horizontalRecyclerview.setAdapter(horizontalAdapter);
        horizontalRecyclerview.scrollToPosition(MusicPlayer.getQueuePosition() - 3);
    }

    private class setBlurredAlbumArt extends AsyncTask<Bitmap, Void, Drawable> {

        @Override
        protected Drawable doInBackground(Bitmap... loadedImage) {
            Drawable drawable = null;
            try {
                drawable = ImageUtils.createBlurredImageFromBitmap(loadedImage[0], getActivity(), 6);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return drawable;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            if (result != null) {
                if (mBlurredArt.getDrawable() != null) {
                    final TransitionDrawable td =
                            new TransitionDrawable(new Drawable[]{
                                    mBlurredArt.getDrawable(),
                                    result
                            });
                    mBlurredArt.setImageDrawable(td);
                    td.startTransition(200);

                } else {
                    mBlurredArt.setImageDrawable(result);
                }
            }
        }

        @Override
        protected void onPreExecute() {
        }
    }


}
