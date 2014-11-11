package com.broccoli.hacca.adapter.noticeboard;

import java.util.Calendar;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.broccoli.hacca.R;
import com.broccoli.hacca.activity.UserType;
import com.broccoli.hacca.adapter.ChildViewAdapterSettable;
import com.broccoli.hacca.adapter.OnAdapterSetterListener;
import com.broccoli.hacca.dialog.LoadingProgressDialog;
import com.broccoli.hacca.dialog.OnDialogListener;
import com.broccoli.hacca.dialog.EditableBoardDialog;
import com.broccoli.hacca.pageinfo.NoticeBoardInfo;
import com.broccoli.hacca.registerapi.OnRegisterAPIListener;
import com.broccoli.hacca.registerapi.RegisterAPI;
import com.broccoli.hacca.registerapi.RegisterAPIImpl;

public class NoticeBoardChildViewSetter implements ChildViewAdapterSettable,
		OnTouchListener, OnDialogListener ,OnRegisterAPIListener{

	private View childView;
	private boolean isCompletedSetting = false;
	private NoticeBoardAdapterItem adapterItem;

	private ImageView editBtn;
	private ImageView deleteBtn;
	private UserType userType;
	private Context context;
	private EditableBoardDialog editableBoardDialog;
	private LoadingProgressDialog loadingProgressDialog;

	private Animation anim;

	private String loginId;
	private String deviceId;
	private String name;
	private OnAdapterSetterListener adapterSetterListener;

	public NoticeBoardChildViewSetter(Context context,
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
		this.adapterItem = (NoticeBoardAdapterItem) personnelInfo;

		((TextView) childView.findViewById(R.id.inform_date))
				.setText(adapterItem.getNoticeBoardInfo().getDate());
		((TextView) childView.findViewById(R.id.inform_content))
				.setText(adapterItem.getNoticeBoardInfo().getContent());

		editBtn = (ImageView) childView.findViewById(R.id.inform_editbtn);
		deleteBtn = (ImageView) childView.findViewById(R.id.inform_deletebtn);

		if (userType == UserType.PROFESSOR
				&& adapterItem.getNoticeBoardInfo().getLoginId()
						.equals(loginId)) {
			editBtn.setOnTouchListener(this);
			deleteBtn.setOnTouchListener(this);
		} else {
			editBtn.setImageBitmap(null);
			deleteBtn.setImageBitmap(null);
		}
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
		if (v.getId() == R.id.inform_editbtn) {

			if (event.getAction() == MotionEvent.ACTION_UP) {

				editableBoardDialog = new EditableBoardDialog(context);
				editableBoardDialog.setOnDialogListener(this);
				editableBoardDialog.setTitle(name + " 님");
				editableBoardDialog.setContent(adapterItem.getNoticeBoardInfo().getContent());
				editableBoardDialog.show();
			}
		} else if (v.getId() == R.id.inform_deletebtn) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				
			}
		}

		return true;
	}

	@Override
	public void onTouchedCommitButton(String content) {
		editableBoardDialog.dismiss();
		
		Calendar c = Calendar.getInstance();
		int Year = c.get(Calendar.YEAR);
		int Month = c.get(Calendar.MONTH);
		int Day = c.get(Calendar.DAY_OF_MONTH);

		String time = String.format("%2d/%d/%d", Year, Month, Day);
		
		NoticeBoardInfo noticeBoardInfo = new NoticeBoardInfo();
		noticeBoardInfo.setNumber(adapterItem.getNoticeBoardInfo().getNumber());
		noticeBoardInfo.setLoginId(loginId);
		noticeBoardInfo.setTitle(adapterItem.getNoticeBoardInfo().getTitle());
		noticeBoardInfo.setContent(adapterItem.getNoticeBoardInfo().getContent());
		noticeBoardInfo.setCategory(adapterItem.getNoticeBoardInfo().getCategory());
		noticeBoardInfo.setDate(time);
		
		RegisterAPI registerAPI = new RegisterAPIImpl(this);
		registerAPI.registerNoticeBoard(noticeBoardInfo);
		
		loadingProgressDialog = new LoadingProgressDialog(context);
		loadingProgressDialog.show();
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

	@Override
	public void onSuccessRegister() {
		loadingProgressDialog.dismiss();
		Toast.makeText(context, "등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
		
		adapterSetterListener.onCompletedSettings();
	}

	@Override
	public void onFailRegister() {
		Toast.makeText(context, "등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDuplicatedRegister() {
		
	}
}
