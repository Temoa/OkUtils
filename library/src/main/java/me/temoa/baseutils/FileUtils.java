package me.temoa.baseutils;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

/**
 * Created by lai
 * on 2018/3/19.
 */

@SuppressWarnings({"WeakerAccess", "unused"}) // public api
public class FileUtils {

    public static boolean copyFile(final File sourceFile, final File targetFile) {
        boolean success = false;
        if (sourceFile == null || targetFile == null) {
            return false;
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fis = new FileInputStream(sourceFile);
            fos = new FileOutputStream(targetFile);
            in = fis.getChannel();
            out = fos.getChannel();
            in.transferTo(0, in.size(), out);
            success = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public static boolean saveFile(
            final String content, final String folder, final String fileName) {

        if (TextUtils.isEmpty(content)) {
            return false;
        }
        InputStream is = new ByteArrayInputStream(content.getBytes());
        return saveFile(is, folder, fileName);
    }

    public static boolean saveFile(
            final InputStream is, final String folder, final String fileName) {

        boolean success = false;
        if (TextUtils.isEmpty(folder) || TextUtils.isEmpty(fileName)) {
            return false;
        }
        File fileDir = new File(folder);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        File file = new File(fileDir, fileName);
        byte[] buffer = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            fos = new FileOutputStream(file);
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public static String readFile(final File file) {
        if (!file.exists()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);
                sb.append("\n");
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
