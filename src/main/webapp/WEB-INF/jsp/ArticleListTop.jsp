<%-- 
    Document   : ArticleListTop
    Created on : 15.6.2011, 16:53:57
    Author     : berk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="cz.svjis.bean.Article"%>
<%@page import="cz.svjis.servlet.Cmd"%>
<%@page import="java.util.List"%>

<jsp:useBean id="language" scope="session" class="cz.svjis.bean.Language" />
<jsp:useBean id="articleTopList" scope="request" class="java.util.ArrayList" />

<!-- Most readed articles -->
 <div class="box-02-top"></div>
 <div class="box-02-top-b box">
     <span class="f-left"><strong><%=language.getText("Most readed articles") %></strong></span>
 </div> <!-- /box-02-top-b -->
 <div class="box-02 box">

     <ul class="mostreaded">
         <%
         for (Article a: (List<Article>) articleTopList) {
         %>
         <li><span class="f-right"><%=a.getNumOfReads() %>&times</span><a href="Dispatcher?page=<%=Cmd.ARTICLE_DETAIL %>&id=<%=a.getId() %>"><%=a.getHeader() %></a></li>
         <%
         }
         %>
     </ul>

 </div> <!-- /box-02 -->
 <div class="box-02-bottom"></div>
 