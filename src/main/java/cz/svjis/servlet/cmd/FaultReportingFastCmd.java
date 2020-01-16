/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.svjis.servlet.cmd;

import cz.svjis.bean.FaultReport;
import cz.svjis.bean.FaultReportDAO;
import cz.svjis.servlet.CmdContext;
import cz.svjis.servlet.Command;
import cz.svjis.validator.Validator;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author jarberan
 */
public class FaultReportingFastCmd extends Command {
    
    public FaultReportingFastCmd(CmdContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() throws Exception {
        
        int parId = Validator.getInt(getRequest(), "id", 0, Validator.maxIntAllowed, false);
        boolean parWatch = Validator.getBoolean(getRequest(), "watch");
        
        FaultReportDAO faultDao = new FaultReportDAO(getCnn());
        
        FaultReport f = faultDao.getFault(getCompany().getId(), parId);
        if (getUser().hasPermission("fault_reporting_resolver") && (f != null)) {
            
            if (getRequest().getParameter("takeTicket") != null) {
                f.setAssignedToUser(getUser());
                faultDao.modifyFault(f);
                faultDao.setUserWatchingFaultReport(f.getId(), f.getAssignedToUser().getId());
            }
            if (getRequest().getParameter("closeTicket") != null) {
                f.setClosed(true);
                faultDao.modifyFault(f);
            }
        }
        
        if ((getRequest().getParameter("watch") != null) && (f != null)) {
            if (parWatch) {
                faultDao.setUserWatchingFaultReport(f.getId(), getUser().getId());
            } else {
                faultDao.unsetUserWatchingFaultReport(f.getId(), getUser().getId());
            }
        }

        String url = "Dispatcher?page=faultDetail&id=" + parId;
        getRequest().setAttribute("url", url);
        
        RequestDispatcher rd = getRequest().getRequestDispatcher("/_refresh.jsp");
        rd.forward(getRequest(), getResponse());
    }
}
