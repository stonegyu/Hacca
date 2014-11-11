package com.broccoli.hacca.adapter.company;

import android.util.DisplayMetrics;
import android.view.animation.Animation;

import com.broccoli.hacca.adapter.ChildViewAdapterSettable;
import com.broccoli.hacca.pageinfo.CompanyPersonnelInfo;

public class CompanyAdapterItem {
	private ChildViewAdapterSettable childViewAdapterSettable;
	private CompanyPersonnelInfo companyPersonnelInfo;
	
	private Animation anim;
	private int animationTime;
	private DisplayMetrics metrics;

	public Animation getAnim() {
		return anim;
	}

	public void setAnim(Animation anim) {
		this.anim = anim;
	}

	public int getAnimationTime() {
		return animationTime;
	}

	public void setAnimationTime(int animationTime) {
		this.animationTime = animationTime;
	}

	public DisplayMetrics getMetrics() {
		return metrics;
	}

	public void setMetrics(DisplayMetrics metrics) {
		this.metrics = metrics;
	}

	public CompanyPersonnelInfo getCompanyPersonnelInfo() {
		return companyPersonnelInfo;
	}

	public CompanyAdapterItem(CompanyPersonnelInfo companyPersonnelInfo) {
		this.companyPersonnelInfo = companyPersonnelInfo;
	}

	public ChildViewAdapterSettable getChildViewAdapterSettable() {
		return childViewAdapterSettable;
	}

	public void setChildViewAdapterSettable(
			ChildViewAdapterSettable childViewAdapterSettable) {
		this.childViewAdapterSettable = childViewAdapterSettable;
	}
}
