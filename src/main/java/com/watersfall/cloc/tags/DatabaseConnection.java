package com.watersfall.cloc.tags;

import com.watersfall.cloc.database.Database;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import javax.sql.DataSource;

public class DatabaseConnection extends TagSupport
{

    private String var;
    private int scope;
    private DataSource ds;

    public void setVar(String var)
    {
        this.var = var;
    }

    private void init()
    {
        ds = Database.getDataSource();
        scope = PageContext.PAGE_SCOPE;
    }

    @Override
    public int doStartTag() throws JspException
    {
        init();

        pageContext.setAttribute(var, ds, scope);

        return SKIP_BODY;
    }
}
