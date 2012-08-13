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
//				print.out("��"+j+"��������£�\r\n"+Temp[i][j].toString());
//			}
//		}
		String state = "";//״̬
		if(ad.getIsdel().toString() !=""||ad.getIsdel()!=null)
		{
			switch (Integer.valueOf(ad.getIsdel()))
			{
				case 0:
					state = "ͨ��";
		            break;
		        case 2:
		        	state = "����";
		            break;
		        case 3:
		        	state = "������";
		            break;
		        default:
		        	state = "����";
		            break;
			}
		}
		 else
         {
			 state = "�޸�����";
         }
		String xingzhi = "";//����
		if(ad.getNature().toString() !=""||ad.getNature()!=null)
		{
			switch (Integer.valueOf(ad.getNature()))
			{
				case 5:
					xingzhi = "����";
		            break;
		        case 6:
		        	xingzhi = "����";
		            break;
		        case 8:
		        	xingzhi = "��������";
		            break;
		        case 130:
		        	xingzhi = "����";
		            break;
		        default:
		        	xingzhi = "";
		            break;
			}
		}
		else
        {
			xingzhi = "�޸�����";
        }
		
		String cuibanjian = "";//�߰��
		if(ad.getIsurge().toString() !=""||ad.getIsurge()!=null)
		{
			if (Integer.valueOf(ad.getIsurge())== 0)
			{
				cuibanjian = "��";
            }
            else
            {
            	cuibanjian = "��";
            }
		}
		else
        {
			cuibanjian = "��";
        }
		
		String ccsh = "";//�ߴ����
		if(ad.getShcc().toString() !=""||ad.getShcc()!=null)
		{
			print.out("ad.getShcc().toString()"+ad.getShcc().toString());
			if (Integer.valueOf(ad.getShcc().toString())== 0)
			{
				ccsh = "δ��";
            }
            else
            {
            	ccsh = "����";
            }
		}
		else
        {
			ccsh = "��";
        }
		
		String endTime ="";
		if (ad.getDeadline().toString() == "" || ad.getDeadline() == null)
        {
			//��浽��ʱ��
			endTime = "��";
        }
        else
        {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
        	endTime = sdf.format(ad.getDeadline()).toString();
        	
        }
		
		code_and_isdel.setText("��ţ�"+ ad.getCode().toString()+"   ״̬��"+state);
		applicant.setText("���뵥λ��"+ ad.getApplicant().toString());
		 phone.setText("��          ����"+ ad.getPhone().toString());
		person.setText("��   ��  �ˣ�"+ ad.getPerson().toString());
		address_and_leibie.setText("�ضΣ�"+ ad.getDiduan().toString() +"     ���"+ ad.getLeibie().toString());
		xingshi_and_isurge.setText("��ʽ��"+ ad.getXingshi().toString()+"   �߰����"+ cuibanjian);
		nature_and_shcc.setText("���ʣ�"+ xingzhi +"  �ߴ���ˣ�"+ ccsh);
		pzrq.setText("��׼ʱ�䣺"+ ad.getPzrq().toString());
		deadline.setText("����ʱ�䣺"+ endTime);
		
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
