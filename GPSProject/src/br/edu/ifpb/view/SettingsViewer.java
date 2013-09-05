package br.edu.ifpb.view;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import br.edu.ifpb.R;

public class SettingsViewer extends ListActivity {
	
	private static final int INICIAR_SERVICO = 0;
	private static final int PARAMETROS = 1;

	
	private String[] options = new String[]{"Ativar Serviço", "Parametros"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		atualizarLista();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings_viewer, menu);
		return true;
	}
	
	private void atualizarLista(){
		
		ArrayAdapter<String> arrayAdapter = 
				new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,options);

		setListAdapter(arrayAdapter);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		switch (item.getItemId()) {				
				case 1:					
					startActivity(new Intent("configServiceIT"));
					break;
				case 2:
					startActivityForResult(new Intent("configServiceIT"), 2);
					break;
				default:
					break;
				}
				
				return true;
	
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		switch (position) {
			case INICIAR_SERVICO:
				startActivity(new Intent("configServiceIT"));
				break;
			case PARAMETROS:
				startActivity(new Intent("configServiceIT"));
				break;

			default:
				break;
		}
	}

	
}
