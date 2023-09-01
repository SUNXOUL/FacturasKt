package com.sagrd.facturasapp.Nav

sealed class AppScreens(val route : String)
{
    object FirstScreen: AppScreens("first_Screen")
    object SecondScreen: AppScreens("second_Screen")
}
