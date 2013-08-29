package br.edu.ifpb.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import br.edu.ifpb.R;

public class Home extends Activity {
	
	private ImageButton btnAmbiente;
	private ImageButton btnSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		this.btnAmbiente = (ImageButton) findViewById(R.id.btnAmbiente);
		this.btnSettings = (ImageButton) findViewById(R.id.btnSettings);
		
		
		btnAmbiente.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//obtem referencia para a proxima tela 
				Intent conf = new Intent("Amb_conf");
				
				//inicia a proxima tela
				startActivity(conf);
			}
		});
		
		btnSettings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//obtem referencia para a proxima tela 
				Intent conf = new Intent("Amb_conf");
				
				//inicia a proxima tela
				startActivity(conf);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}

