package com.lostincontext.rulescreation;

import android.content.Intent;

import com.lostincontext.commons.BasePresenter;
import com.lostincontext.commons.BaseView;
import com.lostincontext.commons.list.Section;
import com.lostincontext.data.FenceCreator;
import com.lostincontext.data.location.repo.LocationRepository;
import com.lostincontext.rulescreation.display.RuleCreationItemCallback;

import java.util.List;

public class RulesCreationContract {

    interface View extends BaseView<RulesCreationContract.Presenter> {
        void showLocationPicker(String fenceName);

        void setPlace(Intent place);

        void setSections(List<Section> sections);

        void onPlaylistPickerClick();

        void showToast(String string);

    }


    interface Presenter extends BasePresenter, RuleCreationItemCallback {

        void onActivityResult(int requestCode, int resultCode, Intent data);

        @Override void onRuleCreationItemClick(FenceCreator fence);

        LocationRepository getLocationRepository();
    }
}
