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

public class LocatorTracker extends Service implements LocationListener{
	
	private boolean isGPSEnabled = false;
	private boolean isNetworkEnabled = false;
	private boolean canGetLocation = false;
	
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES  = 5;
	
	private static final long MIN_TIME_UPDATES  = 1000 * 60 * 1;
	
	private Context context;

	
	public LocatorTracker() {}
	
	public LocatorTracker(Context context){
		this.context = context;
		
		printLocation();
	}
	
	
	private void printLocation(){
			
			LocationManager locationManager;
			String provider;
			
			
			
			locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
	//		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			
			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			
			//Permite definir alguns criterios para escolher um provedor
			Criteria criteria = new Criteria();
			
			//Obtém o melhor provedor, de acordo com os criterios definidos 
			provider = locationManager.getBestProvider(criteria, false);
			
			 //locationManager.getProvider(LocationManager.GPS_PROVIDER);
			//locationManager.getProvider(LocationManager.NETWORK_PROVIDER);
			
			Location location = locationManager.getLastKnownLocation(provider);
			locationManager.requestLocationUpdates(provider,MIN_TIME_UPDATES , MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
			
			if(location != null){
				
				Log.i("INFO", "Provedor "+provider+" foi selecionado");
				onLocationChanged(location);
				
			}
			else {
				Log.e("GPS", "Provedor nao foi selecionado");
			}
		}
		
	@Override
	public IBinder onBind(Intent ITENT) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onLocationChanged(Location loc) {
		double latitude = loc.getLatitude();
		double longitude = loc.getLongitude();
		
		Log.i("Latitude", String.valueOf(latitude));
		Log.i("Longitude", String.valueOf(longitude));
		
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
