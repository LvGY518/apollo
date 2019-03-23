package com.clancey.apollo.common.utils.ocr;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.clancey.apollo.common.RedisService;
import com.clancey.apollo.common.utils.ApplicationContextUtil;
import com.clancey.apollo.common.utils.FileUtil;
import com.clancey.apollo.common.utils.HttpUtil;

public class CustomTemplateScan {

    public static void main(String[] args) throws Exception {
//        String imgUrl = "C:\\Users\\16692\\Desktop\\daobantu\\1526369754604AI一撕得箱型.jpg";
//        BufferedImage image = ImageIO.read(new File(imgUrl));
//        System.out.println(knifeLayoutScan(imgUrl,true).toJSONString());
//        Map<String, String> params = new HashMap<>();
//        String url = "https://gw.sandbox.gopay.com/api";
//        System.out.println(HttpUtil.httpsRequest(url, "GET", null));

        Map<String, Pattern> keys = new HashMap<>();
        Pattern number = Pattern.compile("([0-9. ]+)");
        Pattern word = Pattern.compile("([a-zA-Z0-9]+)");
        Pattern date = Pattern.compile("([^:][0-9\\-:]+)");
        keys.put("date", date);
        keys.put("time", date);
        keys.put("unit", word);
        keys.put("F01", number);
        keys.put("T01", number);
        keys.put("force_max", number);
        keys.put("force_min", number);
        keys.put("force_middle", number);
        keys.put("shift_max", number);
        keys.put("shift_min", number);
        keys.put("shift_middle", number);
        keys.put("constant_load", number);

        String imgUrl = "D:\\Library\\Desktop\\微信图片_20181225212908.jpg";
        try (InputStream file = new FileInputStream(imgUrl)) {
            JSONObject jsonObject = null;
            InputStream inputStream = file;
            //大于3MB，进行压缩
            if (file.available() > 1024 * 3) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Thumbnails.of(file).scale(1f).outputQuality(0.7f).toOutputStream(outputStream);
                inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                jsonObject = CustomTemplateScan.templateScan(inputStream);
                outputStream.close();
                inputStream.close();
            } else {
                jsonObject = CustomTemplateScan.templateScan(inputStream);
            }
            if (jsonObject != null) {
                final JSONObject finalJsonObject = jsonObject;
                keys.forEach((k, v)->{
                    String value = finalJsonObject.getString(k);
                    value = value.replaceAll(" ", "");
                    Matcher matcher = keys.get(k).matcher(value);
                    if (matcher.find()) {
                        System.out.println(k + "=" + matcher.group(1));
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static JSONObject knifeLayoutScan(String imgUrl,Boolean tf) throws IOException{

        //切图
        BufferedImage image = ImageIO.read(new File(imgUrl));
        int width= image.getWidth();
        String newImgUrl = new StringBuffer(imgUrl).insert(imgUrl.lastIndexOf("."), new Date().getTime()).toString();
        cut((width/2-4096/2<0)?0:(tf?0:(width/2-4096/2)),0,4096,width<3500?width:3500,imgUrl,newImgUrl);
        //识别
        JSONObject jsonObject = templateScan(newImgUrl);
        //删除相关文件
        new File(newImgUrl).delete();
        return jsonObject;
//        return new JSONObject();
    }

    public static JSONObject templateScan(String imgUrl){
       try {
           InputStream inputStream = new FileInputStream(imgUrl);
           JSONObject jsonObject = templateScan(inputStream);
           inputStream.close();
           return jsonObject;
       } catch (IOException e) {
           e.printStackTrace();
       }
        return null;
    }

    public static JSONObject templateScan(InputStream inputStream) {
        String access_token = getAccessToken();
        String url = "https://aip.baidubce.com/rest/2.0/solution/v1/iocr/recognise?access_token="+access_token;
        Map<String, String> params = new HashMap<>();
        String img = FileUtil.getImageStr(inputStream);
        params.put("image", img);
        params.put("templateSign", BaiduAuthService.TEMPLATE_SIGN);
        JSONObject jsonObject = JSONObject.parseObject(HttpUtil.sendPost(url, params, "utf-8")).getJSONObject("data");
        JSONObject dateJsonObject = new JSONObject();
        if(jsonObject.getBooleanValue("isStructured")){
            for (Object object : jsonObject.getJSONArray("ret")) {
                JSONObject jsonObject2 = JSONObject.parseObject(object.toString());
                dateJsonObject.put(jsonObject2.get("word_name").toString(), jsonObject2.getString("word"));
                //                System.out.println(jsonObject2.get("word_name")+":"+jsonObject2.getString("word"));
            }
            dateJsonObject.put("isStructured", true);
        }else{
            dateJsonObject.put("isStructured", false);
        }
        return dateJsonObject;
    }

    private static String getAccessToken() {
    	RedisService redisService = ApplicationContextUtil.getBean(RedisService.class);
        String access_token = null;
        Object redis_access_token = redisService.get("baiduOcr");
        JSONObject redisJson = new JSONObject();
        if(redis_access_token!=null&&StringUtils.isNotEmpty(redis_access_token.toString())){
            redisJson = JSONObject.parseObject(redis_access_token.toString());
            int poorDay = (int)((new Date().getTime()-redisJson.getDate("baidu_ocr_access_token_date").getTime())/ (1000*3600*24));
            if (poorDay<25) {
                return redisJson.getString("baidu_ocr_access_token");
            }
        }
        access_token = BaiduAuthService.getAuth();
        redisJson.put("baidu_ocr_access_token", access_token);
        redisJson.put("baidu_ocr_access_token_date", new Date());
        redisService.set("baiduOcr",  redisJson);
        return access_token;
    }

    private static String getAccessToken2() {
        return BaiduAuthService.getAuth();
    }


    /**
     * 图片裁切
     * @param x1 选择区域左上角的x坐标
     * @param y1 选择区域左上角的y坐标
     * @param width 选择区域的宽度
     * @param height 选择区域的高度
     * @param sourcePath 源图片路径
     * @param descpath 裁切后图片的保存路径
     */
    public static void cut(int x1, int y1, int width, int height,
            String sourcePath, String descpath) {

        FileInputStream is = null;
        ImageInputStream iis = null;
        try {
            is = new FileInputStream(sourcePath);
            String fileSuffix = sourcePath.substring(sourcePath
                    .lastIndexOf(".") + 1);
            Iterator<ImageReader> it = ImageIO
                    .getImageReadersByFormatName(fileSuffix);
            ImageReader reader = it.next();
            iis = ImageIO.createImageInputStream(is);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x1, y1, width, height);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            File file = new File(descpath);
            if(file.exists()){
                file.createNewFile();
            }
            ImageIO.write(bi, fileSuffix, file);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                is = null;
            }
            if (iis != null) {
                try {
                    iis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                iis = null;
            }
        }
    }
}
