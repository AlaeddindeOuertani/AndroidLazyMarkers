package zgaw.lazymarkers.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zgaw.lazymarkers.model.GeoPointGrouped;
import zgaw.lazymarkers.model.GeoPointNormal;

/**
 * Created by Alaeddine Ouertani on 15/08/15.
 */
public class LazyMarkersCountryFragment extends BaseFragment{

    @Override
    protected List<GeoPointGrouped> getGroupedPoints(List<GeoPointNormal> normalPoints) {
        Map<String, GeoPointGrouped> groupedPoints = new HashMap<>();
        for (GeoPointNormal normalPoint : normalPoints) {

            if (groupedPoints.containsKey(normalPoint.getCountryName())) {
                GeoPointGrouped groupedPoint = groupedPoints.get(normalPoint.getCountryName());
                groupedPoint.increment();
                groupedPoints.remove(normalPoint.getCountryName());
                groupedPoints.put(normalPoint.getCountryName(), groupedPoint);
                }
            else {
                groupedPoints.put(normalPoint.getCountryName(), new GeoPointGrouped(normalPoint.getLatitude(), normalPoint.getLongitude(), ""));
            }

        }
        return new ArrayList<>(groupedPoints.values());
    }
}
