package com.example.westen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings({"SyntaxError", "CanBeFinal"})
public class ProjetoCadastroActivity extends AppCompatActivity {

    RelativeLayout escolher_membros;
    boolean[] membrosSelecionados;
    ArrayList<Integer> membrosList = new ArrayList<>();
    String[] nomesMembros = {"Otávio", "Maria", "Marco", "Miguel", "Eliza"};
    TextView txtMembrosSelecionados;

    @SuppressWarnings("SyntaxError")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projeto_cadastro);

        escolher_membros = (RelativeLayout) findViewById(R.id.escolher_membros);
        txtMembrosSelecionados = (TextView) findViewById(R.id.txtMembrosSelecionados);

        membrosSelecionados = new boolean[nomesMembros.length];
        
        escolher_membros.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                ProjetoCadastroActivity.this
            );

            builder.setTitle("Selecione os membros");

            builder.setCancelable(false);

            builder.setMultiChoiceItems(nomesMembros, membrosSelecionados, (DialogInterface.OnMultiChoiceClickListener) (dialog, i, b) -> {
                if (b) {
                    membrosList.add(i);
                    Collections.sort(membrosList);
                } else {
                    membrosList.remove(i);
                }
            });

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i){
                    StringBuilder stringBuilder = new StringBuilder();

                    for(int j = 0; j < membrosList.size(); j++){
                        stringBuilder.append(nomesMembros[membrosList.get(j)]);
                        if (j != membrosList.size()-1){
                            stringBuilder.append(", ");
                        }
                    }
                    txtMembrosSelecionados.setText(stringBuilder.toString());
                }
            });

            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i){
                    dialogInterface.dismiss();
                }
            });
        });
    }
}