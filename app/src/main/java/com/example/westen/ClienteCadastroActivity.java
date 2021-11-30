package com.example.westen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ClienteCadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_cadastro);


        Bairro bairro = new Bairro();

        bairro.getBairroNome(inputText);
        clienteDao(bairro)
    }
}