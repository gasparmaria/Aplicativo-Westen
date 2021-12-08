package com.example.westen.Activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.westen.Cliente;
import com.example.westen.DAO.ClienteDAO;
import com.example.westen.DAO.FuncionarioDAO;
import com.example.westen.DAO.FuncionarioProjetoDAO;
import com.example.westen.DAO.ProjetoDAO;
import com.example.westen.FuncionarioProjeto;
import com.example.westen.Projeto;
import com.example.westen.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ProjetoCadastroActivity extends AppCompatActivity implements SensorEventListener {

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
            FK_ClienteCNPJ,
            ProjetoCliente;

    ProjetoDAO projetoDAO;
    ClienteDAO clienteDAO;
    FuncionarioProjetoDAO funcionarioProjetoDAO;
    String[] projetos,
             status;

    private SensorManager sensorManager;
    private Sensor sensorLuz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projeto_cadastro);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO(getApplicationContext());
        String[] nomesMembros = funcionarioDAO.selectTodosNomesFuncionarios();
        txtMembrosSelecionados = findViewById(R.id.txtMembrosSelecionados);
        ArrayList<Integer> membrosList = new ArrayList<>();
        boolean[] membrosSelecionados = new boolean[nomesMembros.length];

        inputProjeto_descricao = findViewById(R.id.inputProjetos_descricao);
        inputProjeto_dataInicio = findViewById(R.id.inputProjetos_dataInicio);
        inputProjeto_dataFinal = findViewById(R.id.inputProjetos_dataFinal);
        spinnerCliente = findViewById(R.id.spinnerProjeto_cliente);
        spinnerServico = findViewById(R.id.spinnerProjeto_servico);
        spinnerStatus = findViewById(R.id.spinnerProjeto_status);
        btnCadastrarProjeto = findViewById(R.id.btnCadastrarProjeto);

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

        carregarSpinnerCliente();
        carregarSpinnerServico();
        carregarSpinnerStatus();

        Intent intent = getIntent();

        if(intent.hasExtra("Projeto")){
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

                projeto.setFK_ClienteCNPJ(clienteCNPJ);
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
                            Toast.makeText(getApplicationContext(), "catch", Toast.LENGTH_SHORT).show();
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

    // SPINNER
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

    // SAVED INSTANCE
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        ProjetoStatus = spinnerStatus.getSelectedItem().toString();
        ProjetoDescricao = inputProjeto_descricao.getText().toString();
        ProjetoServico = spinnerServico.getSelectedItem().toString();
        ProjetoDataInicio = inputProjeto_dataInicio.getText().toString();
        ProjetoDataFinal = inputProjeto_dataFinal.getText().toString();
        ProjetoCliente = spinnerCliente.getSelectedItem().toString();

        outState.putString("ProjetoStatus", ProjetoStatus);
        outState.putString("ProjetoDescricao", ProjetoDescricao);
        outState.putString("ProjetoServico", ProjetoServico);
        outState.putString("ProjetoDataInicio", ProjetoDataInicio);
        outState.putString("ProjetoDataFinal", ProjetoDataFinal);
        outState.putString("ProjetoCliente", ProjetoCliente);
    }

    // CADASTRO
    public void abrirCadastro(View view){
        startActivity(new Intent(getBaseContext(), ProjetoCadastroActivity.class));
        finish();
    }

    // MENU
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

    // SENSOR
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorLuz, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            if(permissaoControlarBrilho()){
                new Timer().schedule(
                    new TimerTask(){
                        @Override
                        public void run(){
                            int brilho = (int) (event.values[0]);
                            controlarBrilho(brilho);
                        }
                    }, 1500);
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {    }

    private boolean permissaoControlarBrilho()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)) {
                return true;
            }
            else
            {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData((Uri.parse("package:" + getApplication().getPackageName())));
                startActivity(intent);
                return false;
            }
        }
        return false;
    }

    private void controlarBrilho(int brilho)
    {
        if(brilho < 0)
        {
            brilho = 0;
        }
        else if(brilho > 255)
        {
            brilho = 255;
        }

        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brilho);
    }
}