package com.lostincontext.ruledetails;

import android.util.Log;

import com.lostincontext.R;
import com.lostincontext.commons.list.Section;
import com.lostincontext.data.FenceCreator;
import com.lostincontext.data.GridBottomSheetItem;
import com.lostincontext.data.rules.LocationFenceVM;
import com.lostincontext.ruledetails.pick.BottomSheetItemSection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static com.google.common.collect.Lists.newArrayList;

public class RuleDetailsPresenter implements RuleDetailsContract.Presenter {


    private static final String TAG = RuleDetailsPresenter.class.getSimpleName();

    private final RuleDetailsContract.View view;

    private List<RuleDetailsItem> items = new ArrayList<>();


    @Inject RuleDetailsPresenter(RuleDetailsContract.View view) {
        this.view = view;
    }

    @Inject void setup() {
        view.setPresenter(this);
    }


    @Override public void start() {
        // items.add(new PlusItem());
        view.setItems(items);
    }

    @Override public void onRuleCreationItemClick(FenceCreator fence) {

    }

    @Override public void onPlaylistPickerClick() {

    }

    @Override public void onPlusButtonClick() {
        view.displayFenceChoice();

    }

    @Override public List<Section> provideFenceChoices() {

        List<GridBottomSheetItem> choices = newArrayList(new GridBottomSheetItem("Walking", R.drawable.ic_walk_24),
                                                         new GridBottomSheetItem("Running", R.drawable.ic_run_24),
                                                         new GridBottomSheetItem("On bicycle", R.drawable.ic_bike_24),
                                                         new GridBottomSheetItem("In vehicle", R.drawable.ic_car_24),
                                                         new GridBottomSheetItem("Plugged in", R.drawable.ic_headset_24),
                                                         new GridBottomSheetItem("Plugged out", R.drawable.ic_headset_24),
                                                         new GridBottomSheetItem(LocationFenceVM.HOME, R.drawable.ic_home_24),
                                                         new GridBottomSheetItem(LocationFenceVM.WORK, R.drawable.ic_work_24));

        BottomSheetItemSection fencesSection = new BottomSheetItemSection("Pick a condition", choices);


        List<GridBottomSheetItem> playlistPickers =
                newArrayList(new GridBottomSheetItem("Playlist", R.drawable.ic_music_note_24));

        BottomSheetItemSection mediaPickSection = new BottomSheetItemSection("pick a playlist", playlistPickers);

        return Arrays.<Section>asList(fencesSection,
                                      mediaPickSection);
    }

    @Override public void onGridBottomSheetItemClick(GridBottomSheetItem item) {
        Log.d(TAG, "onGridBottomSheetItemClick: " + item.name);
    }
}
