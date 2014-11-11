package com.broccoli.hacca.adapter;


import com.broccoli.hacca.activity.UserType;

import android.view.View;

public interface ChildViewAdapterSettable {
	void setView(View view,Object personnelInfo);
	View getView();
	boolean isCompletedSetting();
	void setUserType(UserType userType);
	void startAnimation();
	void resetAnimation();
}
