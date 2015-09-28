package com.monkey.phoneguard.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by MonkeyKiky on 2015/9/20.
 */
public class StreamUtils {

    /**
     * 从输入流中读取字符串
     * @param is 输入流
     * @return 读取的字符串
     * @throws IOException
     */
    public static String readFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        String result = baos.toString();
        baos.close();
        is.close();
        return result;
    }
}
