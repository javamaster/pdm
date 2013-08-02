package br.edu.ifpb.control;

import br.edu.ifpb.R;
import br.edu.ifpb.R.id;
import br.edu.ifpb.R.layout;
import br.edu.ifpb.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class IniciarServico extends Activity {

	
	private static final String CATEGORIA = "livro";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form_service);
		
		final Intent it = new Intent("SERVICE_1");
		Button btIniciar = (Button) findViewById(R.id.iniciar_bt);
		Button btParar = (Button) findViewById(R.id.parar_bt);
		final TextView txv = (TextView) findViewById(R.id.label);
		
		
		btIniciar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Iniciar o Serviço
				ComponentName cn = startService(it);	
				if(cn != null)
					txv.setText("O serviço fica executando em segundo plano");
			}
			
		});
		
		btParar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Parar o Serviço;
				stopService(it);
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
