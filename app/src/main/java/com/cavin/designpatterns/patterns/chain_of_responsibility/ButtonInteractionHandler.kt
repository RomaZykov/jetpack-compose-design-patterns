package com.cavin.designpatterns.patterns.chain_of_responsibility

class ButtonInteractionHandler : InteractionHandler() {
    override fun handleInteraction(interactionType: String, onResult: (String) -> Unit) {
        if (interactionType == "buttonClick") {
            onResult("Button Clicked: Button interaction handled.")
        } else {
            handleNext(interactionType, onResult)
        }
    }
}
