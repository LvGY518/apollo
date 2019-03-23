package com.clancey.apollo.common.utils.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import com.clancey.apollo.common.utils.pojo.ThymeLeafTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.pdf.BaseFont;
import com.clancey.apollo.common.utils.pojo.ThymeLeafTemplate;

/**
 * PDF工具类
 *
 * @author bin
 *
 */
public class PDFUtil {

    /**
     * 合并多个html模板
     *
     * @param templates
     *            (模板对象集合)
     * @param output
     *            (输出地址)
     */
    public static void joinTemplate(List<ThymeLeafTemplate> templates, String output) {

        if (templates != null) {
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(new File(output), "UTF-8");
                for (ThymeLeafTemplate temp : templates) {

                    //创建模板解析器
                    FileTemplateResolver templateResolver = new FileTemplateResolver();
                    templateResolver.setPrefix(temp.getPath());
                    templateResolver.setSuffix(".html");
                    templateResolver.setCharacterEncoding("UTF-8");
                    templateResolver.setTemplateMode(TemplateMode.HTML);
                    templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
                    templateResolver.setCacheable(true);

                    //创建模板引擎并初始化解析器
                    TemplateEngine engine = new TemplateEngine();
                    engine.setTemplateResolver(templateResolver);
                    engine.isInitialized();

                    //获取上下文
                    Context ctx = new Context();
                    ctx.setVariables(temp.getData());
                    engine.process(temp.getName(),  ctx, pw);
                }
                pw.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                pw.close();
            }
        }
    }
    /**
     * html转换pdf
     *
     * @param inputFile
     *            (html文件)
     * @param outputFile
     *            (生成pdf文件)
     * @param fonts
     *            (需要设置的字体)
     * @param resourcesUrl
     *            (静态资源路径)
     * @return
     */
    public static boolean convertHtmlToPdf(String inputFile, String outputFile, String[] fonts, String resourcesUrl) {

        try {
            OutputStream os = new FileOutputStream(outputFile);
            ITextRenderer renderer = new ITextRenderer();
            ITextFontResolver fontResolver = renderer.getFontResolver();
            if (fonts != null) {
                for (String font : fonts) {
                    fontResolver.addFont(font, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                }
            }
            String url = new File(inputFile).toURI().toURL().toString();
            renderer.setDocument(url);
            // 解决图片的相对路径问题,图片路径必须以file开头
            // renderer.getSharedContext().setBaseURL(resourcesUrl);
            renderer.layout();
            renderer.createPDF(os);
            os.flush();
            os.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 转pdf测试
     * @param inputFile
     * @param outputFile
     * @param fontPath
     * @return
     * @throws Exception
     */
    public static boolean convertHtmlToPdfText(String inputFile, String outputFile, String fontPath) throws Exception {

        OutputStream os = new FileOutputStream(outputFile);

        ITextRenderer renderer = new ITextRenderer();
        String url = new File(inputFile).toURI().toURL().toString();
        renderer.setDocument(url);
        // 解决中文支持问题
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont(fontPath + "/simsun.ttf",
                BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        fontResolver.addFont(fontPath + "/simsun.ttc",
                BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        // 解决图片的相对路径问题
        //renderer.getSharedContext().setBaseURL("file:/C:");
        renderer.layout();
        renderer.createPDF(os);
        os.flush();
        os.close();
        return true;
    }

    /**
     * @param inputFile
     * @param outputFile
     * @param fontPath
     * @return
     * @throws Exception
     */
    public static boolean convertHtmlToPdfClientCompact(String inputFile, String outputFile, String fontPath) throws Exception {

        OutputStream os = new FileOutputStream(outputFile);
        ITextRenderer renderer = new ITextRenderer();
        String url = new File(inputFile).toURI().toURL().toString();
        System.out.println(url);
        renderer.setDocument(url);
        // 解决中文支持问题
        ITextFontResolver fontResolver = renderer.getFontResolver();

        fontResolver.addFont(fontPath + "/times.ttf",
                BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        fontResolver.addFont(fontPath + "/msyh.ttf",
                BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        // 解决图片的相对路径问题
        //renderer.getSharedContext().setBaseURL("file:/C:");
        renderer.layout();
        renderer.createPDF(os);
        os.flush();
        os.close();
        return true;
    }

}
