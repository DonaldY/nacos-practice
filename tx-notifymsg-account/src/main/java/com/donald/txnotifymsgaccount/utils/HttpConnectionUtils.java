package com.donald.txnotifymsgaccount.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author donald
 * @date 2022/05/08
 */
public class HttpConnectionUtils extends BaseConnectionUtils{

    /**
     * GET方式请求数据获取资源
     * @param url
     * @param nameValuePairs
     * @return
     */
    public static String getPayData(String url, NameValuePair[] nameValuePairs, Map<String, String> headers, String type) throws Exception{
        String result = "";
        GetMethod getMethod = new GetMethod(url);
        if(ObjectUtils.isEmpty(nameValuePairs)){
            nameValuePairs = new NameValuePair[]{};
        }
        if(!CollectionUtils.isEmpty(headers)){
            for(Map.Entry<String, String> entry : headers.entrySet()){
                getMethod.setRequestHeader(entry.getKey(), entry.getValue());
            }
        }
        getMethod.setQueryString(nameValuePairs);
        try {
            int status = new HttpClient().executeMethod(getMethod);
            if (status == HttpStatus.SC_OK) {
                result = getStringFromGetMethod(getMethod, type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
                getMethod = null;
            }
        }
        return result;
    }
}