package com.lostincontext.model;

import com.google.gson.Gson;
import com.google.gson.InstanceCreator;

/**
 * Created by STrabelsi on 07/07/2016.
 */

public interface DataSerializer<T> extends InstanceCreator<T> {

    String serialize(Gson gson);

    T deserialize(Gson gson, String json);
}
