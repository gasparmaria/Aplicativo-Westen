package com.example.westen.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.westen.Cliente;
import com.example.westen.DAO.ClienteDAO;
import com.example.westen.DAO.FuncionarioDAO;
import com.example.westen.DAO.FuncionarioProjetoDAO;
import com.example.westen.DAO.ProjetoDAO;
import com.example.westen.Funcionario;
import com.example.westen.FuncionarioProjeto;
import com.example.westen.Projeto;
import com.example.westen.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProjetoCadastroActivity extends AppCompatActivity {

    Spinner spinnerCliente, spinnerServico, spinnerStatus;
    Button btnCadastrarProjeto;
    EditText inputProjeto_descricao,
             inputProjeto_dataInicio,
             inputProjeto_dataFinal;

    TextView txtMembrosSelecionados;
    String ProjetoStatus,
            ProjetoDescricao,
            ProjetoServico,
            ProjetoDataInicio,
            ProjetoDataFinal,
            FK_ClienteCNPJ;

    ProjetoDAO projetoDAO;
    ClienteDAO clienteDAO;
    FuncionarioProjetoDAO funcionarioProjetoDAO;
    String[] projetos,
             status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projeto_cadastro);

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO(getApplicationContext());
        String[] nomesMembros = funcionarioDAO.selectTodosNomesFuncionarios();
        txtMembrosSelecionados = (TextView) findViewById(R.id.txtMembrosSelecionados);
        ArrayList<Integer> membrosList = new ArrayList<>();
        boolean[] membrosSelecionados = new boolean[nomesMembros.length];

        inputProjeto_descricao = (EditText) findViewById(R.id.inputProjetos_descricao);
        inputProjeto_dataInicio = (EditText) findViewById(R.id.inputProjetos_dataInicio);
        inputProjeto_dataFinal = (EditText) findViewById(R.id.inputProjetos_dataFinal);
        spinnerCliente = (Spinner) findViewById(R.id.spinnerProjeto_cliente);
        spinnerServico = (Spinner) findViewById(R.id.spinnerProjeto_servico);
        spinnerStatus = (Spinner) findViewById(R.id.spinnerProjeto_status);
        btnCadastrarProjeto = (Button) findViewById(R.id.btnCadastrarProjeto);

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

                for(int j = 0; j < membrosList.size(); j++){
                    stringBuilder.append(nomesMembros[membrosList.get(j)]);

                    if(j != membrosList.size()-1){
                        stringBuilder.append(", ");
                    }
                }
                txtMembrosSelecionados.setText(stringBuilder.toString());
            });

            buider.setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.dismiss());

            buider.setNeutralButton("Limpar", (dialogInterface, i) -> {
                for(int j=0; j<membrosSelecionados.length; j++){
                    membrosSelecionados[j] = false;
                    membrosList.clear();
                    txtMembrosSelecionados.setText("");
                }
            });

            buider.show();
        });

        Intent intent = getIntent();

        carregarSpinnerCliente();
        carregarSpinnerServico();
        carregarSpinnerStatus();

        if(intent != null){
            btnCadastrarProjeto.setText(R.string.txtSalvar);

            Projeto projeto = ((Projeto) intent.getSerializableExtra("Projeto"));
            Cliente cliente = ((Cliente) intent.getSerializableExtra("Cliente"));

            clienteDAO = new ClienteDAO(getApplicationContext());
            List<String> clientesNomes = clienteDAO.selectTodosNomesClientes();
            int posicaoNome;
            posicaoNome = clientesNomes.indexOf(cliente.getClienteNome());

            projetos = getResources().getStringArray(R.array.servico);
            List<String> projetoServicos = new ArrayList<String>(Arrays.asList(projetos));
            int posicaoServico;
            posicaoServico = projetoServicos.indexOf(projeto.getProjetoServico());

            status = getResources().getStringArray(R.array.status);
            List<String> projetoStatus = new ArrayList<String>(Arrays.asList(status));
            int posicaoStatus;
            posicaoStatus = projetoStatus.indexOf(projeto.getProjetoStatus());


            inputProjeto_descricao.setText(projeto.getProjetoDescricao());
            inputProjeto_dataInicio.setText(projeto.getProjetoDataInicio());
            inputProjeto_dataFinal.setText(projeto.getProjetoDataFinal());
            spinnerCliente.setSelection(posicaoNome);
            spinnerServico.setSelection(posicaoServico);
            spinnerStatus.setSelection(posicaoStatus);

            btnCadastrarProjeto.setOnClickListener(v -> {
                ProjetoStatus = spinnerStatus.getSelectedItem().toString();
                ProjetoDescricao = inputProjeto_descricao.getText().toString();
                ProjetoServico = spinnerServico.getSelectedItem().toString();
                ProjetoDataInicio = inputProjeto_dataInicio.getText().toString();
                ProjetoDataFinal = inputProjeto_dataFinal.getText().toString();
                FK_ClienteCNPJ = clienteDAO.selectCNPJPorNome(spinnerCliente.getSelectedItem().toString());

                Projeto updateProjeto = new Projeto();
                updateProjeto.setProjetoDataInicio(ProjetoDataInicio);
                updateProjeto.setProjetoDataFinal(ProjetoDataFinal);
                updateProjeto.setProjetoStatus(ProjetoStatus);
                updateProjeto.setProjetoDescricao(ProjetoDescricao);
                updateProjeto.setProjetoServico(ProjetoServico);

                Cliente updateCliente = new Cliente();
                updateCliente.setClienteCNPJ(FK_ClienteCNPJ);

                projetoDAO = new ProjetoDAO(ProjetoCadastroActivity.this);

                funcionarioProjetoDAO = new FuncionarioProjetoDAO(getApplicationContext());

                int projetoID = projeto.getProjetoID();

                funcionarioProjetoDAO.deleteFuncionarioProjeto(projetoID);

                for (int i = 0; i < membrosList.size(); i++) {
                    String nomeSelecionado = nomesMembros[membrosList.get(i)];
                    String cpf = funcionarioDAO.selectCPFPorNome(nomeSelecionado);

                    FuncionarioProjeto funcionarioProjeto = new FuncionarioProjeto(cpf, projetoID);
                    try {
                        funcionarioProjetoDAO.insertFuncionarioProjeto(funcionarioProjeto);
                    } catch (Exception e) {

                    }
                }
                Toast.makeText(getApplicationContext(), "funcionario projeto funcionando", Toast.LENGTH_SHORT).show();

                updateProjeto.setProjetoID(projeto.getProjetoID());

                try{
                    projetoDAO.updateProjeto(updateProjeto, updateCliente);
                    Toast.makeText(getApplicationContext(), "Edição efetuada com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), ProjetoListarActivity.class));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
        else {
            btnCadastrarProjeto.setOnClickListener(v -> {
                ClienteDAO clienteDAO = new ClienteDAO(getApplicationContext());

                String nomeClienteSelecionado = spinnerCliente.getSelectedItem().toString();

                String clienteCNPJ = clienteDAO.selectCNPJPorNome(nomeClienteSelecionado);

                Projeto projeto = new Projeto(
                        spinnerStatus.getSelectedItem().toString(),
                        inputProjeto_descricao.getText().toString(),
                        spinnerServico.getSelectedItem().toString(),
                        inputProjeto_dataInicio.getText().toString(),
                        inputProjeto_dataFinal.getText().toString(),
                        clienteCNPJ
                );

                projetoDAO = new ProjetoDAO(getApplicationContext());

                try {
                    projetoDAO.insertProjeto(projeto);
                    FuncionarioProjetoDAO funcionarioprojetoDAO = new FuncionarioProjetoDAO(getApplicationContext());

                    int projetoID = projetoDAO.selectUltimoProjetoCadastrado();
                    for (int i = 0; i < membrosList.size(); i++) {
                        String nomeSelecionado = nomesMembros[membrosList.get(i)];
                        String cpf = funcionarioDAO.selectCPFPorNome(nomeSelecionado);

                        FuncionarioProjeto funcionarioProjeto = new FuncionarioProjeto(cpf, projetoID);
                        try {
                            funcionarioprojetoDAO.insertFuncionarioProjeto(funcionarioProjeto);
                        } catch (Exception e) {

                        }
                    }
                    Toast.makeText(getApplicationContext(), "Cadastro efetuado com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), ProjetoListarActivity.class));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "ERRO! O Cadastro falhou", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void carregarSpinnerCliente() {
        ClienteDAO clienteDAO = new ClienteDAO(getApplicationContext());
        List<String> clientesNomes = clienteDAO.selectTodosNomesClientes();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.text_spinner, clientesNomes);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCliente.setAdapter(dataAdapter);
    }

    private void carregarSpinnerServico() {
        ArrayAdapter dataAdapter = ArrayAdapter.createFromResource(this,
              R.array.servico,  R.layout.text_spinner);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServico.setAdapter(dataAdapter);
    }

    private void carregarSpinnerStatus() {
        ArrayAdapter dataAdapter = ArrayAdapter.createFromResource(this,
                R.array.status,  R.layout.text_spinner);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(dataAdapter);
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