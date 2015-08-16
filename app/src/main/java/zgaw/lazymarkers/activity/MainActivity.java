package zgaw.lazymarkers.activity;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import zgaw.lazymarkers.R;
import zgaw.lazymarkers.fragment.LazyMarkersCountryFragment;
import zgaw.lazymarkers.fragment.LazyMarkersDistanceFragment;

/**
 * Created by Alaeddine Ouertani on 15/08/15.
 */
public class MainActivity extends ActionBarActivity{

    private Fragment currentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(new LazyMarkersDistanceFragment());
    }

    private void replaceFragment(Fragment fragment) {
        currentFragment = fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_filter_by_distannce:
                if (!(currentFragment instanceof LazyMarkersDistanceFragment)) {
                    replaceFragment(new LazyMarkersDistanceFragment());
                }
                return true;
            case R.id.menu_filter_by_country_name:
                if (!(currentFragment instanceof LazyMarkersCountryFragment)) {
                    replaceFragment(new LazyMarkersCountryFragment());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}