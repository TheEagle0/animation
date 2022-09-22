package com.predator.theeagle.animationproject


sealed class ButtonState {
    object Loading : ButtonState()
    object Completed : ButtonState()
}