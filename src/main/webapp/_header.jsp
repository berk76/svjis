<%-- 
    Document   : _header
    Created on : 15.6.2011, 16:58:08
    Author     : berk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="company" scope="session" class="cz.svjis.bean.Company" />
<jsp:useBean id="language" scope="session" class="cz.svjis.bean.Language" />
<jsp:useBean id="setup" scope="session" class="java.util.Properties" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta http-equiv="content-language" content="cs" />
    <meta name="robots" content="all,follow" />

    <meta name="author" lang="cs" content="All: WebStones.CZ [www.webstones.cz]; e-mail: jaroslav.beran@gmail.com" />
    <meta name="copyright" lang="cs" content="Webdesign: WebStones.CZ [www.webstones.cz]; e-mail: jaroslav.beran@gmail.com" />

    <% if (setup.get("http.meta.description") != null) { %>
    <meta name="description" content="<%=setup.get("http.meta.description") %>" />
    <% } %>
    <% if (setup.get("http.meta.keywords") != null) { %>
    <meta name="keywords" content="<%=setup.get("http.meta.keywords") %>" />
    <% } %>

    <link rel="stylesheet" media="screen,projection" type="text/css" href="css/reset.css" />
    <link rel="stylesheet" media="screen,projection" type="text/css" href="css/main.css" />
    <!--[if lte IE 6]><link rel="stylesheet" type="text/css" href="css/main-msie.css" /><![endif]-->
    <link rel="stylesheet" media="screen,projection" type="text/css" href="css/style.css" />
    <link rel="stylesheet" media="print" type="text/css" href="css/print.css" />

    <title><%=company.getName() %></title>
    
    <% if (setup.get("google.analytics.id") != null) { %>
    <!-- Google analytics start -->
    <script type="text/javascript">
        var _gaq = _gaq || [];
        _gaq.push(['_setAccount', '<%=setup.get("google.analytics.id") %>']);
        _gaq.push(['_trackPageview']);

        (function() {
          var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
          ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
          var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
        })();
    </script>
    <!-- Google analytics end -->
    <% } %>
</head>

<body>

<div id="main">

    <!-- Header -->
    <div id="header">

        <div id="logo"><a href="Dispatcher" title="[<%=language.getText("Go to homepage") %>]"><span style="background:url('<%=company.getPictureUrl(request.getServletContext().getRealPath("/")) %>') 0 0 no-repeat;"></span></a></div> 
        <div id="company_name"><%=company.getName() %></div>
        <hr class="noscreen" />          

    </div> <!-- /header -->
    