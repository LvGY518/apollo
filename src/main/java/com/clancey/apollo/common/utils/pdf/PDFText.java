package com.clancey.apollo.common.utils.pdf;

import java.util.HashMap;
import java.util.Map;

import com.clancey.apollo.common.utils.pojo.ThymeLeafTemplate;
import com.clancey.apollo.common.utils.pojo.ThymeLeafTemplate;

public class PDFText {


    public static void main(String[] args) {
//        PDF();
//        htmlToPdf();
    }

    private static void htmlToPdf() {

        try {
            PDFUtil.convertHtmlToPdfText("D:/PDFtest/8aadab975844160e015852945a7503f3.1478854206116.html", "D:/PDFtest/test.pdf", "D:/PDFtest");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //生成pdf
    private static void PDF() {
    		//模板路径 项目中替换为 项目路径
            String realPath = "D:\\PDFtest\\";
            //数据集
            Map<String,Object> infoForPDF = new HashMap<String,Object>();
            infoForPDF.put("name", "fengle");
            //生成pdf所需参数
            ThymeLeafTemplate thymeLeafTemplate = new ThymeLeafTemplate();
            thymeLeafTemplate.setData(infoForPDF);
            //pdf保存的文件夹
            thymeLeafTemplate.setFolderName("fengle");
            //模板名称
            thymeLeafTemplate.setName("test");
            //模板路径+模板所在文件夹
            String basePath = realPath + "templates\\";
            thymeLeafTemplate.setPath(basePath);
            //生成PDF
            ExportPdfUtil.executeBuildPDF(thymeLeafTemplate);

    }
}
