package com.taurunium.androidarchitectures.mvc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.taurunium.androidarchitectures.R;

import java.util.ArrayList;
import java.util.List;

//U ovom slucaju
//CountriesController - Controller
//MVCActivity - view
//CountriesService - model

public class MVCActivity extends AppCompatActivity {

    private List<String> listValues = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView list;
    private CountriesController controller;
    private Button retryButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvcactivity);
        setTitle("MVC");

        controller = new CountriesController(this);

        list = findViewById(R.id.list);
        retryButton = (Button)findViewById(R.id.retryButton);
        progressBar = (ProgressBar)findViewById(R.id.progress);

        adapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.listText, listValues);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MVCActivity.this, "Clicked:"+listValues.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setValues(List<String> values){
        listValues.clear();
        listValues.addAll(values);
        retryButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    public static Intent getIntent(Context context){
        return new Intent(context, MVCActivity.class);
    }

    public void onError(){
        Toast.makeText(this, "Unable to get countries", Toast.LENGTH_LONG).show();
        retryButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        list.setVisibility(View.GONE);
    }

    public void onRetry(View view){
        controller.onRefresh();

        retryButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
    }
}