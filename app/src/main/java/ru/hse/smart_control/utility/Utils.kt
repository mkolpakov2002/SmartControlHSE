package ru.hse.smart_control.utility

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.net.wifi.SupplicantState
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException
import java.net.ConnectException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.NetworkInterface
import java.net.Socket
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.charset.StandardCharsets
import java.util.Collections
import java.util.Locale


class Utils {

    companion object {
        fun isVisible(view: View?): Boolean {
            if (view == null) {
                return false
            }
            if (!view.isShown()) {
                return false
            }
            val actualPosition = Rect()
            view.getGlobalVisibleRect(actualPosition)
            val width = Resources.getSystem().displayMetrics.widthPixels
            val height = Resources.getSystem().displayMetrics.heightPixels
            val screen = Rect(0, 0, width, height)
            return actualPosition.intersect(screen)
        }

        /**
         * Get a string resource with its identifier.
         * @param context Current context
         * @param resourceName Identifier
         * @return String resource
         */
        fun getStringByName(context: Context, resourceName: String?): String {
            val packageName = context.packageName
            val resourceId = context.resources.getIdentifier(resourceName, "string", packageName)
            return context.resources.getString(resourceId)
        }

        fun hideSoftKeyboard(view: View) {
            val imm = view.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun pxToCm(context: Context, px: Float): Float {
            val dm = context.resources.displayMetrics
            return px / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 10f, dm)
        }

        fun cmToPx(context: Context, cm: Float): Float {
            val dm = context.resources.displayMetrics
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, cm * 10, dm)
        }

        fun dpToPx(context: Context, dp: Float): Float {
            val dm = context.resources.displayMetrics
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm)
        }

        fun getObjectFromClassName(relativeClassPath: String): Any? {
            val classPath = "com.schneewittchen.rosandroid$relativeClassPath"
            return try {
                val clazz = Class.forName(classPath)
                val constructor = clazz.getConstructor()
                constructor.newInstance()
            } catch (e: Exception) {
                null
            }
        }

        fun getResId(resName: String?, clazz: Class<*>): Int {
            return try {
                val idField = clazz.getDeclaredField(resName)
                idField.getInt(idField)
            } catch (e: Exception) {
                e.printStackTrace()
                -1
            }
        }

        /**
         * Convert byte array to hex string
         * @param bytes toConvert
         * @return hexValue
         */
        fun bytesToHex(bytes: ByteArray): String {
            val sbuf = StringBuilder()
            for (idx in bytes.indices) {
                val intVal = bytes[idx].toInt() and 0xff
                if (intVal < 0x10) sbuf.append("0")
                sbuf.append(Integer.toHexString(intVal).uppercase(Locale.getDefault()))
            }
            return sbuf.toString()
        }

        /**
         * Get utf8 byte array.
         * @param str which to be converted
         * @return  array of NULL if error was found
         */
        fun getUTF8Bytes(str: String): ByteArray? {
            return try {
                str.toByteArray(StandardCharsets.UTF_8)
            } catch (ex: Exception) {
                null
            }
        }

        /**
         * Load UTF8withBOM or any ansi text file.
         * @param filename which to be converted to string
         * @return String value of File
         * @throws IOException if error occurs
         */
        @Throws(IOException::class)
        fun loadFileAsString(filename: String?): String {
            val BUFLEN = 1024
            val `is` = BufferedInputStream(FileInputStream(filename), BUFLEN)
            return try {
                val baos = ByteArrayOutputStream(BUFLEN)
                val bytes = ByteArray(BUFLEN)
                var isUTF8 = false
                var read: Int
                var count = 0
                while (`is`.read(bytes).also { read = it } != -1) {
                    if (count == 0 && bytes[0] == 0xEF.toByte() && bytes[1] == 0xBB.toByte() && bytes[2] == 0xBF.toByte()) {
                        isUTF8 = true
                        baos.write(bytes, 3, read - 3) // drop UTF8 bom marker
                    } else {
                        baos.write(bytes, 0, read)
                    }
                    count += read
                }
                if (isUTF8) String(
                    baos.toByteArray(),
                    StandardCharsets.UTF_8
                ) else String(baos.toByteArray())
            } finally {
                try {
                    `is`.close()
                } catch (ignored: Exception) {
                }
            }
        }

        /**
         * Returns MAC address of the given interface name.
         * @param interfaceName eth0, wlan0 or NULL=use first interface
         * @return  mac address or empty string
         */
        fun getMACAddress(interfaceName: String?): String {
            try {
                val interfaces: List<NetworkInterface> =
                    Collections.list(NetworkInterface.getNetworkInterfaces())
                for (intf in interfaces) {
                    if (interfaceName != null) {
                        if (!intf.name.equals(interfaceName, ignoreCase = true)) continue
                    }
                    val mac = intf.getHardwareAddress() ?: return ""
                    val buf = StringBuilder()
                    for (aMac in mac) buf.append(String.format("%02X:", aMac))
                    if (buf.length > 0) buf.deleteCharAt(buf.length - 1)
                    return buf.toString()
                }
            } catch (ignored: Exception) {
            } // for now eat exceptions
            return ""
            /*try {
                // this is so Linux hack
                return loadFileAsString("/sys/class/net/" +interfaceName + "/address").toUpperCase().trim();
            } catch (IOException ex) {
                return null;
            }*/
        }

        /**
         * Get IP address from first non-localhost interface
         * @param useIPv4   true=return ipv4, false=return ipv6
         * @return  address or empty string
         */
        fun getIPAddress(useIPv4: Boolean): String {
            try {
                val interfaces: List<NetworkInterface> =
                    Collections.list(NetworkInterface.getNetworkInterfaces())
                for (intf in interfaces) {
                    val addrs: List<InetAddress> = Collections.list(intf.getInetAddresses())
                    for (addr in addrs) {
                        if (!addr.isLoopbackAddress) {
                            val sAddr = addr.hostAddress

                            //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                            val isIPv4 = sAddr.indexOf(':') < 0
                            if (useIPv4) {
                                if (isIPv4) return sAddr
                            } else {
                                if (!isIPv4) {
                                    val delim = sAddr.indexOf('%') // drop ip6 zone suffix
                                    return if (delim < 0) sAddr.uppercase(Locale.getDefault()) else sAddr.substring(
                                        0,
                                        delim
                                    ).uppercase(
                                        Locale.getDefault()
                                    )
                                }
                            }
                        }
                    }
                }
            } catch (ignored: Exception) {
            } // for now eat exceptions
            return ""
        }

        /**
         * Get IP address from first non-localhost interface
         * @param useIPv4   true=return ipv4, false=return ipv6
         * @return  address or empty string
         */
        fun getIPAddressList(useIPv4: Boolean): ArrayList<String> {
            val ipAddresses = ArrayList<String>()
            try {
                val interfaces: List<NetworkInterface> =
                    Collections.list(NetworkInterface.getNetworkInterfaces())
                for (intf in interfaces) {
                    val addrs: List<InetAddress> = Collections.list(intf.getInetAddresses())
                    for (addr in addrs) {
                        if (!addr.isLoopbackAddress) {
                            val sAddr = addr.hostAddress

                            //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                            val isIPv4 = sAddr.indexOf(':') < 0
                            if (useIPv4) {
                                if (isIPv4) ipAddresses.add(sAddr)
                            } else {
                                if (!isIPv4) {
                                    val delim = sAddr.indexOf('%') // drop ip6 zone suffix
                                    ipAddresses.add(
                                        if (delim < 0) sAddr.uppercase(Locale.getDefault()) else sAddr.substring(
                                            0,
                                            delim
                                        ).uppercase(
                                            Locale.getDefault()
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            } catch (ignored: Exception) {
            } // for now eat exceptions
            return ipAddresses
        }

        /**
         * Check if host is reachable.
         * @param host The host to check for availability. Can either be a machine name, such as "google.com",
         * or a textual representation of its IP address, such as "8.8.8.8".
         * @param port The port number.
         * @param timeout The timeout in milliseconds.
         * @return True if the host is reachable. False otherwise.
         */
        fun isHostAvailable(host: String?, port: Int, timeout: Int): Boolean {
            try {
                Socket().use { socket ->
                    val inetAddress = InetAddress.getByName(host)
                    val inetSocketAddress =
                        InetSocketAddress(inetAddress, port)
                    socket.connect(inetSocketAddress, timeout)
                    return true
                }
            } catch (e: ConnectException) {
                Log.e("Connection", "Failed do to unavailable network.")
            } catch (e: SocketTimeoutException) {
                Log.e("Connection", "Failed do to reach host in time.")
            } catch (e: UnknownHostException) {
                Log.e("Connection", "Unknown host.")
            } catch (e: IOException) {
                Log.e("Connection", "IO Exception.")
            }
            return false
        }

        fun getWifiSSID(wifiManager: WifiManager?): String? {
            if (wifiManager == null) return null
            val wifiInfo: WifiInfo
            wifiInfo = wifiManager.connectionInfo
            return if (wifiInfo.supplicantState == SupplicantState.COMPLETED) {
                wifiInfo.getSSID()
            } else null
        }

        fun numberToDegrees(number: Int): String {
            return "$number°"
        }

        fun degreesToNumber(degrees: String): Int {
            return degrees.substring(0, degrees.length - 1).toInt()
        }

        /**
         * Check if class of an object contains a field by a given field name.
         * @param object Object to check
         * @param fieldName Name of the field
         * @return Object class includes the field
         */
        fun doesObjectContainField(`object`: Any, fieldName: String): Boolean {
            val objectClass: Class<*> = `object`.javaClass
            for (field in objectClass.getFields()) {
                if (field.getName() == fieldName) {
                    return true
                }
            }
            return false
        }
    }

}

