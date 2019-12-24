<%-- 
    Document   : Faults_reportList
    Created on : 20.12.2019, 10:34:31
    Author     : jarberan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="cz.svjis.bean.FaultReport"%>

<jsp:useBean id="language" scope="session" class="cz.svjis.bean.Language" />
<jsp:useBean id="user" scope="session" class="cz.svjis.bean.User" />
<jsp:useBean id="reportList" scope="request" class="java.util.ArrayList" />

<jsp:include page="_header.jsp" />
<jsp:include page="_tray.jsp" />

    <!-- Columns -->
    <div id="cols" class="box">

        <!-- Content -->
        <div id="content">
            <div id="content-main">
                <div id="content-main-in">
                    <h1 class="page-title"><%=language.getText("Fault reporting") %></h1>
                    <% if (user.hasPermission("fault_reporting_reporter")) { %>
                    [<a href="Dispatcher?page=faultReportingEdit&id=0"><%=language.getText("Report new fault") %></a>]<br>
                    <% } %>
                    <table width="100%" class="list">
                        <tr>
                            <th class="list">&nbsp;</th>
                            <th class="list"><%=language.getText("Ticket id") %></th>
                            <th class="list"><%=language.getText("Date") %></th>
                            <th class="list"><%=language.getText("Subject") %></th>
                            <th class="list"><%=language.getText("User") %></th>
                            <th class="list"><%=language.getText("Resolver") %></th>
                            <th class="list"><%=language.getText("Closed") %></th>
                        </tr>
                        <%
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                        for (FaultReport f : (ArrayList<FaultReport>) reportList) {
                            String stl = (f.isClosed()) ? "background-color:#d0d0d0;text-decoration: line-through;" : "";
                        %>
                        <tr>
                            <td class="list" style="<%=stl %>"><a href="Dispatcher?page=faultDetail&id=<%=f.getId() %>"><img src="gfx/find.png" border="0" title="View"></a></td>
                            <td class="list" style="<%=stl %>text-align: right;"><%=f.getId() %></td>
                            <td class="list" style="<%=stl %>"><%=sdf.format(f.getCreationDate()) %></td>
                            <td class="list" style="<%=stl %>"><%=f.getSubject() %></td>
                            <td class="list" style="<%=stl %>"><%=f.getCreatedByUser().getFirstName() %>&nbsp;<%=f.getCreatedByUser().getLastName() %></td>
                            <td class="list" style="<%=stl %>"><%=(f.getAssignedToUser() != null) ? (f.getAssignedToUser().getFirstName() + "&nbsp;" +f.getAssignedToUser().getLastName()) : "&nbsp;" %></td>
                            <td class="list" style="<%=stl %>"><%=(f.isClosed()) ? language.getText("yes") : language.getText("no") %></td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
                </div> <!-- /content-main-in -->
            </div> <!-- /content-main -->
            <hr class="noscreen" />
        </div> <!-- /content -->

        <jsp:include page="Faults__menu.jsp" />
    
    </div> <!-- /cols -->

<jsp:include page="_footer.jsp" />
