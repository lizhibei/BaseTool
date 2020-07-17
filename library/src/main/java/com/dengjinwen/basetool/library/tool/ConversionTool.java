package com.dengjinwen.basetool.library.tool;

import java.net.URL;

public class ConversionTool {
      /**
       * 字符串转为URL对象
       * @param url url字符串
       * @return url对象
       */
      private static URL stringToURL(String url){
            if(url==null || url.length() == 0 || !url.contains("://")){
                  return null;
            }
            try {
                  StringBuilder sbUrl = new StringBuilder("http");
                  sbUrl.append(url.substring(url.indexOf("://")));
                  URL mUrl = new URL(sbUrl.toString());
                  return mUrl;
            }catch (Exception ex){
                  ex.printStackTrace();
                  return null;
            }
      }
}
