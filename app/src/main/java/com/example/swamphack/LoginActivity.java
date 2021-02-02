package com.example.swamphack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "test";
    private EditText mUserName;
    private EditText mUserPassword;

    private CollectionReference userInfoCollection = FirebaseFirestore.getInstance().collection("UserInfo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserName = findViewById(R.id.userName);
        mUserPassword = findViewById(R.id.userPassword);

        //click sign-up and go to sign up page
        Button signUp_button = (Button) findViewById(R.id.signUp_button);
        signUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        Button forget_button = (Button) findViewById(R.id.forget_button);
        forget_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordAcitivity.class);
                startActivity(intent);
            }
        });

    }

    public void onClickLogin(View view){
        String username = mUserName.getText().toString();
        Log.d(TAG, "onCreate: "+ username);
        String userPassword = mUserPassword.getText().toString();

        // check username and password
        userInfoCollection.whereEqualTo("userEmail",username).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.size() ==1)
                {
                    Log.d(TAG, "onSuccess: size_"+queryDocumentSnapshots.size());
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots)
                    {
                        Log.d(TAG, "onSuccess get data: "+ document.getData());
                        if (userPassword.equals(document.getString("userPassword")))
                        {
                            Log.d(TAG, "onSuccess: jump to other activity");
                            Intent intent = new Intent(LoginActivity.this,MatchAcitivity.class);
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



    }



}