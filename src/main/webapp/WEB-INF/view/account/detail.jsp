<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>

        <h1>계좌상세보기</h1>
        <hr>
        <table class="table">
            <div class="user-box">
                fullname님 계좌<br>
                계좌번호 : 1111<br>
                잔액 : 1000원
            </div>
        </table>
        <div id="list-box">
            <a href="#">전체</a> <a href="/account/${id}/depositForm">입금</a> <a href="/account/${id}/withdrawForm">출금</a>
            <br>
            <table border="1" class="table">
                <thead>
                    <tr>
                        <th>날짜</th>
                        <th>보낸이</th>
                        <th>받은이</th>
                        <th>입출금금액</th>
                        <th>계좌잔액</th>
                    </tr>
                </thead>
                <tr>
                    <td>2022.10.01</td>
                    <td>ATM</td>
                    <td>1111계좌</td>
                    <td>500원</td>
                    <td>1500원</td>
                </tr>
            </table>
        </div>

        <%@ include file="../layout/footer.jsp" %>