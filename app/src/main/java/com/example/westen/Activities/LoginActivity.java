package com.example.westen.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import com.example.westen.DAO.FuncionarioDAO;
import com.example.westen.Funcionario;
import com.example.westen.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private static final String FILE_NAME = "usuarioLogado.json";
    private EditText txtEmail, txtSenha;
    private Button btnLogin;
    private FuncionarioDAO funcionarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
}