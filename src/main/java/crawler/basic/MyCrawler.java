package crawler.basic;

import java.util.Set;

public class MyCrawler {
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
        for (int i = 0; i < seeds.length; i++)
            LinkQueue.addUnvisitedUrl(seeds[i]);
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
        //��ʼ�� URL ����
        initCrawlerWithSeeds(seeds);
        //ѭ����������ץȡ�����Ӳ�����ץȡ����ҳ������1000
        while (!LinkQueue.unVisitedUrlsEmpty() && LinkQueue.getVisitedUrlNum() <= 1000) {
            //��ͷURL������
            String visitUrl = (String) LinkQueue.unVisitedUrlDeQueue();
            if (visitUrl == null) {
                continue;
            }
            DownLoadFile downLoader = new DownLoadFile();
            //������ҳ
            downLoader.downloadFile(visitUrl);
            //�� url ���뵽�ѷ��ʵ� URL ��
            LinkQueue.addVisitedUrl(visitUrl);
            //��ȡ��������ҳ�е� URL

            Set<String> links = HtmlParserTool.extracLinks(visitUrl, filter);
            //�µ�δ���ʵ� URL ���
            for (String link : links) {
                LinkQueue.addUnvisitedUrl(link);
            }
        }
    }

}
