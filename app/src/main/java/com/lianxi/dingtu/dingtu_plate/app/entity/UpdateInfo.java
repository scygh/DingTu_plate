package com.lianxi.dingtu.dingtu_plate.app.entity;

public class UpdateInfo {
    /**
     * id	string($guid)
     * key	string
     * version	string
     * resourceName	string
     * resourceUri	string
     * resourceMD5	string
     * resourceSHA1	string
     * resourceType	integer
     * updateType	integer
     * createTime	string($date-time)
     */
    public String message;
    public boolean result;
    public boolean isOk;
    public Integer statusCode;
    public Content content;

    public static class Content{
        public String id;
        public String key;
        public String version;
        public String resourceName;
        public String resourceUri;
        public String resourceMD5;
        public String resourceSHA1;
        public Integer resourceType;
        public Integer updateType;
        public String createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getResourceName() {
            return resourceName;
        }

        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
        }

        public String getResourceUri() {
            return resourceUri;
        }

        public void setResourceUri(String resourceUri) {
            this.resourceUri = resourceUri;
        }

        public String getResourceMD5() {
            return resourceMD5;
        }

        public void setResourceMD5(String resourceMD5) {
            this.resourceMD5 = resourceMD5;
        }

        public String getResourceSHA1() {
            return resourceSHA1;
        }

        public void setResourceSHA1(String resourceSHA1) {
            this.resourceSHA1 = resourceSHA1;
        }

        public Integer getResourceType() {
            return resourceType;
        }

        public void setResourceType(Integer resourceType) {
            this.resourceType = resourceType;
        }

        public Integer getUpdateType() {
            return updateType;
        }

        public void setUpdateType(Integer updateType) {
            this.updateType = updateType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
