package com.example.westen.Activities;

import android.Manifest;
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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.westen.Cliente;
import com.example.westen.DAO.ClienteDAO;
import com.example.westen.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("unused")
public class ClienteCadastroActivity extends AppCompatActivity implements SensorEventListener {

    ImageButton btnAddImagem;
    EditText txtCNPJ,
            txtNome,
            txtDescricao,
            txtComplemento,
            txtTelefone,
            txtBairro,
            txtCidade,
            txtCEP,
            txtLogradouro,
            txtNumeroEndereco,
            txtUF;
    @SuppressWarnings("unused")
    String ClienteCNPJ,
            ClienteNome,
            ClienteDescricao,
            ClientePathLogo,
            ClienteComplementoEndereco,
            ClienteTelefone,
            ClienteBairro,
            ClienteCidade,
            ClienteUF,
            ClienteCEP,
            ClienteLogradouro;
    int ClienteNumeroEndereco;

    Button btnCadastrarCliente;

    ImageView imageViewCliente;

    final int REQUEST_CODE_GALLERY = 123;

    private SensorManager sensorManager;
    private Sensor sensorLuz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_cadastro);

        btnAddImagem = findViewById(R.id.btnAddImagem);
        btnCadastrarCliente = (Button) findViewById(R.id.btnCadastrarCliente);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        txtCNPJ = findViewById(R.id.inputCliente_cnpj);
        txtNome = findViewById(R.id.inputCliente_nome);
        txtDescricao = findViewById(R.id.inputCliente_descricao);
        txtNumeroEndereco = findViewById(R.id.inputCliente_numero);
        txtComplemento = findViewById(R.id.inputCliente_complemento);
        txtTelefone = findViewById(R.id.inputCliente_telefone);
        txtBairro = findViewById(R.id.inputCliente_bairro);
        txtCidade = findViewById(R.id.inputCliente_cidade);
        txtUF = findViewById(R.id.inputCliente_UF);
        txtCEP = findViewById(R.id.inputCliente_cep);
        txtLogradouro = findViewById(R.id.inputCliente_logradouro);
        imageViewCliente = findViewById(R.id.imagemCliente);

        btnAddImagem.setOnClickListener(v -> visualizarGaleria());

        Intent intent = getIntent();

        if(intent != null) {
            btnCadastrarCliente.setText(R.string.txtSalvar);

            Cliente cliente = ((Cliente) intent.getSerializableExtra("Cliente"));

            txtCNPJ.setText(cliente.getClienteCNPJ());
            txtNome.setText(cliente.getClienteNome());
            txtDescricao.setText(cliente.getClienteDescricao());
            txtNumeroEndereco.setText(String.valueOf(cliente.getClienteNumeroEndereco()));
            txtComplemento.setText(cliente.getClienteComplementoEndereco());
            txtTelefone.setText(cliente.getClienteTelefone());
            txtBairro.setText(cliente.getClienteBairro());
            txtCidade.setText(cliente.getClienteCidade());
            txtUF.setText(cliente.getClienteUF());
            txtCEP.setText(cliente.getClienteCEP());
            txtLogradouro.setText(cliente.getClienteLogradouro());

            byte[] imgClientePerfil = cliente.getClienteImagem();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgClientePerfil, 0, imgClientePerfil.length);
            imageViewCliente.setImageBitmap(bitmap);

            btnCadastrarCliente.setOnClickListener(v -> {
                ClienteCNPJ = txtCNPJ.getText().toString();
                ClienteNome = txtNome.getText().toString();
                ClienteDescricao = txtDescricao.getText().toString();
                ClienteNumeroEndereco = Integer.parseInt(txtNumeroEndereco.getText().toString());
                ClienteComplementoEndereco = txtComplemento.getText().toString();
                ClienteTelefone = txtTelefone.getText().toString();
                ClienteBairro = txtBairro.getText().toString();
                ClienteCidade = txtCidade.getText().toString();
                ClienteUF = txtUF.getText().toString();
                ClienteCEP = txtCEP.getText().toString();
                ClienteLogradouro = txtLogradouro.getText().toString();


                Cliente ClienteUpdate = new Cliente(ClienteCNPJ,
                        ClienteNome,
                        ClienteDescricao,
                        ClienteComplementoEndereco,
                        ClienteTelefone,
                        ClienteBairro,
                        ClienteCidade,
                        ClienteUF,
                        ClienteCEP,
                        ClienteLogradouro,
                        ClienteNumeroEndereco,
                        ImageViewToByte(imageViewCliente));

                ClienteDAO ClienteDAO = new ClienteDAO(ClienteCadastroActivity.this);

                try{
                    ClienteDAO.updateCliente(ClienteUpdate);
                    Toast.makeText(getApplicationContext(), "Edição efetuada com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), ClienteListarActivity.class));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
        else {
            btnCadastrarCliente.setOnClickListener(v -> {
                ClienteCNPJ = txtCNPJ.getText().toString();
                ClienteNome = txtNome.getText().toString();
                ClienteDescricao = txtDescricao.getText().toString();
                ClienteNumeroEndereco = Integer.parseInt(txtNumeroEndereco.getText().toString());
                ClienteComplementoEndereco = txtComplemento.getText().toString();
                ClienteTelefone = txtTelefone.getText().toString();
                ClienteBairro = txtBairro.getText().toString();
                ClienteCidade = txtCidade.getText().toString();
                ClienteUF = txtUF.getText().toString();
                ClienteCEP = txtCEP.getText().toString();
                ClienteLogradouro = txtLogradouro.getText().toString();

                Cliente cliente = new Cliente(ClienteCNPJ,
                        ClienteNome,
                        ClienteDescricao,
                        ClienteComplementoEndereco,
                        ClienteTelefone,
                        ClienteBairro,
                        ClienteCidade,
                        ClienteUF,
                        ClienteCEP,
                        ClienteLogradouro,
                        ClienteNumeroEndereco,
                        ImageViewToByte(imageViewCliente));

                ClienteDAO clienteDAO = new ClienteDAO(ClienteCadastroActivity.this);

                try {
                    clienteDAO.insertCliente(cliente);
                    Toast.makeText(getApplicationContext(), "Cadastro efetuado com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), ProjetoCadastroActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewCliente.setImageBitmap(bitmap);
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
        ActivityCompat.requestPermissions(ClienteCadastroActivity.this,
            new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
            },
            REQUEST_CODE_GALLERY);
    }

    private byte[] ImageViewToByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();
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