package com.broccoli.hacca.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.broccoli.hacca.R;
import com.broccoli.hacca.adapter.company.CompanyAdapterItem;
import com.broccoli.hacca.adapter.company.CompanyListPageAdapter;
import com.broccoli.hacca.pageinfo.PageInfo;
import com.broccoli.hacca.passer.searchparser.CompanyPersonnelInfoListParser;
import com.broccoli.hacca.searchapi.OnSearchAPIListener;
import com.broccoli.hacca.searchapi.SearchAPI;
import com.broccoli.hacca.searchapi.SearchAPIImpl;

public class CompanyListPageActivity extends Activity implements
		OnScrollListener, OnTouchListener, OnSearchAPIListener{
	
	private EditText searchBox;
	private ImageView searchBtn;
	
	private ImageView exitBtn;

	private View footerView;
	
	private ArrayList<CompanyAdapterItem> companyAdapterItems;
	private CompanyListPageAdapter companyListPageAdapter;
	private ExpandableListView expandableListView;
	
	private boolean isSearchingData = false;
	
	private SearchAPI searchAPI;
	
	private DisplayMetrics metrics;
	
	private String searchText = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.company_list_page_activity_layout);

		searchAPI = new SearchAPIImpl(this,new CompanyPersonnelInfoListParser());
		
		expandableListView = (ExpandableListView) findViewById(R.id.company_list_page_activity_expandabellistview);

		searchBox = (EditText) findViewById(R.id.company_list_page_activity_layout_search_box);
		searchBtn = (ImageView) findViewById(R.id.company_list_page_activity_layout_search_icon);	
		
		exitBtn = (ImageView) findViewById(R.id.company_list_page_activity_layout_exit);
		
		footerView = getLayoutInflater().inflate(R.layout.listviewfooter, null);
		
		companyAdapterItems = new ArrayList<CompanyAdapterItem>();
		
		companyListPageAdapter = new CompanyListPageAdapter(this,companyAdapterItems);
		companyListPageAdapter.setGroupViewLayout(R.layout.company_list_page_adapter_parent_layout);
		companyListPageAdapter.setChildViewLayout(R.layout.company_list_page_adapter_child_layout);
		companyListPageAdapter.setLoadingViewLayout(R.layout.listviewfooter);
		companyListPageAdapter.setUserType(UserType.PROFESSOR);
		
		expandableListView.setAdapter(companyListPageAdapter);

		expandableListView.setDivider(null);
		expandableListView.setOnScrollListener(this);
		
		searchBtn.setOnTouchListener(this);
		exitBtn.setOnTouchListener(this);
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
	}


	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (firstVisibleItem + visibleItemCount >= totalItemCount
				&& !isSearchingData) {
			expandableListView.addFooterView(footerView);
			
			isSearchingData = true;
			
			if(searchText == null){
				searchAPI.searchCompanyInfoList(String.valueOf(companyAdapterItems.size()));
			}else{
				searchAPI.searchCompanyInfoList(searchText,String.valueOf(companyAdapterItems.size()));
			}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.company_list_page_activity_layout_search_icon:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				searchBtn.setImageResource(R.drawable.searchicon_down);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				searchBtn.setImageResource(R.drawable.searchicon_up);
				if (searchBox.getText().length() == 0) {
					Toast.makeText(getApplicationContext(),
							"검색명을 입력해주세요.", Toast.LENGTH_SHORT).show();
				} else {
					//search
					companyAdapterItems.removeAll(companyAdapterItems);
					searchText = searchBox.getText().toString();
					searchBox.setText("");
					isSearchingData = false;
				}
			}
			break;
		case R.id.company_list_page_activity_layout_exit:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				exitBtn.setImageResource(R.drawable.exiticon_down);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				exitBtn.setImageResource(R.drawable.exiticon_up);
				finish();
			}
			break;
		}
		return true;
	}

	@Override
	public void onSuccessSearch(PageInfo pageInfo) {
		
		if(pageInfo.getCompanyPersonnelInfos().size() == 0){
			expandableListView.removeFooterView(footerView);
			return;
		}
		
		for(int i=0;i<pageInfo.getCompanyPersonnelInfos().size();i++){
			CompanyAdapterItem companyAdapterItem = new CompanyAdapterItem(pageInfo.getCompanyPersonnelInfos().get(i)); 
			companyAdapterItem.setAnimationTime(700 + 100*i);
			companyAdapterItem.setMetrics(metrics);
			
			companyAdapterItems.add(companyAdapterItem);
		}
		
		expandableListView.removeFooterView(footerView);
		
		companyListPageAdapter.notifyDataSetChanged();
		isSearchingData = false;
	}

	@Override
	public void onFailSearch() {
		Toast.makeText(this, "로드에 실패했습니다.", Toast.LENGTH_SHORT).show();
	}


	@Override
	public void onBackPressed() {
		if(searchText != null){
			searchText = null;
			companyAdapterItems.removeAll(companyAdapterItems);
			isSearchingData = false;
			companyListPageAdapter.notifyDataSetChanged();
		}else{
			finish();
		}
	}
	
}
