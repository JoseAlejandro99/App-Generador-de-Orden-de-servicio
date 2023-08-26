package com.kalex.generadordeordendeservicio.ui.Buscar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.kalex.generadordeordendeservicio.R;

    public class BuscarFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_buscar, container, false);
            return rootView;
        }

    }