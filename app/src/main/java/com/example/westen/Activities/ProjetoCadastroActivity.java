package com.example.westen.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.westen.DAO.ClienteDAO;
import com.example.westen.DAO.FuncionarioDAO;
import com.example.westen.DAO.FuncionarioProjetoDAO;
import com.example.westen.DAO.ProjetoDAO;
import com.example.westen.FuncionarioProjeto;
import com.example.westen.Projeto;
import com.example.westen.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjetoCadastroActivity extends AppCompatActivity {

    Spinner spinnerCliente, spinnerServico, spinnerStatus;
    Button btnCadastrarProjeto;
    EditText inputProjeto_descricao,
             inputProjeto_dataInicio,
             inputProjeto_dataFinal;


    TextView txtMembrosSelecionados;

    ProjetoDAO projetoDAO;
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

        btnCadastrarProjeto.setOnClickListener(v -> {
            ClienteDAO clienteDAO = new ClienteDAO(getApplicationContext());

            String nomeClienteSelecionado = spinnerCliente.toString();

            String clienteCNPJ = clienteDAO.selectCNPJPorNome(nomeClienteSelecionado);

            Projeto projeto = new Projeto(
                    spinnerStatus.toString(),
                    inputProjeto_descricao.getText().toString(),
                    spinnerServico.toString(),
                    inputProjeto_dataInicio.getText().toString(),
                    inputProjeto_dataFinal.getText().toString(),
                    clienteCNPJ
            );

            projetoDAO = new ProjetoDAO(getApplicationContext());
            projetoDAO.insertProjeto(projeto);

            FuncionarioProjetoDAO funcionarioprojetoDAO = new FuncionarioProjetoDAO(getApplicationContext());
            int projetoID = projetoDAO.selectUltimoProjetoCadastrado();

            for (int i = 0; i < membrosList.size() - 1; i++)
            {
                String nomeSelecionado = nomesMembros[membrosList.get(i)];
                FuncionarioProjeto funcionarioProjeto = new FuncionarioProjeto(nomeSelecionado, projetoID);
                funcionarioprojetoDAO.insertFuncionarioProjeto(funcionarioProjeto);
            }
            Toast.makeText(getApplicationContext(), "Cadastro efetuado com sucesso", Toast.LENGTH_SHORT).show();
        });

        carregarSpinnerCliente();
    }

    private void carregarSpinnerCliente() {
        ClienteDAO clienteDAO = new ClienteDAO(getApplicationContext());
        List<String> clientesNomes = clienteDAO.selectTodosNomesClientes();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, clientesNomes);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCliente.setAdapter(dataAdapter);
    }
}