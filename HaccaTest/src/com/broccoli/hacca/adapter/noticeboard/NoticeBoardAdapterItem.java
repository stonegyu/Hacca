package com.broccoli.hacca.adapter.noticeboard;

import com.broccoli.hacca.adapter.ChildViewAdapterSettable;
import com.broccoli.hacca.pageinfo.NoticeBoardInfo;

public class NoticeBoardAdapterItem {

	private ChildViewAdapterSettable childViewAdapterSettable;
	private NoticeBoardInfo noticeBoardInfo;
	
	public NoticeBoardAdapterItem(NoticeBoardInfo noticeBoardInfo){
		this.noticeBoardInfo = noticeBoardInfo;
	}
	
	public ChildViewAdapterSettable getChildViewAdapterSettable() {
		return childViewAdapterSettable;
	}
	public void setChildViewAdapterSettable(
			ChildViewAdapterSettable childViewAdapterSettable) {
		this.childViewAdapterSettable = childViewAdapterSettable;
	}
	public NoticeBoardInfo getNoticeBoardInfo() {
		return noticeBoardInfo;
	}
}
