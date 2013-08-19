package br.edu.ifpb.services;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Location;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GPSService extends Service implements  Runnable, LocationListener{

	private LocationManager locationManager;
	private AudioManager audioManager;
	private String provider;
	private Geocoder geocoder;
	private double latitude, longitude;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES  = 5;
	private int count = 0;
	private int MAX = 10;
	private static final long MIN_TIME_UPDATES  = 1000 * 60 * 1;
	

	private Context con;
	
	
	@Override
	public void onCreate() {
		super.onCreate();		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i("iniciar Serviço", "Startando a Service GPS - onStartCommand");
		new Thread(this).start();
		return START_STICKY;
	}
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}	

	@Override
	public void run() {
		
		//imprime a localização atual do usuario!!
		printLocation();
		
	    //Para o serviço que está rodando em background;
		//stopSelf();
		
	}

	
		
	private void printLocation(){
		
		String provider = null;
		
		//Criterios definidos para escolher o melhor provedor de localização
		Criteria criteria = new Criteria(); 
		
		//Obter o gerenciador de localizacao do SO 
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		
		//locationManager.requestLocationUpdates(provider, 0 , 0, this);
		if(locationManager != null){
			
		//audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			
		//obter o provedor segundo os criterios definidos
		provider = locationManager.getBestProvider(criteria, false);
			
		//Obter a localização atual do usuário;
		Location location = locationManager.getLastKnownLocation(provider);
		
		if(location != null){				
			Log.i("INFO", "Provedor "+provider+" foi selecionado");
			onLocationChanged(location);				
			
			
		}
		else {
			Log.e("GPS", "Provedor nao foi selecionado");
		}
		
		}else {
			Log.e("GPS", "não foi possivel acessar LocationManager");
		}
		
	}
	
	private LocationManager getLocationManager(String provider,long min_time,float min_distance, LocationListener loc){
		
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(provider, min_time , min_distance, loc);
		
		return locationManager;
		
		
	}
	
	private String getNewLocation(Context context){
		
		String localeAdress= "No address found";
		
		geocoder = new Geocoder(context, Locale.getDefault());
		try {
			
			List<Address> adresses = geocoder.getFromLocation(latitude, longitude, 1);
			Address adress = adresses.get(0);		
			
			StringBuilder sb = new StringBuilder();
			
			String locality = adress.getLocality();
			String country = adress.getCountryName();
			String postalCode = adress.getPostalCode();
			
			sb.append(locality).append("\n");
			sb.append(country).append("\n");
			sb.append(postalCode).append("\n");
			
			localeAdress = sb.toString();			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return localeAdress;
		
	}
	

	@Override
	public void onLocationChanged(Location location) {
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		
		Log.i("Latitude", String.valueOf(latitude));
		Log.i("Longitude", String.valueOf(longitude));
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}


