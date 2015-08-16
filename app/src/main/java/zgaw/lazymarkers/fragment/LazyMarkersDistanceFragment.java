package zgaw.lazymarkers.fragment;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zgaw.lazymarkers.R;
import zgaw.lazymarkers.model.GeoPoint;
import zgaw.lazymarkers.model.GeoPointGrouped;
import zgaw.lazymarkers.model.GeoPointNormal;

/**
 * Created by Alaeddine Ouertani on 15/08/15.
 */
public class LazyMarkersDistanceFragment extends BaseFragment implements SeekBar.OnSeekBarChangeListener{

    private static final int DISTANCE_MIN = 0; // distance in Km
    private static final int DISTANCE_MAX = 400; // distance in Km
    private int distanceTolerated = (DISTANCE_MIN + DISTANCE_MAX) / 2; // distance in Km

    private SeekBar seekBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        view.findViewById(R.id.layout_seekbar).setVisibility(View.VISIBLE);
        seekBar = (SeekBar) view.findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(this);

        return view;
    }

    @Override
    protected List<GeoPointGrouped> getGroupedPoints(List<GeoPointNormal> normalPoints) {

        Map<String, GeoPointGrouped> groupedPoints = new HashMap<>();
        for (GeoPointNormal normalPoint : normalPoints) {

            GeoPointGrouped nearestGroupPoint = getNearestPoint(groupedPoints, normalPoint);
            if (nearestGroupPoint!=null) {
                nearestGroupPoint.increment();
                groupedPoints.remove(nearestGroupPoint.getCode());
                groupedPoints.put(nearestGroupPoint.getCode(), nearestGroupPoint);
            }
            else {
                groupedPoints.put(normalPoint.getTitle(), new GeoPointGrouped(normalPoint.getLatitude(), normalPoint.getLongitude(), normalPoint.getTitle()));
            }

        }
        return new ArrayList<>(groupedPoints.values());
    }

    private GeoPointGrouped getNearestPoint(Map<String, GeoPointGrouped> groupedPoints, GeoPointNormal normalPoint) {
        if (groupedPoints.size()>0) {
            for (int i=0; i<groupedPoints.size(); i++) {
                GeoPointGrouped groupedPoint = (new ArrayList<>(groupedPoints.values())).get(i);
                if (getDistanceBetweenTwoPoints(groupedPoint, normalPoint) <= distanceTolerated) {
                    return groupedPoint;
                }
            }
        }
        return null;
    }

    private double getDistanceBetweenTwoPoints(GeoPoint point1, GeoPoint point2) {
        Location locationA = new Location("point1");
        locationA.setLatitude(point1.getLatitude());
        locationA.setLongitude(point1.getLongitude());
        Location locationB = new Location("point2");
        locationB.setLatitude(point2.getLatitude());
        locationB.setLongitude(point2.getLongitude());
        return locationA.distanceTo(locationB)/1000;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        distanceTolerated = (seekBar.getProgress() * DISTANCE_MAX / 100) + ((100 - seekBar.getProgress()) * DISTANCE_MIN /100);
        onSeekBarChanged();
        Toast.makeText(getActivity(), "Tolerated distance = "+distanceTolerated+"km", Toast.LENGTH_SHORT).show();
    }
}
