package com.example.videocallingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
public class DashboardActivity extends AppCompatActivity {
        EditText codeBox;
        Button joinBtn, shareBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        codeBox = findViewById(R.id.codeBox);
        joinBtn = findViewById(R.id.joinBtn);
        shareBtn = findViewById(R.id.shareBtn);

        URL serverURL;
        try{
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverURL).build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        } catch (MalformedURLException e) {
           e.printStackTrace();
        }

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(codeBox.getText().toString()).setFeatureFlag("invite.enabled" , false)
                    .build();
                JitsiMeetActivity.launch(DashboardActivity.this,options);


            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,"Enter the room code to join the meeting : " +codeBox.getText().toString());
                shareIntent.setType("text/plain");
                startActivity(shareIntent);

            }
        });
    }
}