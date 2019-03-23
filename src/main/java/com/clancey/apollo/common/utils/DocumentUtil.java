package com.clancey.apollo.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.clancey.apollo.common.actable.annotation.Document;
import org.apache.commons.lang3.StringUtils;

import com.clancey.apollo.common.actable.annotation.Document;

import lombok.Getter;
import lombok.Setter;




/**
 * @author ZhengZongYuan
 * @date 上午10:36:58
 * @version 1.0
 * 生成markDown文档工具类
 */
@Document(description="测试这是一个文档")
@Getter
@Setter
public class DocumentUtil {

	public static  final  String[] types  = {"int","double","long","bigDecimal","date"};

	@Document(jsonKey="del",type="String",description="hello")
	private String del;

	@Document(jsonKey="del2",type="Date",description="hi",example="12312")
	private Date del2;

	public static void generateDocument(Class<? extends Object> clazz,String filePath,List<String> specialRemarks,List<String> remarks,String Author) throws IOException{
		StringBuffer sb = new StringBuffer();
		String createDateUrl = FileUtil.getCreateDateUrl(filePath);
		StringBuffer stringBuffer = new StringBuffer(createDateUrl);
		StringBuffer append = stringBuffer.append(clazz.getSimpleName());
		File file = new File(append.toString()+".txt");
		Document document = clazz.getAnnotation(Document.class);
		//解析类上描述
		if(document!=null){
			sb.append("**");
			sb.append("**");
			sb.append(document.description());
			sb.append(System.lineSeparator());
			sb.append(System.lineSeparator());
		}
		writeCompleteString(file,sb.toString(),false);
		//解析字段上描述
		Field[] fields = clazz.getDeclaredFields();
		StringBuffer buffer = new StringBuffer();
		//添加表头
		addTableName(fields,buffer);
		writeFileWithNIO(file,buffer.toString(),true);
		StringBuffer sf = new StringBuffer();
		//生成表格
		addTableContent(fields,sf);
		writeFileWithNIO(file,sf.toString(),true);
		//生成json
		StringBuffer jsonBuffer = new StringBuffer();
		String toJson = generateJSON(fields,jsonBuffer,true);
		writeFileWithNIO(file,toJson,true);
		//生成特殊说明
		if(specialRemarks!=null){
			StringBuffer special = new StringBuffer();
			generateSpecial(specialRemarks,special);
			writeFileWithNIO(file,special.toString(),true);
		}
		//生成备注
		if(remarks!=null&&remarks.size()!=0){
			StringBuffer remark = new StringBuffer();
			generateReamrk(remarks,remark);
			writeFileWithNIO(file,remark.toString(),true);
		}
		//生成作者
		String generateAuthor = generateAuthor(Author);
		writeFileWithNIO(file,generateAuthor,true);
	}

	private static String generateAuthor(String author) {
		if(StringUtils.isNoneBlank(author)){
			StringBuffer sb = new StringBuffer();
			sb.append(System.lineSeparator());
			sb.append("**作者**");
			sb.append(System.lineSeparator());
			sb.append("- ").append(author);
			return sb.toString();
		}
		return "";
	}

	/**
	 * @param remarks
	 * @param remark void
	 * @author ZhengZongYuan
	 * 生成备注
	 */
	private static void generateReamrk(List<String> remarks, StringBuffer remark) {
		remark.append(System.lineSeparator());
		remark.append("**备注**");
		remark.append(System.lineSeparator());
		remarks.forEach(remarkRemark->{
			//生成特殊说明
			remark.append("#####&diams;   ").append(remarkRemark);
			remark.append(System.lineSeparator());
		});
	}

	/**
	 * @param specialRemarks
	 * @param special void
	 * @author ZhengZongYuan
	 * 生成特殊说明
	 */
	private static void generateSpecial(List<String> specialRemarks, StringBuffer special) {
		special.append(System.lineSeparator());
		special.append("**特殊说明**");
		special.append(System.lineSeparator());
		specialRemarks.forEach(specialRemark->{
			//生成特殊说明
			special.append("#####&diams;   ").append(specialRemark);
			special.append(System.lineSeparator());
		});
	}

	/**
	 * @param fields
	 * @param jsonBuffer
	 * @param b void
	 * @author ZhengZongYuan
	 * 生成json
	 *
	 */
	private static String generateJSON(Field[] fields, StringBuffer jsonBuffer, boolean b) {
		//生成json
		jsonBuffer.append(System.lineSeparator());
		jsonBuffer.append(System.lineSeparator());
		jsonBuffer.append("**json请求示例**");
		jsonBuffer.append(System.lineSeparator());
		jsonBuffer.append(System.lineSeparator());
		jsonBuffer.append("```JSON");
		jsonBuffer.append(System.lineSeparator());
		jsonBuffer.append("\"{");
		jsonBuffer.append(System.lineSeparator());
		for (Field field : fields) {
			Document annotation = field.getAnnotation(Document.class);
			if(annotation!=null){
				String type = annotation.type();
				String jsonKey = annotation.jsonKey();
				if("default".equals(jsonKey)){
					String name = field.getName();
					jsonBuffer.append("\t").append("\"").append(name).append("\"");
				}else{
					jsonBuffer.append("\t").append("\"").append(jsonKey).append("\"");
				}
				if(containsType(type)){
				    jsonBuffer.append(":").append(annotation.example()).append(",");
				    jsonBuffer.append(System.lineSeparator());
				}else{
					jsonBuffer.append(":").append("\"").append(annotation.example()).append("\"").append(",");
					jsonBuffer.append(System.lineSeparator());
				}
			}
		}
		String substring = jsonBuffer.substring(0, jsonBuffer.lastIndexOf(","));
		System.out.println(substring);
		StringBuffer stringBuffer = new StringBuffer(substring);
		stringBuffer.append(System.lineSeparator());
		stringBuffer.append("}\"");
		stringBuffer.append(System.lineSeparator());
		stringBuffer.append("```");
		return  stringBuffer.toString();
	}

	/**
	 * 判断是否包含不添加双引号的类型
	 * @param type
	 * @return
	 */
	private static boolean containsType(String type) {
		for (int i = 0; i <types.length ; i++) {
			if(type.equalsIgnoreCase(types[i])){
				return true;
			}
		}
		return false;
	}

	/**
	 * @param fields
	 * @param sf void
	 * @author ZhengZongYuan
	 * 添加表正文内容
	 */
	private static void addTableContent(Field[] fields, StringBuffer sf) {
		for (Field field : fields) {
			field.setAccessible(true);
			Document annotation = field.getAnnotation(Document.class);
			if(annotation!=null){
				sf.append(System.lineSeparator());
				String description = annotation.description();
				boolean required = annotation.required();
				String jsonKey = annotation.jsonKey();
				String type = annotation.type();
				if("default".equals(jsonKey)){
					String name = field.getName();
					sf.append("|").append(name);
				}else{
					sf.append("|").append(jsonKey);
				}
				sf.append("|").append(type);
				sf.append("|").append(description);
				if(required){
					sf.append("|").append("必填");
				}else{
					sf.append("|").append("非必填");
				}
				sf.append("|");
			}
		}
	}

	@SuppressWarnings("unused")
	private static void writeFileWithIO(File file, String string, boolean b) {
		int sbLength = string.length();
		int j = sbLength/1024;
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < j+1; i++) {
				if(i==j){
					bufferedWriter.write(string.substring(i*1024, i*1024+sbLength%1024));
				}else{
					bufferedWriter.write(string.substring(i*1024, (i+1)*1024));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unused")
	private  void writeCompleteStringWithIO(File file, String string, boolean b) {
		PrintWriter printwriter = null;
		try {
			printwriter = new PrintWriter(file);
			printwriter.write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	private  static void writeCompleteString(File file, String string, boolean b) {
		FileOutputStream fileOutputStream = null;
		FileChannel channel = null;
		try {
			fileOutputStream = new FileOutputStream(file,b);
			//获取NIO
			channel = fileOutputStream.getChannel();
			ByteBuffer byteBuffer = null;
			byteBuffer = ByteBuffer.wrap(string.getBytes(Charset
					.forName("UTF-8")));
			channel.write(byteBuffer);
			channel.force(true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param fields
	 * @param sb void
	 * @author ZhengZongYuan
	 * 添加表头
	 */
	private static void addTableName(Field[] fields, StringBuffer sb) {
		sb.append("|属性").append("|类型").append("|说明").append("|必填|");
		sb.append(System.lineSeparator());
		for (int i = 0; i < 4; i++) {
			sb.append("|:");
		}
		sb.append("|");
	}


	public static void writeFileWithNIO(File file,String sb,boolean append){
		int sbLength = sb.length();
		int j = sbLength/1024;
		FileOutputStream fileOutputStream = null;
		FileChannel channel = null;
		try {
			fileOutputStream = new FileOutputStream(file,append);
			//获取NIO
			channel = fileOutputStream.getChannel();
			//缓冲区
			for (int i = 0; i < j+1; i++) {
				if(i==j){
					ByteBuffer byteBuffer = null;
					byteBuffer = ByteBuffer.wrap(sb.substring(i*1024, i*1024+sbLength%1024).getBytes(Charset
							.forName("UTF-8")));
					channel.write(byteBuffer);
				}else{
					ByteBuffer byteBuffer = null;
					byteBuffer = ByteBuffer.wrap(sb.substring(i*1024, (i+1)*1024).getBytes(Charset
							.forName("UTF-8")));
					channel.write(byteBuffer);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		try {
			ArrayList<String> arrayList = new ArrayList<>();
			arrayList.add("特殊说明1");
			arrayList.add("特殊说明2");
			arrayList.add("特殊说明3");
			generateDocument(DocumentUtil.class, "D:\\document",arrayList,new ArrayList<>(),"郑棕源");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
