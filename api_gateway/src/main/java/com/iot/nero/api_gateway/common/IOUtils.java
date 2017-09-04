package com.iot.nero.api_gateway.common;

import java.io.*;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/4
 * Time   上午12:13
 */
public class IOUtils {

    public static void inputStreamToFile(InputStream ins, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }
}
