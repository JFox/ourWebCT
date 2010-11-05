
<!--
Design by Free CSS Templates
http://www.freecsstemplates.org
Released for free under a Creative Commons Attribution 2.5 License

Name       : Zion Narrows  
Description: A two-column, fixed-width design with dark color scheme.
Version    : 1.0
Released   : 20102110

-->
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Zion Narrows   by Free CSS Templates</title>
<link href="style.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
<div id="wrapper">
	<div id="header">
		<div id="logo">
			<h1><a href="#">OurWebCT   </a></h1>
		</div>
	</div>
	<!-- end #header -->
	<div id="page">
	<div id="page-bgtop">
	<div id="page-bgbtm">
		<div id="content">
			<div class="post">
				<h2 class="title"><a href="#">Welcome to ourWebCT   </a></h2>
			  <div class="entry">
				<p>&nbsp;</p>
				  <form id="form1" method="post" action="index.jsp">
				    <div align="left">
				      <table width="200" border="1">
				        <tr>
				          <td width="66">Id:</td>
				          <td width="118"><label for="txtUser"></label>
			              <input type="text" name="txtId" id="txtId" /></td>
			            </tr>
				        <tr>
				          <td>Password:</td>
				          <td><label for="txtPassword"></label>
			              <input type="text" name="txtPassword" id="txtPassword" /></td>
			            </tr>
                                      <%
                                      String str = request.getParameter("message");
                                      if(str != null){
                                          %>
                                        <tr>
                                            <td colspan="2"><div align="center">ERROR: <%out.print(str);%> </div></td>
                                        </tr>
                                      <%
                                      }
                                      %>
				        <tr>
				          <td colspan="2"><div align="center">
				            <input type="submit" name="btnLogin" id="btnLogin" value="Login" />
				          </div></td>
			            </tr>
			          </table>
				    </div>
				  </form>
				  <p>&nbsp;</p>
</div>
			</div>
			
			
		<div style="clear: both;">&nbsp;</div>
		</div>
		<!-- end #content -->
		<div id="sidebar">
			<ul>
				<li>
					<h2>Log In</h2>
					<p>In this page you can log into the institution portal.</p>
				</li>
			</ul>
		</div>
		<!-- end #sidebar -->
		<div style="clear: both;">&nbsp;</div>
	</div>
	</div>
	</div>
	<!-- end #page -->
</div>
	<div id="footer">
		<p>Copyright (c) 2010 Sitename.com. All rights reserved. Design by <a href="http://www.freecsstemplates.org/">Free CSS Templates</a></p>
	</div>
	<!-- end #footer -->
</body>
</html>
