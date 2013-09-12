	package br.edu.ifpb.view;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AmbienteConfViewer extends ListActivity {
	
	private static final String[] TELAS = new String[]{
		"Cadastrar","Listar","Sair"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,TELAS));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		switch (position) {
		case 0:
			startActivity(new Intent("Tela_cadastro"));			
			break;
		case 1:
			startActivity(new Intent("Amb_list"));			
			break;
		default:
			finish();
		}
		
	}
}
