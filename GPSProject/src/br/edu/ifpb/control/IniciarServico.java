package br.edu.ifpb.control;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import br.edu.ifpb.R;
import br.edu.ifpb.view.AmbientesList;

public class IniciarServico extends Activity {

	
	private static final String CATEGORIA = "livro";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form_service);
		
		final Intent it = new Intent("SERVICE_1");
		final Intent GPSTesteIT = new Intent("SERVICE_3");
		
		Button btIniciar = (Button) findViewById(R.id.iniciar_bt);
		Button btParar = (Button) findViewById(R.id.parar_bt);
		final TextView txv = (TextView) findViewById(R.id.label);
		
		
		btIniciar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Iniciar o Serviço
				
//				ComponentName cn = startService(GPSTesteIT);	
//				Log.d("IniciarServico", "O serviço fica executando em segundo plano");
//				if(cn != null){
//					txv.setText("O serviço fica executando em segundo plano");					
//				}
				Intent it = new Intent("Amb_conf");
				startActivity(it);				
			}
			
		});
		
		btParar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Parar o Serviço;
				stopService(it);
				Log.d("Parar Serviço", "Parando o serviço...");
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.iniciar_servico, menu);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(CATEGORIA, "IniciarServico.onDestroy");
	}

}
