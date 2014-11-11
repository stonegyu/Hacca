package com.broccoli.hacca.adapter.noticeboard;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.broccoli.hacca.R;
import com.broccoli.hacca.activity.UserType;
import com.broccoli.hacca.pageinfo.NoticeBoardInfo;
import com.broccoli.hacca.pageinfo.PageInfo;
import com.broccoli.hacca.passer.searchparser.NoticeBoardListParser;
import com.broccoli.hacca.searchapi.OnSearchAPIListener;
import com.broccoli.hacca.searchapi.SearchAPI;
import com.broccoli.hacca.searchapi.SearchAPIImpl;

public class NoticeBoardListView implements OnSearchAPIListener,OnScrollListener{
	private View pagerItemView;
	private View footerView;
	private ExpandableListView expandableListView;
	
	private NoticeBoardListViewAdapter adapter;
	private ArrayList<NoticeBoardAdapterItem> noticeBoardAdapterItems;
	
	private SearchAPI searchAPI;
	private String category;
	
	private boolean isSearchingData = false;
	private String searchText=null;
	private Context context;
	
	public NoticeBoardListView(Context context,String category) {
		this.context = context;
		this.category = category;
		
		searchAPI = new SearchAPIImpl(this,new NoticeBoardListParser());
		
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		pagerItemView = layoutInflater.inflate(R.layout.noticeboard_listview_layout, null);
		
		expandableListView = (ExpandableListView) pagerItemView.findViewById(R.id.expandableListView);
		
		footerView = layoutInflater.inflate(R.layout.listviewfooter, null);
		
		noticeBoardAdapterItems = new ArrayList<NoticeBoardAdapterItem>();
		
		adapter = new NoticeBoardListViewAdapter(context,noticeBoardAdapterItems);
		adapter.setGroupViewLayout(R.layout.noticeboard_adapter_parent_layour);
		adapter.setChildViewLayout(R.layout.noticeboard_adapter_child_layout);
		adapter.setUserType(UserType.PROFESSOR);
		
		expandableListView.setAdapter(adapter);

		expandableListView.setDivider(null);
		expandableListView.setOnScrollListener(this);
	}

	public View getView(){
		return pagerItemView;
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (firstVisibleItem + visibleItemCount >= totalItemCount
				&& !isSearchingData) {
			
			expandableListView.addFooterView(footerView);
			
			isSearchingData = true;
			
			if(searchText == null){
				searchAPI.searchNoticeBoard(category, String.valueOf(noticeBoardAdapterItems.size()));
			}else{
				searchAPI.searchNoticeBoard(category,searchText,String.valueOf(noticeBoardAdapterItems.size()));
			}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}

	@Override
	public void onSuccessSearch(PageInfo pageInfo) {
		
		if(pageInfo.getNoticeBoardInfos().size() == 0){
			expandableListView.removeFooterView(footerView);
			return;
		}
		
		for(NoticeBoardInfo noticeBoardInfo : pageInfo.getNoticeBoardInfos()){
			noticeBoardAdapterItems.add(new NoticeBoardAdapterItem(noticeBoardInfo));
		}
		
		expandableListView.removeFooterView(footerView);
		
		adapter.notifyDataSetChanged();
		isSearchingData = false;
	}

	@Override
	public void onFailSearch() {
		Toast.makeText(context, "로드에 실패했습니다.", Toast.LENGTH_SHORT).show();
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
}
