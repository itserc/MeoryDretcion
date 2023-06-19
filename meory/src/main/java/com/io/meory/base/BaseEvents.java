package com.io.meory.base;

import com.io.meory.utils.TypeToken;

public class BaseEvents {

    TypeToken<?> typeToken;

    Object event;

    private BaseEvents(TypeToken<?> typeToken, Object event) {
        this.typeToken = typeToken;
        this.event = event;
    }

    public static BaseEvents creat(TypeToken<?> typeToken, Object event) {
        return new BaseEvents(typeToken, event);
    }

    public TypeToken<?> getTypeToken() {
        return typeToken;
    }

    public Object getEvent() {
        return event;
    }
}
