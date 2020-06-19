package com.example.trackstat;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mLoadingProgress;
    private RecyclerView rvCountries;

    //for date
    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rvCountries = findViewById(R.id.rv_countries);
        //create a new layout manager
        LinearLayoutManager coutriesLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        //now set layout manager for the recycler view
        rvCountries.setLayoutManager(coutriesLayoutManager);

        //display current date
        dateTimeDisplay = (TextView)findViewById(R.id.text_date_display);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        dateTimeDisplay.setText(date);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mLoadingProgress = findViewById(R.id.pb_loading);


        try {
            URL mUrl = ApiUtil.buildBetterUrl("","");
            new CaseQuaryTask().execute(mUrl);
        }catch (Exception e){
            Log.e("Error building URL", e.getMessage());
        }


    }

    public class CaseQuaryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String quaryResult = null;
            try {
                quaryResult = ApiUtil.getJason(searchUrl);
            }catch (IOException e){
                Log.e("REMEMBER THIS ERROR", e.getMessage());
            }
            return quaryResult;
        }

        @Override
        protected void onPostExecute(String result) {
//            TextView displayResult = findViewById(R.id.displayTv);
//            TextView displayResult = findViewById(R.id.displayTv);
            //show and hide the error text view according to need
            TextView tvError = findViewById(R.id.textViewError);
            mLoadingProgress.setVisibility(View.INVISIBLE);
            if (result == null){
                rvCountries.setVisibility(View.INVISIBLE);
                tvError.setVisibility(View.VISIBLE);
            }else{
                rvCountries.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.INVISIBLE);
            }
//            displayResult.setText(result);
            //display formated contents instead of raw, as used above
            ArrayList<Countries> countries = ApiUtil.getCountryFromJson(result);
         /**   String resultString = "";
            for (Countries country: countries
                 ) {
                resultString = resultString + "Country = " + country.countryName + "\n" + "Total cases = " + country.totalCases + "\n" +
                     "Total deaths = " + country.deaths + "\n" + "Total recovered = " + country.recovered + "\n\n";
            } */
//            displayResult.setText(resultString);
            //remove the above an use a recycler view to display data instead
            CountryAdapter adapter = new CountryAdapter(countries);
            rvCountries.setAdapter(adapter);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
