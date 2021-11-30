package com.example.westen.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.westen.Bairro;
import com.example.westen.Cidade;
import com.example.westen.Cliente;
import com.example.westen.Conexao;
import com.example.westen.Endereco;
import com.example.westen.Estado;
import com.example.westen.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public ClienteDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserirCliente(Cliente cliente, Bairro bairro, Cidade cidade, Estado estado, Endereco endereco, Pessoa pessoa){



        
        ContentValues valuesCidade = new ContentValues();
        String selectCidade = "SELECT CidadeID FROM Cidade WHERE CidadeNome = ?";
        Cursor cursorCidade = banco.rawQuery(selectCidade, new String[]{ cidade.getCidadeNome() });;
        
        ContentValues valuesEstado = new ContentValues();
        String selectEstado = "SELECT EstadoID FROM Estado WHERE EstadoUF = ?";
        Cursor cursorEstado = banco.rawQuery(selectEstado, new String[]{ estado.getEstadoUF() });
        
        ContentValues valuesEndereco = new ContentValues();
        String selectEndereco = "SELECT Cep FROM Endereco WHERE Cep = ?";
        Cursor cursorEndereco = banco.rawQuery(selectEndereco, new String[]{ endereco.getCep() });
        
        

        if(cursorCidade.getCount() == 0){
            valuesCidade.put("CidadeNome", cidade.getCidadeNome());
            banco.insert("Cidade", null, valuesCidade);
        }
        if(cursorEstado.getCount() == 0){
            valuesEstado.put("EstadoNome", estado.getEstadoUF());
            banco.insert("Estado", null, valuesEstado);
        }
        if(cursorEndereco.getCount() == 0){
            valuesEndereco.put("Cep", endereco.getCep());
            valuesEndereco.put("Logradouro", endereco.getLogradouro());
            valuesEndereco.put("FK_BairroID", bairro.getBairroID());
            banco.insert("Endereco", null, valuesEndereco);
        }
        return 2;
    }

    public List<Cliente> listarTodosClientes()
    {
        List<Cliente> clientes = new ArrayList<>();

        Cursor cursor = banco.query("Cliente", new String[])
    }
}
