package cosyfish.pro.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

/**
 * 二维码生成工具类
 *
 * @author guangmingxu
 */
public class QrcodeUtil {

    /**
     * @param content 文本，如：123456
     * @param qrCodeSize 二维码大小：建议1080
     * @param logoSize logo大小：建议108
     * @param logoUrl logo图片地址：有默认值
     * @param imageFormat 图片编码：默认JPEG
     */
    public static byte[] createQrcodeWithLogo(String content, int qrCodeSize, int logoSize, String logoUrl, String imageFormat)
            throws WriterException, IOException {
        // 设置二维码纠错级别ＭＡＰ
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); // 矫错级别
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        // 创建比特矩阵(位矩阵)的QR码编码的字符串
        BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hintMap);
        // 使BufferedImage勾画QRCode (matrixWidth 是行二维码像素点)
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth - 200, matrixWidth - 200, BufferedImage.TYPE_INT_RGB);

        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // 使用比特矩阵画并保存图像
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i - 100, j - 100, 1, 1);
                }
            }
        }

        logoUrl = logoUrl == null ? "http://img.suv666.com/wechat/material/image/20180407/5c92e3a5eb099a3469443f4187ec7887.jpg" : logoUrl;
        BufferedImage logo = ImageIO.read(new URL(logoUrl));
        /**
         * 设置logo的大小
         */
        int widthLogo = logoSize;
        int heightLogo = logoSize;

        /**
         * logo放在中心
         */
        int x = (image.getWidth() - widthLogo) / 2;
        int y = (image.getHeight() - heightLogo) / 2;
        /**
         * logo放在右下角 int x = (image.getWidth() - widthLogo); int y = (image.getHeight() -
         * heightLogo);
         */

        // 开始绘制图片
        graphics.drawImage(logo, x, y, widthLogo, heightLogo, null);
        graphics.dispose();
        logo.flush();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, imageFormat == null ? "JPEG" : imageFormat, outputStream);
        image.flush();
        byte[] bytes = outputStream.toByteArray();
        return bytes;
    }

}
