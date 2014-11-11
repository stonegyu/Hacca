package com.broccoli.hacca.adapter.noticeboard;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.broccoli.hacca.R;
import com.broccoli.hacca.activity.UserType;
import com.broccoli.hacca.adapter.OnAdapterSetterListener;

public class NoticeBoardListViewAdapter extends BaseExpandableListAdapter
		implements OnAdapterSetterListener {

	private final String TAG = "NoticeBoardListViewAdapter";

	private LayoutInflater layoutInflater;

	private ArrayList<NoticeBoardAdapterItem> adapterItems;

	private int parentViewLayout;
	private int childViewLayout;

	private Context context;

	private UserType userType;

	public NoticeBoardListViewAdapter(Context context,
			ArrayList<NoticeBoardAdapterItem> adapterItems) {
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

	public void setUserType(UserType userType) {
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

		if (adapterItems.get(groupPosition).getChildViewAdapterSettable() == null) {
			adapterItems.get(groupPosition).setChildViewAdapterSettable(
					new NoticeBoardChildViewSetter(context, this));

			adapterItems.get(groupPosition).getChildViewAdapterSettable()
					.setUserType(userType);

			adapterItems
					.get(groupPosition)
					.getChildViewAdapterSettable()
					.setView(
							layoutInflater.inflate(childViewLayout, parent,
									false), adapterItems.get(groupPosition));
		}

		convertView = adapterItems.get(groupPosition)
				.getChildViewAdapterSettable().getView();
		adapterItems.get(groupPosition).getChildViewAdapterSettable()
				.startAnimation();

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

		TextView name = (TextView) convertView.findViewById(R.id.inform_name);
		TextView title = (TextView) convertView.findViewById(R.id.inform_title);

		name.setText(adapterItems.get(groupPosition).getNoticeBoardInfo()
				.getProfessorName());
		title.setText(adapterItems.get(groupPosition).getNoticeBoardInfo()
				.getTitle());

		ImageView indicator = (ImageView) convertView
				.findViewById(R.id.groupindicater);

		if (isExpanded) {
			indicator
					.setImageResource(R.drawable.list_page_adapter_indicator_up);
		} else {
			indicator
					.setImageResource(R.drawable.list_page_adapter_indicator_down);

			if (adapterItems.get(groupPosition).getChildViewAdapterSettable() != null
					&& adapterItems.get(groupPosition)
							.getChildViewAdapterSettable().isCompletedSetting()) {
				adapterItems.get(groupPosition).getChildViewAdapterSettable()
						.resetAnimation();
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
