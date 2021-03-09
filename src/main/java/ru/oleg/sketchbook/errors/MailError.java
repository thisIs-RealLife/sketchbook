package ru.oleg.sketchbook.errors;

import javax.mail.internet.InternetAddress;

public class MailError extends Exception{
    protected InternetAddress addr;
    protected String cmd;
    protected int rc;
    private static final long serialVersionUID = 804831199768630097L;

    public MailError(InternetAddress addr, String cmd, int rc, String err) {
        super(err);
        this.addr = addr;
        this.cmd = cmd;
        this.rc = rc;
    }

    public InternetAddress getAddress() {
        return this.addr;
    }

    public String getCommand() {
        return this.cmd;
    }

    public int getReturnCode() {
        return this.rc;
    }
}
