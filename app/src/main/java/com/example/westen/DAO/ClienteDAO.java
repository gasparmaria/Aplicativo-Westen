package com.example.westen.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.westen.Cliente;
import com.example.westen.Conexao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
        values.put("ClienteLogo", cliente.getClienteImagem());
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

    public List<String> selectTodosNomesClientes()
    {
        List<String> clienteNomes = new ArrayList<>();

        Cursor cursor = banco.query("tbCliente",
                new String[] {
                        "ClienteNome"
                },
                null,
                null,
                null,
                null,
                null);

        while(cursor.moveToNext()){
            String nome = cursor.getString(0);
            clienteNomes.add(nome);
        }


        return clienteNomes;
    }

    public List<Cliente> selectCliente()
    {
        List<Cliente> listaClientes = new ArrayList<>();

        Cursor cursor = banco.query("tbCliente",
                                    new String[] {
                                        "ClienteCNPJ",
                                        "ClienteNome",
                                        "ClienteDescricao",
                                        "ClienteLogo",
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
            cliente.setClienteImagem(cursor.getBlob(3));
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

    public Cliente selectClientePorCNPJ(String cnpj){
        Cliente cliente = new Cliente();
        Cursor cursor = banco.query("tbCliente",
                new String[]{"ClienteNome, ClienteLogo, ClienteDescricao"},
                "ClienteCNPJ = ?",
                new String[]{cnpj},
                null,
                null,
                null,
                String.valueOf(1)
        );
        while(cursor.moveToNext())
        {
            cliente.setClienteNome(cursor.getString(0));
            cliente.setClienteImagem(cursor.getBlob(1));
            cliente.setClienteDescricao(cursor.getString(2));
        }

        return cliente;
    }

    public String selectCNPJPorNome(String nome)
    {
        String cnpj = null;
        Cursor cursor = banco.query("tbCliente",
                new String[]{"ClienteCNPJ"},
                "ClienteNome = ?",
                new String[]{nome},
                null,
                null,
                null,
                String.valueOf(1)
        );
        while(cursor.moveToNext())
        {
            cnpj = cursor.getString(0);
        }
        return cnpj;
    }

    public long updateCliente(Cliente cliente){
        ContentValues values = new ContentValues();
        values.put("ClienteCNPJ", cliente.getClienteCNPJ());
        values.put("ClienteNome", cliente.getClienteNome());
        values.put("ClienteDescricao", cliente.getClienteDescricao());
        values.put("ClienteLogo", cliente.getClienteImagem());
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
