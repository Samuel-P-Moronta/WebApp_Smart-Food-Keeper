package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Person;
import WEBAPP_SFK.models.User;
import WEBAPP_SFK.services.connect.DataBaseRepository;

public class PersonServices extends DataBaseRepository<Person> {

    private static PersonServices personServices;

    public PersonServices() {
        super(Person.class);
    }


    public static PersonServices getInstance() {
        if(personServices==null){
            personServices = new PersonServices();
        }
        return personServices;
    }


}
