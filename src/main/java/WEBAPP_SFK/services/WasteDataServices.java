package WEBAPP_SFK.services;


import WEBAPP_SFK.models.Stats;
import WEBAPP_SFK.models.WasteData;
import WEBAPP_SFK.services.connect.DataBaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WasteDataServices extends DataBaseRepository<WasteData> {
    private static WasteDataServices wasteDataServices;

    public WasteDataServices() {
        super(WasteData.class);
    }

    public static WasteDataServices getInstance() {
        if(wasteDataServices==null){
            wasteDataServices = new WasteDataServices();
        }
        return wasteDataServices;
    }
    public List<WasteData> findAllWasteDataByBranchOffice(Date date, long idBranchOffice) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateAux = dateFormat.parse(dateFormat.format(date));
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createNativeQuery("" +
                "SELECT DISTINCT * FROM WASTEDATA W " +
                "INNER JOIN CONTAINER C ON W.CONTAINERID = C.CONTAINERID " +
                "INNER JOIN BRANCHOFFICE B ON C.BRANCHOFFICE_ID = B.ID " +
                "WHERE CONVERT(W.SENDDATE,date) =:date AND B.ID = :idBranchOffice",WasteData.class);
        query.setParameter("date", dateAux);
        query.setParameter("idBranchOffice",idBranchOffice);
        return query.getResultList();
    }
    public Stats wasteFruitsWeight(Date date, long idBranchOffice) throws ParseException {
        float[] wasteWeight = new float[24];
        float totalWasteWeight = 0.0F;
        String branchOfficeWasteGenerated = "";
        DateFormat hourFormat = new SimpleDateFormat("HH");
        List<WasteData> stats = findAllWasteDataByBranchOffice(date,idBranchOffice);
        int i;
        for (int j = 0; j < stats.size(); j++) {
            WasteData w = stats.get(j);
            i = Integer.parseInt(hourFormat.format(w.getSendDate()));
            wasteWeight[i] += w.getWasteData();
            totalWasteWeight += w.getWasteData();
            String city = w.getContainer().getBranchOffice().getAddress().getCity();
            String direction = w.getContainer().getBranchOffice().getAddress().getDirection();
            branchOfficeWasteGenerated = city + " " + "(" + direction + ")";
        }
        Map<String, Object> wasteWeightMap = new HashMap();
        wasteWeightMap.put("total",totalWasteWeight);
        wasteWeightMap.put("byHour",wasteWeight);
        wasteWeightMap.put("branchOfficeWasteGenerated",branchOfficeWasteGenerated);
        return new Stats(wasteWeightMap);
    }
}

