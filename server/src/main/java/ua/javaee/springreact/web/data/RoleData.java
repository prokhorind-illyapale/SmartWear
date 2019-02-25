package ua.javaee.springreact.web.data;

/**
 * Created by kleba on 11.02.2019.
 */
public class RoleData {

    private String roleName;

    public RoleData() {
    }

    public RoleData(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
