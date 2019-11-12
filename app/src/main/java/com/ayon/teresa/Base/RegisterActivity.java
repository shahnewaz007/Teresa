package com.ayon.teresa.Base;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ayon.teresa.MainActivity;
import com.ayon.teresa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    String[] gender = { "Male", "Female"};
    private TextView login_redirect;
    private Button buttonRegister;
    private EditText Name;
    private EditText Age;
    private Spinner spinner1;
    private EditText user_phone;
    private EditText Address;
    private EditText Guardian_name;
    private EditText guardian_phone;
    private EditText Email;
    private EditText Password;
    private EditText ConfirmPassword;
    private FirebaseAuth fAuth;
    private ProgressBar progressBarRegister;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonRegister=findViewById(R.id.buttonRegister);
        Name=findViewById(R.id.Name);
        Age=findViewById(R.id.Age);
        spinner1=findViewById(R.id.spinner1);
        user_phone=findViewById(R.id.user_phone);
        Address=findViewById(R.id.Address);
        Guardian_name=findViewById(R.id.Guardian_name);
        guardian_phone=findViewById(R.id.guardian_phone);
        Email=findViewById(R.id.Email);
        Password=findViewById(R.id.Password);
        ConfirmPassword=findViewById(R.id.ConfirmPassword);
        fAuth=FirebaseAuth.getInstance();
        progressBarRegister=findViewById(R.id.progressBarRegister);

        mDatabase= FirebaseDatabase.getInstance().getReference();

        login_redirect = findViewById(R.id.login_redirect);


        buttonRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String email=Email.getText().toString().trim();
                String password=Password.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Email.setError("Email is required!");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Password.setError("Password is required!");
                    return;
                }
                if(password.length()<6){
                    Password.setError("Password must be contained atleast 6 characters");
                    return;
                }

                 progressBarRegister.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Error! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        login_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });




        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        //spin.setOnItemSelectedListener(this);
    }
}
