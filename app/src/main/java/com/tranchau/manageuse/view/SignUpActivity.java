package com.tranchau.manageuse.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tranchau.manageuse.R;
import com.tranchau.manageuse.model.UserModel;
import com.tranchau.manageuse.utils.Constant;
import com.tranchau.manageuse.utils.ScreenUtils;
import com.tranchau.manageuse.utils.SharePref;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "SIGNUP";

    private TextView tvBack;
    private EditText edtUserName, edtPassword;
    private Button btSignUp;

    private FirebaseAuth mAuth;
    private DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.fullScreen(this);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();

        initWidget();
        onClickWidget();
    }

    private void onClickWidget() {
        tvBack.setOnClickListener(this);
        btSignUp.setOnClickListener(this);
    }

    private void initWidget() {
        tvBack = findViewById(R.id.tvBack);
        btSignUp = findViewById(R.id.btnSignUp);
        edtPassword = findViewById(R.id.edtPassword);
        edtUserName = findViewById(R.id.edtUserName);
        validateInput();
    }

    private void validateInput(){

        edtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()){
                    edtUserName.setError("Username is empty.");
                } else {
                    edtUserName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()){
                    edtPassword.setError("Password is empty.");
                } else {
                    edtPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvBack:
                finish();
                startActivity(new Intent(this, LogInActivity.class));
                break;
            case R.id.btnSignUp:
                String username = edtUserName.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                boolean flag = checkInput(username, password);
                Log.d("FLAG", flag+"");
                if (flag){
                    signUpEmail(username, password);
//                    if (username.equals("aa") && password.equals("111111")){
//                        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
//                        SharePref.getInstance().putSharePref(Constant.USERNAME, username);
//                        finish();
//                        startActivity(new Intent(this, LogInActivity.class));
//                    }
                }
                break;
        }
    }

    private boolean checkInput(String username, String password){
        boolean flag;
        if (username.isEmpty()){
            if (password.isEmpty()){
                edtPassword.setError("Password is empty.");
                edtUserName.setError("Username is empty.");
                flag = false;
            } else {
                edtPassword.setError(null);
                edtUserName.setError("Username is empty.");
                flag = false;
            }
        } else {
            edtPassword.setError(null);
            edtUserName.setError(null);
            flag = true;
        }

        if (password.isEmpty()){
            if (username.isEmpty()){
                edtPassword.setError("Password is empty.");
                edtUserName.setError("Username is empty.");
                flag = false;
            } else {
                edtPassword.setError("Password is empty.");
                edtUserName.setError(null);
                flag = false;
            }
        } else {
            edtPassword.setError(null);
            edtUserName.setError(null);
            flag = true;
        }

        return flag;
    }


    private void signUpEmail(final String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
//                            SharePref.getInstance().putSharePref(Constant.USERNAME, email);
                            mData.child("user").child(user.getUid()).setValue(email);
                            finish();
                            startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                            edtUserName.setText(null);
                            edtPassword.setText(null);
                        }

                        // ...
                    }
                });

    }
}
