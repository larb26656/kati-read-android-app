package com.example.errortime.kati_read;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static android.R.attr.id;

public class NotificationListActivity extends AppCompatActivity {
    String token = FirebaseInstanceId.getInstance().getToken();
    TextView topic_text, status_text;
    ListView notification_listview;
    EditText date1_edittext, date2_edittext;
    ImageView type_icon;
    String topic_name, condition1, condition2;
    public Query notification_data;
    ArrayList<String> notification_date_list = new ArrayList<>();
    ArrayList<String> notification_detail_list = new ArrayList<>();
    Calendar myCalendar = Calendar.getInstance();
    FirebaseDatabase database;
    int num_of_record = 0, i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        Intent myIntent = getIntent(); // gets the previously created intent
        topic_name = myIntent.getStringExtra("topic_name");
        type_icon = (ImageView) findViewById(R.id.type_icon);
        topic_text = (TextView) findViewById(R.id.topic_text);
        if (topic_name.equals("behavior")) {
            topic_text.setText(R.string.behavior_topic_name);
        } else {
            topic_text.setText(R.string.pill_topic_name);
        }

        if (topic_name.equals("behavior")) {
            type_icon.setImageResource(R.drawable.behavior_list);
        } else {
            type_icon.setImageResource(R.drawable.pill_list);
        }
        status_text = (TextView) findViewById(R.id.status_text);
        date1_edittext = (EditText) findViewById(R.id.date1_edittext);
        date2_edittext = (EditText) findViewById(R.id.date2_edittext);
        notification_listview = (ListView) findViewById(R.id.notification_listview);
        setCondition1(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        setCondition2(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        updatedatabase(getCondition1(), getCondition2());
        final DatePickerDialog.OnDateSetListener datepicker1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updatedate1();
            }

        };
        final DatePickerDialog.OnDateSetListener datepicker2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updatedate2();
            }

        };
        date1_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(NotificationListActivity.this, datepicker1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        date2_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getCondition1() == null) {
                    Toast.makeText(getApplicationContext(), R.string.warning_date1_null_name,
                            Toast.LENGTH_LONG).show();
                } else {
                    DatePickerDialog dpd = new DatePickerDialog(NotificationListActivity.this, datepicker2, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                    try {
                        Date d = sdf.parse(String.valueOf(date1_edittext.getText()));
                        dpd.getDatePicker().setMinDate(d.getTime());
                        dpd.show();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void updatedate1() {
        String myFormat1 = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        String myFormat2 = "yyyyMMdd"; //In which you need put here
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);
        date1_edittext.setText(sdf1.format(myCalendar.getTime()));
        setCondition1(sdf2.format(myCalendar.getTime()));
        date2_edittext.setText(null);
    }

    private void updatedate2() {
        String myFormat1 = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        String myFormat2 = "yyyyMMdd"; //In which you need put here
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);
        date2_edittext.setText(sdf1.format(myCalendar.getTime()));
        setCondition2(sdf2.format(myCalendar.getTime()));
        updatedatabase(getCondition1(), getCondition2());
    }

    public String getCondition1() {
        return condition1;
    }

    public String getCondition2() {
        return condition2;
    }

    public void setCondition1(String date1) {
        this.condition1 = date1 + "000000";
    }

    public void setCondition2(String date2) {

        this.condition2 = date2 + "235959";
    }

    private void fetchData(DataSnapshot snapshot) {
        notification_date_list.add(snapshot.child("Notification_date_with_format").getValue(String.class));
        notification_detail_list.add(snapshot.child("Notification_detail_"+getString(R.string.lang_label)).getValue(String.class));
    }

    private void setAdapter() {
        NotificationAdapter notification = new NotificationAdapter(this, notification_date_list, notification_detail_list);
        notification_listview.setAdapter(notification);
    }

    private void updatedatabase(String date1, String date2) {
        database = FirebaseDatabase.getInstance();
        notification_data = database.getReference(FirebaseInstanceId.getInstance().getToken() + "/" + topic_name).orderByChild("Notification_date").startAt(date1).endAt(date2);
        notification_data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notification_date_list.clear();
                notification_detail_list.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    notification_date_list.add(postSnapshot.child("Notification_date_with_format").getValue(String.class));
                    notification_detail_list.add(postSnapshot.child("Notification_detail_"+getString(R.string.lang_label)).getValue(String.class));
                }
                setAdapter();
                String result_query_name = getResources().getString(R.string.result_query_name);
                String record_unit_name = getResources().getString(R.string.record_unit_name);
                status_text.setText(result_query_name + " " + String.valueOf(notification_date_list.size()) + " " + record_unit_name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private Date getDate(long time) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();//get your local time zone.
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        sdf.setTimeZone(tz);//set time zone.
        String localTime = sdf.format(new Date((time) * 1000));
        Date date = new Date();
        try {
            date = sdf.parse(localTime);//get local date
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
