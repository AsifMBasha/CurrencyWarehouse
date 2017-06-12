<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<%@ page isELIgnored="false" %>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Exchange Records</title>
 
    <style>
	    table, th, td {
	   	 border: 1px solid black;
		}
    
        tr:first-child{
            font-weight: bold;
            background-color: #C6C9C4;
        }
    </style>
 
</head>
 
 
<body>
    <h2>List of valid Records</h2>  
    <table>
        <tr>
            <td>filename</td><td>Deal Id</td><td>From currency</td><td>To currency</td><td>Deal time</td><td>Deal amount</td>
        </tr>
        <c:forEach items="${records}" var="exchangeRecord">
            <tr>
            <td>${exchangeRecord.filename}</td>
             <td>${exchangeRecord.dealId}</td>
            <td>${exchangeRecord.fromcurrency}</td>
            <td>${exchangeRecord.tocurrency}</td>
            <td>${exchangeRecord.dealtime}</td>
            <td>${exchangeRecord.dealamount}</td>
            
            </tr>
        </c:forEach>
    </table>
    <br/>
    <h2>List of invalid Records</h2>  
    <table>
        <tr>
            <td>filename</td><td>record text</td>
        </tr>
        <c:forEach items="${invalidrecords}" var="invalidExchangeRecord">
            <tr>
            <td>${invalidExchangeRecord.erpk.filename}</td>
            <td>${invalidExchangeRecord.recordtext}</td>
            
            </tr>
        </c:forEach>
    </table>
    <br/>
    <a href="home">Go Home</a>
</body>
</html>