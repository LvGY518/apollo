package com.clancey.apollo.common.utils.pdf;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.clancey.apollo.common.utils.pojo.ThymeLeafTemplate;
import com.clancey.apollo.common.utils.DirectoryUtil;

import com.clancey.apollo.common.utils.FileUtil;
import com.clancey.apollo.common.utils.pojo.ThymeLeafTemplate;

/**
 * PDF工具类
 *
 * @author bin
 *
 */
public class ExportPdfUtil {

    public static String executeBuildPDF(ThymeLeafTemplate thymeLeafTemplate) {

        try {

            String uploadDirectory = DirectoryUtil.getRootPathWithDate() +thymeLeafTemplate.getFolderName()+"\\";
            File uploadFile = new File(uploadDirectory);
            if (!uploadFile.exists()) {
                uploadFile.mkdirs();
            }
            String pdfName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            // 最终生成的PDF路径
            String pdfFile = uploadDirectory + pdfName + ".pdf";
            String htmlFile = initHtml(uploadDirectory, thymeLeafTemplate);
            // html转换成PDF
            PDFUtil.convertHtmlToPdf(htmlFile, pdfFile, new String[] { thymeLeafTemplate.getPath() + "/font/simsun.ttc" }, thymeLeafTemplate.getPath() + "/resource");
            // 转换完成删除html文件
//            FileUtil.deleteFile(htmlFile);
            return pdfFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public static String executeBuildHtml(String path, ThymeLeafTemplate thymeLeafTemplate) {

        String htmlStr = "";
        String htmlFile = initHtml(path, thymeLeafTemplate);
        // todo 获取html内的内容并返回
        htmlStr = FileUtil.readFile(htmlFile);
        // 转换完成删除html文件
        FileUtil.deleteFile(htmlFile);
        return htmlStr;
    }

    /**
     * 组装html模板并暂时存放
     *
     * @param orderId
     * @param path
     * @param webUrl
     * @return
     */
    public static String initHtml(String path, ThymeLeafTemplate thymeLeafTemplate) {

        try {

            List<ThymeLeafTemplate> templates = new ArrayList<ThymeLeafTemplate>();
            templates.add(thymeLeafTemplate);
            String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            // 转换的HTML路径
            String htmlFile = path + fileName + ".html";
            // 合并html模板并填充数据
            PDFUtil.joinTemplate(templates, htmlFile);
            return htmlFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
