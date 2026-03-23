package photo.editor.photoeditor.filtersforpictu.model.gray4.push

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import photo.editor.photoeditor.filtersforpictu.model.gray4.POSTBACK_API_URL
import photo.editor.photoeditor.filtersforpictu.model.gray4.POSTBACK_FCM_TOKEN_KEY
import photo.editor.photoeditor.filtersforpictu.model.gray4.POSTBACK_TRACKING_ID_KEY
import photo.editor.photoeditor.filtersforpictu.model.gray4.PUSH_NOTIFICATION_API_FCM_TOKEN_KEY
import photo.editor.photoeditor.filtersforpictu.model.gray4.PUSH_NOTIFICATION_API_GADID_KEY
import photo.editor.photoeditor.filtersforpictu.model.gray4.PUSH_NOTIFICATION_API_URL
import kotlinx.coroutines.tasks.await
import photo.editor.photoeditor.filtersforpictu.utils.dfg45gtedfebvwe
import retrofit2.Retrofit
import java.util.Locale

class PushRegistrationManager(private val context: android.content.Context) {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://$PUSH_NOTIFICATION_API_URL")
        .build()

    private val apiService = retrofit.create(dfg45gtedfebvwe::class.java)

    suspend fun registerDevice() {
        
        try {
            val gadid = try { FirebaseAnalytics.getInstance(context).appInstanceId.await() } catch (e: Exception) { "error" }
            val rawFcmToken = try { FirebaseMessaging.getInstance().token.await() } catch (e: Exception) { "error" }

            val params = mapOf(
                PUSH_NOTIFICATION_API_GADID_KEY to gadid,
                PUSH_NOTIFICATION_API_FCM_TOKEN_KEY to rawFcmToken
            )

            val fullUrl = "https://$PUSH_NOTIFICATION_API_URL"
            val lang = Locale.getDefault().toLanguageTag()

            val response = apiService.fvh5yhngfr(fullUrl, params, lang)

        } catch (e: Exception) {
            
        }
    }

    suspend fun sendPostback(trackingId: String) {
        
        try {
            val rawFcmToken = try { FirebaseMessaging.getInstance().token.await() } catch (e: Exception) { "error" }

            val params = mapOf(
                POSTBACK_TRACKING_ID_KEY to trackingId,
                POSTBACK_FCM_TOKEN_KEY to rawFcmToken
            )

            val fullUrl = "https://$POSTBACK_API_URL"
            val response = apiService.rsthfsdgvbg4t(fullUrl, params)

            if (response.isSuccessful) {
                
            }
        } catch (e: Exception) {
            
        }
    }
}