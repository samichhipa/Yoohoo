package com.ideazshuttle.chatwall.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ideazshuttle.chatwall.R;
import com.ideazshuttle.chatwall.fragments.OptionsFragment;
import com.ideazshuttle.chatwall.services.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Connect_with_itrack extends AppCompatActivity {

    String Token;
    EditText username,password;
    Button sign_in_button;
    String firebase_id, firebase_number, user_txt, pass_txt;
    private static final int JOB_ID = 101;
    private JobScheduler jobScheduler;
    private ProgressDialog pDialog;
    private JobInfo jobInfo;
    JSONParser jsonParser = new JSONParser();
    String HttpURLin = "https://ideazshuttle.com/mobile_app/chat_firebase_connection.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_with_itrack);

        username = findViewById(R.id.user_txt);
        password = findViewById(R.id.pass_txt);
        sign_in_button = findViewById(R.id.submit_btn);

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_txt = username.getText().toString();
                pass_txt = password.getText().toString();

                final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                firebase_id = currentFirebaseUser.getUid();
                firebase_number = currentFirebaseUser.getPhoneNumber();

                Token = FirebaseInstanceId.getInstance().getToken();
                Log.e("immad",  "Token :" + Token);

                new conect().execute();
            }
        });

    }

    class conect extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Connect_with_itrack.this);
            pDialog.setMessage("Marking Please Wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("firebase_id", firebase_id));
            params.add(new BasicNameValuePair("user_txt", user_txt));
            params.add(new BasicNameValuePair("pass_txt", pass_txt));
            params.add(new BasicNameValuePair("token", Token));
            params.add(new BasicNameValuePair("firebase_number", firebase_number));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(HttpURLin, "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    /*Intent ii = new Intent(Connect_with_itrack.this, OptionsFragment.class);
                    startActivity(ii);

                     */
                    finish();
                } else {

                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String message1) {
            pDialog.dismiss();
            if (message1 != null) {
                Toast.makeText(Connect_with_itrack.this, message1, Toast.LENGTH_LONG).show();
            }
        }

    }
}
