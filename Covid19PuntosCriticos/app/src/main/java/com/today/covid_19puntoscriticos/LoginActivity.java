package com.today.covid_19puntoscriticos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.today.covid_19puntoscriticos.Main.MainActivity;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth firebaseAuth;

    private Button btn_registro;
    private EditText email;
    private EditText password;
    private Button btn_login;
    private ProgressDialog dialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();
        email=(EditText) findViewById(R.id.loginEmail);
        password=(EditText) findViewById(R.id.loginPassword);
        btn_registro = (Button) findViewById(R.id.btn_registro);
        btn_login =(Button) findViewById(R.id.btn_login);
        dialog= new ProgressDialog(LoginActivity.this);




        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setTitle(getResources().getString(R.string.saving));
                dialog.setCancelable(false);
                dialog.show();
                String _email = email.getText().toString();
                final String passwordText = password.getText().toString();
                if (_email.isEmpty()) {
                    dialog.dismiss();
                    email.setError("Por favor ingresa un email");
                    email.requestFocus();
                } else if (passwordText.isEmpty()) {
                    dialog.dismiss();
                    password.setError("Por favor ingresa una contraseña");
                    password.requestFocus();
                } else if (passwordText.isEmpty() && _email.isEmpty()) {
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Campos vacíos favor de llenar", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(_email, passwordText).addOnCompleteListener(
                            LoginActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        dialog.dismiss();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                    );
                }
            }
        });


        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(i);


            }
        });


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        };


    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}
