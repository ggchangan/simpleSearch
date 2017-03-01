package analyze.email;

import com.hankcs.hanlp.HanLP;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import javax.mail.MessagingException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by magneto on 17-2-22.
 */
public class Main {
    static FileWriter fileWriter;

    static {
        try {
            fileWriter = new FileWriter("out");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String path = "/home/magneto/email/leak100";
        long sTime = System.currentTimeMillis();
        Main.emailProcess(path);
        long eTime = System.currentTimeMillis();
        System.out.println(eTime-sTime);
        if (fileWriter != null) {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void emailProcess(String path) {
        File f = new File(path);
        if (!f.exists()) {
            System.out.println(f.getAbsolutePath() + " 不存在， 请检查！");
            return;
        }
        // File or dir exists
        if (f.isDirectory()) {
            File[] fs = f.listFiles();
            for (File tf : fs) {
                emailProcess(tf.getAbsolutePath());
            }
        } else if (f.isFile()) {
            /*
            try {
                String mimeType = FileUtil.detectMimeType(f);
                if (!(mimeType.equalsIgnoreCase("message/rfc822") || mimeType
                    .equalsIgnoreCase("text/html") || mimeType
                    .equalsIgnoreCase("application/xhtml+xml"))) {
                    String warnMsg = String.format("Failed! %s is %s, not email message file.",
                        f.getAbsoluteFile().toString(), mimeType);
                    System.out.println(warnMsg);
                    return;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            */
            EmailParser emailParser = new EmailParser(false);
            try {
                Email email = emailParser.parse(path);
                System.out.println(email.getFromTextList());
                fileWriter.write(email.getFromTextList().toString());
                fileWriter.write("\n");
                //System.out.println(email.content);

                Metadata metadata = new Metadata();

                AutoDetectParser parser = new AutoDetectParser();
                BodyContentHandler handler = new BodyContentHandler(-1);
                //ContentHandler handler = new ToXMLContentHandler();
                ParseContext context = new ParseContext();
                context.set(Parser.class, parser);

                try {
                    parser.parse(new ByteArrayInputStream(email.content.getBytes()), handler, metadata, new ParseContext());
                } catch (IOException | SAXException | TikaException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }

                String content = handler.toString();
                System.out.println(content);
                fileWriter.write(content);
                fileWriter.write("\n");

                List<String> sentenceList = HanLP.extractSummary(content, 3);
                System.out.println(sentenceList);
                fileWriter.write(sentenceList.toString());
                fileWriter.write("\n");
                fileWriter.write("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                fileWriter.write("\n");
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
