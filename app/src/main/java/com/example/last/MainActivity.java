package com.example.last;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Student> studentList = new ArrayList<>();
    StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference ref = database.getReference();
        //Student student = new Student();
        //student.setName("nada");
       // student.setAverage(88);
     //   ref.child("Student").push().setValue(student);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
       DatabaseReference ref = database.getReference();
       ref.child("Student").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   Student student = snapshot.getValue(Student.class);
                   studentList.add(student);
                   adapter.notifyDataSetChanged();
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
        final RecyclerView recyclerView =  findViewById(R.id.rview);
        adapter = new StudentAdapter(MainActivity.this, studentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);




    }
}
