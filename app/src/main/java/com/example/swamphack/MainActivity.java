package com.example.swamphack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    public static final String AUTHOR_KEY = "author";
    public static final String QUOTE_KEY = "quote";
    public static final String TAG = "Check";
    public static final String COURSE_ID = "courseID";
    public static final String COURSE_TIME = "courseTime";
    public static final String STUDENT_ID = "studentID";
    public static final String STUDENT_NAME = "studentName";



    private TextView mQuoteTextView;

    //private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("sampleData/inspiration");

    private CollectionReference UF = FirebaseFirestore.getInstance().collection("University of Florida");
    private CollectionReference userDB = FirebaseFirestore.getInstance().collection("UserInfo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQuoteTextView = findViewById(R.id.textView3);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mDocRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()){
                    String quoteText = value.getString(QUOTE_KEY);
                    String authorText = value.getString(AUTHOR_KEY);
                    mQuoteTextView.setText("\"" + quoteText + "\"----" +authorText);
                }
                else if (error != null)
                {
                    Log.w(TAG, "onEvent: got a exception",error );
                }
            }
        });
    }

    public void toIntroActivity(View view){
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void fetchQuote(View view){

        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String quoteText = documentSnapshot.getString(QUOTE_KEY);
                    String authorText = documentSnapshot.getString(AUTHOR_KEY);
                    mQuoteTextView.setText("\"" + quoteText + "\"----" +authorText);
                }
            }
        });
    }

    public void createData(View view){

        for (int i = 0; i < 20; i++) {
            int index = i+3000;
            String courseID = "ABE" + Integer.toString(index);
            double random = Math.random();
            int hour = (int) (22 * random);
            String courseTime = Integer.toString(hour) + ":00 ~ " + Integer.toString(hour+1) + ":15";
            Map<String,Object> courseInfo = new HashMap<String, Object>();
            courseInfo.put(COURSE_ID, courseID);
            courseInfo.put(COURSE_TIME, courseTime);

            
            UF.document(courseID).set(courseInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccess: create data success!");
                }
            });
        }

    }

    public void createUserData(View view){
        for (int i = 0; i < 20; i++) {
            int index = i *13+30626000;
            String studentID = Integer.toString(index);
            String studentName = "Student_" + i;
            Map<String,Object> studentInfo = new HashMap<String, Object>();
            ArrayList<String> courses = new ArrayList<>();

            Log.d(TAG, "createUserData: in here");

            studentInfo.put(STUDENT_ID, studentID);
            studentInfo.put(STUDENT_NAME, studentName);
            for (int j = 0; j < 4; j++) {
                int id = i+3+j+3000;
                String courseID = "ABE" + Integer.toString(id);
                courses.add(courseID);

            }

            studentInfo.put(COURSE_ID,courses);


            userDB.document(studentID).set(studentInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccess: create user data success!");
                }
            });

        }
    }

    public void saveQuote(View view){
        
        EditText quoteView = findViewById(R.id.textView);
        EditText authorView = findViewById(R.id.textView2);
        String quoteText = quoteView.getText().toString();
        String authorText = authorView.getText().toString();

        if (quoteText.isEmpty() || authorText.isEmpty()){return;}

        Map<String,Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put(QUOTE_KEY, quoteText);
        dataToSave.put(AUTHOR_KEY, authorText);
        
        /*// new way
        db.collection("user").add(dataToSave).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "onSuccess: new way to save");
            }
        });*/
        
        // old way
        mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: success save document!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "onFailure: not success save document!!!!",e);
            }
        });
    }
}