package br.edu.ifpb.testes;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


public class ContadorService extends Service implements Runnable, LocationListener{

	
	private static final int MAX = 10;
	private static final String CATEGORIA = "livro";
	protected int count;
	private boolean ativo;
	
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		Log.d(CATEGORIA, "ContadorService.onCreate()");
		ativo = true;
		
		//Delega para uma thread
		new Thread(this).start();
		
	}
	
	@Override
	public void onDestroy() {
		ativo = false;
		
		Log.i(CATEGORIA, "ContadorService.onDestroy");
	}
	@Override
	public void run() {
		
		while (ativo && count < MAX) {
			processService();
			Log.i(CATEGORIA, "ContadorService executando... "+count);
			count++;
		}
		
		Log.i(CATEGORIA, "Exemplo ");
		
		stopSelf();
	}

	private void processService() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onLocationChanged(Location loc) {
		
		Log.d("Localização", String.valueOf(loc.getLatitude()));
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

}
