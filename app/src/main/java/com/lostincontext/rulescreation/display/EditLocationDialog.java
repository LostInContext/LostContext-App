package com.lostincontext.rulescreation.display;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.lostincontext.R;


public class EditLocationDialog extends DialogFragment {
    public static final int PLACE_PICKER_REQUEST_CODE = 9876;
    private EditText editText;

    public interface EditLocationDialogListener {
        void onFinishEditDialog(String inputText);

        void doPositiveClick();

        void doNegativeClick();
    }

    public EditLocationDialog() {
    }


    @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View dialogView = layoutInflater.inflate(R.layout.location_edit_fragment, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        alert.setTitle("Location");
        alert.setView(dialogView);

        editText = (EditText) dialogView.findViewById(R.id.location_edittext);
//        final EditLocationDialogListener activity = (EditLocationDialogListener) getActivity();
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
//                activity.doPositiveClick();
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                try {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                    getActivity().startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });
        return alert.create();
    }

    public void setTextPlace(CharSequence textPlace) {
        editText.setText(textPlace);
    }
}
