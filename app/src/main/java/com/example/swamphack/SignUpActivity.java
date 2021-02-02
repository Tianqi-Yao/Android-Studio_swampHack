package com.example.swamphack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "test";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_PASSWORD = "userPassword";
    public static final String USER_Q = "userQuestion";
    public static final String USER_A = "userAnswer";

    private EditText mUserEmail;
    private EditText mUserPassword;
    private EditText mUserRePassword;
    private EditText mUserQ;
    private EditText mUserA;

    private String userEmail;
    private String userPassword;
    private String userRePassword;
    private String userQ;
    private String userA;

    private CollectionReference userInfoDB = FirebaseFirestore.getInstance().collection("UserInfo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUserEmail = findViewById(R.id.signupEmail);
        mUserPassword = findViewById(R.id.signupPsw);
        mUserRePassword =findViewById(R.id.signupPswrept);
        mUserQ = findViewById(R.id.signupQ);
        mUserA = findViewById(R.id.signupA);



        //after sign up, click I'm Done to go back to the login page
        Button signupDonebtn = (Button) findViewById(R.id.signupBtn);
        signupDonebtn.setOnClickListener(new View.OnClickListener() {
          @Override
           public void onClick(View v) {

              userEmail = mUserEmail.getText().toString();
              userPassword = mUserPassword.getText().toString();
              userRePassword = mUserRePassword.getText().toString();
              userQ = mUserQ.getText().toString();
              userA = mUserA.getText().toString();


              Log.d(TAG, "onClick: " + userPassword);
              Log.d(TAG, "onClick: " + userRePassword);

              Log.d(TAG, "onClick: in onclick");
              if (userPassword.equals(userRePassword))
              {
                  Log.d(TAG, "onClick: in password");
                  Map<String,Object> userInfo = new HashMap<>();
                  userInfo.put(USER_EMAIL,userEmail);
                  userInfo.put(USER_PASSWORD,userPassword);
                  userInfo.put(USER_Q,userQ);
                  userInfo.put(USER_A,userA);

                  userInfoDB.document(userEmail).set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void aVoid) {
                          Log.d(TAG, "onSuccess: add a new user");
                      }
                  });
              }
              else{
                  Log.e(TAG, "onClick: password error");

              }

               Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
               startActivity(intent);
            }
        });
    }
}

