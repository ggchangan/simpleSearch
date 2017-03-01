package crawler.basic.parser;

import org.json.JSONObject;

import java.util.UUID;
import java.util.Date;
import java.util.Set;
import java.util.Map;

public class MGFile {

    public UUID id;

    public UUID chunkId;
    public UUID fileObject;


    public int createdBy;
    public Date createdOn;
    public int modifiedBy;
    public Date modifiedOn;
    public int accessedBy;
    public Date accessedOn;

    public String content;


    public String label;
    public String description;
    public String fileName;
    public String path;
    public String fileType;
    public String category;
    public String hash;
    public int blockCount;
    public long size;
    public Set<UUID> dataSource;

    public Date timeStart;
    public Date timeEnd;
    public double gisX;
    public double gisY;

    public Set<UUID> linkedObject;
    public Set<UUID> semaTypes;

    public MGFile(UUID id) {
        this.id = id;
    }
    public MGFile() {
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public UUID getFileObject() {
        return fileObject;
    }
    public void setFileObject(UUID fileObject) {
        this.fileObject = fileObject;
    }

    public UUID getChunkId() {
        return chunkId;
    }

    public void setChunkId(UUID chunkId) {
        this.chunkId = chunkId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public int getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(int modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public int getAccessedBy() {
        return accessedBy;
    }

    public void setAccessedBy(int accessedBy) {
        this.accessedBy = accessedBy;
    }

    public Date getAccessedOn() {
        return accessedOn;
    }

    public void setAccessedOn(Date accessedOn) {
        this.accessedOn = accessedOn;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
    	return path;
    }
    
    public void setPath(String path) {
    	this.path = path;
    }
    
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getBlockCount() {
        return blockCount;
    }

    public void setBlockCount(int blockCount) {
        this.blockCount = blockCount;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Set<UUID> getDataSource() {
        return dataSource;
    }

    public void setDataSource(Set<UUID> dataSource) {
        this.dataSource = dataSource;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public double getGisX() {
        return gisX;
    }

    public void setGisX(double gisX) {
        this.gisX = gisX;
    }

    public double getGisY() {
        return gisY;
    }

    public void setGisY(double gisY) {
        this.gisY = gisY;
    }

    public Set<UUID> getSemaTypes() {
    	return semaTypes;
    }
    
    public void setSemaTypes(Set<UUID> semaTypes) {
    	this.semaTypes = semaTypes;
    }
    
    public Set<UUID> getLinkedObject() {
        return linkedObject;
    }

    public void setLinkedObject(Set<UUID> linkedObject) {
        this.linkedObject = linkedObject;
    }

    public enum MGFileField {
        ID("id"),
        FILE_OBJECT("fileObject"),
        CREATED_BY("createdBy"),
        CREATED_ON("createdOn"),
        MODIFIED_BY("modifiedBy"),
        MODIFIED_ON("modifiedOn"),
        ACCESSED_BY("accessedBy"),
        ACCESSED_ON("accessedOn"),
        CONTENT("content"),
        LABEL("label"),
        DESCRIPTION("description"),
        FILE_NAME("fileName"),
        PATH("path"),
        FILE_TYPE("fileType"),
        CATEGORY("category"),
        HASH("hash"),
        BLOCK_COUNT("blockCount"),
        SIZE("size"),
        DATA_SOURCE("dataSource"),
        TIME_START("timeStart"),
        TIME_END("timeEnd"),
        GIS_X("gisX"),
        GIS_Y("gisY"),
        LINKED_OBJECT("linkedObject"),
        SEMA_TYPES("semaTypes");
        private String field;
        MGFileField(String field){
           this.field =field;
        }

        public String getField() {
            return field;
        }
    }

    public JSONObject toJson() {
        JSONObject jsObj = new JSONObject();
        jsObj.put(MGFileField.ID.getField(), getId());
        jsObj.put(MGFileField.FILE_OBJECT.getField(), getFileObject());
        jsObj.put(MGFileField.CREATED_BY.getField(), getCreatedBy());
        jsObj.put(MGFileField.CREATED_ON.getField(), getCreatedOn());
        jsObj.put(MGFileField.ACCESSED_BY.getField(), getAccessedBy());
        jsObj.put(MGFileField.ACCESSED_ON.getField(), getAccessedOn());
        jsObj.put(MGFileField.MODIFIED_BY.getField(), getModifiedBy());
        jsObj.put(MGFileField.MODIFIED_ON.getField(), getModifiedOn());
        if (getContent()!=null){
            if (getContent().length()>100){
                //内容太多，显示"..."
                jsObj.put(MGFileField.CONTENT.getField(), "...");
            } else {
                jsObj.put(MGFileField.CONTENT.getField(), getContent());
            }
        }
        jsObj.put(MGFileField.LABEL.getField(), getLabel());
        jsObj.put(MGFileField.DESCRIPTION.getField(), getDescription());
        jsObj.put(MGFileField.FILE_NAME.getField(), getFileName());
        jsObj.put(MGFileField.PATH.getField(), getPath());
        jsObj.put(MGFileField.FILE_TYPE.getField(), getFileType());
        jsObj.put(MGFileField.CATEGORY.getField(), getCategory());
        jsObj.put(MGFileField.HASH.getField(), getHash());
        jsObj.put(MGFileField.BLOCK_COUNT.getField(), getBlockCount());
        jsObj.put(MGFileField.SIZE.getField(), getSize());
        jsObj.put(MGFileField.DATA_SOURCE.getField(), getDataSource());
        jsObj.put(MGFileField.TIME_START.getField(), getTimeStart());
        jsObj.put(MGFileField.TIME_END.getField(), getTimeEnd());
        jsObj.put(MGFileField.GIS_X.getField(), getGisX());
        jsObj.put(MGFileField.GIS_Y.getField(), getGisY());
        jsObj.put(MGFileField.LINKED_OBJECT.getField(), getLinkedObject());
        jsObj.put(MGFileField.SEMA_TYPES.getField(), getSemaTypes());

        return jsObj;
    }
}
