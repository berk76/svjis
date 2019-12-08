/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.svjis.servlet.cmd;

import cz.svjis.bean.BuildingDAO;
import cz.svjis.servlet.CmdContext;
import cz.svjis.servlet.Command;
import cz.svjis.validator.Validator;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author jaroslav_b
 */
public class UserBuildingUnitAddCmd extends Command {

    public UserBuildingUnitAddCmd(CmdContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() throws Exception {
        
        String parUnitId = getRequest().getParameter("unitId");
        String parUserId = getRequest().getParameter("userId");
        
        if (!validateInput(parUnitId, parUserId)) {
            RequestDispatcher rd = getRequest().getRequestDispatcher("/InputValidationError.jsp");
            rd.forward(getRequest(), getResponse());
            return;
        }
        
        BuildingDAO buildingDao = new BuildingDAO(getCnn());

        if (Integer.valueOf(parUnitId) != 0) {
            buildingDao.addUserHasBuildingUnitConnection(
                    Integer.valueOf(parUserId),
                    Integer.valueOf(parUnitId));
        }
        String url = "Dispatcher?page=userBuildingUnits&id=" + parUserId;
        getRequest().setAttribute("url", url);
        RequestDispatcher rd = getRequest().getRequestDispatcher("/_refresh.jsp");
        rd.forward(getRequest(), getResponse());
    }
    
    private boolean validateInput(String parUnitId, String parUserId) {
        boolean result = true;
        
        if (!Validator.validateInteger(parUnitId, 0, Validator.maxIntAllowed)) {
            result = false;
        }
        
        if (!Validator.validateInteger(parUserId, 0, Validator.maxIntAllowed)) {
            result = false;
        }

        return result;
    }
}
