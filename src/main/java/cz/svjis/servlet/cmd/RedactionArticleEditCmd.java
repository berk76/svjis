/*
 *       RedactionArticleEditCmd.java
 *
 *       This file is part of SVJIS project.
 *       https://github.com/svjis/svjis
 *
 *       SVJIS is free software; you can redistribute it and/or modify
 *       it under the terms of the GNU General Public License as published by
 *       the Free Software Foundation; either version 3 of the License, or
 *       (at your option) any later version. <http://www.gnu.org/licenses/>
 */

package cz.svjis.servlet.cmd;

import cz.svjis.bean.Article;
import cz.svjis.bean.ArticleDAO;
import cz.svjis.bean.Language;
import cz.svjis.bean.LanguageDAO;
import cz.svjis.bean.Menu;
import cz.svjis.bean.MenuDAO;
import cz.svjis.bean.Permission;
import cz.svjis.bean.Role;
import cz.svjis.bean.RoleDAO;
import cz.svjis.servlet.CmdContext;
import cz.svjis.servlet.Command;
import cz.svjis.validator.Validator;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author jaroslav_b
 */
public class RedactionArticleEditCmd extends Command {

    public RedactionArticleEditCmd(CmdContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() throws Exception {

        if (!getUser().hasPermission(Permission.REDACTION_ARTICLES) && !getUser().hasPermission(Permission.REDACTION_ARTICLES_ALL)) {
            new Error401UnauthorizedCmd(getCtx()).execute();
            return;
        }
        
        int parArticleId = Validator.getInt(getRequest(), "id", 0, Validator.MAX_INT_ALLOWED, true);
        
        MenuDAO menuDao = new MenuDAO(getCnn());
        ArticleDAO articleDao = new ArticleDAO(getCnn());
        RoleDAO roleDao = new RoleDAO(getCnn());
        LanguageDAO languageDao = new LanguageDAO(getCnn());

        Article article;
        
        if (parArticleId == 0) {
            article = new Article();
        } else {
            article = articleDao.getArticle(getUser(), parArticleId);
            if (article == null) {
                new Error404NotFoundCmd(getCtx()).execute();
                return;
            }
            if ((article.getAuthor().getId() != getUser().getId()) && !getUser().hasPermission(Permission.REDACTION_ARTICLES_ALL)) {
                new Error401UnauthorizedCmd(getCtx()).execute();
                return;
            }
        }
        
        getRequest().setAttribute("article", article);

        Menu menu = menuDao.getMenu(getCompany().getId());
        menu.setActiveSection(-1);
        getRequest().setAttribute("menu", menu);
        ArrayList<Language> languageList = new ArrayList<>(languageDao.getLanguageList());
        getRequest().setAttribute("languageList", languageList);
        ArrayList<Role> roleList = new ArrayList<>(roleDao.getRoleList(getCompany().getId()));
        getRequest().setAttribute("roleList", roleList);

        RequestDispatcher rd = getRequest().getRequestDispatcher("/WEB-INF/jsp/Redaction_ArticleEdit.jsp");
        rd.forward(getRequest(), getResponse());
    }
}
