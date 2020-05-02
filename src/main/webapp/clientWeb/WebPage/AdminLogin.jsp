<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Envelope</title>
<script type="text/javascript" src="/bongtoo/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/bongtoo/clientWeb/WebScript/LoginCheck.js"></script>

</head>

<body style="margin:100px 0 0 0;background-color:#f2f2f2;">

<form action="../../member/adminLoginCheck.web" id="loginForm" method="post">
	<table>
		<tr>
			<td>
				ID
			</td>
			<td>
				<input type="text" class="input-small" name="input_id">
			</td>
		</tr>
		<tr>
			<td>
				PW
			</td>
			<td>
				<input type="password" class="input-small" name="input_pw">
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" class="btn btn-blue" value="Login">
			</td>
		</tr>
	</table>
</form>

</body>

<style type="text/css">
    table {
    background-color: skyblue;
    border: 1px solid skyblue;
    border-radius: 10px;
    margin-left: auto;
    margin-right: auto;
    padding: 20px 20px 20px 20px;
    }
    
    tr {
    font-family: arial narrow;
    font-size: 12px;
    font-weight: bold;
    text-align: center;
    color: #333;
    width: 50px;
    padding: 10px 5px 10px 5px;
    }
    
    td {
    font-family: arial narrow;
    font-size: 12px;
    font-weight: bold;
    text-align: center;
    color: #333;
    width: 50px;
    padding: 10px 5px 10px 5px;
    }

    
  	.input-small {
    display: inline-block;
    font-family: arial narrow;
    font-size: 14px;
    font-weight: 400;
    text-align: center;
    color: #333;
    background-color: #fff;
    border: 1px solid #ccc;
    border-radius: 4px;
    padding: 3px 5px 3px 5px;
    }
    
    .btn {
    display: inline-block;
    font-family: arial narrow;
    font-size: 14px;
    font-weight: 400;
    text-align: center;
    color: #333;
    background-color: #fff;
    border: 1px solid #ccc;
    border-radius: 4px;
    padding: 6px 12px;
    cursor: pointer;
  	}
  	
  	.btn:hover {
    text-shadow: 1px 1px 1px rgba(051,051,051,1);
  	}
  	
  	.btn:active {
    box-shadow: none;
  	}

  	.btn-red {
    color: #fff;
    background-color: #d9534f;
    border-color: #d43f3a;
  	}
  	
  	.btn-blue {
    color: #fff;
    background-color: #337ab7;
    border-color: #2e6da4;
  	}
</style>


</html>