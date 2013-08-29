package br.edu.ifpb.util;

import java.util.List;

import android.R;
import android.R.layout;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.edu.ifpb.model.Ambiente;

public class AmbienteListAdapter extends BaseAdapter {
	
	private Context context;
	
	private List<Ambiente> ambientes;
	
	public AmbienteListAdapter(Context ctx, List<Ambiente> list) {
		this.context = ctx;
		this.ambientes = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ambientes.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ambientes.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		
		Ambiente a = ambientes.get(position);
		
		LayoutInflater inflater = (LayoutInflater) 
				context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		//inflater.inflate(R.layout., root)
		//TextView nome = view.findViewById(R.id.nome);
		return null;
		
	}

}
