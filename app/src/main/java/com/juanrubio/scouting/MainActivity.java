package com.juanrubio.scouting;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    public EditText teamNumberEditText;
    public EditText matchNumberEditText;
    public EditText highBoxesEditText;
    public EditText lowBoxesOwnSwitchEditText;
    public EditText lowBoxesOpposingEditText;
    public EditText autonomousEditText;
    public EditText passedLineEditText;
    public EditText endgameEditText;
    public EditText notesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teamNumberEditText = (EditText)findViewById(R.id.teamNumberEditText);
        matchNumberEditText = (EditText)findViewById(R.id.matchNumberEditText);
        highBoxesEditText = (EditText)findViewById(R.id.highBoxesEditText);
        lowBoxesOwnSwitchEditText = (EditText)findViewById(R.id.lowBoxesOwnSwitchEditText);
        lowBoxesOpposingEditText = (EditText)findViewById(R.id.lowBoxesOpposingEditText);
        autonomousEditText = (EditText)findViewById(R.id.autonomousEditText);
        passedLineEditText = (EditText)findViewById(R.id.passedLineEditText);
        endgameEditText = (EditText)findViewById(R.id.endgameEditText);
        notesEditText = (EditText)findViewById(R.id.notesEditText);
    }

    public void clear(View view){
        teamNumberEditText.setText("");
        matchNumberEditText.setText("");
        highBoxesEditText.setText("");
        lowBoxesOwnSwitchEditText.setText("");
        lowBoxesOpposingEditText.setText("");
        autonomousEditText.setText("");
        passedLineEditText.setText("");
        endgameEditText.setText("");
        notesEditText.setText("");
    }

    public void save(View view){
        final String teamNum = teamNumberEditText.getText().toString();
        final String matchNum = matchNumberEditText.getText().toString();
        final String highBox = highBoxesEditText.getText().toString();
        final String lowBoxesOwn = lowBoxesOwnSwitchEditText.getText().toString();
        final String lowBoxesOpposing = lowBoxesOpposingEditText.getText().toString();
        final String auto = autonomousEditText.getText().toString();
        final String passed = passedLineEditText.getText().toString();
        final String endgame = endgameEditText.getText().toString();
        final String notes = notesEditText.getText().toString();


        if(teamNum.equals("159896")){
            Intent intent = new Intent(getBaseContext(), HiddenCreditsActivity.class);
            startActivity(intent);
        }
        else if(!teamNum.equals("")&&!matchNum.equals("")&&!highBox.equals("")&&!lowBoxesOwn.equals("")&&!lowBoxesOpposing.equals("")&&!auto.equals("")&&!passed.equals("")&&!endgame.equals("")){
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams data = new RequestParams();
            data.add("teamnum", teamNum);
            data.add("matchnum", matchNum);
            data.add("highboxes", highBox);
            data.add("lowSelf", lowBoxesOwn);
            data.add("auto", auto);
            data.add("endgame", endgame);
            data.add("lowEnemy", lowBoxesOpposing);
            data.add("line", passed);
            data.add("notes", notes);

            Log.i("Data Information: ", data.toString());

            client.post("http://website-env.us-east-2.elasticbeanstalk.com/scoutingsend/", data, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i("Success: ", new String(responseBody));
                    Toast.makeText(getBaseContext(), "Data Sent Successfully!", Toast.LENGTH_LONG).show();
                    teamNumberEditText.setText("");
                    matchNumberEditText.setText("");
                    highBoxesEditText.setText("");
                    lowBoxesOwnSwitchEditText.setText("");
                    lowBoxesOpposingEditText.setText("");
                    autonomousEditText.setText("");
                    passedLineEditText.setText("");
                    endgameEditText.setText("");
                    notesEditText.setText("");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("Error: ", new String(responseBody));
                    Toast.makeText(getBaseContext(), "Error Sending Data. Check your internet connection.", Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            Toast toast = Toast.makeText(this, "Please Complete All Fields", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
