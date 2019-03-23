package com.clancey.apollo.common.utils.captcha;

import com.alibaba.fastjson.JSON;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * 倒立验证码
 * -------------------------------
 * <p>
 * 请点击图片中倒立的文字
 * <p>
 * -------------------------------
 *
 * @author ChenShuai
 * @date 2018/8/1 16:51
 */
public class UpsideDownReCaptcha extends ReCaptcha {
    public static Random random = new Random();
    public static Font font;
    private final int wordCount;
    private List<Rectangle2D> checkWords = new ArrayList<>();

    public UpsideDownReCaptcha(Integer width, Integer height, int wordCount) {
        super("upside_down", width, height, "请点击图片中倒立的文字");
        this.wordCount = wordCount;
        if (font == null) {
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResource("/font/STKAITI.TTF").openStream()).deriveFont(24f);
            } catch (FontFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected Map<String, Object> imageBase64() {
        BufferedImage bufferedImage = new BufferedImage(ReCaptchaFactory.DEFAULT_WIDTH, ReCaptchaFactory.DEFAULT_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphic = bufferedImage.createGraphics();
        graphic.setBackground(Color.YELLOW);

        //画文字
        FontRenderContext context = graphic.getFontRenderContext();
        Rectangle2D fondBound = font.getMaxCharBounds(context);
        int fontWidth = (int) fondBound.getWidth();
        int fontHeight = (int) fondBound.getHeight();
        int y = height / 2;

        String[] words = new String[]{"请", "点", "击", "倒", "立", "的", "文", "字"};
        int xBound = (width - fontWidth * words.length) / 2;
        int rotateNum = random.nextInt(words.length / 2 - 3) + 3;

        Set<Integer> rotateIndex = new HashSet<>();
        while (rotateIndex.size() < rotateNum) {
            rotateIndex.add(random.nextInt(words.length));
        }

        for (int i = 0; i < words.length; i++) {
            boolean upsideDown = rotateIndex.contains(i);

            //设置字体旋转
            TextLayout textLayout = new TextLayout(words[i], font, context);
            Rectangle2D fontRect = textLayout.getBounds();
            AffineTransform transform = getRandomTransform(upsideDown, fontRect.getWidth(), fontRect.getHeight());
            Font font = UpsideDownReCaptcha.font.deriveFont(transform);
            textLayout = new TextLayout(words[i], font, context);
            textLayout.draw(graphic, xBound + i * fontWidth, y);

            if (upsideDown) {
                //获取字体边框
                Rectangle2D f = textLayout.getBounds();
                f.setRect(xBound + i * fontWidth, y - fontHeight, fontHeight, fontHeight);
                //graphic.draw(f);
                checkWords.add(f);
            }
        }
        graphic.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Map<String, Object> result = new HashMap<>();
        try {
            ImageIO.write(bufferedImage, "jpg", outputStream);
            result.put("image", ReCaptchaFactory.encoder.encode(outputStream.toByteArray()));
            result.put("imageType", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Boolean checkValid(String checkData) {
        List<PointVo> pointVos = JSON.parseArray(checkData, PointVo.class);
        if (pointVos.size() < checkWords.size()) {
            return false;
        }

        List<Point> points = new ArrayList<>();
        for (PointVo pointVo : pointVos) {
            points.add(new Point(pointVo.getX(), pointVo.getY()));
        }

        outer:
        for (Rectangle2D checkWord : checkWords) {
            for (Point point : points) {
                if (checkWord.contains(point)) {
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }

    private AffineTransform getRandomTransform(boolean upsideDown, double width, double height) {
        AffineTransform transform = new AffineTransform();
        int rotate = upsideDown ? 125 + random.nextInt(100) : -80 + random.nextInt(170);
        transform.rotate(Math.toRadians(rotate), width / 2, -height / 2);
        return transform;
    }
}
