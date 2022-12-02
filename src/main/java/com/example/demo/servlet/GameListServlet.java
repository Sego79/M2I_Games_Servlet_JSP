package com.example.demo.servlet;

import com.example.demo.dao.GameDao;
import com.example.demo.model.Game;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@WebServlet(urlPatterns = GameListServlet.URL)
public class GameListServlet extends HttpServlet {

        public static final String URL = "/games";
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //DEMO GENERALE
                System.out.println("Reception d'une requete GET sur /games");

                Map<String, String[]> params = req.getParameterMap();

                //entrySet va retourner un set des entrées. Il retourne un couple clé valeur
                params.entrySet().stream().forEach(entry -> {
                        System.out.println(entry.getKey() + " ####### " + Arrays.toString(entry.getValue()) );
                        //On écrit dans l'URL: http://localhost:8080/games?id=1&firstname=Sego&priceMax=1000 et dans notre console cela affiche les paramètre
                });
                System.out.println(req.getHeaders("User-Agent").nextElement()); //on récupère les infos sur notre ordi


        //Exemple 1 d'affichage de données sur l'entité dans notre navigateur
                GameDao gameDao = new GameDao();
                //on récupère les jeux définis dans la DAO et une fois fait on les renvois dans la vue
                List<Game> gameList = gameDao.getAll();
                //on set les attributs sous la forme clé-valeur. La clé sera utilisée dans la JSP
                req.setAttribute("games", gameList);
        //Affichage sur console :
//                gameList.forEach(game -> {
//                        System.out.println(game.getId() + " - " + game.getName() + " - " + game.getDescription());
//                });

       //On renvoit les données à la JSP
                req.getRequestDispatcher("/WEB-INF/list-game.jsp")
                        .forward(req, resp);



        //Exemple 2 : on créé des balises html
                PrintWriter writer = resp.getWriter();
                resp.setContentType("text/html");
                writer.println("<html>");
                writer.println("<body>");
                //On intègre nos données dans notre liste
                gameList.forEach(game -> {
                        writer.println("<p>" +game.getId() + " - " + game.getName() + " - " + game.getDescription());
                });
                writer.println("</body>");
                writer.println("</html>");
        }

}
