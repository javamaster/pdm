package br.edu.ifpb.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import br.edu.ifpb.R;

public class Main_Screen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main__screen, menu);
		return true;
	}

}
