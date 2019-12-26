package com.github.hwhaocool.mm.service.alarm;

public class MarkdownMessage {

    /**
     * 消息内容，最大 2048 字节
     */
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MarkdownMessage [content=" + content + "]";
    }
    
}
