package pl.edu.pb.wi.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface SystemStatusService {
    @WebMethod
    String getStatus();
}
