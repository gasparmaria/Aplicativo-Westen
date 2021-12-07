package com.example.westen.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.westen.Conexao;
import com.example.westen.Funcionario;
import com.example.westen.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class PerfilActivity extends AppCompatActivity {

    private static final String FILE_NAME = "usuarioLogado.json";
    ImageView imgPerfil;
    TextView txtFuncionarioNome,
             txtFuncionarioCargo,
             txtFuncionarioDesenvolvendo,
             txtFuncionarioConcluidos;
    Button btnPerfilEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        imgPerfil = findViewById(R.id.imgPerfil);
        txtFuncionarioNome = findViewById(R.id.txtFuncionarioNome);
        txtFuncionarioCargo = findViewById(R.id.txtFuncionarioCargo);
        txtFuncionarioDesenvolvendo = findViewById(R.id.txtFuncionarioEmAndamento);
        txtFuncionarioConcluidos = findViewById(R.id.txtFuncionarioConcluido);
        btnPerfilEditar = findViewById(R.id.btnPerfilEditar);

        Gson gson = new Gson();
        String funcionarioJson = lerDados();
        Funcionario funcionario = gson.fromJson(funcionarioJson, Funcionario.class);

        byte[] imgFuncionario = funcionario.getFuncionarioImagem();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgFuncionario, 0, imgFuncionario.length);
        imgPerfil.setImageBitmap(bitmap);

        txtFuncionarioNome.setText(funcionario.getFuncionarioNome());
        txtFuncionarioCargo.setText(funcionario.getFuncionarioCargo());

        Conexao conexao = new Conexao(getApplicationContext());
        String selectQuery = "SELECT COUNT(ProjetoID) " +
                            "FROM tbProjeto " +
                            "INNER JOIN tbFuncionarioProjeto " +
                                "ON tbFuncionarioProjeto.FK_ProjetoID = tbProjeto.ProjetoID " +
                            "WHERE tbFuncionarioProjeto.FK_FuncionarioCPF = ? AND tbProjeto.ProjetoStatus = ? " +
                            "LIMIT 1 ";

        try{
            Cursor cursor = conexao.getReadableDatabase().rawQuery(selectQuery, new String[]{funcionario.getFuncionarioNome(), "Em andamento"});

            while (cursor.moveToNext()) {
                txtFuncionarioDesenvolvendo.setText(cursor.getString(0));
            }
        }
        catch (Exception e){

        }

        try{
            Cursor cursor = conexao.getReadableDatabase().rawQuery(selectQuery, new String[]{funcionario.getFuncionarioNome(), "Concluido"});

            while (cursor.moveToNext()) {
                txtFuncionarioConcluidos.setText(cursor.getString(0));
            }
        }
        catch (Exception e){

        }

        btnPerfilEditar.setOnClickListener(v ->{
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            finish();
        });
    }

    // ler dados json
    private String lerDados() {
        FileInputStream fis;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // menu
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