package com.example.finalprojectv1;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

import org.json.JSONException;
import org.json.JSONObject;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SessionWindow5 extends AppCompatActivity implements com.opentok.android.Session.SessionListener, PublisherKit.PublisherListener{

    private static String API_KEY = "46324982";
    private static String SESSION_ID = "2_MX40NjMyNDk4Mn5-MTU1NzI3ODU2MTA3OH5tZmkydmgrUkVGWkMzMkpvalVMY0Jabmx-fg";
    private static String TOKEN = "T1==cGFydG5lcl9pZD00NjMyNDk4MiZzaWc9OTM0ODAzMGY0MmE2NjZjOWU3MzZiNzE1MDkyYjQ0NGExNGJhM2U3ZTpzZXNzaW9uX2lkPTJfTVg0ME5qTXlORGs0TW41LU1UVTFOekkzT0RVMk1UQTNPSDV0Wm1reWRtZ3JVa1ZHV2tNek1rcHZhbFZNWTBKYWJteC1mZyZjcmVhdGVfdGltZT0xNTU3Mjc4NjE5Jm5vbmNlPTAuMTU3MDUwMzA2MTk4NjQ2MzUmcm9sZT1wdWJsaXNoZXImZXhwaXJlX3RpbWU9MTU1OTg3MDYxNyZpbml0aWFsX2xheW91dF9jbGFzc19saXN0PQ==";
    private static final String LOG_TAG = Sessionwindow.class.getSimpleName();
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;

    private com.opentok.android.Session mSession;
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
    public Button btnnext;

    public void fetchSessionConnectionData() {
        RequestQueue reqQueue = Volley.newRequestQueue(this);
        reqQueue.add(new JsonObjectRequest(Request.Method.GET,
                "https://rotateadate3.herokuapp.com/" + "/session",
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

                    mSession = new com.opentok.android.Session.Builder(SessionWindow5.this, API_KEY, SESSION_ID).build();
                    mSession.setSessionListener(SessionWindow5.this);
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
                Intent signupclick = new Intent(SessionWindow5.this, Settings.class);
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
                btnnext = (Button) findViewById(R.id.btnnext);

            }
        });
        btnnext = (Button) findViewById(R.id.btnnext);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hnew = new Intent(SessionWindow5.this, Sessionwindow.class);
                startActivity(hnew);
                android.os.Process.killProcess(android.os.Process.myPid());
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
        setContentView(R.layout.activity_session_window5);
        requestPermissions();
        init2();
    }
    @Override
    public void onConnected(com.opentok.android.Session session) {
        Log.i(LOG_TAG, "Session Connected");

        mPublisher = new Publisher.Builder(this).name("Bob's video").build();

        mPublisher.setPublisherListener(this);

        mPublisherViewContainer.addView(mPublisher.getView());

        mSession.publish(mPublisher);

    }


    @Override
    public void onDisconnected(com.opentok.android.Session session) {
        Log.i(LOG_TAG, "Session Disconnected");
    }

    @Override
    public void onStreamReceived(com.opentok.android.Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Received");

        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(this, stream).build();
            mSession.subscribe(mSubscriber);
            mSubscriberViewContainer.addView(mSubscriber.getView());
        }
    }


    @Override
    public void onStreamDropped(com.opentok.android.Session session, Stream stream) {
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
