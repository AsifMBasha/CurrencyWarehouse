<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<%@ page isELIgnored="false" %>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Records Status</title>
 
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
    <h2>${filename}</h2>  
    
    
 <c:choose>
  <c:when test="${duplicateFile eq true}">
 	 <h2>Duplicate File !</h2>
  </c:when>
  <c:when test="${duplicatedeal eq true}">
 	 <h2>File contains duplicate deal id</h2>
  </c:when>
  <c:otherwise>
    <table>
        <tr>
            <td>Record</td><td>Status</td>
        </tr>
        <c:forEach items="${recordstatusList}" var="recordStatus">
            <tr>
            <td>${recordStatus.recordData}</td>
            <td>${recordStatus.status}</td>            
            </tr>
        </c:forEach>
    </table>
  </c:otherwise>
</c:choose>

    
    <br/>
    <a href="home">Go Home</a>
</body>
</html>