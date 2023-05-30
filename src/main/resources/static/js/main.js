const postCard = document.querySelectorAll('.post-card');

postCard.forEach((card) => {
	card.addEventListener('click', () => {
		window.location.href = '/post/' + card.id;
	});
});