package me.temoa.baseutils;

import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lai
 * on 2018/3/23.
 */

@SuppressWarnings({"WeakerAccess", "unused"}) // public api
public class StreamUtils {

    @NonNull
    public static String readStream(InputStream is, String encode) throws IOException {
        return new String(readStream(is), encode);
    }

    public static byte[] readStream(InputStream is) throws IOException {
        ByteArrayOutputStream baso = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            baso.write(buffer, 0, len);
        }
        byte[] result = baso.toByteArray();
        baso.close();
        return result;
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
