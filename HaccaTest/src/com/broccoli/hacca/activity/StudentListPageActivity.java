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
import com.broccoli.hacca.adapter.student.StudentAdapterItem;
import com.broccoli.hacca.adapter.student.StudentListPageAdapter;
import com.broccoli.hacca.pageinfo.PageInfo;
import com.broccoli.hacca.passer.searchparser.StudentPersonnelInfoListParser;
import com.broccoli.hacca.searchapi.OnSearchAPIListener;
import com.broccoli.hacca.searchapi.SearchAPI;
import com.broccoli.hacca.searchapi.SearchAPIImpl;

public class StudentListPageActivity extends Activity implements
		OnScrollListener, OnTouchListener, OnSearchAPIListener{
	
	private EditText searchBox;
	private ImageView searchBtn;
	
	private ImageView exitBtn;

	private View footerView;
	
	private ArrayList<StudentAdapterItem> studentAdapterItems;
	private StudentListPageAdapter studentListPageAdapter;
	private ExpandableListView expandableListView;
	
	private boolean isSearchingData = false;
	
	private SearchAPI searchAPI;
	
	private DisplayMetrics metrics;
	
	private String searchText = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.student_list_page_activity_layout);

		searchAPI = new SearchAPIImpl(this,new StudentPersonnelInfoListParser());
		
		expandableListView = (ExpandableListView) findViewById(R.id.student_list_page_activity_expandabellistview);

		searchBox = (EditText) findViewById(R.id.student_list_page_activity_layout_search_box);
		searchBtn = (ImageView) findViewById(R.id.student_list_page_activity_layout_search_icon);	
		
		exitBtn = (ImageView) findViewById(R.id.student_list_page_activity_layout_exit);
		
		footerView = getLayoutInflater().inflate(R.layout.listviewfooter, null);
		
		studentAdapterItems = new ArrayList<StudentAdapterItem>();
		
		studentListPageAdapter = new StudentListPageAdapter(this,studentAdapterItems);
		studentListPageAdapter.setGroupViewLayout(R.layout.student_list_page_adapter_parent_layout);
		studentListPageAdapter.setChildViewLayout(R.layout.student_list_page_adapter_child_layout);
		studentListPageAdapter.setLoadingViewLayout(R.layout.listviewfooter);
		studentListPageAdapter.setUserType(UserType.PROFESSOR);
		
		expandableListView.setAdapter(studentListPageAdapter);

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
				searchAPI.searchStudentInfoList(String.valueOf(studentAdapterItems.size()));
			}else{
				searchAPI.searchStudentInfoList(searchText,String.valueOf(studentAdapterItems.size()));
			}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.student_list_page_activity_layout_search_icon:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				searchBtn.setImageResource(R.drawable.searchicon_down);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				searchBtn.setImageResource(R.drawable.searchicon_up);
				if (searchBox.getText().length() == 0) {
					Toast.makeText(getApplicationContext(),
							"검색명을 입력해주세요.", Toast.LENGTH_SHORT).show();
				} else {
					//search
					studentAdapterItems.removeAll(studentAdapterItems);
					searchText = searchBox.getText().toString();
					searchBox.setText("");
					isSearchingData = false;
				}
			}
			break;
		case R.id.student_list_page_activity_layout_exit:
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
		
		if(pageInfo.getStudentPersonnelInfos().size() == 0){
			expandableListView.removeFooterView(footerView);
			return;
		}
		
		for(int i=0;i<pageInfo.getStudentPersonnelInfos().size();i++){
			StudentAdapterItem studentAdapterItem = new StudentAdapterItem(pageInfo.getStudentPersonnelInfos().get(i)); 
			studentAdapterItem.setAnimationTime(700 + 100*i);
			studentAdapterItem.setMetrics(metrics);
			
			studentAdapterItems.add(studentAdapterItem);
		}
		
		expandableListView.removeFooterView(footerView);
		
		studentListPageAdapter.notifyDataSetChanged();
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
			studentAdapterItems.removeAll(studentAdapterItems);
			isSearchingData = false;
			studentListPageAdapter.notifyDataSetChanged();
		}else{
			finish();
		}
	}
	
}
