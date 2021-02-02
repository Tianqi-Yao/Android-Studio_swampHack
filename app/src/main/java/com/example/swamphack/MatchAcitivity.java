package com.example.swamphack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MatchAcitivity extends AppCompatActivity {

    public static final String TAG = "test";
    private EditText mUserName;
    private EditText mUserPassword;

    private ListView listView;
    private ClassListAdapter adapter;
    private ArrayList<ClassName> classNameArrayList = new ArrayList<>();

    private CollectionReference schoolDB = FirebaseFirestore.getInstance().collection("University of Florida");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
    }

    public void onClickAdd(View view){

        listView = (ListView) findViewById(R.id.listView);
        EditText intsertedClass = findViewById(R.id.insertClass);

        String className = intsertedClass.getText().toString();


        schoolDB.whereEqualTo("courseID",className).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty())
                {
                    Log.d(TAG, "onSuccess: size_"+queryDocumentSnapshots.size());
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots)
                    {
                        Log.d(TAG, "onSuccess get data: "+ document.getData());
                        ClassName myClass = new ClassName(document.getString("courseID"));

                        classNameArrayList.add(myClass);
                    }
                    adapter = new ClassListAdapter(MatchAcitivity.this, R.layout.adapter_class, classNameArrayList);
                    listView.setAdapter(adapter);
                }
                else
                {
                    intsertedClass.setError("do not have this course!");
                }
            }
        });

        intsertedClass.setText(null);
        intsertedClass.setHint("Search Your Class");
    }

    public void onClickCourse(View view){
        Intent intent = new Intent(MatchAcitivity.this, ClassDescriptionActivity.class);
        startActivity(intent);
    }
    public void onClickClassmate(View view){
        Intent intent = new Intent(MatchAcitivity.this, ClassmateDescriptionActivity.class);
        startActivity(intent);
    }
}

