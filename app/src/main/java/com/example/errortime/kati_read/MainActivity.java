package com.example.errortime.kati_read;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Map;


public class MainActivity extends AppCompatActivity {
    TextView behavior_notification_num_text;
    TextView pill_notification_num_text;
    Button behavior_notification_button,pill_notification_button;
    public Query pill_notification_num,behavior_notification_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);



        behavior_notification_num_text = (TextView) findViewById(R.id.behavior_notification_num_text);
        pill_notification_num_text = (TextView) findViewById(R.id.pill_notification_num_text);
        behavior_notification_button = (Button) findViewById(R.id.behavior_notification_button);
        pill_notification_button = (Button) findViewById(R.id.pill_notification_button);
      /*  Button btnGetToken = (Button) findViewById(R.id.button2);
        btnGetToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastToken();
            }
        });*/
        //get num of notification
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        behavior_notification_num=database.getReference(FirebaseInstanceId.getInstance().getToken()+"/behavior").orderByChild("Token_Notification_visible_status").equalTo(FirebaseInstanceId.getInstance().getToken()+"_1");
        behavior_notification_num.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int num_of_record = (int)dataSnapshot.getChildrenCount();
                set_behavior_notification_num(num_of_record);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        pill_notification_num=database.getReference(FirebaseInstanceId.getInstance().getToken()+"/pill").orderByChild("Token_Notification_visible_status").equalTo(FirebaseInstanceId.getInstance().getToken()+"_1");
        pill_notification_num.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int num_of_record = (int)dataSnapshot.getChildrenCount();
                set_pill_notification_num(num_of_record);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    //button event
        behavior_notification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_status_behavior();
                Intent myIntent = new Intent(MainActivity.this, NotificationListActivity.class);
                myIntent.putExtra("topic_name","behavior");
                MainActivity.this.startActivity(myIntent);
            }
        });
        pill_notification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_status_pill();
                Intent myIntent = new Intent(MainActivity.this, NotificationListActivity.class);
                myIntent.putExtra("topic_name","pill");
                MainActivity.this.startActivity(myIntent);
            }
        });
        /*th.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_status_pill();
                Intent myIntent = new Intent(MainActivity.this, NotificationListActivity.class);
                myIntent.putExtra("topic_name","pill");
                MainActivity.this.startActivity(myIntent);
            }
        });
        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_status_pill();
                Intent myIntent = new Intent(MainActivity.this, NotificationListActivity.class);
                myIntent.putExtra("topic_name","pill");
                MainActivity.this.startActivity(myIntent);
            }
        });*/




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                open_setting_activity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void open_setting_activity(){
        Intent myIntent = new Intent(MainActivity.this, SettingActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
    public  void set_behavior_notification_num(int notification_num){

                if(notification_num>0){
                    behavior_notification_num_text.setText(String.valueOf(notification_num));
                    behavior_notification_num_text.setBackgroundResource(R.drawable.notification_style);
                }
                else{
                    behavior_notification_num_text.setText("");
                    behavior_notification_num_text.setBackgroundResource(0);

                }

    }
    public void set_pill_notification_num(int notification_num){
        if(notification_num>0){
            pill_notification_num_text.setText(String.valueOf(notification_num));
            pill_notification_num_text.setBackgroundResource(R.drawable.notification_style);
        }
        else{
            pill_notification_num_text.setText("");
            pill_notification_num_text.setBackgroundResource(0);

        }
    }
    public void update_status_behavior(){
        DatabaseReference postReference = FirebaseDatabase.getInstance().getReference(FirebaseInstanceId.getInstance().getToken()+"/behavior");

        postReference.orderByChild("Notification_visible_status").equalTo("1")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                data.getRef().child("Notification_visible_status").setValue("0");
                                data.getRef().child("Token_Notification_visible_status").setValue(FirebaseInstanceId.getInstance().getToken()+"_0");
                            }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
    public void update_status_pill(){
        DatabaseReference postReference = FirebaseDatabase.getInstance().getReference(FirebaseInstanceId.getInstance().getToken()+"/pill");

        postReference.orderByChild("Notification_visible_status").equalTo("1")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                data.getRef().child("Notification_visible_status").setValue("0");
                                data.getRef().child("Token_Notification_visible_status").setValue(FirebaseInstanceId.getInstance().getToken()+"_0");
                            }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


}
