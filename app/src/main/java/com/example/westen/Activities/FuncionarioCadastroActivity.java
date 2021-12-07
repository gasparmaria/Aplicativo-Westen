package com.example.westen.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.westen.DAO.FuncionarioDAO;
import com.example.westen.Funcionario;
import com.example.westen.Projeto;
import com.example.westen.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FuncionarioCadastroActivity extends AppCompatActivity {

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
            FuncionarioLogradouro;

    int FuncionarioNumeroEndereco;

    Button btnCadastrarFuncionario;

    ImageView imageViewFuncionario;

    final int REQUEST_CODE_GALLERY = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario_cadastro);
        
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
        
        btnAddImagem.setOnClickListener(v -> {
            visualizarGaleria();
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
}