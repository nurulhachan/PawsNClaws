package com.example.pawsnclaws;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnimalAdapter animalAdapter;
    private List<Animal> animalList;
    private DatabaseReference databaseReference;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        animalList = new ArrayList<>();
        animalAdapter = new AnimalAdapter(animalList, this);
        recyclerView.setAdapter(animalAdapter);

        databaseReference = FirebaseDatabase.getInstance("https://pawsnclaws-8a5ba-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("animals");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                animalList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Animal animal = snapshot.getValue(Animal.class);
                    if (animal != null) {
                        Log.d(TAG, "Animal: " + animal.getName());
                        animalList.add(animal);
                    }
                }
                animalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to read data", databaseError.toException());
            }
        });
    }
}

