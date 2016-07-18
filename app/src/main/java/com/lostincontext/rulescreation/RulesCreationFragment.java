package com.lostincontext.rulescreation;


import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.lostincontext.R;
import com.lostincontext.commons.list.Section;
import com.lostincontext.databinding.RulesCreationScreenFragmentBinding;
import com.lostincontext.rulescreation.display.EditLocationDialog;

import java.util.List;

public class RulesCreationFragment extends Fragment implements RulesCreationContract.View {

    private RulesCreationContract.Presenter presenter;
    private EditLocationDialog editPlaceDialog;

    private RulesCreationAdapter adapter;

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

        RecyclerView recyclerView = binding.recyclerView;
        Resources resources = getResources();
        final int span = 3;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), span);
        recyclerView.setLayoutManager(layoutManager);
        int space = resources.getDimensionPixelSize(R.dimen.grid_spacing);
        adapter = new RulesCreationAdapter(presenter);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case R.id.view_type_section_header:
                    case R.id.view_type_playlist_picker:
                        return span;
                    case R.id.view_type_rule_creator:
                        return 1;
                }
                throw new RuntimeException("unhandled view type");
            }
        });


        recyclerView.setAdapter(adapter);

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

    @Override
    public void setSections(List<Section> sections) {
        adapter.setSections(sections);
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
