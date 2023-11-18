package com.OptimumPool.Authentication.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.OptimumPool.Authentication.Configuration.TokenDTO;
import com.OptimumPool.Authentication.Exception.UserAlreadyExist;
import com.OptimumPool.Authentication.Model.MQToken;
import com.OptimumPool.Authentication.Model.User;
import com.OptimumPool.Authentication.Repository.UserRepo;
import org.json.simple.JSONObject;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Service
public class UserService implements IService{

    String un;
    int ids;
    long phone_no;
    @Autowired
    private UserRepo urepo;

    public User addUser(User u) throws UserAlreadyExist {
        // TODO Auto-generated method stub
        if(!urepo.findById(u.getId()).isEmpty())
        {
            throw new UserAlreadyExist();
        }
        return urepo.save(u);

    }

    public Map<String, String> Login(User u) {
        // TODO Auto-generated method stub
        Map<String,String> token=new HashMap<String,String>();
         un=u.getName();
         ids=u.getId();
        System.out.println(un);
        User u1=urepo.findByNameAndPassword(u.getName(), u.getPassword());
        token=getToken(u);
        return token;
    }

    @Override
    public List<User> getAllUser() {
        List<User> u = urepo.findAll();
        return u;
    }

    public Map<String,String> getToken(User u)
    {
        String jwtToken=Jwts.builder().setSubject(u.getName()).setIssuedAt(new Date(0)).signWith(SignatureAlgorithm.HS256,"itcKey").compact();
        Map<String,String> jToken=new HashMap<String,String>();
        jToken.put("token", jwtToken);
        return jToken;
    }

    @Autowired( required = false)
    private RabbitTemplate rt;
    @Autowired( required = false)
    private DirectExchange exchange;

    public void sendDataToConsumer()
    {
        /*MQToken m = new MQToken(MQToken m );
        m.getMqtoken();*/
        JSONObject data=new JSONObject();
        data.put("name",un);
        int us = urepo.findByName(un).getId();
        System.out.println("***********" +us);
        data.put("id",us);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setJsonObject(data);
        rt.convertAndSend(exchange.getName(),"token_route",tokenDTO);
    }

}