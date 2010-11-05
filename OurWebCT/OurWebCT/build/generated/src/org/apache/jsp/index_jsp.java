package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import Model.Persona;
import Controller.EntityController.PersonaJpaController;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.Vector _jspx_dependants;

  private org.apache.jasper.runtime.ResourceInjector _jspx_resourceInjector;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.apache.jasper.runtime.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!--\n");
      out.write("  Copyright (c) 2007, Sun Microsystems, Inc. All rights reserved.\n");
      out.write("\n");
      out.write("  Redistribution and use in source and binary forms, with or without\n");
      out.write("  modification, are permitted provided that the following conditions are met:\n");
      out.write("\n");
      out.write("  * Redistributions of source code must retain the above copyright notice,\n");
      out.write("    this list of conditions and the following disclaimer.\n");
      out.write("\n");
      out.write("  * Redistributions in binary form must reproduce the above copyright notice,\n");
      out.write("    this list of conditions and the following disclaimer in the documentation\n");
      out.write("    and/or other materials provided with the distribution.\n");
      out.write("\n");
      out.write("  * Neither the name of Sun Microsystems, Inc. nor the names of its contributors\n");
      out.write("    may be used to endorse or promote products derived from this software without\n");
      out.write("    specific prior written permission.\n");
      out.write("\n");
      out.write("  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\"\n");
      out.write("  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE\n");
      out.write("  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE\n");
      out.write("  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE\n");
      out.write("  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR\n");
      out.write("  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF\n");
      out.write("  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS\n");
      out.write("  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN\n");
      out.write("  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)\n");
      out.write("  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF\n");
      out.write("  THE POSSIBILITY OF SUCH DAMAGE.\n");
      out.write("-->\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write('\n');
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n");
      out.write("   \"http://www.w3.org/TR/html4/loose.dtd\">\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("    </head>\n");
      out.write("    <body\n");
      out.write("        ");

            if(request.getParameter("txtId") != null){
                try{
                    PersonaJpaController m = new PersonaJpaController();
                    Persona p = m.findPersona(Integer.parseInt(request.getParameter("txtId")));

                    if(request.getParameter("txtPassword").equals(p.getPassword())){

                        session.setAttribute("currentUser", p.getId());
                        switch(p.getTipo()){
                            case 0:
      out.write("\n");
      out.write("                                ");
      if (true) {
        _jspx_page_context.forward("adminwelcome.html");
        return;
      }
      out.write("\n");
      out.write("                                ");
break;
                            case 1:
      out.write("\n");
      out.write("                              ");
      if (true) {
        _jspx_page_context.forward("teacherwelcome.html");
        return;
      }
      out.write("\n");
      out.write("                              ");
break;
                            case 2:
      out.write("\n");
      out.write("                              ");
      if (true) {
        _jspx_page_context.forward("studentwelcome.html");
        return;
      }
      out.write("\n");
      out.write("                              ");
break;
                        }

                    }else{
      out.write("\n");
      out.write("                              ");
      if (true) {
        _jspx_page_context.forward("/login.jsp?message=WrongPass");
        return;
      }
      out.write("\n");
      out.write("                              ");


                    }
                    
                }catch(Exception e){
                    out.print("<p>"+e.getMessage()+"</p>");
                }
                
            }else{
      out.write("\n");
      out.write("                ");
      if (true) {
        _jspx_page_context.forward("login.jsp");
        return;
      }
      out.write("\n");
      out.write("                ");

            }
            //<jsp:forward page="ListPerson" />
        
      out.write("\n");
      out.write("    \n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
