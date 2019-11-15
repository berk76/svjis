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
import cz.svjis.validator.Validator;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author jaroslav_b
 */
public class LoginCmd extends Command {

    public LoginCmd(CmdContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() throws Exception {
        
        String parLogin = getRequest().getParameter("login");
        String parPassword = getRequest().getParameter("password");
        
        if (!validateInput(parLogin, parPassword)) {
            RequestDispatcher rd = getRequest().getRequestDispatcher("/BadPage.jsp");
            rd.forward(getRequest(), getResponse());
            return;
        }
        
        UserDAO userDao = new UserDAO(getCnn());
        LanguageDAO languageDao = new LanguageDAO(getCnn());
        LogDAO logDao = new LogDAO(getCnn());

        User u = userDao.getUserByLogin(getCompany().getId(), parLogin);
        if ((u != null) && userDao.verifyPassword(u, parPassword, true)) {
            User user = u;
            getRequest().getSession().setAttribute("user", user);
            Language language = languageDao.getDictionary(user.getLanguageId());
            getRequest().getSession().setAttribute("language", language);
            logDao.log(user.getId(), LogDAO.operationTypeLogin, LogDAO.idNull, getRequest().getRemoteAddr(), getRequest().getHeader("User-Agent"));
            PermanentLoginUtils.savePermanentLogin(getRequest(), getResponse(), user, userDao);
            
            String url = "Dispatcher?page=articleList";
            getRequest().setAttribute("url", url);
            RequestDispatcher rd = getRequest().getRequestDispatcher("/_refresh.jsp");
            rd.forward(getRequest(), getResponse());
        } else {
            getRequest().setAttribute("messageHeader", getLanguage().getText("Bad login"));
            getRequest().setAttribute("message", "<p>" + getLanguage().getText("You can continue") + " <a href=\"Dispatcher\">" + getLanguage().getText("here") + "</a>.</p><p><a href=\"Dispatcher?page=lostPassword\">" + getLanguage().getText("Forgot password?") + "</a></p>");
            RequestDispatcher rd = getRequest().getRequestDispatcher("/_message.jsp");
            rd.forward(getRequest(), getResponse());
        }
    }
    
    private boolean validateInput(String login, String password) {
        boolean result = true;
        
        if (!Validator.validateString(login, 0, 50)) {
            result = false;
        }
        
        if (!Validator.validateString(password, 0, 50)) {
            result = false;
        }
        
        return result;
    }

}