package com.broccoli.hacca.adapter.noticeboard;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.broccoli.hacca.R;
import com.broccoli.hacca.opensource.PagerSlidingTabStrip.IconTabProvider;

public class NoticeBoardPagerAdapter extends PagerAdapter implements
		IconTabProvider {
	//https://github.com/astuetz/PagerSlidingTabStrip/issues/95
	//Changing tabs dynamically
	
	private final String TAG = "NoticeBoardPagerAdapter";
	
	private Context context;

	private final int[] ICONS = { R.drawable.noticeboard_gongmozon,
			R.drawable.noticeboard_alba, R.drawable.noticeboard_hengsa,
			R.drawable.noticeboard_etc};
	private final String[] categoryList={"공모전","아르바이트","행사","기타"};
	private NoticeBoardListView[] noticeBoardListViews = new NoticeBoardListView[4];

	public NoticeBoardPagerAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public int getCount() {
		return ICONS.length;
	}

	@Override
	public int getPageIconResId(int position) {
		return ICONS[position];
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		noticeBoardListViews[position] = new NoticeBoardListView(context,categoryList[position]);
		
		container.addView(noticeBoardListViews[position].getView(), 0);
		return noticeBoardListViews[position].getView();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object view) {
		container.removeView((View) view);
	}

	@Override
	public boolean isViewFromObject(View v, Object o) {
		return v == ((View) o);
	}
}
