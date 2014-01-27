package com.project.make.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class Client_Jsp_update extends Thread {
	String mAddr;
	String check;
	String mResult;
	
		public void Setting(String addr,String check){
			mAddr=addr;
			this.check = check;
			mResult="";
		}
	//정보
		@Override
		public void run(){
			super.run();
			
			HttpClient httpclient = new DefaultHttpClient();
			
			InputStream is = null;
				try{
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("check",check));
					HttpParams params= httpclient.getParams();
					//파라미터 얻기
					HttpConnectionParams.setConnectionTimeout(params, 5000);
					//5초 이상 연결
					HttpPost httppost = new HttpPost(mAddr);
					//post 방식
					UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nameValuePairs,"UTF-8");
					//다국어 처리
					httppost.setEntity(entityRequest);
					//엔티티 지정
					HttpResponse response = httpclient.execute(httppost);
					//실행하고 결과 response 로 받아오기
					HttpEntity entityResponse = response.getEntity();
					//엔티티 얻기
					is = entityResponse.getContent();
					//응답된 데이터를 받음
					BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"),8);
					//인코딩 처리 버퍼드리더 얻어옴
					
					StringBuilder sb = new StringBuilder();
					
					while(true){
						String line = reader.readLine();
						if(line==null){
							break;
						}
						sb.append(line);
					}
					is.close();
					mResult = sb.toString();
				}catch(IOException e){
					e.printStackTrace();
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					httpclient.getConnectionManager().shutdown();
					//httpclient 닫음
				}
		}//run
		
	 public String getResult(){
		 return mResult;
	 }
}
