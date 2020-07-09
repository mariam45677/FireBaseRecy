package com.example.last;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Edit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        final String name = getIntent().getStringExtra("name");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();

        final EditText studentNameEt =  findViewById(R.id.cc);
        final EditText studentAvgEt =   findViewById(R.id.ffe);
        final Query query = ref.child("students").orderByChild("name").equalTo(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Student student = snapshot.getValue(Student.class);
                    studentNameEt.setText(student.getName());
                    studentAvgEt.setText(String.valueOf(student.getAverage()));

            }}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Button saveButton =  findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Student student = new Student();
                student.setName(studentNameEt.getText().toString());
               student.setAverage(Integer.parseInt(studentAvgEt.getText().toString()));
                final Query query1 = ref.child("students").orderByChild("name").equalTo(name);
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            snapshot.getRef().setValue(student);

                    }}

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });



    }
}
