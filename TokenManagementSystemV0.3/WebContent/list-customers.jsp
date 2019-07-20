<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title> Token Management App </title>
	
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>

	<div id="wrapper">
		<div id="header">
			<h2>    Token Management  </h2>
		</div>
	</div>

	<div id="container">
	
		<div id="content">
		
			<!-- put new button: Add Student -->
			
			<input type="button" value="Get Token" 
				   onclick="window.location.href='add-customer-form.jsp'; return false;"
				   class="add-customer-button"
			/>
			
			<table>
			
				<tr>
					<th>Token</th>
					<th>Name</th>
				</tr>
				
				<c:forEach var="tCustomer" items="${CUSTOMERS_LIST}">
					
					<tr>
						<td> ${tCustomer.id} </td>
						<td> ${tCustomer.cName} </td>
					</tr>
				
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
	</form>
	
			<p>
			<a href="CustomerControllerServlet">Update</a>
		</p>
</body>


</html>








