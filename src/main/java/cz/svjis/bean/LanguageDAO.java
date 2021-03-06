/*
 *       LanguageDAO.java
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
public class LanguageDAO extends DAO {
    
    public LanguageDAO (Connection cnn) {
        super(cnn);
    }
    
    public List<Language> getLanguageList() throws SQLException {
        ArrayList<Language> result = new ArrayList<>();
        String select = "SELECT a.ID, a.DESCRIPTION FROM LANGUAGE a ORDER BY a.ID";
        try (Statement st = cnn.createStatement(); ResultSet rs = st.executeQuery(select)) {
            while (rs.next()) {
                Language l = new Language();
                l.setId(rs.getInt("ID"));
                l.setDescription(rs.getString("DESCRIPTION"));
                result.add(l);
            }
        }
        return result;
    }
    
    public Language getDictionary(int languageId) throws SQLException {
        Language result = getLanguage(languageId);
        
        String select2 = "SELECT a.ID, a.TEXT FROM LANGUAGE_DICTIONARY a WHERE a.LANGUAGE_ID = ?";
        try (PreparedStatement ps = cnn.prepareStatement(select2)) {
            ps.setInt(1, languageId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.getPhrases().put(rs.getString("ID"), rs.getString("TEXT"));
                }
            }
        }
        return result;
    }
    
    public Language getLanguage(int languageId) throws SQLException {
        Language result = new Language();
        String select = "SELECT a.ID, a.DESCRIPTION FROM LANGUAGE a WHERE a.ID = ?";
        try (PreparedStatement ps = cnn.prepareStatement(select)) {
            ps.setInt(1, languageId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result.setId(rs.getInt("ID"));
                    result.setDescription(rs.getString("DESCRIPTION"));
                }
            }
        }
        
        return result;
    }
}
