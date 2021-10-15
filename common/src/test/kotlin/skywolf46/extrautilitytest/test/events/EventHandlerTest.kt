package skywolf46.extrautilitytest.test.events

import org.junit.Assert
import org.junit.Test
import skywolf46.extrautility.ExtraUtilityCore
import skywolf46.extrautility.annotations.handler.ExtraEventHandler
import skywolf46.extrautilitytest.test.events.data.DeepTestData
import skywolf46.extrautilitytest.test.events.data.TestData

class EventHandlerTest {
    companion object {
        @ExtraEventHandler
        fun testInvoke(data: TestData) {
            data.data = "Test3"
        }


        @ExtraEventHandler
        fun testInvoke(data: DeepTestData) {
            data.secondary = 40
            println("Test!")
        }

        @ExtraEventHandler(baseEvent = TestData::class)
        fun testInvokeSecond(data: TestData) {
            data.data = "Test4"
        }
    }

    @Test
    fun invokeAndListen() {
        ExtraUtilityCore.scanLegacyEventHandlers()
        val data = TestData("Test2")
        ExtraUtilityCore.callEvent(data)
        Assert.assertEquals("Test3", data.data)
    }

    @Test
    fun invokeImplementationAndListen() {
        ExtraUtilityCore.scanLegacyEventHandlers()
        val data = DeepTestData(20, "Test2")
        ExtraUtilityCore.callEvent(data)
        Assert.assertEquals(40, data.secondary)
        Assert.assertEquals("Test3", data.data)
    }
}