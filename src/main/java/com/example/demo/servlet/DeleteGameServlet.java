package com.example.demo.servlet;

import com.example.demo.dao.GameDao;
import com.example.demo.model.Game;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = "/games/delete")
public class DeleteGameServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //On récupère l'id mis en paramètre dans la jsp "list-game"
            String idStr = req.getParameter("IdGame");
            Long id = Long.parseLong(idStr);
            GameDao dao = new GameDao();
            Optional<Game> game = dao.get(id);
            //On utilise la méthode de notre DAO
            dao.delete(game.get());
            resp.sendRedirect(req.getContextPath() + "/games");
    }

}
