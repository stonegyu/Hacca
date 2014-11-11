package com.broccoli.hacca.adapter.company;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.broccoli.hacca.R;
import com.broccoli.hacca.activity.UserType;
import com.broccoli.hacca.adapter.ChildViewAdapterSettable;
import com.broccoli.hacca.adapter.OnAdapterSetterListener;
import com.broccoli.hacca.dialog.OnDialogListener;
import com.broccoli.hacca.dialog.EditableBoardDialog;

public class CompanyAdapterChildViewSetter implements ChildViewAdapterSettable,
		OnTouchListener, OnDialogListener {

	private View childView;
	private boolean isCompletedSetting = false;
	private CompanyAdapterItem adapterItem;

	private ImageView commitBtn;
	private UserType userType;
	private Context context;
	private EditableBoardDialog professorCommentBoardDialog;

	private Animation anim;

	private String loginId;
	private String deviceId;
	private String name;
	private OnAdapterSetterListener adapterSetterListener;

	public CompanyAdapterChildViewSetter(Context context,
			OnAdapterSetterListener adapterSetterListener) {
		this.context = context;
		this.adapterSetterListener = adapterSetterListener;

		// InternalStorageAPI internalStorageAPI = new
		// InternalStorageAPIImpl(context);
		// loginId = internalStorageAPI.getDeviceId();
		// deviceId = internalStorageAPI.getDeviceId();

		loginId = "adminpro";
		name = "관리자";
	}

	@Override
	public void setView(View view, Object personnelInfo) {
		this.childView = view;
		this.adapterItem = (CompanyAdapterItem) personnelInfo;

		commitBtn = (ImageView) childView
				.findViewById(R.id.company_push_button);
		commitBtn.setOnTouchListener(this);

		((TextView) childView.findViewById(R.id.company_businesstype))
				.setText(adapterItem.getCompanyPersonnelInfo()
						.getCompanyBusinessType());
		((TextView) childView.findViewById(R.id.company_homepage))
				.setText(adapterItem.getCompanyPersonnelInfo()
						.getCompanyHomePage());
		((TextView) childView.findViewById(R.id.company_area))
				.setText(adapterItem.getCompanyPersonnelInfo().getCompanyArea());
		((TextView) childView.findViewById(R.id.company_worktype))
				.setText(adapterItem.getCompanyPersonnelInfo()
						.getCompanyWorkType());
		((TextView) childView.findViewById(R.id.company_recruitperiod))
				.setText(adapterItem.getCompanyPersonnelInfo()
						.getCompanyRecruitPeriod());
		
	}

	@Override
	public View getView() {
		return childView;
	}

	@Override
	public boolean isCompletedSetting() {
		return isCompletedSetting;
	}

	@Override
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v.getId() == R.id.company_push_button) {

			if (event.getAction() == MotionEvent.ACTION_UP) {

				professorCommentBoardDialog = new EditableBoardDialog(
						context);
				professorCommentBoardDialog.setOnDialogListener(this);
				professorCommentBoardDialog.setTitle(name + " 님");
				professorCommentBoardDialog.show();
			}
		}

		return true;
	}

	@Override
	public void onTouchedCommitButton(String content) {
		professorCommentBoardDialog.dismiss();

		if (userType == UserType.PROFESSOR) {

		} else if (userType == UserType.STUDENT) {

		}
	}

	@Override
	public void startAnimation() {
		if (anim == null) {
			anim = new ScaleAnimation(1, 1, 0, 1);
			anim.setDuration(500);
			childView.startAnimation(anim);
		}
	}

	@Override
	public void resetAnimation() {
		anim = null;
	}
}
