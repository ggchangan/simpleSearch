package crawler.basic;

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
import java.nio.file.Path;

public class DownLoadFile {
    public static final String SAVE_PAHT = "data";
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
        String filePath = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK){
                System.err.println("Method failed: " + response.getStatusLine());
                filePath = null;
            }

            HttpEntity entity = response.getEntity();
            byte[] responseBody = EntityUtils.toByteArray(entity);
            String contentType = response.getFirstHeader("Content-Type").getValue();
            File path = new File(SAVE_PAHT);
            if (!path.exists()){
                path.mkdir();
            }
            filePath = new File(SAVE_PAHT, getFileNameByUrl(url, contentType)).getPath();
            saveToLocal(responseBody, filePath);

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

        return filePath;
    }

    public static void main(String[] args) {
        String visitUrl = "http://www.lietu.com/";
        DownLoadFile downLoader=new DownLoadFile();
        downLoader.downloadFile(visitUrl);
    }
}
