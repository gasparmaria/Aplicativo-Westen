package com.example.westen.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.westen.R;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
    }

    public void abrirHome(View view){
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }
    public void abrirProjeto(View view){
        startActivity(new Intent(getBaseContext(), ProjetoListarActivity.class));
        finish();
    }
    public void abrirFuncionario(View view){
        startActivity(new Intent(getBaseContext(), FuncionarioListarActivity.class));
        finish();
    }
    public void abrirCliente(View view){
        startActivity(new Intent(getBaseContext(), ClienteListarActivity.class));
        finish();
    }
    public void abrirPerfil(View view){
        startActivity(new Intent(getBaseContext(), PerfilActivity.class));
        finish();
    }
}