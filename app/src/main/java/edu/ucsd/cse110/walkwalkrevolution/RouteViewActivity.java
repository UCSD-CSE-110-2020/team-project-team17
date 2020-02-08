package edu.ucsd.cse110.walkwalkrevolution;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.walkwalkrevolution.adapter.RecycleViewAdapter;
import edu.ucsd.cse110.walkwalkrevolution.model.Item;

public class RouteViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    List<Item> items = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routeview);

        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        for(int i=0; i< 20; i++)
        {
            Item stuff = new Item("Test route " + (i+1));
            items.add(stuff);
        }

        RecycleViewAdapter adapter = new RecycleViewAdapter(items);
        recyclerView.setAdapter(adapter);


    }



}
