package com.example.demo.servlet;

import com.example.demo.dao.GameDao;
import com.example.demo.model.Game;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/games/add")
public class AddGameServlet extends HttpServlet {

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/add-game.jsp")
                .forward(req, resp); //forward permet d'aller sur la page
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name =  req.getParameter("gameName");
        String description =  req.getParameter("gameDescription");

        System.out.println(name);
        System.out.println(description);
        Game game = new Game(name,description);
        GameDao gameDao = new GameDao();
        gameDao.save(game);

        resp.sendRedirect(req.getContextPath() + "/games");
        System.out.println("POST /games/add");


    }


}
