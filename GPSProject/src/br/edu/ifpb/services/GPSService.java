package br.edu.ifpb.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GPSService extends Service implements LocationListener, Runnable {

	private LocationManager locationManager;
	private AudioManager audioManager;
	private String provider;
	
	@Override
	public void onCreate() {
		
		Log.i("Criar Serviço", "Startando a Service GPS");
		
		//Delega para uma thread
		new Thread(this).start();
		
	}
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public void onLocationChanged(Location location) {
//		
//		Log.d("Resposta do GPS", String.valueOf(location.getLatitude()));
//		Log.d("Resposta do GPS", String.valueOf(location.getLongitude()));
//		
//	}

	

	@Override
	public void run() {
		
		//imprime a localização atual do usuario!!
		printLocation();
		
		//Para o serviço que está rodando em background;
		stopSelf();
		
	}

	
	private void printLocation(){
		
		LocationManager locationManager;
		String provider;
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		//Permite definir alguns criterios para escolher um provedor
		Criteria criteria = new Criteria();
		
		//Obtém o melhor provedor, de acordo com os criterios definidos 
		provider = locationManager.getBestProvider(criteria, false);
		
		 //locationManager.getProvider(LocationManager.GPS_PROVIDER);
		//locationManager.getProvider(LocationManager.NETWORK_PROVIDER);
		
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		if(location != null){
			
			Log.i("INFO", "Provedor "+provider+" foi selecionado");
			onLocationChanged(location);
			
		}
		else {
			Log.e("GPS", "Provedor nao foi selecionado");
		}
	}


	@Override
	public void onLocationChanged(Location loc) {

		double latitude = loc.getLatitude();
		double longitude = loc.getLongitude();
		
		Log.i("Latitude", String.valueOf(latitude));
		Log.i("Longitude", String.valueOf(longitude));
		
	}


	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
}
