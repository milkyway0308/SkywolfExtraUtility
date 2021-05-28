package skywolf46.extrautilitytest.test.reflection

import org.junit.Assert
import org.junit.Test
import skywolf46.extrautility.util.MethodUtil

class ReflectionTest {
    private fun test1() {

    }

    private fun test2() {

    }

    private fun test3() {

    }


    @Test
    fun checkPrivate() {
        Assert.assertEquals(
            3,
            MethodUtil.filter(ReflectionTest::class.java)
                .filter(MethodUtil.ReflectionMethodFilter.ACCESSOR_PRIVATE).methods.size
        )
    }
}