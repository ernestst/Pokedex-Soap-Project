package pl.edu.pb.wi.ws.auth;

import javax.jws.WebService;
import pl.edu.pb.wi.user.dao.UserDAO;
import pl.edu.pb.wi.user.dto.Role;
import pl.edu.pb.wi.user.dto.UserData;
import pl.edu.pb.wi.user.mapper.UserMapper;
import pl.edu.pb.wi.ws.resposne.User;

@WebService(endpointInterface = "pl.edu.pb.wi.ws.auth.UserService")
public class UserServiceImpl implements UserService {

    private final UserMapper mapper = new UserMapper();

    @Override
    public Role login(User user) {
        UserDAO dao = new UserDAO();
        UserData requestedUser = dao.getUser(user.getLogin());

        if (requestedUser != null) {
            if (requestedUser.getPassword().equals(user.getPassword())) {
                return requestedUser.getRole();
            }

            return null;
        }

        return null;
    }

    @Override
    public String register(User user) {
        UserDAO dao = new UserDAO();
        UserData requestedUser = dao.getUser(user.getLogin());

        if (requestedUser == null) {
            dao.saveEntity(mapper.map(user));
            return "SUCCESS";
        }

        return "USER_ALREADY_EXISTS";
    }
}
