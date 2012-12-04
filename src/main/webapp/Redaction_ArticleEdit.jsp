<%-- 
    Document   : Redaction_ArticleEdit
    Created on : 23.6.2011, 16:03:00
    Author     : berk
--%>

<%@page import="java.io.File"%>
<%@page import="cz.svjis.bean.ArticleAttachment"%>
<%@page import="cz.svjis.bean.Role"%>
<%@page import="cz.svjis.bean.Language"%>
<%@page import="cz.svjis.bean.MenuItem"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="language" scope="session" class="cz.svjis.bean.Language" />
<jsp:useBean id="user" scope="session" class="cz.svjis.bean.User" />
<jsp:useBean id="article" scope="request" class="cz.svjis.bean.Article" />
<jsp:useBean id="languageList" scope="request" class="java.util.ArrayList" />
<jsp:useBean id="roleList" scope="request" class="java.util.ArrayList" />
<jsp:useBean id="menuNodeList" scope="request" class="java.util.ArrayList" />

<jsp:include page="_header.jsp" />
<jsp:include page="_tray.jsp" />

<%
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
%>

    <!-- Columns -->
    <div id="cols" class="box">

        <!-- Content -->
        <div id="content">
            <div id="content-main">
                <div id="content-main-in">
                    <h2><%=language.getText("Article") %></h2>
                    <form action="Dispatcher" method="post">
                        <input type="hidden" name="page" value="redactionArticleSave">
                        <input type="hidden" name="id" value="<%=article.getId() %>">
                        <input type="hidden" name="authorId" value="<%=article.getAuthorId() %>">
                        <input type="hidden" name="creationDate" value="<%=sdf.format(article.getCreationDate()) %>">

                        <p>
                            <%=language.getText("Header") %><br>
                            <input id="common-input" type="text" name="header" maxlength="50" size="100" value="<%=article.getHeader() %>">
                        </p>
                        
                        <p>
                        <%=language.getText("Description") %><br>
                        <textarea
                            name="description"
                            id="common-textarea"
                            rows=10 cols=80
                            wrap
                            ><%=article.getDescription() %></textarea>
                        </p>
                        <p>
                        <%=language.getText("Body") %><br>
                        <textarea
                            name="body"
                            id="common-textarea"
                            rows=20 cols=80
                            wrap
                            ><%=article.getBody() %></textarea>
                        </p>
                        <fieldset>
                            <legend><%=language.getText("Properties") %></legend>
                            <p>
                                <label id="common-label" for="common-input"><%=language.getText("Menu") %></label>
                                <select name="menuId" id="common-input">
                                    <%
                                        Iterator menuI = menuNodeList.iterator();
                                        while (menuI.hasNext()) {
                                            MenuItem m = (MenuItem) menuI.next();
                                    %>
                                        <option value="<%=m.getSection().getId() %>" <%=(m.getSection().getId() == article.getMenuNodeId()) ? "SELECTED" : "" %>><%=m.getSection().getDescription() %></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </p>
                            <p>
                                <label id="common-label" for="common-input"><%=language.getText("Language") %></label>
                                <select name="language" id="common-input">
                                    <%
                                        Iterator langI = languageList.iterator();
                                        while (langI.hasNext()) {
                                            Language l = (Language) langI.next();
                                    %>
                                        <option value="<%=l.getId() %>" <%=(l.getId() == article.getLanguageId()) ? "SELECTED" : "" %>><%=l.getDescription() %></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </p>
                            <p>
                                <label id="common-label" for="common-input"><%=language.getText("Enable comments") %></label>
                                <input id="common-input" type="checkbox" name="commentsAllowed" <%=(article.isCommentsAllowed()) ? "checked" : "" %>>
                            </p>
                            <p>
                                <label id="common-label" for="common-input"><%=language.getText("Publish article") %></label>
                                <input id="common-input" type="checkbox" name="publish" <%=(article.isPublished()) ? "checked" : "" %>>
                            </p>
                        </fieldset>
                        <fieldset>
                            <legend><%=language.getText("Visible for") %></legend>
                            <%
                                Iterator roleI = roleList.iterator();
                                while (roleI.hasNext()) {
                                    Role r = (Role) roleI.next();
                            %>
                            <p>
                                <label id="common-label" for="common-input"><%=r.getDescription() %></label>
                                <input id="common-input" type="checkbox" name="r_<%=r.getId() %>" <%=(article.getRoles().get(new Integer(r.getId())) != null) ? "checked" : "" %> />
                            </p>
                            <%
                                }
                            %>        
                        </fieldset>
                        <p>
                            <input type="submit" value="<%=language.getText("Save") %>" />
                        </p>
                    </form>
                        
                    <% if (article.getId() != 0) { %>
                    <form action="Dispatcher?page=redactionArticleAttachmentSave&articleId=<%=article.getId() %>" enctype="multipart/form-data" method="post">
                        <fieldset>
                            <legend><%=language.getText("Attachments") %></legend>
                            <%
                                if ((article.getAttachmentList() != null) && (article.getAttachmentList().size() != 0)) {
                            %>
                            <p>
                            <table class="list">
                                <tr>
                                    <th class="list" colspan="3"><%=language.getText("File") %></th>
                                </tr>
                                <%
                                Iterator<ArticleAttachment> attachI = article.getAttachmentList().iterator();
                                while (attachI.hasNext()) {
                                    ArticleAttachment a = attachI.next();
                                    String icon = "gfx/Files_unknown.gif";
                                    String extension = a.getFileName().toLowerCase().substring(a.getFileName().lastIndexOf(".") + 1);
                                    File f = new File(request.getServletContext().getRealPath("/gfx") + "/Files_" + extension + ".gif");
                                    if (f.exists()) {
                                        icon = "gfx/Files_" + extension + ".gif";
                                    }
                                %>
                                <tr>
                                    <td class="list"><img src="<%=icon%>" border="0"></td>
                                    <td class="list"><a href="Upload?page=download&id=<%=a.getId() %>"><%=a.getFileName() %></a></td>
                                    <td class="list"><a onclick="if (!confirm('<%=language.getText("Really do you want to remove attachment") %> <%=a.getFileName() %> ?')) return false;" href="Dispatcher?page=redactionArticleAttachmentDelete&id=<%=a.getId() %>&articleId=<%=a.getArticleId() %>"><%=language.getText("Delete") %></a></td>
                                </tr>
                                <%
                                }
                                %>
                            </table>
                            <p>
                            <%
                               }
                            %>
                            <p>
                                <input type="file" name="attachment" size="40">
                                <input type="submit" value="<%=language.getText("Insert attachment") %>">
                            </p>
                        </fieldset>
                    </form>
                    <% } %>
                        
                </div> <!-- /content-main-in -->
            </div> <!-- /content-main -->
            <hr class="noscreen" />
        </div> <!-- /content -->

        <jsp:include page="Redaction__menu.jsp" />
    
    </div> <!-- /cols -->

<jsp:include page="_footer.jsp" />