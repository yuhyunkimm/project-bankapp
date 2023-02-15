<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>

    <h1>ATM입금</h1>
    <hr>
    <form action="/account/deposit" method="post">
        <input type="text" name="amount" placeholder="enter 입금금액"><br>
        <input type="text" name="wAccountNumber" placeholder="enter 입금계좌번호"><br>
        <button>입금</button>
    </form>

<%@ include file="../layout/footer.jsp" %>