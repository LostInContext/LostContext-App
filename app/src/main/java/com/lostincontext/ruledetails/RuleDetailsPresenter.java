package com.lostincontext.ruledetails;

import android.util.Log;

import com.lostincontext.R;
import com.lostincontext.commons.list.Section;
import com.lostincontext.data.FenceCreator;
import com.lostincontext.data.GridBottomSheetItem;
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

    public enum Picker {
        WALK,
        RUN,
        BIKE,
        CAR,
        PLUG_IN,
        PLUG_OUT,
        HOME,
        WORK,
        PLAYLIST
    }

    @Override public List<Section> provideFenceChoices() {

        List<GridBottomSheetItem> choices = newArrayList(new GridBottomSheetItem("Walking",
                                                                                 R.drawable.ic_walk_24,
                                                                                 Picker.WALK),
                                                         new GridBottomSheetItem("Running",
                                                                                 R.drawable.ic_run_24,
                                                                                 Picker.RUN),
                                                         new GridBottomSheetItem("On bicycle",
                                                                                 R.drawable.ic_bike_24,
                                                                                 Picker.BIKE),
                                                         new GridBottomSheetItem("In vehicle",
                                                                                 R.drawable.ic_car_24,
                                                                                 Picker.CAR),
                                                         new GridBottomSheetItem("Plugged in",
                                                                                 R.drawable.ic_headset_24,
                                                                                 Picker.PLUG_IN),
                                                         new GridBottomSheetItem("Plugged out",
                                                                                 R.drawable.ic_headset_24,
                                                                                 Picker.PLUG_OUT),
                                                         new GridBottomSheetItem("At home",
                                                                                 R.drawable.ic_home_24,
                                                                                 Picker.HOME),
                                                         new GridBottomSheetItem("At work",
                                                                                 R.drawable.ic_work_24,
                                                                                 Picker.WORK));

        BottomSheetItemSection fencesSection = new BottomSheetItemSection("Pick a condition", choices);


        List<GridBottomSheetItem> playlistPickers = newArrayList(new GridBottomSheetItem("Playlist",
                                                                                         R.drawable.ic_music_note_24,
                                                                                         Picker.PLAYLIST));

        BottomSheetItemSection mediaPickSection = new BottomSheetItemSection("pick a playlist", playlistPickers);

        return Arrays.<Section>asList(fencesSection,
                                      mediaPickSection);
    }

    @Override public void onGridBottomSheetItemClick(GridBottomSheetItem item) {
       switch (item.picker) {

           case WALK:
               break;
           case RUN:
               break;
           case BIKE:
               break;
           case CAR:
               break;
           case PLUG_IN:
               break;
           case PLUG_OUT:
               break;
           case HOME:
               break;
           case WORK:
               break;
           case PLAYLIST:
               break;
       }
    }
}
