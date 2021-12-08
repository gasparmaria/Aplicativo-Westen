package com.example.westen.Activities;

import android.content.ContentResolver;
import android.content.Context;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Timer;
import java.util.TimerTask;

public class PerfilActivity extends AppCompatActivity implements SensorEventListener {

    private static final String FILE_NAME = "usuarioLogado.json";
    ImageView imgPerfil;
    TextView txtFuncionarioNome,
             txtFuncionarioCargo,
             txtFuncionarioDesenvolvendo,
             txtFuncionarioConcluidos;
    Button btnPerfilEditar;
    private SensorManager sensorManager;
    private Sensor sensorLuz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        imgPerfil = findViewById(R.id.imgPerfil);
        txtFuncionarioNome = findViewById(R.id.txtFuncionarioNome);
        txtFuncionarioCargo = findViewById(R.id.txtFuncionarioCargo);
        txtFuncionarioDesenvolvendo = findViewById(R.id.txtFuncionarioDesenvolvendo);
        txtFuncionarioConcluidos = findViewById(R.id.txtFuncionarioConcluidos);
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
        String selectQuery =
                "SELECT tbProjeto.* " +
                        "FROM tbProjeto " +
                        "INNER JOIN tbFuncionarioProjeto " +
                        "ON tbFuncionarioProjeto.FK_ProjetoID = tbProjeto.ProjetoID " +
                        "WHERE tbFuncionarioProjeto.FK_FuncionarioCPF = ? AND tbProjeto.ProjetoStatus = ? ";

        try {
            Cursor cursor = conexao.getReadableDatabase().rawQuery(selectQuery, new String[]{funcionario.getFuncionarioCPF(), "Em andamento"});

            String count = String.valueOf(cursor.getCount());

            txtFuncionarioDesenvolvendo.setText(count);
        }
        catch (Exception e){

        }

        try{
            Cursor cursor = conexao.getReadableDatabase().rawQuery(selectQuery, new String[]{funcionario.getFuncionarioCPF(), "Concluido"});

            String count = String.valueOf(cursor.getCount());

            txtFuncionarioConcluidos.setText(count);
        }
        catch (Exception e){

        }

        btnPerfilEditar.setOnClickListener(v ->{
            startActivity(new Intent(getBaseContext(), FuncionarioCadastroActivity.class));
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

    public void abrirCadastro(View view){
        startActivity(new Intent(getBaseContext(), FuncionarioCadastroActivity.class));
        finish();
    }

    public void sair(View view){
        startActivity(new Intent(getBaseContext(), LoginActivity.class));
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
                    }, 1500
                );
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