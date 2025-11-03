package com.importservice.dto;

public class ImportRequest {
    private String dataType;
    private String data;
    private String filename;
    
    public String getDataType() { return dataType; }
    public void setDataType(String dataType) { this.dataType = dataType; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }
}