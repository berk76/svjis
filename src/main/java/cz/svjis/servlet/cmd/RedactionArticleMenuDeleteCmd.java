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
import cz.svjis.validator.Validator;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author jaroslav_b
 */
public class RedactionArticleMenuDeleteCmd extends Command {

    public RedactionArticleMenuDeleteCmd(CmdContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() throws Exception {

        int parMenuId = Validator.getInt(getRequest(), "id", 0, Validator.maxIntAllowed, false);

        MenuDAO menuDao = new MenuDAO(getCnn());
        
        MenuNode n = menuDao.getMenuNode(parMenuId, getCompany().getId());
        if (n != null) {
            if (n.getNumOfChilds() != 0) {
                String message = "Cannot delete menu which has child nodes.";
                getRequest().setAttribute("messageHeader", "Error");
                getRequest().setAttribute("message", message);
                RequestDispatcher rd = getRequest().getRequestDispatcher("/_message.jsp");
                rd.forward(getRequest(), getResponse());
                return;
            }
            menuDao.deleteMenuNode(n, getUser().getCompanyId());
        }
        String url = "Dispatcher?page=redactionArticleMenu";
        getRequest().setAttribute("url", url);
        RequestDispatcher rd = getRequest().getRequestDispatcher("/_refresh.jsp");
        rd.forward(getRequest(), getResponse());
    }
}
