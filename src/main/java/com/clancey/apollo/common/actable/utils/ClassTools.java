package com.clancey.apollo.common.actable.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;


/**
 * 通过包名获取class
 *
 * @author sunchenbin
 * @version 2016年6月23日 下午5:55:18
 */
public class ClassTools{

	/**
	 * 从包package中获取所有的Class
	 *
	 * @param pack 扫描的包
	 * @return 该包下的class
	 */
	public static Set<Class<?>> getClasses(String pack){
		Set<Class<?>> classes = new LinkedHashSet<>();
		Set<String> classNames = getClassNames(pack, true);
		classNames.forEach(clz -> {
			try {
				classes.add(Thread.currentThread().getContextClassLoader().loadClass(clz));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
		return classes;
	}

	public static Set<String> getClassNames(String pack, boolean isFile) {
		Set<String> result = new LinkedHashSet<>();
		String packDir = pack.replace('.', '/');
		String prefix = packDir.substring(0, packDir.contains("**") ?  packDir.indexOf("**") : 0);
		try {
 			ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
			Resource[] resources = resourceLoader.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + packDir);

			for (Resource resource : resources) {
				String url = resource.getURL().toString();
				url = url.substring(url.indexOf(prefix) + prefix.length());
				String className = prefix + url;
				if (isFile) {
					className = className.replace(".class", "");
				}
				if (className.endsWith("/")) {
					className = className.substring(0, className.length() - 1);
				}
				className = className.replace('/', '.');
				result.add(className);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 以文件的形式来获取包下的所有Class
	 *
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	public static void findAndAddClassesInPackageByFile(
			String packageName,
			String packagePath,
			final boolean recursive,
			Set<Class<?>> classes){
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			// log.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter(){

			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file){
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles){
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
			}else{
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0, file.getName().length() - 6);
				try{
					// 添加到集合中去
					// classes.add(Class.forName(packageName + '.' +
					// className));
					// 经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
					classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
				}catch (ClassNotFoundException e){
					// log.error("添加用户自定义视图类错误 找不到此类的.class文件");
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 取出list对象中的某个属性的值作为list返回
	 * @param objList
	 * @param fieldName
	 * @return
	 */
	public static <T, E> List<E> getPropertyValueList(List<T> objList, String fieldName){
		List<E> list = new ArrayList<E>();
		try{
			for (T object : objList){
				Field field = object.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);
				list.add((E) field.get(object));
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return list;
	}
}
