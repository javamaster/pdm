package br.edu.ifpb.view;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import br.edu.ifpb.R;
import br.edu.ifpb.dao.AmbienteDao;
import br.edu.ifpb.model.Ambiente;

public class CadastroEdicaoViewer extends Activity implements OnItemSelectedListener,LocationListener{

	@SuppressWarnings("unused")
	private Ambiente ambiente;
	private String ringtone_mode_selected;
	private Button btSave, btCancel;
	private ImageButton btRemove;
	private Long id;
	
	private AmbienteDao dao;
	private EditText campoNome, campoRaio, campoLatitude,
	campoLongitude;
	private LocationManager manager;
	private Location location;
	private boolean isProviderEnabled = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_screen_ccadastro);
		
		
		campoNome = (EditText) findViewById(R.id.editTextNome);
		campoRaio = (EditText) findViewById(R.id.editTextRaio);
		campoLatitude = (EditText) findViewById(R.id.editTextLatitude);
		campoLongitude = (EditText) findViewById(R.id.editTextLongitude);
		
		 btSave = (Button) findViewById(R.id.btSave);
		 btCancel = (Button) findViewById(R.id.btCancel);
		 btRemove = (ImageButton) findViewById(R.id.btRemove);
		 
		 dao = new AmbienteDao(this);
		 dao.open();
		 
		
		id = null;
		
		Bundle extras = getIntent().getExtras();
		
		if(extras != null){
			
			id = extras.getLong("ID");
			
			if(id != null){
				
				//é uma edição, busca Ambiente...
				Ambiente a = buscarAmbiente(id);
				
				Log.d("Atualizar", "atualizar ambiente "+id);
				
				//Atualiza o formulario com os dados recuperados
				atualizaTelaCadastro(a);
				
			}
			
			if(id == null){
				Log.d("Atualizar", "nao atualizou ambiente");
				finish();
			}
			
		}else{
			Log.d("Atualizar", "Nao atualizou ambiente");
		}
		 
		btSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				salvar();
				
				finish();
			}
		});
		
		btCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finish();
				
			}
		});

		btRemove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Remover o Ambiente selecionado
				remove(id);
				
				//Encerrar a activity
				finish();
			}
		});
		Spinner spinner = (Spinner) findViewById(R.id.spinner_ringtone);
		spinner.setBackgroundColor(Color.GRAY);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ringtone_modes, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(this);
		
		habilitaBotaoRemover();		

		//Configura dados do gps na tela de cadastro
		configuraGps();
				
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		habilitaBotaoRemover();
		
		configuraGps();
	}

	private void configuraGps(){
		
		
		
		manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		
		Criteria criteria = new Criteria();
		
		String provider = manager.getBestProvider(criteria, false);
		
		isProviderEnabled = manager.isProviderEnabled(provider);
		
		if(isProviderEnabled){
		
				location = manager.getLastKnownLocation(provider);
				
				manager.requestLocationUpdates(provider,0 , 0, this);
				
				if(location != null){
					Log.i("Localizacao", "Latitude: "+location.getLatitude()+" \n Longitude: "+location.getLongitude());
					onLocationChanged(location);
				}
				else{
					Log.i("Localizacao", "Localização não definida!!");
					campoLatitude.setText("0.0");
					campoLongitude.setText("0.0");
				}
		}
		else{
			onProviderDisabled(provider);
		}
	}
	
	private void atualizaTelaCadastro(Ambiente a) {
		//Atualiza os campos
		campoNome.setText(a.getNome());
		campoRaio.setText(String.valueOf(a.getRaio()));
		campoLatitude.setText(String.valueOf(a.getLocation().getLatitude()));
		campoLongitude.setText(String.valueOf(a.getLocation().getLongitude()));
	}

	protected void salvar() {
	
		double raio = 0;
		Location loc = new Location("");
				
		try {
			raio = Double.valueOf(campoRaio.getText().toString());
			
			loc.setLatitude(Double.valueOf(campoLatitude.getText().toString()));
			loc.setLongitude(Double.valueOf(campoLongitude.getText().toString()));
			
		} catch (NumberFormatException e) {
			Toast.makeText(this, "Raio: "+e.getMessage(), Toast.LENGTH_SHORT);
			return;
		}
		
		Ambiente ambiente = new Ambiente();
		
		if(id != null){
			//Eh uma atualização
			ambiente.setId(id);
		}
		
		ambiente.setNome(campoNome.getText().toString());
		ambiente.setRaio(raio);
		ambiente.setData_persist(new Date());
		
		
		ambiente.setLocation(loc);
				
		if(ringtone_mode_selected.equals("Normal"))
			ambiente.setPerfil(Ambiente.NORMAL);
		else if(ringtone_mode_selected.equals("Silent")){
			ambiente.setPerfil(Ambiente.SILENCIOSO);
		}
		else{
			ambiente.setPerfil(Ambiente.VIBRACAO);
		}
		
		ambiente.setDescricao("kdkljglkdf");
		
		//Salvar/Atualizar o ambiente no banco
		salvarAmbiente(ambiente);		
		
		//Exibe uma mensagem
		Toast.makeText(this, "Ambiente Salvo!!", Toast.LENGTH_SHORT).show();
		
		liberarRecursos();
		finish();
	}

	protected void remove(long id){
		dao.remove(id);
	}

	private void salvarAmbiente(Ambiente ambiente) {		
		dao.salvar(ambiente);		
		liberarRecursos();
	}
	
	public void habilitaBotaoRemover(){
		if(id != null){
			btRemove.setVisibility(Button.VISIBLE);
		}
		else{
			btRemove.setVisibility(Button.INVISIBLE);
		}
	}


	
	private Ambiente buscarAmbiente(Long id) {
		//Aqui eh necessario realizar uma busca pelo id do ambiente 
		return dao.buscarAmbiente(id);
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
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		dao.close();
	}
	
	public void liberarRecursos(){
		dao.close();
	}
	
	public void updateLocation(Location location) {
		
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		
		campoLatitude.setText(String.valueOf(latitude));
		campoLongitude.setText(String.valueOf(longitude));
		
	}
		
	
	@Override
	public void onLocationChanged(Location location) {
		updateLocation(location);		
	}
	
	@Override
	public void onProviderDisabled(String arg0) {
		AlertDialog.Builder builder = new AlertDialog.Builder(CadastroEdicaoViewer.this);
		builder.setMessage("Your GPS is disabled! Would you like to enable it?").setCancelable(false).
		setPositiveButton("GPS enabled", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialogInterface, int arg) {
				
				Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(gpsOptionsIntent);
				
			}
		});
		
		builder.setNegativeButton("Do nothing", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				
				dialog.cancel();
				
			}
		});
		
		AlertDialog alert = builder.create();
		alert.show();
		
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		
		Toast.makeText(this, "Provedor de localzação definido: "+provider, Toast.LENGTH_SHORT).show();
		
	}
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	

}
