package br.edu.ifpb.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LocationReceiver extends BroadcastReceiver {

	private double latitude, longitude; 
	
	@Override
	public void onReceive(Context context, Intent calledIntent) {
		Log.d("LOC_RECEIVER", "Location RECEIVED!");

        latitude = calledIntent.getDoubleExtra("latitude", -1);
        longitude = calledIntent.getDoubleExtra("longitude", -1);
        
        updateRemote(latitude, longitude);

	}

	private void updateRemote(final double latitude, final double longitude )
    {
       Log.d("Localizacao", String.valueOf(latitude));
       Log.d("Localizacao", String.valueOf(latitude));
    }
}
