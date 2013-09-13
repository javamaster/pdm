package br.edu.ifpb.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import br.edu.ifpb.dao.AmbienteDao;
import br.edu.ifpb.model.Ambiente;
import br.edu.ifpb.util.GeoCoordinate;
import br.edu.ifpb.util.GeoUtils;

public class GPSTeste extends Service{
	
	private Thread triggerService;
	private LocationManager locationManager;
	private AudioManager audioManager;
	private LocationListener listner;
	private static final String PROVIDER = "PROVIDER";
	public static final String AMBIENTE = "AMBIENTE"; 
	public static final String LOCALIZACAO = "LOCALIZACAO";
	private static boolean ambienteSelected= false;
	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;
	
	// Distancia minima, em metros, para atualizar 
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
	private static final long MIN_TIME_BW_UPDATES = 0;
	private static Ambiente ambienteAtual;
	
	private AmbienteDao dao;
	private List<Ambiente> ambientes = null;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ComponentName startService(Intent service) {
		// TODO Auto-generated method stub
		return super.startService(service);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		
		this.dao = new AmbienteDao(getApplicationContext());
		
		Log.d("Context", String.valueOf(getApplicationContext()));

		//Recuperar a lista de ambintes do banco
		ambientes = getListAmbientes();
		
		//imprimir a lista de ambientes 
		printAmbientes();
		addLocationListner();
		
	 return START_STICKY;
	 
	}
	
	public void  printAmbientes() {
		
		if(ambientes!=null){
			for (int i = 0; i < ambientes.size(); i++) {
				Log.i("Ambiente", ambientes.get(i).toString());
			}
		}else{
			Log.e("Ambiente", "Nenhum Ambiente retornado");
		}
	}
	
	public List<Ambiente>  getListAmbientes() {
		try {
			
			//Abrindo conexão com o banco sqlite
			dao.open();
			
			if(dao != null){
				return dao.getAll();
			}
			else{
				Log.e("Error", "Dao nao foi instanciado");
			}
			
		} catch (Exception e) {
			Log.e("Error", "Não recupera a lista de ambientes - error -> "+e.getMessage());
			dao.close();
		}
		return null;
	}
	
	public void addLocationListner(){
		
		triggerService = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					
					Looper.prepare();
					
					locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
					audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
					
					Criteria c = new Criteria();					
					String provider = locationManager.getBestProvider(c, true);
					listner = new MyLocationListner();
					
					if(provider!=null){
						
						Log.d(PROVIDER, "MELHOR PROVEDOR SELECIONADO: "+provider);
						
						
						locationManager.requestLocationUpdates(provider,MIN_TIME_BW_UPDATES , MIN_DISTANCE_CHANGE_FOR_UPDATES,listner);
						
						Location location = locationManager.getLastKnownLocation(provider);
						
						
						if(location != null){
							Log.d(PROVIDER, "localização obtida:\n latitude "+location.getLatitude()+" Longitude: "+location.getLongitude());
							updateLocation(location);
						}
						else{
							Log.d(PROVIDER, "localização não foi obtida");						
						}
						
					}
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
		
		Location location = new Location("gps");
		location.setLatitude(-6.4035924);
		location.setLongitude(-37.9069351);
		
		ambiente1.setLocation(location);
		ambiente1.setNome("Biblioteca");
		ambiente1.setDescricao("Biblioteca do IFPB");
		ambiente1.setRaio(2500.0);
		ambiente1.setPerfil(Ambiente.VIBRACAO);
		ambiente1.setData_persist(new Date());
		
		
		Ambiente ambiente2 = new Ambiente();
		
		Location location2 = new Location("gps");
		location2.setLatitude(-6.844032);
		location2.setLongitude(-38.347366);
		
		ambiente2.setLocation(location2);
		ambiente2.setNome("Pátio");
		ambiente2.setPerfil(Ambiente.VIBRACAO);
		ambiente2.setDescricao("Pátio do IFPB");	
		ambiente2.setRaio(120);
		ambiente2.setData_persist(new Date());
		
		ambientes.add(ambiente1);
		ambientes.add(ambiente2);
		
		return ambientes;
	}
	
	
	public void updateLocation(Location location){
	
		
		double latitude, longitude;
		

		latitude = location.getLatitude();
		longitude = location.getLongitude();
		
		GeoCoordinate coordinate = new GeoCoordinate(latitude, longitude);
		
		Log.d("Latitude do Emulador ", String.valueOf(latitude));
	    Log.d("Longitude do Emulador ", String.valueOf(longitude));
	    
	//----------------------------------------------------------------------------------------
	    
	    if(GPSTeste.ambienteSelected){
			
	    	Location loc = ambienteAtual.getLocation();
	    	
			GeoCoordinate coordinateAnterior = new GeoCoordinate(loc.getLatitude(), loc.getLongitude());
			double distance2 = GeoUtils.geoDistanceInKm(coordinate, coordinateAnterior)*1000;
			
			if(distance2>ambienteAtual.getRaio()){
				//altera conf para outro ambiente
				Log.d(AMBIENTE, "Não está mais dentro do ambiente "+ambienteAtual.getNome());
				ambienteAtual = null;
				ambienteSelected = false;
				updateAmbiente(coordinate);
			}
			else{
				Log.d(AMBIENTE, "Usuario continua dentro do ambiente "+ambienteAtual.getNome());
			}
		}else{
			updateAmbiente(coordinate);
		}
	}
	 
	public int alterarConfiguracoesChamada(int perfil){
		
		int ringer = 0;
		
		switch (perfil) {
			case Ambiente.NORMAL:
				audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				ringer = Ambiente.NORMAL;
				break;
			case Ambiente.SILENCIOSO:
				audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
				ringer = Ambiente.SILENCIOSO;
				break;
			case Ambiente.VIBRACAO:
				audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
				ringer = Ambiente.VIBRACAO;
				break;
			}
		return ringer;
	} 
	    
	   
	public double calculateDistanceInMeters(GeoCoordinate geo, GeoCoordinate geo2){
		return (GeoUtils.geoDistanceInKm(geo, geo2))*1000;
	}
	    
	    
	public void updateAmbiente(GeoCoordinate geo){
		
		if(ambientes != null){
			for (int i = 0; i < ambientes.size(); i++) {
				
				Ambiente ambiente = ambientes.get(i);
				Location location = ambiente.getLocation();
				double raio = ambiente.getRaio();
				
				//Transforma os dados obtidos em GeoCoordinate
				GeoCoordinate geo2 = new GeoCoordinate(location.getLatitude(), location.getLongitude());
				
				double distance = calculateDistanceInMeters(geo, geo2);
				
				Log.i(LOCALIZACAO, "Distancia entre a posicao atual do usuario e o ambiente "+ambiente.getNome()+" eh "+distance);
				
				//O usuario está dentro do ambiente
				if(distance <= raio){
					
					//altera as conf. de chamadas para o ambiente					
					int ringer = alterarConfiguracoesChamada(ambiente.getPerfil());
					Log.d(AMBIENTE, "configuração definida para o perfil "+Ambiente.getRinger(ringer));
					ambienteAtual = ambiente;
					ambienteSelected = true;
					Log.d(AMBIENTE, "configuração definida para o ambiente "+ambiente.getNome());
					break;
				}
				
		}
		
		if(!ambienteSelected){
			Log.d(AMBIENTE, "Nenhum ambiente referenciado");
		}
	
		}
		else{
			Log.d(AMBIENTE, "Nenhum ambiente cadastrado");
		}
	
	}
	
	
	class MyLocationListner implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			updateLocation(location);			
		}

		@Override
		public void onProviderDisabled(String provider) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(GPSTeste.this);
			builder.setMessage("Your GPS is disabled! Would you like to enable it?").setCancelable(false).
			setPositiveButton("GPS enabled", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialogInterface, int arg) {
					
					Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCALE_SETTINGS);
					startActivity(gpsOptionsIntent);
					
				}
			});
			
			builder.setNegativeButton("Do nothing", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					
					dialog.cancel();
					
				}
			});
			
			AlertDialog alert = builder.create();
			alert.show();
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
