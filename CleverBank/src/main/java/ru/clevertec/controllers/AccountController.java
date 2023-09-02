package ru.clevertec.controllers;

import com.google.gson.Gson;
import ru.clevertec.dao.AccountDAOImpl;
import ru.clevertec.dao.BankDAOImpl;
import ru.clevertec.dao.UserDAOImpl;
import ru.clevertec.dao.api.AccountDAO;
import ru.clevertec.dao.api.BankDAO;
import ru.clevertec.dao.api.UserDAO;
import ru.clevertec.data.account.request.RequestAccount;
import ru.clevertec.data.account.response.ResponseAccount;
import ru.clevertec.mapper.AccountMapper;
import ru.clevertec.service.AccountServiceImpl;
import ru.clevertec.service.api.AccountService;
import ru.clevertec.util.ControllerUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/accounts/*")
public class AccountController extends HttpServlet {

    private final BankDAO bankDAO = new BankDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();
    private final AccountDAO accountDAO = new AccountDAOImpl(bankDAO, userDAO);
    private final AccountMapper accountMapper = new AccountMapper(bankDAO, userDAO);
    private final AccountService accountService = new AccountServiceImpl(accountDAO, accountMapper);
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (Objects.isNull(pathInfo)) {
            List<ResponseAccount> banks;
            banks = accountService.geAllAccounts();
            String json = gson.toJson(banks);
            sendJsonResponse(json, resp);
        } else if (ControllerUtil.isId(pathInfo)) {
            String id = pathInfo.substring(1);
            ResponseAccount account;
            account = accountService.getAccountById(Long.parseLong(id));
            String json = gson.toJson(account);
            sendJsonResponse(json, resp);
        } else {
            resp.sendError(404, String.format("The requested resource [%s] is not available", req.getRequestURI()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestAccount accountDto = gson.fromJson(req.getReader(), RequestAccount.class);
        accountService.addAccount(accountDto);
        String json = gson.toJson(accountDto);
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
        RequestAccount account = gson.fromJson(req.getReader(), RequestAccount.class);
        accountService.updateAccount(Long.parseLong(id), account);
        String json = gson.toJson(account);
        sendJsonResponse(json, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (!ControllerUtil.isId(pathInfo)) {
            resp.sendError(400, "Id must be set");
            return;
        }
        String id = pathInfo.substring(1);
        accountService.deleteAccount(Long.parseLong(id));
        resp.setStatus(204);
    }

    private void sendJsonResponse(String json, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setStatus(200);
        response.getWriter().println(json);
    }
}
