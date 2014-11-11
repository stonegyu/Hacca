package com.broccoli.hacca.dataconnectapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;
import android.util.Log;

public class DataBaseConnectWorker extends AsyncTask<String, Void, String> {
	private final String TAG = "DataBaseConnectWorker";
	private OnDataBaseConnectWorkerListner workerListner;

	public DataBaseConnectWorker(OnDataBaseConnectWorkerListner workerListner) {
		this.workerListner = workerListner;
	}

	@Override
	protected String doInBackground(String... urls) {
		String result = "";

		DefaultHttpClient client = new DefaultHttpClient();

		try {
			HttpPost post = new HttpPost(urls[0]);

			HttpParams httpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
			HttpConnectionParams.setSoTimeout(httpParams, 3000);

			HttpResponse response = client.execute(post);
			BufferedReader bufreader = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent(),
							"utf-8"));

			String line;
			while ((line = bufreader.readLine()) != null){
				result += line;
			}

		} catch (Exception e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		workerListner.onCompleted(result);
	}

}
