package br.com.mau;

public class GeoTeste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		double latitudeFrom = -6.843676;
		double longitudeFrom = -38.346351;
		
		double latitudeTo = -6.844035;
		double longitudeTo = -38.346881;
		
		GeoCoordinate coordinateSource = new GeoCoordinate(latitudeFrom, longitudeFrom);
		GeoCoordinate coordinateDestination = new GeoCoordinate(latitudeTo, longitudeTo);
		
		
		double distanceMetros = GeoUtils.geoDistanceInKm(coordinateSource, coordinateDestination);
			
		System.out.printf("%b\n", distanceMetros<0.088?true:false,distanceMetros);
		System.out.print(distanceMetros*1000);
		
		
		
	}

}
