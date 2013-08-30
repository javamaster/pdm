package br.edu.ifpb.view;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import br.edu.ifpb.R;

public class SettingsViewer extends ListActivity {

	
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
		// TODO Auto-generated method stub
		return super.onMenuItemSelected(featureId, item);
	}


	
}
