package com.donald.txnotifymsgaccount.utils;

import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author donald
 * @date 2022/05/08
 */
public class BaseConnectionUtils {
    //文件流类型
    public static final String TYPE_STREAM = "type_stream";
    //字符串类型
    public static final String TYPE_STRING = "type_string";


    /**
     * 从getMethod中获取字符串
     * @param getMethod
     * @param type
     * @return
     * @throws Exception
     */
    protected static String getStringFromGetMethod(GetMethod getMethod, String type) throws Exception{
        String result = "";
        switch (type) {
            case TYPE_STREAM:
                InputStream in = getMethod.getResponseBodyAsStream();
                if(in != null){
                    InputStreamReader reader = new InputStreamReader(in, CharsetCode.getCharset(CharsetCode.CHARSET_UTF));
                    result = FileCopyUtils.copyToString(reader);
                }
                break;
            case TYPE_STRING:
                result = getMethod.getResponseBodyAsString();
                break;
            default:
                result = getMethod.getResponseBodyAsString();
                break;
        }
        return result;
    }
}