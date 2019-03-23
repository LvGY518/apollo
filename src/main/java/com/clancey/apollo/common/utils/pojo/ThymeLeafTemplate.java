package com.clancey.apollo.common.utils.pojo;

import java.util.Map;

/**
 * 模板合并类
 *
 * @author bin
 *
 */
public class ThymeLeafTemplate {
	/**
	 * 模板路径
	 */
	private String path;
	/**
	 * 模板名称
	 */
	private String name;
	/**
	 * 文件夹名称(生成pdf存放的文件夹)
	 */
	private String folderName;
	/**
	 * 数据集
	 */
	private Map<String, Object> data;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

    public String getFolderName() {

        return folderName;
    }


    public void setFolderName(String folderName) {

        this.folderName = folderName;
    }

}
