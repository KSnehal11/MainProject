package com.OptimumPool.OfferRide.Services;

import com.OptimumPool.OfferRide.Configuration.OfferDTO;
import com.OptimumPool.OfferRide.Exception.OfferRideNotFound;
import com.OptimumPool.OfferRide.Model.Offerride;
import com.OptimumPool.OfferRide.Repository.OfferRideRepo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service

public class OfferService implements IOfferService {

    @Autowired
    OfferRideRepo orepo;

    List<Offerride> offerrideList ;
    private String name ;
    private int id;
    @Override
    public Offerride addOffer(Offerride of) {
        return orepo.save(of);
    }

    @Override
    public Offerride updateRide(Offerride or , int id) throws OfferRideNotFound {
        Offerride newRide;
        if (orepo.findById(id).isEmpty()) {
            throw new OfferRideNotFound();
        }
        else{
            newRide = orepo.findById(id).get();
            newRide.setOffer_id(or.getOffer_id());
            newRide.setCar_info(or.getCar_info());
            newRide.setCar_owner(or.getCar_owner());
            newRide.setDate(or.getDate());
            newRide.setDistance(or.getDistance());
            newRide.setTime(or.getTime());
            newRide.setWayPoint(or.getWayPoint());
            newRide.setCharge_per_km(or.getCharge_per_km());

            orepo.save(newRide);
//            return newRide;
        }
        return newRide;


    }

    public List<Offerride> getRide(){
        offerrideList = orepo.findAll();
        return offerrideList;
    }
    @Override
    public boolean updateSeats(int numSeats , int id){
        Offerride getObjRide = orepo.findById(id).get();
        int want_to_book = numSeats;
        int avl_seat = getObjRide.getCar_info().getAvl_seats();

        if(want_to_book>avl_seat)
            return false;
        else {
            avl_seat=avl_seat-want_to_book;
            getObjRide.getCar_info().setAvl_seats(avl_seat);
        }
        orepo.save(getObjRide);
        return true;
    }

    /*how to listen from rabit */

    /*
    @RabbitListener(queues = "token_queue")
    public void  recieveDataFromRabbitMQ(TokenDTO tdto){


        name = (String) tdto.getJsonObject().get("name");
        id = (int) tdto.getJsonObject().get("id");
        System.out.println("string " +name +" "+id);
    }

     */


    /* how to send the data from rabbit mq*/

     @Autowired(required = false)
    private RabbitTemplate rt;

    @Autowired(required = false)
    private DirectExchange exchange;

    public void sendDataToConsumer(){
        JSONObject data =new JSONObject();

        data.put("list" , offerrideList);
        OfferDTO odto = new OfferDTO();
        odto.setOfferList(getRide());
//        odto.setJsonObject(data);
        rt.convertAndSend(exchange.getName(),"mq_route",odto);
    }

}
