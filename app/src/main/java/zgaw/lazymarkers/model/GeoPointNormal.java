package zgaw.lazymarkers.model;

/**
 * Created by Alaeddine Ouertani on 15/08/15.
 */
public class GeoPointNormal extends GeoPoint{

    private String title;
    private String countryName;

    public GeoPointNormal(double latitude, double longitude, String title, String countryName) {
        super(latitude, longitude);
        this.title = title;
        this.countryName = countryName;
    }

    public String getTitle() {
        return title;
    }

    public String getCountryName() {
        return countryName;
    }
}
