package com.example.westen.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.westen.Activities.FuncionarioCadastroActivity;
import com.example.westen.Funcionario;
import com.example.westen.R;

import java.io.Serializable;
import java.util.List;

public class ListViewFuncionarioAdapter extends BaseAdapter {
    private final int layout;
    private final Context context;
    List<Funcionario> listaFuncionarios;

    public ListViewFuncionarioAdapter(Context context, int layout, List<Funcionario> listaFuncionarios) {
        this.layout = layout;
        this.context = context;
        this.listaFuncionarios = listaFuncionarios;
    }

    @Override
    public int getCount() {
        return listaFuncionarios.size();
    }

    @Override
    public Object getItem(int position) {
        return listaFuncionarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView txtFuncionarioCodigo, txtFuncionarioNome, txtFuncionarioCargo;
        ImageButton btnFuncionarioEditar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ListViewFuncionarioAdapter.ViewHolder holder = new ListViewFuncionarioAdapter.ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.btnFuncionarioEditar = row.findViewById(R.id.btnClienteEditar);
            //holder.txtFuncionarioCodigo.setText(String.valueOf(position));
            holder.txtFuncionarioCodigo = row.findViewById(R.id.txtCodigo);
            holder.txtFuncionarioNome = row.findViewById(R.id.txtNome);
            holder.txtFuncionarioCargo = row.findViewById(R.id.txtComplemento);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        Funcionario funcionario = listaFuncionarios.get(position);

        holder.btnFuncionarioEditar.setOnClickListener(v -> {
            Intent intentAbrirEditar = new Intent(context, FuncionarioCadastroActivity.class);
            intentAbrirEditar.putExtra("Funcionario", funcionario);
            context.startActivity(intentAbrirEditar);
        });

        holder.txtFuncionarioCodigo.setText(funcionario.getFuncionarioCPF());
        holder.txtFuncionarioNome.setText(funcionario.getFuncionarioNome());
        holder.txtFuncionarioCargo.setText(funcionario.getFuncionarioCargo());

        return row;
    }
}