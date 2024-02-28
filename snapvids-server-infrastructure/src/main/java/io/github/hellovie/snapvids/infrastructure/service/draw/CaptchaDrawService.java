package io.github.hellovie.snapvids.infrastructure.service.draw;

import io.github.hellovie.snapvids.common.service.DrawService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 图片验证码绘制服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Component("captchaDrawService")
public class CaptchaDrawService implements DrawService {

    private static final Logger LOG = LoggerFactory.getLogger(CaptchaDrawService.class);

    private static final int FONT_SIZE = 40;

    /**
     * {@inheritDoc}
     *
     * @see DrawService#drawToBufferedImage(BufferedImage, String)
     */
    @Override
    public void drawToBufferedImage(BufferedImage bufferedImage, String text) {
        if (bufferedImage == null || StringUtils.isBlank(text)) {
            return;
        }

        final int width = calculateWidth(text);
        final int height = calculateHeight(text);

        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        // 设置画笔颜色-验证码背景色
        graphics.setColor(Color.WHITE);
        // 填充背景
        graphics.fillRect(0, 0, width, height);
        graphics.setFont(new Font("宋体,楷体,微软雅黑", Font.BOLD, FONT_SIZE));


        // 旋转原点的 x 坐标
        int x = 10;
        String ch;
        Random random = new Random();

        for (int i = 0; i < text.length(); i++) {
            graphics.setColor(getRandomColor());

            // 设置字体旋转角度，角度小于 30 度
            int degree = random.nextInt() % 30;

            ch = String.valueOf(text.charAt(i));

            // 正向旋转
            graphics.rotate(degree * Math.PI / 180, x, 45);
            graphics.drawString(ch, x, 45);

            // 反向旋转
            graphics.rotate(-degree * Math.PI / 180, x, 45);
            x += 48;
        }

        // 画干扰线
        for (int i = 0; i < 6; i++) {
            // 设置随机颜色
            graphics.setColor(getRandomColor());

            // 随机画线
            graphics.drawLine(random.nextInt(width), random.nextInt(height),
                    random.nextInt(width), random.nextInt(height));

        }

        // 添加噪点
        for (int i = 0; i < 30; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);

            graphics.setColor(getRandomColor());
            graphics.fillRect(x1, y1, 2, 2);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see DrawService#drawToBase64(StringBuffer, String)
     */
    @Override
    public void drawToBase64(StringBuffer base64, String text) {
        if (base64 == null || StringUtils.isBlank(text)) {
            return;
        }

        final int width = calculateWidth(text);
        final int height = calculateHeight(text);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        drawToBufferedImage(image, text);
        try (FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream();) {
            ImageIO.write(image, "png", outputStream);
            String base64Image = new BASE64Encoder().encode(outputStream.toByteArray());
            base64.append(base64Image);
        } catch (IOException e) {
            LOG.error("[Draw Base64 error]>>> message={}", e.getMessage(), e);
        }
    }

    /**
     * 随机取色
     */
    private static Color getRandomColor() {
        Random ran = new Random();
        return new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
    }

    /**
     * 计算字符串宽度。
     *
     * @param text 字符串
     * @return 字符串宽度
     */
    private static int calculateWidth(String text) {
        if (StringUtils.isBlank(text)) {
            return 0;
        }

        return 50 * text.length();
    }

    /**
     * 计算字符串高度。
     *
     * @param text 字符串
     * @return 字符串高度
     */
    private static int calculateHeight(String text) {
        if (StringUtils.isBlank(text)) {
            return 0;
        }

        return 15 * text.length();
    }
}
