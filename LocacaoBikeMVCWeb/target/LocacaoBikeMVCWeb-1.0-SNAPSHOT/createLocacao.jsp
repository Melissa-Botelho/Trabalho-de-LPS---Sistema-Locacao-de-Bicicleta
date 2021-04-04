<%-- 
    Document   : createLocacao
    Created on : 3 de abr de 2021, 17:04:47
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
        
        <form method="POST" action='ControllerLocacao' name="cliente">

            ID do Cliente : <input type="text" name="idcliente" value="" /> <br />
            ID da Bicicleta : <input type="text" name="idbicicleta" value="" /> <br />
            Horario Locacao : <input type="number" name="horariolocacao" /> <br />
            Data Locacao:  <input type="date" name="datalocacao" value=""/> <br /> 
            
            Horario Devolucao : <input type="number" name="horariodevolucao" /> <br />
            Data Devolucao:  <input type="date" name="datadevolucao" value=""/> <br /> 
            Valor da Locacao : <input type="number" name="valor" /> <br /> 
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>
