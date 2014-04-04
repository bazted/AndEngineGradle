package com.bazted.wsiz.physics;

import android.app.ListActivity;
import android.widget.ArrayAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.res.StringArrayRes;

@EActivity
public class MainListActivity extends ListActivity {

    @StringArrayRes
    String[] list_items;

    @AfterInject
    void fillList() {
        setListAdapter(new ArrayAdapter<String>(
                        this, android.R.layout.simple_list_item_1,
                        android.R.id.text1, list_items)
        );
    }

    @ItemClick
    void list(int position) {
        switch (position) {

            default:
                MeshActivity_.intent(this).start();
        }


    }

}
