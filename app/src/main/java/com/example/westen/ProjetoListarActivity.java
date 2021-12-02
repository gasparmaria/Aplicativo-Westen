package com.example.westen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.westen.databinding.ActivityProjetoListarBinding;

public class ProjetoListarActivity extends AppCompatActivity {

    GridView gridProjetos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projeto_listar);

        gridProjetos = (GridView) findViewById(R.id.grid_projetos);

        String[] nomesClientes = {"Dragon Sushi", "Recipily", "Otma", "Mavio"};
        String[] nomesServicos = {"Sistema", "Site", "Mobile", "IoT"};
        int[] imagens = {R.drawable.ic_desenvolvendo,
                R.drawable.ic_adicionar,
                R.drawable.ic_deletar,
                R.drawable.ic_editar};


        GridAdapter gridAdapter = new GridAdapter(ProjetoListarActivity.this, nomesClientes, nomesServicos, imagens);
        gridProjetos.setAdapter(gridAdapter);


        gridProjetos.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(ProjetoListarActivity.this,
                    "Clicou em: " + nomesClientes[position],
                    Toast.LENGTH_SHORT);
        });
    }
}