package pl.edu.pb.wi.ws.auth;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.soap.SOAPException;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import pl.edu.pb.wi.pokemon.dao.PokemonDAO;
import pl.edu.pb.wi.pokemon.mapper.PokemonMapper;
import pl.edu.pb.wi.user.dao.TeamDAO;
import pl.edu.pb.wi.user.dao.UserDAO;
import pl.edu.pb.wi.user.dto.TeamData;
import pl.edu.pb.wi.ws.resposne.Pokemon;

@WebService(endpointInterface = "pl.edu.pb.wi.ws.auth.TeamService")
public class TeamServiceImpl implements TeamService {

    @Resource
    private WebServiceContext ctx;

    @Override
    public Pokemon[] getUserTeamMembers(String login) throws Exception {
        MessageContext messageCtx = ctx.getMessageContext();
        Map http_headers = (Map) messageCtx.get(MessageContext.HTTP_REQUEST_HEADERS);
        List userList = (List) http_headers.get("username");

        if (!userList.get(0).equals(login)) {
            throw new Exception("UNAUTHORIZED");
        }

        TeamDAO teamDAO = new TeamDAO();
        PokemonDAO pokemonDAO = new PokemonDAO();
        UserDAO userDAO = new UserDAO();
        PokemonMapper mapper = new PokemonMapper();

        List<TeamData> team = teamDAO.getUserTeam(userDAO.getUser(login).getId());
        if (team == null) {
            return new Pokemon[0];
        }

        Pokemon[] pokemons = new Pokemon[team.size()];

        int i = 0;
        for (TeamData teamInfo : team) {
            pokemons[i] = mapper.map(pokemonDAO.getPokemon(teamInfo.getPokemon()));
            i++;
        }

        return pokemons;
    }

    @Override
    public Pokemon[] addUserTeamMember(String login, int dex) throws Exception {
        MessageContext messageCtx = ctx.getMessageContext();
        Map http_headers = (Map) messageCtx.get(MessageContext.HTTP_REQUEST_HEADERS);
        List userList = (List) http_headers.get("username");

        if (!userList.get(0).equals(login)) {
            throw new Exception("UNAUTHORIZED");
        }

        TeamDAO teamDAO = new TeamDAO();
        UserDAO userDAO = new UserDAO();
        PokemonDAO pokemonDAO = new PokemonDAO();

        List<TeamData> team = teamDAO.getUserTeam(userDAO.getUser(login).getId());
        if (team == null) {
            return new Pokemon[0];
        } else if (team.size() == 6) {
            throw new SOAPException("TEAM_IS_FULL");
        } else if (team.stream().anyMatch(teamData -> teamData.getPokemon() == dex)) {
            throw new SOAPException("POKEMON_ALREADY_IN_TEAM");
        }

        if (pokemonDAO.getPokemon(dex) == null) {
            throw new Exception("POKEMON_DOES_NOT_EXIST");
        }

        TeamData nextTeamInfo = new TeamData();
        nextTeamInfo.setUser(userDAO.getUser(login).getId());
        nextTeamInfo.setPokemon(dex);
        teamDAO.saveEntity(nextTeamInfo);

        return getUserTeamMembers(login);
    }

    @Override
    public Pokemon[] removeUserTeamMember(String login, int dex) throws Exception {
        MessageContext messageCtx = ctx.getMessageContext();
        Map http_headers = (Map) messageCtx.get(MessageContext.HTTP_REQUEST_HEADERS);
        List userList = (List) http_headers.get("username");

        if (!userList.get(0).equals(login)) {
            throw new Exception("UNAUTHORIZED");
        }

        TeamDAO teamDAO = new TeamDAO();
        UserDAO userDAO = new UserDAO();

        int userId = userDAO.getUser(login).getId();
        List<TeamData> team = teamDAO.getUserTeam(userId);
        if (team == null) {
            return new Pokemon[0];
        }

        for (TeamData details : team) {
            if (details.getPokemon() == dex) {
                teamDAO.removeUserTeam(userId, dex);
                break;
            }
        }

        return getUserTeamMembers(login);
    }
}
