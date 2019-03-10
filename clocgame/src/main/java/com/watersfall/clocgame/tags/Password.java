package com.watersfall.clocgame.tags;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Password extends SimpleTagSupport
{

    private String value;
    StringWriter sw = new StringWriter();

    public void setValue(String value)
    {
        this.value = value;
    }

    public String md5(String value) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(value.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        return number.toString(16);
    }

    public void doTag() throws JspException, IOException
    {
        if(value != null)
        {
            JspWriter out = getJspContext().getOut();
            try
            {
                out.println(md5(value).toUpperCase());
            }
            catch(NoSuchAlgorithmException e)
            {

            }
        }
    }
}
