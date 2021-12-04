package com.example.westen.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.westen.Adapters.ListViewFuncionarioAdapter;
import com.example.westen.DAO.FuncionarioDAO;
import com.example.westen.Funcionario;
import com.example.westen.R;
import com.example.westen.databinding.ActivityFuncionarioListarBinding;

import java.util.ArrayList;

public class FuncionarioListarActivity extends AppCompatActivity {


    ListView listViewFuncionarios;
    ArrayList<Funcionario> listaFuncionarios;
    ListViewFuncionarioAdapter adapter = null;
    FuncionarioDAO funcionarioDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario_listar);

        funcionarioDAO = new FuncionarioDAO(this);
        listViewFuncionarios = (ListView) findViewById(R.id.listviewFuncionarios);
        listaFuncionarios = (ArrayList<Funcionario>) funcionarioDAO.selectFuncionario();
        adapter = new ListViewFuncionarioAdapter(this, R.layout.listview_item, listaFuncionarios);
        listViewFuncionarios.setAdapter(adapter);
    }
}