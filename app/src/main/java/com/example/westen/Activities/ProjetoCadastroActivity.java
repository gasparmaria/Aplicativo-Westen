package com.example.westen.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.westen.Funcionario;
import com.example.westen.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjetoCadastroActivity extends AppCompatActivity {

    RelativeLayout escolher_membros;
    boolean[] membrosSelecionados;
    List<Funcionario> listaFuncionarios;
    ArrayList<Integer> membrosList = new ArrayList<>();
    String[] nomesMembros = {"Otávio", "Maria", "Marco", "Miguel", "Eliza"};
    TextView txtMembrosSelecionados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projeto_cadastro);

        escolher_membros = (RelativeLayout) findViewById(R.id.escolher_membros);
        txtMembrosSelecionados = (TextView) findViewById(R.id.txtMembrosSelecionados);

        membrosSelecionados = new boolean[nomesMembros.length];

        txtMembrosSelecionados.setOnClickListener(view -> {
            AlertDialog.Builder buider = new AlertDialog.Builder(
                    ProjetoCadastroActivity.this
            );
            buider.setTitle("Selecione os membros");

            buider.setCancelable(false);

            buider.setMultiChoiceItems(nomesMembros, membrosSelecionados, (dialogInterface, i, b) -> {
                if(b){
                    membrosList.add(i);
                    Collections.sort(membrosList);
                }
                else{
                    membrosList.remove(i);
                }
            });

            buider.setPositiveButton("OK", (dialogInterface, i) -> {
                StringBuilder stringBuilder = new StringBuilder();

                for(int j=0; j < membrosList.size(); j++){
                    stringBuilder.append(nomesMembros[membrosList.get(j)]);

                    if(j!=membrosList.size()-1){
                        stringBuilder.append(", ");
                    }
                }
                txtMembrosSelecionados.setText(stringBuilder.toString());
            });

            buider.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

            buider.setNeutralButton("Clear All", (dialogInterface, i) -> {
                for(int j=0; j<membrosSelecionados.length; j++){
                    membrosSelecionados[j] = false;
                    membrosList.clear();
                    txtMembrosSelecionados.setText("");
                }
            });

            buider.show();
        });
    }
}