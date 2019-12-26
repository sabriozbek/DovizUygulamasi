package com.example.dovizuygulamasi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView tryText;
    TextView usdText;
    TextView cadText;
    TextView audText;
    TextView jpyText;
    EditText editText;
    Spinner spinner;
    String GeciciPsraBirimi;
    Button button;
   float HesaplamaSonuc;
   float GelenDeger=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tryText = findViewById(R.id.tryText);
        usdText = findViewById(R.id.usdText);
        cadText = findViewById(R.id.cadText);
        audText = findViewById(R.id.audText);
        jpyText = findViewById(R.id.jpyText);
        editText=findViewById(R.id.editText);
        spinner=findViewById(R.id.spinner);
        button=findViewById(R.id.button2);

        ArrayList<String> paraBirimleri=new ArrayList<>();
        paraBirimleri.add("AUD");
        paraBirimleri.add("USD");
        paraBirimleri.add("CAD");
        paraBirimleri.add("JPY");
        paraBirimleri.add("TRY");
        ArrayAdapter paraBirimleriAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,paraBirimleri);
        spinner.setAdapter(paraBirimleriAdapter);
        VerileriGetir();

button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        GeciciPsraBirimi=spinner.getSelectedItem().toString();
        VerileriGetir();


    }
});

    }

    public void verileriGetir(View view) {
        DownloadData downloadData = new DownloadData();
        try {
            String url = "http://data.fixer.io/api/latest?access_key=66f63aaf0c4753475958dadf06daa42d";
            downloadData.execute(url);
        } catch (Exception e) {

        }
    }
    public void VerileriGetir() {
        DownloadData downloadData = new DownloadData();
        try {
            String url = "http://data.fixer.io/api/latest?access_key=66f63aaf0c4753475958dadf06daa42d";
            downloadData.execute(url);
        } catch (Exception e) {

        }
    }

    private class DownloadData extends AsyncTask<String, Void, String> {
        //float sonuc=0;
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //System.out.println("alınan veri : " + s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                //System.out.println(base);

                String rates = jsonObject.getString("rates");
                JSONObject jsonObject1 = new JSONObject(rates);

                String turkLirasi = jsonObject1.getString("TRY");
                tryText.setText("TRY: " + turkLirasi);

                String abdDolari = jsonObject1.getString("USD");
                usdText.setText("USD: " + abdDolari);

                String kanadaDolari = jsonObject1.getString("CAD");
                cadText.setText("CAD: " + kanadaDolari);

                String avustralyaDolari = jsonObject1.getString("AUD");
                audText.setText("AUD: " + avustralyaDolari);

                String japonYeni = jsonObject1.getString("JPY");
                jpyText.setText("JPY: " + japonYeni);
                GelenDeger=Float.parseFloat(jsonObject1.get(GeciciPsraBirimi).toString());
                HesaplamaSonuc=Float.parseFloat( editText.getText().toString())*GelenDeger;
                setTitle("Hesap Sonucu: "+HesaplamaSonuc);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();

                while (data > 0) {
                    char karakter = (char) data;
                    result += karakter;
                    data = reader.read();
                }
                return result;

            } catch (Exception e) {
                return null;
            }
        }
    }
}
