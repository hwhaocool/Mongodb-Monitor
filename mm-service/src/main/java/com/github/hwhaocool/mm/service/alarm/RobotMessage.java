package com.github.hwhaocool.mm.service.alarm;

public class RobotMessage {

    private String msgtype;
    
    private MarkdownMessage markdown;
    
    public RobotMessage() {
        msgtype = "markdown";
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public MarkdownMessage getMarkdown() {
        return markdown;
    }

    public void setMarkdown(MarkdownMessage markdown) {
        this.markdown = markdown;
    }

    @Override
    public String toString() {
        return "RobotMessage [msgtype=" + msgtype + ", markdown=" + markdown + "]";
    }
    
    
}
