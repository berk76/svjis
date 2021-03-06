/*
 *       AdvertListCmd.java
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

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;

import cz.svjis.bean.Advert;
import cz.svjis.bean.AdvertDAO;
import cz.svjis.bean.AdvertType;
import cz.svjis.bean.Permission;
import cz.svjis.bean.SliderImpl;
import cz.svjis.servlet.CmdContext;
import cz.svjis.servlet.Command;
import cz.svjis.validator.Validator;

public class AdvertListCmd extends Command {
    
    public AdvertListCmd(CmdContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() throws Exception {
        
        if (!getUser().hasPermission(Permission.MENU_ADVERTS)) {
            new Error401UnauthorizedCmd(getCtx()).execute();
            return;
        }
        
        String parPage = Validator.getString(getRequest(), "page", 0, Validator.MAX_STRING_LEN_ALLOWED, false, false);
        int parTypeId = Validator.getInt(getRequest(), "typeId", 0, Validator.MAX_INT_ALLOWED, true);
        int parPageNo = Validator.getInt(getRequest(), "pageNo", 0, Validator.MAX_INT_ALLOWED, true);
        
        AdvertDAO advertDao = new AdvertDAO(getCnn());
        
        int pageNo = (parPageNo == 0) ? 1 : parPageNo;
        int pageSize = getSetup().getAdvertPageSize();
        
        SliderImpl sl = new SliderImpl();
        sl.setPageId(parPage);
        sl.setSliderWide(10);
        sl.setCurrentPage(pageNo);
        sl.setNumOfItemsAtPage(pageSize);
        int listSize;
        switch (parTypeId) {
            case AdvertDAO.ALL_ADVERTS_TYPE_ID:
                listSize = advertDao.getAdverListSize(AdvertDAO.AdvertListType.ALL, getCompany().getId(), 0);
                break;
            case AdvertDAO.MY_ADVERTS_TYPE_ID:
                listSize = advertDao.getAdverListSize(AdvertDAO.AdvertListType.USER, getCompany().getId(), getUser().getId());
                break;
            default:
                listSize = advertDao.getAdverListSize(AdvertDAO.AdvertListType.ADVERT_TYPE, getCompany().getId(), parTypeId);
        }
        sl.setTotalNumOfItems(listSize);
        getRequest().setAttribute("slider", sl);
        
        ArrayList<AdvertType> advertMenuList = new ArrayList<>(advertDao.getAdvertTypeList(getCompany().getId()));
        AdvertType at = advertDao.getAllAdvertType(getCompany().getId());
        at.setDescription(getLanguage().getText(at.getDescription()));
        advertMenuList.add(0, at);
        if (getUser().hasPermission(Permission.CAN_INSERT_ADVERT)) {
            at = advertDao.getMyAdvertType(getCompany().getId(), getUser().getId());
            at.setDescription(getLanguage().getText(at.getDescription()));
            advertMenuList.add(at);
        }
        getRequest().setAttribute("advertMenuList", advertMenuList);
        
        AdvertType currMenu = null; 
        for (AdvertType att: advertMenuList) {
            if (att.getId() == parTypeId) {
                currMenu = att;
                break;
            }
        }
        if (currMenu == null) {
            new Error404NotFoundCmd(getCtx()).execute();
            return;
        }
        getRequest().setAttribute("currMenu", currMenu);
        
        ArrayList<AdvertType> advertTypeList = new ArrayList<>(advertDao.getAdvertTypeList(getCompany().getId()));
        getRequest().setAttribute("advertTypeList", advertTypeList);
        
        ArrayList<Advert> advertList;
        switch (parTypeId) {
            case AdvertDAO.ALL_ADVERTS_TYPE_ID:
                advertList = new ArrayList<>(advertDao.getAdvertList(pageNo, pageSize, AdvertDAO.AdvertListType.ALL, getCompany().getId(), 0));
                break;
            case AdvertDAO.MY_ADVERTS_TYPE_ID:
                advertList = new ArrayList<>(advertDao.getAdvertList(pageNo, pageSize, AdvertDAO.AdvertListType.USER, getCompany().getId(), getUser().getId()));
                break;
            default:
                advertList = new ArrayList<>(advertDao.getAdvertList(pageNo, pageSize, AdvertDAO.AdvertListType.ADVERT_TYPE, getCompany().getId(), parTypeId));
        }
        getRequest().setAttribute("advertList", advertList);
        
        RequestDispatcher rd;
        rd = getRequest().getRequestDispatcher("/WEB-INF/jsp/AdvertList.jsp");    
        rd.forward(getRequest(), getResponse());
    }

}
