package ru.clevertec.controllers;

import com.google.gson.Gson;
import ru.clevertec.dao.BankDAOImpl;
import ru.clevertec.dao.api.BankDAO;
import ru.clevertec.data.bank.request.RequestBank;
import ru.clevertec.data.bank.response.ResponseBank;
import ru.clevertec.mapper.BankMapper;
import ru.clevertec.service.BankServiceImpl;
import ru.clevertec.service.api.BankService;
import ru.clevertec.util.ControllerUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@WebServlet("/banks/*")
public class BankController extends HttpServlet {

    private final BankDAO bankDAO = new BankDAOImpl();
    private final BankMapper bankMapper = new BankMapper();
    private final BankService bankService = new BankServiceImpl(bankDAO, bankMapper);

    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (Objects.isNull(pathInfo)) {
            List<ResponseBank> banks;
            try {
                banks = bankService.getAllBanks();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            String json = gson.toJson(banks);
            sendJsonResponse(json, resp);
        } else if (ControllerUtil.isId(pathInfo)) {
            String id = pathInfo.substring(1);
            ResponseBank bank;
            try {
                bank = bankService.getBankById(Long.parseLong(id));
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            String json = gson.toJson(bank);
            sendJsonResponse(json, resp);
        } else {
            resp.sendError(404, String.format("The requested resource [%s] is not available", req.getRequestURI()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestBank bankDto = gson.fromJson(req.getReader(), RequestBank.class);
        try {
            bankService.addBank(bankDto);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String json = gson.toJson(bankDto);
        sendJsonResponse(json, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (!ControllerUtil.isId(pathInfo)) {
            resp.sendError(400, "Id must be set!");
            return;
        }
        String id = pathInfo.substring(1);
        RequestBank bank = gson.fromJson(req.getReader(), RequestBank.class);
        try {
            bankService.updateBank(Long.parseLong(id), bank);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String json = gson.toJson(bank);
        sendJsonResponse(json, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (!ControllerUtil.isId(pathInfo)) {
            resp.sendError(400, "Invalid id!");
            return;
        }
        String id = pathInfo.substring(1);
        try {
            bankService.deleteBank(Long.parseLong(id));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        resp.setStatus(204);
    }

    private void sendJsonResponse(String json, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setStatus(200);
        response.getWriter().println(json);
    }
}
