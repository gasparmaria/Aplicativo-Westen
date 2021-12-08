package com.example.westen.Activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.westen.Adapters.GridProjetosAdapter;
import com.example.westen.Cliente;
import com.example.westen.Conexao;
import com.example.westen.Projeto;
import com.example.westen.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ProjetoListarActivity extends AppCompatActivity implements SensorEventListener{

    GridView gridProjetos;
    List<Cliente> listaClientes = new ArrayList<>();
    List<Projeto> listaProjetos = new ArrayList<>();

    private SensorManager sensorManager;
    private Sensor sensorLuz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projeto_listar);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);


        Conexao conexao = new Conexao(getApplicationContext());
        String selectQuery = "SELECT tbCliente.ClienteNome, " +
                "tbCliente.ClienteLogo, " +
                "tbProjeto.* " +
                "FROM tbProjeto " +
                "INNER JOIN tbCliente " +
                "ON tbCliente.ClienteCNPJ = tbProjeto.FK_ClienteCNPJ;";

        try {
            Cursor cursor = conexao.getNovaQuery(selectQuery);

            while(cursor.moveToNext())
            {
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

                listaClientes.add(new Cliente(nome, imagem));
                listaProjetos.add(projeto);
            }
        }
        catch (Exception e) {

        }
        finally {
            gridProjetos = findViewById(R.id.grid_projetos);
            GridProjetosAdapter gridAdapter = new GridProjetosAdapter(ProjetoListarActivity.this, R.layout.grid_item_projetos, listaClientes, listaProjetos);
            gridProjetos.setAdapter(gridAdapter);
            gridAdapter.notifyDataSetChanged();
        }

        gridProjetos.setOnItemClickListener((lista, item, position, id) -> {
            Projeto Projeto = (Projeto) gridProjetos.getItemAtPosition(position);
            Intent intentAbrirDetalhes = new Intent(ProjetoListarActivity.this, ProjetoDetalhesActivity.class);
            intentAbrirDetalhes.putExtra("Projeto", Projeto);
            startActivity(intentAbrirDetalhes);
        });
    }


    public void abrirCadastro(View view){
        startActivity(new Intent(getBaseContext(), ProjetoCadastroActivity.class));
        finish();
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