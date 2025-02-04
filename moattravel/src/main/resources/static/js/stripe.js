const stripe = Stripe('pk_test_51QliOXCIzQesVfMVXxQOYua8cJJOfHuYcoXDk9Mjel17g7MD93mWqYqWdfFkbsgOyjmh51dKnojm7k4XW2CxJEwH00dvKdoUn0');
const paymentButton = document.querySelector('#paymentButton');

paymentButton.addEventListener('click', () => {
	stripe.redirectToCheckout({
		sessionId: sessionId		
	})
});