package com.OptimumPool.Authentication.Configuration;

import org.json.simple.JSONObject;

public class TokenDTO {
    private JSONObject jsonObject;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public TokenDTO(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public TokenDTO() {
        super();
    }
}
