package com.tranchau.manageuse.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.tranchau.manageuse.utils.Constant;
import com.tranchau.manageuse.utils.ScreenUtils;
import com.tranchau.manageuse.utils.SharePref;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvCreateAccount, tvForgotPass;
    private EditText edtUserName, edtPassword;
    private Button btnLogIn;

    private FirebaseAuth mAuth;
    private DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.fullScreen(this);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)‌​;
        setContentView(R.layout.activity_login);
        Log.d("LOG", "onCreate");
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();

        initWidget();
        onClickWidget();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("LOG", "onStart");
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
        if (currentUser != null) {
            edtUserName.setText(currentUser.getEmail());
            //startActivity(new Intent(this, MainActivity.class));
        }
    }


    @Override
    protected void onResume() {
        Log.d("LOG", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LOG", "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LOG", "onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LOG", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LOG", "onDestroy");
    }

    private void onClickWidget() {
        tvCreateAccount.setOnClickListener(this);
        tvForgotPass.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);
    }

    private void initWidget() {
        tvCreateAccount = findViewById(R.id.tvNewAccount);
        tvForgotPass = findViewById(R.id.tvForgotPassword);
        btnLogIn = findViewById(R.id.btnLogIn);
        edtPassword = findViewById(R.id.edtPassword);
        edtUserName = findViewById(R.id.edtUserName);
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
            case R.id.tvNewAccount:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.tvForgotPassword:
                break;
            case R.id.btnLogIn:
                String email = edtUserName.getText().toString();
                String password = edtPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    finish();
                                    startActivity(new Intent(LogInActivity.this, MainActivity.class));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LogInActivity.this, "Lỗi đăng nhập, vui long thử lại sau!", Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
                break;
        }
    }
}
