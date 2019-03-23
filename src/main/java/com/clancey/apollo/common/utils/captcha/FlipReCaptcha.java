package com.clancey.apollo.common.utils.captcha;

import com.clancey.apollo.common.utils.FileUtil;
import com.clancey.apollo.common.utils.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 滑动验证码
 * -------------------------------
 * -----                  - - -
 * -----                  - - -
 * <p>
 * -------------------------------
 *
 * @author ChenShuai
 * @date 2018/7/31 15:32
 */
public class FlipReCaptcha extends ReCaptcha {
    public static Random random = new Random();
    public static int xBound = 15;
    public static int yBound = 15;
    public static int rectWidth = 40;
    public static int rectHeight = 40;
    private Rectangle checkBlock = new Rectangle(0, 0, rectWidth, rectHeight);
    private Point checkImage = new Point(0, 0);

    public FlipReCaptcha(Integer width, Integer height) {
        super("flip", width, height, "请将图片滑动到正确的位置");
    }

    /**
     * 返回验证码图片的base64编码
     *
     * @return
     */
    @Override
    protected Map<String, Object> imageBase64() {
        String path = this.getClass().getResource(ReCaptchaFactory.DEFAULT_DIRECTORY).getPath();
        File directory = new File(path);
        File[] files = directory.listFiles(ReCaptchaFactory.filter);
        if (files == null || files.length == 0) {
            return new HashMap<>();
        }

        //随机读取验证码图片
        Map<String, Object> result = new HashMap<>();
        Random random = new Random();
        File file = files[random.nextInt(files.length)];
        String imageFormat = FileUtil.getExtension(file.getName());
        try {
            BufferedImage sourceImage = ImageIO.read(file);
            BufferedImage checkBlockImage;

            //随机初始化滑动图块位置
            randomLocation(checkImage, checkBlock);

            //剪切滑动图块部分的图片
            checkBlockImage = ImageUtil.getImageCut(sourceImage, checkBlock);

            //将剪切的部分加半透明遮罩
            Graphics2D graph = sourceImage.createGraphics();
            int rule = AlphaComposite.SRC_OVER;
            float alpha = 0.5f;
            graph.setComposite(AlphaComposite.getInstance(rule, alpha));
            graph.setPaint(Color.DARK_GRAY);
            graph.fill(checkBlock);
            graph.dispose();

            //将滑块加边框
            graph = checkBlockImage.createGraphics();
            graph.setComposite(AlphaComposite.getInstance(rule, alpha));
            graph.setPaint(Color.DARK_GRAY);
            graph.draw(new Rectangle(0, 0, checkBlock.width - 1, checkBlock.height - 1));
            graph.dispose();

            //将图片转化成流
            ByteArrayOutputStream sourceOutputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream checkBlockOutputStream = new ByteArrayOutputStream();
            ImageIO.write(sourceImage, imageFormat, sourceOutputStream);
            ImageIO.write(checkBlockImage, imageFormat, checkBlockOutputStream);

            result.put("image", ReCaptchaFactory.encoder.encode(sourceOutputStream.toByteArray()));
            result.put("imageCheck", ReCaptchaFactory.encoder.encode(checkBlockOutputStream.toByteArray()));
            result.put("imageType", imageFormat);
            result.put("imageCheckX", checkImage.x);
            result.put("imageCheckY", checkImage.y);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 检查用户输入的验证码是否合法
     *
     * @param checkData
     * @return
     */
    @Override
    public Boolean checkValid(String checkData) {
        int distance = Integer.parseInt(checkData);
        return Math.abs(checkBlock.x - distance - checkImage.x) <= 5;
    }

    /**
     * 随机获取切割的位置
     *
     * @param point
     * @param rectangle
     */
    private void randomLocation(Point point, Rectangle rectangle) {
        int x = width / 2 + random.nextInt(width / 2 - xBound - rectWidth);
        int y = random.nextInt(height - 2 * yBound - rectHeight) + yBound;
        rectangle.setLocation(x, y);
        point.setLocation(xBound, y);
    }

    /**
     * 获取
     *
     * @param rectangle
     * @return
     */
    private BufferedImage slicingImage(Rectangle rectangle) {
        return null;
    }
}
