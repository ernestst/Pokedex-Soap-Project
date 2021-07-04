package pl.edu.pb.wi.pokemon.mapper;

import pl.edu.pb.wi.core.mapper.Mapper;
import pl.edu.pb.wi.pokemon.dto.PokemonData;
import pl.edu.pb.wi.ws.resposne.Pokemon;

public class PokemonMapper implements Mapper<PokemonData, Pokemon> {
    public PokemonData map(Pokemon pokemon) {
        PokemonData pokemonData = new PokemonData();
        pokemonData.setCode(pokemon.getCode());
        pokemonData.setName(pokemon.getName());
        pokemonData.setNationalDex(pokemon.getNationalDex());
        pokemonData.setPrimaryType(pokemon.getPrimaryType());
        pokemonData.setSecondType(pokemon.getSecondType());

        return pokemonData;
    }

    public Pokemon map(PokemonData pokemonData) {
        Pokemon pokemon = new Pokemon();
        pokemon.setCode(pokemonData.getCode());
        pokemon.setName(pokemonData.getName());
        pokemon.setNationalDex(pokemonData.getNationalDex());
        pokemon.setPrimaryType(pokemonData.getPrimaryType());
        pokemon.setSecondType(pokemonData.getSecondType());

        return pokemon;
    }
}
