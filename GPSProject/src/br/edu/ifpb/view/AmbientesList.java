package br.edu.ifpb.view;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.edu.ifpb.dao.AmbienteDao;
import br.edu.ifpb.model.Ambiente;

public class AmbientesList extends ListActivity {
	
	private static final int INSERIR_EDITAR = 1;
	private static final int BUSCAR = 2;
	private static Context contexto;
	
	private List<Ambiente> ambientes;
	private AmbienteDao dao = new AmbienteDao(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		atualizarLista();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		
		editarAmbiente(position);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		atualizarLista();
	}
	private void editarAmbiente(int position) {
		
		Ambiente a = ambientes.get(position);
		
		Intent it = new Intent("Tela_cadastro");
		
		it.putExtra("ID", a.getId());
		
		Log.d("Editar", "Editar "+a.getId());
		
		startActivity(it);
		
	}
	
	private void atualizarLista(){
		dao.open();
		ambientes = dao.getAll();
		
		ArrayAdapter<Ambiente> arrayAdapter = 
				new ArrayAdapter<Ambiente>(this, android.R.layout.simple_list_item_1,ambientes);

		setListAdapter(arrayAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		super.onCreateOptionsMenu(menu);
		
		menu.add(0,INSERIR_EDITAR,0,"Inserir Novo");
		menu.add(0,BUSCAR,0,"Buscar");
		
		return true;
		
	}
	
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		switch (item.getItemId()) {
		
		case INSERIR_EDITAR:
			startActivityForResult(new Intent("Tela_cadastro"), INSERIR_EDITAR);
			break;
		case BUSCAR:
			startActivityForResult(new Intent("Tela_cadastro"), BUSCAR);
			break;
		default:
			break;
		}
		
		return true;
	
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		dao.close();
	}
	
	

}
