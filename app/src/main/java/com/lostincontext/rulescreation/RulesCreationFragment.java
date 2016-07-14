package com.lostincontext.rulescreation;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.lostincontext.R;
import com.lostincontext.databinding.RulesCreationScreenFragmentBinding;
import com.lostincontext.rulescreation.display.EditLocationDialog;

public class RulesCreationFragment extends Fragment implements RulesCreationContract.View {

    private RulesCreationContract.Presenter presenter;
    private EditLocationDialog editPlaceDialog;

    public static RulesCreationFragment newInstance() {
        return new RulesCreationFragment();
    }


    public RulesCreationFragment() { }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        RulesCreationScreenFragmentBinding binding = DataBindingUtil.inflate(inflater,
                                                                             R.layout.rules_creation_screen_fragment,
                                                                             container,
                                                                             false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(RulesCreationContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void showDialog() {
        FragmentManager fm = this.getActivity().getSupportFragmentManager();
        editPlaceDialog = new EditLocationDialog();
        editPlaceDialog.show(fm, "fragment_edit_location");
    }

    @Override public void setPlace(Intent data) {
        Place place = PlacePicker.getPlace(getActivity(), data);
        String toastMsg = String.format("Place: %s", place.getName());
        Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
        if (editPlaceDialog != null) {
            editPlaceDialog.setTextPlace(place.getAddress());
        }
    }
}
