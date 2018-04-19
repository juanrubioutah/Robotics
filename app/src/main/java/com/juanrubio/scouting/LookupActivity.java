package com.juanrubio.scouting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class LookupActivity extends AppCompatActivity {

    public EditText searchEditText;
    public TextView resultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup);
        searchEditText = findViewById(R.id.searchEditText);
        resultsTextView = findViewById(R.id.infoTextView);
    }
    public void search(View view){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams data = new RequestParams();
        data.add("teamnum", searchEditText.getText().toString());

        client.post("http://website-env.us-east-2.elasticbeanstalk.com/scoutingsend/", data, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("Success: ", new String(responseBody));
                resultsTextView.setText(new String(responseBody));
                setContentView(R.layout.activity_lookup); //refresh view
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("Error: ", new String(responseBody));
                Toast.makeText(getBaseContext(), "Error Searching. Check your internet connection.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
