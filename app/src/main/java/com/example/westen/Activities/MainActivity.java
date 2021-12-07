package com.example.westen.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.westen.Adapters.GridProjetosAdapter;
import com.example.westen.Cliente;
import com.example.westen.Conexao;
import com.example.westen.Funcionario;
import com.example.westen.Projeto;
import com.example.westen.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "usuarioLogado.json";
    List<Cliente> listaClientesConcluidos = new ArrayList<>();
    List<Cliente> listaClientesNaoIniciados = new ArrayList<>();
    List<Cliente> listaClientesEmAndamento = new ArrayList<>();
    List<Projeto> listaProjetosConcluidos = new ArrayList<>();
    List<Projeto> listaProjetosNaoIniciados = new ArrayList<>();
    List<Projeto> listaProjetosEmAndamento = new ArrayList<>();
    GridView gridProjetos;
    TextView txtFuncionarioEmAndamento,
             txtFuncionarioNaoIniciado,
             txtFuncionarioConcluido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtFuncionarioEmAndamento = findViewById(R.id.txtFuncionarioEmAndamento);
        txtFuncionarioNaoIniciado = findViewById(R.id.txtFuncionarioNaoIniciado);
        txtFuncionarioConcluido = findViewById(R.id.txtFuncionarioConcluido);

        Gson gson = new Gson();
        String funcionarioJson = lerDados();
        Funcionario funcionario = gson.fromJson(funcionarioJson, Funcionario.class);

        Conexao conexao = new Conexao(getApplicationContext());
        String selectQuery =
            "SELECT tbCliente.ClienteNome, " +
                    "tbCliente.ClienteLogo, " +
                    "tbProjeto.* " +
                    "FROM tbProjeto " +
                    "INNER JOIN tbCliente " +
                    "ON tbCliente.ClienteCNPJ = tbProjeto.FK_ClienteCNPJ " +
                    "INNER JOIN tbFuncionarioProjeto " +
                    "ON tbFuncionarioProjeto.FK_ProjetoID = tbProjeto.ProjetoID " +
                    "WHERE tbFuncionarioProjeto.FK_FuncionarioCPF = ? AND tbProjeto.ProjetoStatus = ? " +
                    "ORDER BY tbProjeto.ProjetoID DESC " +
                    "LIMIT 6 ";

        try {
            Cursor cursor = conexao.getReadableDatabase().rawQuery(selectQuery, new String[]{funcionario.getFuncionarioNome(), "Em andamento"});

            if(cursor.getCount() <= 0){
                txtFuncionarioEmAndamento.setVisibility(View.VISIBLE);
            }
            else {
                while (cursor.moveToNext()) {
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

                    listaClientesEmAndamento.add(new Cliente(nome, imagem));
                    listaProjetosEmAndamento.add(projeto);
                }
            }
        }
        catch (Exception e) {

        }
        finally {
            gridProjetos = (GridView) findViewById(R.id.grid_projetosConcluidos);
            GridProjetosAdapter gridAdapter = new GridProjetosAdapter(MainActivity.this, R.layout.grid_item_projetos, listaClientesEmAndamento, listaProjetosEmAndamento);
            gridProjetos.setAdapter(gridAdapter);
            gridAdapter.notifyDataSetChanged();
        }

        try {
            Cursor cursor = conexao.getReadableDatabase().rawQuery(selectQuery, new String[]{funcionario.getFuncionarioNome(), "Não iniciado"});

            if(cursor.getCount() <= 0){
                txtFuncionarioNaoIniciado.setVisibility(View.VISIBLE);
            }
            else {
                while (cursor.moveToNext()) {
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

                    listaClientesNaoIniciados.add(new Cliente(nome, imagem));
                    listaProjetosNaoIniciados.add(projeto);
                }
            }
        }
        catch (Exception e) {

        }
        finally {
            gridProjetos = (GridView) findViewById(R.id.grid_projetosEmAndamento);
            GridProjetosAdapter gridAdapter = new GridProjetosAdapter(MainActivity.this, R.layout.grid_item_projetos, listaClientesNaoIniciados, listaProjetosNaoIniciados);
            gridProjetos.setAdapter(gridAdapter);
            gridAdapter.notifyDataSetChanged();
        }

        try {
            Cursor cursor = conexao.getReadableDatabase().rawQuery(selectQuery, new String[]{funcionario.getFuncionarioNome(), "Concluído"});

            if(cursor.getCount() <= 0){
                txtFuncionarioConcluido.setVisibility(View.VISIBLE);
            }
            else {
                while (cursor.moveToNext()) {
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

                    listaClientesConcluidos.add(new Cliente(nome, imagem));
                    listaProjetosConcluidos.add(projeto);
                }
            }
        }
        catch (Exception e) {

        }
        finally {
            gridProjetos = (GridView) findViewById(R.id.grid_projetosNaoIniciados);
            GridProjetosAdapter gridAdapter = new GridProjetosAdapter(MainActivity.this, R.layout.grid_item_projetos, listaClientesConcluidos, listaProjetosConcluidos);
            gridProjetos.setAdapter(gridAdapter);
            gridAdapter.notifyDataSetChanged();
        }
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