<%-- 
    Document   : Redaction_MiniNewsEdit
    Created on : 30.6.2011, 15:42:30
    Author     : berk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="cz.svjis.bean.Language"%>
<%@page import="cz.svjis.common.JspSnippets"%>
<%@page import="cz.svjis.servlet.Cmd"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Iterator"%>

<jsp:useBean id="language" scope="session" class="cz.svjis.bean.Language" />
<jsp:useBean id="user" scope="session" class="cz.svjis.bean.User" />
<jsp:useBean id="miniNews" scope="request" class="cz.svjis.bean.MiniNews" />
<jsp:useBean id="languageList" scope="request" class="java.util.ArrayList" />
<jsp:useBean id="message" scope="request" class="java.lang.String" />

<jsp:include page="_header.jsp" />
<jsp:include page="_tray.jsp" />

    <!-- Columns -->
    <div id="cols" class="box">

        <!-- Content -->
        <div id="content">
            <div id="content-main">
                <div id="content-main-in">
                    <h1 class="page-title"><%=language.getText("Mini news") %></h1>
                    <strong class="message"><%=message %></strong>
                    <form action="Dispatcher" method="post">
                        <input type="hidden" name="page" value="<%=Cmd.REDACTION_NEWS_SAVE %>">
                        <input type="hidden" name="id" value="<%=miniNews.getId() %>">
                        
                        <p>
                        <%=language.getText("Body") %><br>
                        <textarea
                            class="common-textarea"
                            id="body-textarea"
                            name="body"
                            rows=5 cols=80
                            wrap
                            ><%=miniNews.getBody() %></textarea>
                        </p>
                        
                        <fieldset>
                            <legend><%=language.getText("Properties") %></legend>
                            <p>
                                <label class="common-label" id="lang-label" for="lang-input"><%=language.getText("Language") %></label>
                                <select class="common-input" id="lang-input" name="language">
                                    <%
                                        Iterator langI = languageList.iterator();
                                        while (langI.hasNext()) {
                                            Language l = (Language) langI.next();
                                    %>
                                        <option value="<%=l.getId() %>" <%=(l.getId() == miniNews.getLanguageId()) ? "SELECTED" : "" %>><%=l.getDescription() %></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </p>
                            <p>
                                <label class="common-label" id="time-label" for="time-input"><%=language.getText("Time") %></label>
                                <input class="common-input" id="time-input" type="text" name="time" value="<%=(miniNews.getTime() != null) ? JspSnippets.renderDateTime(miniNews.getTime()) : JspSnippets.renderDateTime(new Date()) %>">
                            </p>
                            <p>
                                <label class="common-label" id="publish-label" for="publish-input"><%=language.getText("Publish article") %></label>
                                <input class="common-input" id="publish-input" type="checkbox" name="publish" <%=(miniNews.isPublished()) ? "checked" : "" %>>
                            </p>
                        </fieldset>
                        <p>
                            <input class="my-button" id="submit" type="submit" value="<%=language.getText("Save") %>" />
                        </p>
                    </form>
                </div> <!-- /content-main-in -->
            </div> <!-- /content-main -->
            <hr class="noscreen" />
        </div> <!-- /content -->

        <jsp:include page="Redaction__menu.jsp" />
    
    </div> <!-- /cols -->

<jsp:include page="_footer.jsp" />
