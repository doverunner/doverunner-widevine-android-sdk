package com.pallycon.pallyconsample

import android.content.Context
import com.google.android.exoplayer2.offline.DownloadManager
import com.google.android.exoplayer2.ui.DownloadNotificationHelper
import com.pallycon.widevine.model.PallyConCallback
import com.pallycon.widevine.model.PallyConDrmConfigration
import com.pallycon.widevine.model.PallyConEventListener
import com.pallycon.widevine.sdk.PallyConWvSDK
import java.io.File

class ObjectSingleton {
    val contents = mutableListOf<ContentData>()
    val downloadChannel = "download_channel"
    var context: Context? = null

    companion object {
        private var instance: ObjectSingleton? = null

        fun getInstance(): ObjectSingleton {
            return instance ?: synchronized(this) {
                instance ?: ObjectSingleton().also {
                    instance = it
                }
            }
        }
    }

    fun createContents(context: Context, pallyConEventListener: PallyConEventListener?, pallyConCallback: PallyConCallback?) {
        this.context = context

        val fi = context.getExternalFilesDir(null) ?: context.filesDir
        val localPath = File(fi, "downloads").toString()

        val config = PallyConDrmConfigration(
            "DEMO",
            "eyJrZXlfcm90YXRpb24iOmZhbHNlLCJyZXNwb25zZV9mb3JtYXQiOiJvcmlnaW5hbCIsInVzZXJfaWQiOiJ0ZXN0VXNlciIsImRybV90eXBlIjoid2lkZXZpbmUiLCJzaXRlX2lkIjoiREVNTyIsImhhc2giOiJpSGlpQmM3U1QrWTR1T0h1VnVPQVNmNU1nTDVibDJMb1FuNzNHREtcLzltbz0iLCJjaWQiOiJtdWx0aXRyYWNrcyIsInBvbGljeSI6IjlXcUlXa2RocHhWR0s4UFNJWWNuSnNjdnVBOXN4Z3ViTHNkK2FqdVwvYm9tUVpQYnFJK3hhZVlmUW9jY2t2dUVmQWFxZFc1aFhnSk5nY1NTM2ZTN284TnNqd3N6ak11dnQrMFF6TGtaVlZObXgwa2VmT2Uyd0NzMlRJVGdkVTRCdk45YWJoZDByUWtNSXJtb0llb0pIcUllSGNSdlZmNlQxNFJtVEFERXBDWTQ2NHdxamNzWjA0Uk82Zm90Nm5yZjhXSGZ3QVNjek9kV1d6QStFRlRadDhRTWw5SFRueWVYK1g3YXp1Y2VmQjJBd2V0XC9hQm0rZXpmUERodFZuaUhsSiIsInRpbWVzdGFtcCI6IjIwMjItMDgtMDVUMDY6MDM6MjJaIn0="
        )
        val data = com.pallycon.widevine.model.ContentData(
            contentId = "multitracks",
            url = "https://contents.pallycon.com/DEV/sglee/multitracks/dash/stream.mpd",
            localPath = localPath,
            drmConfig = config,
            cookie = null
        )
        val wvSDK = PallyConWvSDK.createPallyConWvSDK(
            context,
            data
        )
        wvSDK.setPallyConEventListener(pallyConEventListener)
//        wvSDK.setPallyConCallback(pallyConCallback)
        wvSDK.setDownloadService(DemoDownloadService::class.java)

        val state = wvSDK.getDownloadState()
        contents.add(
            ContentData(
                "multitracks",
                state,
                state.toString(),
                data,
                wvSDK,
                null,
                "multitracks",
                "multitracks"
            )
        )

        val config2 = PallyConDrmConfigration(
            "DEMO",
            "eyJkcm1fdHlwZSI6IldpZGV2aW5lIiwic2l0ZV9pZCI6IkRFTU8iLCJ1c2VyX2lkIjoidGVzdFVzZXIiLCJjaWQiOiJkZW1vLWJiYi1zaW1wbGUiLCJwb2xpY3kiOiI5V3FJV2tkaHB4VkdLOFBTSVljbkp1dUNXTmlOK240S1ZqaTNpcEhIcDlFcTdITk9uYlh6QS9pdTdSa0Vwbk85c0YrSjR6R000ZkdCMzVnTGVORGNHYWdPY1Q4Ykh5c3k0ZHhSY2hYV2tUcDVLdXFlT0ljVFFzM2E3VXBnVVdTUCIsInJlc3BvbnNlX2Zvcm1hdCI6Im9yaWdpbmFsIiwia2V5X3JvdGF0aW9uIjpmYWxzZSwidGltZXN0YW1wIjoiMjAyMi0wOS0xOVQwNzo0Mjo0MFoiLCJoYXNoIjoiNDBDb1RuNEpFTnpZUHZrT1lTMHkvK2VIN1dHK0ZidUIvcThtR3VoaHVNRT0ifQ=="
        )
        val data2 = com.pallycon.widevine.model.ContentData(
            "https://contents.pallycon.com/DEMO/app/big_buck_bunny/dash/stream.mpd",
            localPath,
            config2
        )
        val wvSDK2 = PallyConWvSDK.createPallyConWvSDK(
            context,
            data2
        )
        val state2 = wvSDK2.getDownloadState()
        contents.add(
            ContentData(
                "basic content",
                state2,
                state2.toString(),
                data2,
                wvSDK2,
                null,
                "demo-bbb-simple",
                "demo-bbb-simple"
            )
        )

        val config3 = PallyConDrmConfigration(
            "DEMO",
            "eyJrZXlfcm90YXRpb24iOmZhbHNlLCJyZXNwb25zZV9mb3JtYXQiOiJvcmlnaW5hbCIsInVzZXJfaWQiOiJwYWxseWNvbiIsImRybV90eXBlIjoid2lkZXZpbmUiLCJzaXRlX2lkIjoiREVNTyIsImhhc2giOiJkNTBDSVVUS1RwRDl6T3dGaU9DSysrXC83Q3pLOStZN3NkcHFhUUppdDJWQT0iLCJjaWQiOiJUZXN0UnVubmVyIiwicG9saWN5IjoiOVdxSVdrZGhweFZHSzhQU0lZY25Kc2N2dUE5c3hndWJMc2QrYWp1XC9ib21RWlBicUkreGFlWWZRb2Nja3Z1RWZBYXFkVzVoWGdKTmdjU1MzZlM3bzhNczB3QXNuN05UbmJIUmtwWDFDeTEyTkhwMlZPN1pMeFJvZDhVdkUwZnBFbUpYOUpuRDh6ZktkdE9RWk9UYXljK280RzNCT0xmU29OaFpWbkIwUGxEbW1rVk5jbXpndko2YloxdXBudjFcLzJFM2lXZXd3eklTNFVOQlhTS21zVUFCZnBRQjg4Q2VJYlZSM0hKZWJvcEpwZG1DTFFvRmtCT09DQU9qWElBOUVHIiwidGltZXN0YW1wIjoiMjAyMi0xMC0xMVQwNzowMToxN1oifQ=="
        )
        val data3 = com.pallycon.widevine.model.ContentData(
            contentId = "TestRunner_DASH",
            url = "https://contents.pallycon.com/TEST/PACKAGED_CONTENT/TEST_SIMPLE/dash/stream.mpd",
            localPath = localPath,
            drmConfig = config3
        )
        val wvSDK3 = PallyConWvSDK.createPallyConWvSDK(
            context,
            data3
        )
        val state3 = wvSDK3.getDownloadState()
        contents.add(
            ContentData(
                "short duration content",
                state3,
                state3.toString(),
                data3,
                wvSDK3,
                null,
                "TestRunner",
                "TestRunner"
            )
        )

        val config4 = PallyConDrmConfigration(
            "DEMO",
            "eyJrZXlfcm90YXRpb24iOmZhbHNlLCJyZXNwb25zZV9mb3JtYXQiOiJvcmlnaW5hbCIsInVzZXJfaWQiOiJUZXN0UnVubmVyIiwiZHJtX3R5cGUiOiJ3aWRldmluZSIsInNpdGVfaWQiOiJERU1PIiwiaGFzaCI6IjdqcjNOb2w4N1l1U29hNlk2RXJCMFVoMkNIM1pWR2VBUVNtMTh5YkZheFU9IiwiY2lkIjoiVGVzdFJ1bm5lciIsInBvbGljeSI6IjlXcUlXa2RocHhWR0s4UFNJWWNuSnNjdnVBOXN4Z3ViTHNkK2FqdVwvYm9tUVpQYnFJK3hhZVlmUW9jY2t2dUVmQWFxZFc1aFhnSk5nY1NTM2ZTN284TnNqd3N6ak11dnQrMFF6TGtaVlZObXgwa2VmT2Uyd0NzMlRJVGdkVTRCdk45YWJoZDByUWtNSXJtb0llb0pIcUllSGNSdlZmNlQxNFJtVEFERXBDWTdQSGZQZlwvVkZZXC9WYlh1eFhcL1dUdFZTRkpTSDlzeHB3UUlRWHI1QjZSK0FhYWZTZlZYU0trNG1WRmxlMlBcL3Byamg1OCtiT2hidFU0NDRseDlvcmVHNSIsInRpbWVzdGFtcCI6IjIwMjMtMDUtMTlUMDI6MTM6NTlaIn0="
        )
        val data4 = com.pallycon.widevine.model.ContentData(
            contentId = "TestRunner_HLS",
            url = "https://contents.pallycon.com/TEST/PACKAGED_CONTENT/TEST_SIMPLE/cmaf/master.m3u8",
            localPath = localPath,
            drmConfig = config4,
        )
        val wvSDK4 = PallyConWvSDK.createPallyConWvSDK(
            context,
            data4
        )
        val state4 = wvSDK4.getDownloadState()
        contents.add(
            ContentData(
                "hls",
                state4,
                state4.toString(),
                data4,
                wvSDK4,
                null,
                "TestRunner",
                "TestRunner"
            )
        )
    }

    fun getDownloadNotificationHelper(): DownloadNotificationHelper {
        return DownloadNotificationHelper(this.context!!, downloadChannel)
    }

    fun getDownloadManager(): DownloadManager? {
        return if (contents.size > 0) {
            contents[0].wvSDK.getDownloadManager()
        } else {
            null
        }
    }
}