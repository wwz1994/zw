package com.bus.factory;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.Map;

/**
 * @author wwz on 2019-06-27.
 */
public class MapWrapperFactory implements ObjectWrapperFactory {
    @Override
    public boolean hasWrapperFor(Object object) {
        return object != null && object instanceof Map;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        return new MyMapWrapper(metaObject, (Map) object);
    }
    static class MyMapWrapper extends MapWrapper {
        MyMapWrapper(MetaObject metaObject, Map<String, Object> map) {
            super(metaObject, map);
        }

        @Override
        public String findProperty(String name, boolean useCamelCaseMapping) {
            if (useCamelCaseMapping
                    && ((name.charAt(0) >= 'A' && name.charAt(0) <= 'Z')
                    || name.contains("_"))) {
                return underlineToCamelhump(name);
            }
            return name;
        }

        /**
         * 将下划线风格替换为驼峰风格
         *
         * @param inputString
         * @return
         */
        private String underlineToCamelhump(String inputString) {
            StringBuilder sb = new StringBuilder();

            boolean nextUpperCase = false;
            for (int i = 0; i < inputString.length(); i++) {
                char c = inputString.charAt(i);
                if (c == '_') {
                    if (sb.length() > 0) {
                        nextUpperCase = true;
                    }
                } else {
                    if (nextUpperCase) {
                        sb.append(Character.toUpperCase(c));
                        nextUpperCase = false;
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                }
            }
            return sb.toString();
        }
    }
}