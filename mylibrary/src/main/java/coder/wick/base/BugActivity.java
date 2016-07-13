package coder.wick.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import coder.mylibrary.R;


public class BugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug);
        TextView textView = (TextView) findViewById(R.id.bugTextView);
        textView.setText(getIntent().getStringExtra("stackTrace"));
    }

}
