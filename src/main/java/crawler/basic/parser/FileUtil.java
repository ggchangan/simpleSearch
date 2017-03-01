package crawler.basic.parser;

import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.apache.tika.detect.EncodingDetector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.txt.UniversalEncodingDetector;
import org.apache.tika.sax.BodyContentHandler;
//import org.elasticsearch.common.io.stream.StreamInput;
import org.xml.sax.SAXException;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by magneto on 16-5-12.
 */
public class FileUtil {
    private static Logger log = Logger.getLogger(FileUtil.class);

    public static final String UNRECOGNITION_MIME = "application/octet-stream";

    public static String detectMimeType(byte[] data){
        Tika tika = new Tika();
        String mimeType = tika.detect(data);
        if (mimeType.equalsIgnoreCase("application/octet-stream")){
            log.warn("未探测出文件MIME类型，默认为text/plain");
            mimeType = "text/plain";
        }
        return mimeType;
    }

    public static String detectMimeType(byte[] data, String filename){
        Tika tika = new Tika();
        String mimeType = tika.detect(data, filename);
        if (mimeType.equalsIgnoreCase("application/octet-stream")){
            log.warn("未探测出文件MIME类型，默认为text/plain");
            mimeType = "text/plain";
        }
        return mimeType;
    }

    public static String detectMimeType(File file) throws IOException {
        Tika tika = new Tika();
        String mimeType = tika.detect(file);
        if (mimeType.equalsIgnoreCase("application/octet-stream")){
            log.warn("未探测出文件MIME类型，默认为text/plain");
            mimeType = "text/plain";
        }
        return mimeType;
    }

    public static FileCategory fileCategory(String mimeType){
        assert mimeType!=null && !mimeType.isEmpty() && mimeType.contains("/");

        String category = mimeType.substring(0, mimeType.indexOf("/"));
        return transformCategory(category);
    }

    public static FileCategory transformCategory(String category){
        if (category.equalsIgnoreCase(FileCategory.IMAGE.name())) return FileCategory.IMAGE;
        if (category.equalsIgnoreCase(FileCategory.AUDIO.name())) return FileCategory.AUDIO;
        if (category.equalsIgnoreCase(FileCategory.VIDEO.name())) return FileCategory.VIDEO;

        return FileCategory.DOC;
    }

    public static String fileType(String mimeType){
        assert mimeType!=null && !mimeType.isEmpty() && mimeType.contains("/");

        return mimeType.substring(mimeType.indexOf("/")+1);
    }

    public static String encoding(File file) throws IOException {
        EncodingDetector detector=new UniversalEncodingDetector();
        Charset charset = detector.detect(new BufferedInputStream(new FileInputStream(file)), new Metadata());
        return charset.name();
    }

    public static String parseFileContent(File f) throws IOException {
        return parseFileContent(f, null);
    }

    public static String parseFileContent(File f, String contentType) throws IOException {
        Metadata metadata = new Metadata();
        metadata.set(Metadata.RESOURCE_NAME_KEY, f.getName());
        if (contentType == null || contentType.isEmpty()){
            contentType = detectMimeType(f);
        }
        metadata.set(Metadata.CONTENT_TYPE, contentType);

        InputStream is = new FileInputStream(f);
        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler(-1);
        //ContentHandler handler = new ToXMLContentHandler();
        ParseContext context = new ParseContext();
        context.set(Parser.class, parser);

        try {
            parser.parse(is, handler, metadata, new ParseContext());
        } catch (IOException | SAXException | TikaException e) {
            e.printStackTrace();
            return "";
        } finally {
            is.close();
        }

        return handler.toString();

    }

    /*
    public static String parseFileContent(ByteBuffer buffer, String filename, String contentType) {
        Metadata metadata = new Metadata();
        if (filename!=null && !filename.isEmpty()){
            metadata.add(Metadata.RESOURCE_NAME_KEY, filename);
        }

        if (contentType!=null && !contentType.isEmpty()){
            metadata.add(Metadata.CONTENT_TYPE, contentType);
        }

        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler(-1);
        //ContentHandler handler = new ToXMLContentHandler();
        ParseContext context = new ParseContext();
        context.set(Parser.class, parser);

        try {
            parser.parse(StreamInput.wrap(buffer.array()), handler, metadata, new ParseContext());
        } catch (IOException | SAXException | TikaException e) {
            e.printStackTrace();
            return "";
        }

        return handler.toString();

    }
    */

    // 计算文件的 MD5 值
    public static String getFileMD5(File file) throws NoSuchAlgorithmException, IOException {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[8192];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFileMD5(byte[] data) throws NoSuchAlgorithmException {

        MessageDigest digest = null;
        digest = MessageDigest.getInstance("MD5");

        BigInteger bigInt = new BigInteger(1, digest.digest(data));
        return bigInt.toString(16);
    }

    public static void updateMGFileBylocalFile(File f, MGFile file) throws IOException{
        file.fileName = f.getName();
        file.path= f.getPath();
        updateMGFileByTmpFile(f,file);
    }

    public static void updateMGFileByTmpFile(File f, MGFile file) throws IOException{
        file.size = file.size == 0 ?f.length():file.size;

        //忽略前段MIME类型，以后端作为标准
        String mimeType = FileUtil.detectMimeType(f);
        file.category = FileUtil.fileCategory(mimeType).name();
        file.fileType = FileUtil.fileType(mimeType);

        if (file.category.equalsIgnoreCase(FileCategory.DOC.name()) && (file.content == null || file.content.isEmpty())){
            file.content = FileUtil.parseFileContent(f);
        }

        if (file.hash == null || file.hash.isEmpty()){
            try {
                file.hash = FileUtil.getFileMD5(f);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                file.hash = "";
            }
        }

        if (file.createdOn == null){
            file.createdOn = new Date();
        }

        if (file.semaTypes == null){
            file.semaTypes = new HashSet<>();
        }
    }

    /*
    public static void updateMGFileByBuffer(ByteBuffer buffer, MGFile file){
        file.size = file.size==0?buffer.array().length:file.size;

        //自动探测
        String mimeType = FileUtil.detectMimeType(buffer.array(), file.getFileName());
        file.category = FileUtil.fileCategory(mimeType).name();
        file.fileType = FileUtil.fileType(mimeType);

        if (file.category.equalsIgnoreCase(FileCategory.DOC.name()) && (file.content == null || file.content.isEmpty())){
            file.content = FileUtil.parseFileContent(buffer, file.getFileName(), mimeType);
        }

        if (file.hash == null || file.hash.isEmpty()) {
            try {
                file.hash = FileUtil.getFileMD5(buffer.array());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                log.warn(e.getMessage());
                file.hash = "";
            }
        }

        if (file.createdOn == null){
            file.createdOn = new Date();
        }

        if (file.semaTypes == null){
            file.semaTypes = new HashSet<>();
        }
    }

    public static String parseFileContent(File file){
        String fileContent = null;
        try {
            String fileEncoding = encoding(file);
            fileContent = doParseTxtContent(file, fileEncoding);
        } catch (Exception e){
            MGLogger.warn(e.getMessage());
            try {
                fileContent = doParseFileContent(file);
            } catch (IOException e1) {
                e1.printStackTrace();
                MGLogger.warn(e1.getMessage());
                return "";
            }
        }

        return fileContent;
    }
    */


    /*
    public static String doParseFileContent(File f) throws IOException {
        Metadata metadata = new Metadata();
        metadata.set(Metadata.RESOURCE_NAME_KEY, f.getName());
        metadata.set(Metadata.CONTENT_TYPE, detectMimeType(f));

        InputStream is = new FileInputStream(f);
        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler(-1);
        ParseContext context = new ParseContext();
        context.set(Parser.class, parser);

        try {
            parser.parse(is, handler, metadata, new ParseContext());
        } catch (IOException | SAXException | TikaException e) {
            e.printStackTrace();
            return "";
        } finally {
            is.close();
        }

        return handler.toString();
    }

    public static String doParseTxtContent(File file, String fileEncoding) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), fileEncoding));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }
    */

    private byte[] defaultCharSetBytes(String filePath, String fileEncoding, String targetEncoding){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            //String fileEncoding = encoding(filePath);
            //String targetEncoding = Charset.defaultCharset().name();
            BufferedReader fis = new BufferedReader(new InputStreamReader(new FileInputStream(file), fileEncoding));
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(bos, targetEncoding));
            char[] b = new char[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bw.write(b, 0, n);
            }
            fis.close();
            bos.close();
            bw.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer;
    }

    private byte[] getBytes(String filePath) throws IOException {
        byte[] buffer = null;
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
        byte[] b = new byte[1000];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        fis.close();
        bos.close();
        buffer = bos.toByteArray();
        return buffer;
    }

}
