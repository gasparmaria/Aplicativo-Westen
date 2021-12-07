package com.example.westen.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.westen.Activities.ClienteCadastroActivity;
import com.example.westen.Cliente;
import com.example.westen.R;

import java.io.Serializable;
import java.util.List;

public class ListViewClienteAdapter extends BaseAdapter {
    private int layout;
    private Context context;
    List<Cliente> listaClientes;

    public ListViewClienteAdapter(Context context, int layout, List<Cliente> listaClientes) {
        this.layout = layout;
        this.context = context;
        this.listaClientes = listaClientes;
    }

    @Override
    public int getCount() {
        return listaClientes.size();
    }

    @Override
    public Object getItem(int position) {
        return listaClientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView txtClienteCodigo, txtClienteNome, txtClienteCNPJ;
        ImageButton btnClienteEditar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.btnClienteEditar = row.findViewById(R.id.btnClienteEditar);
            //holder.txtClienteCodigo.setText(String.valueOf(position));
            holder.txtClienteNome = (TextView) row.findViewById(R.id.txtNome);
            holder.txtClienteCNPJ = (TextView) row.findViewById(R.id.txtComplemento);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Cliente cliente = listaClientes.get(position);

        holder.btnClienteEditar.setOnClickListener(v -> {
            Intent intentAbrirEditar = new Intent(context, ClienteCadastroActivity.class);
            intentAbrirEditar.putExtra("Cliente", (Serializable) cliente);
            context.startActivity(intentAbrirEditar);
        });

        //holder.txtClienteCodigo.setText(String.valueOf(position));
        holder.txtClienteNome.setText(cliente.getClienteNome());
        holder.txtClienteCNPJ.setText(cliente.getClienteCNPJ());

        return row;
    }
}