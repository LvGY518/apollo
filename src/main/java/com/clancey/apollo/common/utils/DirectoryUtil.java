package com.clancey.apollo.common.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

/**
 * @author ChenShuai
 * @date 2018/10/26 16:54
 */
public class DirectoryUtil {
    private final static String ROOT;
    private static String rootPathWithDate;
    private static String lastUpdateDay = "none";

    static {
        ROOT = PropertyReadUtil.getValue("setting.properties", "directory.root");
        rootPathWithDate = ROOT;
        if (StringUtil.isNullOrEmpty(ROOT)) {
            throw new IllegalArgumentException("请在setting.properties中配置directory.root的路径");
        }
    }

    /**
     * 获取带日期的目录
     *
     * @return
     */
    public static String getRootPathWithDate() {
        Calendar calendar = Calendar.getInstance();
        String day = calendar.get(Calendar.DATE) + "";
        if (lastUpdateDay.equals(day)) {
            return rootPathWithDate;
        }

        String year = calendar.get(Calendar.YEAR) + "";
        String month = calendar.get(Calendar.MONTH) + 1 + "";

        Path path = Paths.get(ROOT, year, month, day);
        File file = new File(path.toUri());
        if (file.isDirectory() || file.mkdirs()) {
            rootPathWithDate = path.toString();
            lastUpdateDay = day;
        }
        return rootPathWithDate;
    }

    /**
     * 获取带日期或不带日期的目录
     *
     * @return
     */
    public static String getRootPathWithoutDate() {
        return ROOT;
    }

    public static void main(String[] args) {
        System.out.println(DirectoryUtil.getRootPathWithDate());
    }
}
