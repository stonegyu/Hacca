package com.broccoli.hacca.adapter.student;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.broccoli.hacca.R;
import com.broccoli.hacca.activity.UserType;
import com.broccoli.hacca.adapter.OnAdapterSetterListener;

public class StudentListPageAdapter extends BaseExpandableListAdapter implements
		OnAdapterSetterListener {
	private final String TAG = "StudentListPageAdapter";
	
	private LayoutInflater layoutInflater;

	private ArrayList<StudentAdapterItem> adapterItems;

	private int parentViewLayout;
	private int childViewLayout;
	private int loadingLayout;

	private Context context;

	private UserType userType;

	public StudentListPageAdapter(Context context,
			ArrayList<StudentAdapterItem> adapterItems) {
		this.context = context;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		this.adapterItems = adapterItems;
	}

	public void setGroupViewLayout(int groupViewLayout) {
		this.parentViewLayout = groupViewLayout;
	}

	public void setChildViewLayout(int childViewLayout) {
		this.childViewLayout = childViewLayout;
	}

	public void setLoadingViewLayout(int loadinglayout) {
		this.loadingLayout = loadinglayout;
	}
	
	public void setUserType(UserType userType){
		this.userType = userType;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return adapterItems.get(groupPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		if(adapterItems.get(groupPosition).getChildViewAdapterSettable() != null && adapterItems.get(groupPosition).getChildViewAdapterSettable()
				.isCompletedSetting()){
			convertView = adapterItems.get(groupPosition).getChildViewAdapterSettable().getView();
			adapterItems.get(groupPosition).getChildViewAdapterSettable().startAnimation();
		}else{
			convertView = layoutInflater.inflate(loadingLayout, parent, false);
		}
		
		if (adapterItems.get(groupPosition).getChildViewAdapterSettable() == null) {
			adapterItems.get(groupPosition).setChildViewAdapterSettable(
					new StudentAdapterChildViewSetter(context, this));
			
			adapterItems.get(groupPosition).getChildViewAdapterSettable().setUserType(userType);
			
			adapterItems
					.get(groupPosition)
					.getChildViewAdapterSettable()
					.setView(
							layoutInflater.inflate(childViewLayout, parent,
									false), adapterItems.get(groupPosition));
		}

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return adapterItems.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return adapterItems.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = layoutInflater.inflate(parentViewLayout, parent,
					false);
		}

		TextView name = (TextView) convertView
				.findViewById(R.id.index_studnetname);
		TextView age = (TextView) convertView
				.findViewById(R.id.index_studentage);
		TextView hope = (TextView) convertView
				.findViewById(R.id.index_studenthope);

		name.setText(adapterItems.get(groupPosition).getStudentPersonnelInfo().getStudentName());
		age.setText("(" + adapterItems.get(groupPosition).getStudentPersonnelInfo().getStudentAge() + ")");
		hope.setText(adapterItems.get(groupPosition).getStudentPersonnelInfo().getStudentHope());

		if (adapterItems.get(groupPosition).getAnim() == null) {
			adapterItems.get(groupPosition).setAnim(new TranslateAnimation(
					adapterItems.get(groupPosition).getMetrics().widthPixels, 0, 0, 0));

			adapterItems.get(groupPosition).getAnim().setDuration(adapterItems.get(groupPosition).getAnimationTime());
			convertView.startAnimation(adapterItems.get(groupPosition).getAnim());
		} else {
			if (!adapterItems.get(groupPosition).getAnim().hasStarted())
				convertView
						.startAnimation(adapterItems.get(groupPosition).getAnim());
		}

		ImageView indicator = (ImageView) convertView
				.findViewById(R.id.groupindicater);

		if (isExpanded) {
			indicator.setImageResource(R.drawable.list_page_adapter_indicator_up);
		} else {
			indicator.setImageResource(R.drawable.list_page_adapter_indicator_down);
			
			if(adapterItems.get(groupPosition).getChildViewAdapterSettable() != null && adapterItems.get(groupPosition).getChildViewAdapterSettable().isCompletedSetting()){
				adapterItems.get(groupPosition).getChildViewAdapterSettable().resetAnimation();
			}
		}

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return super.areAllItemsEnabled();
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	@Override
	public void onFailSettings() {

	}

	@Override
	public void onCompletedSettings() {
		this.notifyDataSetChanged();
	}
}
