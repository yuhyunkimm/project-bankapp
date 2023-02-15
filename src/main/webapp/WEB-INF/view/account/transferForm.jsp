<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>

<body>
    <h1>이체</h1>
    <hr>
    <form action="/account/transfer" method="post">
        <input type="text" name="amount" placeholder="enter 이체금액"><br>
        <input type="text" name="wAccountNumber" placeholder="enter 출금계좌번호"><br>
        <input type="text" name="dAccountNumber" placeholder="enter 입금계좌번호"><br>
        <input type="password" name="wAccountPassword" placeholder="enter 출금계좌비밀번호"><br>
        <button>이체</button>
    </form>

<%@ include file="../layout/footer.jsp" %>
