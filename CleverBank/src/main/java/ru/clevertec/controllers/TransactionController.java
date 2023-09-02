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
import ru.clevertec.data.transaction.response.ResponseTransaction;
import ru.clevertec.mapper.TransactionMapper;
import ru.clevertec.service.TransactionServiceImpl;
import ru.clevertec.service.api.TransactionService;
import ru.clevertec.util.ControllerUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/transactions/*")
public class TransactionController extends HttpServlet {
    private final BankDAO bankDAO = new BankDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();
    private final AccountDAO accountDAO = new AccountDAOImpl(bankDAO, userDAO);
    private final TransactionDAO transactionDAO = new TransactionDAOImpl(accountDAO);
    private final TransactionMapper transactionMapper = new TransactionMapper(accountDAO);
    private final TransactionService transactionService = new TransactionServiceImpl(transactionDAO, transactionMapper);
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (Objects.isNull(pathInfo)) {
            List<ResponseTransaction> transactions;
            transactions = transactionService.getAllTransactions();
            String json = gson.toJson(transactions);
            sendJsonResponse(json, resp);
        } else if (ControllerUtil.isId(pathInfo)) {
            String id = pathInfo.substring(1);
            ResponseTransaction transaction;
            transaction = transactionService.getTransactionById(Long.parseLong(id));
            String json = gson.toJson(transaction);
            sendJsonResponse(json, resp);
        } else {
            resp.sendError(404, String.format("The requested resource [%s] is not available", req.getRequestURI()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestTransaction transactionDto = gson.fromJson(req.getReader(), RequestTransaction.class);
        transactionService.addTransaction(transactionDto);
        String json = gson.toJson(transactionDto);
        sendJsonResponse(json, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (!ControllerUtil.isId(pathInfo)) {
            resp.sendError(400, "Id must be set");
            return;
        }
        String id = pathInfo.substring(1);
        RequestTransaction transactionDto = gson.fromJson(req.getReader(), RequestTransaction.class);
        transactionService.updateTransaction(Long.parseLong(id), transactionDto);
        String json = gson.toJson(transactionDto);
        sendJsonResponse(json, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (!ControllerUtil.isId(pathInfo)) {
            resp.sendError(400, "Id must be set!");
            return;
        }
        String id = pathInfo.substring(1);
        transactionService.deleteTransaction(Long.parseLong(id));
        resp.setStatus(204);
    }

    private void sendJsonResponse(String json, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setStatus(200);
        response.getWriter().println(json);
    }
}
