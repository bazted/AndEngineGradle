package com.bazted.wsiz.physics;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.oersted_fragment)
public class OerstedFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    @ViewById
    ImageView compass_bg_iv;

    @ViewById
    ImageView compass_arrow_iv;

    @ViewById
    TextView coords_tv;

    // record the compass picture angle turned
    private float currentDegree = 0f;

    @Click
    void coords_tv() {
        MeshActivity_.intent(getActivity()).start();
    }


    @AfterViews
    void afterView() {

        // Read an SVG from the assets folder
        Log.e("afterView", "setText");


        Log.e("afterView", "start try");


        // Create a canvas to draw onto

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

}
