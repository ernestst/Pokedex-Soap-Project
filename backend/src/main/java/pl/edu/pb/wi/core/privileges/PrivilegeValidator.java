package pl.edu.pb.wi.core.privileges;

import org.jboss.logging.Logger;
import pl.edu.pb.wi.user.dto.Role;

public class PrivilegeValidator {
    public static Role getRoleForService(String localPart) {
        Logger.getLogger(PrivilegeValidator.class.getName()).warn(localPart);
        switch (localPart) {
            case "savePokemonEvolution":
            case "savePokemon":
            case "uploadImage":
                return Role.ADMINISTRATOR;
            case "getUserTeamMembers":
            case "addUserTeamMember":
            case "removeUserTeamMember":
                return Role.USER;
            default:
                return null;
        }
    }
}
