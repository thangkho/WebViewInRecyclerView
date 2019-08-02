package com.bklabs.webviewinrecyclerview.calendar.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bklabs.webviewinrecyclerview.R;
import com.bklabs.webviewinrecyclerview.calendar.model.RoomChannel;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CalendarDialog extends DialogFragment {
    ViewGroup root;
    private EditText edtCount;
    RoomChannel roomChannel;
    int test2[] = new int[2];

    public CalendarDialog() {
        // Empty constructor required for DialogFragment
    }

    public static CalendarDialog newInstance(RoomChannel roomChannel, int with, int height, float x, float y, int[] test2) {
        CalendarDialog frag = new CalendarDialog();
        Bundle args = new Bundle();
        args.putSerializable("roomChannel", roomChannel);
        args.putIntArray("paramposition", test2);
        args.putInt("with", with);
        args.putInt("height", height);
        args.putFloat("xPosition", x);
        args.putFloat("yPosition", y);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        root = new FrameLayout(inflater.getContext());
        root.setLayoutParams(params);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() == null) {
            return;
        }
        float x = getArguments().getFloat("xPosition", 0);
        float y = getArguments().getFloat("yPosition", 0);
        int width = getArguments().getInt("with", 0);
        int height = getArguments().getInt("height", 0);
        int[] test2 = getArguments().getIntArray("paramposition");
        roomChannel = (RoomChannel) getArguments().getSerializable("roomChannel");


        addView(x, y, width, height, view.getContext(), test2);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.setTitle(getString(R.string.app_name));
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            dialog.getWindow().setSoftInputMode(
//                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void addView(float x, float y, int width, int height, Context context, int[] test2) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dynamicView = inflater.inflate(R.layout.item_edit_room, root, false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.topMargin = test2[1] - height / 2;
        params.leftMargin = test2[0];
        final EditText edtCount = dynamicView.findViewById(R.id.edtCount);
        final TextView tvDay = dynamicView.findViewById(R.id.tv_date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(roomChannel.getDate());
        tvDay.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
//        showKeyboard();
        edtCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtCount.setBackgroundColor(TextUtils.isEmpty(edtCount.getText()) ? Color.RED : Color.WHITE);

            }
        });
        root.addView(dynamicView, params);
    }

    public void showKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
