package com.broccoli.hacca.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.broccoli.hacca.R;
import com.broccoli.hacca.dialog.LoadingProgressDialog;
import com.broccoli.hacca.internalstorageapi.InternalStorageAPI;
import com.broccoli.hacca.internalstorageapi.InternalStorageAPIImpl;
import com.broccoli.hacca.pageinfo.PageInfo;
import com.broccoli.hacca.pageinfo.StudentPersonnelInfo;
import com.broccoli.hacca.passer.searchparser.StudentPersonnelInfoParser;
import com.broccoli.hacca.registerapi.OnRegisterAPIListener;
import com.broccoli.hacca.registerapi.RegisterAPI;
import com.broccoli.hacca.registerapi.RegisterAPIImpl;
import com.broccoli.hacca.searchapi.OnSearchAPIListener;
import com.broccoli.hacca.searchapi.SearchAPI;
import com.broccoli.hacca.searchapi.SearchAPIImpl;

public class StudentEditProfilePageActivity extends Activity implements
		OnItemSelectedListener, OnClickListener, OnTouchListener,
		OnRegisterAPIListener, OnSearchAPIListener {

	private EditText studentName, studentCareer, studentHope, studentBlog,
			studentMail, studentAbility;

	private ArrayAdapter<String> studentAgeAdapter = null;
	private ArrayAdapter<String> studentSexAdapter = null;
	private ArrayAdapter<String> studentDepartmentAdapter = null;

	private String[] ageList;
	private String[] sexList;
	private String[] departmentList;

	private String studentAge;
	private String studentSex;
	private String studentDepartment;

	private Spinner studentAgeSpinner;
	private Spinner studentSexSpinner;
	private Spinner studentDepartmentSpinner;

	private ImageButton studentEditProfilePageCommitBtn;
	
	private String loginId;
	private String deviceId;
	
	private LoadingProgressDialog loadingProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.student_edit_profile_page_activity_layout);

		ageList = getResources().getStringArray(R.array.ages);
		sexList = getResources().getStringArray(R.array.sexs);
		departmentList = getResources().getStringArray(R.array.departments);

		studentName = (EditText) findViewById(R.id.student_name);
		studentCareer = (EditText) findViewById(R.id.student_career);
		studentHope = (EditText) findViewById(R.id.student_hope);
		studentBlog = (EditText) findViewById(R.id.student_blog);
		studentMail = (EditText) findViewById(R.id.student_mail);
		studentAbility = (EditText) findViewById(R.id.student_ability);

		studentAgeSpinner = (Spinner) findViewById(R.id.student_age);
		studentSexSpinner = (Spinner) findViewById(R.id.student_sex);
		studentDepartmentSpinner = (Spinner) findViewById(R.id.student_department);

		studentAgeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, ageList);
		studentAgeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		studentAgeSpinner.setAdapter(studentAgeAdapter);
		studentAgeSpinner.setSelection(0);
		studentAgeSpinner.setOnItemSelectedListener(this);

		studentSexAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, sexList);
		studentSexAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		studentSexSpinner.setAdapter(studentSexAdapter);
		studentSexSpinner.setSelection(0);
		studentSexSpinner.setOnItemSelectedListener(this);

		studentDepartmentAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, departmentList);
		studentDepartmentAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		studentDepartmentSpinner.setAdapter(studentDepartmentAdapter);
		studentDepartmentSpinner.setSelection(0);
		studentDepartmentSpinner.setOnItemSelectedListener(this);

		studentEditProfilePageCommitBtn = (ImageButton) findViewById(R.id.student_commit);
		studentEditProfilePageCommitBtn.setOnClickListener(this);
		studentEditProfilePageCommitBtn.setOnTouchListener(this);
		
		loadingProgressDialog = new LoadingProgressDialog(this);

		InternalStorageAPI storageAPI = new InternalStorageAPIImpl(this);
		
		loginId = storageAPI.getLoginId();
		deviceId = storageAPI.getDeviceId();
		
		if (loginId != null) {
			SearchAPI searchAPI = new SearchAPIImpl(this,
					new StudentPersonnelInfoParser());
			searchAPI.searchStudentInfo(loginId);

			loadingProgressDialog.show();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		switch (parent.getId()) {
		case R.id.student_age:
			studentAge = parent.getItemAtPosition(position).toString();
			break;
		case R.id.student_sex:
			studentSex = parent.getItemAtPosition(position).toString();
			break;
		case R.id.student_department:
			studentDepartment = parent.getItemAtPosition(position).toString();
			break;
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (v.getId() == R.id.student_commit
				&& event.getAction() == MotionEvent.ACTION_DOWN) {
			studentEditProfilePageCommitBtn
					.setBackgroundResource(R.drawable.edit_profile_page_layout_commit_btn_push);
		}
		if (v.getId() == R.id.student_commit
				&& event.getAction() == MotionEvent.ACTION_UP) {
			studentEditProfilePageCommitBtn
					.setBackgroundResource(R.drawable.edit_profile_page_layout_commit_btn);
		}
		return false;
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.student_commit) {

			if (!isFilledInTheBlanks()) {
				return;
			}

			// loginId
			// deviceId

			RegisterAPI registerAPI = new RegisterAPIImpl(this);
			registerAPI.registerStudent(getFilledStduentPersonnelInfo());

			loadingProgressDialog.show();
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	@Override
	public void onBackPressed() {
		// UIInterface.onBackPressed("profileexit", this);
	}

	private boolean isFilledInTheBlanks() {
		if (!isFilledInTheBlank(studentName.getText().toString(), "이름")) {
			return false;
		} else if (!isFilledInTheBlank(studentHope.getText().toString(), "희망분야")) {
			return false;
		} else if (!isFilledInTheBlank(studentBlog.getText().toString(), "블로그")) {
			return false;
		} else if (!isFilledInTheBlank(studentMail.getText().toString(), "메일")) {
			return false;
		} else if (!isFilledInTheBlank(studentAge, "나이")) {
			return false;
		} else if (!isFilledInTheBlank(studentSex, "성별")) {
			return false;
		} else if (!isFilledInTheBlank(studentDepartment, "학과")) {
			return false;
		}

		return true;
	}

	private boolean isFilledInTheBlank(String value, String name) {

		if (value.toString().length() == 0 || value.equals("선택")) {
			Toast.makeText(getApplicationContext(), name + "을 입력해주세요.",
					Toast.LENGTH_LONG).show();
			return false;
		} else {
			return true;
		}
	}

	private StudentPersonnelInfo getFilledStduentPersonnelInfo() {

		StudentPersonnelInfo studentPersonnelInfo = new StudentPersonnelInfo();

		studentPersonnelInfo.setStudentName(studentName.getText().toString());
		studentPersonnelInfo.setStudentCareer(studentCareer.getText()
				.toString());
		studentPersonnelInfo.setStudentMail(studentMail.getText().toString());
		studentPersonnelInfo.setStudentHope(studentHope.getText().toString());
		studentPersonnelInfo.setStudentBlog(studentBlog.getText().toString());
		studentPersonnelInfo.setStudentAbility(studentAbility.getText()
				.toString());
		studentPersonnelInfo.setStudentAge(studentAge);
		studentPersonnelInfo.setStudentSex(studentSex);
		studentPersonnelInfo.setStudentDepartment(studentDepartment);
		studentPersonnelInfo.setLoginId(loginId);
		studentPersonnelInfo.setDeviceId(deviceId);

		return studentPersonnelInfo;
	}

	@Override
	public void onSuccessRegister() {
		loadingProgressDialog.dismiss();
		Toast.makeText(getApplicationContext(), "등록에 성공하였습니다.",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onFailRegister() {
		loadingProgressDialog.dismiss();
		Toast.makeText(getApplicationContext(), "등록에 실패하였습니다.",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSuccessSearch(PageInfo pageInfo) {
		StudentPersonnelInfo studentPersonnelInfo = pageInfo.getStudentPersonnelInfo();
		setStudentInfo(studentPersonnelInfo);
		
		loadingProgressDialog.dismiss();
	}

	@Override
	public void onFailSearch() {
		loadingProgressDialog.dismiss();
		Toast.makeText(getApplicationContext(), "데이터를 가져오는데 실패하였습니다.",
				Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onDuplicatedRegister() {
		loadingProgressDialog.dismiss();
		Toast.makeText(getApplicationContext(), "이미 등록된 ID입니다.",
				Toast.LENGTH_SHORT).show();
	}
	
	private void setStudentInfo(StudentPersonnelInfo studentPersonnelInfo){
		studentName.setText(studentPersonnelInfo.getStudentName());
		studentCareer.setText(studentPersonnelInfo.getStudentCareer());
		studentMail.setText(studentPersonnelInfo.getStudentMail());
		studentHope.setText(studentPersonnelInfo.getStudentHope());
		studentBlog.setText(studentPersonnelInfo.getStudentBlog());
		studentAbility.setText(studentPersonnelInfo.getStudentAbility());
		studentAge = studentPersonnelInfo.getStudentAge();
		studentSex = studentPersonnelInfo.getStudentSex();
		studentDepartment = studentPersonnelInfo.getStudentDepartment();
		studentAgeSpinner.setSelection(getSpinnerNumber(ageList, studentAge));
		studentSexSpinner.setSelection(getSpinnerNumber(sexList, studentSex));
		studentDepartmentSpinner.setSelection(getSpinnerNumber(departmentList, studentDepartment));
	}
	
	private int getSpinnerNumber(String[] list,String value){
		int number=0;
		
		value = value.replace(" ", "");
		
		for(int i=0;i<list.length;i++){
			if(list[i].equals(value)){
				number = i; 
			}
		}
		
		return number;
	}
}
