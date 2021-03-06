/*
 *       CompanyDAO.java
 *
 *       This file is part of SVJIS project.
 *       https://github.com/svjis/svjis
 *
 *       SVJIS is free software; you can redistribute it and/or modify
 *       it under the terms of the GNU General Public License as published by
 *       the Free Software Foundation; either version 3 of the License, or
 *       (at your option) any later version. <http://www.gnu.org/licenses/>
 */

package cz.svjis.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author berk
 */
public class CompanyDAO extends DAO {

    public CompanyDAO (Connection cnn) {
        super(cnn);
    }
    
    public List<Company> getCompanyList() throws SQLException {
        ArrayList<Company> result = new ArrayList<>();
        
        String select = "SELECT "
                + "a.ID, "
                + "a.NAME, "
                + "a.ADDRESS, "
                + "a.CITY, "
                + "a.POST_CODE, "
                + "a.PHONE, "
                + "a.FAX, "
                + "a.E_MAIL, "
                + "a.REGISTRATION_NO, "
                + "a.VAT_REGISTRATION_NO, "
                + "a.DATABASE_CREATION_DATE, "
                + "a.INTERNET_DOMAIN "
                + "FROM COMPANY a "
                + "ORDER BY a.NAME collate UNICODE_CI_AI";
        
        try (Statement st = cnn.createStatement(); ResultSet rs = st.executeQuery(select)) {
            while (rs.next()) {
                Company c = new Company();
                c.setId(rs.getInt("ID"));
                c.setName(rs.getString("NAME"));
                c.setAddress(rs.getString("ADDRESS"));
                c.setCity(rs.getString("CITY"));
                c.setPostCode(rs.getString("POST_CODE"));
                c.setPhone(rs.getString("PHONE"));
                c.setFax(rs.getString("FAX"));
                c.seteMail(rs.getString("E_MAIL"));
                c.setRegistrationNo(rs.getString("REGISTRATION_NO"));
                c.setVatRegistrationNo(rs.getString("VAT_REGISTRATION_NO"));
                c.setDatabaseCreationDate(rs.getDate("DATABASE_CREATION_DATE"));
                c.setInternetDomain(rs.getString("INTERNET_DOMAIN"));
                result.add(c);
            }
        }
        return result;
    }
    
    public Company getCompany(int id) throws SQLException {
        Company result = null;
        
        String select = "SELECT "
                + "a.ID, "
                + "a.NAME, "
                + "a.ADDRESS, "
                + "a.CITY, "
                + "a.POST_CODE, "
                + "a.PHONE, "
                + "a.FAX, "
                + "a.E_MAIL, "
                + "a.REGISTRATION_NO, "
                + "a.VAT_REGISTRATION_NO, "
                + "a.DATABASE_CREATION_DATE, "
                + "a.INTERNET_DOMAIN, "
                + "a.PICTURE_CONTENT_TYPE, "
                + "a.PICTURE_FILENAME, "
                + "a.PICTURE_DATA, "
                + "(SELECT count(*) FROM BUILDING b left join BUILDING_ENTRANCE en on (en.BUILDING_ID = b.ID) where (b.COMPANY_ID = a.ID) AND (en.ID IS NOT null)) AS ENTRANCE_CNT, "
                + "(SELECT count(*) FROM BUILDING b left join BUILDING_UNIT u on (u.BUILDING_ID = b.ID) where (b.COMPANY_ID = a.ID) AND (u.ID IS NOT null)) AS UNIT_CNT, "
                + "(SELECT count(*) FROM BOARD_MEMBER m LEFT JOIN \"USER\" u ON u.ID = m.USER_ID WHERE u.COMPANY_ID = a.ID) AS BOARD_CNT, "
                + "(SELECT count(*) FROM \"USER\" u where (u.COMPANY_ID = a.ID) AND (u.ENABLED = 1)) AS USER_CNT, "
                + "(SELECT count(*) FROM \"ROLE\" r where (r.COMPANY_ID = a.ID)) AS ROLE_CNT, "
                + "(SELECT count(*) FROM MESSAGE_QUEUE m WHERE (m.COMPANY_ID = a.ID) AND (m.STATUS = 0)) AS MESSAGE_CNT "
                + "FROM COMPANY a "
                + "WHERE a.ID = ?";
        
        try (PreparedStatement ps = cnn.prepareStatement(select)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = new Company();
                    result.setId(rs.getInt("ID"));
                    result.setName(rs.getString("NAME"));
                    result.setAddress(rs.getString("ADDRESS"));
                    result.setCity(rs.getString("CITY"));
                    result.setPostCode(rs.getString("POST_CODE"));
                    result.setPhone(rs.getString("PHONE"));
                    result.setFax(rs.getString("FAX"));
                    result.seteMail(rs.getString("E_MAIL"));
                    result.setRegistrationNo(rs.getString("REGISTRATION_NO"));
                    result.setVatRegistrationNo(rs.getString("VAT_REGISTRATION_NO"));
                    result.setDatabaseCreationDate(rs.getDate("DATABASE_CREATION_DATE"));
                    result.setInternetDomain(rs.getString("INTERNET_DOMAIN"));
                    result.setPictureContentType(rs.getString("PICTURE_CONTENT_TYPE"));
                    result.setPictureFilename(rs.getString("PICTURE_FILENAME"));
                    result.setPictureData(rs.getBytes("PICTURE_DATA"));
                    result.setEntranceCnt(rs.getInt("ENTRANCE_CNT"));
                    result.setUnitCnt(rs.getInt("UNIT_CNT"));
                    result.setBoardCnt(rs.getInt("BOARD_CNT"));
                    result.setUserCnt(rs.getInt("USER_CNT"));
                    result.setRoleCnt(rs.getInt("ROLE_CNT"));
                    result.setMessageCnt(rs.getInt("MESSAGE_CNT"));
                }
            }
        }
        return result;
    }
    
    public Company getCompanyByDomain(String domain) throws SQLException {
        Company result = null;
        
        String select = "SELECT "
                + "a.ID, "
                + "a.NAME, "
                + "a.ADDRESS, "
                + "a.CITY, "
                + "a.POST_CODE, "
                + "a.PHONE, "
                + "a.FAX, "
                + "a.E_MAIL, "
                + "a.REGISTRATION_NO, "
                + "a.VAT_REGISTRATION_NO, "
                + "a.DATABASE_CREATION_DATE, "
                + "a.INTERNET_DOMAIN, "
                + "a.PICTURE_CONTENT_TYPE, "
                + "a.PICTURE_FILENAME, "
                + "a.PICTURE_DATA, "
                + "(SELECT count(*) FROM BUILDING b left join BUILDING_UNIT u on (u.BUILDING_ID = b.ID) where (b.COMPANY_ID = a.ID) AND (u.ID IS NOT null)) AS UNIT_CNT, "
                + "(SELECT count(*) FROM \"USER\" u where (u.COMPANY_ID = a.ID)) AS USER_CNT, "
                + "(SELECT (count(*)) FROM \"ROLE\" r where (r.COMPANY_ID = a.ID)) AS ROLE_CNT, "
                + "(SELECT count(*) FROM BOARD_MEMBER m LEFT JOIN \"USER\" u ON u.ID = m.USER_ID WHERE u.COMPANY_ID = a.ID) AS BOARD_CNT "
                + "FROM COMPANY a "
                + "WHERE a.INTERNET_DOMAIN collate UNICODE_CI_AI = ?";
        
        try (PreparedStatement ps = cnn.prepareStatement(select)) {
            ps.setString(1, domain);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = new Company();
                    result.setId(rs.getInt("ID"));
                    result.setName(rs.getString("NAME"));
                    result.setAddress(rs.getString("ADDRESS"));
                    result.setCity(rs.getString("CITY"));
                    result.setPostCode(rs.getString("POST_CODE"));
                    result.setPhone(rs.getString("PHONE"));
                    result.setFax(rs.getString("FAX"));
                    result.seteMail(rs.getString("E_MAIL"));
                    result.setRegistrationNo(rs.getString("REGISTRATION_NO"));
                    result.setVatRegistrationNo(rs.getString("VAT_REGISTRATION_NO"));
                    result.setDatabaseCreationDate(rs.getDate("DATABASE_CREATION_DATE"));
                    result.setInternetDomain(rs.getString("INTERNET_DOMAIN"));
                    result.setPictureContentType(rs.getString("PICTURE_CONTENT_TYPE"));
                    result.setPictureFilename(rs.getString("PICTURE_FILENAME"));
                    result.setPictureData(rs.getBytes("PICTURE_DATA"));
                    result.setUnitCnt(rs.getInt("UNIT_CNT"));
                    result.setUserCnt(rs.getInt("USER_CNT"));
                    result.setRoleCnt(rs.getInt("ROLE_CNT"));
                    result.setBoardCnt(rs.getInt("BOARD_CNT"));
                }
            }
        }
        return result;
    }
    
    public void modifyCompany(Company c) throws SQLException {
        String update = "UPDATE COMPANY SET "
                + "NAME = ?, "
                + "ADDRESS = ?, "
                + "CITY = ?, "
                + "POST_CODE = ?, "
                + "PHONE = ?, "
                + "FAX = ?, "
                + "E_MAIL = ?, "
                + "REGISTRATION_NO = ?, "
                + "VAT_REGISTRATION_NO = ?, "
                + "INTERNET_DOMAIN = ? "
                + "WHERE (ID = ?)";
        
        try (PreparedStatement ps = cnn.prepareStatement(update)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getAddress());
            ps.setString(3, c.getCity());
            ps.setString(4, c.getPostCode());
            ps.setString(5, c.getPhone());
            ps.setString(6, c.getFax());
            ps.setString(7, c.geteMail());
            ps.setString(8, c.getRegistrationNo());
            ps.setString(9, c.getVatRegistrationNo());
            ps.setString(10, c.getInternetDomain());
            ps.setInt(11, c.getId());
            ps.executeUpdate();
        }
    }
    
    public void savePicture(int companyId, String contentType, String fileName, byte[] data) throws SQLException {
        String update = "UPDATE COMPANY SET "
                + "PICTURE_CONTENT_TYPE = ?, "
                + "PICTURE_FILENAME = ?, "
                + "PICTURE_DATA = ? "
                + "WHERE ID = ?";
        try (PreparedStatement ps = cnn.prepareStatement(update)) {
            ps.setString(1, contentType);
            ps.setString(2, fileName);
            ps.setBytes(3, data);
            ps.setInt(4, companyId);
            ps.execute();
        }
    }
}
