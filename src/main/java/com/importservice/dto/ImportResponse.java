package com.importservice.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ImportResponse {
    private int recordsImported;
    private int recordsFailed;
    private List<String> errors;
    private String message;
    private LocalDateTime timestamp;
    
    public ImportResponse() {
        this.timestamp = LocalDateTime.now();
    }
    
    public ImportResponse(int recordsImported, int recordsFailed, List<String> errors) {
        this();
        this.recordsImported = recordsImported;
        this.recordsFailed = recordsFailed;
        this.errors = errors;
        this.message = "Proceso completado";
    }
    
    public int getRecordsImported() { return recordsImported; }
    public void setRecordsImported(int recordsImported) { this.recordsImported = recordsImported; }
    public int getRecordsFailed() { return recordsFailed; }
    public void setRecordsFailed(int recordsFailed) { this.recordsFailed = recordsFailed; }
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}