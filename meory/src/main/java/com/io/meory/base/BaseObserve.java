package com.io.meory.base;

import com.io.meory.utils.PatternUtils;
import com.io.meory.utils.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseObserve<T, V extends BaseViewHolder> {

    ArrayList<Class> classes = new ArrayList<>();

    public BaseObserve() {
        autoAnaylsType();
    }

    private void autoAnaylsType() {

        try {
            Type superClass = getClass().getGenericSuperclass();
            Type[] oneOrder = ((ParameterizedType) superClass).getActualTypeArguments();

            //一阶
            for (Type type : oneOrder) {
                classes.add(PatternUtils.getClassFromType(type));
            }

            //二阶
            for (Type type : oneOrder) {
                if (type instanceof ParameterizedType) {
                    Type[] twoOrder = ((ParameterizedType) type).getActualTypeArguments();
                    for (Type typeInner : twoOrder)
                        classes.add(PatternUtils.getClassFromType(typeInner));
                }
            }

            //三阶级
            for (Type type : oneOrder) {
                if (type instanceof ParameterizedType) {
                    Type[] twoOrder = ((ParameterizedType) type).getActualTypeArguments();
                    for (Type typeInner : twoOrder) {
                        if (typeInner instanceof ParameterizedType) {
                            Type[] typeInnerInner = ((ParameterizedType) typeInner).getActualTypeArguments();
                            for (Type item : typeInnerInner) {
                                classes.add(PatternUtils.getClassFromType(item));
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void response(V vh, T t);

    public boolean match(TypeToken typeToken) {

        if (typeToken.getClasses() == null)
            return false;

        if (typeToken.getClasses().size() != classes.size())
            return false;

        for (int i = 0; i < classes.size(); i++) {

            if (classes.get(i) == List.class && List.class.isAssignableFrom((Class<?>) typeToken.getClasses().get(i)))
                continue;

            if (classes.get(i) == Map.class && Map.class.isAssignableFrom((Class<?>) typeToken.getClasses().get(i)))
                continue;

            if (classes.get(i) == Set.class && Set.class.isAssignableFrom((Class<?>) typeToken.getClasses().get(i)))
                continue;

            if (classes.get(i) != typeToken.getClasses().get(i))
                return false;
        }

        return true;
    }

}
