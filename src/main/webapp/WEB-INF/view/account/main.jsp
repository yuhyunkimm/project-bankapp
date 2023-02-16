<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>

        <h1>메인페이지</h1>
        <hr>
        <table border="1">
            <thead>
                <tr>
                    <th>계좌번호</th>
                    <th>계좌잔액</th>
                </tr>
            </thead>
            <c:forEach items="${accountList}" var="account">
                <tr>
                    <td><a href="/account/{account.id}">${account.number}</a></td>
                    <td>${account.balace}</td>
                </tr>
            </c:forEach>
        </table>

        <%@ include file="../layout/footer.jsp" %>