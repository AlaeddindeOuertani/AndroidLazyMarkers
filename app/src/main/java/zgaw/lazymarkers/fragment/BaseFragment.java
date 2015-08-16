package zgaw.lazymarkers.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import zgaw.lazymarkers.R;
import zgaw.lazymarkers.model.GeoPointGrouped;
import zgaw.lazymarkers.model.GeoPointNormal;

/**
 * Created by Alaeddine Ouertani on 15/08/15.
 */
public abstract class BaseFragment extends Fragment implements GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraChangeListener{

    private GoogleMap googleMap;
    private static int ZOOM_SHOW_ALL = 4;
    private float zoom;

    private List<GeoPointNormal> normalPoints;
    private List<GeoPointGrouped> groupedPoints;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        normalPoints = getNormalPoints();
        groupedPoints = getGroupedPoints(normalPoints);

        initilizeMap();
        showGroupedMarkers();
        return view;
    }

    protected abstract List<GeoPointGrouped> getGroupedPoints(List<GeoPointNormal> normalPoints);

    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.getUiSettings().setRotateGesturesEnabled(false);
            googleMap.setOnMarkerClickListener(this);
            googleMap.setOnCameraChangeListener(this);
        }
    }

    protected void onSeekBarChanged() {
        groupedPoints = getGroupedPoints(normalPoints);
        if (isGroupedView()) {
            showGroupedMarkers();
        }
        else {
            showAllMarkers();
        }
    }

    private void showGroupedMarkers() {
        googleMap.clear();

        if (groupedPoints!=null) {
            for (GeoPointGrouped point : groupedPoints) {

                View viewMarker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.inflate_grouped_markers, null);
                ((TextView)viewMarker.findViewById(R.id.textview_marker_count)).setText(point.getCount());

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(point.getLatitude(), point.getLongitude()))
                        .icon(BitmapDescriptorFactory.fromBitmap(buildBitmap(viewMarker))));
            }
        }
    }

    private void showAllMarkers() {
        googleMap.clear();

        if (normalPoints!=null) {
            for (GeoPointNormal point : normalPoints) {
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(point.getLatitude(), point.getLongitude()))
                        .title(point.getTitle()));
            }
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        if (cameraPosition.zoom>=ZOOM_SHOW_ALL && zoom<ZOOM_SHOW_ALL) {
            showAllMarkers();
        }
        else if (cameraPosition.zoom<ZOOM_SHOW_ALL && zoom>=ZOOM_SHOW_ALL) {
            showGroupedMarkers();
        }
        zoom = cameraPosition.zoom;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (isGroupedView()) {
            LatLng trackedPosition = marker.getPosition();
            CameraUpdate center= CameraUpdateFactory.newLatLng(trackedPosition);
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(ZOOM_SHOW_ALL);
            googleMap.moveCamera(center);
            googleMap.animateCamera(zoom);
        }
        else {
            Toast.makeText(getActivity(), "Clicked on "+marker.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private boolean isGroupedView() {
        return zoom<ZOOM_SHOW_ALL;
    }

    private Bitmap buildBitmap(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    private List<GeoPointNormal> getNormalPoints () {
        List<GeoPointNormal> list = new ArrayList<>();
        list.add(new GeoPointNormal(48.858370, 2.294481, "Paris, Tour Effeil", "France"));
        list.add(new GeoPointNormal(48.872234, 2.775808, "Paris Disney Land", "France"));
        list.add(new GeoPointNormal(42.688659, 2.894833, "Perpignan", "France"));
        list.add(new GeoPointNormal(40.416775, -3.703790, "Madrid", "Spain"));
        list.add(new GeoPointNormal(41.385064, 2.173403, "Barcelona", "Spain"));
        list.add(new GeoPointNormal(36.806495, 10.181532, "Tunis", "Tunisia"));
        list.add(new GeoPointNormal(35.825603, 10.608395, "Sousse", "Tunisia"));
        list.add(new GeoPointNormal(41.902783, 12.496366, "Roma", "Italy"));
        list.add(new GeoPointNormal(45.465422, 9.185924, "Milano", "Italy"));
        list.add(new GeoPointNormal(40.712784, -74.005941, "New York", "United States"));
        list.add(new GeoPointNormal(37.774929, -122.419416, "San Francisco", "United States"));
        list.add(new GeoPointNormal(45.421530, -75.697193, "Point 12", "Canada"));
        list.add(new GeoPointNormal(52.939916, -73.549136, "Quebec", "Canada"));
        list.add(new GeoPointNormal(35.011636, 135.768029, "Kyoto", "Japan"));
        list.add(new GeoPointNormal(35.689487, 139.691706, "Tokyo", "Japan"));
        list.add(new GeoPointNormal(-34.603684, -58.381559, "Buenos Aires", "Argentina"));
        list.add(new GeoPointNormal(-32.889459, -68.845839, "Mendoza", "Argentina"));
        list.add(new GeoPointNormal(-33.450000, -70.666667, "Santiago de chile", "Chile"));
        list.add(new GeoPointNormal(-14.235004, -51.925280, "Brasilia", "Brasil"));
        list.add(new GeoPointNormal(-22.906847, -43.172896, "Rio de Janeiro", "Brasil"));
        list.add(new GeoPointNormal(39.904211, 116.407395, "Beijing", "China"));
        list.add(new GeoPointNormal(31.230416, 121.473701, "Shanghai", "China"));
        list.add(new GeoPointNormal(28.613939, 77.209021, "New Delhi", "India"));
        list.add(new GeoPointNormal(19.075984, 72.877656, "Bombay", "India"));
        list.add(new GeoPointNormal(28.535516, 77.391026, "Noida", "India"));
        list.add(new GeoPointNormal(-33.924869, 18.424055, "Cap Town", "South Africa"));
        list.add(new GeoPointNormal(-34.409200, 19.250444, "Hermanus", "South Africa"));
        return list;
    }
}