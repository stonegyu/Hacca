package com.broccoli.hacca.adapter.student;

import android.util.DisplayMetrics;
import android.view.animation.Animation;

import com.broccoli.hacca.adapter.ChildViewAdapterSettable;
import com.broccoli.hacca.pageinfo.StudentPersonnelInfo;

public class StudentAdapterItem {
	private StudentPersonnelInfo studentPersonnelInfo;
	private ChildViewAdapterSettable childViewAdapterSettable;

	private Animation anim;
	private int animationTime;
	private DisplayMetrics metrics;

	public DisplayMetrics getMetrics() {
		return metrics;
	}

	public void setMetrics(DisplayMetrics metrics) {
		this.metrics = metrics;
	}

	public ChildViewAdapterSettable getChildViewAdapterSettable() {
		return childViewAdapterSettable;
	}

	public void setChildViewAdapterSettable(ChildViewAdapterSettable childViewAdapterSettable) {
		this.childViewAdapterSettable = childViewAdapterSettable;
	}

	public StudentAdapterItem(StudentPersonnelInfo studentPersonnelInfo) {
		this.studentPersonnelInfo = studentPersonnelInfo;
	}

	public StudentPersonnelInfo getStudentPersonnelInfo() {
		return studentPersonnelInfo;
	}

	public void setStudentPersonnelInfo(StudentPersonnelInfo studentPersonnelInfo) {
		this.studentPersonnelInfo = studentPersonnelInfo;
	}

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
}
