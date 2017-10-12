package com.lucabl.gridviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Luca on 12/10/2017.
 */

public class GridViewPager extends ViewPager {

    private int gridSizeX, gridSizeY;
    private View[][] pages;
    private VerticalViewPager[] columns;
    private boolean nowSyncingColumns;
//    private ViewPager horiPager;
    private PageRequestCallback pageRequestCallback;
    private PageSelectionCallback pageSelectionCallback;

    public GridViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewPager(Context context, int gridSizeX, int gridSizeY, boolean initialCenter,
                         PageRequestCallback pageRequestCallback,
                         PageSelectionCallback pageSelectionCallback) {
        super(context);
        initialize(context, gridSizeX, gridSizeY, initialCenter, pageRequestCallback,
                pageSelectionCallback);
    }

    public GridViewPager(Context context, int gridSizeX, int gridSizeY, int initialX, int initialY,
                         PageRequestCallback pageRequestCallback,
                         PageSelectionCallback pageSelectionCallback) {
        super(context);
        initialize(context, gridSizeX, gridSizeY, initialX, initialY, pageRequestCallback,
                pageSelectionCallback);
    }

    public void initialize(Context context, int gridSizeX, int gridSizeY, boolean initialCenter,
                           PageRequestCallback pageRequestCallback,
                           PageSelectionCallback pageSelectionCallback) {
        initialize(context, gridSizeX, gridSizeY, (initialCenter ? gridSizeX / 2 : 0),
                (initialCenter ? gridSizeY / 2 : 0), pageRequestCallback, pageSelectionCallback);
    }

    public void initialize(Context context, int gridSizeX, int gridSizeY, int initialX, int initialY,
                           PageRequestCallback pageRequestCallback,
                           PageSelectionCallback pageSelectionCallback) {
        this.gridSizeX = gridSizeX;
        this.gridSizeY = gridSizeY;
        this.pageRequestCallback = pageRequestCallback;
        this.pageSelectionCallback = pageSelectionCallback;
        this.pages = new View[gridSizeX][];
        this.columns = new VerticalViewPager[gridSizeX];
        this.nowSyncingColumns = false;

        VerticalViewPager pager;
        for (int x = 0; x < gridSizeX; x++) {
            columns[x] = new VerticalViewPager(context);

            pages[x] = new View[gridSizeY];
            // changed to initialization on request
//            for (int y = 0; y < gridSizeY; y++) {
//                pages[x][y] = pageRequestCallback.getPage(x, y);
//            }
        }

        for (int x = 0; x < gridSizeX; x++) {
            pager = columns[x];
            pager.setAdapter(new ColumnPagerAdapter(x));
            pager.setOnPageChangeListener(getColumnPageChangeListener(x));
            pager.setCurrentItem(initialY, false);
        }

//        horiPager = new ViewPager(context);
//        horiPager.setOffscreenPageLimit(gridsize-1);
        setAdapter(new RowPagerAdapter());
        setOnPageChangeListener(getRowPageChangeListener(this));
        setCurrentItem(initialX, false);
    }

    private class RowPagerAdapter extends PagerAdapter {

        private RowPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup parent, int position) {
            View page = columns[position];
            parent.addView(page);
            return page;
        }

        @Override
        public void destroyItem(ViewGroup parent, int position, Object object) {
            View view = (View) object;
            parent.removeView(view);
        }

        @Override
        public int getCount() {
            return gridSizeX;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private class ColumnPagerAdapter extends VerticalViewPager.VerticalPagerAdapter {

        int column;

        private ColumnPagerAdapter(int column) {
            this.column = column;
        }

        @Override
        public Object instantiateItem(ViewGroup parent, int position) {
            View page = pages[column][position];
            if(page==null) {
                page = pages[column][position] = pageRequestCallback.getPage(column, position);
            }
            parent.addView(page);
            return page;
        }

        @Override
        public void destroyItem(ViewGroup parent, int position, Object object) {
            View view = (View) object;
            parent.removeView(view);
            pages[column][position] = null;
        }

        @Override
        public int getCount() {
            return gridSizeY;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private ViewPager.OnPageChangeListener getRowPageChangeListener(final ViewPager pager) {
        return new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }

                    @Override
                    public void onPageScrolled(int position,
                                               float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (pageSelectionCallback != null)
                            pageSelectionCallback.pageSelected(position,
                                    columns[pager.getCurrentItem()].getCurrentItem());
                    }
                };
    }

    private VerticalViewPager.OnPageChangeListener getColumnPageChangeListener(final int column) {
        return new VerticalViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }

                    @Override
                    public void onPageScrolled(int position,
                                               float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (!nowSyncingColumns) {
                            nowSyncingColumns = true;
                            for (int i = 0; i < gridSizeX; i++) {
                                if (i != column) {
                                    columns[i].setCurrentItem(position, false);
                                }
                            }
                            if (pageSelectionCallback != null)
                                pageSelectionCallback.pageSelected(column, position);
                            nowSyncingColumns = false;
                        }
                    }
                };
    }

    public interface PageRequestCallback {
        View getPage(int gridPositionX, int gridPositionY);
    }

    public interface PageSelectionCallback {
        void pageSelected(int gridPositionX, int gridPositionY);
    }
}
