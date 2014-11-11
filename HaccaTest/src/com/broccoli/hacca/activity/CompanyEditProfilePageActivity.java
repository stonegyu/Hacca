package com.broccoli.hacca.activity;

import android.app.Activity;
import android.graphics.PorterDuff.Mode;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.broccoli.hacca.R;
import com.broccoli.hacca.dialog.LoadingProgressDialog;
import com.broccoli.hacca.internalstorageapi.InternalStorageAPI;
import com.broccoli.hacca.internalstorageapi.InternalStorageAPIImpl;
import com.broccoli.hacca.pageinfo.CompanyPersonnelInfo;
import com.broccoli.hacca.pageinfo.PageInfo;
import com.broccoli.hacca.passer.searchparser.CompanyPersonnelInfoParser;
import com.broccoli.hacca.registerapi.OnRegisterAPIListener;
import com.broccoli.hacca.registerapi.RegisterAPI;
import com.broccoli.hacca.registerapi.RegisterAPIImpl;
import com.broccoli.hacca.searchapi.OnSearchAPIListener;
import com.broccoli.hacca.searchapi.SearchAPI;
import com.broccoli.hacca.searchapi.SearchAPIImpl;

public class CompanyEditProfilePageActivity extends Activity implements
		OnItemSelectedListener, OnClickListener, OnTouchListener,
		OnRegisterAPIListener, OnSearchAPIListener {

	private EditText companyName, companySalary, companyRecruitVolume,
			companyRecruitPeriod, companyMail, companyHomePage,
			companyPassword, companyRepassword, companyArea;

	private Spinner companySizeSpinner, companyBusinessTypeSpinner,
			companyWorkTypeSpinner;

	private ArrayAdapter<String> companySizeAdapter,
			companyBusinessTypeAdapter, companyWorkTypeAdapter;

	private String[] companySizeList;
	private String[] companyBusinessTypeList;
	private String[] companyWorkTypeList;

	private String companySize, companyBusinessType, companyWorkType;

	private ImageButton companyEditProfilePageCommitBtn;

	private String loginId;

	private String deviceId;
	
	private LoadingProgressDialog loadingProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.company_edit_profile_page_activity_layout);

		companySizeList = getResources().getStringArray(
				R.array.company_size_list);
		companyBusinessTypeList = getResources().getStringArray(
				R.array.company_businesstype_list);
		companyWorkTypeList = getResources().getStringArray(
				R.array.company_worktype_list);

		companyName = (EditText) findViewById(R.id.company_name);
		companySalary = (EditText) findViewById(R.id.company_salary);
		companyRecruitVolume = (EditText) findViewById(R.id.company_recruitvolume);
		companyRecruitPeriod = (EditText) findViewById(R.id.company_recruitperiod);
		companyMail = (EditText) findViewById(R.id.company_mail);
		companyHomePage = (EditText) findViewById(R.id.company_homepage);
		companyPassword = (EditText) findViewById(R.id.company_passwd);
		companyRepassword = (EditText) findViewById(R.id.company_repasswd);
		companyArea = (EditText) findViewById(R.id.company_area);

		companySizeSpinner = (Spinner) findViewById(R.id.company_size);
		companySizeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, companySizeList);
		companySizeSpinner.setAdapter(companySizeAdapter);
		companySizeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		companySizeSpinner.setSelection(0);
		companySizeSpinner.setOnItemSelectedListener(this);

		companyBusinessTypeSpinner = (Spinner) findViewById(R.id.company_businesstype);
		companyBusinessTypeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, companyBusinessTypeList);
		companyBusinessTypeSpinner.setAdapter(companyBusinessTypeAdapter);
		companyBusinessTypeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		companyBusinessTypeSpinner.setSelection(0);
		companyBusinessTypeSpinner.setOnItemSelectedListener(this);

		companyWorkTypeSpinner = (Spinner) findViewById(R.id.company_worktype);
		companyWorkTypeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, companyWorkTypeList);
		companyWorkTypeSpinner.setAdapter(companyWorkTypeAdapter);
		companyWorkTypeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		companyWorkTypeSpinner.setSelection(0);
		companyWorkTypeSpinner.setOnItemSelectedListener(this);

		companyEditProfilePageCommitBtn = (ImageButton) findViewById(R.id.company_commit);
		companyEditProfilePageCommitBtn.setOnClickListener(this);
		companyEditProfilePageCommitBtn.setOnTouchListener(this);
		
		loadingProgressDialog = new LoadingProgressDialog(this);

		InternalStorageAPI storageAPI = new InternalStorageAPIImpl(this);

		loginId = storageAPI.getLoginId();
		deviceId = storageAPI.getDeviceId();

		if (loginId != null) {
			SearchAPI searchAPI = new SearchAPIImpl(this,
					new CompanyPersonnelInfoParser());
			searchAPI.searchCompanyInfo(loginId,"");

			loadingProgressDialog.show();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		switch (parent.getId()) {
		case R.id.company_size:
			companySize = parent.getItemAtPosition(position).toString();
			break;
		case R.id.company_businesstype:
			companyBusinessType = parent.getItemAtPosition(position).toString();
			break;
		case R.id.company_worktype:
			companyWorkType = parent.getItemAtPosition(position).toString();
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.company_commit) {

			if (!isFilledInTheBlanks()) {
				return;
			}

			if (!companyPassword.getText().toString()
					.equals(companyRepassword.getText().toString())) {
				Toast.makeText(this, "비밀번호와 비밀번호확인이 일치하지 않습니다.",
						Toast.LENGTH_LONG).show();
			}

			// loginId
			// deviceId

			RegisterAPI registerAPI = new RegisterAPIImpl(this);
			registerAPI.registerCompany(getFilledCompanyPersonnelInfo());

			loadingProgressDialog.show();
		}

	}

	private boolean isFilledInTheBlanks() {
		if (!isFilledInTheBlank(companyName.getText().toString(), "이름")) {
			return false;
		} else if (!isFilledInTheBlank(companyPassword.getText().toString(),
				"비밀번호")) {
			return false;
		} else if (!isFilledInTheBlank(companyRepassword.getText().toString(),
				"비밀번호확인")) {
			return false;
		} else if (!isFilledInTheBlank(companySize, "회사규모")) {
			return false;
		} else if (!isFilledInTheBlank(companyBusinessType, "업종")) {
			return false;
		} else if (!isFilledInTheBlank(companyWorkType, "근무형태")) {
			return false;
		} else if (!isFilledInTheBlank(companySalary.getText().toString(), "연봉")) {
			return false;
		} else if (!isFilledInTheBlank(companyRecruitVolume.getText()
				.toString(), "모집인원")) {
			return false;
		} else if (!isFilledInTheBlank(companyRecruitPeriod.getText()
				.toString(), "모집기간")) {
			return false;
		} else if (!isFilledInTheBlank(companyHomePage.getText().toString(),
				"홈페이지")) {
			return false;
		} else if (!isFilledInTheBlank(companyMail.getText().toString(), "메일")) {
			return false;
		} else if (!isFilledInTheBlank(companyArea.getText().toString(), "근무위치")) {
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

	private CompanyPersonnelInfo getFilledCompanyPersonnelInfo() {

		CompanyPersonnelInfo companyPersonnelInfo = new CompanyPersonnelInfo();

		companyPersonnelInfo.setCompanyName(companyName.getText().toString());
		companyPersonnelInfo.setCompanyArea(companyArea.getText().toString());
		companyPersonnelInfo.setCompanyBusinessType(companyBusinessType);
		companyPersonnelInfo.setCompanyHomePage(companyHomePage.getText()
				.toString());
		companyPersonnelInfo.setCompanyMail(companyMail.getText().toString());
		companyPersonnelInfo.setCompanyPassword(companyPassword.getText()
				.toString());
		companyPersonnelInfo.setCompanyRecruitPeriod(companyRecruitPeriod
				.getText().toString());
		companyPersonnelInfo.setCompanyRecruitVolume(companyRecruitVolume
				.getText().toString());
		companyPersonnelInfo.setCompanySalary(companySalary.getText()
				.toString());
		companyPersonnelInfo.setCompanySize(companySize);
		companyPersonnelInfo.setCompanyWorkType(companyWorkType);
		companyPersonnelInfo.setLoginId(loginId);
		companyPersonnelInfo.setDeviceId(deviceId);

		return companyPersonnelInfo;
	}

	@Override
	public void onBackPressed() {
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		ImageView view = (ImageView) v;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			view.setPadding(2, 2, 2, 2);
			view.setColorFilter(0xaa111111, Mode.SRC_OVER);
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			view.setPadding(0, 0, 0, 0);
			view.setColorFilter(0x00000000, Mode.SRC_OVER);
		}
		return false;
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
		CompanyPersonnelInfo companyPersonnelInfo = pageInfo.getCompanyPersonnelInfo();
		setCompanyInfo(companyPersonnelInfo);
		
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
	
	private void setCompanyInfo(CompanyPersonnelInfo companyPersonnelInfo){
		companyName.setText(companyPersonnelInfo.getCompanyName());
		companySalary.setText(companyPersonnelInfo.getCompanySalary());
		companyRecruitVolume.setText(companyPersonnelInfo.getCompanyRecruitVolume());
		companyRecruitPeriod.setText(companyPersonnelInfo.getCompanyRecruitPeriod());
		companyMail.setText(companyPersonnelInfo.getCompanyMail());
		companyHomePage.setText(companyPersonnelInfo.getCompanyHomePage());
		companyArea.setText(companyPersonnelInfo.getCompanyArea());
		companySize = companyPersonnelInfo.getCompanySize();
		companyBusinessType = companyPersonnelInfo.getCompanyBusinessType();
		companyWorkType = companyPersonnelInfo.getCompanyWorkType();
		companySizeSpinner.setSelection(getSpinnerNumber(companySizeList, companySize));
		companyBusinessTypeSpinner.setSelection(getSpinnerNumber(companyBusinessTypeList, companyBusinessType));
		companyWorkTypeSpinner.setSelection(getSpinnerNumber(companyWorkTypeList, companyWorkType));
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
