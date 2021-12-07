package com.example.westen.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.westen.Cliente;
import com.example.westen.Conexao;
import com.example.westen.DAO.ClienteDAO;
import com.example.westen.DAO.FuncionarioDAO;
import com.example.westen.DAO.FuncionarioProjetoDAO;
import com.example.westen.Funcionario;
import com.example.westen.FuncionarioProjeto;
import com.example.westen.Projeto;
import com.example.westen.R;
import com.example.westen.databinding.ActivityProjetoDetalhesBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDetalhesActivity extends AppCompatActivity {

    TextView txtEmpresa,
            txtDescricaoEmpresa,
            txtServicoProjeto,
            txtDescricaoProjeto,
            txtDataInicio,
            txtDataFinal,
            txtStatus;
    ImageView imageClienteProjeto;
    ListView listViewFuncionarios;
    ImageButton btnProjetoEditar;

    Cliente cliente;
    Funcionario funcionario;
    FuncionarioProjetoDAO funcionarioProjetoDAO;
    FuncionarioDAO funcionarioDAO;

    List<Funcionario> listaFuncionarios = new ArrayList<>();
    List<Cliente> listaClientes = new ArrayList<>();
    List<Projeto> listaProjetos = new ArrayList<>();

    Conexao conexao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projeto_detalhes);

        txtEmpresa = findViewById(R.id.txtEmpresa);
        txtDescricaoEmpresa = findViewById(R.id.txtDescricaoEmpresa);
        txtServicoProjeto = findViewById(R.id.txtServicoProjeto);
        txtDescricaoProjeto = findViewById(R.id.txtDescricaoProjeto);
        txtDataInicio = findViewById(R.id.txtDataInicio);
        txtDataFinal = findViewById(R.id.txtDataFinal);
        txtStatus = findViewById(R.id.txtStatus);
        imageClienteProjeto = findViewById(R.id.imageClienteProjeto);
        btnProjetoEditar = findViewById(R.id.btnProjetoEditar);

        Intent intent = getIntent();
        Projeto projeto = ((Projeto) intent.getSerializableExtra("Projeto"));

        ClienteDAO clienteDAO = new ClienteDAO(getApplicationContext());

        try {
            cliente = clienteDAO.selectClientePorCNPJ(projeto.getFK_ClienteCNPJ());
        }
        catch (Exception e){

        }
        finally{
            txtEmpresa.setText(cliente.getClienteNome());
            txtDescricaoEmpresa.setText(cliente.getClienteDescricao());
            txtServicoProjeto.setText(projeto.getProjetoServico());
            txtDescricaoProjeto.setText(projeto.getProjetoDescricao());
            txtDataInicio.setText(projeto.getProjetoDataInicio());
            txtDataFinal.setText(projeto.getProjetoDataFinal());
            txtStatus.setText(projeto.getProjetoStatus());

            byte[] imgLogotipo = cliente.getClienteImagem();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgLogotipo, 0, imgLogotipo.length);
            imageClienteProjeto.setImageBitmap(bitmap);

            listViewFuncionarios = (ListView) findViewById(R.id.listviewFuncionariosProjeto);

            conexao = new Conexao(getApplicationContext());
            try {
                Cursor cursor = conexao.getReadableDatabase().rawQuery("SELECT tbFuncionario.FuncionarioNome " +
                        "FROM tbFuncionario " +
                        "INNER JOIN tbFuncionarioProjeto " +
                        "ON tbFuncionarioProjeto.FK_FuncionarioCPF = tbFuncionario.FuncionarioNome " +
                        "WHERE tbFuncionarioProjeto.FK_ProjetoID = ?", new String[]{String.valueOf(projeto.getProjetoID())});

                while (cursor.moveToNext()) {
                    Funcionario funcionario = new Funcionario();

                    funcionario.setFuncionarioNome(cursor.getString(0));

                    listaFuncionarios.add(funcionario);
                }

                ArrayAdapter<Funcionario> adapter = new ArrayAdapter<Funcionario>(getApplicationContext(), R.layout.text_list, listaFuncionarios);
                listViewFuncionarios.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            catch(Exception e){

            }

            btnProjetoEditar.setOnClickListener(v -> {
                Intent intentAbrirEditar = new Intent(ProjetoDetalhesActivity.this, ProjetoCadastroActivity.class);
                intentAbrirEditar.putExtra("Projeto", (Serializable) projeto);
                intentAbrirEditar.putExtra("Cliente", (Serializable) cliente);
                startActivity(intentAbrirEditar);
            });
        }
    }

    public void voltarProjeto(View view){
        startActivity(new Intent(getBaseContext(), ProjetoListarActivity.class));
        finish();
    }
}