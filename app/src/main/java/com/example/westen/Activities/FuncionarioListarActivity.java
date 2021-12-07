package com.example.westen.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.example.westen.Adapters.ListViewFuncionarioAdapter;
import com.example.westen.DAO.FuncionarioDAO;
import com.example.westen.Funcionario;
import com.example.westen.R;
;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioListarActivity extends AppCompatActivity {

    ListView listViewFuncionarios;
    List<Funcionario> listaFuncionarios;
    FuncionarioDAO funcionarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario_listar);

        funcionarioDAO = new FuncionarioDAO(getApplicationContext());
        listaFuncionarios = funcionarioDAO.selectFuncionario();

        listViewFuncionarios = (ListView) findViewById(R.id.listviewFuncionarios);

        ListViewFuncionarioAdapter adapter = new ListViewFuncionarioAdapter(this, R.layout.listview_item, listaFuncionarios);
        listViewFuncionarios.setAdapter(adapter);
    }
}