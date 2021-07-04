package pl.edu.pb.wi.user.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import pl.edu.pb.wi.core.dao.AbstractDAO;
import pl.edu.pb.wi.user.dto.UserData;

public class UserDAO extends AbstractDAO<UserData> {
    public UserDAO() {
        super(UserData.class);
    }

    public UserData getUser(String login) {
        List<String> conditions = Collections.singletonList("e.login = :login");
        Map<String, Object> parameters = Collections.singletonMap("login", login);
        return getEntity(conditions, parameters);
    }
}
