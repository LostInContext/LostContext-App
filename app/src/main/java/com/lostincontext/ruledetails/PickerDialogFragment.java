package com.lostincontext.ruledetails;


import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lostincontext.R;
import com.lostincontext.commons.list.Section;
import com.lostincontext.ruledetails.pick.GridBottomSheetItem;
import com.lostincontext.databinding.RuleDetailsBottomSheetBinding;
import com.lostincontext.ruledetails.pick.PickerDialogCallback;
import com.lostincontext.ruledetails.pick.RulePickAdapter;

import java.util.List;


// todo handle rotations
public class PickerDialogFragment extends BottomSheetDialogFragment implements PickerDialogCallback {

    public static final String TAG = "PickerDialogFragment";

    public static PickerDialogFragment newInstance() {
        return new PickerDialogFragment();
    }


    private RulePickAdapter adapter = new RulePickAdapter(this);

    private PickerDialogCallback callback;


    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        RuleDetailsBottomSheetBinding binding = DataBindingUtil.inflate(inflater,
                                                                        R.layout.rule_details_bottom_sheet,
                                                                        container,
                                                                        false);

        RecyclerView recyclerView = binding.recyclerView;
        Resources resources = getResources();
        final int span = 3;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), span);
        recyclerView.setLayoutManager(layoutManager);
        int space = resources.getDimensionPixelSize(R.dimen.grid_spacing);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case R.id.view_type_section_header:
                        return span;
                    case R.id.view_type_grid_bottom_sheet_item:
                        return 1;
                }
                throw new RuntimeException("unhandled view type");
            }
        });


        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);

        return binding.getRoot();
    }

    public void registerCallback(PickerDialogCallback callback) {
        this.callback = callback;
    }

    public void setSections(List<Section> sections) {
        adapter.setSections(sections);
    }

    @Override public void onGridBottomSheetItemClick(GridBottomSheetItem item) {
        dismiss();
        callback.onGridBottomSheetItemClick(item);
    }

}
