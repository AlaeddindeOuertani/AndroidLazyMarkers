package zgaw.lazymarkers.model;

/**
 * Created by Alaeddine Ouertani on 15/08/15.
 */
public class GeoPointGrouped extends GeoPoint{

    private String code;
    private int count;

    public GeoPointGrouped(double latitude, double longitude, String code) {
        super(latitude, longitude);
        this.code = code;
        count = 1;
    }

    public String getCode() {
        return code;
    }

    public void increment() {
        count++;
    }

    public String getCount() {
        return new StringBuilder("").append(count).toString();
    }
}
