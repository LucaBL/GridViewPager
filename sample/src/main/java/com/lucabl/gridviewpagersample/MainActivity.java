package com.lucabl.gridviewpagersample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.lucabl.gridviewpager.GridViewPager;

public class MainActivity extends AppCompatActivity {

    public static Activity activity;
    private static final int gridSizeX = 3;
    private static final int gridSizeY = 5;
    private final boolean initialCenter = true;
    private static GridViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        pager = (GridViewPager) this.findViewById(R.id.pager);
        pager.initialize(this, gridSizeX, gridSizeY, initialCenter,
            new GridViewPager.PageRequestCallback() {
                @Override
                public View getPage(int gridPositionX, int gridPositionY) {
                    return new PageView(
                            Color.parseColor("#ff"+
                                    Integer.toHexString(gridPositionX*(16/gridSizeX))+
                                    "f"+
                                    Integer.toHexString(gridPositionY*(16/gridSizeY))+
                                    "f"),
                            activity);
                }
            },
            new GridViewPager.PageCreationCallback() {
                @Override
                public void pageCreated(int gridPositionX, int gridPositionY) {
                    Log.i("page", "created "+gridPositionY+" in column "+gridPositionX);
                }
            },
            new GridViewPager.PageSelectionCallback() {
                @Override
                public void pageSelected(int gridPositionX, int gridPositionY) {
                    Log.i("page", "selected "+gridPositionY+" in column "+gridPositionX);
                }
            }
        );
    }
}
