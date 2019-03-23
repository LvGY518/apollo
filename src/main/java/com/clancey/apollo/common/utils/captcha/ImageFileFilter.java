package com.clancey.apollo.common.utils.captcha;

import com.clancey.apollo.common.utils.FileUtil;

import java.io.File;
import java.io.FileFilter;

/**
 * @author ChenShuai
 * @date 2018/7/31 18:07
 */
public class ImageFileFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        if (!pathname.isFile()) {
            return false;
        }

        return FileUtil.isExtension(pathname.getName(), "jpg", "jpeg", "png", "bmp");
    }
}
