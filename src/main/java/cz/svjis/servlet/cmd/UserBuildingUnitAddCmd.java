/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.svjis.servlet.cmd;

import cz.svjis.bean.Building;
import cz.svjis.bean.BuildingDAO;
import cz.svjis.bean.BuildingUnit;
import cz.svjis.bean.User;
import cz.svjis.bean.UserDAO;
import cz.svjis.servlet.CmdContext;
import cz.svjis.servlet.Command;
import cz.svjis.validator.Validator;
import java.util.ArrayList;
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

        int parUnitId = Validator.getInt(getRequest(), "unitId", 0, Validator.maxIntAllowed, false);
        int parUserId = Validator.getInt(getRequest(), "userId", 0, Validator.maxIntAllowed, false);

        BuildingDAO buildingDao = new BuildingDAO(getCnn());
        UserDAO userDao = new UserDAO(getCnn());

        Building b = buildingDao.getBuilding(getCompany().getId());
        BuildingUnit unit = buildingDao.getBuildingUnit(parUnitId);
        User u = userDao.getUser(getCompany().getId(), parUserId);

        if ((unit != null) && (unit.getBuildingId() == b.getId()) && u != null) {
            boolean alreadyHas = false;
            ArrayList<User> usrList = buildingDao.getBuildingUnitHasUserList(parUnitId);
            for (User usr: usrList) {
                if (usr.getId() == u.getId()) {
                    alreadyHas = true;
                    break;
                }
            }

            if (!alreadyHas) {
                buildingDao.addUserHasBuildingUnitConnection(parUserId, parUnitId);
            }
        }

        String url = "Dispatcher?page=userBuildingUnits&id=" + parUserId;
        getRequest().setAttribute("url", url);
        RequestDispatcher rd = getRequest().getRequestDispatcher("/_refresh.jsp");
        rd.forward(getRequest(), getResponse());
    }
}
