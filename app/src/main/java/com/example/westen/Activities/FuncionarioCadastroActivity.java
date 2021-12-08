package com.example.westen.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.westen.DAO.FuncionarioDAO;
import com.example.westen.Funcionario;
import com.example.westen.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class FuncionarioCadastroActivity extends AppCompatActivity implements SensorEventListener {

    ImageButton btnAddImagem;
    EditText txtCPF,
            txtNome,
            txtEmail,
            txtSenha,
            txtCargo,
            txtComplemento,
            txtTelefone,
            txtBairro,
            txtCidade,
            txtCEP,
            txtLogradouro,
            txtNumeroEndereco,
            txtUF;

    String FuncionarioCPF,
            FuncionarioNome,
            FuncionarioEmail,
            FuncionarioSenha,
            FuncionarioCargo,
            FuncionarioComplementoEndereco,
            FuncionarioTelefone,
            FuncionarioBairro,
            FuncionarioCidade,
            FuncionarioUF,
            FuncionarioCEP,
            FuncionarioLogradouro,
            FuncionarioNumero;

    int FuncionarioNumeroEndereco;

    Button btnCadastrarFuncionario;

    ImageView imageViewFuncionario;

    final int REQUEST_CODE_GALLERY = 123;


    private SensorManager sensorManager;
    private Sensor sensorLuz;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Button btnLocalizacaoAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario_cadastro);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btnAddImagem = findViewById(R.id.btnAddImagem);
        btnCadastrarFuncionario = (Button) findViewById(R.id.btnCadastrarFuncionario);

        txtCPF = findViewById(R.id.inputFuncionario_cpf);
        txtNome = findViewById(R.id.inputFuncionario_nome);
        txtEmail = findViewById(R.id.inputFuncionario_email);
        txtNumeroEndereco = findViewById(R.id.inputFuncionario_numero);
        txtComplemento = findViewById(R.id.inputFuncionario_complemento);
        txtTelefone = findViewById(R.id.inputFuncionario_telefone);
        txtBairro = findViewById(R.id.inputFuncionario_bairro);
        txtCidade = findViewById(R.id.inputFuncionario_cidade);
        txtUF = findViewById(R.id.inputFuncionario_UF);
        txtCEP = findViewById(R.id.inputFuncionario_cep);
        txtLogradouro = findViewById(R.id.inputFuncionario_logradouro);
        txtCargo = findViewById(R.id.inputFuncionario_cargo);
        txtSenha = findViewById(R.id.inputFuncionario_senha);
        imageViewFuncionario = findViewById(R.id.imagemFuncionario);
        btnLocalizacaoAtual = (Button) findViewById(R.id.btnFuncionarioLocalizacaoAtual);

        btnAddImagem.setOnClickListener(v -> visualizarGaleria());

        btnLocalizacaoAtual.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //PERMISSÃO CEDIDA
                getLocalizacaoAtual();
            } else {
                //PERMISSÃO NEGADA
                ActivityCompat.requestPermissions(FuncionarioCadastroActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        44);
            }
        });
        
        Intent intent = getIntent();

        if (intent != null)
        {
            btnCadastrarFuncionario.setText(R.string.txtSalvar);

            Funcionario funcionario = ((Funcionario) intent.getSerializableExtra("Funcionario"));
            txtCPF.setText(funcionario.getFuncionarioCPF());
            txtNome.setText(funcionario.getFuncionarioNome());
            txtEmail.setText(funcionario.getFuncionarioEmail());
            txtNumeroEndereco.setText(String.valueOf(funcionario.getFuncionarioNumeroEndereco()));
            txtComplemento.setText(funcionario.getFuncionarioComplementoEndereco());
            txtTelefone.setText(funcionario.getFuncionarioTelefone());
            txtBairro.setText(funcionario.getFuncionarioBairro());
            txtCidade.setText(funcionario.getFuncionarioCidade());
            txtUF.setText(funcionario.getFuncionarioUF());
            txtCEP.setText(funcionario.getFuncionarioCEP());
            txtLogradouro.setText(funcionario.getFuncionarioLogradouro());
            txtCargo.setText(funcionario.getFuncionarioCargo());
            txtSenha.setText(funcionario.getFuncionarioSenha());

            byte[] imgFuncionarioPerfil = funcionario.getFuncionarioImagem();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgFuncionarioPerfil, 0, imgFuncionarioPerfil.length);
            imageViewFuncionario.setImageBitmap(bitmap);

            btnCadastrarFuncionario.setOnClickListener(v -> {
                FuncionarioCPF = txtCPF.getText().toString();
                FuncionarioNome = txtNome.getText().toString();
                FuncionarioEmail = txtEmail.getText().toString();
                FuncionarioNumeroEndereco = Integer.parseInt(txtNumeroEndereco.getText().toString());
                FuncionarioComplementoEndereco = txtComplemento.getText().toString();
                FuncionarioTelefone = txtTelefone.getText().toString();
                FuncionarioBairro = txtBairro.getText().toString();
                FuncionarioCidade = txtCidade.getText().toString();
                FuncionarioUF = txtUF.getText().toString();
                FuncionarioCEP = txtCEP.getText().toString();
                FuncionarioLogradouro = txtLogradouro.getText().toString();
                FuncionarioCargo = txtCargo.getText().toString();
                FuncionarioSenha = txtSenha.getText().toString();


                Funcionario funcionarioUpdate = new Funcionario(FuncionarioCPF,
                        FuncionarioNome,
                        FuncionarioEmail,
                        FuncionarioSenha,
                        FuncionarioCargo,
                        FuncionarioComplementoEndereco,
                        FuncionarioTelefone,
                        FuncionarioBairro,
                        FuncionarioCidade,
                        FuncionarioUF,
                        FuncionarioCEP,
                        FuncionarioLogradouro,
                        FuncionarioNumeroEndereco,
                        ImageViewToByte(imageViewFuncionario));

                FuncionarioDAO funcionarioDAO = new FuncionarioDAO(FuncionarioCadastroActivity.this);

                try{
                    funcionarioDAO.updateFuncionario(funcionarioUpdate);
                    Toast.makeText(getApplicationContext(), "Edição efetuada com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), FuncionarioListarActivity.class));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
        else {
            btnCadastrarFuncionario.setOnClickListener(v -> {
                FuncionarioCPF = txtCPF.getText().toString();
                FuncionarioNome = txtNome.getText().toString();
                FuncionarioEmail = txtEmail.getText().toString();
                FuncionarioNumeroEndereco = Integer.parseInt(txtNumeroEndereco.getText().toString());
                FuncionarioComplementoEndereco = txtComplemento.getText().toString();
                FuncionarioTelefone = txtTelefone.getText().toString();
                FuncionarioBairro = txtBairro.getText().toString();
                FuncionarioCidade = txtCidade.getText().toString();
                FuncionarioUF = txtUF.getText().toString();
                FuncionarioCEP = txtCEP.getText().toString();
                FuncionarioLogradouro = txtLogradouro.getText().toString();
                FuncionarioCargo = txtCargo.getText().toString();
                FuncionarioSenha = txtSenha.getText().toString();


                Funcionario funcionario = new Funcionario(FuncionarioCPF,
                        FuncionarioNome,
                        FuncionarioEmail,
                        FuncionarioSenha,
                        FuncionarioCargo,
                        FuncionarioComplementoEndereco,
                        FuncionarioTelefone,
                        FuncionarioBairro,
                        FuncionarioCidade,
                        FuncionarioUF,
                        FuncionarioCEP,
                        FuncionarioLogradouro,
                        FuncionarioNumeroEndereco,
                        ImageViewToByte(imageViewFuncionario));

                FuncionarioDAO funcionarioDAO = new FuncionarioDAO(FuncionarioCadastroActivity.this);

                try {
                    funcionarioDAO.insertFuncionario(funcionario);
                    Toast.makeText(getApplicationContext(), "Cadastro efetuado com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), FuncionarioListarActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    // manipulação de imagens
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewFuncionario.setImageBitmap(bitmap);
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(),"Acesso à galeria negado.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void visualizarGaleria() {
        ActivityCompat.requestPermissions(FuncionarioCadastroActivity.this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE
                },
                REQUEST_CODE_GALLERY);
    }

    private byte[] ImageViewToByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    // saved instance
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        FuncionarioCPF = txtCPF.getText().toString();
        FuncionarioNome = txtNome.getText().toString();
        FuncionarioEmail = txtEmail.getText().toString();
        FuncionarioNumero = txtNumeroEndereco.getText().toString();
        FuncionarioComplementoEndereco = txtComplemento.getText().toString();
        FuncionarioTelefone = txtTelefone.getText().toString();
        FuncionarioBairro = txtBairro.getText().toString();
        FuncionarioCidade = txtCidade.getText().toString();
        FuncionarioUF = txtUF.getText().toString();
        FuncionarioCEP = txtCEP.getText().toString();
        FuncionarioLogradouro = txtLogradouro.getText().toString();
        FuncionarioCargo = txtCargo.getText().toString();

        outState.putString("FuncionarioCPF", FuncionarioCPF);
        outState.putString("FuncionarioNome", FuncionarioNome);
        outState.putString("FuncionarioEmail", FuncionarioEmail);
        outState.putString("FuncionarioNumeroEndereco", FuncionarioNumero);
        outState.putString("FuncionarioComplementoEndereco", FuncionarioComplementoEndereco);
        outState.putString("FuncionarioTelefone", FuncionarioTelefone);
        outState.putString("FuncionarioBairro", FuncionarioBairro);
        outState.putString("FuncionarioCidade", FuncionarioCidade);
        outState.putString("FuncionarioUF", FuncionarioUF);
        outState.putString("FuncionarioCEP", FuncionarioCEP);
        outState.putString("FuncionarioLogradouro", FuncionarioLogradouro);
        outState.putString("FuncionarioCargo", FuncionarioCargo);
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

    @SuppressLint("MissingPermission")
    private void getLocalizacaoAtual() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            //PEGAR A LOCALIZAÇÃO
            Location location = task.getResult();
            if (location != null) {
                try {
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> addressList = geocoder.getFromLocation(
                            location.getLatitude(), location.getLongitude(), 1
                    );
                    //EXIBIR TEXTOS
                    txtLogradouro.setText(Html.fromHtml(String.valueOf(addressList.get(0).getThoroughfare())));
                    txtNumeroEndereco.setText(Html.fromHtml(String.valueOf(addressList.get(0).getFeatureName())));
                    txtCEP.setText((Html.fromHtml(String.valueOf(addressList.get(0).getPostalCode()))));
                    txtCidade.setText(Html.fromHtml(String.valueOf(addressList.get(0).getSubAdminArea())));
                    txtUF.setText(Html.fromHtml(String.valueOf(addressList.get(0).getAdminArea())));
                    txtBairro.setText(Html.fromHtml(String.valueOf(addressList.get(0).getSubLocality())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
                        }, 150);
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