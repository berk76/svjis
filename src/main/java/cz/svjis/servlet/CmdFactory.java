/*
 *       CmdFactory.java
 *
 *       This file is part of SVJIS project.
 *       https://github.com/svjis/svjis
 *
 *       SVJIS is free software; you can redistribute it and/or modify
 *       it under the terms of the GNU General Public License as published by
 *       the Free Software Foundation; either version 3 of the License, or
 *       (at your option) any later version. <http://www.gnu.org/licenses/>
 */

package cz.svjis.servlet;

import cz.svjis.bean.Permission;
import cz.svjis.servlet.cmd.*;


/**
 *
 * @author jaroslav_b
 */
public class CmdFactory {
    
    public static final String ADVERT_LIST = "advertList";
    public static final String ADVERT_EDIT = "advertEdit";
    public static final String ADVERT_SAVE = "advertSave";
    public static final String ADVERT_ATTACHMENT_SAVE = "advertAttachmentSave";
    public static final String ADVERT_ATTACHMENT_DELETE = "advertAttachmentDelete";

    private CmdFactory() {}
    
    public static Command create(String page, CmdContext ctx) {

        // *****************
        // * Login         *
        // *****************
        if (page.equals("login")) {
            return new LoginCmd(ctx);
        }
        
        if (page.equals("logout")) {
            return new LogoutCmd(ctx);
        }

        // *****************
        // * Lost login    *
        // *****************
        if (page.equals("lostPassword")) {
            return new LostPasswordCmd(ctx);
        }

        if (page.equals("lostPassword_submit")) {
            return new LostPasswordSubmitCmd(ctx);
        }

        // *****************
        // * Article       *
        // *****************
        if (ctx.getUser().hasPermission(Permission.MENU_ARTICLES)) {

            if (page.equals("articleList") || page.equals("search")) {
                return new ArticleListCmd(ctx);
            }

            if (page.equals("inquiryVote")) {
                return new ArticleInquiryVoteCmd(ctx);
            }

            if (page.equals("articleDetail")) {
                return new ArticleDetailCmd(ctx);
            }

            if (page.equals("insertArticleComment")) {
                return new ArticleInsertCommentCmd(ctx);
            }
            
            if (page.equals("articleFast")) {
                return new ArticleFastCmd(ctx);
            }
        }

        // *****************
        // * Contact       *
        // *****************
        if (ctx.getUser().hasPermission(Permission.MENU_CONTACT)) {

            if (page.equals("contact")) {
                return new ContactCompanyCmd(ctx);
            }

            if (page.equals("phonelist")) {
                return new ContactPhoneListCmd(ctx);
            }
        }

        // ***********************
        // * My bulilding units  *
        // ***********************
        if (ctx.getUser().hasPermission(Permission.MENU_BUILDING_UNITS)) {
            if (page.equals("myBuildingUnitList")) {
                return new MyBuildingUnitsCmd(ctx);
            }
        }

        // **********************
        // * Personal settings  *
        // **********************
        if (ctx.getUser().hasPermission(Permission.MENU_PERSONAL_SETTINGS)) {
            if (page.equals("psUserDetail")) {
                return new PersonalUserDetailCmd(ctx);
            }

            if (page.equals("psUserDetailSave")) {
                return new PersonalUserDetailSaveCmd(ctx);
            }

            if (page.equals("psPasswordChange")) {
                return new PersonalPasswordChangeCmd(ctx);
            }
            if (page.equals("psPasswordChangeSave")) {
                return new PersonalPasswordChangeSaveCmd(ctx);
            }
        }

        // *****************
        // * Redaction     *
        // *****************
        if (ctx.getUser().hasPermission(Permission.MENU_REDACTION)) {
            if (page.equals("redactionArticleList") || page.equals("redactionArticleSearch")) {
                return new RedactionArticleListCmd(ctx);
            }

            if (page.equals("redactionArticleEdit")) {
                return new RedactionArticleEditCmd(ctx);
            }

            if (page.equals("redactionArticleSave")) {
                return new RedactionArticleSaveCmd(ctx);
            }

            if (page.equals("redactionArticleSendNotifications")) {
                return new RedactionArticleSendNotificationsCmd(ctx);
            }

            if (page.equals("redactionArticleSendNotificationsConfirmation")) {
                return new RedactionArticleSendNotificationsConfirmationCmd(ctx);
            }

            if (page.equals("redactionArticleAttachmentSave")) {
                return new RedactionArticleAttachmentSaveCmd(ctx);
            }
            if (page.equals("redactionArticleAttachmentDelete")) {
                return new RedactionArticleAttachmentDeleteCmd(ctx);
            }

            if (page.equals("redactionNewsList")) {
                return new RedactionNewsListCmd(ctx);
            }
            if (page.equals("redactionNewsEdit")) {
                return new RedactionNewsEditCmd(ctx);
            }
            if (page.equals("redactionNewsEditSave")) {
                return new RedactionNewsEditSaveCmd(ctx);
            }
            if (page.equals("redactionNewsDelete")) {
                return new RedactionNewsDeleteCmd(ctx);
            }

            if (page.equals("redactionInquiryList")) {
                return new RedactionInquiryListCmd(ctx);
            }
            if (page.equals("redactionInquiryLog")) {
                return new RedactionInquiryLogCmd(ctx);
            }
            if (page.equals("redactionInquiryEdit")) {
                return new RedactionInquiryEditCmd(ctx);
            }
            if (page.equals("redactionInquirySave")) {
                return new RedactionInquirySaveCmd(ctx);
            }
            if (page.equals("redactionInquiryOptionDelete")) {
                return new RedactionInquiryOptionDeleteCmd(ctx);
            }

            if (page.equals("redactionArticleMenu")) {
                return new RedactionArticleMenuCmd(ctx);
            }
            if (page.equals("redactionArticleMenuEdit")) {
                return new RedactionArticleMenuEditCmd(ctx);
            }
            if (page.equals("redactionArticleMenuSave")) {
                return new RedactionArticleMenuSaveCmd(ctx);
            }
            if (page.equals("redactionArticleMenuDelete")) {
                return new RedactionArticleMenuDeleteCmd(ctx);
            }
        }
        
        // *******************
        // * Fault reporting *
        // *******************
        if (ctx.getUser().hasPermission(Permission.MENU_FAULT_REPORTING)) {
            if (page.startsWith("faultReportingList")) {
                return new FaultReportingListCmd(ctx);
            }

            if (page.equals("faultReportingEdit")) {
                return new FaultReportingEditCmd(ctx);
            }

            if (page.equals("faultReportingSave")) {
                return new FaultReportingSaveCmd(ctx);
            }
            
            if (page.equals("faultDetail")) {
                return new FaultReportingDetailCmd(ctx);
            }
            
            if (page.equals("faultInsertComment")) {
                return new FaultReportingInsertCommentCmd(ctx);
            }
            
            if (page.equals("faultReportingFast")) {
                return new FaultReportingFastCmd(ctx);
            }
            
            if (page.equals("faultReportingAttachmentSave")) {
                return new FaultReportingAttachmentSaveCmd(ctx);
            }
            
            if (page.equals("faultReportingAttachmentDelete")) {
                return new FaultReportingAttachmentDeleteCmd(ctx);
            }
        }
        
        // *******************
        // * Adverts         *
        // *******************
        if (ctx.getUser().hasPermission(Permission.MENU_ADVERTS)) {
            if (page.equals(ADVERT_LIST)) {
                return new AdvertListCmd(ctx);
            }
            
            if (page.equals(ADVERT_EDIT)) {
                return new AdvertEditCmd(ctx);
            }
            
            if (page.equals(ADVERT_SAVE)) {
                return new AdvertSaveCmd(ctx);
            }
            
            if (page.equals(ADVERT_ATTACHMENT_SAVE)) {
                return new AdvertAttachmentSaveCmd(ctx);
            }
            
            if (page.equals(ADVERT_ATTACHMENT_DELETE)) {
                return new AdvertAttachmentDeleteCmd(ctx);
            }
            
        }
        
        // ******************
        // * Administration *
        // ******************
        if (ctx.getUser().hasPermission(Permission.MENU_ADMINISTRATION)) {
            if (page.equals("companyDetail")) {
                return new CompanyDetailCmd(ctx);
            }

            if (page.equals("companySave")) {
                return new CompanySaveCmd(ctx);
            }

            if (page.equals("buildingDetail")) {
                return new BuildingDetailCmd(ctx);
            }

            if (page.equals("buildingSave")) {
                return new BuildingSaveCmd(ctx);
            }

            if (page.equals("buildingPictureSave")) {
                return new BuildingPictureSaveCmd(ctx);
            }

            if (page.equals("buildingEntranceList")) {
                return new BuildingEntranceListCmd(ctx);
            }
            
            if (page.equals("buildingEntranceEdit")) {
                return new BuildingEntranceEditCmd(ctx);
            }
            
            if (page.equals("buildingEntranceSave")) {
                return new BuildingEntranceSaveCmd(ctx);
            }
            
            if (page.equals("buildingEntranceDelete")) {
                return new BuildingEntranceDeleteCmd(ctx);
            }
            
            if (page.equals("buildingUnitList")) {
                return new BuildingUnitListCmd(ctx);
            }

            if (page.equals("buildingUnitEdit")) {
                return new BuildingUnitEditCmd(ctx);
            }

            if (page.equals("buildingUnitSave")) {
                return new BuildingUnitSaveCmd(ctx);
            }

            if (page.equals("buildingUnitDelete")) {
                return new BuildingUnitDeleteCmd(ctx);
            }

            if (page.equals("buildingUnitOwner")) {
                return new BuildingUnitOwnerCmd(ctx);
            }
            
            if (page.equals("boardMemberList")) {
                return new BoardListCmd(ctx);
            }
            
            if (page.equals("boardMemberEdit")) {
                return new BoardEditCmd(ctx);
            }
            
            if (page.equals("boardMemberSave")) {
                return new BoardSaveCmd(ctx);
            }
            
            if (page.equals("boardMemberDelete")) {
                return new BoardDeleteCmd(ctx);
            }

            if (page.equals("userList")) {
                return new UserListCmd(ctx);
            }

            if (page.equals("userEdit")) {
                return new UserEditCmd(ctx);
            }

            if (page.equals("userSave")) {
                return new UserSaveCmd(ctx);
            }

            if (page.equals("userDelete")) {
                return new UserDeleteCmd(ctx);
            }

            if (page.equals("userBuildingUnits")) {
                return new UserBuildingUnitsCmd(ctx);
            }

            if (page.equals("userBuildingUnitAdd")) {
                return new UserBuildingUnitAddCmd(ctx);
            }

            if (page.equals("userBuildingUnitRemove")) {
                return new UserBuildingUnitRemoveCmd(ctx);
            }

            if (page.equals("roleList")) {
                return new RoleListCmd(ctx);
            }

            if (page.equals("roleEdit")) {
                return new RoleEditCmd(ctx);
            }

            if (page.equals("roleSave")) {
                return new RoleSaveCmd(ctx);
            }

            if (page.equals("roleDelete")) {
                return new RoleDeleteCmd(ctx);
            }

            if (page.equals("propertyList")) {
                return new PropertyListCmd(ctx);
            }

            if (page.equals("propertyEdit")) {
                return new PropertyEditCmd(ctx);
            }

            if (page.equals("propertySave")) {
                return new PropertySaveCmd(ctx);
            }

            if (page.equals("propertyDelete")) {
                return new PropertyDeleteCmd(ctx);
            }

            if (page.equals("messagesPending")) {
                return new MessagesPendingCmd(ctx);
            }
        }

        // ************************
        // * Page does not exists *
        // ************************
        return new Error404NotFoundCmd(ctx);
    }
}
