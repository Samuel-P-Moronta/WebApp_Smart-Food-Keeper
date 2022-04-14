package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Form;
import WEBAPP_SFK.services.connect.DataBaseRepository;
import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class FormServices extends DataBaseRepository<Form> {
    private static FormServices formServices;
    public FormServices() {
        super(Form.class);
    }


    public static FormServices getInstance() {
        if(formServices==null){
            formServices = new FormServices();
        }
        return formServices;
    }
}
