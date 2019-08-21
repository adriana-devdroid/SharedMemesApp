package com.sharedmemesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrarseActivity extends BaseActivity {


    @BindView(R.id.mBotonRegistrarse)
    Button Rgs;

    @BindView(R.id.editTextRegUsuario)
    EditText txtUser;


    @BindView(R.id.editTextRegContrasena)
    EditText txtPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.mBotonRegistrarse)
    public void btnReg() {
        crearUsuario();
    }

    public void crearUsuario() {

        String email = txtUser.getText().toString();
        String pass = txtPass.getText().toString();
        if (email.isEmpty()) {
            txtUser.setError("Tienes que completar este campo");
        }
        if (pass.isEmpty()) {
            txtPass.setError("Tienes que completar este campo");
        }
        if (!pass.isEmpty() && !email.isEmpty()) {
            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Se ha creado usuario", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegistrarseActivity.this,AuthActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                }
            });
        }

    }
}
