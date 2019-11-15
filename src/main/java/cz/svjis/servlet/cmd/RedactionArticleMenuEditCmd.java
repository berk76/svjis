/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.svjis.servlet.cmd;

import cz.svjis.bean.MenuDAO;
import cz.svjis.bean.MenuNode;
import cz.svjis.servlet.CmdContext;
import cz.svjis.servlet.Command;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author jaroslav_b
 */
public class RedactionArticleMenuEditCmd extends Command {

    public RedactionArticleMenuEditCmd(CmdContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() throws Exception {
        MenuDAO menuDao = new MenuDAO(getCnn());

        int id = Integer.parseInt(getRequest().getParameter("id"));
        MenuNode menuNode = new MenuNode();
        if (id != 0) {
            menuNode = menuDao.getMenuNode(id, getUser().getCompanyId());
        }
        getRequest().setAttribute("menuNode", menuNode);
        ArrayList<MenuNode> menuNodeList = menuDao.getMenuNodeList(getUser().getCompanyId());
        getRequest().setAttribute("menuNodeList", menuNodeList);
        RequestDispatcher rd = getRequest().getRequestDispatcher("/Redaction_ArticleMenuEdit.jsp");
        rd.forward(getRequest(), getResponse());
    }
}