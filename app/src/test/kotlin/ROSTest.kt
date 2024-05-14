import com.schneewittchen.rosandroid.model.entities.ConfigEntity
import com.schneewittchen.rosandroid.model.entities.MasterEntity
import com.schneewittchen.rosandroid.model.entities.SSHEntity
import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity
import com.schneewittchen.rosandroid.widgets.battery.BatteryEntity
import com.schneewittchen.rosandroid.widgets.button.ButtonEntity
import com.schneewittchen.rosandroid.widgets.camera.CameraEntity
import com.schneewittchen.rosandroid.widgets.debug.DebugEntity
import com.schneewittchen.rosandroid.widgets.gps.GpsEntity
import com.schneewittchen.rosandroid.widgets.joystick.JoystickEntity
import com.schneewittchen.rosandroid.widgets.label.LabelEntity
import com.schneewittchen.rosandroid.widgets.logger.LoggerEntity
import com.schneewittchen.rosandroid.widgets.rqtplot.RqtPlotEntity
import com.schneewittchen.rosandroid.widgets.viz2d.Viz2DEntity
import ru.hse.smart_control.model.entities.universal.scheme.common.ros.ROSDeviceObject
import ru.hse.smart_control.model.entities.universal.scheme.common.ros.ROSInfo
import ru.hse.smart_control.model.entities.universal.scheme.common.ros.toJson
import ru.hse.smart_control.model.entities.universal.scheme.common.ros.toROSInfo
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ROSTest {
    @Test
    fun `test ROSInfo serialization and deserialization`() {
        val configEntity = ConfigEntity().apply {
            creationTime = System.currentTimeMillis()
            lastUsed = System.currentTimeMillis()
            name = "Test Config"
            isFavourite = true
        }
        val masterEntity = MasterEntity().apply {
            configId = 1
            ip = "192.168.1.101"
            port = 11311
        }
        val sshEntity = SSHEntity().apply {
            configId = 1
            ip = "192.168.1.102"
            port = 22
            username = "user"
            password = "password"
        }
        val uiWidgets = mutableListOf<BaseEntity>(
            ButtonEntity().apply {
                id = 1
                name = "Button Widget"
                type = "Button"
                configId = 1
                creationTime = System.currentTimeMillis()
                text = "Press Me"
                rotation = 90
            },
            JoystickEntity().apply {
                id = 2
                name = "Joystick Widget"
                type = "Joystick"
                configId = 1
                creationTime = System.currentTimeMillis()
                xAxisMapping = "Angular/Z"
                yAxisMapping = "Linear/X"
                xScaleLeft = -1f
                xScaleRight = 1f
                yScaleLeft = -1f
                yScaleRight = 1f
            },
            BatteryEntity().apply {
                id = 3
                name = "Battery Widget"
                type = "Battery"
                configId = 1
                creationTime = System.currentTimeMillis()
                displayVoltage = true
            },
            CameraEntity().apply {
                id = 4
                name = "Camera Widget"
                type = "Camera"
                configId = 1
                creationTime = System.currentTimeMillis()
                colorScheme = 0
                drawBehind = false
                useTimeStamp = false
            },
            DebugEntity().apply {
                id = 5
                name = "Debug Widget"
                type = "Debug"
                configId = 1
                creationTime = System.currentTimeMillis()
                numberMessages = 10
            },
            GpsEntity().apply {
                id = 6
                name = "GPS Widget"
                type = "Gps"
                configId = 1
                creationTime = System.currentTimeMillis()
            },
            LabelEntity().apply {
                id = 7
                name = "Label Widget"
                type = "Label"
                configId = 1
                creationTime = System.currentTimeMillis()
                text = "Sample Label"
            },
            LoggerEntity().apply {
                id = 8
                name = "Logger Widget"
                type = "Logger"
                configId = 1
                creationTime = System.currentTimeMillis()
            },
            RqtPlotEntity().apply {
                id = 9
                name = "RQT Plot Widget"
                type = "RqtPlot"
                configId = 1
                creationTime = System.currentTimeMillis()
            },
            Viz2DEntity().apply {
                id = 10
                name = "Viz2D Widget"
                type = "Viz2D"
                configId = 1
                creationTime = System.currentTimeMillis()
            }
        )

        val rosInfo = ROSInfo(devices = mutableListOf(ROSDeviceObject(
            configEntity = configEntity,
            masterEntity = masterEntity,
            sshEntity = sshEntity,
            uiWidgets = uiWidgets
        )))

        val json = rosInfo.toJson()

        val deserializedROSInfo = json.toROSInfo()

        assertEquals(rosInfo, deserializedROSInfo)

        assertEquals(configEntity, deserializedROSInfo.devices[0].configEntity)
        assertEquals(masterEntity, deserializedROSInfo.devices[0].masterEntity)
        assertEquals(sshEntity, deserializedROSInfo.devices[0].sshEntity)
        assertEquals(uiWidgets, deserializedROSInfo.devices[0].uiWidgets)
    }
}