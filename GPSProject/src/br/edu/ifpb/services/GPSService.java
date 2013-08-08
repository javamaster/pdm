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

public class GPSService extends Service implements LocationListener, Runnable {

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
		
		Log.i("Criar Serviço", "Startando a Service GPS");
		
//		geocoder = new Geocoder(this,Locale.getDefault());
		
		//Delega para uma thread
		new Thread(this).start();
		
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
		
		LocationManager locationManager;
		String provider;
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		//Permite definir alguns criterios para escolher um provedor
		Criteria criteria = new Criteria();
		
		if (locationManager != null) {
			//Obtém o melhor provedor, de acordo com os criterios definidos 
			provider = locationManager.getBestProvider(criteria, false);
			
			 //locationManager.getProvider(LocationManager.GPS_PROVIDER);
			//locationManager.getProvider(LocationManager.NETWORK_PROVIDER);
			
			Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			//locationManager.requestLocationUpdates(provider,MIN_TIME_UPDATES , MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
			
			if(location != null){				
				Log.i("INFO", "Provedor "+provider+" foi selecionado");
				onLocationChanged(location);				
			}
			else {
				Log.e("GPS", "Provedor nao foi selecionado");
			}
		
		}
		else{
			
			Log.e("GPS", "Não foi possivel acessar a API do gps");
		}
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
