package com.example.westen.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.example.westen.Adapters.GridProjetosAdapter;
import com.example.westen.Cliente;
import com.example.westen.Conexao;
import com.example.westen.Projeto;
import com.example.westen.R;

import java.util.List;

public class ProjetoListarActivity extends AppCompatActivity {

    GridView gridProjetos;
    List<Cliente> listaClientes;
    List<Projeto> listaProjetos;

    private static Conexao conexao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projeto_listar);

        gridProjetos = (GridView) findViewById(R.id.grid_projetos);
        GridProjetosAdapter gridAdapter = new GridProjetosAdapter(ProjetoListarActivity.this, R.layout.grid_item_projetos, listaClientes, listaProjetos);
        gridProjetos.setAdapter(gridAdapter);

        String selectQuery = "SELECT tbCliente.ClienteNome, " +
                                    "tbCliente.ClienteLogo," +
                                    "tbProjeto.ProjetoServico" +
                              "FROM tbProjeto" +
                              "INNER JOIN tbCliente " +
                                "ON tbCliente.ClienteCNPJ = tbProjeto.FK_ClienteCNPJ;";

        Cursor cursor = ProjetoListarActivity.conexao.getNovaQuery(selectQuery);
        while(cursor.moveToNext())
        {
            String nome = cursor.getString(0);
            byte[] imagem = cursor.getBlob(1);
            String servico = cursor.getString(2);

            listaClientes.add(new Cliente(nome, imagem));
            listaProjetos.add(new Projeto(servico));
        }
        gridAdapter.notifyDataSetChanged();
    }

}