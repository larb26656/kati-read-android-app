package com.example.errortime.kati_read;

import android.content.ClipboardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

public class SettingActivity extends AppCompatActivity {
    private  EditText token_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        token_text = (EditText) findViewById(R.id.token_text);
        set_token_text();
        Button copy_token__button = (Button) findViewById(R.id.copy_token_button);
        copy_token__button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(token_text.getText());
                Toast.makeText(getApplicationContext(),
                        R.string.warning_copy_name, Toast.LENGTH_LONG).show();
            }
        });

    }
    private void set_token_text(){
        String token = FirebaseInstanceId.getInstance().getToken();
        token_text.setText(token);
        Log.d("myTag", token);

    }
}
