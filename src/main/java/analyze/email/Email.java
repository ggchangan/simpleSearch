package analyze.email;

import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Email {
    private static Logger logger = LogManager.getLogger(Email.class);

    public Email() {
        super();
        this.from = new LinkedList<Address>();
        this.replyTo = new LinkedList<Address>();
        this.to = new LinkedList<Address>();
        this.cc = new LinkedList<Address>();
        this.bcc = new LinkedList<Address>();
        this.sysFlags = new LinkedList<Flags.Flag>();
        this.userFlags = new LinkedList<String>();
        this.headers = new LinkedList<String>();
        this.attaches = new LinkedList<EmailAttachInfo>();
        this.receiveds = null;
        this.xOriginatingIp = null;
        this.messageId = null;
    }

    public void addRecipients(Address a, RecipientType recipientType) {
        if (recipientType == RecipientType.TO)
            this.addTO(a);
        else if (recipientType == RecipientType.CC)
            this.addCC(a);
        else if (recipientType == RecipientType.BCC)
            this.addBCC(a);
        else
            logger.error("Error recipientType, discarded " + a.toString());
    }

    public void addReplyTo(Address addr) {
        this.replyTo.add(addr);
    }

    public List<String> getReplyToTextList() throws UnsupportedEncodingException {
        return addressesTextList(this.replyTo);
    }

    public void addFrom(Address addr) {
        this.from.add(addr);
    }

    public List<String> getFromTextList() throws UnsupportedEncodingException {
        return addressesTextList(this.from);
    }

    private List<String> addressesTextList(List<Address> addrs) throws UnsupportedEncodingException {
        List<String> addrsString = new ArrayList<>();
        for (Address addr : addrs) {
            // sb.append(MimeUtility.decodeText(addr.toString()));
            if ("rfc822".equals(addr.getType())) {
                InternetAddress ia = (InternetAddress) addr;
                addrsString.add(ia.getAddress());
                //sb.append(ia.getPersonal() + " <" + ia.getAddress() + ">");
            } else {
                logger.warn("Unknown address type:" + addr.getType() + ",dicarded " + addr.toString());
            }
        }
        return addrsString;
    }

    public void addTO(Address addr) {
        this.to.add(addr);
    }

    public List<String> getToTextList() throws UnsupportedEncodingException {
        return this.addressesTextList(to);
    }

    public void addCC(Address addr) {
        this.cc.add(addr);
    }

    public List<String> getCCTextList() throws UnsupportedEncodingException {
        return this.addressesTextList(cc);
    }

    public void addBCC(Address addr) {
        this.bcc.add(addr);
    }

    public List<String> getBCCTextList() throws UnsupportedEncodingException {
        return this.addressesTextList(bcc);
    }

    public void addSysFlag(Flags.Flag f) {
        this.sysFlags.add(f);
    }

    public String getSysFlagsText() {
        return this.flagTextList(sysFlags);
    }

    private String flagTextList(List<Flag> flags) {
        StringBuilder sb = new StringBuilder();
        for (Flag f : flags) {
            if (f == Flags.Flag.ANSWERED)
                sb.append("Answered,");
            else if (f == Flags.Flag.DELETED)
                sb.append("Deleted,");
            else if (f == Flags.Flag.DRAFT)
                sb.append("Draft,");
            else if (f == Flags.Flag.FLAGGED)
                sb.append("Flagged,");
            else if (f == Flags.Flag.RECENT)
                sb.append("Recent,");
            else if (f == Flags.Flag.SEEN)
                sb.append("Seen,");
        }

        if (sb.length() >= 1)
            sb.replace(sb.length() - 1, sb.length(), "");

        return sb.toString();
    }

    public void addUserFlags(String flag) {
        this.userFlags.add(flag);
    }

    public String getUserFlagsText() {
        return stringsJoin(this.userFlags, ",");
    }

    private String stringsJoin(List<String> ss, String delim) {
        StringBuilder sb = new StringBuilder();
        for (String s : ss) {
            sb.append(s + delim);
        }
        // remove the last ","
        if (sb.length() >= 1)
            sb.replace(sb.length() - 1, sb.length(), "");

        return sb.toString();
    }

    public void addHeader(String s) {
        this.headers.add(s);
    }

    public String getHeadersText() {
        return stringsJoin(this.headers, "\n");
    }

    public void addAttaches(EmailAttachInfo attach) {
        this.attaches.add(attach);
    }

    public List<String> getAttachesTextList() {
        List<String> eaisString = new ArrayList<>();
        for (EmailAttachInfo eai : attaches) {
            eaisString.add(eai.fileName);
        }

        return eaisString;
    }

    public String subject;
    public List<Address> from;
    public List<Address> replyTo;
    public List<Address> to;
    public List<Address> cc;
    public List<Address> bcc;
    public Date receiveDate;
    public Date sendDate;
    public List<Flags.Flag> sysFlags;
    public List<String> userFlags;
    public String xmailer;
    public String content;
    public List<String> headers;
    public List<EmailAttachInfo> attaches;
    public String[] receiveds;
    public String xOriginatingIp;
    public String messageId;

    public String emlFile = null;

    public UUID objId = null;
    public UUID dsid = null;
    public Map<String, Object> indexMap = new HashMap<>();
    public Map<UUID, UUID> propertyMap = new HashMap<>();

    // received merged to one string, seperated with "\n"
    public List<String> getReceivedList() {
        List<String> receivedsString = new ArrayList<>();
        if (receiveds==null) return receivedsString;
        for (String r : receiveds) {
            receivedsString.add(r);
        }

        return receivedsString;
    }

    public boolean isValid() {
        if (from == null || from.isEmpty()) return false;
        //if (messageId == null || messageId.isEmpty()) return false;
        return true;
    }
}
