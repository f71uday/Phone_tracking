package xyz.m1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class SendingData extends AppCompatActivity {
    GPSTracker gps;
    TextView textView_Status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_data);
        Button button = (Button) findViewById(R.id.button);
        final TextView textView_lat = (TextView) findViewById(R.id.textView_lat);
        final TextView textView_long = (TextView) findViewById(R.id.textView_lon);
         textView_Status = (TextView)findViewById(R.id.textView_status);
        LocationManager locationManager = (LocationManager)getApplication().getSystemService(Context.LOCATION_SERVICE);
        Intent intent= getIntent();
        final String PID = intent.getStringExtra("pid");


        String locationProvider = LocationManager.NETWORK_PROVIDER;
        String Lo = LocationManager.GPS_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager = (LocationManager)this.getSystemService(LOCATION_SERVICE);


        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {

            double old_latitude=location.getLatitude();
            double old_longitude=location.getLongitude();
            Log.d("old","lat :  "+old_latitude);
            Log.d("old","long :  "+old_longitude);


        }


        final String CurLat = String.valueOf(location.getLatitude());
        final String Curlongi= String.valueOf(location.getLongitude());
        GetXMLTask task = new GetXMLTask();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               textView_lat.setText(CurLat);
                textView_long.setText(Curlongi);
                GetXMLTask task = new GetXMLTask();
                task.execute(new String[] {"http://192.168.0.8:8080/WebApplication1/location?long="+CurLat+"&"+"lat"+Curlongi });

            }
        });
    }

    public static class GetXMLTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String output = null;
            for (String url : urls) {
                output = getOutputFromUrl(url);
            }
            return output;
        }

        private String getOutputFromUrl(String url) {
            String output = null;
            try {

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                output = EntityUtils.toString(httpEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output;
        }


    }
}