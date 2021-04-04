<%-- 
    Document   : createBicicleta
    Created on : 3 de abr de 2021, 17:05:09
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
        
        <form method="POST" action='ControllerBicicleta' name="bicicletas">

                Nome Completo : <input type="text" name="nome" value="" /> <br />
                Modelo : <input type="text" name="modelo" value=""/> <br /> 
                Cor : <input type="text" name="cor" value=""/> <br /> 
                Tamanho : <input type="number" name="tamanho" /> <br /> 
                Tempo de uso : <input type="number" name="tempouso" /> <br /> 
                Tipo pneu : <input type="text" name="tipopneu" value=""/> <br />
                Valor da Locacao : <input type="number" name="valor" /> <br /> 
                
                <input type="submit" value="Submit" />
        </form>
    </body>
</html>
