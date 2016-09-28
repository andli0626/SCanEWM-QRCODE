package com.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crash.LogUtils;
import com.epoint.scan.ewm.R;
import com.google.zxing.WriterException;
import com.zxing.activity.CaptureActivity;
import com.zxing.encoding.EncodingHandler;

public class MainActivity extends Activity {

	private TextView 	resultTextView;	
	private EditText 	qrStrEditText;
	private ImageView 	qrImgImageView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainactivity);
		setTitle("新点扫码");

		resultTextView = (TextView) this.findViewById(R.id.tv_scan_result);
		qrStrEditText  = (EditText) this.findViewById(R.id.et_qr_string);
		qrImgImageView = (ImageView) this.findViewById(R.id.iv_qr_image);

		// 扫一扫
		Button scanBarCodeButton = (Button) this.findViewById(R.id.btn_scan_barcode);
		scanBarCodeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 打开扫描界面扫描条形码或二维码
				Intent openCameraIntent = new Intent(MainActivity.this,CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
			}
		});

		// 生成二维码
		Button generateQRCodeButton = (Button) this.findViewById(R.id.btn_add_qrcode);
		generateQRCodeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String contentString = qrStrEditText.getText().toString();
					if (!contentString.equals("")) {
						// 根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
						Bitmap qrCodeBitmap = EncodingHandler.createQRCode(contentString, 350);
						qrImgImageView.setImageBitmap(qrCodeBitmap);
					} else {
						Toast.makeText(MainActivity.this, "内容不为空!",Toast.LENGTH_SHORT).show();
					}

				} catch (WriterException e) {
					e.printStackTrace();
				}
			}
		});
		
		// 扫描内容点击
		resultTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String scanResult = resultTextView.getText().toString().trim();
				if(scanResult!=null && !scanResult.equals("") && scanResult.length()>4 && scanResult.substring(0, 4).contains("http")){
					openURL(scanResult);
				}
			}
		});
	}

	// 扫一扫回调页面
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 处理扫描结果（在界面上显示）
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			String barcode 	  = bundle.getString("barcode");
			
			resultTextView.setText(scanResult);
			LogUtils.Log2Storage("扫描结果："+scanResult);
			if(scanResult!=null && !scanResult.equals("") && scanResult.length()>4 && scanResult.substring(0, 4).contains("http")){
				openURL(scanResult);
			}
		}
	}

	// 打击URL
	private void openURL(final String url) {
		new AlertDialog
		.Builder(MainActivity.this)
		.setTitle("提示")
		.setCancelable(false)
		.setMessage("是否打开该网址")
		.setPositiveButton("是", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Intent intent = new Intent(MainActivity.this,OpenUrlActivity.class);
						intent.putExtra("url", url);
						startActivity(intent);
					}
				})
		.setNegativeButton("否", null).create().show();

	}
}