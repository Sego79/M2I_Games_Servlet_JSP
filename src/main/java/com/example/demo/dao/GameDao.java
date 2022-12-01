package com.example.demo.dao;

import com.example.demo.model.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameDao implements Dao<Game>{

    private static Long idSequence = 1L;

    //On met en static notre liste afin que la liste puisse être mise à jour à chaque ajouts
    private static List<Game> games = new ArrayList<Game>(){{
        add(new Game(idSequence++, "Counter Strike 60","jeu de combats"));
        add(new Game(idSequence++, "Civilisation","jeu de strategie"));
    }};


    @Override
    public Optional<Game> get(Long id) {
        //On veut récupérer l'élement en fonction de son id et non de sa position (car id=1 à la position 0 par exemple)
        return games.stream().filter(g-> g.getId() == id).findFirst();
    }

    @Override
    public List<Game> getAll() {
        return games;
    }

    @Override
    public void save(Game game) {
        game.setId(idSequence++); //on incrémente notre id
        this.games.add(game);
    }

    @Override
    public void update(Game gameParam) {
        games.stream()
                .forEach(game -> {
                    if(game.getId() == gameParam.getId()) {
                        game.setName(gameParam.getName());
                        game.setDescription(gameParam.getDescription());
                }
                });
    }

    @Override
    public void delete(Game gametoDelete) {
        games = games.stream()
                .filter(game-> game.getId() != gametoDelete.getId())
                .collect(Collectors.toList());

    }
}
