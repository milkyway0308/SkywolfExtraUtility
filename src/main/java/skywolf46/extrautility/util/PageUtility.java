package skywolf46.extrautility.util;

import java.util.ArrayList;
import java.util.List;

public class PageUtility {
    public static <T> int pages(List<T> list, int itemPerPage) {
        if (list.size() == 0)
            return 0;
        if (list.size() <= itemPerPage)
            return 1;
        return list.size() / itemPerPage + (list.size() % itemPerPage == 0 ? 0 : 1);
    }

    public static <T> List<T> splitPage(List<T> list, int itemPerPage, int page) {
        int listPage = pages(list, itemPerPage);
        if (listPage < page)
            return new ArrayList<>();
        return list.subList((page - 1) * itemPerPage, Math.min(list.size(), page * itemPerPage));
    }
}
