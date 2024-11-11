package wolf.north.notepad.composables

import android.annotation.SuppressLint
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.KeyboardVoice
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Voicemail
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import wolf.north.notepad.R
import wolf.north.notepad.composables.ui.theme.NotepadTheme

class HomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotepadTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeNotepadScreen(modifier = Modifier)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeNotepadScreen(modifier: Modifier) {
    //Navigation panel vals
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var text by remember { mutableStateOf("") }

    val topBarTitleColor = colorResource(R.color.black)
    val topBarBgColor = colorResource(R.color.white)
    val topBarIconsColor = colorResource(R.color.black)
    val unfocusedTextFieldBgColor = colorResource(R.color.white)

    // Wrapper for Drawer
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onItemClicked = {
                    // Zamykanie Drawer po kliknięciu
                    scope.launch { drawerState.close() }
                }
            )
        },
        content = {
            Scaffold(
                topBar = {
                    //TopBar of HomeScreen
                    TopAppBar(
                        title = { Text(text = "Notepad", color = topBarTitleColor) },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = topBarBgColor),
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch { drawerState.open() } // Drawer open icon
                            }) {
                                Icon(
                                    Icons.Filled.Menu,
                                    contentDescription = "Menu Icon",
                                    tint = topBarIconsColor
                                )
                            }
                        }, actions = {
                            IconButton(onClick = {/* do something */ }) {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = "Search icon",
                                    tint = topBarIconsColor
                                )
                            }
                        }
                    )
                }, // Main Content of HomePage
                content = { paddingValues ->
                },
                bottomBar = {
                    BottomAppBar(
                        modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        containerColor = topBarBgColor,
                        content = {

                            //Add icon
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = "Add note Icon",
                                tint = topBarIconsColor
                            )

                            OutlinedTextField(
                                value = "Enter note", onValueChange = {}, maxLines = 1,
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedContainerColor = unfocusedTextFieldBgColor,
                                    unfocusedBorderColor = unfocusedTextFieldBgColor
                                )
                            )
                            IconButton(onClick = {/* do something */ }) {
                                Icon(
                                    Icons.Filled.AddTask,
                                    contentDescription = "Add tasks Icon",
                                    tint = topBarIconsColor
                                )
                            }
                            IconButton(onClick = {/* do something */ }) {
                                Icon(
                                    Icons.Filled.Voicemail,
                                    contentDescription = "Record voice Icon",
                                    tint = topBarIconsColor
                                )
                            }
                            Spacer(modifier.width(4.dp))
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun DrawerContent(onItemClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight() // Drawer zajmuje 70% szerokości
            .background(Color.White)
            .padding(16.dp) // Główne odstępy od brzegów
    ) {
        // Nagłówek sekcji
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Grupa notatek",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Edytuj",
                color = Color.Magenta,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(8.dp)) // Odstęp między sekcjami
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)

        // Lista grup notatek
        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            groupItem("Niepogrupowane", Color.Red, 38)
            groupItem("Daily life", Color.Green, 0)
            groupItem("Work", Color.Blue, 12)

            // Nowa sekcja dla "Zaszyfruj" i "Kosz"
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                // Element "Zaszyfruj"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        Icons.Outlined.Lock,
                        contentDescription = "Encrypt Icon",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Zaszyfruj",
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Element "Kosz"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        Icons.Outlined.Delete,
                        contentDescription = "Trash Icon",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Kosz",
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

        }
        Row(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth(), verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                Icons.Outlined.Settings,
                contentDescription = "Settings icon",
                modifier = Modifier.size(32.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Ustawienia",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


@Composable
fun groupItem(name: String, dotColor: Color, numberOfNotes: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp), // Odstępy
        verticalAlignment = Alignment.CenterVertically, // Wyśrodkowanie pionowe
        horizontalArrangement = Arrangement.SpaceBetween // Rozłożenie na pełną szerokość
    ) {
        // Sekcja kropki i tekstu
        Row(
            modifier = Modifier.weight(1f), // Równa szerokość dla tej części
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp) // Rozmiar kropki
                    .clip(CircleShape)
                    .background(dotColor)
            )
            Spacer(modifier = Modifier.width(8.dp)) // Odstęp między kropką a tekstem
            Text(text = name, fontWeight = FontWeight.SemiBold)
        }

        // Liczba na końcu
        Text(
            text = numberOfNotes.toString(),
            color = Color.Gray,
            textAlign = TextAlign.End
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    NotepadTheme {
        HomeNotepadScreen(modifier = Modifier)
        // DrawerContent { }
    }
}