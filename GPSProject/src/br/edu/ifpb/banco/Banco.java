package br.edu.ifpb.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper{
	public static final String TABLE_AMBIENTE = "ambiente";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NOME = "nome";
	public static final String COLUMN_LATITUDE = "latidude";
	public static final String COLUMN_LONGITUDE = "longitude";
	public static final String COLUMN_DATE = "data";
	public static final String COLUMN_RAIO = "raio";
	public static final String COLUMN_DESCRICAO = "descricao";
	public static final String COLUMN_PERFIL = "perfil";
	
	public static final String[] colunas = new String[]{COLUMN_ID,COLUMN_NOME,COLUMN_LATITUDE,COLUMN_LONGITUDE,
		COLUMN_DATE,COLUMN_RAIO,COLUMN_DESCRICAO,COLUMN_PERFIL};
	
	private static final String DATABASE_NAME = "ambiente.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_AMBIENTE + " ( " 
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_NOME +" text not null, "
			+ COLUMN_LATITUDE + " double not null, "
			+ COLUMN_LONGITUDE +" double not null, "
			+ COLUMN_DATE + " text not null, " 
			+ COLUMN_RAIO + " double not null, "
			+ COLUMN_DESCRICAO + " text not null, " 
			+ COLUMN_PERFIL + " integer not null);";
	

	public Banco(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_AMBIENTE);
	    onCreate(db);
	}

}
