package com.kinview.zft.ad;

import java.text.SimpleDateFormat;

import com.kinview.config.Config;
import com.kinview.config.print;
import com.kinview.entity.Ad;
import com.kinview.util.AdService;
import com.kinview.zft.R;
import com.kinview.zft.R.string;
import com.sun.org.apache.bcel.internal.generic.LoadClass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActivityAdView extends Activity
{
	String codeString  = "";
	private TextView  code_and_isdel,applicant,phone,person,address_and_leibie,xingshi_and_isurge;
	private TextView  pzrq,deadline,nature_and_shcc;
	private Button buttonOK;
	String[][] Temp = null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent it = this.getIntent();
		codeString = it.getStringExtra("codeid");
		setContentView(R.layout.czs_adview);
		print.out("codeString ="+ codeString);
		load();
	}
	
	
	private void load()
	{
		// TODO Auto-generated method stub
		Ad ad = new Ad();
		code_and_isdel = (TextView) findViewById(R.id.czs_ad_tv_code_and_isdel);
		applicant = (TextView) findViewById(R.id.czs_ad_tv_applicant);
		phone = (TextView) findViewById(R.id.czs_ad_tv_phone);
		person = (TextView) findViewById(R.id.czs_ad_tv_person);
		address_and_leibie = (TextView) findViewById(R.id.czs_ad_tv_address_and_leibie);
		xingshi_and_isurge = (TextView) findViewById(R.id.czs_ad_tv_xingshi_and_isurge);
		pzrq = (TextView) findViewById(R.id.czs_ad_tv_pzrq);
		deadline = (TextView) findViewById(R.id.czs_ad_tv_deadline);
		nature_and_shcc = (TextView) findViewById(R.id.czs_ad_tv_nature_and_shcc);
        buttonOK = (Button)findViewById(R.id.czs_ad_btnBack);
        buttonOK.setOnClickListener(btnClick);
        
		AdService  adService = new AdService();
		ad = adService.getAdInformation(codeString);
//		for(int i = 0;i<Temp.length;i++)
//		{
//			for(int j = 0;j<Temp[0].length;j++)
//			{
//				print.out("第"+j+"条结果如下：\r\n"+Temp[i][j].toString());
//			}
//		}
		String state = "";//状态
		if(ad.getIsdel().toString() !=""||ad.getIsdel()!=null)
		{
			switch (Integer.valueOf(ad.getIsdel()))
			{
				case 0:
					state = "通过";
		            break;
		        case 2:
		        	state = "待审";
		            break;
		        case 3:
		        	state = "待处理";
		            break;
		        default:
		        	state = "作废";
		            break;
			}
		}
		 else
         {
			 state = "无该数据";
         }
		String xingzhi = "";//性质
		if(ad.getNature().toString() !=""||ad.getNature()!=null)
		{
			switch (Integer.valueOf(ad.getNature()))
			{
				case 5:
					xingzhi = "新增";
		            break;
		        case 6:
		        	xingzhi = "续办";
		            break;
		        case 8:
		        	xingzhi = "更换画面";
		            break;
		        case 130:
		        	xingzhi = "其它";
		            break;
		        default:
		        	xingzhi = "";
		            break;
			}
		}
		else
        {
			xingzhi = "无该数据";
        }
		
		String cuibanjian = "";//催办件
		if(ad.getIsurge().toString() !=""||ad.getIsurge()!=null)
		{
			if (Integer.valueOf(ad.getIsurge())== 0)
			{
				cuibanjian = "否";
            }
            else
            {
            	cuibanjian = "是";
            }
		}
		else
        {
			cuibanjian = "无";
        }
		
		String ccsh = "";//尺寸审核
		if(ad.getShcc().toString() !=""||ad.getShcc()!=null)
		{
			print.out("ad.getShcc().toString()"+ad.getShcc().toString());
			if (Integer.valueOf(ad.getShcc().toString())== 0)
			{
				ccsh = "未审";
            }
            else
            {
            	ccsh = "已审";
            }
		}
		else
        {
			ccsh = "无";
        }
		
		String endTime ="";
		if (ad.getDeadline().toString() == "" || ad.getDeadline() == null)
        {
			//广告到期时间
			endTime = "无";
        }
        else
        {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        	endTime = sdf.format(ad.getDeadline()).toString();
        	
        }
		
		code_and_isdel.setText("编号："+ ad.getCode().toString()+"   状态："+state);
		applicant.setText("申请单位："+ ad.getApplicant().toString());
		 phone.setText("电          话："+ ad.getPhone().toString());
		person.setText("负   责  人："+ ad.getPerson().toString());
		address_and_leibie.setText("地段："+ ad.getDiduan().toString() +"     类别："+ ad.getLeibie().toString());
		xingshi_and_isurge.setText("形式："+ ad.getXingshi().toString()+"   催办件："+ cuibanjian);
		nature_and_shcc.setText("性质："+ xingzhi +"  尺寸审核："+ ccsh);
		pzrq.setText("批准时间："+ ad.getPzrq().toString());
		deadline.setText("到期时间："+ endTime);
		
	}
	
	
	OnClickListener btnClick = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			finish();
		}
	};
	
	
}
