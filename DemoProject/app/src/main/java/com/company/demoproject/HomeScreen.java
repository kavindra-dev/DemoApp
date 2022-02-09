package com.company.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeScreen extends FragmentActivity implements OnMapReadyCallback {

    Button b1,b2;
    RecyclerView recy;
    LinearLayout l1;
    GoogleMap map;
    ArrayList<User> u = new ArrayList<>();
    ArrayList<LatLng> u2 = new ArrayList<LatLng>();
    UserAdapter adapter;
    private long backpressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        b1 =findViewById(R.id.mapV);
        b2 = findViewById(R.id.user);
        recy = findViewById(R.id.recycle);
        l1 = findViewById(R.id.k1);
        recy.setLayoutManager(new LinearLayoutManager(this));

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://aryupay.com/hrportal/public/tracking/viewreport";

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l1.setVisibility(View.VISIBLE);
                recy.setVisibility(View.GONE);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray2 = response.getJSONArray("location");
                            for (int i=0; i<jsonArray2.length(); i++){
                                JSONObject jsonObject2 = jsonArray2.getJSONObject(i);
                                Double d1 = Double.parseDouble(jsonObject2.getString("lat"));
                                Double d2 = Double.parseDouble(jsonObject2.getString("long"));
                                Toast.makeText(HomeScreen.this, d1+", "+d2, Toast.LENGTH_SHORT).show();
                                LatLng lt = new LatLng(d1,d2);
                                u2.add(lt);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HomeScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                requestQueue.add(jsonObjectRequest);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l1.setVisibility(View.GONE);
                recy.setVisibility(View.VISIBLE);

                if(u.size() > 0){
                    u.clear();
                }


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("success");
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String nm = jsonObject.getString("name");
                                String em = jsonObject.getString("email");
                                String ca = jsonObject.getString("created_at");
                                 User us = new User(
                                         nm,em,ca
                                 );
                                 u.add(us);
                            }
                            adapter =new UserAdapter(HomeScreen.this, u);
                            recy.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HomeScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                requestQueue.add(jsonObjectRequest);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        for (int i =0; i<u2.size();i++){
            map.addMarker(new MarkerOptions().position(u2.get(i))).setTitle("Marker");
            map.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            map.moveCamera(CameraUpdateFactory.newLatLng(u2.get(i)));
        }
    }


    @Override
    public void onBackPressed() {

        if (backpressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        } else{
            Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
        }
        backpressedTime = System.currentTimeMillis();
    }
}