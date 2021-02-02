package com.example.swamphack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class ForgetPasswordAcitivity extends AppCompatActivity {

    public static final String TAG = "test";
    private EditText mUserName;
    private EditText mUserPassword;
    private EditText mUserAnswer;

    private CollectionReference userInfoCollection = FirebaseFirestore.getInstance().collection("UserInfo");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mUserName = findViewById(R.id.forgetUser);
        mUserPassword = findViewById(R.id.newPsw);
        mUserAnswer = findViewById(R.id.answerQuestion2);

        Button forgetDonebtn = (Button) findViewById(R.id.Donebtn);
        forgetDonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mUserName.getText().toString();
                Log.d(TAG, "onCreate: "+ username);
                String userPassword = mUserPassword.getText().toString();
                String userAnswer = mUserAnswer.getText().toString();
                userInfoCollection.whereEqualTo("userEmail",username).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.size() ==1)
                        {
                            Log.d(TAG, "onSuccess: size_"+queryDocumentSnapshots.size());
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots)
                            {
                                Log.d(TAG, "onSuccess get data: "+ document.getData());
                                //  if forget answer is right
                                if (userAnswer.equals(document.getString("userAnswer")))
                                {
                                    // update password
                                    userInfoCollection.document(username).update("userPassword",userPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: update password");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e(TAG, "onFailure: failing update", e);
                                        }
                                    });


                                    Log.d(TAG, "onSuccess: jump to other activity");
                                    Intent intent = new Intent(ForgetPasswordAcitivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    mUserPassword.setError("wrong password");
                                }
                            }
                        }
                        else
                        {
                            mUserName.setError("wrong Email address");
                            Log.e(TAG, "onSuccess: user name have error");
                        }
                    }
                });



                Intent intent = new Intent(ForgetPasswordAcitivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}