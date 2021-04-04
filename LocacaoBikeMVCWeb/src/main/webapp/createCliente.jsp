<%-- 
    Document   : createCliente
    Created on : 3 de abr de 2021, 17:05:31
    Author     : melisa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <center><h1>Sistema Locação de Bicicletas</h1></center>
        
        <form method="POST" action='ControllerCliente' name="cliente">
        
        Nome Completo : <input type="text" name="nome" value="" /> <br />
        Sexo : <input type="text" name="sexo" value="M"/> <br /> 
        Idade : <input type="number" name="idade" /> <br /> 
        CPF : <input type="text" name="cpf" value=""/> <br /> 
        Endereco : <input type="text" name="endereco" value="" /> <br />
        Email : <input type="text" name="email" value="" /> <br />
        Telefone : <input type="text" name="telefone" value="" /> <br />
        <input type="submit" value="Submit" />
    </form>
    </body>
</html>
