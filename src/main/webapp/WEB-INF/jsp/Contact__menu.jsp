<%-- 
    Document   : Phonelist__menu
    Created on : 25.6.2011, 14:30:01
    Author     : berk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="cz.svjis.bean.Permission"%>
<%@page import="cz.svjis.servlet.Cmd"%>

<jsp:useBean id="language" scope="session" class="cz.svjis.bean.Language" />
<jsp:useBean id="user" scope="session" class="cz.svjis.bean.User" />

<%
    String p = request.getParameter("page");
    if (p == null) {
        p = Cmd.CONTACT;
    }
%>

<!-- Aside -->
        <div id="aside">

            <div id="aside-top"></div>
            
            <!-- Categories -->
            <div class="padding">
                <h4 class="nom"><%=language.getText("Contact") %>:</h4>
            </div> <!-- /padding -->
            
            <ul class="nav">
                <li <%=(p.startsWith("contact") ? "id=\"nav-active\"" : "") %>><a href="Dispatcher?page=<%=Cmd.CONTACT %>"><%=language.getText("Company") %></a></li>
                <% if (user.hasPermission(Permission.MENU_PHONE_LIST)) { %>
                <li <%=(p.startsWith("phonelist") ? "id=\"nav-active\"" : "") %>><a href="Dispatcher?page=<%=Cmd.PHONE_LIST %>"><%=language.getText("Contact list") %></a></li>
                <% } %>
            </ul>
            
            <div class="padding">
            <jsp:include page="_menu_login.jsp" />
            </div> <!-- /padding -->
            
        <hr class="noscreen" />          
        </div> <!-- /aside -->
        
        <div id="aside-bottom"></div>

