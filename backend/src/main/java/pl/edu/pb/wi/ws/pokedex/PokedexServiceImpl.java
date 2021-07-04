package pl.edu.pb.wi.ws.pokedex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.jws.WebService;
import javax.xml.soap.SOAPException;
import pl.edu.pb.wi.pokemon.dao.PokemonDAO;
import pl.edu.pb.wi.core.dto.Pageable;
import pl.edu.pb.wi.pokemon.dto.PokemonData;
import pl.edu.pb.wi.core.mapper.PageMapper;
import pl.edu.pb.wi.pokemon.mapper.PokemonMapper;
import pl.edu.pb.wi.ws.resposne.Page;
import pl.edu.pb.wi.ws.resposne.Pokemon;
import pl.edu.pb.wi.ws.resposne.Type;

@WebService(endpointInterface = "pl.edu.pb.wi.ws.pokedex.PokedexService")
public class PokedexServiceImpl implements PokedexService {

    private final PokemonMapper pokemonMapper = new PokemonMapper();

    @Override
    public Pokemon getPokemonById(int dex) {
        PokemonDAO dao = new PokemonDAO();
        return pokemonMapper.map(dao.getPokemon(dex));
    }

    @Override
    public Pokemon getPokemonByCode(String code) {
        PokemonDAO dao = new PokemonDAO();
        return pokemonMapper.map(dao.getPokemon(code));
    }

    @Override
    public Page<Pokemon> getPokemons(int page) throws Exception {
        PokemonDAO dao = new PokemonDAO();
        Pageable<PokemonData> pokemons = dao.getPage(page);
        PageMapper<PokemonData, Pokemon> pageMapper = new PageMapper<>(pokemonMapper);

        return pageMapper.map(pokemons);
    }

    @Override
    public Page<Pokemon> getPokemonsByName(int page, String name) throws Exception {
        PokemonDAO dao = new PokemonDAO();
        List<String> conditions = Collections.singletonList("lower(e.name) LIKE :name");
        Map<String, Object> parameters = Collections.singletonMap("name", "%" + name.toLowerCase(Locale.ROOT) + "%");

        Pageable<PokemonData> pokemons = dao.getPage(page, conditions, parameters);
        PageMapper<PokemonData, Pokemon> pageMapper = new PageMapper<>(pokemonMapper);

        return pageMapper.map(pokemons);
    }

    @Override
    public Page<Pokemon> getPokemonsByType(int page, Type [] types) throws Exception {
        PokemonDAO dao = new PokemonDAO();
        List<String> conditions = new ArrayList<>();
        Map<String, Object> parameters = new HashMap<>();

        StringBuilder queryBuilder = new StringBuilder();
        int index = 0;
        for (Type type : types) {
            queryBuilder.append("(e.primaryType = :type").append(index).append(" OR e.secondType = :type").append(index).append(")");
            if (index + 1 < types.length) {
                queryBuilder.append(" OR ");
            }

            parameters.put("type" + index, type);
            index++;
        }

        conditions.add(queryBuilder.toString());
        Pageable<PokemonData> pokemons = dao.getPage(page, conditions, parameters);
        PageMapper<PokemonData, Pokemon> pageMapper = new PageMapper<>(pokemonMapper);

        return pageMapper.map(pokemons);
    }

    @Override
    public Page<Pokemon> getPokemonsAdvanced(int page, String name, Type[] types) throws Exception {
        PokemonDAO dao = new PokemonDAO();
        List<String> conditions = new ArrayList<>();
        Map<String, Object> parameters = new HashMap<>();

        if (name != null && !name.isEmpty()) {
            conditions.add("lower(e.name) LIKE :name");
            parameters.put("name", "%" + name.toLowerCase(Locale.ROOT) + "%");
        }

        if (types != null && types.length != 0) {
            StringBuilder queryBuilder = new StringBuilder();
            int index = 0;
            for (Type type : types) {
                queryBuilder.append("(e.primaryType = :type").append(index).append(" OR e.secondType = :type").append(index).append(")");
                if (index + 1 < types.length) {
                    queryBuilder.append(" OR ");
                }

                parameters.put("type" + index, type);
                index++;
            }

            conditions.add(queryBuilder.toString());
        }

        Pageable<PokemonData> pokemons = dao.getPage(page, conditions, parameters);
        PageMapper<PokemonData, Pokemon> pageMapper = new PageMapper<>(pokemonMapper);

        return pageMapper.map(pokemons);
    }

    @Override
    public void savePokemon(Pokemon pokemon) throws SOAPException {
        PokemonDAO dao = new PokemonDAO();
        if (dao.getPokemon(pokemon.getNationalDex()) != null) {
            throw new SOAPException("Pokemon already exists");
        }

        dao.saveEntity(pokemonMapper.map(pokemon));
    }
}
