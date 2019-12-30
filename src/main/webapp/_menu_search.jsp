<%-- 
    Document   : _menu_search
    Created on : 30.12.2019, 10:58:12
    Author     : jarberan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="language" scope="session" class="cz.svjis.bean.Language" />

                <h4 class="margin"><%=language.getText("Search:") %></h4>

                <form action="Dispatcher" method="get">
                    <input type="hidden" name="page" value="search" />
                    <div id="search" class="box">
                        <input type="text" size="20" id="search-input" name="search" value="<%=(request.getParameter("search") != null) ? request.getParameter("search") : "" %>" /><input type="submit" id="search-submit" value="<%=language.getText("Search") %>" />
                    </div> <!-- /search -->
                </form>