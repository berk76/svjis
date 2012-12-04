/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.svjis.bean;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author berk
 */
public class Inquiry {
    private int id;
    private int companyId;
    private int userId;
    private String description;
    private Date startingDate;
    private Date endingDate;
    private boolean enabled;
    private ArrayList<InquiryOption> optionList;
    private boolean userCanVote;
    private int count;
    private int maximum;
    
    public Inquiry() {
        clear();
    }
    
    public void clear() {
        id = 0;
        companyId = 0;
        userId = 0;
        description = "";
        startingDate = null;
        endingDate = null;
        enabled = false;
        optionList = new ArrayList<InquiryOption>();
        userCanVote = false;
        count = 0;
        maximum = 0;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the companyId
     */
    public int getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the startingDate
     */
    public Date getStartingDate() {
        return startingDate;
    }

    /**
     * @param startingDate the startingDate to set
     */
    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    /**
     * @return the endingDate
     */
    public Date getEndingDate() {
        return endingDate;
    }

    /**
     * @param endingDate the endingDate to set
     */
    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the optionList
     */
    public ArrayList<InquiryOption> getOptionList() {
        return optionList;
    }

    /**
     * @param optionList the optionList to set
     */
    public void setOptionList(ArrayList<InquiryOption> optionList) {
        this.optionList = optionList;
    }

    /**
     * @return the userCanVote
     */
    public boolean isUserCanVote() {
        return userCanVote;
    }

    /**
     * @param userCanVote the userCanVote to set
     */
    public void setUserCanVote(boolean userCanVote) {
        this.userCanVote = userCanVote;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the maximum
     */
    public int getMaximum() {
        return maximum;
    }

    /**
     * @param maximum the maximum to set
     */
    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }
}