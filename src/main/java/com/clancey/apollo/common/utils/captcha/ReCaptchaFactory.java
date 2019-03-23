package com.clancey.apollo.common.utils.captcha;

import sun.misc.BASE64Encoder;

import java.util.Random;

/**
 * @author ChenShuai
 * @date 2018/7/31 16:21
 */
public class ReCaptchaFactory {
    public final static String[] reCaptchas = {"flip", "upside_down"};
    public final static String DEFAULT_DIRECTORY = "/captcha";
    public final static Integer DEFAULT_WIDTH = 260;
    public final static Integer DEFAULT_HEIGHT = 116;
    public final static Integer DEFAULT_WORD_COUNT = 7;
    public final static ImageFileFilter filter = new ImageFileFilter();
    public final static BASE64Encoder encoder = new BASE64Encoder();
    public final static Random random = new Random();

    public static ReCaptcha getReCaptcha() {
        Random random = new Random();
        int index = random.nextInt(reCaptchas.length);
        return getReCaptcha(reCaptchas[index]);
    }

    public static ReCaptcha getReCaptcha(Integer width, Integer height) {
        Random random = new Random();
        int index = random.nextInt(reCaptchas.length);
        return getReCaptcha(reCaptchas[index], width, height);
    }


    public static ReCaptcha getReCaptcha(String type) {
        return getReCaptcha(type, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public static ReCaptcha getReCaptcha(String type, Integer width, Integer height) {
        switch (type) {
            case "flip":
                return new FlipReCaptcha(width, height);
            case "upside_down":
                return new UpsideDownReCaptcha(width, height, DEFAULT_WORD_COUNT);
            default:
                throw new RuntimeException("找不到" + type + "类型的验证码");
        }
    }
}
