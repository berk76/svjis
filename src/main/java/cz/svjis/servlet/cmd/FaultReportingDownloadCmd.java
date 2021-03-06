/*
 *       FaultReportingDownloadCmd.java
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

import cz.svjis.bean.Attachment;
import cz.svjis.bean.FaultReportDAO;
import cz.svjis.bean.Permission;
import cz.svjis.common.JspSnippets;
import cz.svjis.servlet.CmdContext;
import cz.svjis.servlet.Command;
import cz.svjis.validator.Validator;

/**
 *
 * @author jarberan
 */
public class FaultReportingDownloadCmd extends Command {
    
    public FaultReportingDownloadCmd(CmdContext ctx) {
        super(ctx);
    }
    
    @Override
    public void execute() throws Exception {
        
        if (!getUser().hasPermission(Permission.MENU_FAULT_REPORTING)) {
            new Error401UnauthorizedCmd(getCtx()).execute();
            return;
        }
        
        int parId = Validator.getInt(getRequest(), "id", 0, Validator.MAX_INT_ALLOWED, false);

        FaultReportDAO faultDao = new FaultReportDAO(getCnn());
        Attachment fa = faultDao.getFaultReportAttachment(parId);
        if ((fa == null) || (faultDao.getFault(getCompany().getId(), fa.getDocumentId()) == null)) {
            new Error404NotFoundCmd(getCtx()).execute();
            return;
        }
        JspSnippets.writeBinaryData(fa.getContentType(), fa.getFileName(), fa.getData(), getRequest(), getResponse());
    }
}
