package zgaw.lazymarkers.model;

/**
 * Created by Alaeddine Ouertani on 15/08/15.
 */
public class GeoPoint {

    private double latitude;
    private double longitude;

    public GeoPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
