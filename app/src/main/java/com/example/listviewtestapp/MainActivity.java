package com.example.listviewtestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    MyAdapter myAdapter;
    public static ArrayList<Employee> employeeArrayList = new ArrayList<>();
    String url = "https://reqres.in/api/users?page=2";
    Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.myListView);
        myAdapter = new MyAdapter(this, employeeArrayList);
        listView.setAdapter(myAdapter);
        retrieveData();

    }
    public void retrieveData()
    {
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        employeeArrayList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            JSONObject obj = jsonArray.getJSONObject(0);
                            for (int i = 0; i < jsonArray.length();i++)
                                {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String email = object.getString("email");
                                    String first_name = object.getString("first_name");
                                    String last_name = object.getString("last_name");

                                    Toast.makeText(MainActivity.this, ""+first_name, Toast                                              .LENGTH_SHORT).show();

                                    employee = new Employee(id, email, first_name, last_name);
                                    employeeArrayList.add(employee);
                                    myAdapter.notifyDataSetChanged();

                                }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }



}