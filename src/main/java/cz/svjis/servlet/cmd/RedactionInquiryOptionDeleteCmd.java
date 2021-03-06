/*
 *       RedactionInquiryOptionDeleteCmd.java
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

import cz.svjis.bean.InquiryDAO;
import cz.svjis.bean.InquiryOption;
import cz.svjis.bean.Permission;
import cz.svjis.servlet.Cmd;
import cz.svjis.servlet.CmdContext;
import cz.svjis.servlet.Command;
import cz.svjis.validator.Validator;

/**
 *
 * @author jaroslav_b
 */
public class RedactionInquiryOptionDeleteCmd extends Command {

    public RedactionInquiryOptionDeleteCmd(CmdContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() throws Exception {
        
        if (!getUser().hasPermission(Permission.REDACTION_INQUIRY)) {
            new Error401UnauthorizedCmd(getCtx()).execute();
            return;
        }
        
        int parId = Validator.getInt(getRequest(), "id", 0, Validator.MAX_INT_ALLOWED, false);

        InquiryDAO inquiryDao = new InquiryDAO(getCnn());

        InquiryOption io = inquiryDao.getInquiryOption(getUser().getCompanyId(), parId);
        if (io == null) {
            new Error404NotFoundCmd(getCtx()).execute();
            return;
        }
        inquiryDao.deleteInquiryOption(io);
        String url = String.format("Dispatcher?page=%s&id=%d", Cmd.REDACTION_INQUIRY_EDIT ,io.getInquiryId());
        getResponse().sendRedirect(url);
    }
}
