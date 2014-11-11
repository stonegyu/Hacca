package com.broccoli.hacca.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.broccoli.hacca.R;

public class EditableBoardDialog extends Dialog implements OnTouchListener{
	
	private ImageView commitBtn;
	private OnDialogListener dialogListener;
	private EditText editText;
	private String title;
	private String content;
	
	public EditableBoardDialog(Context context) {
		super(context);
	}
	
	public void setOnDialogListener(OnDialogListener dialogListener){
		this.dialogListener = dialogListener;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setContent(String content){
		this.content = content;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.professor_commentboard_dialog_layout);
		
		commitBtn = (ImageView) findViewById(R.id.commentok_button);
		commitBtn.setOnTouchListener(this);
		
		editText = (EditText)findViewById(R.id.edit_comment);
		editText.setText(content);
		
		((TextView)findViewById(R.id.professorid)).setText(title);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v.getId() == R.id.commentok_button){
			dialogListener.onTouchedCommitButton(editText.getText().toString());
		}
		
		return true;
	}
}
