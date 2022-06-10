package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

class MyTask extends AsyncTask<String, Void, ArrayList<String[]>> {
    @Override
    protected void onPreExecute() {
    }
    @Override
    protected ArrayList<String[]> doInBackground(String... params) {
        ArrayList<String[]> res=new ArrayList <>();
        return res;
    }
    @Override
    protected void onPostExecute(ArrayList<String[]> result) {
        super.onPostExecute(result);

    }
}

public class Theater extends AppCompatActivity {

    TextView tvInfo;
    EditText tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvName = (EditText) findViewById(R.id.editTextTextPersonName);
// calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

    }


    public void onclick(View v) {

        MyTask mt = new MyTask();
        mt.execute(tvName.getText().toString());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



 
    protected ArrayList<String[]> doInBackground(String... params) {
        ArrayList<String[]> res=new ArrayList <>();
        HttpURLConnection myConnection = null;
        try {
            URL mySite = new
                    URL("http://10.0.2.2:8080/json?id=1&name="+params[0]);
            myConnection =
                    (HttpURLConnection) mySite.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i=0;
        try {
            i = myConnection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (i==200) {
            InputStream responseBody=null;
            try {
                responseBody = myConnection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStreamReader responseBodyReader =null;
            try {
                responseBodyReader =
                        new InputStreamReader(responseBody, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            JsonReader jsonReader;
            jsonReader = null;
            jsonReader = new JsonReader(responseBodyReader);
            try {
                jsonReader.beginArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String key=null;
            String value =null;
            while (true) {
                try {
                    if (!jsonReader.hasNext()) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    jsonReader.beginObject();
                } catch (IOException e) {
                    e.printStackTrace();
                };
                String[] str=new String[2];
                int n=0;
                while (true) {
                    try {
                        if (!jsonReader.hasNext()) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        key = jsonReader.nextName();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
// sb.append("\r\n : " +key);
                    try {
                        value = jsonReader.nextString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
// sb.append("\r\n : " +value);
                    str[n]=value;
                    n++;
                }
                try {
                    jsonReader.endObject();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                res.add(str);
            }
            try {
                jsonReader.endArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        myConnection.disconnect();
        return res;
    }

}