/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.svjis.servlet.cmd;

import cz.svjis.bean.BuildingDAO;
import cz.svjis.bean.BuildingUnit;
import cz.svjis.servlet.CmdContext;
import cz.svjis.servlet.Command;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author jaroslav_b
 */
public class BuildingUnitDeleteCmd extends Command {

    public BuildingUnitDeleteCmd(CmdContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() throws Exception {
        BuildingDAO buildingDao = new BuildingDAO(getCnn());

        BuildingUnit u = new BuildingUnit();
        u.setId(Integer.valueOf(getRequest().getParameter("id")));
        u.setBuildingId(buildingDao.getBuilding(getCompany().getId()).getId());
        buildingDao.deleteBuildingUnit(u);
        String url = "Dispatcher?page=buildingUnitList";
        getRequest().setAttribute("url", url);
        RequestDispatcher rd = getRequest().getRequestDispatcher("/_refresh.jsp");
        rd.forward(getRequest(), getResponse());
    }
}