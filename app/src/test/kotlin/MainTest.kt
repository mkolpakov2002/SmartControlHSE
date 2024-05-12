import kotlin.test.Test

class MainTest {

    @Test
    fun `test main`() {
        ArduinoTest().`test packet creation`()
        ArduinoTest().`test packet serialization`()
        ArduinoTest().`test packet configuration serialization`()
        ArduinoTest().`test packet creation with custom data`()
        ROSTest().`test ROSInfo serialization and deserialization`()
        YandexApiExtensionsTest().`test mapManageDeviceCapabilitiesStateRequest`()
        YandexApiExtensionsTest().`test mapManageGroupCapabilitiesStateRequest`()
        YandexApiExtensionsTest().`test mapDeviceStateResponse`()
        YandexApiExtensionsTest().`test mapUserInfoResponse`()
        UniversalSchemeTest().`test SmartHome configuration`()
    }
}