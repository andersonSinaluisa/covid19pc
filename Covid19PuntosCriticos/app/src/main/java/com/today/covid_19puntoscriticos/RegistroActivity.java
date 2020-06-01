package com.today.covid_19puntoscriticos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.today.covid_19puntoscriticos.Activities.Poll;
import com.today.covid_19puntoscriticos.Main.MainActivity;
import com.today.covid_19puntoscriticos.Slides.PollSlideActivity;

public class RegistroActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button btn_registro;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        email=(EditText) findViewById(R.id.Email);
        password =(EditText) findViewById(R.id.Password);
        btn_registro =(Button) findViewById(R.id.btn_registro);




        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();


                String _email = email.getText().toString();
                String _passwordText = password.getText().toString();


                if (_email.isEmpty()) {
                    email.setError("Por favor ingresa un email");
                    email.requestFocus();
                } else if (_passwordText.isEmpty()) {
                    password.setError("Por favor ingresa un email");
                    password.requestFocus();
                } else if (_passwordText.isEmpty() && _email.isEmpty()) {
                    Toast.makeText(RegistroActivity.this, "Campos vacíos favor de llenar", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(_email, _passwordText).addOnCompleteListener(
                            RegistroActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        System.out.println(task.getException()+"");
                                        Toast.makeText(RegistroActivity.this, "No pudimos registrarte, intentalo de nuevo más tarde", Toast.LENGTH_SHORT).show();
                                    } else {
                                        startActivity(new Intent(RegistroActivity.this, PollSlideActivity.class));
                                    }
                                }
                            }
                    );
                }


            }
        });


    }
}
