package com.clancey.apollo.common.utils;

import com.clancey.apollo.common.utils.file.FileTypeEnum;
import com.clancey.apollo.common.utils.file.FileTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.util.Calendar;

/**
 * 文件工具类
 *
 * @author bin
 */
@Component
public class FileUtil {

    private static final char last2byte = (char) Integer
            .parseInt("00000011", 2);
    private static final char last4byte = (char) Integer
            .parseInt("00001111", 2);
    private static final char last6byte = (char) Integer
            .parseInt("00111111", 2);
    private static final char lead6byte = (char) Integer
            .parseInt("11111100", 2);
    private static final char lead4byte = (char) Integer
            .parseInt("11110000", 2);
    private static final char lead2byte = (char) Integer
            .parseInt("11000000", 2);
    private static final char[] encodeTable = new char[]{'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/'};

    /**
     * 读文件成为字符串
     * 默认编码utf-8
     *
     * @param input
     * @return
     */
    public static String readFile(InputStream input) {
        return readFile(input, "utf-8");
    }

    /**
     * 读文件成为字符串
     *
     * @param input
     * @return
     */
    public static String readFile(InputStream input, String encode) {
        BufferedReader buffer = null;
        String laststr = "";
        try {
            buffer = new BufferedReader(new InputStreamReader(input, encode));
            String tempString = null;
            while ((tempString = buffer.readLine()) != null) {
                laststr += tempString;
            }
            buffer.close();
            return laststr;
        } catch (IOException e) {
            throw new RuntimeException("", e);
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 读取文件内容
     *
     * @param fullFileName
     * @return
     */
    public static String readFile(String fullFileName) {
        File file = new File(fullFileName);
        BufferedReader buffer = null;
        String laststr = "";
        try {
            buffer = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            String tempString = null;
            while ((tempString = buffer.readLine()) != null) {
                laststr += tempString;
            }
            buffer.close();
            return laststr;
        } catch (IOException e) {
            throw new RuntimeException(String.format("读取[%s]文件内容出现异常", file.getName()), e);
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static String readHtmlFile(String file) {
        File pdfFile = new File(file);
        try {
            if (!pdfFile.exists() || pdfFile.isDirectory())
                throw new FileNotFoundException();
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(pdfFile));
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            in.close();
            return out.toString();
        } catch (IOException ie) {
            ie.printStackTrace();
            throw new RuntimeException(String.format("读取[%s]文件内容出现异常", pdfFile.getName()), ie);
        }
    }

    /**
     * 删除文件
     *
     * @param src
     */
    public static void deleteFile(String src) {
        File file = new File(src);
        if (file.exists()) {
            file.delete();
        }
    }

    public static String getImageStr(String imgFile) {
        try {
            return getImageStr(new FileInputStream(imgFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getImageStr(InputStream inputStream) {
        byte[] data = null;
        try {
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder to = new StringBuilder((int) (data.length * 1.34) + 3);
        int num = 0;
        char currentByte = 0;
        for (int i = 0; i < data.length; i++) {
            num = num % 8;
            while (num < 8) {
                switch (num) {
                    case 0:
                        currentByte = (char) (data[i] & lead6byte);
                        currentByte = (char) (currentByte >>> 2);
                        break;
                    case 2:
                        currentByte = (char) (data[i] & last6byte);
                        break;
                    case 4:
                        currentByte = (char) (data[i] & last4byte);
                        currentByte = (char) (currentByte << 2);
                        if ((i + 1) < data.length) {
                            currentByte |= (data[i + 1] & lead2byte) >>> 6;
                        }
                        break;
                    case 6:
                        currentByte = (char) (data[i] & last2byte);
                        currentByte = (char) (currentByte << 4);
                        if ((i + 1) < data.length) {
                            currentByte |= (data[i + 1] & lead4byte) >>> 4;
                        }
                        break;
                    default:
                        break;
                }
                to.append(encodeTable[currentByte]);
                num += 6;
            }
        }
        if (to.length() % 4 != 0) {
            for (int i = 4 - to.length() % 4; i > 0; i--) {
                to.append("=");
            }
        }
        return to.toString();
    }

    public static String getExtension(String fileName) {
        if (fileName == null) {
            return "";
        }

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(dotIndex + 1);
    }

    public static boolean isExtension(String fileName, String... extension) {
        if (fileName == null || extension == null) {
            return false;
        }
        String fileExtension = getExtension(fileName);
        for (String s : extension) {
            if (s != null && s.equals(fileExtension)) {
                return true;
            }
        }
        return false;
    }

    public static void createDir(String zipFileName) {
        String filePath = StringUtils.substringBeforeLast(zipFileName, "/");
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
    }

    public static String getCreateDateUrl(String url) {
        if (url != null) {
            Calendar now = Calendar.getInstance();
            StringBuffer resultUrl = new StringBuffer(url);
            if (!url.endsWith("/")) {
                resultUrl.append("/");
            }
            resultUrl.append(now.get(Calendar.YEAR));
            resultUrl.append("/");
            resultUrl.append((now.get(Calendar.MONTH) + 1));
            resultUrl.append("/");
            resultUrl.append(now.get(Calendar.DAY_OF_MONTH));
            resultUrl.append("/");
            createDir(resultUrl.toString());
            return resultUrl.toString();
        }
        return null;
    }

    /**
     * @param response
     * @param fileName 设置文件的响应格式为utf-8
     */
    public static void intercalateResponse2UTF8(HttpServletResponse response, String fileName) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据文件名获取文件扩展名
     *
     * @param file
     * @return
     */
    public static String getExtensionByFileName(String file) {
        int dotIndex = file.lastIndexOf('.');
        if (dotIndex > -1) {
            return file.substring(dotIndex + 1);
        }
        return "";
    }

    /**
     * 根据魔数获取文件扩展名
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static FileTypeEnum getExtensionByMagicNumber(String file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        int readMaxLen = 16;
        byte[] data = new byte[readMaxLen];
        inputStream.read(data, 0, readMaxLen);
        String hex = new BigInteger(1, data).toString(16);
        int hexLength = hex.length();
        for (FileTypeEnum fileType : FileTypeEnum.values()) {
            String magicNumber = fileType.getValue();
            int magicNumberLength = magicNumber.length();
            if (hexLength >= magicNumberLength && magicNumber.equalsIgnoreCase(hex.substring(0, magicNumberLength))) {
                return fileType;
            }
        }
        return FileTypeEnum.UNKNOWN;
    }
}
