package br.com.mau;

import java.util.ArrayList;
import java.util.Iterator;

public class GeoTeste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		double latitudeFrom = -6.844364;
//		double longitudeFrom = -38.349055;
//		
//		double latitudeTo = -6.844032;
//		double longitudeTo = -38.347366;
//
//		GeoCoordinate coordinateSource = new GeoCoordinate(-7.5693537, -38.1023603);
//		GeoCoordinate coordinateDestination = new GeoCoordinate(-7.5568070, -38.1003287);
		
		
		//double distanceMetros = GeoUtils.geoDistanceInKm(coordinateSource, coordinateDestination)*1000;

//---------------------------COORDENADAS PARAIBA----------------------------------------------------------------------------
		
		GeoCoordinate coordenate1 = new GeoCoordinate(-6.4035924, -37.9069351);
		GeoCoordinate coordenate2 = new GeoCoordinate(-6.3987920, -37.8988864);
		GeoCoordinate coordenate3 = new GeoCoordinate(-6.3994892, -37.8882088);
		GeoCoordinate coordenate4 = new GeoCoordinate(-6.3939530, -37.8848047);
		
		ArrayList<GeoCoordinate> geocoordenates = new ArrayList<GeoCoordinate>();
		geocoordenates.add(coordenate2);	
		geocoordenates.add(coordenate3);
		geocoordenates.add(coordenate4);
		
		double distanceMetros = 0.0;
		
		for (Iterator<GeoCoordinate> iterator = geocoordenates.iterator(); iterator.hasNext();) {
			GeoCoordinate geoCoordinate = (GeoCoordinate) iterator.next();
			
			distanceMetros = GeoUtils.geoDistanceInKm(coordenate1, geoCoordinate)*1000;
			
			System.out.println(distanceMetros);
		}
		
		
		
		
	}

}
