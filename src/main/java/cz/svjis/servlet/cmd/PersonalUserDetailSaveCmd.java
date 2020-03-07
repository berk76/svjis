/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.svjis.servlet.cmd;

import cz.svjis.bean.Language;
import cz.svjis.bean.LanguageDAO;
import cz.svjis.bean.UserDAO;
import cz.svjis.servlet.CmdContext;
import cz.svjis.servlet.Command;
import cz.svjis.validator.Validator;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author jaroslav_b
 */
public class PersonalUserDetailSaveCmd extends Command {

    public PersonalUserDetailSaveCmd(CmdContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() throws Exception {

        String parSalutation = Validator.getString(getRequest(), "salutation", 0, 30, false, false);
        String parFirstName = Validator.getString(getRequest(), "firstName", 0, 30, false, false);
        String parLastName = Validator.getString(getRequest(), "lastName", 0, 30, false, false);
        int parLangId = Validator.getInt(getRequest(), "language", 0, Validator.MAX_INT_ALLOWED, false);
        String parAddress = Validator.getString(getRequest(), "address", 0, 50, false, false);
        String parCity = Validator.getString(getRequest(), "city", 0, 50, false, false);
        String parPostCode = Validator.getString(getRequest(), "postCode", 0, 10, false, false);
        String parCountry = Validator.getString(getRequest(), "country", 0, 50, false, false);
        String parFixedPhone = Validator.getString(getRequest(), "fixedPhone", 0, 30, false, false);
        String parCellPhone = Validator.getString(getRequest(), "cellPhone", 0, 30, false, false);
        String parEMail = Validator.getString(getRequest(), "eMail", 0, 50, false, false);

        LanguageDAO languageDao = new LanguageDAO(getCnn());
        UserDAO userDao = new UserDAO(getCnn());
        
        getUser().setSalutation(parSalutation);
        getUser().setFirstName(parFirstName);
        getUser().setLastName(parLastName);
        getUser().setLanguageId(parLangId);
        getUser().setAddress(parAddress);
        getUser().setCity(parCity);
        getUser().setPostCode(parPostCode);
        getUser().setCountry(parCountry);
        getUser().setFixedPhone(parFixedPhone);
        getUser().setCellPhone(parCellPhone);
        getUser().seteMail(parEMail);
        getUser().setShowInPhoneList(getRequest().getParameter("phoneList") != null);
        userDao.modifyUser(getUser());
        Language language = languageDao.getDictionary(getUser().getLanguageId());
        getRequest().getSession().setAttribute("language", language);
        String url = "Dispatcher?page=psUserDetail";
        getRequest().setAttribute("url", url);
        RequestDispatcher rd = getRequest().getRequestDispatcher("/_refresh.jsp");
        rd.forward(getRequest(), getResponse());
    }
}
