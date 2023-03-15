package com.example.calorifyi.Navigation

import com.example.calorifyi.R

class OnboardingItems {
    class OnBoardingItems(
        val image: Int,
        val title: Int,
        val desc: Int
    ) {
        companion object{
            fun getData(): List<OnBoardingItems>{
                return listOf(
                    OnBoardingItems(R.drawable.os_1, R.string.OnboardingTitle1, R.string.Desc1),
                    OnBoardingItems(R.drawable.os_2, R.string.OnboardingTitle2, R.string.Desc2),
                    OnBoardingItems(R.drawable.os_3, R.string.OnboardingTitle3, R.string.Desc3)
                )
            }
        }
    }
}