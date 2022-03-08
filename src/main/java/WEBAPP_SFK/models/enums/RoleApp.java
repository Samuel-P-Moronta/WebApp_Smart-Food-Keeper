package WEBAPP_SFK.models.enums;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public enum RoleApp implements io.javalin.core.security.Role{
    ROLE_ADMIN,
    ROLE_EMPLOYEE,
    ROLE_ROOT,

}
