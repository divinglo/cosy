package cosyfish.pro.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

@SuppressWarnings("restriction")
public class BASE64Utils {
    private static Logger LOGGER = LoggerFactory.getLogger(BASE64Utils.class);

    public static String convertToBase64(String pathname) {

        return convertToBase64(new File(pathname));
    }

    public static String convertToBase64(File file) {
        String base64Str = "";

        try {
            try (InputStream in = new FileInputStream(file); InputStream bin = new BufferedInputStream(in);) {
                // jdk7新特性，对资源自动进行回收
                byte[] bytes = new byte[bin.available()];
                bin.read(bytes);
                Base64.Encoder encoder = Base64.getEncoder();
                base64Str = encoder.encodeToString(bytes);
            }

        } catch (FileNotFoundException fe) {
            LOGGER.error("File does not exist", fe);
        } catch (IOException ioe) {
            LOGGER.error("", ioe);
        }

        return base64Str;
    }

    public static String convertToBase64(InputStream in) {
        String base64Str = "";

        try {
            try (InputStream bin = new BufferedInputStream(in);) { // jdk7新特性，对资源自动进行回收
                byte[] bytes = new byte[bin.available()];
                bin.read(bytes);
                Base64.Encoder encoder = Base64.getEncoder();
                base64Str = encoder.encodeToString(bytes);
            }

        } catch (FileNotFoundException fe) {
            LOGGER.error("File does not exist", fe);
        } catch (IOException ioe) {
            LOGGER.error("", ioe);
        }

        return base64Str;
    }

    public static void convertBase64ToFile(String path, String fileName, String base64Str) {
        File filePath = new File(path);
        if (!filePath.exists() && !filePath.mkdirs()) {
            LOGGER.error("File path:[ " + path + " ] does not exist and make directories fail!!!");
            return;
        }
        String fileFullPath = (path.lastIndexOf(File.separator) != path.length() - 1 ? path + File.separator : path) + fileName;
        Base64.Decoder decoder = Base64.getDecoder();
        try {

            try (OutputStream fos = new FileOutputStream(fileFullPath); OutputStream bos = new BufferedOutputStream(fos);) {
                byte[] bytes = decoder.decode(base64Str);
                bos.write(bytes);
            }

        } catch (IOException e) {
            LOGGER.error("convertBase64ToFile fail!", e);
        }
    }

    public static byte[] base64ConvertToBytes(String base64Str) throws IOException {
        Base64.Decoder decoder = Base64.getDecoder();
        return  decoder.decode(base64Str);
    }

    public static String convertToBase64(BufferedImage bi) {

        String base64 = "";

        try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
            ImageIO.write(bi, "JPEG", os);
            try (InputStream is = new ByteArrayInputStream(os.toByteArray());) {
                base64 = convertToBase64(is);
            }
        } catch (IOException e) {
            LOGGER.error("convertBase64To BufferedImage fail!", e);
        }
        return base64;
    }
}
