package com.laoshiren.base.collect.map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName MapMain
 * @Description
 * @Author laoshiren
 * @Date 17:13 2022/9/1
 */
public class MapMain {

    public static void main(String[] args) {
        //以访问顺序
        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>(16, 0.75f, true);
        linkedHashMap.put(1, "A");
        linkedHashMap.put(2, "B");
        linkedHashMap.put(3, "C");
        linkedHashMap.get(2);
        System.out.println(linkedHashMap);

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "A");
        map.put(2, "B");
        //compute会将指定Key的值进行重新计算，若Key不存在，v会返回null
        map.compute(1, (k, v) -> {
            //这里返回原来的value+M
            return v + "M";
        });
        String compute = map.compute(3, (k, v) -> v + "M");
        System.out.println("compute:" + compute);
        //当Key存在时存在则计算并赋予新的值
        map.computeIfPresent(1, (k, v) -> {
            //这里返回原来的value+M
            return v + "M";
        });
        compute = map.computeIfPresent(4, (k, v) -> v + "M");

        System.out.println("computeIfPresent:" + compute);


        String defaultString = map.getOrDefault(5, "default");
        System.out.println("default:" + defaultString);
        System.out.println("map:"+map);
    }

}
