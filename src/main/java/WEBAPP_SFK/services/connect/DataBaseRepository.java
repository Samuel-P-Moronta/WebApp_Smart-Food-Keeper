package WEBAPP_SFK.services.connect;


import WEBAPP_SFK.utilities.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

public class DataBaseRepository<T>{
    private static EntityManagerFactory entityManagerFactory;
    private Class<T> entityClass;

    public DataBaseRepository(Class<T> clase) {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        }
        this.entityClass = clase;
    }
    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    /**
     * Permite obtener el valor del campo
     */
    private Object getCampValue(T entity){
        Object valorCampo = null;
        if(entity != null){
            for(Field field : entity.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    try {
                        field.setAccessible(true);
                        valorCampo = field.get(entity);
                        System.out.println("Nombre del campo: "+field.getName());
                        System.out.println("Tipo del campo: "+field.getType().getName());
                        System.out.println("Valor del campo: "+valorCampo );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return valorCampo;
    }


    /**
     * CRUD BASICO IMPLEMENTANDO ORM
     * @param entity
     * @return
     * @throws IllegalArgumentException
     * @throws EntityExistsException
     * @throws PersistenceException
     */
    public boolean create(T entity) throws PersistenceException{
        boolean state = false;
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
            state = true;
        } catch (Exception e){
            Logger.getInstance().getLog(getClass()).error(String.format("Error creating entity - Exception message: %s", e.getMessage()));
            e.printStackTrace();
        } finally {
            em.close();
        }
        return state;
    }

    public boolean delete(Object entidadId) throws PersistenceException {
        boolean state = false;
        T entity;
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            entity = em.find(this.entityClass, entidadId);
            em.remove(entity);
            em.getTransaction().commit();
            state = true;
        }
        finally {
            em.close();
        }
        return state;
    }
    public boolean update(T entidad) throws PersistenceException {
        boolean state = false;
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(entidad);
            em.getTransaction().commit();
            state = true;
        }catch (Exception e){
            Logger.getInstance().getLog(getClass()).error(String.format("Error updating entity - %s", e.getMessage()));
        }
        finally {
            em.close();
        }

        return state;
    }

    public T find(Object id) throws PersistenceException {
        EntityManager em = getEntityManager();
        try{
            return em.find(entityClass, id);
        } catch (Exception ex){
            throw  ex;
        } finally {
            em.close();
        }
    }


    public List<T> findAll() throws PersistenceException {
        EntityManager em = getEntityManager();
        List<T> entity = null;
        try {
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(this.entityClass);
            criteriaQuery.select(criteriaQuery.from(this.entityClass));
            entity = em.createQuery(criteriaQuery).getResultList();
        } finally {
            em.close();
        }
        return entity;
    }

    /**
     * Configurar información de la conexión de Heroku.
     * Tomado de https://gist.github.com/mlecoutre/4088178
     * @return
     */
    private EntityManagerFactory getConfiguracionBaseDatosHeroku(){
        //Leyendo la información de la variable de ambiente de Heroku
        String databaseUrl = System.getenv("DATABASE_URL");
        StringTokenizer st = new StringTokenizer(databaseUrl, ":@/");
        //Separando las información del conexión.
        String dbVendor = st.nextToken();
        String userName = st.nextToken();
        String password = st.nextToken();
        String host = st.nextToken();
        String port = st.nextToken();
        String databaseName = st.nextToken();
        //creando la jbdc String
        String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s", host, port, databaseName);
        //pasando las propiedades.
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", jdbcUrl );
        properties.put("javax.persistence.jdbc.user", userName );
        properties.put("javax.persistence.jdbc.password", password );
        //
        return Persistence.createEntityManagerFactory("Heroku", properties);
    }


}
