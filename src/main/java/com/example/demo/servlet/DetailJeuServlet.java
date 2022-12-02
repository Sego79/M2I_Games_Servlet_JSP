package com.example.demo.servlet;

import com.example.demo.dao.GameDao;
import com.example.demo.model.Game;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = "/games/details")
public class DetailJeuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GameDao gameDao = new GameDao();
        //On récupère l'id mis en paramètre dans la jsp "list-game"
        Long id = Long.valueOf(req.getParameter("id"));
        Optional<Game> game = gameDao.get(id);
        if(game.isPresent()){
        //On récupère les infos de notre entité game :
        String name = game.get().getName();
        String description = game.get().getDescription();
        //On met à jour ses infos pour l'envoi à la JSP
        req.setAttribute("id", id);
        req.setAttribute("name", name);
        req.setAttribute("description", description);
        }else {
            System.out.println("Il n'y a pas d'ID pour ce jeu");
        }
        //On envoi notre requête à la jsp pour affichage
        req.getRequestDispatcher("/WEB-INF/detail-game.jsp").forward(req, resp);
    }


}
