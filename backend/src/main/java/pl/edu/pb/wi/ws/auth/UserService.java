package pl.edu.pb.wi.ws.auth;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import pl.edu.pb.wi.user.dto.Role;
import pl.edu.pb.wi.ws.resposne.User;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface UserService {
    @WebMethod
    Role login(@WebParam(name = "credentials") User user);

    @WebMethod
    String register(@WebParam(name = "credentials") User user);
}
