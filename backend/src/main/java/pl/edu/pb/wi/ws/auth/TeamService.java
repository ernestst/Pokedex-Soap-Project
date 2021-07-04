package pl.edu.pb.wi.ws.auth;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import pl.edu.pb.wi.ws.resposne.Pokemon;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
@HandlerChain(file = "handler-chain.xml")
public interface TeamService {
    @WebMethod
    Pokemon [] getUserTeamMembers(@WebParam(name = "login") String login) throws Exception;

    @WebMethod
    Pokemon [] addUserTeamMember(@WebParam(name = "login") String login, @WebParam(name = "dex") int dex) throws Exception;

    @WebMethod
    Pokemon [] removeUserTeamMember(@WebParam(name = "login") String login, @WebParam(name = "dex") int dex) throws Exception;
}
