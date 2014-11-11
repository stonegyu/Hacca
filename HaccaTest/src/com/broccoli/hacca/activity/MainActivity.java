package com.broccoli.hacca.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.broccoli.hacca.R;
import com.broccoli.hacca.adapter.noticeboard.NoticeBoardPagerAdapter;
import com.broccoli.hacca.opensource.PagerSlidingTabStrip;

public class MainActivity extends FragmentActivity {
	
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private NoticeBoardPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.noticeboard_page_activity_layout);
		
		tabs = (PagerSlidingTabStrip)findViewById(R.id.pager_tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new NoticeBoardPagerAdapter(this);

		pager.setAdapter(adapter);

		tabs.setViewPager(pager);
	}
}
