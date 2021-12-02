package com.example.westen.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.westen.Cliente;
import com.example.westen.Conexao;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public ClienteDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long insertCliente(Cliente cliente){
        ContentValues values = new ContentValues();
        values.put("ClienteCNPJ", cliente.getClienteCNPJ());
        values.put("ClienteNome", cliente.getClienteNome());
        values.put("ClienteDescricao", cliente.getClienteDescricao());
        values.put("ClientePathLogo", cliente.getClientePathLogo());
        values.put("ClienteNumeroEndereco", cliente.getClienteNumeroEndereco());
        values.put("ClienteComplementoEndereco", cliente.getClienteComplementoEndereco());
        values.put("ClienteTelefone", cliente.getClienteTelefone());
        values.put("ClienteBairro", cliente.getClienteBairro());
        values.put("ClienteCidade", cliente.getClienteCidade());
        values.put("ClienteUF", cliente.getClienteUF());
        values.put("ClienteCEP", cliente.getClienteCEP());
        values.put("ClienteLogradouro", cliente.getClienteLogradouro());

        return banco.insert("tbCliente", null, values);
    }

    public List<Cliente> selectCliente()
    {
        List<Cliente> listaClientes = new ArrayList<>();

        Cursor cursor = banco.query("tbCliente",
                                    new String[] {
                                        "ClienteCNPJ",
                                        "ClienteNome",
                                        "ClienteDescricao",
                                        "ClientePathLogo",
                                        "ClienteNumeroEndereco",
                                        "ClienteComplementoEndereco",
                                        "ClienteTelefone",
                                        "ClienteBairro",
                                        "ClienteCidade",
                                        "ClienteUF",
                                        "ClienteCEP",
                                        "ClienteLogradouro"
                                    },
                                    null,
                                    null,
                                    null,
                                    null,
                                    null);

        while(cursor.moveToNext()){
            Cliente cliente = new Cliente();

            cliente.setClienteCNPJ(cursor.getString(0));
            cliente.setClienteNome(cursor.getString(1));
            cliente.setClienteDescricao(cursor.getString(2));
            cliente.setClientePathLogo(cursor.getString(3));
            cliente.setClienteNumeroEndereco(cursor.getInt(4));
            cliente.setClienteComplementoEndereco(cursor.getString(5));
            cliente.setClienteTelefone(cursor.getString(6));
            cliente.setClienteBairro(cursor.getString(7));
            cliente.setClienteCidade(cursor.getString(8));
            cliente.setClienteUF(cursor.getString(9));
            cliente.setClienteCEP(cursor.getString(10));
            cliente.setClienteLogradouro(cursor.getString(11));

            listaClientes.add(cliente);
        }

        return listaClientes;
    }

    public long updateCliente(Cliente cliente){
        ContentValues values = new ContentValues();
        values.put("ClienteCNPJ", cliente.getClienteCNPJ());
        values.put("ClienteNome", cliente.getClienteNome());
        values.put("ClienteDescricao", cliente.getClienteDescricao());
        values.put("ClientePathLogo", cliente.getClientePathLogo());
        values.put("ClienteNumeroEndereco", cliente.getClienteNumeroEndereco());
        values.put("ClienteComplementoEndereco", cliente.getClienteComplementoEndereco());
        values.put("ClienteTelefone", cliente.getClienteTelefone());
        values.put("ClienteBairro", cliente.getClienteBairro());
        values.put("ClienteCidade", cliente.getClienteCidade());
        values.put("ClienteUF", cliente.getClienteUF());
        values.put("ClienteCEP", cliente.getClienteCEP());
        values.put("ClienteLogradouro", cliente.getClienteLogradouro());

        return banco.update("tbCliente", values, "ClienteCNPJ = ?", new String[]{ cliente.getClienteCNPJ() });
    }
}
