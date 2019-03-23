package com.clancey.apollo.common.utils.message.mail;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import com.clancey.apollo.common.utils.ApplicationContextUtil;
import com.clancey.apollo.common.utils.PropertyReadUtil;
import com.clancey.apollo.common.utils.pojo.ThymeLeafTemplate;

@Component
public class SendMailUtil{

    private static JavaMailSender mailSender =ApplicationContextUtil.getBean(JavaMailSender.class); //自动注入的Bean
    private static String Sender = PropertyReadUtil.getPropertyByKey("spring.mail.username"); //读取配置文件中的参数
    /**
     * 发送文字邮件
     * @param tos
     * @param title
     * @param content
     * @throws Exception
     */
    public static void sendSimpleMail(String[] tos, String title, String content) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Sender);
        message.setTo(tos);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }
    /**
     * 发送Html邮件
     * @param tos
     * @param title
     * @param content html文本
     * @throws Exception
     */
    public static void sendHtmlMail(String[] tos, String title, String content) {
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(Sender);
            helper.setTo(tos);
            helper.setSubject(title);

            helper.setText(content, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailSender.send(message);
    }
    /**
     * 发送邮件带附件
     * @param tos
     * @param title
     * @param content
     * @param files
     */
    public static void sendAttachmentsMail(String[] tos, String title, String content ,String[] files) {
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(Sender);
            helper.setTo(tos);
            helper.setSubject(title);
            helper.setText(content,true);

			if (files != null && files.length != 0) {
				for (int i = 0; i < files.length; i++) {
					File file = new File(files[i]);
					helper.addAttachment(file.getName(), file);
				}
			}
        } catch (Exception e){
            e.printStackTrace();
        }
        mailSender.send(message);
    }
    /**
     * 发送带静态资源的邮件
     * @param tos
     * @param title
     * @param content
     * @param files
     * content示例：<html><body>带静态资源的邮件内容 图片:<img src='cid:picture' /></body></html>
     * files中key要和cid保持一致 例：{picture:"C:test.jpg"}
     */
    public static void sendInlineMail(String[] tos, String title, String content ,Map<String, String> files) {
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(Sender);
            helper.setTo(tos);
            helper.setSubject(title);
            //第二个参数指定发送的是HTML格式,同时cid:是固定的写法
            helper.setText(content, true);
			if (files != null ) {
				files.forEach((key,value)->{
					File file = new File(value);
					try {
						helper.addInline(key,file);
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				});
			}

        } catch (Exception e){
            e.printStackTrace();
        }
        mailSender.send(message);
    }
    /**
     * 发送模板邮件
     * @param tos
     * @param title
     * @param content
     * @param files
     */
    public static void sendTemplateMail(String[] tos, String title, String content ,ThymeLeafTemplate thymeLeafTemplate) {
    	MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(Sender);
            helper.setTo(tos);
            helper.setSubject(title);

            StringWriter SW = new StringWriter();
            //创建模板解析器
            FileTemplateResolver templateResolver = new FileTemplateResolver();
            templateResolver.setPrefix(thymeLeafTemplate.getPath());
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
            ctx.setVariables(thymeLeafTemplate.getData());
            engine.process(thymeLeafTemplate.getName(),  ctx, SW);

            helper.setText(SW.toString(), true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        mailSender.send(message);

    }

}
