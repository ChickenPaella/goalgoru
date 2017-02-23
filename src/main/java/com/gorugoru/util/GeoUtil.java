package com.gorugoru.util;

public class GeoUtil {
	
	/**
	 * 거리계산
	 * Haversine formula
	 * @param latitude1
	 * @param longitude1
	 * @param latitude2
	 * @param longitude2
	 * @return distance in km
	 */
	public static double getDistanceFromLatLng(double latitude1, double longitude1, double latitude2, double longitude2) {
		final int R = 6371; // Radius of the earth in km

		double dLat = deg2rad(latitude2 - latitude1); // deg2rad below
		double dLong = deg2rad(longitude2 - longitude1);

		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(deg2rad(latitude1)) * Math.cos(deg2rad(latitude2)) * Math.pow(Math.sin(dLong / 2), 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = R * c; // Distance in km
		
		return d; 
	}

	private static double deg2rad(double x) {
		final double p = 0.017453292519943295;    // Math.PI / 180
		return x * p;
	}
}
