package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Person;
import WEBAPP_SFK.services.connect.DataBaseRepository;

public class PersonServices extends DataBaseRepository<Person> {
    public PersonServices() {
        super(Person.class);
    }

}
