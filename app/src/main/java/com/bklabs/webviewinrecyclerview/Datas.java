package com.bklabs.webviewinrecyclerview;

import java.util.ArrayList;
import java.util.List;

public class Datas {
    public static String table = "test table\n" +
            "<table>\n" +
            "<tr>\n" +
            "<td>Row 1, Column 1</td>\n" +
            "<td>Row 1, Column 2</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td>Row 2, Column 1</td>\n" +
            "<td>Row 2, Column 2</td>\n" +
            "</tr>\n" +
            "</table>";
    public static String str = "<div>I'm hurting, baby, I'm broken down\n" +
            "I need your loving, loving, I need it now</div>";
    public static String str2 = "Sugar";

    public static List<ItemData> createData() {
        List<ItemData> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            ItemData itemData = new ItemData();
            itemData.setName("Test " + i);
            itemData.setTime("11/11/2011");

            if (i % 2 == 0) {
                itemData.setContent(str);
            } else {
                itemData.setContent(str2);
            }
            list.add(itemData);
        }
        return list;
    }
}
