package br.edu.ifpb.testes;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
	
	private static Context context;

	@Override
	public void onCreate() {
		MyApplication.context = getBaseContext();
	}
	
	public static Context getAppContext(){
		return MyApplication.context;
	}
}
