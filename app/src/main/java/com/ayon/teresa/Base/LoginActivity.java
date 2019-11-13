package com.ayon.teresa.Base;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ayon.teresa.Home;
import com.ayon.teresa.MainActivity;
import com.ayon.teresa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    private TextView register_redirect;
    private FirebaseAuth fAuth;
    private EditText login_mail;
    private EditText login_password;
    private ProgressBar login_progressBar;
    private Button button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_mail=findViewById(R.id.login_mail);
        login_password=findViewById(R.id.login_password);
        login_progressBar=findViewById(R.id.login_progressBar);
        button_login=findViewById(R.id.button_login);
        fAuth=FirebaseAuth.getInstance();
        register_redirect = findViewById(R.id.register_redirect);


        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), Home.class));
            finish();
        }

        button_login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String email=login_mail.getText().toString().trim();
                String password=login_password.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    login_mail.setError("Email is required!");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    login_password.setError("Password is required!");
                    return;
                }
                if(password.length()<6){
                    login_password.setError("Password must be contained atleast 6 characters");
                    return;
                }

                login_progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Logged in Successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Error! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        register_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });




    }
}
