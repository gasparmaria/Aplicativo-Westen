package com.example.westen.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.example.westen.Adapters.GridProjetosAdapter;
import com.example.westen.Cliente;
import com.example.westen.Conexao;
import com.example.westen.Projeto;
import com.example.westen.R;

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
                "tbProjeto.ProjetoServico " +
                "FROM tbProjeto " +
                "INNER JOIN tbCliente " +
                "ON tbCliente.ClienteCNPJ = tbProjeto.FK_ClienteCNPJ;";

        try {
            Cursor cursor = conexao.getNovaQuery("SELECT * FROM tbProjeto INNER JOIN tbCliente ON tbCliente.ClienteCNPJ = tbProjeto.FK_ClienteCNPJ");

            while(cursor.moveToNext())
            {
                String nome = cursor.getString(0);
                byte[] imagem = cursor.getBlob(1);
                String servico = cursor.getString(2);

                listaClientes.add(new Cliente(nome, imagem));
                listaProjetos.add(new Projeto(servico));

                Toast.makeText(getApplicationContext(), nome, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e)
        {
            byte[] anotherbyte = {1, 2};
            listaClientes.add(new Cliente("error", anotherbyte));
            listaProjetos.add(new Projeto("IoT"));
        }
        finally {
            gridProjetos = (GridView) findViewById(R.id.grid_projetos);
            GridProjetosAdapter gridAdapter = new GridProjetosAdapter(ProjetoListarActivity.this, R.layout.grid_item_projetos, listaClientes, listaProjetos);
            gridProjetos.setAdapter(gridAdapter);
            gridAdapter.notifyDataSetChanged();
        }
    }

}