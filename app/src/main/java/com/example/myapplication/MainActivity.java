package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    private final String base_Url = "https://cex.io/api/last_price/BTC/";

    TextView priceTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        priceTextView = findViewById(R.id.priceLabel);
        Spinner spinner = findViewById(R.id.currencySpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("BitCoin", " " + parent.getItemAtPosition(position));

                String final_URl = base_Url + parent.getItemAtPosition(position);
                //String new_url = final_URl + json;
                Log.d("Bit Coin", "New URL is:" + final_URl);
                letsDoSomeNetworking(final_URl);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("BitCoin--", "Nothing Selected");

            }
        });
    }

    private void letsDoSomeNetworking(String url) {
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //called when response HTTP status is "200 OK"
                Log.d(" Bit coin ", "JSON: " + response.toString());

                try {
                    String price = response.getString("lprice");
                    Log.d("price:", "Bits:" + price);
                    priceTextView.setText(price);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, java.lang.String responseString, java.lang.Throwable throwable) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)


                Log.d("Bit coin", "Request fail! Status code: " + statusCode);
                Log.d("Bit coin", "Fail response: " + responseString);
                Log.e("ERROR", throwable.toString());
                //Toast.makeText(WeatherController.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

