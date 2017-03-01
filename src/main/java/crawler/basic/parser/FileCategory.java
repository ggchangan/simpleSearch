package crawler.basic.parser;

/**
 * Created by magneto on 16-5-24.
 * 针对不同类型的文件分别写其解析程序，抽象出解析流程
 */
public enum FileCategory {
    IMAGE, AUDIO, VIDEO, HTML, DOC, PPT, XLS
}
