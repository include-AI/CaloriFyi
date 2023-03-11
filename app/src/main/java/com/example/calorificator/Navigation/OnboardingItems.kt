package com.example.calorificator.Navigation

import com.example.calorificator.R

class OnboardingItems {
    class OnBoardingItems(
        val image: Int,
        val title: Int,
        val desc: Int
    ) {
        companion object{
            fun getData(): List<OnBoardingItems>{
                return listOf(
                    OnBoardingItems(R.drawable.os1, R.string.OnboardingTitle1, R.string.Desc1),
                    OnBoardingItems(R.drawable.os2, R.string.OnboardingTitle2, R.string.Desc2),
                    OnBoardingItems(R.drawable.os3, R.string.OnboardingTitle3, R.string.Desc3)
                )
            }
        }
    }
}