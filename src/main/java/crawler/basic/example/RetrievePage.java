package crawler.basic.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by magneto on 17-1-16.
 */
public class RetrievePage {
    public static String downloadPage(String path) throws IOException {
        //根据传入的路径构造URL
        URL pageURL = new URL(path);
        //创建网络流
        BufferedReader reader = new BufferedReader(new InputStreamReader(pageURL.openStream()));
        String line;
        //读取网页内容
        StringBuilder pageBuffer = new StringBuilder();
        while ((line = reader.readLine())  != null) {
            pageBuffer.append(line);
        }

        return pageBuffer.toString();
    }

    public static void main(String[] args) {
        //抓取lietu首页然后输出
        //String path = "http://www.lietu.com";
        String path = "http://www.baidu.com";
        try {
            System.out.println(RetrievePage.downloadPage(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
