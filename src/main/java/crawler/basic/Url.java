package crawler.basic;

import java.sql.Timestamp;
import java.util.Date;

public class Url {
	// ԭʼurl��ֵ����������������
	private String oriUrl;
	// url��ֵ������������IP,Ϊ�˷�ֹ�ظ������ĳ���
	private String url;
	//URL NUM
	private int urlNo;
	// ��ȡURL���صĽ����
	private int statusCode;
	// ��URL������������õĴ���
	private int hitNum;
	// ��URL��Ӧ���µĺ��ֱ���
	private String charSet;
	// ����ժҪ
	private String abstractText;
	// ����
	private String author;
	// ���µ�Ȩ�أ���������ʵ���Ϣ��
	private int weight;
	// ���µ�����
	private String description;
	// ���´�С
	private int fileSize;
	// ����޸�ʱ��
	private Timestamp lastUpdateTime;
	// ����ʱ��
	private Date timeToLive;
	// ��������
	private String title;
	// ��������
	private String type;
	// ���õ�����
	private String[] urlRefrences;
    //��ȡ�Ĳ�Σ������ӿ�ʼ������Ϊ��0�㣬��1��...
	private int layer;
	public String getOriUrl() {
		return oriUrl;
	}

	public void setOriUrl(String oriUrl) {
		this.oriUrl = oriUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getUrlNo() {
		return urlNo;
	}

	public void setUrlNo(int urlNo) {
		this.urlNo = urlNo;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getHitNum() {
		return hitNum;
	}

	public void setHitNum(int hitNum) {
		this.hitNum = hitNum;
	}

	public String getCharSet() {
		return charSet;
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public String getAbstractText() {
		return abstractText;
	}

	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Date getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(Date timeToLive) {
		this.timeToLive = timeToLive;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getUrlRefrences() {
		return urlRefrences;
	}

	public void setUrlRefrences(String[] urlRefrences) {
		this.urlRefrences = urlRefrences;
	}
}
