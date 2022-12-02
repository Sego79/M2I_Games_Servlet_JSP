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

@WebServlet(urlPatterns = "/games/edit")
public class EditGameServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GameDao gameDao = new GameDao();
        //On récupère l'id qui apparaît dans l'URL car il a été mis en paramètre dans la jsp "list-game"
        Long id = Long.parseLong(req.getParameter("id"));
        Optional<Game> game = gameDao.get(id);

        //On récupère les infos de notre entité game :
        if(game.isPresent()){
            String name = game.get().getName();
            String description = game.get().getDescription();
            //On met à jour ses infos pour l'envoi à la JSP
            req.setAttribute("id", id);
            req.setAttribute("name", name);
            req.setAttribute("description", description);
            //Autre méthode plus rapide car il récupère toutes les infos d'un coup et ça évite de faire une ligne pour chaque attribut :
            //req.setAttribute("gameEdit", game.get());
        }else {
            System.out.println("Il n'y a pas d'ID pour ce jeu");
        }
        //On envoit ces infos à notre JSP pour qu'elles soient affichées
        req.getRequestDispatcher("/WEB-INF/edit-game.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Modification du formulaire
        //Attention à prendre le name dans le JSP et non l'ID
        Long id = Long.parseLong(req.getParameter("gameId"));
        String name =  req.getParameter("gameName");
        String description= req.getParameter("gameDescription");

        Game game = new Game(id, name, description);
        GameDao gameDao = new GameDao();

        try{
            Optional<Game> gameOptional = gameDao.get(id);
            if(gameOptional.isPresent()){
                gameDao.update(game);
            }else {
                System.out.println("Il n'y a pas d'ID pour ce jeu " + id);
            }
        }catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //On se remet sur l'URL présentant la liste des jeux
        resp.sendRedirect(req.getContextPath() + "/games");

    }
}
