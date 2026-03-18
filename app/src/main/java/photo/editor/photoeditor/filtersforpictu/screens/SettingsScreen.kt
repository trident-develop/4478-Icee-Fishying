package photo.editor.photoeditor.filtersforpictu.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import photo.editor.photoeditor.filtersforpictu.R
import photo.editor.photoeditor.filtersforpictu.ui.components.MenuButton
import photo.editor.photoeditor.filtersforpictu.ui.components.SquareButton
import photo.editor.photoeditor.filtersforpictu.ui.theme.GameFont
import photo.editor.photoeditor.filtersforpictu.ui.theme.TealDark

@Composable
fun SettingsScreen(
    musicEnabled: Boolean,
    soundEnabled: Boolean,
    onMusicToggle: () -> Unit,
    onSoundToggle: () -> Unit,
    onHowToPlayClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onBackClick: () -> Unit
) {
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
                    text = "Settings",
                    color = TealDark,
                    fontSize = 36.sp,
                    fontFamily = GameFont,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Music toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Music",
                    color = TealDark,
                    fontSize = 28.sp,
                    fontFamily = GameFont
                )
                SquareButton(
                    btnRes = if (musicEnabled) R.drawable.music_on_button else R.drawable.music_off_button,
                    btnMaxWidth = 0.24f,
                    cooldownMillis = 0L,
                    btnClickable = onMusicToggle
                )
            }

            // Sound toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Sound",
                    color = TealDark,
                    fontSize = 28.sp,
                    fontFamily = GameFont
                )
                SquareButton(
                    btnRes = if (soundEnabled) R.drawable.sound_on_button else R.drawable.sound_off_button,
                    btnMaxWidth = 0.24f,
                    cooldownMillis = 0L,
                    btnClickable = onSoundToggle
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            MenuButton(text = "How To Play", fontSize = 24.sp, onClick = onHowToPlayClick)
            MenuButton(text = "Privacy Policy", fontSize = 24.sp, onClick = onPrivacyPolicyClick)
        }
    }
}