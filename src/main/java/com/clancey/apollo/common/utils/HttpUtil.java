package com.clancey.apollo.common.utils;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import com.clancey.apollo.common.exception.HttpUtilException;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.clancey.apollo.common.exception.HttpUtilException;





public class HttpUtil {


    /**
     * 使用Get方式获取数据
     *
     * @param url
     *            URL包括参数，http://HOST/XX?XX=XX&XXX=XXX
     * @param charset
     * @return
     */
    public static String sendGet(String url, String charset) {

        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * POST请求，字符串形式数据
     *
     * @param url
     *            请求地址
     * @param param
     *            请求数据
     * @param charset
     *            编码方式
     * @throws Exception
     */
    public static String sendPostUrl(String url, String param, String charset) {

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * POST请求，Map形式数据
     *
     * @param url
     *            请求地址
     * @param param
     *            请求数据
     * @param charset
     *            编码方式
     */
    public static String sendPost(String url, Map<String, String> param, String charset) {

        StringBuffer buffer = new StringBuffer();
        if (param != null && !param.isEmpty()) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                try {
                    buffer.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8")).append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        buffer.deleteCharAt(buffer.length() - 1);

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(buffer);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * POST请求，Json形式数据
     *
     * @param url
     *            请求地址
     * @param param
     *            请求数据
     * @param charset
     *            编码方式
     */
    public static String sendPostJson(String url, String json, String charset) {

//        String encoderJson = null;
//        try {
//            encoderJson = URLEncoder.encode(json, "utf-8");
//        } catch (UnsupportedEncodingException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(json);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static JSONObject httpGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().
                setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpGet.setConfig(requestConfig);
        try {
            response = httpClient.execute(httpGet, new BasicHttpContext());
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
                        + ", url=" + url);
                return null;
            }

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                return JSON.parseObject(resultStr);
            }
        } catch (IOException e) {
            System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static JSONObject httpPost(String url, Object data) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().
                setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type", "application/json");

        try {
            StringEntity requestEntity = new StringEntity(JSON.toJSONString(data), "utf-8");
            httpPost.setEntity(requestEntity);
            response = httpClient.execute(httpPost, new BasicHttpContext());
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
                        + ", url=" + url);
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                return JSON.parseObject(resultStr);
            }
        } catch (IOException e) {
            System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }





    public static JSONObject uploadMedia(String url, File file) throws HttpUtilException {

        HttpPost httpPost = new HttpPost(url);

        CloseableHttpResponse response = null;

        CloseableHttpClient httpClient = HttpClients.createDefault();

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();

        httpPost.setConfig(requestConfig);



        HttpEntity requestEntity = MultipartEntityBuilder.create().addPart("media",

                new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, file.getName())).build();

        httpPost.setEntity(requestEntity);



        try {

            response = httpClient.execute(httpPost, new BasicHttpContext());



            if (response.getStatusLine().getStatusCode() != 200) {



                System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()

                                   + ", url=" + url);

                return null;

            }

            HttpEntity entity = response.getEntity();

            if (entity != null) {

                String resultStr = EntityUtils.toString(entity, "utf-8");



                JSONObject result = JSON.parseObject(resultStr);

                if (result.getInteger("errcode") == 0) {

                    // 成功

                    result.remove("errcode");

                    result.remove("errmsg");

                    return result;

                } else {

                    System.out.println("request url=" + url + ",return value=");

                    System.out.println(resultStr);

                    int errCode = result.getInteger("errcode");

                    String errMsg = result.getString("errmsg");

                    throw new HttpUtilException(errCode, errMsg);

                }

            }

        } catch (IOException e) {

            System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());

            e.printStackTrace();

        } finally {

            if (response != null) try {

                response.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }



        return null;

    }





    public static JSONObject downloadMedia(String url, String fileDir) throws HttpUtilException {

        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = null;

        CloseableHttpClient httpClient = HttpClients.createDefault();

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();

        httpGet.setConfig(requestConfig);



        try {

            HttpContext localContext = new BasicHttpContext();



            response = httpClient.execute(httpGet, localContext);



            RedirectLocations locations = (RedirectLocations) localContext.getAttribute(HttpClientContext.REDIRECT_LOCATIONS);

            if (locations != null) {

                URI downloadUrl = locations.getAll().get(0);

                String filename = downloadUrl.toURL().getFile();

                System.out.println("downloadUrl=" + downloadUrl);

                File downloadFile = new File(fileDir + File.separator + filename);

                FileUtils.writeByteArrayToFile(downloadFile, EntityUtils.toByteArray(response.getEntity()));

                JSONObject obj = new JSONObject();

                obj.put("downloadFilePath", downloadFile.getAbsolutePath());

                obj.put("httpcode", response.getStatusLine().getStatusCode());







                return obj;

            } else {

                if (response.getStatusLine().getStatusCode() != 200) {



                    System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()

                                       + ", url=" + url);

                    return null;

                }

                HttpEntity entity = response.getEntity();

                if (entity != null) {

                    String resultStr = EntityUtils.toString(entity, "utf-8");



                    JSONObject result = JSON.parseObject(resultStr);

                    if (result.getInteger("errcode") == 0) {

                        // 成功

                        result.remove("errcode");

                        result.remove("errmsg");

                        return result;

                    } else {

                        System.out.println("request url=" + url + ",return value=");

                        System.out.println(resultStr);

                        int errCode = result.getInteger("errcode");

                        String errMsg = result.getString("errmsg");

                        throw new HttpUtilException(errCode, errMsg);

                    }

                }

            }

        } catch (IOException e) {

            System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());

            e.printStackTrace();

        } finally {

            if (response != null) try {

                response.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }



        return null;

    }
}
