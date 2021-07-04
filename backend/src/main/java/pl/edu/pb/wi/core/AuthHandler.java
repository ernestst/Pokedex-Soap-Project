package pl.edu.pb.wi.core;

import com.sun.xml.ws.transport.Headers;
import java.util.List;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;
import pl.edu.pb.wi.core.privileges.PrivilegeValidator;
import pl.edu.pb.wi.user.dao.UserDAO;
import pl.edu.pb.wi.user.dto.Role;
import pl.edu.pb.wi.user.dto.UserData;

public class AuthHandler implements SOAPHandler<SOAPMessageContext> {

    private final static String HTTP_HEADERS = "javax.xml.ws.http.request.headers";
    private final static String WSDL_OPERATION = "javax.xml.ws.wsdl.operation";

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        boolean isRequest = (boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (!isRequest) {
            SOAPMessage message = context.getMessage();
            if (context.containsKey(HTTP_HEADERS)) {
                Headers headers = (Headers) context.get(HTTP_HEADERS);

                QName operationName = (QName) context.get(WSDL_OPERATION);
                Role requirementRole = PrivilegeValidator.getRoleForService(operationName.getLocalPart());
                if (requirementRole == null) {
                    return true;
                }

                String username = getHeaderField(headers, "username", message);
                String password = getHeaderField(headers, "password", message);

                UserData requestedUser = getRequestedUser(message, username);
                if (!requestedUser.getPassword().equals(password)) {
                    generateErrorMessage(message, "INVALID_PASSWORD");
                }

                if (!requestedUser.getRole().equals(requirementRole)) {
                    generateErrorMessage(message, "ACCESS_DENIED");
                }
            } else {
                generateErrorMessage(message, "INVALID_PASSWORD");
            }
        }

        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) { }

    private String getHeaderField(Headers headers, String field, SOAPMessage message) {
        List<String> usernames = headers.get(field);
        if (usernames == null || usernames.size() == 0) {
            generateErrorMessage(message, "NO_CREDENTIALS");
            return null;
        }

        return usernames.get(0);
    }

    private UserData getRequestedUser(SOAPMessage message, String username) {
        UserDAO dao = new UserDAO();
        UserData requestedUser = dao.getUser(username);
        if (requestedUser == null) {
            generateErrorMessage(message, "USER_DOES_NOT_EXIST");
        }

        return requestedUser;
    }

    private void generateErrorMessage(SOAPMessage message, String content) throws SOAPFaultException {
        try {
            SOAPBody body = message.getSOAPPart().getEnvelope().getBody();
            SOAPFault fault = body.addFault();
            fault.setFaultString(content);

            throw new SOAPFaultException(fault);
        } catch (SOAPException ignored) {}
    }
}
