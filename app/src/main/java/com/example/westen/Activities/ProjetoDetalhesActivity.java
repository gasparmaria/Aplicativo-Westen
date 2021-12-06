package com.example.westen.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
    Cliente cliente;
    Funcionario funcionario;
    FuncionarioProjetoDAO funcionarioProjetoDAO;
    ListView listViewFuncionarios;
    List<Funcionario> listaFuncionarios;
    FuncionarioDAO funcionarioDAO;

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

        Intent intent = getIntent();
        Projeto projeto = ((Projeto) intent.getSerializableExtra("Projeto"));
        if (projeto != null) {
            Toast.makeText(getApplicationContext(), projeto.getProjetoServico(), Toast.LENGTH_SHORT).show();
        }

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


            funcionarioProjetoDAO = new FuncionarioProjetoDAO(getApplicationContext());
            List<FuncionarioProjeto> funcionarioProjetos = funcionarioProjetoDAO.selectFuncionariosProjeto(projeto.getProjetoID());
            funcionarioDAO = new FuncionarioDAO(getApplicationContext());
            listViewFuncionarios = (ListView) findViewById(R.id.listviewFuncionariosProjeto);

            for(int i = 0; i <= funcionarioProjetos.size(); i++){
                String funcionarioCPF = funcionarioProjetos.get(i).getFK_FuncionarioCPF();
                listaFuncionarios.add(funcionarioDAO.selectFuncionarioPorCPF(funcionarioCPF));
            }

            ArrayAdapter<Funcionario> adapter = new ArrayAdapter<Funcionario>(getApplicationContext(), android.R.layout.simple_list_item_1, listaFuncionarios);
            listViewFuncionarios.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}