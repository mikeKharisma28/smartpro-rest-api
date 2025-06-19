package com.juaracoding.smartpro_rest_api.util;

import com.juaracoding.smartpro_rest_api.dto.MenuLoginDTO;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransformationDataMenu {
    private int intListMenuSize = 0;
    private final List<Object> lObj = new ArrayList<>();
    private ModelMapper modelMapper = new ModelMapper();

    public record MenuGroupKey(String menuName, String featherIconTag, String menuPath) {}

    public List<Object> doTransformAksesMenuLogin(List<MenuLoginDTO> z){
        lObj.clear();
        intListMenuSize = z.size();

        Map<MenuGroupKey, List<MenuLoginDTO>> map = groupBy(z, MenuLoginDTO::getParentMenuName, MenuLoginDTO::getParentFeatherIconTags);
        Map<String, Object> map2 = null;
        for (Map.Entry<MenuGroupKey, List<MenuLoginDTO>> x : map.entrySet()) {
            if (x.getKey().menuName.equals("-")) {
                // check for parent
                for (MenuLoginDTO item : x.getValue()) {
                    if (!item.getHasChild()) {
                        map2 = new HashMap<>();
                        map2.put("parentMenu", new MenuGroupKey(item.getName(), item.getFeatherIconTag(), item.getPath()));
                        lObj.add(map2);
                    }
                }
            } else {
                map2 = new HashMap<>();
                map2.put("parentMenu", x.getKey());
                map2.put("subMenu", x.getValue());
                lObj.add(map2);
            }
        }
        return lObj;
    }

    public <E> Map<MenuGroupKey, List<E>> groupBy(List<E> list, Function<E, String> nameFunction, Function<E, String> iconFunction) {
        return Optional.ofNullable(list)
                .orElseGet(ArrayList::new)
                .stream().skip(0)
                .collect(Collectors.groupingBy(
                        e -> new MenuGroupKey(nameFunction.apply(e), iconFunction.apply(e), ""),
                        Collectors.toList()
                ));
    }
}
