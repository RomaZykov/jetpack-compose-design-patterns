package com.cavin.designpatterns.patterns.chain_of_responsibility

class FormInteractionHandler : InteractionHandler() {
    override fun handleInteraction(interactionType: String, onResult: (String) -> Unit) {
        if (interactionType == "formSubmit") {
            onResult("Form submitted successfully.")
        } else {
            handleNext(interactionType, onResult)
        }
    }
}
