<%-- 
    Document   : ArticleList
    Created on : 15.6.2011, 16:53:57
    Author     : berk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="cz.svjis.bean.Article"%>
<%@page import="cz.svjis.common.JspSnippets"%>
<%@page import="java.util.Iterator"%>

<jsp:useBean id="language" scope="session" class="cz.svjis.bean.Language" />
<jsp:useBean id="slider" scope="request" class="cz.svjis.bean.SliderImpl" />
<jsp:useBean id="articleList" scope="request" class="java.util.ArrayList" />
<jsp:useBean id="sectionId" scope="request" class="java.lang.String" />
<jsp:useBean id="menu" scope="request" class="cz.svjis.bean.Menu" />


<jsp:include page="_header.jsp" />
<jsp:include page="_tray.jsp" />

    <!-- Columns -->
    <div id="cols" class="box">

        <!-- Content -->
        <div id="content">
            <div id="content-left">
                <div id="content-left-in">

                    <!-- Recent Articles -->
                    <h1 class="article-page-title"><%=menu.getActiveSection(language).getDescription() %></h1>
                    
                    <%
                        Iterator articleListI = articleList.iterator();
                        while (articleListI.hasNext()) {
                            Article a = (Article) articleListI.next();
                    %>
                    <!-- Article -->
                    <div class="article box">
                        <div class="article-desc">
                            <h1 class="article-title-list"><a href="Dispatcher?page=articleDetail&id=<%=a.getId() %><%=(request.getParameter("search") != null) ? "&search=" + JspSnippets.encodeUrl(request.getParameter("search")) : "" %>"><%=JspSnippets.highlight(a.getHeader(), request.getParameter("search")) %></a></h1>
                            <p class="info">
                                <a href="Dispatcher?page=articleList&section=<%=a.getMenuNodeId() %>"><%=a.getMenuNodeDescription() %></a>:
                                <%=JspSnippets.renderDate(a.getCreationDate()) %>,
                                <%=language.getText("by:") %> <%=a.getAuthor().getFullName(false) %><%=(a.getNumOfComments() != 0) ? ", " + language.getText("Comments:") + " " + a.getNumOfComments() : "" %></p> 
                            <%=JspSnippets.highlight(a.getDescription(), request.getParameter("search")) %>
                        </div>
                    </div> <!-- /article -->
                    <%
                        }
                    %>

                    <p class="t-left">
                        <%=JspSnippets.renderPaginator(slider, request.getParameter("search"), null, request) %>
                    </p>

                </div> <!-- /content-left-in -->

            </div> <!-- /content-left -->

            <hr class="noscreen" />

            <div id="content-right">
                <jsp:include page="ArticleListInquiry.jsp" />                
                <jsp:include page="ArticleListNews.jsp" />
                <jsp:include page="ArticleListTop.jsp" />
            </div> <!-- /content-right -->

        <hr class="noscreen" />
        </div> <!-- /content -->

        <jsp:include page="Article__menu.jsp" />
    
    </div> <!-- /cols -->

<jsp:include page="_footer.jsp" />
