package com.OptimumPool.OfferRide.Controller;

import com.OptimumPool.OfferRide.Exception.OfferRideNotFound;
import com.OptimumPool.OfferRide.Model.Offerride;
import com.OptimumPool.OfferRide.Services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class OfferRideController {

    @Autowired
    private OfferService oService;




    @PostMapping("offerride")
    public ResponseEntity<?> addOfferRideDb(@RequestBody Offerride f){
        return new ResponseEntity<>(oService, HttpStatus.CREATED);
    }


    @PutMapping("updateRide/{id}")
    public ResponseEntity<?>  updateOfferRide(@RequestBody Offerride of , @PathVariable int id ) throws OfferRideNotFound {
        try {
            oService.updateRide(of,id);
            return new ResponseEntity<>("ok",HttpStatus.OK);
        } catch(OfferRideNotFound e) {
            throw new OfferRideNotFound();
        }
        catch(Exception e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("offerride/request/{no_of_seats}/{offer_id}")
    public ResponseEntity<?> updateNumberOfSeats(@PathVariable int no_of_seats , @PathVariable int offer_id){
        return new ResponseEntity<>(oService.updateSeats(no_of_seats,offer_id) , HttpStatus.OK);
    }

    @GetMapping("offerride")
    public  ResponseEntity<?> getUser(){
        return  new ResponseEntity<>(oService.getRide(),HttpStatus.OK);
    }

    @GetMapping("send_data")
    public String sendData(){
        oService.sendDataToConsumer();
        return "data send successfully";
    }
}
