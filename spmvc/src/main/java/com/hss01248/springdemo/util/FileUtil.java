package com.hss01248.springdemo.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2017/1/8 0008.
 */
public class FileUtil {

    public static boolean downloadFile(String urlString, String imageName)
    {

        try {

            URL url =  new URL(urlString);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
            DataInputStream dataInputStream = new DataInputStream(conn.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }

            MyLog.e(urlString+" 下载完成,\n路径:" +imageName);



            dataInputStream.close();
            fileOutputStream.close();
            return true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            MyLog.e(urlString+" 下载失败");
        } catch (IOException e) {
            e.printStackTrace();
            MyLog.e(urlString+" 下载失败");
        }
        return false;
        /*URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File f = new File(localFilePath);
        try
        {
            urlfile = new URL(url);
            httpUrl = (HttpURLConnection)urlfile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(f));
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1)
            {
                bos.write(b, 0, len);
            }
            bos.flush();
            bis.close();
            httpUrl.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                bis.close();
                bos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }*/
    }
}
