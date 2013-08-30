package br.edu.ifpb.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;
import br.edu.ifpb.banco.Banco;
import br.edu.ifpb.model.Ambiente;

public class AmbienteDao {
	private SQLiteDatabase database;
	
	private String[] columns = { Banco.COLUMN_ID , Banco.COLUMN_NOME, Banco.COLUMN_LATITUDE, 
			Banco.COLUMN_LONGITUDE, Banco.COLUMN_DATE,Banco.COLUMN_RAIO,Banco.COLUMN_DESCRICAO, Banco.COLUMN_PERFIL};
	private Banco banco;
	
	public AmbienteDao(Context context){
		this.banco = new Banco(context);
	}
	
	public void open() throws SQLException {
		 database = banco.getWritableDatabase();
	}
	
	public void close() {
		 banco.close();
	}
	
	public long salvar(Ambiente ambiente) {
		long id = ambiente.getId();
		
		if(id != 0){
			atualizar(ambiente);
		}
		else{
			id = create(ambiente);
		}
		
		return id;
	}
	
	public long create(Ambiente ambiente) {
		 ContentValues values = new ContentValues();
		 
		 values.put(Banco.COLUMN_NOME, ambiente.getNome());
		 values.put(Banco.COLUMN_LATITUDE, ambiente.getLocation().getLatitude());
		 values.put(Banco.COLUMN_LONGITUDE, ambiente.getLocation().getLongitude());
		 values.put(Banco.COLUMN_DATE, ambiente.getData_persist().toString());
		 
		 values.put(Banco.COLUMN_RAIO, ambiente.getRaio());
		 values.put(Banco.COLUMN_DESCRICAO, ambiente.getDescricao());
		 values.put(Banco.COLUMN_PERFIL, ambiente.getPerfil());
		 
		 long insertId = database.insert(Banco.TABLE_AMBIENTE , null, values);
		 	 
		 return insertId;		 
	}
	
	public void delete(Ambiente ambiente) {
		long id = ambiente.getId();
		database.delete(Banco.TABLE_AMBIENTE, Banco.COLUMN_ID + " = " + id, null);
	}
	
	public List <Ambiente> getAll() {
		
		 List <Ambiente> ambientes = new ArrayList <Ambiente>();
		 
		 Cursor cursor = database.query(Banco.TABLE_AMBIENTE ,columns , null, null, null, null, null);
		 cursor.moveToFirst();
		 
		 while (!cursor.isAfterLast()) {
			 
		 Ambiente ambiente = new Ambiente();
		 
		 ambiente.setId(cursor.getInt(0));
		 ambiente.setNome(cursor.getString(1));
		 
		 Location location = new Location("");
		 location.setLatitude(cursor.getDouble(2));
		 location.setLongitude(cursor.getDouble(3));
		 ambiente.setLocation(location);
		 ambiente.setData_persist(new Date());
		 ambiente.setRaio(cursor.getDouble(5));
		 ambiente.setDescricao(cursor.getString(6));
		 ambiente.setPerfil(cursor.getInt(7));
		 ambientes.add(ambiente);
		 
		 cursor.moveToNext();
		 }
		 
		cursor.close();
		
	 return ambientes;
	 
	}
	
	public void atualizar(Ambiente ambiente){
		ContentValues values = new ContentValues();
		 
		 values.put(Banco.COLUMN_NOME, ambiente.getNome());
		 values.put(Banco.COLUMN_LATITUDE, ambiente.getLocation().getLatitude());
		 values.put(Banco.COLUMN_LONGITUDE, ambiente.getLocation().getLongitude());
		 values.put(Banco.COLUMN_DATE, ambiente.getData_persist().toString());
		 
		 values.put(Banco.COLUMN_RAIO, ambiente.getRaio());
		 values.put(Banco.COLUMN_DESCRICAO, ambiente.getDescricao());
		 values.put(Banco.COLUMN_PERFIL, ambiente.getPerfil());
		 
		 String id = String.valueOf(ambiente.getId());
		 
		 String where = Banco.COLUMN_ID + "=?";
		 
		 String[] whereArgs = new String[]{id};
		 
		 int count = atualizar(values, where, whereArgs);
		 
	}
	
	private int atualizar(ContentValues values, String where, String[] whereArgs){
		
		int count = database.update(Banco.TABLE_AMBIENTE, values, where, whereArgs);
		
		Log.i("Atualizacao", "Atualizou [ "+count+" ] registros");
		
		return count;
	}
	
	public void remove(long id){
		database.delete(Banco.TABLE_AMBIENTE, Banco.COLUMN_ID + " = " + id, null);
		Log.i("Remover", "Removeu registro");
	}
	
	public Ambiente buscarAmbiente(long id) {
		
		Cursor c = database.query(true, Banco.TABLE_AMBIENTE, Banco.colunas, Banco.COLUMN_ID+"="+id, null,
				null, null, null, null);
		
		if(c.getCount()>0){
			c.moveToFirst();
			
			Location loc = new Location("");
			loc.setLatitude(c.getDouble(2));
			loc.setLongitude(c.getDouble(3));
			
			Ambiente ambiente = new Ambiente();
			ambiente.setId(c.getInt(0));
			ambiente.setNome(c.getString(1));
			ambiente.setLocation(loc);
			ambiente.setRaio(c.getDouble(5));
			ambiente.setData_persist(new Date());
			ambiente.setDescricao(c.getString(6));
			ambiente.setPerfil(c.getInt(7));
			
			return ambiente;
		}
		return null;
	}
}
