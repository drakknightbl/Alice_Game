package com.alu.alice_game.server;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.alu.alice_game.Main;

public class HttpRequest {
    	HttpPostThread htp;
    	HttpGetThread htg;

        /**
        * Post request (upload files)
        * @param sUrl
        * @param params Form data
        * @param files
        * @return
        */
        public void post(String sUrl, List<NameValuePair>nameValuePairs) {
                this.htp = new HttpPostThread(sUrl, nameValuePairs);
                this.htp.run();
        }

        
        

        public void get(String sUrl, Context ctx) {
            this.htg = new HttpGetThread(sUrl, ctx);
            this.htg.run();
        }
        
        class HttpPostThread extends Thread {
        	String sUrl;
        	List<NameValuePair> params;
        	
        	public HttpPostThread(String sUrl, List<NameValuePair>nameValuePairs) {
                    this.sUrl = sUrl;
                    this.params = nameValuePairs;
        	}
        	
        	public void run() {
                    try {
                        HttpClient hc = new DefaultHttpClient();
                        HttpPost hp = new HttpPost(this.sUrl);
                        Log.i("HttpRequest", "send : " + this.sUrl);
                        hp.setEntity(new UrlEncodedFormEntity(this.params));
                        HttpResponse hr = hc.execute(hp);
                        
                    } catch (ClientProtocolException e) {
                        Log.e("HttpRequest", "ClientProtocolException");
                    } catch (IOException e) {
                        Log.e("HttpRequest", "IOException" + e.getMessage());

                    }

             }
        	
        	
        }
        
        public void onFinish() {
    		if(this.htg!=null){
    			this.htg.onFinish();
    		}
    	}

        class HttpGetThread extends Thread {
            String sUrl;
            Context ctx;
            private Handler mHandler;

            public HttpGetThread(String sUrl, Context ctx) {
                this.sUrl = sUrl;
                this.ctx = ctx;
                this.mHandler = new Handler();
            }

            private Runnable pollAgain = new Runnable() {
                public void run() {
                	HttpRequest.this.get(HttpGetThread.this.sUrl, HttpGetThread.this.ctx);
                }
            };
            
            public void onFinish() {
            	Log.i("HttpRequest", "onFinish");
            	this.mHandler.removeCallbacks(pollAgain);
            }

            public void run() {

                try {
                    Log.i("HttpRequest", "get " + this.sUrl);
                    HttpClient hc = new DefaultHttpClient();
                    HttpGet get = new HttpGet(this.sUrl);
                    HttpResponse hr = hc.execute(get);
                    HttpEntity he = hr.getEntity();
                    
                    if(he != null) {
                        String message = EntityUtils.toString(he);
                        if(message.equals("")) { // poll
                            this.mHandler.removeCallbacks(pollAgain);
                            this.mHandler.postDelayed(pollAgain, 5000);
                        } else { 
                            Main m = (Main) this.ctx;
                            m.messageReceived(message);
                        }
                    } else {
                        Log.e("HttpRequest", this.sUrl + " return null");
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }

            }


        }
        
}
