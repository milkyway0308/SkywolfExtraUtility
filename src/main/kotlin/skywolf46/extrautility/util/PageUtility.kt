package skywolf46.extrautility.util

import java.util.ArrayList
import kotlin.math.min


object PageUtility {
    fun <T> pages(list: List<T>, itemPerPage: Int): Int {
        if (list.isEmpty()) return 0
        return if (list.size <= itemPerPage) 1 else list.size / itemPerPage + if (list.size % itemPerPage == 0) 0 else 1
    }

    fun <T> splitPage(list: List<T>, itemPerPage: Int, page: Int): List<T> {
        val listPage = pages(list, itemPerPage)
        return if (listPage < page) ArrayList() else list.subList(
            (page - 1) * itemPerPage,
            min(list.size, page * itemPerPage)
        )
    }
}