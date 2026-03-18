package photo.editor.photoeditor.filtersforpictu.screens

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import photo.editor.photoeditor.filtersforpictu.R
import photo.editor.photoeditor.filtersforpictu.ui.components.SquareButton
import photo.editor.photoeditor.filtersforpictu.ui.theme.GameFont
import photo.editor.photoeditor.filtersforpictu.ui.theme.TealDark

@Composable
fun PrivacyPolicyScreen(
    onBackClick: () -> Unit
) {
    var loadWeb by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.bg_vertical),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                SquareButton(
                    btnRes = R.drawable.back_button,
                    btnMaxWidth = 0.12f,
                    modifier = Modifier.align(Alignment.CenterStart),
                    btnClickable = onBackClick
                )

                Text(
                    text = "Privacy Policy",
                    color = TealDark,
                    fontSize = 32.sp,
                    fontFamily = GameFont,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            AndroidView(
                factory = { context ->
                    FrameLayout(context).apply {
                        val webView = WebView(context).apply {
                            settings.setSupportZoom(true)
                            settings.builtInZoomControls = true
                            settings.displayZoomControls = false
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true
                            webViewClient = object : WebViewClient() {
                                override fun onPageFinished(view: WebView?, url: String?) {
                                    super.onPageFinished(view, url)
                                    loadWeb = false
                                }

                                override fun shouldOverrideUrlLoading(
                                    view: WebView?,
                                    request: WebResourceRequest?
                                ): Boolean {
                                    return false
                                }
                            }
                            loadUrl("https://telegra.ph/Privacy-Policy-for-Icee-Fishying-03-16")
                        }
                        addView(
                            webView, FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                FrameLayout.LayoutParams.MATCH_PARENT
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
        }

        if (loadWeb) {
            LinearProgressIndicator(
                color = Color.Blue,
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun PolicySection(title: String, text: String) {
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = title,
        color = Color(0xFF4FC3F7),
        fontSize = 18.sp,
        fontFamily = GameFont
    )
    Spacer(modifier = Modifier.height(6.dp))
    Text(
        text = text,
        color = Color.White.copy(alpha = 0.85f),
        fontSize = 14.sp,
        fontFamily = GameFont,
        lineHeight = 20.sp
    )
}