//package com.bklabs.webviewinrecyclerview.calendar.customcalendar;
//
//import android.app.Dialog;
//import android.content.res.Resources;
//import android.os.Bundle;
//
//import android.util.DisplayMetrics;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//
//import com.bklabs.webviewinrecyclerview.R;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.DialogFragment;
//
//
//public class LinkServiceDialogFragment extends DialogFragment {
//
//
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.getWindow().setGravity(Gravity.TOP);
//        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
//        return dialog;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_Dialog_Custom);
//
//    }
//
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.dialog_fragment_mst_link_service, container, false);
//
//        return v;
//    }
//
//    private void calculatePositionDynamicView() {
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        View dynamicView = inflater.inflate(R.layout.mst_link_button, null);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.width_of_services),
//                getResources().getDimensionPixelSize(R.dimen.height_of_services));
//        params.topMargin = getTopMargin();
//        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        rlRoot.addView(dynamicView, params);
//        bindView(dynamicView);
//    }
//
//    private void bindView(View view) {
//        ImageView ivMail = view.findViewById(R.id.iv_1);
//        ImageView ivTel = view.findViewById(R.id.iv_2);
//
//        setupImageView(ivMail, IV_MAIL);
//        setupImageView(ivTel, IV_TEL);
//
//        viewDump.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//    }
//
//    private void setupImageView(ImageView iv, int type) {
//        switch (type) {
//            case IV_MAIL:
//                iv.setActivated(isMailVisible);
//                iv.setBackground(ContextCompat.getDrawable(getContext(), isMailVisible ? R.drawable.bg_button_link_mail : R.drawable.bg_button_link_disable));
//                iv.setOnClickListener(mClickMail);
//                break;
//
//            case IV_TEL:
//                iv.setActivated(isTelVisible);
//                iv.setBackground(ContextCompat.getDrawable(getContext(), isTelVisible ? R.drawable.bg_button_link_tel : R.drawable.bg_button_link_disable));
//                iv.setOnClickListener(mClickTel);
//        }
//    }
//
//    private View.OnClickListener mClickMail = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if (isMailVisible) {
//                Utils.gotoMailService(mstUser, (TimerBaseActivity) getActivity());
//                dismiss();
//            }
//        }
//    };
//
//    private View.OnClickListener mClickTel = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if (isTelVisible) {
//                Utils.gotoTelService(mstUser, (TimerBaseActivity) getActivity());
//                dismiss();
//            }
//        }
//    };
//
//    private int getTopMargin() {
//        Resources resource = getResources();
//        int offsetHeight = resource.getDimensionPixelSize(R.dimen.people_item_height);
//        int limitHeight = getLimitOfItemHeightWindow();
//
//        if (coorY <= limitHeight)
//            return (int) coorY - offsetHeight;
//        else
//            return (int) (coorY - offsetHeight - getResources().getDimensionPixelSize(R.dimen.dimen_50) /*height of trigger toolbar bottom*/);
//    }
//
//    private int getLimitOfItemHeightWindow() {
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        return displayMetrics.heightPixels - getResources().getDimensionPixelSize(R.dimen.people_item_height);
//    }
//}
