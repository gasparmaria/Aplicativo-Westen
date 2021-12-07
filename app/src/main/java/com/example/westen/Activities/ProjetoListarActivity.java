package com.example.westen.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.westen.Adapters.GridProjetosAdapter;
import com.example.westen.Cliente;
import com.example.westen.Conexao;
import com.example.westen.Projeto;
import com.example.westen.Projeto;
import com.example.westen.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProjetoListarActivity extends AppCompatActivity {

    GridView gridProjetos;
    List<Cliente> listaClientes = new ArrayList<>();
    List<Projeto> listaProjetos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projeto_listar);

        Conexao conexao = new Conexao(getApplicationContext());
        String selectQuery = "SELECT tbCliente.ClienteNome, " +
                "tbCliente.ClienteLogo, " +
                "tbProjeto.* " +
                "FROM tbProjeto " +
                "INNER JOIN tbCliente " +
                "ON tbCliente.ClienteCNPJ = tbProjeto.FK_ClienteCNPJ;";

        try {
            Cursor cursor = conexao.getNovaQuery(selectQuery);

            while(cursor.moveToNext())
            {
                String nome = cursor.getString(0);
                byte[] imagem = cursor.getBlob(1);

                Projeto projeto = new Projeto();

                projeto.setProjetoID(cursor.getInt(2));
                projeto.setProjetoDataInicio(cursor.getString(3));
                projeto.setProjetoDataFinal(cursor.getString(4));
                projeto.setProjetoStatus(cursor.getString(5));
                projeto.setProjetoDescricao(cursor.getString(6));
                projeto.setProjetoServico(cursor.getString(7));
                projeto.setFK_ClienteCNPJ(cursor.getString(8));

                listaClientes.add(new Cliente(nome, imagem));
                listaProjetos.add(projeto);
            }
        }
        catch (Exception e) {

        }
        finally {
            gridProjetos = (GridView) findViewById(R.id.grid_projetos);
            GridProjetosAdapter gridAdapter = new GridProjetosAdapter(ProjetoListarActivity.this, R.layout.grid_item_projetos, listaClientes, listaProjetos);
            gridProjetos.setAdapter(gridAdapter);
            gridAdapter.notifyDataSetChanged();
        }


        gridProjetos.setOnItemClickListener((lista, item, position, id) -> {
            Projeto Projeto = (Projeto) gridProjetos.getItemAtPosition(position);
            Intent intentAbrirDetalhes = new Intent(ProjetoListarActivity.this, ProjetoDetalhesActivity.class);
            intentAbrirDetalhes.putExtra("Projeto", (Serializable) Projeto);
            startActivity(intentAbrirDetalhes);
        });
    }

}