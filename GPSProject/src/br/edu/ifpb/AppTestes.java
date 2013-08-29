package br.edu.ifpb;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import br.edu.ifpb.dao.AmbienteDao;
import br.edu.ifpb.model.Ambiente;

public class AppTestes extends Activity {

	private AmbienteDao dao ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_testes);
		
		dao = new AmbienteDao(this);
		
		dao.open();
		
		Location loc = new Location("");
		loc.setLatitude(12.345555);
		loc.setLatitude(6.222555);
		
		Ambiente ambiente = new Ambiente();
		ambiente.setNome("Dan");
		ambiente.setRaio(13.657);
		ambiente.setLocation(loc);
		ambiente.setDescricao("nfsçkdljf~ks");
		ambiente.setPerfil(Ambiente.SILENCIOSO);
		ambiente.setData_persist(new Date());
		
		dao.create(ambiente);
		
		List<Ambiente> ambs = dao.getAll();
		
		for(int i = 0;i<ambs.size();i++){
			Log.d("Ambiente", ambs.get(i).toString());
			System.out.println(ambs.get(i));
		}
		dao.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.app_testes, menu);
		return true;
	}

}
