package br.edu.ifpb.testes;

import br.edu.ifpb.dao.AmbienteDao;
import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
	
	private static Context context;

	@Override
	public void onCreate() {
		MyApplication.context = getApplicationContext();
	}
	
	public static Context getAppContext(){
		return MyApplication.context;
	}
	
	public static AmbienteDao getAmbienteDAO() {		
		return new AmbienteDao(getAppContext());
	}
	
	
}
