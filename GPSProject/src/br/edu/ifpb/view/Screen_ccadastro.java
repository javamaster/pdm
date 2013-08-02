package br.edu.ifpb.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import br.edu.ifpb.R;

public class Screen_ccadastro extends Activity implements OnItemSelectedListener, OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_screen_ccadastro);
		
		Button btSave = (Button) findViewById(R.id.btSave);
		Button btCancel = (Button) findViewById(R.id.btCancel);
		
		btSave.setOnClickListener(this);
		btCancel.setOnClickListener(this);
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner_ringtone);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ringtone_modes, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(this);
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
