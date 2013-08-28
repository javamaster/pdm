package br.edu.ifpb.model;

import java.util.Date;

import android.location.Location;

public class Ambiente {
	
	private String nome;
	private Date data_persist;
	private Location location;
	private double raio;
	private String descricao;
	private int perfil;
	public static final int SILENCIOSO = 1;
	public static final int VIBRACAO = 2;
	public static final int NORMAL = 0;
	
	public Ambiente() {}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getData_persist() {
		return data_persist;
	}
	public void setData_persist(Date data_persist) {
		this.data_persist = data_persist;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public double getRaio() {
		return raio;
	}
	
	public int getPerfil() {
		return perfil;
	}

	public void setPerfil(int perfil) {
		this.perfil = perfil;
	}

	public void setRaio(double raio) {
		this.raio = raio;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Ambiente(String nome, Date data_persist, Location location,
			double raio, String descricao) {
		super();
		this.nome = nome;
		this.data_persist = data_persist;
		this.location = location;
		this.raio = raio;
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Nome: "+nome+" Raio: "+raio+" Latitude: "+location.getLatitude();
	}
}


