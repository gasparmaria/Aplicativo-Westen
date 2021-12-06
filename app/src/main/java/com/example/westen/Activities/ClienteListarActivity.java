package com.example.westen.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.westen.Adapters.ListViewClienteAdapter;
import com.example.westen.Cliente;
import com.example.westen.DAO.ClienteDAO;
import com.example.westen.R;

import java.util.ArrayList;

public class ClienteListarActivity extends AppCompatActivity {

    ListView listViewClientes;
    ArrayList<Cliente> listaClientes;
    ListViewClienteAdapter adapter = null;
    ClienteDAO clienteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_listar);

        clienteDAO = new ClienteDAO(this);
        listViewClientes = (ListView) findViewById(R.id.listviewClientes);
        listaClientes = (ArrayList<Cliente>) clienteDAO.selectCliente();
        adapter = new ListViewClienteAdapter(this, R.layout.listview_item, listaClientes);
        listViewClientes.setAdapter(adapter);
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