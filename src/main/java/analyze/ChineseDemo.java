package analyze;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.awt.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by magneto on 17-1-20.
 */
public class ChineseDemo {
    private static String[] strings = {"道德经",
    "程序员(英文Programmer)是从事程序开发、维护的专业人员。一般将程序员分为程序设计人员和程序编码人员，但两者的界限并不非常清楚，特别是在中国。软件从业人员分为初级程序员、高级程序员、系统分析员和项目经理四大类。"
    };

    private static Analyzer[] analyzers = {
        //new SimpleAnalyzer(),
        //new StandardAnalyzer(),
        //new CJKAnalyzer(),
        new MyIkAnalyzer()
    };

    public static void main(String[] args) throws IOException {
        /*
        for (String string: strings) {
            for (Analyzer analyzer: analyzers) {
                List<String> words = analyze(string, analyzer);
                decorate(words, analyzer.getClass().getSimpleName(), string);
            }
        }
        */

        List<Term> termList = NLPTokenizer.segment(strings[1]);
        System.out.println(termList);
    }

    //lucene6.0 http://lucene.apache.org/core/6_3_0/core/org/apache/lucene/analysis/package-summary.html#package.description
    private static List<String> analyze(String string, Analyzer analyzer){
        List<String> words = new ArrayList<>();

        TokenStream stream = analyzer.tokenStream("contents", new StringReader(string));
        stream.addAttribute(CharTermAttribute.class);

        try {
            stream.reset();
            while(stream.incrementToken()) {
                CharTermAttribute charTermAttribute = stream.getAttribute(CharTermAttribute.class);
                words.add(charTermAttribute.toString());
            }
            stream.end();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return words;
    }

    private static void decorate(List<String> words, String analyzerName, String originStr) {
        StringBuffer buffer = new StringBuffer();
        for (String word: words) {
            buffer.append("[");
            buffer.append(word);
            buffer.append("]");
        }

        String output = buffer.toString();

        Frame f = new Frame();
        f.setTitle( analyzerName + ":" + originStr);
        f.setResizable(false);

        Font font = new Font(null, Font.PLAIN, 36);
        int width = getWidth(f.getFontMetrics(font), output);

        f.setSize((width < 250)? 250: width + 50, 75);
        Label label = new Label(output);
        label.setSize(width, 75);
        label.setAlignment(Label.CENTER);
        label.setFont(font);
        f.add(label);
        f.setVisible(true);
    }

    private static int getWidth(FontMetrics metrics, String s) {
        int size = 0;
        int length = s.length();
        for (int i=0; i<length; i++) {
            size += metrics.charWidth(s.charAt(i));
        }

        return size;
    }
}
