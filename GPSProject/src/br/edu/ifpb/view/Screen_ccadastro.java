package br.edu.ifpb.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.location.Location;
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
import android.widget.Spinner;
import android.widget.Toast;
import br.edu.ifpb.R;
import br.edu.ifpb.dao.AmbienteDao;
import br.edu.ifpb.model.Ambiente;

public class Screen_ccadastro extends Activity implements OnItemSelectedListener, OnClickListener{

	@SuppressWarnings("unused")
	private Ambiente ambiente;
	private String ringtone_mode_selected;
	private Button btSave, btCancel;
	private Long id;
	private List<Ambiente> ambientes;
	private AmbienteDao dao;
	private EditText campoNome, campoRaio, campoLatitude, campoLongitude, campoPerfil;
	
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
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner_ringtone);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ringtone_modes, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(this);
		
		
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
				
		try {
			raio = Double.valueOf(campoRaio.getText().toString());
		} catch (NumberFormatException e) {
			//Tratar exception aqui
		}
		
		Ambiente ambiente = new Ambiente();
		ambiente.setNome(campoNome.getText().toString());
		ambiente.setRaio(raio);
		ambiente.setData_persist(new Date());
		
		Location loc =new Location("");
		loc.setLatitude(Double.valueOf(campoLatitude.getText().toString()));
		loc.setLatitude(Double.valueOf(campoLatitude.getText().toString()));
		
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
		
		//Persiste o ambiente
		dao.create(ambiente);
		
		//Exibe uma mensagem
		Toast.makeText(this, "Ambiente Salvo!!", Toast.LENGTH_SHORT).show();
		
		finish();
	}
	

	private List<Ambiente> repositorio(){
		//Aqui estamos apenas simulando uma busca no repositorio
		ambientes = new ArrayList<Ambiente>();
		ambientes.add(new Ambiente(1,"Biblioteca", new Date(), new Location(""), 130, ""));
		ambientes.add(new Ambiente(2,"Pátio", new Date(), new Location(""), 150, ""));
		ambientes.add(new Ambiente(3,"Sala", new Date(), new Location(""), 100, ""));

		return ambientes;
	}

	private Ambiente buscarAmbiente(Long id) {
		//Aqui eh necessario realizar uma busca pelo id do ambiente 
		return repositorio().get(id.intValue());
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
	public void onClick(View v) {
		
		Button button = (Button)v;
		if(button.getId() == 0x7f09000f){
			
			
			
		}
		
		
	}

}
