package com.broccoli.hacca.adapter.student;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.broccoli.hacca.R;
import com.broccoli.hacca.activity.UserType;
import com.broccoli.hacca.adapter.ChildViewAdapterSettable;
import com.broccoli.hacca.adapter.OnAdapterSetterListener;
import com.broccoli.hacca.dialog.OnDialogListener;
import com.broccoli.hacca.dialog.EditableBoardDialog;
import com.broccoli.hacca.internalstorageapi.InternalStorageAPI;
import com.broccoli.hacca.internalstorageapi.InternalStorageAPIImpl;
import com.broccoli.hacca.pageinfo.CommentInfo;
import com.broccoli.hacca.pageinfo.PageInfo;
import com.broccoli.hacca.passer.searchparser.CommentInfoListParser;
import com.broccoli.hacca.registerapi.OnRegisterAPIListener;
import com.broccoli.hacca.registerapi.RegisterAPI;
import com.broccoli.hacca.registerapi.RegisterAPIImpl;
import com.broccoli.hacca.searchapi.OnSearchAPIListener;
import com.broccoli.hacca.searchapi.SearchAPI;
import com.broccoli.hacca.searchapi.SearchAPIImpl;

public class StudentAdapterChildViewSetter implements ChildViewAdapterSettable,
		OnSearchAPIListener, OnTouchListener, OnDialogListener,
		OnRegisterAPIListener {

	private final String TAG = "StudentAdapterChildViewSetter";

	private View childView;
	private boolean isCompletedSetting = false;
	private StudentAdapterItem adapterItem;
	private OnAdapterSetterListener adapterSetterListener;
	private LayoutInflater layoutInflater;

	private TextView blog;
	private TextView mail;

	private LinearLayout commentListLayout;
	private ImageView commitBtn;
	private UserType userType;
	private Context context;
	private EditableBoardDialog professorCommentBoardDialog;

	private Animation anim;

	private String loginId;
	private String deviceId;
	private String name;
	
	private String mComment;

	public StudentAdapterChildViewSetter(Context context,
			OnAdapterSetterListener adapterSetterListener) {
		this.context = context;
		this.adapterSetterListener = adapterSetterListener;
		this.layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
		this.adapterItem = (StudentAdapterItem) personnelInfo;

		blog = (TextView) childView.findViewById(R.id.student_blog);
		mail = (TextView) childView.findViewById(R.id.student_mail);
		commentListLayout = (LinearLayout) childView
				.findViewById(R.id.professor_comment_list);

		commitBtn = (ImageView) childView
				.findViewById(R.id.student_push_button);
		commitBtn.setOnTouchListener(this);

		blog.setText(adapterItem.getStudentPersonnelInfo().getStudentMail());
		mail.setText(adapterItem.getStudentPersonnelInfo().getStudentMail());

		SearchAPI searchAPI = new SearchAPIImpl(this,
				new CommentInfoListParser());
		searchAPI.searchStudentComment(adapterItem.getStudentPersonnelInfo()
				.getLoginId());
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

	public void updateComment(String studnetLoginId) {
		SearchAPI searchAPI = new SearchAPIImpl(this,
				new CommentInfoListParser());
		searchAPI.searchStudentComment(studnetLoginId);
	}

	private void setComment(CommentInfo commentInfo) {

		View commentView = layoutInflater.inflate(
				R.layout.professor_comment_layout, null);

		TextView professorId = (TextView) commentView
				.findViewById(R.id.professor_id);
		TextView comment = (TextView) commentView.findViewById(R.id.comment);

		professorId.setText("[" + commentInfo.getProfessorName() + "]님");
		comment.setText(commentInfo.getComment());

		commentListLayout.addView(commentView);
		
		if(commentInfo.getProfessorLoginId().equals(loginId)){
			mComment = commentInfo.getComment();
		}
	}

	@Override
	public void onSuccessSearch(PageInfo pageInfo) {

		commentListLayout.removeAllViews();

		for (CommentInfo commentInfo : pageInfo.getCommentInfos()) {
			setComment(commentInfo);
		}

		adapterSetterListener.onCompletedSettings();

		isCompletedSetting = true;
		adapterSetterListener.onCompletedSettings();
	}

	@Override
	public void onFailSearch() {
		adapterSetterListener.onFailSettings();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v.getId() == R.id.student_push_button) {

			if (event.getAction() == MotionEvent.ACTION_UP) {

				professorCommentBoardDialog = new EditableBoardDialog(
						context);
				professorCommentBoardDialog.setOnDialogListener(this);
				professorCommentBoardDialog.setTitle(name+" 님");
				professorCommentBoardDialog.setContent(mComment);
				professorCommentBoardDialog.show();
			}
		}

		return true;
	}

	@Override
	public void onTouchedCommitButton(String content) {
		professorCommentBoardDialog.dismiss();

		if (userType == UserType.PROFESSOR) {
			CommentInfo commentInfo = new CommentInfo();

			commentInfo.setProfessorLoginId(loginId);
			commentInfo.setStudentLoginId(adapterItem.getStudentPersonnelInfo()
					.getLoginId());
			commentInfo.setComment(content);

			RegisterAPI registerAPI = new RegisterAPIImpl(this);
			registerAPI.registerComment(commentInfo);
		} else if (userType == UserType.COMPANY) {
			// register
		}
	}

	@Override
	public void onSuccessRegister() {
		if (userType == UserType.PROFESSOR) {
			Toast.makeText(context, "등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
			updateComment(adapterItem.getStudentPersonnelInfo().getLoginId());
		} else if (userType == UserType.COMPANY) {
			// toast
		}
	}

	@Override
	public void onFailRegister() {
		Toast.makeText(context, "입력에 실패하였습니다.", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDuplicatedRegister() {

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
