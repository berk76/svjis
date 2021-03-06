<%-- 
    Document   : _menu_login
    Created on : 25.6.2011, 14:35:03
    Author     : berk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="cz.svjis.servlet.Cmd"%>

<jsp:useBean id="user" scope="session" class="cz.svjis.bean.User" />
<jsp:useBean id="language" scope="session" class="cz.svjis.bean.Language" />
                    
                <%
                    if (!user.isUserLogged()) {
                %>
                    <h4 class="margin"><%=language.getText("Login:") %></h4>
                    <form action="Dispatcher" method="post">
                        <input type="hidden" name="page" value="<%=Cmd.LOGIN %>" />
                        <div id="login" class="box">
                            <p><label id="login-label" for="login-name"><%=language.getText("Name") %></label> <input id="login-input" type="text" name="login" size="10" /></p>
                            <p><label id="password-label" for="login-password"><%=language.getText("Password") %></label> <input id="password-input" type="password" name="password" size="10" /></p>
                            <p><input id="login-submit" class="my-button" type="submit" value="<%=language.getText("Log-in") %>" /></p>
                            <p><a href="Dispatcher?page=<%=Cmd.LOST_PWD %>"><%=language.getText("Forgot password?")%></a></p>
                        </div>
                    </form>
                <%
                    } else {
                %>
                   <h4 class="margin"><%=language.getText("User:") %></h4>
                   <form action="Dispatcher" method="post">
                        <input type="hidden" name="page" value="<%=Cmd.LOGOUT %>" />
                        <div id="logout" class="box">
                            <%=user.getFullName(false) %><br><input id="logout-submit" class="my-button" type="submit" value="<%=language.getText("Logout") %>" />
                        </div>
                   </form>
                <%
                    }
                %>
