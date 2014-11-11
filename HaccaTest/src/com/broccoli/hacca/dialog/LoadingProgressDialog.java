package com.broccoli.hacca.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import com.broccoli.hacca.R;

public class LoadingProgressDialog extends Dialog{

	public LoadingProgressDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		setContentView(R.layout.loading_progress_dialog_layout);
		setCancelable(false);
	}
}
