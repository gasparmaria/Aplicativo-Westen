package com.example.westen.Activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.westen.Cliente;
import com.example.westen.Conexao;
import com.example.westen.DAO.ClienteDAO;
import com.example.westen.DAO.FuncionarioDAO;
import com.example.westen.DAO.FuncionarioProjetoDAO;
import com.example.westen.DAO.ProjetoDAO;
import com.example.westen.Funcionario;
import com.example.westen.Projeto;
import com.example.westen.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ProjetoDetalhesActivity extends AppCompatActivity implements SensorEventListener {

    TextView txtEmpresa,
            txtDescricaoEmpresa,
            txtServicoProjeto,
            txtDescricaoProjeto,
            txtDataInicio,
            txtDataFinal,
            txtStatus;
    ImageView imageClienteProjeto;
    ListView listViewFuncionarios;
    ImageButton btnProjetoEditar,
                btnProjetoDeletar;
    AlertDialog alerta;

    Cliente cliente;
    Funcionario funcionario;
    FuncionarioProjetoDAO funcionarioProjetoDAO;
    FuncionarioDAO funcionarioDAO;
    ProjetoDAO projetoDAO;

    List<Funcionario> listaFuncionarios = new ArrayList<>();
    List<Cliente> listaClientes = new ArrayList<>();
    List<Projeto> listaProjetos = new ArrayList<>();

    Conexao conexao;

    private SensorManager sensorManager;
    private Sensor sensorLuz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projeto_detalhes);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);


        txtEmpresa = findViewById(R.id.txtEmpresa);
        txtDescricaoEmpresa = findViewById(R.id.txtDescricaoEmpresa);
        txtServicoProjeto = findViewById(R.id.txtServicoProjeto);
        txtDescricaoProjeto = findViewById(R.id.txtDescricaoProjeto);
        txtDataInicio = findViewById(R.id.txtDataInicio);
        txtDataFinal = findViewById(R.id.txtDataFinal);
        txtStatus = findViewById(R.id.txtStatus);
        imageClienteProjeto = findViewById(R.id.imageClienteProjeto);
        btnProjetoEditar = findViewById(R.id.btnProjetoEditar);
        btnProjetoDeletar = findViewById(R.id.btnProjetoDeletar);

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

            btnProjetoDeletar.setOnClickListener(v ->{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Excluir");
                builder.setMessage("Deseja realmente excluir o projeto?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        projetoDAO = new ProjetoDAO(getApplicationContext());
                        projetoDAO.deleteProjeto(projeto);
                        startActivity(new Intent(getBaseContext(), ProjetoListarActivity.class));
                        finish();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                alerta = builder.create();
                alerta.show();
            });
        }
    }

    public void voltarProjeto(View view){
        startActivity(new Intent(getBaseContext(), ProjetoListarActivity.class));
        finish();
    }

    //MÉTODOS DO SENSOR

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