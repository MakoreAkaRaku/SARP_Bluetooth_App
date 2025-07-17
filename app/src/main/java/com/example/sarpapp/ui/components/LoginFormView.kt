package com.example.sarpapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sarpapp.viewmodel.api.TokenViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginFormView(navController: NavController,tokenViewModel: TokenViewModel) {
    val corroutineScope = rememberCoroutineScope()
    var username by rememberSaveable { mutableStateOf("") }
    var pwd by rememberSaveable { mutableStateOf("") }
    var pwdVisible by rememberSaveable { mutableStateOf(false) }
    val usernameOK = username.length <= 16 //TOFIX
    val pwdOK = pwd.length <= 16 //TOFIX

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            label = { Text("Username", textAlign = TextAlign.Center) },
            placeholder = { Text("Your SARP username") },
            textStyle = TextStyle(fontWeight = FontWeight.Bold),
            isError = !usernameOK,
            maxLines = 1,
            singleLine = true,
            onValueChange = {
                username = it
            },
            modifier = Modifier.padding(20.dp),
            shape = RoundedCornerShape(40.dp),
            supportingText = {
                AnimatedVisibility(!usernameOK) {
                    Text(
                        color = MaterialTheme.colorScheme.error,
                        text = "Wrong Username"
                    )
                }

            }
        )
        OutlinedTextField(
            value = pwd,
            isError = !pwdOK,
            onValueChange = {
                pwd = it
            },
            supportingText = {
                AnimatedVisibility(!pwdOK) {
                    Text(
                        text = "The password is too long",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            label = { Text("Password", textAlign = TextAlign.Center) },
            placeholder = { Text("Your SARP password") },
            singleLine = true,
            visualTransformation = if (pwdVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (pwdVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description = if (pwdVisible) "Hide password" else "Show password"
                val iconTint = if (pwdVisible) Color.LightGray else Color.Gray
                IconButton(onClick = { pwdVisible = !pwdVisible }) {
                    Icon(imageVector = image, contentDescription = description, tint = iconTint)
                }
            },
            modifier = Modifier.padding(20.dp),
            shape = RoundedCornerShape(40.dp)
        )
        Button(
            onClick = {
                if (usernameOK && pwdOK) {
                    corroutineScope.launch {
                        if (tokenViewModel.login(username, pwd)) {
                            navController.navigate("main_view")
                        }
                    }
                }
            },
            enabled = usernameOK && pwdOK,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.size(200.dp, 50.dp)
        ) {
            Text("Log In")
        }
    }
}