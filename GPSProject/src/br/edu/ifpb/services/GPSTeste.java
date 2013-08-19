package br.edu.ifpb.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import br.edu.ifpb.model.Ambiente;
import br.edu.ifpb.util.GeoCoordinate;
import br.edu.ifpb.util.GeoUtils;

public class GPSTeste extends Service{
	
	private Thread triggerService;
	private LocationManager manager;
	private LocationListener listner;
	private static final String PROVIDER = "PROVIDER";
	private boolean ambienteSelected= false;
	private static List<Ambiente> ambientes;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		ambientes = recuperaAmbientes();
		addLocationListner();
		return START_STICKY;
	}
	
	public void addLocationListner(){
		
		triggerService = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					
					Looper.prepare();
					manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
					
					Criteria c = new Criteria();
					
					String provider = manager.getBestProvider(c, true);
					
					Log.d(PROVIDER, "MELHOR PROVEDOR SELECIONADO: "+provider);
					
					listner = new MyLocationListner();
					
					Location location = manager.getLastKnownLocation(provider);
					
					if(location != null){
						Log.d(PROVIDER, "localização obtida:\n latitude "+location.getLatitude());
					}
					else{
						Log.d(PROVIDER, "localização não foi obtida");						
					}
					manager.requestLocationUpdates(provider, 0, 0, listner);
					
					Looper.loop();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, "Thread Service");
		
		triggerService.start();
		
	}
	
	public List<Ambiente> recuperaAmbientes(){
		List<Ambiente> ambientes = new ArrayList<Ambiente>();
		
		Ambiente ambiente1 = new Ambiente();
		
		Location location = new Location("");
		location.setLatitude(-6.84436);
		location.setLongitude(-38.349055);
		
		ambiente1.setLocation(location);
		ambiente1.setNome("Biblioteca");
		ambiente1.setDescricao("Biblioteca do IFPB");
		ambiente1.setRaio(100);
		ambiente1.setData_persist(new Date());
		
		
		Ambiente ambiente2 = new Ambiente();
		
		Location location2 = new Location("");
		location2.setLatitude(-6.844032);
		location2.setLongitude(-38.347366);
		
		ambiente2.setLocation(location2);
		ambiente2.setNome("Pátio");
		ambiente2.setDescricao("Pátio do IFPB");
		ambiente2.setRaio(10);
		ambiente2.setData_persist(new Date());
		
		ambientes.add(ambiente1);
		ambientes.add(ambiente2);
		
		return ambientes;
	}
	
	
	public static void updateLocation(Location location){
	
		
		double latitude, longitude;
		
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		
	
		//Encapsula o ponto de localização atual
		GeoCoordinate coordinate = new GeoCoordinate(latitude, longitude);
		
		//obtem os dados da base
		Ambiente ambiente = ambientes.get(0);
		
		//Cria mais um coordinate para os dados provenientes do banco
		GeoCoordinate coordinate2 = new GeoCoordinate(ambiente.getLocation().getLatitude()
				, ambiente.getLocation().getLongitude());
		
		GeoUtils utils = new GeoUtils();
		
		@SuppressWarnings("static-access")
		double d = utils.geoDistanceInKm(coordinate, coordinate2)*1000;
		Log.d("Distancia para a biblioteca ", String.valueOf(d));
		
		if(d<ambiente.getRaio()){
			Log.d("Distancia menor", "O usuario está dentro do ambiente "+ambiente.getNome());
		}
		else{
			Log.d("Distancia maior", "O usuario está fora de um ambiente ");
		}
		
		Log.d("Localizacao", String.valueOf(latitude));
	       Log.d("Localizacao", String.valueOf(longitude));
	}
	
	class MyLocationListner implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			updateLocation(location);			
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
}
