package br.edu.ifpb.view;

import br.edu.ifpb.R;
import br.edu.ifpb.R.layout;
import br.edu.ifpb.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ConfigsService extends Activity {
	
	private ToggleButton iniciarServiceTB;
	private final Intent GPSTesteIT = new Intent("SERVICE_3");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config_service);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.configs, menu);
		return true;
	}
	
	public void onToggleClicked(View view) {
		boolean on = ((ToggleButton) view).isChecked();
		
		if(on){
			//Caso seja habilitado, inicia o serviço
			startService(GPSTesteIT);
			Log.d("IniciarServico", "O serviço fica executando em segundo plano");
			Toast.makeText(this, "O serviço foi iniciado!!", Toast.LENGTH_SHORT).show();
		}
		else{
			//desabilitado, para o serviço
			stopService(GPSTesteIT);
			Log.d("Parar Serviço", "Parando o serviço...");
			Toast.makeText(this, "O serviço foi desabilitado!!", Toast.LENGTH_SHORT).show();
;		}
		
	}
	

}
