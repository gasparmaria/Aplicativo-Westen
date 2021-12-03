package com.example.westen.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
}