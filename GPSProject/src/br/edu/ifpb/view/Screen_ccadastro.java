package br.edu.ifpb.view;

import java.util.Date;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import br.edu.ifpb.R;
import br.edu.ifpb.model.Ambiente;

public class Screen_ccadastro extends Activity implements OnItemSelectedListener, OnClickListener{

	private Ambiente ambiente;
	private String ringtone_mode_selected;
	private Button btSave, btCancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_screen_ccadastro);
		
		 btSave = (Button) findViewById(R.id.btSave);
		 btCancel = (Button) findViewById(R.id.btCancel);
		
		btSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ambiente = preencheAmbiente();
				
				exibeAmbiente(ambiente);
				
				finish();
			}
		});
		
		btCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finish();
				
			}
		});
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner_ringtone);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ringtone_modes, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(this);
		
		
	}

	protected void exibeAmbiente(Ambiente ambiente2) {
		Toast.makeText(this, ambiente2.toString(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.screen_ccadastro, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		
		CharSequence s = (CharSequence) parent.getItemAtPosition(pos);
		this.ringtone_mode_selected = s.toString();
		Toast toast = Toast.makeText(this,s, Toast.LENGTH_SHORT);
		toast.show();
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	private Ambiente preencheAmbiente(){
		Ambiente ambiente = new Ambiente();
		
		EditText nome = (EditText) findViewById(R.id.editTextNome);
		String ValueNome = nome.getText().toString();
		
		EditText raio = (EditText) findViewById(R.id.editTextRaio);
		String ValueRaio = raio.getText().toString();
		
		EditText latitude = (EditText) findViewById(R.id.editTextLatitude);
		String ValueLatitude = latitude.getText().toString();
		
		EditText longitude = (EditText) findViewById(R.id.editTextLongitude);
		String ValueLongitude = longitude.getText().toString();
		
		
		ambiente.setNome(ValueNome);
		ambiente.setRaio(Double.valueOf(ValueRaio));
		ambiente.setData_persist(new Date());
		
		if(ringtone_mode_selected.equals("Normal"))
			ambiente.setPerfil(Ambiente.NORMAL);
		else if(ringtone_mode_selected.equals("Silent")){
			ambiente.setPerfil(Ambiente.SILENCIOSO);
		}
		else{
			ambiente.setPerfil(Ambiente.VIBRACAO);
		}
		
		Location location = new Location("");
		location.setLatitude(Double.valueOf(ValueLatitude));
		location.setLongitude(Double.valueOf(ValueLongitude));
		
		ambiente.setLocation(location);
		ambiente.setDescricao(null);
		
		return ambiente;
	}
	
	
	@Override
	public void onClick(View v) {
		
		Button button = (Button)v;
		if(button.getId() == 0x7f09000f){
			
			
			
		}
		
		
	}

}
