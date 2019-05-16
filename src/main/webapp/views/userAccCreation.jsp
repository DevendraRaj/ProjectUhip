<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Account Creation</title>


<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	
	

<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>



<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script	
src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>

<script type="text/javascript">
	$(function() {
		$("#dob").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : "1960:" + new Date(),
			maxDate : new Date(),
			dateFormat : 'dd/M/yy'
		});
	});

	$(document).ready(function() {
		//alert("ready");
		$("#emailId").blur(function() {
			//alert("blur");
			var email = $("#emailId").val();
			$("#errMsg").html("");
			$("#createAccBtn").attr('disabled', false);

			$.ajax({
				url : window.location + "/checkEmail",
				type : "GET",

				data : {
					"email" : email
				},

				success : function(data) {
					alert(data);
					if (data == 'Duplicate') {
						$("#errMsg").html("Email Already Registered");
						$("#createAccBtn").attr('disabled', true);
					}
				},
				error : function(e) {
					alert("error")
				}

			});
		});
		
		$("#accRegForm").validate({
			rules : {
				firstName : 'required',
				lastName : 'required',
				email : 'required',
				password : 'required',
				dob : 'required',
				gender : 'required',
				ssn : 'required',
			},
			messages : {
				firstName : 'First Name is Mandatory',
				lastName : 'Last Name is Mandatory',
				email : 'Enter Your Valid Email',
				password : 'Enter Your Currect Password',
				dob : 'Enter Your Date of  birth',
				gender : 'Choose Your Gender  ',
				ssn : 'Please Enter SSN No'
			}
		});

	});
</script>

</head>
<body>
	<h2 style="color: blue; text-align: center">User Account Creation</h2>

	<h1 style="color: green; text-align: center">${succMsg}</h1>
	<br>

	<h1 style="color: red; text-align: center">${failMsg}</h1>

	<form:form  action="createUserAcc" method="POST" id= "accRegForm"
		modelAttribute="userAccModel" enctype="multipart/form-data">

		<center>
			<table>
				<tr>
					<td>First Name :</td>
					<td><form:input path="firstName" placeholder="First Name"/></td>
				</tr>

				<tr>
					<td>Last Name :</td>
					<td><form:input path="lastName" placeholder="Last Name"/></td>
				</tr>
				<tr>
					<td>Email :</td>
					<td><form:input path="email" id="emailId" placeholder="Email"/> <font
						color='red'> <span id="errMsg"></span></font></td>
				</tr>
				<tr>
					<td>Password :</td>
					<td><form:password path="password" placeholder="Password"/></td>
				</tr>
				<tr>
					<td>DOB :</td>
					<td><form:input path="dob" id="dob" autocomplete="off" placeholder="Date of Birth" /></td>
				</tr>
				<tr>
					<td>Gender :</td>
					<td><form:radiobuttons items="${genders}" path="gender"/></td>
				</tr>
				<tr>
					<td>SSN :</td>
					<td><form:input path="ssn" placeholder="Socicial Security Number" /></td>
				</tr>

				<tr>
					<td>Role :</td>
					<td><form:select items="${roles}" path="role"/></td>
				</tr>

				
				<tr>
					<td><input type="reset" value="Reset" /></td>
					<td><input type="submit" value="Create Account"
						id="createAccBtn" /></td>
				</tr>

			</table>
		</center>
	</form:form>
</body>
</html>