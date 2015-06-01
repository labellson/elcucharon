package com.labellson.elcucharon.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.labellson.elcucharon.model.Reserva;
import com.labellson.elcucharon.ui.activities.ListReservasActivasFragment;
import com.labellson.elcucharon.ui.activities.ListReservasPasadasFragment;

import java.util.List;

/**
 * Created by dani on 1/06/15.
 */
public class ViewPagerAdapter extends  FragmentStatePagerAdapter {

    private CharSequence[] titles;
    private int numTabs;
    private ListReservasActivasFragment mListActiva;
    private ListReservasPasadasFragment mListPasada;

    public ViewPagerAdapter(FragmentManager fm, CharSequence[] titles, List<Reserva> r_pasada, List<Reserva> r_activa){
        super(fm);

        this.titles = titles;
        numTabs = titles.length;
        mListActiva = new ListReservasActivasFragment();
        mListActiva.setmReservas(r_activa);
        mListPasada = new ListReservasPasadasFragment();
        mListPasada.setmReservas(r_pasada);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return mListPasada;
            case 1: return mListActiva;
            default: return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
