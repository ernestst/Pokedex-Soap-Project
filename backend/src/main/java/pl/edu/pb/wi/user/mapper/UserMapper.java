package pl.edu.pb.wi.user.mapper;

import pl.edu.pb.wi.core.mapper.Mapper;
import pl.edu.pb.wi.user.dto.Role;
import pl.edu.pb.wi.user.dto.UserData;
import pl.edu.pb.wi.ws.resposne.User;

public class UserMapper implements Mapper<UserData, User> {
    @Override
    public UserData map(User wsResponse) {
        UserData user = new UserData();
        user.setLogin(wsResponse.getLogin());
        user.setPassword(wsResponse.getPassword());
        user.setRole(Role.USER);

        return user;
    }

    @Override
    public User map(UserData entity) {
        return null;
    }
}
