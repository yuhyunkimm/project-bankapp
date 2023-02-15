<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>

    <h1>회원가입페이지</h1>
    <hr>
    <form action="/join" method="post">
        <input type="text" name="username" placeholder="enter username"><br>
        <input type="password" name="password" placeholder="enter password"><br>
        <input type="text" name="fullname" placeholder="enter fullname"><br>
        <button>회원가입</button>
    </form>
    
<%@ include file="../layout/footer.jsp" %>