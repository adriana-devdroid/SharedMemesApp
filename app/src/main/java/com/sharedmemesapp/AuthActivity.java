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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthActivity extends BaseActivity {

    @BindView(R.id.mBotonAuth)
    Button authButton;

    @BindView(R.id.mBotonActivityRegistrarse)
    Button activityReg;

    @BindView(R.id.editTextUsuario)
    EditText txtUser;

    @BindView(R.id.editTextContrasena)
    EditText txtPass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Intent intent = new Intent(AuthActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else{

        }
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);






    }

    @OnClick(R.id.mBotonActivityRegistrarse)
    public void func(){
        Intent intent = new Intent(this, RegistrarseActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.mBotonAuth)
    public void func2(){
        autenticarse();
    }

    public void autenticarse() {

        String email = txtUser.getText().toString();
        String pass = txtPass.getText().toString();
        if (email.isEmpty()) {
            txtUser.setError("Tienes que completar este campo");
        }
        if (pass.isEmpty()) {
            txtPass.setError("Tienes que completar este campo");
        }
        if (!pass.isEmpty() && !email.isEmpty()) {
            auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"User Login",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AuthActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                }
            });
        }
    }



}


