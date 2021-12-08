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
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.westen.DAO.FuncionarioDAO;
import com.example.westen.Funcionario;
import com.example.westen.R;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity implements SensorEventListener {

    private static final String FILE_NAME = "usuarioLogado.json";
    private EditText txtEmail, txtSenha;
    private Button btnLogin;
    private FuncionarioDAO funcionarioDAO;
    private SensorManager sensorManager;
    private Sensor sensorLuz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        txtEmail = findViewById(R.id.txtEmailLogin);
        txtSenha = findViewById(R.id.txtSenhaLogin);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v ->{
            String emailLogin = String.valueOf(txtEmail.getText());
            String senhaLogin = String.valueOf(txtSenha.getText());

            validarCampos();

            funcionarioDAO = new FuncionarioDAO(getApplicationContext());

            if(funcionarioDAO.verificarLogin(emailLogin, senhaLogin)){
                Funcionario funcionario = funcionarioDAO.selectFuncionarioPorEmail(emailLogin);

                Gson gson = new Gson();
                String json = gson.toJson(funcionario);
                gravarDados(json);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(LoginActivity.this, "Usuário ou senha não correspondem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // VALIDAR CAMPOS
    private void validarCampos(){
        boolean verificacao = false;

        String email = txtEmail.getText().toString();
        String senha = txtSenha.getText().toString();

        if (verificacao = campoNulo(email)) {
            txtEmail.requestFocus();
            Toast.makeText(this, "Preencha o campo e-mail.", Toast.LENGTH_SHORT).show();
        } else if (verificacao = campoNulo(senha)) {
            txtSenha.requestFocus();
            Toast.makeText(this, "Preencha o campo senha.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean campoNulo (String campo){
        boolean verificacao = (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
        return verificacao;
    }

    // SAVED INSTANCE
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String email = txtEmail.getText().toString();
        outState.putString("Email", email);
    }

    // ARMAZENAR DADOS
    private void gravarDados(String json) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(json.getBytes());
            Toast.makeText(this, "Usuário logado.", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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