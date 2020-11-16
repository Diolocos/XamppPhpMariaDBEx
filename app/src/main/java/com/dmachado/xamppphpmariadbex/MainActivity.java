package com.dmachado.xamppphpmariadbex;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dmachado.xamppphpmariadbex.Adapters.AdaptadorLista;
import com.dmachado.xamppphpmariadbex.Model.UserM;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    private ListView listView;
    private Button bt_lista;
    private AdaptadorLista adaptadorLista;
    private List<UserM> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lista);
        bt_lista = findViewById(R.id.bt_get_list);

        list = new ArrayList<>();

        bt_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                readUsers();

            }
        });
    }

    private void readUsers() {

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_USERS, null, CODE_GET_REQUEST);
        request.execute();

    }

    @SuppressLint("StaticFieldLeak")
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters

        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        //when the task started displaying a progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressBar.setVisibility(View.GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    //refreshing the herolist after every operation
                    //so we get an updated list
                    //we will create this method right now it is commented
                    //because we haven't created it yet
                    refreshUsersList(object.getJSONArray("users"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }
    private void refreshUsersList(JSONArray users) throws JSONException {
        //clearing previous heroes
        list.clear();

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < users.length(); i++) {
            //getting each hero object
            JSONObject obj = users.getJSONObject(i);

            //adding the hero to the list
            list.add(new UserM(
                    obj.getString("id"),
                    obj.getString("username"),
                    obj.getString("fullname"),
                    obj.getString("email")
            ));
        }

        //creating the adapter and setting it to the listview
        AdaptadorLista adapter = new AdaptadorLista(getApplicationContext(), list);
        listView.setAdapter(adapter);
    }
}