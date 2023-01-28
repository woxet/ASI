package agenda.synchro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*String[] items = new String[]{"John", "Jane", "Michael", "Emily", "David", "Jessica", "Daniel", "Sophia", "William", "Isabella"};
        ListView listView = findViewById(R.id.list_view);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, AppointmentDetailsActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });*/

        FloatingActionButton fab = findViewById(R.id.fabAddRdv);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, addRdvActivity.class);
                startActivity(intent);
            }
        });

        CalendarView calendarView;
        ListView listView;

        // Initialisation des éléments graphiques
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        listView = (ListView) findViewById(R.id.list_view);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Récupération de la date sélectionnée
                        String date = year + "-" + (month + 1) + "-" + day;

                        HttpURLConnection urlConnection = null;
                        try {
                            URL url = new URL("http://192.168.1.10:8080/ASI_war/rest/rdv/getdate/" + date);
                            Log.i("Exchange-JSON", "URL == " + url);

                            urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestMethod("GET");

                            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                            Scanner scanner = new Scanner(in);
                            final String jsonString = scanner.nextLine();
                            Log.i("Exchange-JSON", "Result == " + jsonString);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        // Traitement de la réponse
                                        JSONArray jsonArray = new JSONArray(jsonString);
                                        Map<Integer, String> rdvMap = new HashMap<>();

                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            int idRDV = jsonObject.getInt("idRDV");
                                            String name = jsonObject.getString("name");
                                            rdvMap.put(idRDV, name);
                                        }

                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, new ArrayList<>(rdvMap.values()));
                                        listView.setAdapter(adapter);
                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                String name = (String) parent.getItemAtPosition(position);
                                                int idRDV = -1;
                                                for (Map.Entry<Integer, String> entry : rdvMap.entrySet()) {
                                                    if (entry.getValue().equals(name)) {
                                                        idRDV = entry.getKey();
                                                        break;
                                                    }
                                                }
                                                Intent intent = new Intent(MainActivity.this, AppointmentDetailsActivity.class);
                                                intent.putExtra("idRDV", idRDV);
                                                startActivity(intent);
                                            }
                                        });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            in.close();
                        } catch (IOException e) {
                            Log.e("Exchange-JSON", "Cannot found HTTP server", e);
                        } finally {
                            if (urlConnection != null) urlConnection.disconnect();
                        }
                    }
                }).start();
            }
        });
    }
}

