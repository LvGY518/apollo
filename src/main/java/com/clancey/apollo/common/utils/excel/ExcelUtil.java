package com.clancey.apollo.common.utils.excel;

import com.clancey.apollo.common.utils.DirectoryUtil;
import com.clancey.apollo.common.utils.pojo.ThymeLeafTemplate;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelUtil {


    /**
     * 模板数据填充
     *
     * @param thymeLeafTemplate
     * @param code              编码格式
     *                          (模板对象集合)
     */
    public static String executeBuildCSV(ThymeLeafTemplate thymeLeafTemplate, String code) {
        String csvFile = "";
        if (thymeLeafTemplate != null) {
            PrintWriter pw = null;
            try {
                String output = DirectoryUtil.getRootPathWithDate() + thymeLeafTemplate.getFolderName() + "/";
                File uploadFile = new File(output);
                if (!uploadFile.exists()) {
                    uploadFile.mkdirs();
                }
                String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                csvFile = output + fileName + ".csv";
                pw = new PrintWriter(new File(csvFile), code);
                //创建模板解析器
                FileTemplateResolver templateResolver = new FileTemplateResolver();
                templateResolver.setPrefix(thymeLeafTemplate.getPath());
                templateResolver.setSuffix(".csv");
                templateResolver.setCharacterEncoding(code);
                templateResolver.setTemplateMode(TemplateMode.TEXT);
                templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
                templateResolver.setCacheable(true);

                //创建模板引擎并初始化解析器
                TemplateEngine engine = new TemplateEngine();
                engine.setTemplateResolver(templateResolver);
                engine.isInitialized();
                //获取上下文
                Context ctx = new Context();
                ctx.setVariables(thymeLeafTemplate.getData());
                engine.process(thymeLeafTemplate.getName(), ctx, pw);
                pw.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                pw.close();
            }
        }
        return csvFile;
    }

    /**
     * 读取CSV
     *
     * @param path
     * @param charset
     * @param csvFormat
     * @return
     */
    public static List<List<String>> readCSV(String path, String charset, CSVFormat csvFormat) {
        try {
            CSVParser parser = CSVParser.parse(new File(path), Charset.forName(charset), csvFormat);
            List<List<String>> result = new ArrayList<>();
            parser.getRecords().forEach(row -> {
                List<String> tempRow = new ArrayList<>();
                row.forEach(tempRow::add);
                result.add(tempRow);
            });
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
