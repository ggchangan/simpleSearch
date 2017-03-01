package crawler.basic.queue.local;

import crawler.basic.support.DownLoadFile;
import crawler.basic.parser.HtmlParserTool;
import crawler.basic.parser.LinkFilter;

import java.util.Set;

public class MyCrawler {
    private LinkQueue localQueue = new LinkQueue();
    //main �������
    public static void main(String[] args) {
        MyCrawler crawler = new MyCrawler();
        //crawler.crawling(new String[] {"http://www.twt.edu.cn"});
        crawler.crawling(new String[] {"http://www.twt.edu.cn", "http://lietu.com/"});
    }

    /**
     * ʹ�����ӳ�ʼ�� URL ����
     *
     * @param seeds ����URL
     * @return
     */
    private void initCrawlerWithSeeds(String[] seeds) {
        for (int i = 0; i < seeds.length; i++) {
            localQueue.addUnvisited(seeds[i]);
        }
    }

    /**
     * ץȡ����
     *
     * @param seeds
     * @return
     */
    public void crawling(String[] seeds) {   //�������������ȡ��http://www.lietu.com��ͷ������
        LinkFilter filter = new LinkFilter() {
            public boolean accept(String url) {
                if (url.startsWith("http://www.lietu.com")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        initCrawlerWithSeeds(seeds);
        while (!localQueue.unvisitedEmpty() && localQueue.getVisitedNumber() <= 1000) {
            String visitedUrl = localQueue.getUnvisited();
            if (visitedUrl == null) {
                continue;
            }
            DownLoadFile downLoader = new DownLoadFile();
            //������ҳ
            downLoader.downloadFile(visitedUrl);
            //�� url ���뵽�ѷ��ʵ� URL ��
            localQueue.addVisited(visitedUrl);
            //��ȡ��������ҳ�е� URL

            Set<String> links = HtmlParserTool.extracLinks(visitedUrl, filter);
            //�µ�δ���ʵ� URL ���
            for (String link : links) {
                localQueue.addUnvisited(link);
            }
        }
    }

}
