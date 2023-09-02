package ru.clevertec.controllers;

import com.google.gson.Gson;
import ru.clevertec.dao.AccountDAOImpl;
import ru.clevertec.dao.BankDAOImpl;
import ru.clevertec.dao.TransactionDAOImpl;
import ru.clevertec.dao.UserDAOImpl;
import ru.clevertec.dao.api.AccountDAO;
import ru.clevertec.dao.api.BankDAO;
import ru.clevertec.dao.api.TransactionDAO;
import ru.clevertec.dao.api.UserDAO;
import ru.clevertec.data.transaction.request.RequestTransaction;
import ru.clevertec.mapper.TransactionMapper;
import ru.clevertec.service.ServiceImpl;
import ru.clevertec.service.api.Service;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@WebServlet("/services/*")

public class ServiceController extends HttpServlet {

    private final BankDAO bankDAO = new BankDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();
    private final AccountDAO accountDAO = new AccountDAOImpl(bankDAO, userDAO);
    private final TransactionDAO transactionDAO = new TransactionDAOImpl(accountDAO);
    private final TransactionMapper transactionMapper = new TransactionMapper(accountDAO);
    private final Service service = new ServiceImpl(accountDAO, transactionDAO, transactionMapper);
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (Objects.isNull(pathInfo)) {
            resp.sendError(400, "Operation must be specified");
            return;
        }
        switch (pathInfo) {
            case "/accrueInterest" -> {
                try {
                    accrueInterest();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            case "/deposit" -> deposit(req, resp);
            case "/withdraw" -> withdraw(req, resp);
            case "/transfer" -> {
                try {
                    transfer(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            default ->
                    resp.sendError(404, String.format("The requested resource [%s] is not available", req.getRequestURI()));
        }
    }

    private void deposit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestTransaction requestTransaction = gson.fromJson(req.getReader(), RequestTransaction.class);
        service.depositMoney(requestTransaction);
        String json = gson.toJson(requestTransaction);
        sendJsonResponse(json, resp);
    }

    private void withdraw(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestTransaction requestTransaction = gson.fromJson(req.getReader(), RequestTransaction.class);
        service.withdrawMoney(requestTransaction);
        String json = gson.toJson(requestTransaction);
        sendJsonResponse(json, resp);
    }

    private void transfer(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        RequestTransaction requestTransaction = gson.fromJson(req.getReader(), RequestTransaction.class);
        service.transferMoney(requestTransaction);
        String json = gson.toJson(requestTransaction);
        sendJsonResponse(json, resp);
    }

    private void accrueInterest() throws SQLException {
        service.accrueInterest();
    }

    private void sendJsonResponse(String json, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setStatus(200);
        response.getWriter().println(json);
    }
}

