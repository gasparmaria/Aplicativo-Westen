package com.example.westen.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.westen.Adapters.GridProjetosAdapter;
import com.example.westen.Adapters.ListViewFuncionarioAdapter;
import com.example.westen.Cliente;
import com.example.westen.Conexao;
import com.example.westen.DAO.FuncionarioDAO;
import com.example.westen.Funcionario;
import com.example.westen.Projeto;
import com.example.westen.R;
import com.example.westen.databinding.ActivityFuncionarioListarBinding;

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
        listViewFuncionarios = (ListView) findViewById(R.id.listviewFuncionarios);

        listaFuncionarios = funcionarioDAO.selectFuncionario();
        ArrayAdapter<Funcionario> adapter = new ArrayAdapter<Funcionario>(getApplicationContext(), android.R.layout.simple_list_item_1, listaFuncionarios);
        listViewFuncionarios.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}