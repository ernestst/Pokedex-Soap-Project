package pl.edu.pb.wi.ws.pokedex;

import java.util.List;
import javax.jws.WebService;
import javax.xml.soap.SOAPException;
import pl.edu.pb.wi.pokemon.dao.EvolutionDAO;
import pl.edu.pb.wi.pokemon.dto.EvolutionData;
import pl.edu.pb.wi.pokemon.mapper.EvolutionMapper;
import pl.edu.pb.wi.ws.resposne.Evolution;

@WebService(endpointInterface = "pl.edu.pb.wi.ws.pokedex.EvolutionService")
public class EvolutionServiceImpl implements EvolutionService {

    private final EvolutionMapper evolutionMapper = new EvolutionMapper();

    @Override
    public Evolution [] getPokemonEvolutions(int dex) {
        EvolutionDAO dao = new EvolutionDAO();
        return dao.getEvolutionDetails(dex)
                .stream()
                .map(evolutionMapper::map)
                .toArray(Evolution[]::new);
    }

    @Override
    public void savePokemonEvolution(Evolution evolution) throws SOAPException {
        EvolutionDAO dao = new EvolutionDAO();
        List<EvolutionData> evolutions = dao.getEvolutionDetails(evolution.getSourceNationalDex());
        boolean exists = evolutions
                .stream()
                .anyMatch(evo -> evo.getEvolutionNationalDex() == evolution.getEvolutionNationalDex());

        if (exists) {
            throw new SOAPException("Evolution already exists");
        }

        dao.saveEntity(evolutionMapper.map(evolution));
    }
}
