package com.bklabs.webviewinrecyclerview.calendar;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import com.bklabs.webviewinrecyclerview.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CalendarDialog extends DialogFragment {
    RelativeLayout layoutParent;

    public CalendarDialog() {
        // Empty constructor required for DialogFragment
    }

    public static CalendarDialog newInstance(RoomChannel roomChannel, int with, int height, float x, float y, int[] test2) {
        CalendarDialog frag = new CalendarDialog();
        Bundle args = new Bundle();
        args.putSerializable("roomChannel", roomChannel);
        args.putIntArray("paramposition",test2);
        args.putInt("with", with);
        args.putInt("height", height);
        args.putFloat("xPosition", x);
        args.putFloat("yPosition", y);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(getString(R.string.app_name));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.fragment_add_note_dialog,
                container);

        return view;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        float x = getArguments().getFloat("xPosition", 0);
        float y = getArguments().getFloat("yPosition", 0);
        int width = getArguments().getInt("with", 0);
        int height = getArguments().getInt("height", 0);
        int[] test2 = getArguments().getIntArray("paramposition");
        layoutParent = view.findViewById(R.id.layout_parent);
        addView(x, y, width, height, view.getContext(),test2);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    private void addView(float x, float y, int width, int height, Context context, int[] test2) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dynamicView = inflater.inflate(R.layout.item_edit_room, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.topMargin = test2[1];
        params.leftMargin = test2[0];
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParent.addView(dynamicView, params);
    }
}
