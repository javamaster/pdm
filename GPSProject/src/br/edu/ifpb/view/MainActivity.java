package br.edu.ifpb.view;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import br.edu.ifpb.R;

public class MainActivity extends Activity implements LocationListener {

	private LocationManager manager;
	private TextView longitudetxt;
	private TextView latitudetxt;
	private String provider;
	private AudioManager audio_manager;
	private TextView ringtone_mode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		latitudetxt = (TextView) findViewById(R.id.latitudetxt);
		ringtone_mode = (TextView) findViewById(R.id.mode_ringtone);
		longitudetxt = (TextView) findViewById(R.id.longitudetxt);

		manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		audio_manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		// Altera o modo atual do ringtone para o modo Silencioso
		audio_manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

		// Obtem o modo atual do ringtone
		int mode = audio_manager.getRingerMode();

		String ringerMode = "DEFAULT";

		switch (mode) {
		case 0:
			ringerMode = "SILENCIOSO";
			break;
		case 1:
			ringerMode = "VIBRAÇÃO";
			break;
		case 2:
			ringerMode = "NORMAL";
			break;

		default:
			ringerMode = "DEFAULT";
			break;
		}

		// Exibe a constante relacionada ao Ringtone
		ringtone_mode.setText(ringerMode);

		Criteria criteria = new Criteria();
		provider = manager.getBestProvider(criteria, false);
		Location location = manager.getLastKnownLocation(provider);

		if (location != null) {
			Log.d("MainActivity", "Provider " + provider + "foi selecionado!!");
			onLocationChanged(location);

		} else {
			latitudetxt.setText(R.string.location_not_available);
			longitudetxt.setText(R.string.location_not_available);
			Log.d("MainActivity", "Nenhuma localização encontrada!");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		manager.requestLocationUpdates(provider, 400, 1, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		manager.removeUpdates(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {

		double lat = location.getLatitude();
		double lon = location.getLongitude();

		latitudetxt.setText(getString(R.string.point, lat));
		longitudetxt.setText(getString(R.string.point, lon));

		Log.i("Latitude", String.valueOf(lat));
		Log.i("Longitude", String.valueOf(lon));
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Provider desabilitado " + provider,
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Novo provider " + provider, Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

}
