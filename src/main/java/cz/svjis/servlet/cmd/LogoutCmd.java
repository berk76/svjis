/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.svjis.servlet.cmd;

import cz.svjis.bean.Language;
import cz.svjis.bean.LanguageDAO;
import cz.svjis.bean.LogDAO;
import cz.svjis.bean.User;
import cz.svjis.bean.UserDAO;
import cz.svjis.common.PermanentLoginUtils;
import cz.svjis.servlet.CmdContext;
import cz.svjis.servlet.Command;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author jaroslav_b
 */
public class LogoutCmd extends Command {

    public LogoutCmd(CmdContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() throws Exception {
        UserDAO userDao = new UserDAO(getCnn());
        LanguageDAO languageDao = new LanguageDAO(getCnn());
        LogDAO logDao = new LogDAO(getCnn());

        if (getUser() != null) {
            logDao.log(getUser().getId(), LogDAO.operationTypeLogout, LogDAO.idNull, getRequest().getRemoteAddr(), getRequest().getHeader("User-Agent"));
            PermanentLoginUtils.clearPermanentLogin(getRequest(), getResponse());
        }
        User user = new User();
        user.setCompanyId(getCompany().getId());

        if ((getSetup().getProperty("anonymous.user.id") != null) && (userDao.getUser(getCompany().getId(),
                Integer.valueOf(getSetup().getProperty("anonymous.user.id"))) != null)) {
            user = userDao.getUser(getCompany().getId(), Integer.valueOf(getSetup().getProperty("anonymous.user.id")));
        }

        getRequest().getSession().setAttribute("user", user);
        Language language = languageDao.getDictionary(user.getLanguageId());
        getRequest().getSession().setAttribute("language", language);

        String url = "Dispatcher?page=articleList";
        getRequest().setAttribute("url", url);
        RequestDispatcher rd = getRequest().getRequestDispatcher("/_refresh.jsp");
        rd.forward(getRequest(), getResponse());
    }
}