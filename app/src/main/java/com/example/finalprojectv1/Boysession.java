package com.example.finalprojectv1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class Boysession extends AppCompatActivity implements Session.SessionListener, PublisherKit.PublisherListener{
    private static String API_KEY = "46324852";
    private static String SESSION_ID = "1_MX40NjMyNDg1Mn5-MTU1NzI2MDQzNzA0MX5JdkhiMkVjNTFpTXlDQ2NIWUNMYU83cnB-fg";
    private static String TOKEN = "T1==cGFydG5lcl9pZD00NjMyNDg1MiZzaWc9NTBiMmE3ZjJlOTlhMzE2MzU2MDk3ZmMyM2EwZmJmZWIzOTlmOGY5OTpzZXNzaW9uX2lkPTFfTVg0ME5qTXlORGcxTW41LU1UVTFOekkyTURRek56QTBNWDVKZGtoaU1rVmpOVEZwVFhsRFEyTklXVU5NWVU4M2NuQi1mZyZjcmVhdGVfdGltZT0xNTU3MjYwNDYyJm5vbmNlPTAuMjA5MTYyMjE5ODgzMDExNjUmcm9sZT1wdWJsaXNoZXImZXhwaXJlX3RpbWU9MTU1OTg1MjQ1OSZpbml0aWFsX2xheW91dF9jbGFzc19saXN0PQ==";
    private static final String LOG_TAG = Boysession.class.getSimpleName();
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;

    private Session mSession;
    private Publisher mPublisher;
    private FrameLayout mPublisherViewContainer;
    private FrameLayout mSubscriberViewContainer;
    private Subscriber mSubscriber;
    private Boolean muted = false;
    public Button btnstop;
    public Button btnmute;
    public Button btnunmute;
    public Button btncamera;
    public Button btnflag;

    public void fetchSessionConnectionData() {
        RequestQueue reqQueue = Volley.newRequestQueue(this);
        reqQueue.add(new JsonObjectRequest(Request.Method.GET,
                "https://rotateadateboys.herokuapp.com/" + "/session",
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    API_KEY = response.getString("apiKey");
                    SESSION_ID = response.getString("sessionId");
                    TOKEN = response.getString("token");

                    Log.i(LOG_TAG, "API_KEY: " + API_KEY);
                    Log.i(LOG_TAG, "SESSION_ID: " + SESSION_ID);
                    Log.i(LOG_TAG, "TOKEN: " + TOKEN);

                    mSession = new Session.Builder(Boysession.this, API_KEY, SESSION_ID).build();
                    mSession.setSessionListener(Boysession.this);
                    mSession.connect(TOKEN);

                } catch (JSONException error) {
                    Log.e(LOG_TAG, "Web Service error: " + error.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG, "Web Service error: " + error.getMessage());
            }
        }));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
            // initialize view objects from your layout
            mPublisherViewContainer = (FrameLayout) findViewById(R.id.publisher_container);
            mSubscriberViewContainer = (FrameLayout) findViewById(R.id.subscriber_container);

            // initialize and connect to the session
            fetchSessionConnectionData();

        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }

    public void init2() {
        btnstop = (Button) findViewById(R.id.btnstop);
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupclick = new Intent(Boysession.this, Settings.class);
                startActivity(signupclick);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        btnmute = (Button) findViewById(R.id.btnmute);

        btnmute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPublisher.setPublishAudio(false);

            }
        });
        btnunmute = (Button) findViewById(R.id.btnunmute);

        btnunmute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPublisher.setPublishAudio(true);
            }
        });

        btncamera = (Button) findViewById(R.id.btncamera);
        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPublisher.swapCamera();



            }
        });
        btnflag = (Button) findViewById(R.id.btnreport);
        btnflag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mPublisher.setPublishVideo(false);


            }
        });

    }


    @Override
    protected void onPause() {

        Log.d(LOG_TAG, "onPause");

        super.onPause();

        if (mSession != null) {
            mSession.onPause();
        }

    }

    @Override
    protected void onResume() {

        Log.d(LOG_TAG, "onResume");

        super.onResume();

        if (mSession != null) {
            mSession.onResume();
        }
    }

    public Boolean getMuted() {
        return muted;
    }

    public void setMuted(Boolean muted) {
        this.muted = muted;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boysession);
        requestPermissions();
        init2();
    }
    @Override
    public void onConnected(Session session) {
        Log.i(LOG_TAG, "Session Connected");

        mPublisher = new Publisher.Builder(this).name("Bob's video").build();

        mPublisher.setPublisherListener(this);

        mPublisherViewContainer.addView(mPublisher.getView());

        mSession.publish(mPublisher);

    }


    @Override
    public void onDisconnected(Session session) {
        Log.i(LOG_TAG, "Session Disconnected");
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Received");

        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(this, stream).build();
            mSession.subscribe(mSubscriber);
            mSubscriberViewContainer.addView(mSubscriber.getView());
        }
    }


    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Dropped");

        if (mSubscriber != null) {
            mSubscriber = null;
            mSubscriberViewContainer.removeAllViews();
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.e(LOG_TAG, "Session error: " + opentokError.getMessage());
    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {

    }
}
