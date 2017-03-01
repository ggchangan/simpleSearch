package crawler.basic.support;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DownLoadFile {
    public static final String SAVE_PATH = "data";
    /**
     * ���� url ����ҳ����������Ҫ�������ҳ���ļ��� ȥ���� url �з��ļ����ַ�
     */
    public String getFileNameByUrl(String url, String contentType) {
        System.out.println("原始URL：" + url);
        //remove http://
        url = url.substring(7);
        String filename;
        //text/html����
        if (contentType.indexOf("html") != -1) {
            filename = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
        }
        else {
            filename = url.replaceAll("[\\?/:*|<>\"]", "_") + "." +
                contentType.substring(contentType.lastIndexOf("/") + 1);
        }

        System.out.println("处理之后文件名：" + filename);
        return filename;
    }

    public String getFilePathByUrl(String url, String contentType) {
        String path = null;
        try {
            URI uri = new URI(url);
            System.out.println("uri scheme:" + uri.getScheme());
            System.out.println("uri host:" + uri.getHost());
            System.out.println("uri path:" + uri.getPath());
            System.out.println("uri port:" + uri.getPort());
            System.out.println("uri query:" + uri.getQuery());

            path = uri.getPath();
            //主页
            if (path.equalsIgnoreCase("/")) {
                path = "/index.html";
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return path;
    }

    /**
     * ������ҳ�ֽ����鵽�����ļ� filePath ΪҪ������ļ�����Ե�ַ
     */
    private void saveToLocal(byte[] data, String filePath) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(filePath)));
            for (int i = 0; i < data.length; i++)
                out.write(data[i]);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ���� url ָ�����ҳ */
    public String downloadFile(String url) {
        System.out.println(url);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = null;
        File savePath = null;
        try {
            response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK){
                System.err.println("Method failed: " + response.getStatusLine());
                return null;
            }

            HttpEntity entity = response.getEntity();
            byte[] responseBody = EntityUtils.toByteArray(entity);
            //TODO Fix 同名文件出现在目录之前，会导致目录不可被创建，而导致文件保存失败！
            /*
            如何区分下面的三个链接？
            URI uri1 = new URI("http://www.lietu.com/train");
            URI uri2 = new URI("http://www.lietu.com/train/");
            URI uri3 = new URI("http://www.lietu.com/train.html");
            http://www.lietu.com/train 是被当做目录还是文件，怎样区分？
             */
            try {
                String uriPath = new URI(url).getPath();
                //主页
                if (uriPath==null || uriPath.isEmpty() || uriPath.endsWith("/")) {
                    uriPath += "index.html";
                } else if(!uriPath.endsWith("/") && !uriPath.substring(uriPath.lastIndexOf("/"), uriPath.length()).contains(".")){
                    uriPath += "/index.html";
                }
                savePath = new File(SAVE_PATH, new File(uriPath).toString());
                if (!savePath.getParentFile().exists()){
                    savePath.getParentFile().mkdirs();
                }

                saveToLocal(responseBody, savePath.getPath());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return savePath!=null?savePath.getPath():null;
    }

    public static void main(String[] args) {
        String visitUrl = "http://www.lietu.com/";
        DownLoadFile downLoader=new DownLoadFile();
        downLoader.downloadFile(visitUrl);
    }
}
